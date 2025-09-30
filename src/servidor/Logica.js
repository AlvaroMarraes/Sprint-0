class Logica {
  constructor(db) {
    this.db = db;
  }

  guardarMedicion(datos) {
    return new Promise((resolve, reject) => {
      const sql = "INSERT INTO mediciones (sensorId, valor, tiempo) VALUES (?, ?, ?)";
      this.db.run(sql, [datos.sensorId, datos.valor, datos.tiempo], function (err) {
        if (err) {
          reject(err);
        } else {
          resolve({ ok: true, mensaje: "Medición guardada correctamente", id: this.lastID });
        }
      });
    });
  }

  getMedicion(sensorId) {
    return new Promise((resolve, reject) => {
      const sql = "SELECT * FROM mediciones WHERE sensorId = ? ORDER BY tiempo DESC LIMIT 1";
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
