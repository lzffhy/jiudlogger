package com.jiud;


import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 描述：配置信息 实体
 */
@ConfigurationProperties(prefix = "jiudlogger")
public class JiudLoggerProperties {

    private String type;

    private String describe;

    public JiudLoggerProperties(){}

    public JiudLoggerProperties(String type, String describe) {
        this.type = type;
        this.describe = describe;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}
