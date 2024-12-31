const express = require('express');
const router = express.Router();
const authenticateToken = require('../middleware/authenticateToken');
const PublicList = require('../models/publicList');

router.get('/', async (req, res) => {
  try {
      
      const isAdmin = req.user?.isAdmin || false;

      const publicLists = await PublicList.find()
          .populate({
              path: 'reviews',
              match: isAdmin ? {} : { visibility: { $ne: 'hidden' } }, 
          })
          .sort({ createdAt: -1 })
          .lean();

      const formattedLists = publicLists.map((list) => ({
          ...list,
          destinations: list.destinations.map((dest) => ({
              Destination: dest.Destination,
              Country: dest.Country,
          })),
      }));

      res.status(200).json(formattedLists);
  } catch (err) {
      console.error('Error fetching public lists:', err);
      res.status(500).json({ error: 'Failed to fetch public lists.' });
  }
});


router.post('/', authenticateToken, async (req, res) => {
  const { name, destinations } = req.body;

  if (!name || !Array.isArray(destinations) || destinations.length === 0) {
    return res.status(400).json({ error: 'Invalid list data.' });
  }

  try {
    const newList = new PublicList({
      name,
      destinations,
      createdBy: req.user.nickname,
    });

    await newList.save();
    res.status(201).json({ message: `List '${name}' created successfully.`, list: newList });
  } catch (err) {
    console.error('Error creating public list:', err);
    res.status(500).json({ error: 'Failed to create public list.' });
  }
});

router.delete('/:id', authenticateToken, async (req, res) => {
  const { id } = req.params;

  try {
    const deletedList = await PublicList.findByIdAndDelete(id);

    if (!deletedList) {
      return res.status(404).json({ error: 'Public list not found.' });
    }

    res.status(200).json({ message: `List '${deletedList.name}' deleted successfully.` });
  } catch (err) {
    console.error('Error deleting public list:', err);
    res.status(500).json({ error: 'Failed to delete public list.' });
  }
});

module.exports = router;

