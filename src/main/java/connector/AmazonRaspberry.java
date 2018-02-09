package connector;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Julio Sorroche
 *
 * Esta clase se conecta a Amazon AWS para comunicarse con la capa intermedia
 * entre la "cosa" (thing) y la aplicacion que invoca la clase: Las shadow
 * things
 */
public class AmazonRaspberry {

    private int temp; // Temperatura
    private double lightLvl; // Nivel de luz
    private boolean presence; // Presencia (False = no, True = si)

    private enum mode {
        off, on, pres;

        public mode getNext() {
            return this.ordinal() < mode.values().length - 1
                    ? mode.values()[this.ordinal() + 1]
                    : null;
        }
    }; // off = Apagado, on = Encendido, pres = Presencia

    private mode state; // Donde se guarda el estado.

    public AmazonRaspberry() {
        temp = 30;
        lightLvl = 0.5;
        presence = false;
        state = mode.on;
    }

    /**
     * @return the temp
     */
    public int getTemp() {
        return temp;
    }

    /**
     * @param temp the temp to set
     */
    public void setTemp(int temp) {
        this.temp = temp;
    }

    /**
     * @return the lightLvl
     */
    public double getLightLvl() {
        return lightLvl;
    }

    /**
     * @param lightLvl the lightLvl to set
     */
    public void setLightLvl(double lightLvl) {
        this.lightLvl = lightLvl;
    }

    /*
     * @return the presence
     */
    public boolean isPresence() {
        return presence;
    }

    /**
     * @param presence the presence to set
     */
    public void setPresence(boolean presence) {
        this.presence = presence;
    }

    /**
     * @return the state
     */
    public String getState() {

        String res = "";

        switch (state) {
            case on:
                res = "ON";
                break;

            case off:
                res = "OFF";
                break;

            case pres:
                res = "Presence mode";
                break;

            default:
                res = "Broken";
                break;
        }

        return res;
    }

    public mode getEnumState() {

        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(mode state) {
        this.state = state;
    }

    public void upTemp() {
        temp++;
    }

    public void downTemp() {
        temp--;
    }

    public void upLight() {
        lightLvl += 0.01;
    }

    public void downLight() {
        lightLvl -= 0.01;
    }

    public void switchMode() {
        
        this.state = getEnumState().getNext();
        
        if(state.equals(mode.pres))
        {
            setPresence(true);
        }
        else
        {
            setPresence(false);
        }
    }

}
