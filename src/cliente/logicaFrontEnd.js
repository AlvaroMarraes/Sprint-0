// Función para formatear el timestamp
function formatearTimestamp(iso) {
  if (!iso) return "--";
  const d = new Date(iso);
  const dia = String(d.getDate()).padStart(2, "0");
  const mes = String(d.getMonth() + 1).padStart(2, "0");
  const año = d.getFullYear();
  const horas = String(d.getHours()).padStart(2, "0");
  const minutos = String(d.getMinutes()).padStart(2, "0");
  const segundos = String(d.getSeconds()).padStart(2, "0");
  return `${dia}/${mes}/${año} ${horas}:${minutos}:${segundos}`;
}

async function obtenerUltimaMedicion() {
  try {
    const res = await fetch(`/api/mediciones/ultima`);
    if (!res.ok) throw new Error("Error cargando medición");

    const data = await res.json();

    document.getElementById("salida").innerHTML = `
      <div><span class="label">Sensor ID:</span> ${data.sensorId || "--"}</div>
      <div><span class="label">Valor:</span> ${data.valor || "--"}</div>
      <div><span class="label">Timestamp:</span> ${formatearTimestamp(data.timestamp)}</div>
    `;
  } catch (err) {
    alert(err.message);
  }
}

// Botón
function cargarUltima() {
  obtenerUltimaMedicion();
}

// Cargar automáticamente al iniciar
window.onload = () => {
  cargarUltima();
};

