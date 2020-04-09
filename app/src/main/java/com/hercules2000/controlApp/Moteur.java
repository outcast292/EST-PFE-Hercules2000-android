package com.hercules2000.controlApp;

import com.hercules2000.control.connectionUtils;

public class Moteur {

    private String nomMoteur;
    private int minAngle,maxAngle;
    private int curAngle = 0;
    private int previousAngle;
    private char lettreMoteur;
    private boolean isCreated= false;


    public Moteur(String nomMoteur, int minAngle, int maxAngle) {
        this.nomMoteur = nomMoteur;
        this.minAngle = minAngle;
        this.maxAngle = maxAngle;
        this.lettreMoteur = nomMoteur.charAt(0);
        isCreated = true;
    }

    public boolean existsIn(String cmdApr){

        for(int i=0;i<cmdApr.length();i++)
        {
            if(this.lettreMoteur == cmdApr.charAt(i)){
                return true;
            }
        }
        return false;

    }

    public void setpreviousAngle(int previousAngle) {
        this.previousAngle = previousAngle;
    }

    public int getMinAngle() {
        return minAngle;
    }

    public int getMaxAngle() {
        return maxAngle;
    }

    public String getNomMoteur() {
        return nomMoteur;
    }

    public char getLettreMoteur() {
        return lettreMoteur;
    }

    public int getCurAngle() {
        return curAngle;
    }

    public void setCurAngle(int curAngle) {
        this.curAngle = curAngle;
    }

    public boolean isCreated() {
        return isCreated;
    }

    public int getpreviousAngle() {
        return previousAngle;
    }
}
