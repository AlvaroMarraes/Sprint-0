const sqlite3 = require("sqlite3").verbose();
const db = new sqlite3.Database("Sprint0.db");

module.exports = db;