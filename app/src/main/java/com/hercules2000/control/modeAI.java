package com.hercules2000.control;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import com.hercules2000.controlApp.R;

public class modeAI extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode_a_i);
    }
    protected void doInBackground(){

    }
    public void startAI(View v){

        connectionUtils.sendMessage("setmode 2");
        connectionUtils.run(connectionUtils.getServerIp(), 9999);
        connectionUtils.OnMessageReceived()

    }
}