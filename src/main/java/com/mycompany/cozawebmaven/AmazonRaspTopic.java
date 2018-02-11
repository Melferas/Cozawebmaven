/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.cozawebmaven;

import com.amazonaws.services.iot.client.AWSIotMessage;
import com.amazonaws.services.iot.client.AWSIotQos;
import com.amazonaws.services.iot.client.AWSIotTopic;
import java.io.StringReader;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

/**
 *
 * @author Melferas
 */
public class AmazonRaspTopic extends AWSIotTopic {

    DeviceSessionHandler s;

    public AmazonRaspTopic(String topic, AWSIotQos qos, DeviceSessionHandler s) {
        super(topic, qos);
    }

    public AmazonRaspTopic(String topic, DeviceSessionHandler s) {
        super(topic);
        this.s=s;
    }

    @Override
    public void onMessage(AWSIotMessage message) {
        System.out.println(System.currentTimeMillis() + ": <<< " + message.getStringPayload());

        try (JsonReader reader = Json.createReader(new StringReader(message.getStringPayload()))) {
            JsonObject jsonMessage = reader.readObject();
            s.receiveData(jsonMessage);

        }catch(Exception e)
        {
            System.out.println(e);
        }
    }
}
