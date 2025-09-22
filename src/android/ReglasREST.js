const express = require("express");

function crearReglasREST(logica) {
    const router = express.Router();

    // POST /medicion -> guardarMedicion
    router.post("/medicion", async (req, res) => {
        try {
            const result = await logica.guardarMedicion(req.body);
            res.json(result);
        } catch (err) {
            console.error(err);
            res.status(500).json({ ok: false, error: "Error al guardar la medición" });
        }
    });

    // GET /medicion/:sensorId -> getMedicion
    router.get("/medicion/:sensorId", async (req, res) => {
        try {
            const result = await logica.getMedicion(req.params.sensorId);
            if (result) {
                res.json(result);
            } else {
                res.status(404).json({ ok: false, mensaje: "No se encontró medición" });
            }
        } catch (err) {
            console.error(err);
            res.status(500).json({ ok: false, error: "Error al obtener la medición" });
        }
    });

    return router;
}

module.exports = crearReglasREST;
