package com.hercules2000.control;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hercules2000.controlApp.R;
import com.r0adkll.slidr.Slidr;

public class Connection extends AppCompatActivity {

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

    public void connectBtn(View V) {

        ConnectionHandler.run(adrIP.getText().toString(),Integer.parseInt(port.getText().toString()));
        if (!ConnectionHandler.ismRun()){
            showDialog("Erreur de connection !","Veuiller verfier l'ip et port");
        }
        else {
            showDialog("Connecté !", "Connection avec " + adrIP.getText().toString() + " avec succees");
            __ifConnected();
            getArmStat();
        }
    }

    public void __ifConnected(){
            if (ConnectionHandler.ismRun())
            {
                conBtn.setText("Disconnect");
                conBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        disconnectBtn();
                    }
                });
            }
    }
    public void __ifDisconnected()
    {
        if (!ConnectionHandler.ismRun())
        {
            conBtn.setText("Connect");
            conBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    connectBtn(v);
                }
            });
        }
    }
    public void disconnectBtn(){

        ConnectionHandler.stopClient();
        __ifDisconnected();
    }

    public void getArmStat(){
        ConnectionHandler.sendMessage("$");
        ConnectionHandler.readMessage();
    }

}