class Logica {
  constructor(db) {
    this.db = db;
  }

  // Guardar una medición
  guardarMedicion(datos) {
    return new Promise((resolve, reject) => {
      const sql = "INSERT INTO mediciones (sensorId, valor, timestamp) VALUES (?, ?, ?)";
      this.db.run(sql, [datos.sensorId, datos.valor, datos.timestamp], function(err) {
        if (err) {
          reject(err);
        } else {
          resolve({ ok: true, mensaje: "Medición guardada correctamente", id: this.lastID });
        }
      });
    });
  }

  // Obtener la última medición de un sensor
  getMedicion(sensorId) {
    return new Promise((resolve, reject) => {
      const sql = "SELECT * FROM mediciones WHERE sensorId = ? ORDER BY timestamp DESC LIMIT 1";
      this.db.get(sql, [sensorId], (err, row) => {
        if (err) {
          reject(err);
        } else {
          resolve(row || null);
        }
      });
    });
  }
}

module.exports = Logica;
