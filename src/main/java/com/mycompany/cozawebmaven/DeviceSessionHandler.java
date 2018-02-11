package com.mycompany.cozawebmaven;

import java.io.IOException;
import static java.lang.Math.round;
import java.text.DecimalFormat;
import javax.enterprise.context.ApplicationScoped;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.json.JsonObject;
import javax.json.spi.JsonProvider;
import javax.websocket.Session;

@ApplicationScoped
public class DeviceSessionHandler {

    private final Set<Session> sessions = new HashSet<>();
    private int temp = 30; // Temperatura
    private double lightLvl = 100.00; // Nivel de luz
    private boolean presence = true; // Presencia (False = no, True = si)

    private enum mode {
        on, off, pres;

        public mode getNext() {
            return this.ordinal() < mode.values().length - 1
                    ? mode.values()[this.ordinal() + 1]
                    : mode.values()[0];
        }
    }; // off = Apagado, on = Encendido, pres = Presencia

    private mode state = mode.on; // Donde se guarda el estado.

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
        return round(lightLvl);
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
    public int getState() {

        int res = 3;

        switch (state) {
            case on:
                res = 0;
                break;

            case off:
                res = 1;
                break;

            case pres:
                res = 2;
                break;

            default:
                res = 3;
                break;
        }

        return res;
    }

    public mode getEnumState() {

        return this.state;
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
        lightLvl += 1;
    }

    public void downLight() {
        lightLvl -= 1;
    }

    public void switchMode() {

        this.state = getEnumState().getNext();

        if (this.state.equals(mode.pres)) {
        } else {
            setPresence(false);
        }
    }

    public void addSession(Session session) {
        sessions.add(session);
        JsonObject addMessage = createAddMessage();
        sendToSession(session, addMessage);

    }

    public void removeSession(Session session) {
        sessions.remove(session);
    }

    public void receiveData(JsonObject mes) {
        JsonObject report = mes.getJsonObject("state");

        if (report != null && report.getJsonObject("reported") != null) {

            report = report.getJsonObject("reported");

            if (report.getInt("temperature", 999999) != 999999) {
                this.setTemp(report.getInt("temperature"));

            }
            if (!report.isNull("light")) {
                this.setLightLvl((double) (report.getInt("light")));
            }
            if (report.getInt("presence") > -1 && report.getInt("presence") < 2) {
                boolean bool = report.getInt("presence") != 0;

                this.setPresence(bool);
            }

            if (report.getInt("mode") > -1) {
                this.setState(mode.values()[report.getInt("mode")]);
            }
        }

        JsonObject mensaje = createAddMessage();
        sendToAllConnectedSessions(mensaje);
    }

    private JsonObject createAddMessage() {
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
            Logger.getLogger(DeviceSessionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @PostConstruct
    public void afterCreate() {
        System.out.println("Soup created");
    }
}
