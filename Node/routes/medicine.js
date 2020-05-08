var express = require('express');
var router = express.Router();
const mongoose=require('mongoose');
const Productmed=require('../models/medicine');
/* GET home page. */
router.post('/save', function(req, res, next) {
  const product=new Productmed({
    _id: new mongoose.Types.ObjectId(),
   name:req.body.name,
    price:req.body.price
  });
  product.save().then(result=>{
console.log(result);
  }).catch(err=>{
    console.log(err)
  });
  res.status(201).json({
    message:'handling post request to /save',
    createdProductmed:product
  });
});


router.post("/getmed", (req, res, next) => {
    Productmed.find({ medname: req.body.medname })
      .exec()
      .then(result => {
        if (result.length < 1) {
            return res.status(401).json({
              message: "stock not available"
            });
          }
          if (result) {
            
            return res.status(200).json({
              message: "medicine find",
             price: result[0].price
            });
          }
      })
          
      .catch(err => {
        console.log(err);
        res.status(500).json({
          error: err
        });
      });
  });
  

module.exports = router;