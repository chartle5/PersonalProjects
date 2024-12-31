const express = require('express');
const router = express.Router();
const User = require('../models/User');
const Review = require('../models/Review');
const authenticateToken = require('../middleware/authenticateToken');

function checkAdmin(req, res, next) {
  if (!req.user.isAdmin) {
    return res.status(403).json({ message: 'Admin privilege required' });
  }
  next();
}

router.put('/deactivate-user/:id', authenticateToken, checkAdmin, async (req, res) => {
  try {
    const user = await User.findByIdAndUpdate(req.params.id, { isDeactivated: true }, { new: true });
    if (!user) return res.status(404).json({ message: 'User not found' });
    res.json({ message: `User '${user.nickname}' has been deactivated.` });
  } catch (error) {
    res.status(500).json({ message: 'Error deactivating user.', error });
  }
});

router.put('/reactivate-user/:id', authenticateToken, checkAdmin, async (req, res) => {
  try {
    const user = await User.findByIdAndUpdate(req.params.id, { isDeactivated: false }, { new: true });
    if (!user) return res.status(404).json({ message: 'User not found' });
    res.json({ message: `User '${user.nickname}' has been reactivated.` });
  } catch (error) {
    res.status(500).json({ message: 'Error reactivating user.', error });
  }
});

router.put('/grant-admin/:id', authenticateToken, checkAdmin, async (req, res) => {
  try {
    const user = await User.findByIdAndUpdate(req.params.id, { isAdmin: true }, { new: true });
    if (!user) return res.status(404).json({ message: 'User not found' });
    res.json({ message: `User '${user.nickname}' is now an admin.` });
  } catch (error) {
    res.status(500).json({ message: 'Error granting admin privileges.', error });
  }
});

router.put('/hide-review/:id', authenticateToken, checkAdmin, async (req, res) => {
  try {
    const review = await Review.findByIdAndUpdate(req.params.id, { visibility: 'hidden' }, { new: true });
    if (!review) return res.status(404).json({ message: 'Review not found' });
    res.json({ message: 'Review has been hidden.' });
  } catch (error) {
    res.status(500).json({ message: 'Error hiding review.', error });
  }
});

router.put('/unhide-review/:id', authenticateToken, checkAdmin, async (req, res) => {
  try {
    const review = await Review.findByIdAndUpdate(req.params.id, { visibility: 'visible' }, { new: true });
    if (!review) return res.status(404).json({ message: 'Review not found' });
    res.json({ message: 'Review visibility has been restored.' });
  } catch (error) {
    res.status(500).json({ message: 'Error unhiding review.', error });
  }
});

router.get('/users', authenticateToken, async (req, res) => {
    try {
        if (!req.user.isAdmin) {
            return res.status(403).json({ message: 'Access denied. Admins only.' });
        }

        const users = await User.find({}, 'nickname email isDeactivated isAdmin');
        res.status(200).json(users);
    } catch (error) {
        console.error('Error fetching users:', error);
        res.status(500).json({ message: 'Failed to fetch users.' });
    }
});

module.exports = router;
