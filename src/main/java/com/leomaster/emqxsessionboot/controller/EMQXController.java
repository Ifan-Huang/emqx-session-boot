package com.leomaster.emqxsessionboot.controller;

import com.leomaster.emqxsessionboot.param.MessageParam;
import com.leomaster.emqxsessionboot.service.EMQXService;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/emqx")
public class EMQXController {

    private final EMQXService emqxService;

    @Autowired
    public EMQXController(EMQXService emqxService) {
        this.emqxService = emqxService;
    }

    @PostMapping("/pushMessage")
    public ResponseEntity<String> pushMessage(@Validated @RequestBody MessageParam messageParam) {
        emqxService.pushMessage(messageParam);
        return ResponseEntity.ok("SUCCESS");
    }
}
