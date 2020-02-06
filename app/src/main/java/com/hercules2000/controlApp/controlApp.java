package com.hercules2000.controlApp;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.widget.TextView;

import com.hercules2000.control.connectionActivity;
import com.hercules2000.control.connectionUtils;
import com.r0adkll.slidr.Slidr;
import com.sdsmdg.harjot.crollerTest.*;
import com.vashisthg.startpointseekbar.StartPointSeekBar;

public class controlApp extends AppCompatActivity {
    private TextView nomMoteur,angletxtValue;
    private Croller knobVitesse;
    private StartPointSeekBar knobAngle;
    private ConstraintLayout paneCtrl;
    char lettreMoteur;
    int angleSaisie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initControl();
    }

    public void initControl(){
        setContentView(R.layout.activity_main);
        initView();
        vitesseValue();
        angleValue();
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
        moteurDefault(-90,90,"Tanguage");
    }
    public void btnBras(View v)
    {
        moteurDefault(-160,160,"Roulis");
    }
    public void btnCoude(View v)
    {
        moteurDefault(-124,82,"Coude");
    }
    public void btnEpaule(View v)
    {
        moteurDefault(-115,91,"Epaule");
    }
    public void btnBase(View v)
    {
        moteurDefault(-160,160,"Base");
    }


    public void btnValider(View v) {

        int angleMouvement = angleSaisie;
        int vitesseMouvement = knobVitesse.getProgress() + 1;

        String signe = ((angleMouvement>0) ? "+" : "");
        String COMMANDE = lettreMoteur  + signe + angleMouvement + ":" + vitesseMouvement;

        if (!connectionUtils.ismRun())
        {
            showDialog("Socket","Veullez vous connectez!");
             }
        else if(lettreMoteur != 0){
            connectionUtils.sendMessage(COMMANDE);
        }
        else        showDialog("Erreur", "Veuillez choisir un moteur" );


    }



    public void moteurDefault(int minAngle,int maxAngle, String nomM) {

            knobAngle.setAbsoluteMinMaxValue(minAngle,maxAngle);
            nomMoteur.setText(nomM);
            lettreMoteur=nomM.charAt(0);
            angleSaisie = getArmStat(lettreMoteur);
            knobAngle.setProgress(angleSaisie);
            angletxtValue.setText("Angle de rotation : " + (int)angleSaisie + "°");
    }


    public static String getDollar()
    {
        connectionUtils.sendMessage("$");

        return connectionUtils.readMessage().toString() ;

    }

    public int getArmStat(char moteur){

        String dollarRequest = getDollar();
        int angle=0;
        for(int i=0;i<dollarRequest.length();i++)
        {
            if(dollarRequest.charAt(i) == moteur){
                angle = Integer.parseInt(dollarRequest.substring(i+1,i+5));
            }
        }
        return angle;

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
