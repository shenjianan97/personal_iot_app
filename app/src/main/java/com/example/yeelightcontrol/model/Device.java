package com.example.yeelightcontrol.model;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class Device {
    private static final String TAG = "Device";
    private final JSONObject description;
    private JSONObject capabilities;

    public Device(JSONObject description){
        this.description = description;
        try {
            this.capabilities = this.description.getJSONObject("capabilities");
        } catch (JSONException error) {
            Log.e(TAG, error.getMessage(), error);
        }
    }

    public String getIp() {
        String result = "";
        try{
            result = this.description.getString("ip");
        } catch (JSONException error) {
            Log.e(TAG, error.getMessage(), error);
        }
        return result;
    }

    public int getPort() {
        int port = -1;

        try{
            port = this.description.getInt("port");
        } catch (JSONException error) {
            Log.e(TAG, error.getMessage(), error);
        }
        return port;
    }

    public String getName() {
        String name = "";
        try{
            name = capabilities.getString("name");
        } catch (JSONException error) {
            Log.d(TAG, error.getMessage(), error);
        }
        return name;
    }

    public int getColorTemp() {
        int colorTemp = -1;
        try{
            colorTemp = capabilities.getInt("ct");
        } catch (JSONException error) {
            Log.d(TAG, error.getMessage(), error);
        }
        return colorTemp;
    }

    public int getBrightness() {
        int brightness = -1;
        try{
            brightness = capabilities.getInt("bright");
        } catch (JSONException error) {
            Log.d(TAG, error.getMessage(), error);
        }
        return brightness;
    }

    public boolean getPowerState() {
        boolean powerState = false;
        String power = "";
        try{
            power = capabilities.getString("power");
        } catch (JSONException error) {
            Log.d(TAG, error.getMessage(), error);
        }
        if (power.equals("off")) {
            powerState = false;
        } else {
            powerState = true;
        }
        return powerState;
    }
}
