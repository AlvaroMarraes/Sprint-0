/*
 * Test de la API REST de mediciones.
 * Inserta una medición (POST) y comprueba que la última (GET) coincide.
 */

async function testAPI() {
  try {
    // Crear una nueva medición
    const nuevaMedicion = {
      sensorId: 12,
      valor: 555
    };

    let res = await fetch("http://localhost:3000/api/mediciones", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(nuevaMedicion)
    });

    const postData = await res.json();
    console.log("POST status:", res.status);
    console.log("POST respuesta:", postData);

    //Obtener la última medición
    res = await fetch("http://localhost:3000/api/mediciones/ultima");
    const ultima = await res.json();

    console.log("GET status:", res.status);
    console.log("GET respuesta:", ultima);

    //Comprobar si la última medición coincide con la enviada
    if (
      ultima.sensorId === nuevaMedicion.sensorId &&
      ultima.valor === nuevaMedicion.valor
    ) {
      console.log("Test OK: la API devuelve la última medición correctamente");
    } else {
      console.log("Test FAIL: la última medición no coincide con la enviada");
    }

  } catch (err) {
    console.error("Error en el test:", err);
  }
}

// Ejecutar test
testAPI();
