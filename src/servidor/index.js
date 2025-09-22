const express = require("express");
const bodyParser = require("body-parser");
const path = require("path");
const db = require("./db");
const Logica = require("./Logica");
const crearReglasREST = require("./ReglasREST");

const app = express();
const logica = new Logica(db);

// Middleware
app.use(bodyParser.json());

// Rutas REST bajo /api
app.use("/api-sensores", crearReglasREST(logica));

// Servir la carpeta public
app.use(express.static(path.join(__dirname, "public")));

const PORT = 3000;
app.listen(PORT, () => {
    console.log(`Servidor escuchando en http://localhost:${PORT}`);
});

