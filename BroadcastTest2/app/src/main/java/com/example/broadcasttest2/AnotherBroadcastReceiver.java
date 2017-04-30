package com.example.broadcasttest2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AnotherBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
//        Toast.makeText(context, "Another Broadcast Receiver", Toast.LENGTH_SHORT).show();
        Toast.makeText(context, "Another local broadcast", Toast.LENGTH_SHORT).show();
    }

}
