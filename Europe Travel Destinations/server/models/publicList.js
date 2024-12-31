const mongoose = require('mongoose');

const publicListSchema = new mongoose.Schema({
  name: { type: String, required: true },
  destinations: [
    {
      Destination: { type: String, required: true },
      Country: { type: String, required: true },
    },
  ],
  createdBy: { type: String, required: true },
  reviews: [{ type: mongoose.Schema.Types.ObjectId, ref: 'Review' }],
}, { timestamps: true });

module.exports = mongoose.model('PublicList', publicListSchema);
