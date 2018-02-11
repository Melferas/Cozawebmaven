package com.mycompany.cozawebmaven;

import com.amazonaws.services.iot.client.AWSIotException;
import com.amazonaws.services.iot.client.AWSIotMqttClient;
import com.amazonaws.services.iot.client.AWSIotTopic;
import com.amazonaws.services.iot.client.sample.sampleUtil.SampleUtil;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ApplicationScoped
@ServerEndpoint("/actions")
public class DeviceWebSocketServer {

    private final String certificateFile = "C:/Users/Melferas/Documents/GitHub/Cozawebmaven/Cozawebmaven/src/main/webapp/resources/cert/f4dafc24ae-certificate.pem.crt";                       // X.509 based certificate file
    private final String privateKeyFile = "C:/Users/Melferas/Documents/GitHub/Cozawebmaven/Cozawebmaven/src/main/webapp/resources/cert/f4dafc24ae-private.pem.key";
    private final SampleUtil.KeyStorePasswordPair pair = SampleUtil.getKeyStorePasswordPair(certificateFile, privateKeyFile);
    private AWSIotMqttClient client;

    @Inject
    private DeviceSessionHandler sessionHandler;

    public DeviceWebSocketServer() {
        this.client = new AWSIotMqttClient("a3hsjmb0q4i06f.iot.eu-west-1.amazonaws.com", "awsiot1", pair.keyStore, pair.keyPassword);
        try {
            this.client.connect();
        } catch (AWSIotException ex) {
            Logger.getLogger(DeviceWebSocketServer.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Fallo al logearse");
        }
    }

    @OnOpen
    public void open(Session session) throws AWSIotException {
        sessionHandler.addSession(session);
        String topico = "$aws/things/Cozita1/shadow/update/accepted";
        AmazonRaspTopic atopac = new AmazonRaspTopic(topico, sessionHandler);
        client.subscribe(atopac);

        String getshadow = "$aws/things/Cozita1/shadow/get";

        String listenshadow = "$aws/things/Cozita1/shadow/get/accepted";
        AmazonRaspTopic laston = new AmazonRaspTopic(listenshadow, sessionHandler);
        client.subscribe(laston);

        for (int i = 0; i < 10; i++) {
            client.publish(getshadow, "");
        }

    }

    @OnClose
    public void close(Session session) throws AWSIotException {
        sessionHandler.removeSession(session);
        client.disconnect();
    }

    @OnError
    public void onError(Throwable error) {
        Logger.getLogger(DeviceWebSocketServer.class.getName()).log(Level.SEVERE, null, error);
    }

    @OnMessage
    public void handleMessage(String message, Session session) throws AWSIotException {

        try (JsonReader reader = Json.createReader(new StringReader(message))) {
            JsonObject jsonMessage = reader.readObject();
            String topic = "$aws/things/Cozita1/shadow/update";

            if ("tempup".equals(jsonMessage.getString("action"))) {
                sessionHandler.upTemp();
                String msn = "{\"state\":{\"desired\":{\"temperature\":" + sessionHandler.getTemp() + "}}}";
                //   System.out.println(sessionHandler.getTemp());
                client.publish(topic, msn);
            } else if ("tempdown".equals(jsonMessage.getString("action"))) {
                sessionHandler.downTemp();
                String msn = "{\"state\":{\"desired\":{\"temperature\":" + sessionHandler.getTemp() + "}}}";
                //  System.out.println(sessionHandler.getTemp());
                client.publish(topic, msn);
            } else if ("temptext".equals(jsonMessage.getString("action"))) {
                sessionHandler.setTemp(jsonMessage.getInt("tempvalue"));
                String msn = "{\"state\":{\"desired\":{\"temperature\":" + sessionHandler.getTemp() + "}}}";
                //  System.out.println(sessionHandler.getTemp());
                client.publish(topic, msn);
            } else if ("lightup".equals(jsonMessage.getString("action"))) {
                sessionHandler.upLight();
                String msn = "{\"state\":{\"desired\":{\"light\":" + sessionHandler.getLightLvl() + "}}}";
                client.publish(topic, msn);
            } else if ("lightdown".equals(jsonMessage.getString("action"))) {
                sessionHandler.downLight();
                String msn = "{\"state\":{\"desired\":{\"light\":" + sessionHandler.getLightLvl() + "}}}";
                client.publish(topic, msn);
            } else if ("lightext".equals(jsonMessage.getString("action"))) {
                sessionHandler.setLightLvl((double) jsonMessage.getInt("lightvalue"));
                String msn = "{\"state\":{\"desired\":{\"light\":" + sessionHandler.getLightLvl() + "}}}";
                client.publish(topic, msn);
            } else if ("preschange".equals(jsonMessage.getString("action"))) {
                sessionHandler.switchMode();
                int presencia = sessionHandler.isPresence() ? 1 : 0;
                String msn = "{\"state\":{\"desired\":{\"mode\":" + sessionHandler.getState() + ",\"presence\":" + presencia + "}}}";
                client.publish(topic, msn);
            } else if ("prestext".equals(jsonMessage.getString("action"))) {
                sessionHandler.setPresencedelay(jsonMessage.getJsonNumber("prestime").longValue());
                String msn = "{\"state\":{\"desired\":{\"presenceDelay\":" + sessionHandler.getPresencedelay() + "}}}";
                client.publish(topic, msn);
            } else {
                System.out.println("Fallo al coger el string");
            }
        }
    }

}
