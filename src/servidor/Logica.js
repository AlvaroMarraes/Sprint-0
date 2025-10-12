const sqlite3 = require("sqlite3").verbose();
const path = require("path");

class Logica {
  constructor(nombreBD) {
    const rutaBD = path.resolve(nombreBD);
    this.db = new sqlite3.Database(rutaBD, (err) => {
      if (err) console.error("Error abriendo la BD:", err.message);
    });

    this.db.run(`CREATE TABLE IF NOT EXISTS Mediciones (
      Id INTEGER PRIMARY KEY AUTOINCREMENT,
      sensorId INTEGER NOT NULL,
      valor REAL NOT NULL,
      timestamp TEXT NOT NULL
    )`, (err) => {
      if (err) console.error("Error creando tabla:", err.message);
    });
  }

  // Guardar una medición
  guardarMedicion(datos) {
    return new Promise((resolve, reject) => {
      const sensorId = parseInt(datos.sensorId);
      const valor = Number(datos.valor);
      const timestamp = new Date().toISOString();

      if (isNaN(sensorId) || isNaN(valor)) {
        return reject(new Error("Datos inválidos"));
      }

      this.db.run(
        "INSERT INTO Mediciones (sensorId, valor, timestamp) VALUES (?,?,?)",
        [sensorId, valor, timestamp],
        function (err) {
          if (err) reject(err);
          else resolve({ ok: true, id: this.lastID });
        }
      );
    });
  }

  // Obtener la última medición global (opcional)
  obtenerUltimaMedicion() {
    return new Promise((resolve, reject) => {
      this.db.get(
        "SELECT * FROM Mediciones ORDER BY timestamp DESC LIMIT 1",
        [],
        (err, row) => {
          if (err) reject(err);
          else resolve(row || null);
        }
      );
    });
  }
}

module.exports = Logica;
