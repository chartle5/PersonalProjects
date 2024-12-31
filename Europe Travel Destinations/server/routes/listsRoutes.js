const express = require('express');
const router = express.Router();
const List = require('../models/List');
const authenticateToken = require('../middleware/authenticateToken');

router.post('/:listName', authenticateToken, async (req, res) => {
    const { listName } = req.params;
    const { destinations } = req.body;
  
    if (!listName || !destinations || destinations.length === 0) {
      return res.status(400).json({ error: 'List name and destinations are required.' });
    }
  
    const formattedDestinations = destinations.map((destination) => ({
      Destination: destination.Destination,
      Country: destination.Country || 'Unknown',
    }));
  
    try {
      let list = await List.findOne({ title: listName, userId: req.user.id });
  
      if (list) {
        list.destinations = Array.from(new Set([...list.destinations, ...destinations]));
        await list.save();
        return res.status(200).json({ message: `List '${listName}' updated successfully.`, list });
      }
  
      const newList = new List({
        title: listName,
        destinations,
        userId: req.user.id,
      });
  
      await newList.save();
      res.status(201).json({ message: `List '${listName}' created successfully.`, list: newList });
    } catch (error) {
      console.error('Error saving list:', error);
      res.status(500).json({ error: 'Failed to save the list.' });
    }
  });
  

  router.get('/', authenticateToken, async (req, res) => {
    try {
      const lists = await List.find({ userId: req.user.id }).sort({ createdAt: -1 });
  
      const formattedLists = lists.map((list) => ({
        title: list.title,
        destinations: list.destinations.map((dest) => 
          typeof dest === 'string' 
            ? { Destination: dest, Country: 'Unknown' } 
            : dest
        ),
      }));
  
      res.status(200).json(formattedLists);
    } catch (error) {
      console.error('Error fetching lists:', error);
      res.status(500).json({ error: 'Failed to fetch lists.' });
    }
  });
  

router.delete('/:listName', authenticateToken, async (req, res) => {
  const { listName } = req.params;

  try {
    const result = await List.deleteOne({ title: listName, userId: req.user.id });
    if (result.deletedCount === 0) {
      return res.status(404).json({ error: 'List not found.' });
    }
    res.status(200).json({ message: `List '${listName}' deleted successfully.` });
  } catch (error) {
    console.error('Error deleting list:', error);
    res.status(500).json({ error: 'Failed to delete the list.' });
  }
});

router.get('/:listName', authenticateToken, async (req, res) => {
  const { listName } = req.params;

  try {
    const list = await List.findOne({ title: listName, userId: req.user.id });
    if (!list) {
      return res.status(404).json({ error: 'List not found.' });
    }
    res.status(200).json(list);
  } catch (error) {
    console.error('Error fetching list details:', error);
    res.status(500).json({ error: 'Failed to fetch list details.' });
  }
});

router.put('/:listName', authenticateToken, async (req, res) => {
  const { listName } = req.params;
  const { destinations } = req.body;

  if (!destinations || !Array.isArray(destinations)) {
    return res.status(400).json({ error: 'Invalid destinations payload' });
  }

  try {
    const list = await List.findOneAndUpdate(
      { title: listName, userId: req.user.id }, // Use `title` instead of `name`
      { $set: { destinations } },
      { new: true }
    );

    if (!list) {
      return res.status(404).json({ error: 'List not found' });
    }

    res.status(200).json({ message: 'List updated successfully', list });
  } catch (err) {
    console.error('Error updating list:', err);
    res.status(500).json({ error: 'Internal server error' });
  }
});



module.exports = router;

