package com.example.yeelightcontrol;

import android.content.Context;
import android.support.v4.os.IResultReceiver;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.yeelightcontrol.model.Device;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NetworkInformationManager {
    private static final String TAG = "NetworkInformationManager";

    private static NetworkInformationManager sInstance;
    private static Context mContext;
    private static RequestQueue requestQueue;
    private static final String serverUrl = "http://192.168.50.198:5002";
    private static final String discoverPath = "discover";
    private static final String turnonPath = "turnon";
    private static final String turnoffPath = "turnoff";


    public interface OnDiscoverListener {
        void onSuccess(ArrayList<Device> result);

        void onNetworkFail();

        void onFail(Exception error);
    }

    public interface OnOffListener {
        void onSuccess();

        void onNetworkFail();

        void onFail(Exception error);
    }

    private NetworkInformationManager(Context context) {
        mContext = context;
        requestQueue = Volley.newRequestQueue(mContext);
    }

    public static NetworkInformationManager getInstance(Context c) {
        if (null == sInstance)
            sInstance = new NetworkInformationManager(c);
        return sInstance;
    }

    public void discoverDevices(final OnDiscoverListener l) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, serverUrl + "/" + discoverPath,
                response -> {
                    ArrayList<Device> result = new ArrayList<>();
                    try {
                        JSONArray jsonArr = new JSONArray(response);
                        for (int i = 0; i < jsonArr.length(); i++) {
                            JSONObject jsonObj = jsonArr.getJSONObject(i);

                            Log.d(TAG, jsonObj.toString());
                            result.add(new Device(jsonObj));
                        }
                    } catch (JSONException error) {
                        l.onFail(error);
                        Log.e(TAG, error.getMessage(), error);
                    }
                    l.onSuccess(result);
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NetworkError) {
                    Log.e(TAG, error.getMessage(), error);
                    l.onNetworkFail();
                } else {
                    l.onFail(error);
                    Log.e(TAG, error.getMessage(), error);
                }
            }
        });
        requestQueue.add(stringRequest);
    }

    public void turnon(final String ip, final OnOffListener l) {
        /*
        {
          "ip": "192.168.50.198"
        }
         */
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("ip", ip);
        } catch (JSONException error) {
            l.onFail(error);
            return;
        }
        Log.d(TAG, jsonObject.toString());

        JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(Request.Method.POST, serverUrl + "/" + turnonPath, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "turn on response" + response.toString());
                        l.onSuccess();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NetworkError) {
                    l.onNetworkFail();
                } else {
                    l.onFail(error);
                    Log.e(TAG, error.getMessage(), error);
                }
            }
        }) {

            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                headers.put("Content-Type", "application/json; charset=UTF-8");
                return headers;
            }
        };
        requestQueue.add(jsonRequest);
    }

    public void turnoff(final String ip, final OnOffListener l) {
        /*
        {
          "ip": "192.168.50.198"
        }
         */
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("ip", ip);
        } catch (JSONException error) {
            l.onFail(error);
            return;
        }
        Log.d(TAG, jsonObject.toString());

        JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(Request.Method.POST, serverUrl + "/" + turnoffPath, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "turn on response" + response.toString());
                        l.onSuccess();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NetworkError) {
                    l.onNetworkFail();
                } else {
                    l.onFail(error);
                    Log.e(TAG, error.getMessage(), error);
                }
            }
        }) {

            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                headers.put("Content-Type", "application/json; charset=UTF-8");
                return headers;
            }
        };
        requestQueue.add(jsonRequest);
    }
}