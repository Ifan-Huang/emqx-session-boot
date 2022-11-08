package com.leomaster.emqxsessionboot.service.impl;

import com.leomaster.emqxsessionboot.param.MessageParam;
import com.leomaster.emqxsessionboot.service.EMQXService;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EMQXServiceImpl implements EMQXService {

    private final MqttClient mqttClient;

    @Autowired
    public EMQXServiceImpl(MqttClient mqttClient) {
        this.mqttClient = mqttClient;
    }

    @Override
    public void pushMessage(MessageParam messageParam) {
        checkMessageParam(messageParam);
        MqttMessage message = new MqttMessage(messageParam.getMessage().getBytes());
        try {
            mqttClient.publish(messageParam.getTopic(), message);
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }

    private void checkMessageParam(MessageParam messageParam) {
        String message = messageParam.getMessage();
        String topic = messageParam.getTopic();
        if(null == message || null == topic) {
            throw new RuntimeException("message or topic cannot be empty.");
        }
    }
}
