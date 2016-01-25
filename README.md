# Dojo Chat

## Server

The Dojo Chat Server is built using NodeJS. Before you start you should download and
install NodeJS from http://nodejs

npm init

npm install -g grunt-cli

npm install --save body-parser express express-mailer express-session jade nconf sqlite3 uuid

npm install --save sequelize connect-session-sequelize

Storing data

During prototyping we will be using SQLite 3 to store data. 

npm install sqlite3 --save

http://blog.modulus.io/nodejs-and-sqlite

SQLite 3 stores records in a file (in our case it will be called chat.db) and to be able to read
it you need to http://sqlitebrowser.org


## Android App

You are going to be running the Server on you laptop and your Android Virtual Device
needs to know how to access it.

C:\Android\SDK\platform-tools\adb pull /system/etc/hosts .

C:\Android\SDK\platform-tools\adb remount

Add:
10.0.2.2 chat.coderdojowarehouse.com

C:\Android\SDK\platform-tools\adb push hosts /system/etc