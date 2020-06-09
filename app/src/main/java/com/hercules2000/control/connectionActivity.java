package com.hercules2000.control;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hercules2000.controlApp.R;
import com.r0adkll.slidr.Slidr;

public class connectionActivity extends AppCompatActivity {

    public EditText adrIP,port;
    Button conBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);
        initView();
    }

    public void initView(){
        adrIP  = findViewById(R.id.InputIP);
        port = findViewById(R.id.InputPort);
        conBtn = findViewById(R.id.connectBtn);
        connectionStat();
        Slidr.attach(this);
    }
    public void showDialog(String title ,String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setCancelable(true);

        AlertDialog alert = builder.create();
        alert.setTitle(title);
        alert.show();
    }

    public void connectionStat() {

        if(connectionUtils.ismRun()){
            __ifConnected();
        }else{
            __ifDisconnected();
        }

    }
    public void connectBtn(View V) {

        connectionUtils.run(adrIP.getText().toString(),Integer.parseInt(port.getText().toString()));
        if (!connectionUtils.ismRun()){
            showDialog("Erreur de connection !","Veuiller verfier l'ip et port");
        }
        else {
            showDialog("Connecté !", "Connection avec " + adrIP.getText().toString() + " avec succees");
            __ifConnected();
        }
    }

    public void __ifConnected(){
            if (connectionUtils.ismRun())
            {
                conBtn.setText("Se deconnecter");
                conBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        disconnectBtn();
                    }
                });
                adrIP.setText(connectionUtils.getServerIp());
                port.setText(Integer.toString(connectionUtils.getServerPort()));
            }
    }
    public void __ifDisconnected()
    {
        if (!connectionUtils.ismRun())
        {
            conBtn.setText("Se connecter");
            conBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    connectBtn(v);
                }
            });
        }
    }
    public void disconnectBtn(){

        connectionUtils.stopClient();
        __ifDisconnected();
    }



}