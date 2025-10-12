const express = require("express");
const bodyParser = require("body-parser");
const path = require("path");
const Logica = require("./Logica.js");

const rutaBD = path.resolve(__dirname, "mediciones.db");
const logica = new Logica(rutaBD);

const app = express();
app.use(bodyParser.json());
app.use(express.static(path.join(__dirname, "public"))); // Frontend

// POST /api/mediciones -> guardar medición
app.post("/api/mediciones", async (req, res) => {
  try {
    const result = await logica.guardarMedicion(req.body);
    res.json(result);
  } catch (err) {
    res.status(400).json({ error: err.message, body: req.body });
  }
});

// GET /api/mediciones/ultima -> última medición global
app.get("/api/mediciones/ultima", async (req, res) => {
  try {
    const ultima = await logica.obtenerUltimaMedicion();
    res.json(ultima || {});
  } catch (err) {
    res.status(500).json({ error: "Error obteniendo medición", detalles: err.message });
  }
});

// GET / -> index.html
app.get("/", (req, res) => {
  res.sendFile(path.join(__dirname, "index.html"));
});

const PORT = process.env.PORT || 3000;
app.listen(PORT, () => {
  console.log(`Servidor Node.js corriendo en puerto ${PORT}`);
});
