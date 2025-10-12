const express = require("express");
const bodyParser = require("body-parser");
const path = require("path");
const Logica = require("./Logica.js");

// Ruta de la base de datos
const rutaBD = path.resolve(__dirname, "mediciones.db");
const logica = new Logica(rutaBD);

const app = express();

// Middleware
app.use(bodyParser.json());
app.use(express.static(path.join(__dirname, "cliente"))); // ruta correcta en Plesk

// Servir index.html al abrir la raíz
app.get("/", (req, res) => {
  console.log("GET / -> sirviendo index.html");
  res.sendFile(path.join(__dirname, "cliente/index.html"));
});

// API para guardar medición
app.post("/api/mediciones", async (req, res) => {
  console.log("POST /api/mediciones -> body recibido:", req.body);
  try {
    const result = await logica.guardarMedicion(req.body);
    console.log("Medición guardada con ID:", result.id);
    res.json(result);
  } catch (err) {
    console.error("Error guardando medición:", err.message);
    res.status(400).json({ error: err.message, body: req.body });
  }
});

// API para obtener la última medición
app.get("/api/mediciones/ultima", async (req, res) => {
  console.log("GET /api/mediciones/ultima -> solicitando última medición");
  try {
    const ultima = await logica.obtenerUltimaMedicion();
    console.log("Última medición:", ultima);
    res.json(ultima || {});
  } catch (err) {
    console.error("Error obteniendo medición:", err.message);
    res.status(500).json({ error: "Error obteniendo medición", detalles: err.message });
  }
});

// Arranque del servidor
const PORT = process.env.PORT || 3000;
app.listen(PORT, () => {
  console.log(`Servidor Node.js corriendo en puerto ${PORT}`);
});
