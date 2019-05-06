package com.wjq.testapp;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothProfile;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

public class SecondActivity extends AppCompatActivity {

    private static final String TAG = "SecondActivity";
    private BluetoothProfile mBluetoothProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Button btn = (Button) findViewById(R.id.test2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getConnectBt();
            }
        });
    }


    //检查已连接的蓝牙设备
    private void getConnectBt() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        @SuppressLint("MissingPermission")
        int a2dp = bluetoothAdapter.getProfileConnectionState(BluetoothProfile.A2DP);

        @SuppressLint("MissingPermission")
        int headset = bluetoothAdapter.getProfileConnectionState(BluetoothProfile.HEADSET);

        @SuppressLint("MissingPermission")
        int health = bluetoothAdapter.getProfileConnectionState(BluetoothProfile.HEALTH);
        int flag = -1;
        if (a2dp == BluetoothProfile.STATE_CONNECTED) {
            flag = a2dp;
        } else if (headset == BluetoothProfile.STATE_CONNECTED) {
            flag = headset;
        } else if (health == BluetoothProfile.STATE_CONNECTED) {
            flag = health;
        }
        if (flag != -1) {
            bluetoothAdapter.getProfileProxy(SecondActivity.this, new BluetoothProfile.ServiceListener() {
                @Override
                public void onServiceDisconnected(int profile) {
                    Toast.makeText(SecondActivity.this,profile+"",Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onServiceConnected(int profile, BluetoothProfile proxy) {

                    @SuppressLint("MissingPermission")
                    List<BluetoothDevice> mDevices = proxy.getConnectedDevices();
                    if (mDevices != null && mDevices.size() > 0) {
                        for (BluetoothDevice device : mDevices) {
                            @SuppressLint("MissingPermission")
                              String name = device.getName();
                                    Log.d(TAG, "device.name: "+name);
                            //Toast.makeText(SecondActivity.this, device.getName() + "," + device.getAddress(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(SecondActivity.this, "mDevices is null", Toast.LENGTH_SHORT).show();
                    }
                }
            }, 4);
        }
    }
}
