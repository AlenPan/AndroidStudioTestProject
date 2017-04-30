package com.example.broadcasttest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

//    NetworkChangeReceiver receiver;
    private LocalReceiver mLocalReceiver;
    private LocalBroadcastManager mLocalBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
//        receiver = new NetworkChangeReceiver();
//        registerReceiver(receiver, intentFilter);

        mLocalBroadcastReceiver = LocalBroadcastManager.getInstance(this);
        mLocalReceiver = new LocalReceiver();
        IntentFilter intentFilter = new IntentFilter("com.example.broadcasttest.LOCAL_BROADCAST");
        mLocalBroadcastReceiver.registerReceiver(mLocalReceiver, intentFilter);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent("com.example.broadcasttest.MyReceiver");
//                sendOrderedBroadcast(intent, null);

                Intent intent = new Intent("com.example.broadcasttest.LOCAL_BROADCAST");
                mLocalBroadcastReceiver.sendBroadcast(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        unregisterReceiver(receiver);
        mLocalBroadcastReceiver.unregisterReceiver(mLocalReceiver);
    }
}

class NetworkChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
//        Toast.makeText(context, "network changed", Toast.LENGTH_SHORT).show();
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService
                (Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isAvailable()) {
            Toast.makeText(context, "network is available", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "network is not available", Toast.LENGTH_SHORT).show();
        }
    }
}

class LocalReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Received local broadcast", Toast.LENGTH_SHORT).show();
    }
}