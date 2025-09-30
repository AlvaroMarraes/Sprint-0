const express = require("express");
const sqlite3 = require("sqlite3").verbose();
const bodyParser = require("body-parser");
const path = require("path");

const Logica = require("./Logica.js");
const crearReglasREST = require("./crearReglasREST");

const app = express();
const db = new sqlite3.Database("mediciones.db");

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

// Crear lógica y montar reglas REST
const logica = new Logica(db);
app.use("/api", crearReglasREST(logica));

// Servir archivos estáticos (frontend)
app.use(express.static(path.join(__dirname, "public")));

// Puerto (Plesk inyecta uno si usas Passenger, por eso || 3000)
const PORT = process.env.PORT || 3000;
app.listen(PORT, () => {
  console.log(`Servidor escuchando en http://localhost:${PORT}`);
});
