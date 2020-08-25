package com.example.test2;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.LocalActivityManager;
import android.app.Notification;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;

import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.test2.services.LoginService;

public class Login_Activity extends AppCompatActivity {

    private Button btn_login;
    private Button btn_cancel;
    private EditText et_id;
    private EditText et_psw;
    private Context context = this;
    private boolean bl = false;
    private ServiceConnect sc;

    private LocalBroadcastManager lbManager;
    private MyBroatcastReceiver receiver;
    private Intent lbIntent;

    @SuppressLint("HandlerLeak")
    final Handler mh=new Handler()
    {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if(msg.what==0x1235){
                et_id.setText("null find!");
                et_psw.setText("null find!");
            }
        }
    };



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if ((ContextCompat.checkSelfPermission(this, Manifest.permission_group.STORAGE)) != PackageManager.PERMISSION_GRANTED) {
                System.out.println("request permissions!");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.MANAGE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.INTERNET
                }, 1);
            }
        }

        setContentView(R.layout.login_page);



        btn_login = findViewById(R.id.btn_login);
        btn_cancel = findViewById(R.id.btn_login_cancel);
        et_id = findViewById(R.id.edit_id_Text);
        et_psw = findViewById(R.id.edit_psw_Text);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startService();
            }
        });

        btn_cancel.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(sc!=null) {
                            unbindService(sc);
                            et_id.setText("");
                            et_psw.setText("");

                        }
                    }
                });

        receiver=new MyBroatcastReceiver();
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction("android.intent.broadcast.no1");
        lbManager= LocalBroadcastManager.getInstance(context);
        lbManager.registerReceiver(receiver,intentFilter);
        lbIntent=new Intent("android.intent.broadcast.no1");
    }
    protected void startService(){
        System.out.println(et_id.getText().toString() + et_psw.getText().toString());
        sc=new ServiceConnect();
        Intent intent = new Intent(context, LoginService.class);
        intent.putExtra("id", et_id.getText().toString());
        intent.putExtra("psw", et_psw.getText().toString());
        bindService(intent,sc,BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        if(sc!=null){
            unbindService(sc);
        }
        super.onDestroy();
    }

    public class ServiceConnect implements ServiceConnection {
    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        LoginService.MyBinder binder = (LoginService.MyBinder) iBinder;
        bl = binder.getB();
        if (bl){
            lbManager.sendBroadcast(lbIntent);
        }else{
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);

                        Message message=new Message();
                        message.what=0x1235;
                        Bundle bundle=new Bundle();
                        bundle.putCharSequence("bundle","hello nmb");
                        message.setData(bundle);

                        mh.sendMessage(message);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {
        System.out.println("onServiceDisconnected");
    }
}




}

/*


 */