var express = require('express');
var session = require('express-session');
var app = express();
var bodyParser = require('body-parser');
var mailer = require('express-mailer');
var uuid = require('uuid');
var nconf = require('nconf');

// Load the sequelize

var Sequelize = require("sequelize");

// Open a sqlite3 database

var sequelize = new Sequelize('dojochat', '', '', {
  dialect: 'sqlite',
  storage: 'dojochat.db'
});

// Define the data model for user objects

var User = sequelize.define('user', {
  nickname: { type: Sequelize.STRING, field: 'nickname', allowNull: false, primaryKey: true },
  fullName: { type: Sequelize.STRING, field: 'full_name', allowNull: false },
  emailAddress: { type: Sequelize.STRING, field: 'email_address', allowNull: false, unique: true },
  password: { type: Sequelize.STRING, field: 'password' },
  otpToken: { type: Sequelize.STRING, field: 'otp_token' },
  otpExpiry: { type: Sequelize.INTEGER, field: 'otp_expiry' }
});

// Make sure the underlying tables for the user objects exist

sequelize.sync();

// Load the configuration properties from environment variable, command line arguments and 
// the configuration file named config.json. For default values we are specifying the details
// for Google Mail.

nconf
  .env()
  .argv()
  .file({ file: 'config.json' })
  .defaults({
    baseUrl: 'http://localhost:8080',
    mailer: {
      host: 'smtp.gmail.com',
      secure: true,
      port: 465
    }  
  });

// Configure the express-mailer module which is used to send e-mails to users during registration,
// and password reset operations.

var mailer_config = {
  from: nconf.get('mailer:from'),
  host: nconf.get('mailer:host'),
  secureConnection: nconf.get('mailer:secure'),
  port: nconf.get('mailer:port'), 
  transportMethod: 'SMTP',
  auth: {
    user: nconf.get('mailer:username'),
    pass: nconf.get('mailer:password')
  }
};

mailer.extend(app, mailer_config);

app.use("/js", express.static(__dirname + '/js'));

app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());

app.disable('x-powered-by');

app.use(session({
  genid: function() {
    return uuid.v1();
  },
  resave: false,
  saveUninitialized: true,
  secret: 'YgZLfe24sqzEkUrb7eQ263e5'
}));

// Set the location of view and e-mail templates to the the ./views
// sub-directory.

app.set('views', __dirname + '/views');

// Set the template engine that we will use for view and e-mail templates
// to Jade.

app.set('view engine', 'jade');

var port = process.env.PORT || 8080;

var router = express.Router();

// Handle the HTTP POST request to /api/registrations which is used by clients when registering
// a new user.

router.post('/registrations', function(req, res) {
  
  // Handle any error parsing the request payload by returning status code 400 (Bad Request)
  // and an error payload. 
  
  if (!req.body) {
    res.status(400).json({ result: 'ERROR' });
    return;
  }
  
  var user = {
  
    // Copy the new user's full name, nickname and e-mail address from the request payload.
  
    fullName: req.body.fullName,
    nickname: req.body.nickname,
    emailAddress: req.body.emailAddress,
  
    // Generate a globally unique one-time password token that will expire in 1 hr
  
    otpToken: uuid.v1(), 
    otpExpiry: Date.now() + 3600000
  };

  User.create(user)
    .then(function(user) {

      // Send an email using the ./views/registration_mail.jade template and send it to the new user's
      // e-mail address. Also the new user's full name and nickname and the one-time password token
      // are made available for use in the e-mail template.

      var mail_options = {
        to: user.emailAddress,
        subject: 'Dojo Chat registration',
        fullName: user.fullName,
        nickname: user.nickname,
        baseUrl: nconf.get('baseUrl'),
        token: user.otpToken
      };

      app.mailer.send('registration_mail', mail_options, function (err) {

        // Handle any error sending the e-mail by returning status code 500 (Internal Server Error)
        // and an error payload.

        if (err) {
          console.log(err);
          res.status(500).json({ result: 'ERROR' });
          return;
        }
        
        // Otherwise, return status code 200 (OK) by default with an success payload containing
        // the result code, nickname and one-time password token.
        
        res.json({ result: 'OK', nickname: user.nickname, token: user.otpToken });
      });
    })
    .catch(function(err) {
      // Handle any error storing the user record in the database by returning status code 
      // 500 (Internal Server Error) and an error payload.
      res.status(500).json({ result: 'ERROR' });
    });
});

router.put('/registrations/:token', function(req, res) {
  if (!req.body) {
    res.status(400).json({ result: 'OK' });
    return;
  }
  User.update({
    password: req.body.password,
    otpToken: null,
    otpExpiry: null
  }, {
    where: {
      otpToken: req.params.token,
      otpExpiry: { $gt: Date.now() }
    }
  }).then(function(rows){
    if (rows[0] == 0) {
      res.status(404).json({ result: 'ERROR' });
      return;
    }
    res.json({ result: 'OK' });
  }).catch(function(err){
    console.log(err);
    res.status(500).json({ result: 'ERROR' });
  });
});

// 'users' resource

router.get("/users", function(req, res) {
  var offset = req.query.offset || 0;
  var limit = req.query.limit || 1000;
  User.findAll({
    offset: offset,
    limit: limit,
    attributes: [ 'fullName', 'nickname', 'emailAddress' ],
    order: 'fullName asc'  
  }).then(function(rows) {
    res.json({ result: 'OK', offset: offset, users: rows });
  }).catch(function(err) {
    res.status(500).json({ result: 'OK' });
  });
});

router.post("/users", function(req, res) {
  if (!req.body) {
    res.status(400).json({ result: 'ERROR' });
    return;
  }
  var user = {
    nickname: req.body.nickname,
    fullName: req.body.fullName,
    emailAddress: req.body.emailAddress
  };
  User.create(user).then(function(user) {
    res.json({ result: 'OK' });
  }).catch(function(err) {
    res.status(500).json({ result: 'ERROR' });
  });
});

router.get("/users/:username", function(req, res) {
  User.findOne({
    attributes: [ 'fullName', 'nickname', 'emailAddress' ],
    where: {
      $or: [
        { nickname: req.params.username },
        { email_address: req.params.username }
      ]
    }
  }).then(function(user) {
    if (user) {
      res.json({ result: 'OK', user: user });
    } else {
      res.status(404).json({ result: 'ERROR' });
    }
  }).catch(function(err){
    res.status(500).json({ result: 'OK' });
  });
});

router.put("/users/:username", function(req, res) {
  if (!req.body) {
    res.status(400).json({ result: 'ERROR' });
    return;
  }
  var user = { };
  if (req.body.nickname) { user.nickname = req.body.nickname; }
  if (req.body.fullName) { user.fullName = req.body.fullName; }
  if (req.body.emailAddress) { user.emailAddress= req.body.emailAddress; }
  User.update(user, {
    where: {
      $or: [
        { nickname: req.params.username },
        { email_address: req.params.username }
      ]
    }
  }).then(function(rows) {
    if (rows[0] === 0) {
      res.status(404).json({ result: 'ERROR' });
      return;
    }
    res.json({ result: 'OK' });
  }).catch(function(err) {
    res.status(500).json({ result: 'ERROR' });
  })
});

router.delete("/users/:username", function(req, res) {
  User.destroy({
    where: {
      $or: [
        { nickname: req.params.username },
        { email_address: req.params.username }
      ]
    }
  }).then(function(count) {
    if (count === 0) {
      res.status(404).json({ result: 'ERROR' });
      return;
    }
    res.json({ result: 'OK' });
  }).catch(function(err) {
    res.status(500).json({ result: 'ERROR' });
  });
});

router.post("/sessions", function(req, res) {
  if (!req.body) {
    res.status(400).json({ result: 'ERROR'});
    return;
  }
  User.findAndCount({
    where: {
      $and: [{
        $or: [
          { nickname: req.body.username },
          { emailAddress: req.body.username }
        ]
      }, {
        password: req.body.password
      }]
    }
  }).then(function(result) {
    if (result.count === 0) {
      res.status(401).json({ result: 'ERROR' });
      return;
    }
    req.session.user = result.rows[0].user;
    res.json({ result: 'OK' });
  }).catch(function(err) {
    console.log(err);
    res.status(500).json({ result: 'ERROR' });
  })
});

router.delete("/sessions/:id", function(req, res) {
  req.session.destroy(function(err) {
    if (err) {
      res.status(500).json({ result: 'ERROR' });
      return;
    }
    res.json({ result: 'OK' });
  });
});

router.get("/profile", function(req, res) {
  if (req.session.user) {
    req.json({ result: 'OK', user: req.session.user });
  } else {
    res.status(401).json({ result: 'ERROR' });
  }
});

app.use('/api', router);

app.get("/login.html", function(req, res) {
  res.render('login');
});

app.get('/registration.html', function(req, res) {
  res.render('registration');
});

app.get("/index.html", function(req, res) {
  if (req.session.user) {
    res.render('index');
  } else {
    res.redirect("/login.html")
  }
});

app.listen(port);
console.log('Dojo Chat Server running on port ' + port);