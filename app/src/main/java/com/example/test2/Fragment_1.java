package com.example.test2;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.test2.services.HttpService;
import com.example.test2.services.LoginService;
import com.example.test2.services.MyPullXML;
import com.example.test2.services.Person;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Objects;


public class Fragment_1 extends Fragment {

    private View root;
    private Button button;
    private Button button_2;
    private TextView textView;
    private Context context;
    private MyPullXML pullXML;


    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater,
                             @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_1, container, false);
        textView = root.findViewById(R.id.text_fragment);
        button = root.findViewById(R.id.write_button);
        button_2 = root.findViewById(R.id.read_button);
        context = inflater.getContext();
        pullXML = new MyPullXML(context);
        final HttpServiceConnect hsconn = new HttpServiceConnect();

        button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                //WRThread thread=new WRThread("String could be writed !",hdr);
                //thread.start();

                //new Login_Task().execute();
                System.out.println("==========");
                Intent intent = new Intent(getActivity(), HttpService.class);
                getActivity().bindService(intent, hsconn, Context.BIND_AUTO_CREATE);
                System.out.println("-------------");
                /*
                try {
                    pullXML.setData();
                    Toast.makeText(context,"save success!",Toast.LENGTH_SHORT).show();


                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                 */
            }
        });
        button_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //WRThread thread=new WRThread(hdr);
                //thread.start();


                /*
                try {
                    InputStream is=context.openFileInput("a.xml");
                    ArrayList<Person> list=new ArrayList<>();
                    list=pullXML.getPersons(is);
                    textView.setText(list.toString());

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                 */


            }
        });


        return root;
    }

    class Login_Task extends AsyncTask<Void, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(Void... voids) {

            int progress = 0;
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }

                if (progress++ > 100) {
                    break;
                }
                publishProgress(progress);
            }
            return true;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {

            Message message = new Message();
            message.what = 0x999;
            Bundle bundle = new Bundle();
            bundle.putInt("key", values[0]);
            message.setData(bundle);

            hdr.sendMessage(message);


        }

        @Override
        protected void onPreExecute() {
            button.setBackgroundColor(Color.RED);
            //super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            button.setBackgroundColor(Color.BLUE);
        }
    }

    class HttpServiceConnect implements ServiceConnection{
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            HttpService.MyHttpBinder binder=(HttpService.MyHttpBinder)iBinder;
            if(binder.getMessage()!=null){
                hdr.sendMessage(binder.getMessage());
            }
            System.out.println("connect--------------");
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            System.out.println("do not connect");
        }
    }
    @SuppressLint("HandlerLeak")
    final Handler hdr = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 0x999:
                    Toast.makeText(context, "进度" + msg.getData().toString(), Toast.LENGTH_SHORT).show();
                    break;
                case 0x998://read

                    textView.setText(msg.getData().getString("string"));

                    break;
                case 0x997://write
                    Toast.makeText(context, String.valueOf(msg.getData().getBoolean("key")), Toast.LENGTH_SHORT).show();
                case 0x996:
                    textView.append(msg.getData().getStringArrayList("stringList").toString());
            }
        }
    };


}

