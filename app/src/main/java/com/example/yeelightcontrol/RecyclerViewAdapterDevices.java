package com.example.yeelightcontrol;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yeelightcontrol.model.Device;

import java.util.List;

public class RecyclerViewAdapterDevices extends RecyclerView.Adapter<RecyclerViewAdapterDevices.ViewHolder> {
    private static final String TAG = "RecyclerViewAdapterDevices";

    private List<Device> mDeviceList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView deviceName;
        ImageView deviceIcon;
        RelativeLayout parentLayout;

        public ViewHolder(View view) {
            super(view);
            deviceName = view.findViewById(R.id.device_name);
            deviceIcon = view.findViewById(R.id.device_icon);
            parentLayout = itemView.findViewById(R.id.mainLayout);
        }
    }

    public interface onDeviceSelectedListener {
        void onDeviceSelected(Device selectedDevice);
    }

    private onDeviceSelectedListener mListener;

    public RecyclerViewAdapterDevices(List<Device> deviceList, onDeviceSelectedListener mListener) {
        this.mDeviceList = deviceList;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_devices, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapterDevices.ViewHolder holder, final int position) {
        Log.d(TAG, "recycler wifi adapter called");
        holder.deviceName.setText(mDeviceList.get(position).getName());
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked on: " + mDeviceList.get(position).getName());
                if(mListener != null) {
                    System.out.println("Successfully select a device.");
                    mListener.onDeviceSelected(mDeviceList.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDeviceList.size();
    }
}