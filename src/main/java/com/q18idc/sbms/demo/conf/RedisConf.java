package com.q18idc.sbms.demo.conf;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author q18idc.com QQ993143799
 * @date 2018/5/4 11:25
 */
@Component
@ConfigurationProperties(prefix = "")
@PropertySource("classpath:redis.properties")
public class RedisConf {
    @Getter
    @Setter
    private String host;

    @Getter
    @Setter
    private String port;

    @Getter
    @Setter
    private String database;

}
