package com.leomaster.emqxsessionboot.factory;

import org.eclipse.paho.client.mqttv3.MqttClient;

public interface EMQXFactory {

    /**
     * 生成mqtt客户端
     * @param properties EMQX properties
     * @return client
     */
    MqttClient generate(EMQXProperties properties);

}
