package com.example.test2;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.FileOutputStream;

public class Main_Activity extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if((ContextCompat.checkSelfPermission(Main_Activity.this,Manifest.permission_group.STORAGE))!= PackageManager.PERMISSION_GRANTED){
                System.out.println("request permissions!");
                ActivityCompat.requestPermissions(Main_Activity.this,new String[]{Manifest.permission.MANAGE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.INTERNET
                },1);
            }



            
        }




    }




}
