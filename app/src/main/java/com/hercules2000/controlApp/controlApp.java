package com.hercules2000.controlApp;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.hercules2000.control.connectionActivity;
import com.hercules2000.control.connectionUtils;
import com.r0adkll.slidr.Slidr;
import com.sdsmdg.harjot.crollerTest.*;
import com.vashisthg.startpointseekbar.StartPointSeekBar;

import static com.hercules2000.controlApp.controllerHandler.Base;
import static com.hercules2000.controlApp.controllerHandler.Coude;
import static com.hercules2000.controlApp.controllerHandler.Epaule;
import static com.hercules2000.controlApp.controllerHandler.Roulis;
import static com.hercules2000.controlApp.controllerHandler.Tanguage;

public class controlApp extends AppCompatActivity {
    private TextView nomMoteur, angletxtValue;
    public static Croller knobVitesse;
    private static StartPointSeekBar knobAngle;
    private ConstraintLayout paneCtrl;
    char lettreMoteurSelectionner;
    private Moteur MoteurSelected;
    static boolean isAprMode = false;
    private String cmdApr = "";
    private CheckBox modeApr;
    private Button btnAjouter;
    public static int angleSaisie;
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
        initControl();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void initControl() {
        setContentView(R.layout.activity_main);
        initView();
    }

    public void initView() {
        nomMoteur = findViewById(R.id.nomMoteur);
        knobAngle = findViewById(R.id.knobAngle);
        knobVitesse = findViewById(R.id.knobVitesse);
        paneCtrl = findViewById(R.id.paneCtrl);
        angletxtValue = findViewById(R.id.angletxtValue);
        modeApr = findViewById(R.id.isAppMode);
        btnAjouter = findViewById(R.id.Ajouter);
        //
        findViewById(R.id.btnPince).setOnTouchListener(listener);
        findViewById(R.id.btnMain).setOnTouchListener(listener);
        findViewById(R.id.btnBras).setOnTouchListener(listener);
        findViewById(R.id.btnCoude).setOnTouchListener(listener);
        findViewById(R.id.btnEpaule).setOnTouchListener(listener);
        findViewById(R.id.btnBase).setOnTouchListener(listener);


        vitesseValue();
        angleValue();
        Slidr.attach(this);

    }

    public void angleValue() {

        knobAngle.setOnSeekBarChangeListener(new StartPointSeekBar.OnSeekBarChangeListener() {
            @Override
            public void onOnSeekBarValueChange(StartPointSeekBar bar, double value) {
                angletxtValue.setText("Angle de rotation : " + (int) value + "°");
                angleSaisie = (int) value;
            }
        });
     }

    public void vitesseValue() {
        knobVitesse.setOnCrollerChangeListener(new OnCrollerChangeListener() {
            @Override
            public void onProgressChanged(Croller croller, int progress) {
                knobVitesse.setLabel("" + progress + " °/s");
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

    public void btnPince(View v) {
        showDialog("Erreur", "En cours de construction");
    }

    public void btnMain(View v) {
        initMoteur(Tanguage);
    }

    public void btnBras(View v) {
        initMoteur(Roulis);
    }

    public void btnCoude(View v) {
        initMoteur(Coude);
    }

    public void btnEpaule(View v) {
        initMoteur(Epaule);
    }

    public void btnBase(View v) {
        initMoteur(Base);
    }


    public void btnValider(View v) {

        if (!connectionUtils.ismRun()) {
            showDialog("Socket", "Veullez vous connectez!");
        } else if (lettreMoteurSelectionner != 0) {
            if(getModeApr().isChecked())
            {
                if (cmdApr.isEmpty()){
                   showDialog("Erreur", "Veuillez ajouter un moteur");
                }
                else{
                    connectionUtils.sendMessage("L" + cmdApr);
                    cmdApr = "";
                 }
            }
            else {
                connectionUtils.sendMessage(controllerHandler.Commande(MoteurSelected));
            }
        }
        else showDialog("Erreur", "Veuillez choisir un moteur");

    }

    public void btnAjouter(View v){

        if(lettreMoteurSelectionner != 0){
            if(!MoteurSelected.existsIn(cmdApr)){
                cmdApr = cmdApr + controllerHandler.Commande(MoteurSelected).replace("L","");
            }else{
                showDialog("Existe deja",  "" + MoteurSelected.getLettreMoteur());
            }
        }else{
            showDialog("Erreur", "Veuillez choisir un moteur");
        }

    }
    public void onClickCheckBox(View v) {

        if(getModeApr().isChecked()){
            btnAjouter.setEnabled(true);
        }
        else{
            btnAjouter.setEnabled(false);
        }

    }
    public void initMoteur(Moteur m) {
            knobAngle.setAbsoluteMinMaxValue(m.getMinAngle(), m.getMaxAngle());
            nomMoteur.setText(m.getNomMoteur());
            knobAngle.setProgress(m.getCurAngle());
            knobVitesse.setProgress(29);
            lettreMoteurSelectionner = m.getLettreMoteur();
            MoteurSelected = m;
            angletxtValue.setText("Angle de rotation : " + m.getCurAngle() + "°");
            angleSaisie = m.getCurAngle();
    }

    public void showDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setCancelable(true);

        AlertDialog alert = builder.create();
        alert.setTitle(title);
        alert.show();
    }

    public void viewCmd(View v){

        if(cmdApr == ""){
            showDialog("Commande complexe" , "Cliquez sur Ajouter pour former votre commande complexe");
        }else{
            showDialog("Commande complexe" , cmdApr);

        }
    }

    public CheckBox getModeApr() {
        return modeApr;
    }

    public static boolean isAprMode() {
        return isAprMode;
    }

    public static StartPointSeekBar getKnobAngle() {
        return knobAngle;
    }

    public static int getAngleSaisie() {
        return angleSaisie;
    }
}
