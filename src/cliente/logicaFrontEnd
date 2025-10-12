// Función para obtener la última medición de un sensor
async function obtenerUltimaMedicion(sensorId = 11) {
  try {
    const res = await fetch(`/api/medicion/${sensorId}`);
    if (!res.ok) throw new Error("Error cargando medición");

    const data = await res.json();

    document.getElementById("salida").innerHTML = `
      <div><span class="label">Sensor ID:</span> ${data.sensorId || "--"}</div>
      <div><span class="label">Valor:</span> ${data.valor || "--"}</div>
      <div><span class="label">Timestamp:</span> ${data.timestamp || "--"}</div>
    `;
  } catch (err) {
    alert(err.message);
  }
}

// Puedes llamar a esta función desde un botón
function cargarUltima() {
  obtenerUltimaMedicion(11); 
}

// También cargar automáticamente al iniciar la página
window.onload = () => {
  cargarUltima();
};
