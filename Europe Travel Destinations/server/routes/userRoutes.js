const express = require('express');
const router = express.Router();
const User = require('../models/User');
const bcrypt = require('bcrypt');
const jwt = require('jsonwebtoken');
const authenticateToken = require('../middleware/authenticateToken');

const SECRET_KEY = process.env.JWT_SECRET;


router.post('/register', async (req, res) => {
  const { nickname, email, password } = req.body;

  if (!nickname || !email || !password) {
    return res.status(400).json({ message: 'All fields are required' });
  }

  try {
    const existingUser = await User.findOne({ email });
    if (existingUser) {
      return res.status(400).json({ message: 'This email is already registered.' });
    }

    const hashedPassword = await bcrypt.hash(password, 10);

    const newUser = new User({
      nickname,
      email,
      password: hashedPassword,
      isVerified: true, 
    });

    await newUser.save();

    return res.status(201).json({ message: 'User registered successfully. You can now log in.' });
  } catch (error) {
    console.error('Error registering user:', error);
    return res.status(500).json({ message: 'An error occurred during registration.' });
  }
});

router.post('/login', async (req, res) => {
  const { email, password } = req.body;

  if (!email || !password) {
    return res.status(400).json({ message: 'Email and password are required' });
  }

  try {
    const user = await User.findOne({ email });
    if (!user) {
      return res.status(400).json({ message: 'User not found' });
    }

    
    if (user.isDeactivated) {
      return res.status(403).json({ message: 'Your account has been deactivated. Please contact chartle5@uwo.ca' });
    }

    const isPasswordCorrect = await bcrypt.compare(password, user.password);
    if (!isPasswordCorrect) {
      return res.status(400).json({ message: 'Invalid credentials' });
    }

    const token = jwt.sign(
      { id: user._id, nickname: user.nickname, isAdmin: user.isAdmin },
      SECRET_KEY,
      { expiresIn: '1h' }
    );

    res.status(200).json({
      message: 'Login successful',
      token,
      isAdmin: user.isAdmin,
    });
  } catch (error) {
    console.error('Error during login:', error);
    res.status(500).json({ message: 'An error occurred', error });
  }
});

router.post('/update-password', authenticateToken, async (req, res) => {
  const { currentPassword, newPassword, confirmPassword } = req.body;

  if (!currentPassword || !newPassword || !confirmPassword) {
    return res.status(400).json({ message: 'All fields are required.' });
  }

  if (newPassword !== confirmPassword) {
    return res.status(400).json({ message: 'New passwords do not match.' });
  }

  try {
    const user = await User.findById(req.user.id);

    if (!user) {
      return res.status(404).json({ message: 'User not found.' });
    }

    const isPasswordCorrect = await bcrypt.compare(currentPassword, user.password);
    if (!isPasswordCorrect) {
      return res.status(401).json({ message: 'Current password is incorrect.' });
    }

    const hashedPassword = await bcrypt.hash(newPassword, 10);

    user.password = hashedPassword;
    await user.save();

    res.status(200).json({ message: 'Password updated successfully.' });
  } catch (error) {
    console.error('Error updating password:', error);
    res.status(500).json({ message: 'Failed to update password.' });
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
