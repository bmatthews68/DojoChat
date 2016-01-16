var fs = require('fs');
var file = 'chat.db';
var exists = fs.existsSync(file);
var sqlite3 = require('sqlite3').verbose();
var db = new sqlite3.Database(file);

var express = require('express');
var app = express();
var bodyParser = require('body-parser');
var mailer = require('express-mailer');
var uuid = require('uuid');
var nconf = require('nconf');

 db.serialize(function() {
   if (!exists) {
    db.run('create table users (nickname text, full_name text, email_address text, password text, otp_token text, otp_expiry integer)');
  }
});

nconf.env().argv().file({ file: 'config.json' });

mailer.extend(app, {
  from: nconf.get('mailer:from'),
  host: nconf.get('mailer:host'),
  secureConnection: true,
  port: 465,
  transportMethod: 'SMTP',
  auth: {
    user: nconf.get('mailer:username'),
    pass: nconf.get('mailer:password')
  }
});

app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());

app.set('views', __dirname + '/views');
app.set('view engine', 'jade');

var port = process.env.PORT || 8080;

var router = express.Router();

router.post('/registrations', function(req, res) {
  if (!req.body) {
    res.status(400).json({ result: 'ERROR' });
    return;
  }
  var user = {
    fullName: req.body.fullName,
    nickname: req.body.nickname,
    emailAddress: req.body.emailAddress,
    otp: {
      token: uuid.v1(),
      expiry: Date.now() + 3600000
    }
  };

  db.serialize(function() {
    var stmt = db.prepare('insert into users (nickname, full_name, email_address, otp_token, otp_expiry) values (?, ?, ?, ?, ?)');
    stmt.run(user.nickname, user.fullName, user.emailAddress, user.otp.token, user.otp.expiry, function(err) {
      if (err) {
        console.log(err);
        res.status(500).json({ result: 'ERROR' });
      } else {
        app.mailer.send('registration_mail', {
          to: user.emailAddress,
          subject: 'Dojo Chat registration',
          fullName: user.fullName,
          nickname: user.nickname,
          token: user.otp.token
        }, function (err) {
          if (err) {
            console.log(err);
            res.status(500).json({ result: 'ERROR' });
            return;
          }
          console.log('Registered ' + user.otp.token);
          res.json({ result: 'OK', nickname: user.nickname, token: user.otp.token });
        });
      }
    });
    stmt.finalize();
  });
});

router.put('/registrations/:token', function(req, res) {
  if (!req.body) {
    res.status(400).json({ result: 'OK' });
    return;
  }
  db.serialize(function() {
    var stmt = db.prepare('update users set otp_token = null, otp_expiry = null, password = ? where otp_token = ? and otp_expiry > ?');
    stmt.run(req.body.password, req.params.token, Date.now(), function(err) {
      if (err) {
        console.log(err);
        res.status(500).json({ result: 'ERROR' });
      } else {
        if (this.changes > 0) {
          res.status(200).json({ result: 'OK' });
        } else {
          res.status(404).json({ result: 'ERROR' });
        }
      }
    });
    stmt.finalize();
  });
});

router.post("/login", function(req, res) {
  if (!req.body) {
    res.status(400).json({ result: 'ERROR'});
    return;
  }
  var username = req.body.username;
  var password = req.body.password;
  for (var i = 0; i < users.length; ++i) {
    if (users[i].nickname === username || users[i].emailAddress === username) {
      if (users[i].password === password) {
        res.json({ result: 'OK'});
      } else {
        res.status(401).json({ result: 'ERROR' });
      }
      return;
    }
  }
  res.status(404).json({ result: 'ERROR' });
});

app.use('/api', router);

app.listen(port);
console.log('Dojo Chat Server running on port ' + port);