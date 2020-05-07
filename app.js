var express = require('express');
var path = require('path');
var cookieParser = require('cookie-parser');
var logger = require('morgan');
const bodyParser=require('body-parser');
const mongoose=require('mongoose');

//var Router = require('./routes');
//var usersRouter = require('./routes/users');
const userRoutes = require('./routes/user');
const medRoutes = require('./routes/medicine');
mongoose.connect('mongodb://localhost:27017/testing');
var app = express();

app.use(logger('dev'));
app.use(express.json());
app.use(express.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));
app.use(bodyParser.json());
app.get('/',(req,res)=>{
    res.json({
        message:'Hello world'
    });
});
//app.use('/Router', Router);
app.use("/user", userRoutes);
app.use("/meduser", medRoutes);
//app.use('/users', usersRouter);
const port=process.env.port||3000;
app.listen(port,()=>{
    console.log('Listening on port',port)
})
//module.exports = app;
