const express = require('express');
const router = express.Router();
const authenticateToken = require('../middleware/authenticateToken');
const PublicList = require('../models/publicList');
const Review = require('../models/Review');

function checkAdmin(req, res, next) {
  if (!req.user || !req.user.isAdmin) {
    return res.status(403).json({ message: 'Admin privilege required' });
  }
  next();
}


router.post('/:id', authenticateToken, async (req, res) => {
  const { id } = req.params; 
  const { rating, comment } = req.body;

  if (!rating || rating < 1 || rating > 5) {
    return res.status(400).json({ message: 'Rating must be between 1 and 5.' });
  }

  try {
    const publicList = await PublicList.findById(id);
    if (!publicList) {
      return res.status(404).json({ message: 'Public list not found.' });
    }

    const review = new Review({
      listId: id,
      userId: req.user.id,
      rating,
      comment,
    });

    await review.save();

    
    publicList.reviews.push(review._id);
    await publicList.save();

    res.status(201).json({ message: 'Review added successfully.', review });
  } catch (error) {
    console.error('Error adding review:', error);
    res.status(500).json({ message: 'Failed to add review.' });
  }
});


router.put('/hide/:id', authenticateToken, checkAdmin, async (req, res) => {
    try {
        const review = await Review.findByIdAndUpdate(
            req.params.id,
            { visibility: 'hidden' },
            { new: true }
        );

        if (!review) {
            return res.status(404).json({ message: 'Review not found.' });
        }

        res.json({ message: 'Review hidden successfully.', review });
    } catch (error) {
        console.error('Error hiding review:', error);
        res.status(500).json({ message: 'Failed to hide review.', error });
    }
});


router.put('/reviews/unhide/:id', authenticateToken, checkAdmin, async (req, res) => {
  try {
    const review = await Review.findByIdAndUpdate(
      req.params.id,
      { visibility: 'visible' },
      { new: true }
    );

    if (!review) {
      return res.status(404).json({ message: 'Review not found.' });
    }

    res.json({ message: 'Review visibility restored.', review });
  } catch (error) {
    console.error('Error unhiding review:', error);
    res.status(500).json({ message: 'Failed to unhide review.', error });
  }
});

module.exports = router;

