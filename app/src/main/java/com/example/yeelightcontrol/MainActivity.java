package com.example.yeelightcontrol;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yeelightcontrol.model.Device;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerViewAdapterDevices.onDeviceSelectedListener {
    public static ArrayList<Device> discoveredDevices = new ArrayList<>();
    private static final String TAG = "NetworkInformationManager";
    private NetworkInformationManager networkInformationManager;
    private Button discover;
    private RecyclerView device_recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        networkInformationManager = NetworkInformationManager.getInstance(getApplicationContext());

        discover = findViewById(R.id.discoverButton);
        device_recyclerView = findViewById(R.id.device_recyclerview);

        //hider recyclerview first
        device_recyclerView.setVisibility(View.INVISIBLE);
        discover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "discover button clicked!");
                networkInformationManager.discoverDevices(new NetworkInformationManager.OnDiscoverListener(
                ) {
                    @Override
                    public void onSuccess(ArrayList<Device> result) {
                        for(int i=0; i<result.size(); i++){
                            Device device = result.get(i);
                            Log.d(TAG, "ip: " + device.getIp() + " port: " + device.getPort() + " brightness: " + device.getBrightness() + " power: " + device.getPowerState() + " color temp: " + device.getColorTemp() + " name: " + device.getName());
                        }
                        discoveredDevices = result;
                        if (result.size() == 0) {
                            Toast.makeText(getApplicationContext(), "No device found!", Toast.LENGTH_LONG).show();
                        } else {
                            initRecyclerView(result);
                            device_recyclerView.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onNetworkFail() {
                        Toast.makeText(getApplicationContext(), "network error!", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFail(Exception error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    // Recycler View
    private void initRecyclerView(List<Device> deviceList) {
        RecyclerView recyclerView = findViewById(R.id.device_recyclerview);
        RecyclerViewAdapterDevices adapter = new RecyclerViewAdapterDevices(deviceList, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onDeviceSelected(Device selectedDevice) {
        Log.d(TAG, selectedDevice.getName());
    }
}