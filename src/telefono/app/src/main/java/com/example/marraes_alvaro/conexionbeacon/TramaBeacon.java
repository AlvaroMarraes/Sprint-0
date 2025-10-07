package com.example.marraes_alvaro.conexionbeacon;

import java.util.Arrays;

// -----------------------------------------------------------------------------------
// @author: Jordi Bataller i Mascarell
// -----------------------------------------------------------------------------------

public class TramaBeacon {

    // --------------------------- CAMPOS PRIVADOS ---------------------------
    private byte[] prefijo = null;
    private byte[] uuid = null;
    private byte[] major = null;
    private byte[] minor = null;
    private byte txPower = 0;
    private byte[] losBytes;

    private byte[] advFlags = null;
    private byte[] advHeader = null;
    private byte[] companyID = new byte[2];
    private byte iBeaconType = 0;
    private byte iBeaconLength = 0;

    // -------------------------------------------------------------------------------
    // Constructor de la clase TramaBeacon.
    // Recibe un array de bytes con la trama BLE y extrae los campos relevantes.
    // -------------------------------------------------------------------------------
    public TramaBeacon(byte[] bytes) {
        this.losBytes = bytes;

        // Extracción de campos según su posición en la trama
        prefijo = Arrays.copyOfRange(losBytes, 0, 8 + 1);
        uuid = Arrays.copyOfRange(losBytes, 9, 24 + 1);
        major = Arrays.copyOfRange(losBytes, 25, 26 + 1);
        minor = Arrays.copyOfRange(losBytes, 27, 28 + 1);
        txPower = losBytes[29];

        // Campos dentro del prefijo
        advFlags = Arrays.copyOfRange(prefijo, 0, 2 + 1);
        advHeader = Arrays.copyOfRange(prefijo, 3, 4 + 1);
        companyID = Arrays.copyOfRange(prefijo, 5, 6 + 1);
        iBeaconType = prefijo[7];
        iBeaconLength = prefijo[8];
    }

    // -------------------------------------------------------------------------------
    // Devuelve los 9 bytes del prefijo del beacon.
    // -------------------------------------------------------------------------------
    public byte[] getPrefijo() {
        return prefijo;
    }

    // -------------------------------------------------------------------------------
    // Devuelve el UUID del beacon (16 bytes).
    // -------------------------------------------------------------------------------
    public byte[] getUUID() {
        return uuid;
    }

    // -------------------------------------------------------------------------------
    // Devuelve el identificador "Major" (2 bytes).
    // -------------------------------------------------------------------------------
    public byte[] getMajor() {
        return major;
    }

    // -------------------------------------------------------------------------------
    // Devuelve el identificador "Minor" (2 bytes).
    // -------------------------------------------------------------------------------
    public byte[] getMinor() {
        return minor;
    }

    // -------------------------------------------------------------------------------
    // Devuelve la potencia de transmisión (Tx Power).
    // -------------------------------------------------------------------------------
    public byte getTxPower() {
        return txPower;
    }

    // -------------------------------------------------------------------------------
    // Devuelve la trama completa de bytes recibida.
    // -------------------------------------------------------------------------------
    public byte[] getLosBytes() {
        return losBytes;
    }

    // -------------------------------------------------------------------------------
    // Devuelve los flags de anuncio BLE (3 bytes).
    // -------------------------------------------------------------------------------
    public byte[] getAdvFlags() {
        return advFlags;
    }

    // -------------------------------------------------------------------------------
    // Devuelve la cabecera del anuncio BLE (2 bytes).
    // -------------------------------------------------------------------------------
    public byte[] getAdvHeader() {
        return advHeader;
    }

    // -------------------------------------------------------------------------------
    // Devuelve el identificador de la compañía (2 bytes).
    // -------------------------------------------------------------------------------
    public byte[] getCompanyID() {
        return companyID;
    }

    // -------------------------------------------------------------------------------
    // Devuelve el tipo de iBeacon (1 byte).
    // -------------------------------------------------------------------------------
    public byte getiBeaconType() {
        return iBeaconType;
    }

    // -------------------------------------------------------------------------------
    // Devuelve la longitud del iBeacon (1 byte).
    // -------------------------------------------------------------------------------
    public byte getiBeaconLength() {
        return iBeaconLength;
    }
}
