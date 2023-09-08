package com.example.client;

import androidx.appcompat.app.AppCompatActivity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import android.content.Intent;
import android.os.Bundle;
import com.example.server.ILanguage;

public class MainActivity extends AppCompatActivity {

    private ILanguage iLanguage;
    private EditText ipt_search;
    private Button btn_query;
    private TextView txt_show;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ServiceConnection connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder iBinder) {
                iLanguage = ILanguage.Stub.asInterface(iBinder);
                Log.d("TAG", "onServiceConnected: " + iLanguage);
            }

            public void onServiceDisconnected(ComponentName name) {
                //iLanguage = null;
            }
        };


        Intent intent = new Intent();
        intent.setAction("com.example.server.action");
        intent.setPackage("com.example.server");
        Boolean isBind = bindService(intent,connection,BIND_AUTO_CREATE);
        Log.d("TAG", "isBin: " + isBind);

        ipt_search = (EditText) findViewById(R.id.ipt_search);
        btn_query = (Button) findViewById(R.id.btn_query);
        txt_show = (TextView) findViewById(R.id.txt_show);

        btn_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = ipt_search.getText().toString();
                Log.d("TAG", "onClick: " + number);
                int num = Integer.valueOf(number);
                Log.d("TAG","+++++++++++++++++++++++++++++");
                Log.d("TAG","" + iLanguage);
                Log.d("TAG","+++++++++++++++++++++++++++++");
                if(iLanguage != null){
                    try {
                        txt_show.setText(iLanguage.queryLanguage(num));
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    ipt_search.setText("");
                }


            }
        });
    }
}