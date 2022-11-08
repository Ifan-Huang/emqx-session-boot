package com.leomaster.emqxsessionboot.factory;

import com.leomaster.emqxsessionboot.callback.EMQXMessageCallback;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DefaultEMQXFactory implements EMQXFactory {

    private final EMQXMessageCallback emqxMessageCallback;

    @Autowired
    public DefaultEMQXFactory(EMQXMessageCallback emqxMessageCallback) {
        this.emqxMessageCallback = emqxMessageCallback;
    }
    /**
     * generate mqtt client.
     * @param properties EMQX properties
     * @return client
     */
    @Override
    public MqttClient generate(EMQXProperties properties) {
        MqttClient client = null;
        try {
            client = new MqttClient(properties.getUrl(), properties.getClientId(), new MemoryPersistence());
            // MQTT 连接选项
            MqttConnectOptions connOpts = new MqttConnectOptions();
            // 保留会话
            connOpts.setCleanSession(properties.getIsCleanSession());
            connOpts.setUserName(properties.getUsername());
            connOpts.setPassword(properties.getPassword().toCharArray());
            connOpts.setConnectionTimeout(properties.getTimeout());
            connOpts.setKeepAliveInterval(properties.getKeepAlive());
            connOpts.setAutomaticReconnect(true);
            connOpts.setWill(properties.getWillTopic(), (properties.getClientId()+ "connection fail").getBytes(), 0, false);
            // 设置回调
            client.setCallback(emqxMessageCallback);
            // 建立连接
            IMqttToken iMqttToken = client.connectWithResult(connOpts);
            if (iMqttToken.isComplete()) {
                log.info("mqtt connection success");
            } else {
                log.info("mqtt connection fail");
            }
        } catch (MqttException e) {
            log.info("connection mqtt exception,exception:{}", e.getStackTrace());
            e.printStackTrace();
        }
        return client;
    }
}
