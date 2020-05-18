package com.jiud;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jiud.eum.LogType;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LogEntity {
    private String requestTime;//时间

    private String requestUrl;//请求路径

    private String description;//描述信息

    private String requestMethod;//请求类型

    private String classMethod;//请求类方法信息

    private String clientRealIp;//客户端真实ip

    private String requestArgs;//请求参数

    private String responseResult;//请求结果

    private String userAgent;//客户端信息

    private String  elapsedTime;//耗时

    private int logType;//info,warning,debug,error

    private String system;//系统id

    private String faildMessage;//失败信息


    public LogEntity(){}

    public void setFaildMessage(String faildMessage) {
        this.faildMessage = faildMessage;
    }

    public String getFaildMessage() {
        return faildMessage;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public String getSystem() {
        return system;
    }

    public void setLogType(int logType) {
        this.logType = logType;
    }

    public int getLogType() {
        return logType;
    }

    public String getResponseResult() {
        return responseResult;
    }

    public void setResponseResult(String responseResult) {
        this.responseResult = responseResult;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(String elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public String getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(String requestTime) {
        this.requestTime = requestTime;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public String getClassMethod() {
        return classMethod;
    }

    public void setClassMethod(String classMethod) {
        this.classMethod = classMethod;
    }

    public String getClientRealIp() {
        return clientRealIp;
    }

    public void setClientRealIp(String clientRealIp) {
        this.clientRealIp = clientRealIp;
    }

    public String getRequestArgs() {
        return requestArgs;
    }

    public void setRequestArgs(String requestArgs) {
        this.requestArgs = requestArgs;
    }

    public String getSplitEntity(String split, String showInfo, String suffix, String postfix) {
        List<String> names = null;
        if (StringUtils.isNotBlank(showInfo)) {
            names = Arrays.asList(showInfo.split(","));
        } else {
            Field[] fields = this.getClass().getDeclaredFields();
            names = Arrays.asList(fields).stream().map(e -> {return  e.getName();}).collect(Collectors.toList());
        }
        JSONObject entity = JSON.parseObject(JSON.toJSONString(this));
        StringBuilder sb = new StringBuilder();
        if (StringUtils.isNotBlank(suffix)) sb.append(suffix);
        if (this.getLogType() == LogType.INFO.getStatenum()) sb.append("【系统提示】");
        for (String name : names) {
            sb.append(entity.getString(name));
            sb.append(split);
        }
        return sb.substring(0,sb.length() - split.length()) + postfix;
    }
}
