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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity_a";
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothProfile myproxy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.wjq.testapp.R.layout.activity_main);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mBluetoothAdapter.getProfileProxy(this, new BluetoothProfile.ServiceListener() {
            public void onServiceDisconnected(int var1) {
                Log.d(TAG, "onServiceDisconnected==  " + var1);
            }

            public void onServiceConnected(int var1, BluetoothProfile var2) {
                Log.d(TAG, "ss  onServiceConnected  = " + var1);
                myproxy = var2;
                //RemoteBLE.this.mHandler.postDelayed(RemoteBLE.this.connectRunnable, 0L);
            }
        }, 4);


        Button test = (Button) findViewById(R.id.test);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                @SuppressLint("MissingPermission")
                List devices = myproxy.getConnectedDevices();
                @SuppressLint("MissingPermission")
                Set<BluetoothDevice> set = mBluetoothAdapter.getBondedDevices();
                Iterator iterator = devices.iterator();

                while(iterator.hasNext()) {
                    BluetoothDevice var3 = (BluetoothDevice)iterator.next();
                    @SuppressLint("MissingPermission")
                    String var4 = var3.getName();

                    Log.d(TAG, "ss  connected name = " + var4.trim());
                    Log.d(TAG, "ss  connected address = " + var3.getAddress());
                }
            }
        });

    }
}
