package com.leomaster.emqxsessionboot.factory;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component("emqxProperties")
@Configuration
@RequiredArgsConstructor
@ConfigurationProperties("emqx")
public class EMQXProperties {

    private String url;
    /**
     * # host选择策略: 策略1:随机;策略2:选择集合第一个
     */
    private Integer policy;

    /**
     * emq 链接地址
     */
    private List<String> host;

    /**
     * 端口
     */
    private Integer port;

    /**
     * 链接方式 true ssl  false  tcp
     */
    private Boolean isTlsConnection;

    /**
     * 保持session链接
     */
    private Boolean isCleanSession;

    /**
     * 客户端ID
     */
    private String clientId;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 超时时间
     */
    private int timeout;
    /**
     * 心跳检测时间
     */
    private int keepAlive;

    /**
     * 主题
     */
    private String[] topic;

    /**
     * 遗嘱主题
     */
    private String willTopic;

    /**
     * 服务质量
     */
    private Integer qos;

    /**
     * 保留策略
     */
    private Boolean retain;

    /**
     * 回调的topic
     */
    private String[] callBackTopic;

    /**
     * 回调服务质量
     */
    private Integer callBackQos;

    /**
     * 回调的客户端订阅topic
     */
    private String[] callSubBackTopic;

}