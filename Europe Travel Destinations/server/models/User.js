const mongoose = require('mongoose');
const bcrypt = require('bcrypt');

const userSchema = new mongoose.Schema({
  nickname: { type: String, required: true },
  email: { type: String, required: true, unique: true },
  password: { type: String, required: true },
  isAdmin: {type: Boolean, default: false },
  isDeactivated: {type: Boolean, default: false },
});

const User = mongoose.model('User', userSchema);
module.exports = User;