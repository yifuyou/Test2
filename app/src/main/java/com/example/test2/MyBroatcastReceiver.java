package com.example.test2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyBroatcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Intent i=new Intent(context,Main_Activity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);

    }
}
