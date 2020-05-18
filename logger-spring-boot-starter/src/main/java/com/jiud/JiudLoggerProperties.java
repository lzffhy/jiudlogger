package com.jiud;


import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 描述：配置信息 实体
 */
@ConfigurationProperties(prefix = "jiudlogger")
public class JiudLoggerProperties {

    private String suffix = "";

    private String postfix = "++++";

    private String split = "+";

    private String system;

    private String showinfo = null;

    public JiudLoggerProperties(){}

    public JiudLoggerProperties(String suffix, String postfix, String split, String system, String showinfo) {
        this.suffix = suffix;
        this.postfix = postfix;
        this.split = split;
        this.system = system;
        this.showinfo = showinfo;
    }

    public void setShowinfo(String showinfo) {
        this.showinfo = showinfo;
    }

    public String getShowinfo() {
        return showinfo;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public String getSystem() {
        return system;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getPostfix() {
        return postfix;
    }

    public void setPostfix(String postfix) {
        this.postfix = postfix;
    }

    public String getSplit() {
        return split;
    }

    public void setSplit(String split) {
        this.split = split;
    }
}
