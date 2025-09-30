package com.example.marraes_alvaro.conexionbeacon;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Logica {

    private static final String TAG = "Logica";
    private String urlServidor;

    public Logica(String urlServidor) {
        this.urlServidor = "https://amarare.upv.edu.es/medicion";
    }

    /**
     * Envía la medición al servidor REST
     */
    public void guardarMedicion(int sensorId, int valor, String timestamp) {
        JSONObject json = new JSONObject();
        try {
            json.put("sensorId", sensorId);
            json.put("valor", valor);
            json.put("timestamp", timestamp);
        } catch (Exception e) {
            Log.e(TAG, "Error creando JSON", e);
            return;
        }

        // Llamada REST genérica
        hacerPeticionREST("POST", urlServidor + "/medicion", json.toString(), new RespuestaREST() {
            @Override
            public void callback(int codigo, String cuerpo) {
                if (codigo >= 200 && codigo < 300) {
                    Log.d(TAG, "Medición enviada correctamente");
                } else {
                    Log.e(TAG, "Error enviando medición. Código: " + codigo + ", cuerpo: " + cuerpo);
                }
            }
        });
    }

    /**
     * INTERFAZ para callbacks
     */
    public interface RespuestaREST {
        void callback(int codigo, String cuerpo);
    }

    /**
     * Método genérico para hacer peticiones REST en background
     */
    @SuppressLint("StaticFieldLeak")
    private void hacerPeticionREST(String metodo, String urlDestino, String cuerpo, RespuestaREST respuesta) {
        new AsyncTask<Void, Void, Boolean>() {

            int codigoRespuesta;
            String cuerpoRespuesta = "";

            @Override
            protected Boolean doInBackground(Void... params) {
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

                    try {
                        InputStream is = conn.getInputStream();
                        BufferedReader br = new BufferedReader(new InputStreamReader(is));
                        StringBuilder acumulador = new StringBuilder();
                        String linea;
                        while ((linea = br.readLine()) != null) {
                            acumulador.append(linea);
                        }
                        cuerpoRespuesta = acumulador.toString();
                    } catch (Exception ex) {
                        // Puede que no haya cuerpo en la respuesta
                        cuerpoRespuesta = "";
                    }

                    conn.disconnect();
                    return true;

                } catch (Exception e) {
                    Log.e(TAG, "Error en petición REST", e);
                    return false;
                }
            }

            @Override
            protected void onPostExecute(Boolean exito) {
                if (!exito) {
                    codigoRespuesta = -1;
                    cuerpoRespuesta = "";
                }
                respuesta.callback(codigoRespuesta, cuerpoRespuesta);
            }
        }.execute();
    }
}
