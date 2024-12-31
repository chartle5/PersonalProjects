const mongoose = require('mongoose');

const reviewSchema = new mongoose.Schema({
  listId: { type: mongoose.Schema.Types.ObjectId, required: true, ref: 'PublicList' },
  userId: { type: mongoose.Schema.Types.ObjectId, required: true, ref: 'User' },
  rating: { type: Number, required: true, min: 1, max: 5 },
  comment: { type: String, default: '' },
  createdAt: { type: Date, default: Date.now },
  visibility: { type: String, default: 'visible' }, 
});

module.exports = mongoose.model('Review', reviewSchema);
