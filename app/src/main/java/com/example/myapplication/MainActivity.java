package com.example.myapplication;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import android.widget.TextView;

import com.r0adkll.slidr.Slidr;
import com.sdsmdg.harjot.crollerTest.*;
import com.vashisthg.startpointseekbar.StartPointSeekBar;

public class MainActivity extends AppCompatActivity {
    private TextView nomMoteur,angletxtValue;
    private Croller knobAngle ,knobVitesse;
    private StartPointSeekBar sbAngle;
    private ConstraintLayout paneCtrl;
    String lettreMoteur;
    int angleSaisie;
    boolean boolTangage;

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
    }

    public void angleValue() {
        knobAngle.setOnProgressChangedListener(new Croller.onProgressChangedListener() {
            @Override
            public void onProgressChanged(int progress) {
                knobAngle.setLabel( "" + progress + "°");
                angleSaisie = progress;
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
        ConstraintLayout layoutCntrl = findViewById(R.id.paneCtrl);

        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.tangage_main, null);

        layoutCntrl.removeAllViews();
        layoutCntrl.addView(layout);

        sbAngle = findViewById(R.id.sbAngle);
        angletxtValue = findViewById(R.id.angletxtValue);
        nomMoteur = findViewById(R.id.nomMoteur);


        sbAngle.setOnSeekBarChangeListener(new StartPointSeekBar.OnSeekBarChangeListener() {
            @Override
            public void onOnSeekBarValueChange(StartPointSeekBar bar, double value) {
                angletxtValue.setText((int) value + "°");
                angleSaisie = (int)value;
            }
        });

        moteurDefault(-90,90,20,"Tangage",true);
    }
    public void btnBras(View v)
    {
        moteurDefault(0,206,20,"Roulis",false);
    }
    public void btnCoude(View v)
    {
        moteurDefault(0,206,20,"Coude",false);
    }
    public void btnEpaule(View v)
    {
        moteurDefault(0,206,20,"Epaule",false);
    }
    public void btnBase(View v)
    {
        moteurDefault(0,320,20,"Base",false);
    }


    public void btnValider(View v) {
        int angleMouvement;

        if(boolTangage){
            angleMouvement = angleSaisie - 20;
        }else{
            angleMouvement = angleSaisie - 20;
        }

        int vitesseMouvement = knobVitesse.getProgress() + 1;
        String signe = ((angleMouvement>0) ? "+" : "");

        if (lettreMoteur != null )
        {showDialog("Commande", lettreMoteur + ":" + signe + angleMouvement + ":" + vitesseMouvement );}
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
    public void moteurDefault(int minAngle,int maxAngle, int angleValue, String nomM, boolean isTanguage) {
        if(!isTanguage) {
            initControl();
            knobAngle.setMin(minAngle);
            knobAngle.setMax(maxAngle);
            knobAngle.setProgress(angleValue);
            nomMoteur.setText(nomM);
            lettreMoteur=nomM.substring(0,1);
        }else{
            sbAngle.setAbsoluteMinMaxValue(minAngle,maxAngle);
            sbAngle.setProgress(angleValue);
            angleSaisie=angleValue;
            nomMoteur.setText(nomM);
            lettreMoteur=nomM.substring(0,1);
            boolTangage = true;
        }

    }


}
