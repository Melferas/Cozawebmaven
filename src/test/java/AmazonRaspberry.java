

import com.mycompany.cozawebmaven.*;
import com.amazonaws.services.iot.client.AWSIotMqttClient;
import java.io.IOException;
import java.security.KeyStore;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.json.JsonObject;
import javax.json.spi.JsonProvider;
import javax.websocket.Session;

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
@ApplicationScoped
public class AmazonRaspberry extends AWSIotMqttClient {

    private int temp = 30; // Temperatura
    private double lightLvl = 100.00; // Nivel de luz
    private boolean presence = true; // Presencia (False = no, True = si)
    private final Set<Session> sessions = new HashSet<>();

    public AmazonRaspberry(String clientEndpoint, String clientId, KeyStore keyStore, String keyPassword) {
        super(clientEndpoint, clientId, keyStore, keyPassword);
    }
  
    private enum mode {
        off, on, pres;

        public mode getNext() {
            return this.ordinal() < mode.values().length - 1
                    ? mode.values()[this.ordinal() + 1]
                    : null;
        }
    }; // off = Apagado, on = Encendido, pres = Presencia

    private mode state; // Donde se guarda el estado.
/*
    public AmazonRaspberry() {
        temp = 30;
        lightLvl = 0.5;
        presence = false;
        state = mode.on;
    }
     */
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
        lightLvl += 0.1;
    }

    public void downLight() {
        lightLvl -= 0.1;
    }

    public void switchMode() {

        this.state = getEnumState().getNext();

        if (state.equals(mode.pres)) {
            setPresence(true);
        } else {
            setPresence(false);
        }
    }

    public void addSession(Session session) {
        sessions.add(session);
        JsonObject showMessage = createShowMessage();
        sendToSession(session, showMessage);

    }

    public void removeSession(Session session) {
        sessions.remove(session);
    }

    private JsonObject createShowMessage() {
        JsonProvider provider = JsonProvider.provider();
        JsonObject addMessage = provider.createObjectBuilder()
                .add("action", "show")
                .add("temp", this.getTemp())
                .add("light", this.getLightLvl())
                .add("mode", this.getState())
                .add("pres", this.isPresence())
                .build();
        return addMessage;
    }

    private void sendToAllConnectedSessions(JsonObject message) {
        for (Session session : sessions) {
            sendToSession(session, message);
        }
    }

    private void sendToSession(Session session, JsonObject message) {
        try {
            session.getBasicRemote().sendText(message.toString());
        } catch (IOException ex) {
            sessions.remove(session);
            Logger.getLogger(AmazonRaspberry.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
