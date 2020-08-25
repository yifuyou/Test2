package com.example.test2;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class WRThread extends Thread {
    private boolean io;

    private String parentpath;
    private String fileName;
    private File file;
    private Handler handler;
    private Context context;

    private StringBuffer stringBuffer;

    public WRThread(Handler handler) {
        this.io = false;
        init(handler);
    }

    public WRThread(String string,Handler handler) {
        this.io = true;
        init(handler);
        stringBuffer.append(string);
    }
    protected void init(Handler handler){
        parentpath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download";
        fileName = "Test2_test1.txt";
        this.handler = handler;
        stringBuffer = new StringBuffer();
    }

    @Override
    public void run() {
        file = new File(parentpath, fileName);
        Message message = new Message();
        Bundle bundle = new Bundle();
        if (!io) {
            if (readString()) {
                message.what = 0x998;
                bundle.putString("string",stringBuffer.toString());
            }
        } else {
            if (writeString(stringBuffer.toString())) {
                message.what = 0x997;
                bundle.putBoolean("key",true);

            }
        }
        message.setData(bundle);
        handler.sendMessage(message);
    }

    protected boolean writeString(String string) {
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();

            }
        }
        if (!file.exists()) System.out.println("file do not exists! ");
        try {
            FileOutputStream fop = new FileOutputStream(file);

            byte[] bytes = string.getBytes();
            fop.write(bytes);
            fop.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    protected boolean readString() {
        if (file.exists()) {
            try {

                InputStream fis = new FileInputStream(file);
                InputStreamReader reader = new InputStreamReader(fis);
                char[] chars = new char[30];
                if (reader.ready()) {
                    reader.read(chars);
                }

                stringBuffer.append(chars);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return false;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }


}
