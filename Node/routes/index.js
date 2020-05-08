var express = require('express');
var router = express.Router();
const mongoose=require('mongoose');
const Product=require('../models/product');
/* GET home page. */
router.post('/', function(req, res, next) {
  const product=new Product({
    imagepath:req.body.imagepath,
    title:req.body.title,
    description:req.body.description,
    price:req.body.price
  });
  product.save().then(result=>{
console.log(result);
  }).catch(err=>{
    console.log(err)
  });
  res.status(201).json({
    message:'handling post request to /product',
    createdProduct:product
  });
});

module.exports = router;
