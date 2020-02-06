package com.hercules2000.controlApp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.hercules2000.control.connectionActivity;

public class HomeMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_menu);
    }

    public void openControl(View v) {
        Intent intent = new Intent(this, controlApp.class);
        startActivity(intent);
    }
    public void btnSettings(View v) {
        Intent intent = new Intent(this, connectionActivity.class);
        startActivity(intent);
    }

    public void showDialog(String title ,String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setCancelable(true);

        AlertDialog alert = builder.create();
        alert.setTitle(title);
        alert.show();
    }
}
