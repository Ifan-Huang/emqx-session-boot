package com.leomaster.emqxsessionboot.config;

import com.leomaster.emqxsessionboot.factory.EMQXFactory;
import com.leomaster.emqxsessionboot.factory.EMQXProperties;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Random;



@Slf4j
@Configuration
public class EMQXConfig {

    private final EMQXProperties emqxProperties;

    private final EMQXFactory emqxFactory;

    @Autowired
    public EMQXConfig(EMQXProperties emqxProperties, EMQXFactory emqxFactory) {
        this.emqxProperties = emqxProperties;
        this.emqxFactory = emqxFactory;
    }

    @Bean
    public MqttClient mqttClient() {
        Random random = new Random();
        String hosts = emqxProperties.getHost().get(random.nextInt(emqxProperties.getHost().size()));
        String url;
        if (emqxProperties.getIsTlsConnection()) {
            url = "ssl://" + hosts + ":" + emqxProperties.getPort();
        } else {
            url = "tcp://" + hosts + ":" + emqxProperties.getPort();
        }
        emqxProperties.setUrl(url);
        MqttClient generate = emqxFactory.generate(emqxProperties);
        Arrays.stream(emqxProperties.getCallSubBackTopic()).forEach(s -> {
            try {
                generate.subscribe(s, emqxProperties.getCallBackQos());
                log.info("MqttConfig,{},{}",s, emqxProperties.getCallBackQos());
            } catch (MqttException e) {
                e.printStackTrace();
            }
        });

        return generate;
    }
}
