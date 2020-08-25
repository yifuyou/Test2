package com.example.test2.services;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.test2.MyBroatcastReceiver;

public class LoginService extends Service {

    public MyBinder myBinder;
    private Thread thread;
    private LocalBroadcastManager lbManager;
    private Context context;
    private MyBroatcastReceiver receiver;

    @Override
    public void onCreate() {
        System.out.println("onCreate()!");
        thread=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    Handler handler=new Handler();
                    Message message=new Message();
                    message.what=0x1235;
                    Bundle bundle=new Bundle();
                    bundle.putCharSequence("bundle","hello nmb");
                    message.setData(bundle);

                    handler.sendMessage(message);
                    Looper.loop();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        super.onCreate();
    }
    @Override
    public void onDestroy() {
        System.out.println("onDestory()!");
        lbManager.unregisterReceiver(receiver);
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        System.out.println("onBind()");
        String id= null;
        String psw=null;
        if (intent != null) {
            id = intent.getStringExtra("id");
            psw=intent.getStringExtra("psw");
        }

        System.out.println(id+"    "+psw);

        myBinder= new MyBinder();
        myBinder.setB(false);
        if(id!=null&&psw!=null)
            if(id.equals("123")&&psw.equals("12345")){
                myBinder.setB(true);
                broatcastReceiver();
            }else{
                thread.start();
            }
        return myBinder;
    }
    private void broatcastReceiver(){
        receiver=new MyBroatcastReceiver();
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction("android.intent.broadcast.no1");
        lbManager=LocalBroadcastManager.getInstance(context);
        lbManager.registerReceiver(receiver,intentFilter);
        Intent intent=new Intent();
        intent.setAction("android.intent.broadcast.no1");
        lbManager.sendBroadcast(intent);

    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }
    public class MyBinder extends Binder{
        protected boolean b;
        public LoginService getService(){
            return LoginService.this;
        }

        public boolean getB(){
            return b;
        }
        public void setB(boolean b1){
            b=b1;
        }
    }

}
