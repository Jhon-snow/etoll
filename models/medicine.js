const mongoose = require('mongoose');
const Schema=mongoose.Schema;
const medSchema=new Schema({
    _id: mongoose.Schema.Types.ObjectId,
    name:{type:String,required:true},
    price:{type:String,required:true}
});

module.exports = mongoose.model('medicine', medSchema);