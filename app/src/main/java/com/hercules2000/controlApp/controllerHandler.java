package com.hercules2000.controlApp;

import com.hercules2000.control.connectionUtils;

import static java.lang.Integer.parseInt;

public class controllerHandler {
    static Moteur Tanguage, Roulis, Coude, Epaule, Base;
    private static String dollarRequest = getDollar();

    public static void initMotors()
    {
        String dollarRequest = getDollar();

        Tanguage = new Moteur("Tanguage", -90, 90);
        setCurrentState(Tanguage,dollarRequest);
        Roulis = new Moteur("Roulis", -160, 160);
        setCurrentState(Roulis,dollarRequest);
        Coude = new Moteur("Coude", -124, 82);
        setCurrentState(Coude,dollarRequest);
        Epaule = new Moteur("Epaule", -115, 91);
        setCurrentState(Epaule,dollarRequest);
        Base = new Moteur("Base", -160, 160);
        setCurrentState(Base,dollarRequest);
    }


    public static String getDollar()
    {
        connectionUtils.sendMessage("$");

        return connectionUtils.readMessage().toString() ;

    }

    public static String Commande(Moteur m)
    {

        m.setpreviousAngle(m.getCurAngle());
        m.setCurAngle(controlApp.getAngleSaisie());
        System.out.println(controlApp.getKnobAngle().getProgress());

        int vitesseMouvement = controlApp.knobVitesse.getProgress();


        String signe = ((m.getCurAngle() > 0) ? "+" : "");
        String commande = "L" + m.getLettreMoteur() + signe + String.format("%03d", map_value(m.getCurAngle(), m.getMinAngle(),m.getMaxAngle(),-511,511)) + ":" + String.format("%02d",vitesseMouvement);


        return commande;
    }
    public static void setCurrentState(Moteur m, String dollarRequest){

        int angle=0;

        if(m.getCurAngle() == 0)
        {
            for(int i=0;i<dollarRequest.length();i++)
            {
                if(dollarRequest.charAt(i) == m.getLettreMoteur()){
                    angle = parseInt(dollarRequest.substring(i+1,i+5));
                    m.setCurAngle(angle);
                }
            }
        }

    }
    private static int map_value(int x, int in_min, int in_max, int out_min, int out_max)
    {
        return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
    }

}
