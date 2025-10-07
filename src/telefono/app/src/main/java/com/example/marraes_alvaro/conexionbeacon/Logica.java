package com.example.marraes_alvaro.conexionbeacon;

import android.util.Log;

import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class Logica {

    private static final String TAG = ">>>>";
    private final String urlServidor;

    public Logica(String urlServidor) {
        this.urlServidor = urlServidor;
    }

    // -------------------------------------------------------------
    // Envía una medición al servidor mediante POST /medicion
    // Recibe: id del sensor, valor medido y timestamp
    // -------------------------------------------------------------
    public void guardarMedicion(int sensorId, int valor, String timestamp) {
        try {
            JSONObject json = new JSONObject();
            json.put("sensorId", sensorId);
            json.put("valor", valor);
            json.put("timestamp", timestamp);

            hacerPeticionREST("POST", urlServidor + "/medicion", json.toString());
        } catch (Exception e) {
            Log.e(TAG, "Error creando JSON", e);
        }
    }

    // -------------------------------------------------------------
    // Obtiene la última medición de un sensor (GET /medicion/:id)
    // Recibe el id del sensor 11,12...
    // -------------------------------------------------------------
    public void obtenerMedicion(int sensorId) {
        hacerPeticionREST("GET", urlServidor + "/medicion/" + sensorId, null);
    }

    // -------------------------------------------------------------
    // Método genérico para realizar peticiones REST (GET o POST)
    // Se ejecuta en un hilo separado para no bloquear la interfaz
    // -------------------------------------------------------------
    private void hacerPeticionREST(String metodo, String urlDestino, String cuerpo) {
        new Thread(() -> {
            int codigoRespuesta = -1;
            try {
                URL url = new URL(urlDestino);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod(metodo);
                conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
                conn.setDoInput(true);

                if (!metodo.equals("GET") && cuerpo != null) {
                    conn.setDoOutput(true);
                    DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
                    dos.writeBytes(cuerpo);
                    dos.flush();
                    dos.close();
                }

                codigoRespuesta = conn.getResponseCode();

                InputStream is = (codigoRespuesta >= 200 && codigoRespuesta < 300)
                        ? conn.getInputStream()
                        : conn.getErrorStream();

                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                StringBuilder sb = new StringBuilder();
                String linea;
                while ((linea = br.readLine()) != null) {
                    sb.append(linea);
                }
                br.close();
                conn.disconnect();

                Log.d(TAG, "Respuesta (" + codigoRespuesta + "): " + sb);

            } catch (Exception e) {
                Log.e(TAG, "Error en petición REST", e);
            }
        }).start(); // <--- se ejecuta en un hilo aparte
    }
}
