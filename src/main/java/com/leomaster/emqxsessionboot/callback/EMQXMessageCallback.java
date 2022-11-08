package com.leomaster.emqxsessionboot.callback;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Slf4j
@Component
public class EMQXMessageCallback implements MqttCallbackExtended {

    @Value("${emqx.callSubBackTopic}")
    private List<String> callSubBackTopic;

    @Value("${emqx.callBackQos}")
    private Integer callBackQos;

    private final MqttClient mqttClient;

    @Autowired
    public EMQXMessageCallback(MqttClient mqttClient) {
        this.mqttClient = mqttClient;
    }

    @Override
    public void connectComplete(boolean reconnect, String serverURI) {
        log.info("ConnectComplete,{},{}",reconnect,serverURI);
        if (reconnect) {
            try {
                callSubBackTopic.forEach(s -> {
                    try {
                        mqttClient.subscribe(s, callBackQos);
                        log.info("ConnectComplete callback,{},{}",s,callBackQos);
                    } catch (MqttException e) {
                        log.info("reconnect subscribe fail,exception:{}", e.getMessage());
                        e.printStackTrace();
                    }
                });
            } catch (Exception e) {
                log.info("reconnect fail,exception:{}", e.getMessage());
                e.printStackTrace();
            }
        }
    }

    @Override
    public void connectionLost(Throwable cause) {
        // 连接丢失后，一般在这里面进行重连
        log.info("connection fail,start reconnect");
        log.error("[connection fail Message]:{}", cause.getMessage());
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) {
        String msg = new String(message.getPayload());
        log.info("receive message,topic:{},msg:{}", topic, msg);
        if (!CollectionUtils.isEmpty(callSubBackTopic)) {
            try {
                // 处理回调的数据
                log.info("decrypt receive message,topic:{}, msg : {}", topic,msg);
            } catch (Exception e) {
                log.info("[decrypt fail,msg]:{}", msg);
            }
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        log.info("[deliveryComplete]:{}", token.isComplete());
    }
}
