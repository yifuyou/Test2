package com.example.test2.services;

import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.test2.Fragment_1;
import com.example.test2.MyBroatcastReceiver;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class HttpService extends Service {
    private final String uri="https://raw.githubusercontent.com/yifuyou/Test2/master/srcs.xml";
    private final String fileName="srcs.xml";
    private MyHttpBinder binder;
    private Message message;
    @Override
    public void onCreate() {
        System.out.println("=========onCreate");
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
            System.out.println(uri);
            binder=new MyHttpBinder();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        message=new Message();
                        URL url=new URL(uri);
                        HttpURLConnection urlConnection=(HttpURLConnection)url.openConnection();
                        urlConnection.setConnectTimeout(5000);
                        urlConnection.setRequestMethod("GET");
                        //urlConnection.setRequestProperty("User-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows 2000)");

                        int code=urlConnection.getResponseCode();
                        if(code==200){
                            InputStream is=urlConnection.getInputStream();
                            Bundle bundle=new Bundle();
                            byte [] bytes=new byte[40];
                            ArrayList<String> list=new ArrayList<>();
                            while ((is.read(bytes))!=-1){
                                list.add(String.valueOf(bytes));

                            }

                            bundle.putStringArrayList("stringList",list);
                            message.what=0x996;
                            message.setData(bundle);
                            binder.setMessage(message);
                        }else {
                            System.out.println("================url connect fail");
                        }

                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }

            ).start();


        return binder;
    }
    public class MyHttpBinder extends Binder{
        private Message message;
        public Message getMessage() {
            return message;
        }

        public void setMessage(Message message) {
            this.message = message;
        }

    }



}
