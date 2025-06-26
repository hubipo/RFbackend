package org.links.anonymouschatroomservice.service;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Service;

@Service
public class MqttService {
    private final MqttClient client;

    public MqttService() throws MqttException {
        client = new MqttClient("tcp://broker.emqx.io:1883", MqttClient.generateClientId());
        MqttConnectOptions options = new MqttConnectOptions();
        options.setAutomaticReconnect(true);
        options.setCleanSession(true);
        client.connect(options);
    }

    public void sendMessage(String topic, String message) throws MqttException {
        client.publish(topic, new MqttMessage(message.getBytes()));
    }
}
