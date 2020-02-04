package com.hercules2000.controlApp;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.widget.TextView;

import com.hercules2000.control.ConnectionHandler;
import com.r0adkll.slidr.Slidr;
import com.sdsmdg.harjot.crollerTest.*;
import com.vashisthg.startpointseekbar.StartPointSeekBar;

public class MainActivity extends AppCompatActivity {
    private TextView nomMoteur,angletxtValue;
    private Croller knobVitesse;
    private StartPointSeekBar knobAngle;
    private ConstraintLayout paneCtrl;
    String lettreMoteur;
    int angleSaisie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initControl();
    }

    public void initControl(){
        setContentView(R.layout.activity_main);
        initView();
        angleValue();
        vitesseValue();
        Slidr.attach(this);
    }
    public void initView(){
        nomMoteur = findViewById(R.id.nomMoteur);
        knobAngle = findViewById(R.id.knobAngle);
        knobVitesse = findViewById(R.id.knobVitesse);
        paneCtrl = findViewById(R.id.paneCtrl);
        angletxtValue = findViewById(R.id.angletxtValue);
    }

    public void angleValue() {

        knobAngle.setOnSeekBarChangeListener(new StartPointSeekBar.OnSeekBarChangeListener() {
            @Override
            public void onOnSeekBarValueChange(StartPointSeekBar bar, double value) {
                angletxtValue.setText("Angle de rotation : " + (int)value + "°");
                angleSaisie = (int)value;
            }
        });
    }
    public void vitesseValue() {
        knobVitesse.setOnCrollerChangeListener(new OnCrollerChangeListener() {
            @Override
            public void onProgressChanged(Croller croller, int progress) {
                knobVitesse.setLabel( "" + progress + " °/s");
            }

            @Override
            public void onStartTrackingTouch(Croller croller) {

            }

            @Override
            public void onStopTrackingTouch(Croller croller) {
                knobVitesse.setLabel("Vitesse de rotation (°/s)");
            }
        });
    }

    public void btnPince(View v){
        showDialog("Erreur", "En cours de construction");
    }



    public void btnMain(View v){
        moteurDefault(-90,90,20,"Tanguage");
    }
    public void btnBras(View v)
    {
        moteurDefault(-160,160,20,"Roulis");
    }
    public void btnCoude(View v)
    {
        moteurDefault(-124,82,20,"Coude");
    }
    public void btnEpaule(View v)
    {
        moteurDefault(-115,91,20,"Epaule");
    }
    public void btnBase(View v)
    {
        moteurDefault(-160,160,20,"Base");
    }


    public void btnValider(View v) {

        int angleMouvement = angleSaisie;
        int vitesseMouvement = knobVitesse.getProgress() + 1;

        String signe = ((angleMouvement>0) ? "+" : "");
        String COMMANDE = lettreMoteur  + signe + angleMouvement + ":" + vitesseMouvement;

        if (lettreMoteur != null )
        {
            //showDialog("Commande",  );
            ConnectionHandler.sendMessage(COMMANDE);
             }
        else        showDialog("Erreur", "Veuillez choisir un moteur" );


    }
    public void showDialog(String title ,String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setCancelable(true);

        AlertDialog alert = builder.create();
        alert.setTitle(title);
        alert.show();
    }
    public void moteurDefault(int minAngle,int maxAngle, int angleValue, String nomM) {

            knobAngle.setAbsoluteMinMaxValue(minAngle,maxAngle);
            knobAngle.setProgress(angleValue);
            angleSaisie=angleValue;
            nomMoteur.setText(nomM);
            lettreMoteur=nomM.substring(0,1);
    }

/*
    public String getAngle(char moteur){

        String dollar = "B+100:29E-103:29C-089:29R+047:29T+090:29";

        String angle;

        angle = (() ? "" : "")
    }
*/
}
