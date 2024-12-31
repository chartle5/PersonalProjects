require('dotenv').config();
const express = require('express');
const mongoose = require('mongoose');
const cors = require('cors');
const bodyParser = require('body-parser');
const jwt = require('jsonwebtoken');
const fs = require('fs');
const csv = require('csv-parser');
const path = require('path');
const userRoutes = require('./routes/userRoutes');
const User = require('./models/User');
const PublicList = require('./models/publicList');
const publicListRoutes = require('./routes/publicListRoutes');
const Fuse = require('fuse.js');
const bcrypt = require('bcrypt');
const reviewRoutes = require('./routes/reviewRoutes');
const adminRoutes = require('./routes/adminRoutes');


const app = express();
const PORT = process.env.PORT || 5001;
const MONGO_URI = process.env.MONGO_URI;

let destinations = [];
let countries = new Set();
let destinationLists = {};
let destinationIdCounter = 0;

app.use(cors({
  origin: 'http://localhost:4200',
  methods: ['GET', 'POST', 'PUT', 'DELETE', 'OPTIONS'],
  allowedHeaders: ['Content-Type', 'Authorization']
}));

app.use(bodyParser.json());

mongoose.connect(MONGO_URI)
  .then(() => console.log('Connected to MongoDB'))
  .catch((error) => console.error('Error connecting to MongoDB:', error));

fs.createReadStream('./data/destinations.csv', { encoding: 'utf8' })
  .pipe(csv())
  .on('data', (row) => {
    row.destinationId = destinationIdCounter++;
    destinations.push(row);
    countries.add(row.Country);
  })
  .on('end', () => {
    console.log('CSV file successfully processed');
  });


function authenticateToken(req, res, next) {
  const token = req.header('Authorization');
  if (!token) return res.status(401).json({ message: 'Access Denied' });

  try {
    const verified = jwt.verify(token, process.env.JWT_SECRET);
    req.user = verified; 
    next();
  } catch (err) {
    res.status(400).json({ message: 'Invalid Token' });
  }
}

app.get('/destination/:id', (req, res) => {
  const id = parseInt(req.params.id, 10);
  const destination = destinations.find(dest => dest.destinationId === id);

  if (!destination) {
    return res.status(404).json({ error: 'Destination not found' });
  }
  res.json(destination);
});

app.get('/destination/:id/coordinates', (req, res) => {
  const id = parseInt(req.params.id, 10);
  const destination = destinations.find(dest => dest.destinationId === id);

  if (!destination) {
    return res.status(404).json({ error: 'Destination not found' });
  }

  const latitude = parseFloat(destination.Latitude);
  const longitude = parseFloat(destination.Longitude);

  if (!isNaN(latitude) && !isNaN(longitude)) {
    res.json({ latitude, longitude });
  } else {
    res.status(404).json({ error: 'Coordinates not available' });
  }
});


app.get('/countries', (req, res) => {
  res.json(Array.from(countries));
});

app.get('/search', (req, res) => {
  const { query = '', searchType = 'Destination', n = 5, page = 1 } = req.query;

  const normalizeString = (str) => str.trim().toLowerCase().replace(/\s+/g, ' ');
  const normalizedQuery = normalizeString(query);

  const resultsPerPage = parseInt(n, 10);
  const currentPage = parseInt(page, 10);

  
  const normalizedDestinations = destinations.map((dest) => ({
    ...dest,
    Destination: normalizeString(dest.Destination),
    Region: normalizeString(dest.Region),
    Country: normalizeString(dest.Country),
  }));

  const startsWithMatches = normalizedDestinations.filter((dest) =>
    dest[searchType].startsWith(normalizedQuery)
  );

  let mergedResults = startsWithMatches; 

  
  if (normalizedQuery.length >= 4) {
    const options = {
      keys: [searchType],
      threshold: 0.3, 
      includeScore: true,
      isCaseSensitive: false,
      distance: 100,
    };

    const fuse = new Fuse(normalizedDestinations, options);

    const fuzzyMatches = fuse.search(normalizedQuery).map((match) => match.item);

    mergedResults = [
      ...startsWithMatches,
      ...fuzzyMatches.filter(
        (item) => !startsWithMatches.some((match) => match.Destination === item.Destination)
      ),
    ];
  }

  const totalResults = mergedResults.length;
  const startIndex = (currentPage - 1) * resultsPerPage;
  const pageResults = mergedResults.slice(startIndex, startIndex + resultsPerPage);

  res.json({ totalResults, results: pageResults });
});










app.post('/lists/:listName', authenticateToken, (req, res) => {
  const listName = req.params.listName;

  if (destinationLists[listName]) {
    return res.status(400).json({ error: 'List with this name already exists' });
  }

  destinationLists[listName] = [];
  res.json({ message: `List '${listName}' created successfully` });
});

app.post('/lists/:listName/add', authenticateToken, (req, res) => {
  const listName = req.params.listName;
  const { destinations: destinationIds } = req.body;

  if (!destinationLists[listName]) {
    return res.status(404).json({ error: 'List not found' });
  }

  destinationLists[listName] = Array.from(
    new Set([...destinationLists[listName], ...destinationIds])
  );
  res.json({ message: `Destinations added to list '${listName}' successfully!` });
});

app.get('/lists/:listName', authenticateToken, (req, res) => {
  const listName = req.params.listName;

  if (!destinationLists[listName]) {
    return res.status(404).json({ error: 'List not found' });
  }

  const listDetails = destinationLists[listName].map(id =>
    destinations.find(dest => dest.destinationId === id)
  );

  res.json(listDetails);
});

app.delete('/lists/:listName', authenticateToken, (req, res) => {
  const listName = req.params.listName;

  if (!destinationLists[listName]) {
    return res.status(404).json({ error: 'List not found' });
  }

  delete destinationLists[listName];
  res.json({ message: `List '${listName}' deleted successfully` });
});

app.listen(PORT, () => {
  console.log(`Server is running on http://localhost:${PORT}`);
});

const listsRoutes = require('./routes/listsRoutes');
app.use('/api/lists', listsRoutes);

app.get('/public-lists', async (req, res) => {
  try {
    const publicLists = await PublicList.find().sort({ createdAt: -1 }); 
    res.status(200).json(publicLists);
  } catch (err) {
    console.error('Error fetching public lists:', err);
    res.status(500).json({ error: 'Failed to fetch public lists.' });
  }
});


app.post('/public-lists', authenticateToken, async (req, res) => {
  const { name, destinations } = req.body;

  if (!name || !destinations || !Array.isArray(destinations)) {
    return res.status(400).json({ error: 'Invalid list data' });
  }

  try {
    const newList = new PublicList({
      name,
      destinations,
      createdBy: req.user.nickname, 
    });

    await newList.save();
    res.status(201).json({ message: `List '${name}' posted successfully!`, list: newList });
  } catch (err) {
    console.error('Error saving public list:', err);
    res.status(500).json({ error: 'Failed to save the public list.' });
  }
});


app.delete('/public-lists/:id', authenticateToken, async (req, res) => {
  const { id } = req.params;

  try {
    const deletedList = await PublicList.findByIdAndDelete(id);

    if (!deletedList) {
      return res.status(404).json({ error: 'List not found.' });
    }

    res.status(200).json({ message: `List '${deletedList.name}' deleted successfully.` });
  } catch (err) {
    console.error('Error deleting public list:', err);
    res.status(500).json({ error: 'Failed to delete the public list.' });
  }
});

app.use('/api', userRoutes);
app.use('/api/public-lists', publicListRoutes);
app.use('/api/reviews', reviewRoutes);
app.use('/api/users', userRoutes);
app.use('/api/admin', adminRoutes);




async function createInitialAdmin() {
  try {
    const nickname = 'chartle5';
    const password = '36203Aa!';
    const email = 'chartle5@uwo.ca';


    const existingAdmin = await User.findOne({ nickname });
    if (!existingAdmin) {
      const hashedPassword = await bcrypt.hash(password, 10);
      const adminUser = new User({
        nickname,
        email,
        password: hashedPassword,
        isAdmin: true,
      });
      await adminUser.save();
      console.log('Admin user created successfully.');
    } else {
      console.log('Admin user already exists.');
    }
  } catch (error) {
    console.error('Error creating admin user:', error);
  }
}

mongoose.connect(process.env.MONGO_URI)
  .then(async () => {
    console.log('Connected to MongoDB');
    await createInitialAdmin(); 
  })
  .catch((error) => {
    console.error('MongoDB connection error:', error);
  });