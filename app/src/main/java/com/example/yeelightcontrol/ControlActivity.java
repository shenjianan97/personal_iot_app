package com.example.yeelightcontrol;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.yeelightcontrol.model.Device;

import org.json.JSONException;
import org.json.JSONObject;

public class ControlActivity extends AppCompatActivity {
    private static final String TAG = "ControlActivity";
    private NetworkInformationManager networkInformationManager;
    private Device device;

    private Button turnonButton;
    private Button turnoffButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);
        turnonButton = findViewById(R.id.turnon);
        turnonButton.setOnClickListener(new TurnOnButtonClick());
        turnoffButton = findViewById(R.id.turnoff);
        turnoffButton.setOnClickListener(new TurnOffButtonClick());

        networkInformationManager = NetworkInformationManager.getInstance(getApplicationContext());


        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String deviceString = intent.getStringExtra("KEY");
        try {
            Device device = new Device(new JSONObject(deviceString));
            Log.d(TAG, device.getName());
            this.device = device;
        } catch (JSONException error) {
            Log.e(TAG, error.getMessage(), error);
        }
    }

    class TurnOnButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            networkInformationManager.turnon(device.getIp(), new NetworkInformationManager.OnOffListener() {
                @Override
                public void onSuccess() {
                    Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNetworkFail() {

                }

                @Override
                public void onFail(Exception error) {
                    Log.e(TAG, error.getMessage());
                }
            });
        }
    }

    class TurnOffButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            networkInformationManager.turnoff(device.getIp(), new NetworkInformationManager.OnOffListener() {
                @Override
                public void onSuccess() {
                    Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNetworkFail() {

                }

                @Override
                public void onFail(Exception error) {
                    Log.e(TAG, error.getMessage());
                }
            });
        }
    }
}