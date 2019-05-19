package com.my.iot.common.influxdb.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Deprecated
@ConfigurationProperties(prefix="influx")
@AllArgsConstructor
@Getter @Setter
/**
 * InfluxDBConfig.java
 * 미사용
 * @author 효민영♥
 *
 */
public class InfluxDBConfig {
    
    public String connectUri;
    public String username;
    public String password;
}
