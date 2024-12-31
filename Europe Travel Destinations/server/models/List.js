const mongoose = require('mongoose');

const listSchema = new mongoose.Schema({
  title: { type: String, required: true },
  destinations: [
    {
      Destination: { type: String, required: true },
      Country: { type: String, required: true },
    },
  ],
  userId: { type: mongoose.Schema.Types.ObjectId, ref: 'User', required: true },
  createdAt: { type: Date, default: Date.now },
});

module.exports = mongoose.model('List', listSchema);


