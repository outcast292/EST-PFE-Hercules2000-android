package com.hercules2000.controlApp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.hercules2000.control.connectionUtils;
import com.hercules2000.control.connectionActivity;

public class HomeMenu extends AppCompatActivity {

    Button btnAI;
    static private int mode = 0;
    private View.OnTouchListener listener = new View.OnTouchListener() {

        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN: {
                    v.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                    v.invalidate();
                    break;
                }
                case MotionEvent.ACTION_UP: {
                    v.getBackground().clearColorFilter();
                    v.invalidate();
                    break;
                }
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_menu);
        ((Button) findViewById(R.id.btnAI)).setOnTouchListener(listener);
        ((Button) findViewById(R.id.btnSettings)).setOnTouchListener(listener);
        ((Button) findViewById(R.id.openControl)).setOnTouchListener(listener);
    }

    public void openControl(View v) {
        if (connectionUtils.ismRun()) {
            Intent intent = new Intent(this, controlApp.class);
            startActivity(intent);

            connectionUtils.sendMessage("setmode 1");
            connectionUtils.readMessage();

            controllerHandler.initMotors();

        } else {
            showDialog("Erreur", "Veuillez vous connectez!");
        }
    }

    public void btnSettings(View v) {
        Intent intent = new Intent(this, connectionActivity.class);
        startActivity(intent);
    }

    public void btnAI(View v) {
        if (connectionUtils.ismRun()) {
            switch (mode) {
                case 2:
                    connectionUtils.sendMessage("setmode 1");
                    if (connectionUtils.readMessage().toString().charAt(0) == '0') {
                        v.setBackgroundColor(Color.LTGRAY);
                        mode = 1;
                    }
                    break;
                default:
                    connectionUtils.sendMessage("setmode 2");
                    if (connectionUtils.readMessage().toString().charAt(0) == '0') {
                        v.setBackgroundColor(Color.rgb(137, 224, 123));
                        mode = 2;
                    }
                    break;
            }

        } else {
            showDialog("Erreur", "Veuillez vous connectez!");
        }

    }

    public void showDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setCancelable(true);

        AlertDialog alert = builder.create();
        alert.setTitle(title);
        alert.show();
    }


}
