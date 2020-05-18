package com.jiud.aop;

import com.alibaba.fastjson.JSON;
import com.jiud.JiudLoggerProperties;
import com.jiud.LogEntity;
import com.jiud.eum.LogType;
import com.jiud.annotation.SysLog;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Aspect
@Component
public class SysLogAspect {

    protected Logger logger = LoggerFactory.getLogger(SysLogAspect.class);

    private final static SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    private LogEntity logEntity = null;

    @Autowired
    private JiudLoggerProperties jiudLoggerProperties;

    @Pointcut("@annotation(com.jiud.annotation.SysLog)")
    public void webLog(){}

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable{
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String methodDesription = getAspectLogDescription(joinPoint);
        this.logEntity = new LogEntity();
        this.logEntity.setSystem(jiudLoggerProperties.getSystem());
        this.logEntity.setRequestTime(FORMAT.format(new Date()));
        this.logEntity.setRequestUrl(request.getRequestURL().toString());
        this.logEntity.setDescription(methodDesription == null ? "" : methodDesription);
        this.logEntity.setRequestMethod(request.getMethod());
        this.logEntity.setClassMethod(joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName());
        this.logEntity.setClientRealIp( getClientRealIp(request));
        this.logEntity.setRequestArgs(JSON.toJSONString(joinPoint.getArgs()));
        this.logEntity.setUserAgent(request.getHeader("User-Agent"));
    }

    @Around("webLog()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
        long startTime = System.currentTimeMillis();
        Object result = null;
        try {
            result = proceedingJoinPoint.proceed();
            this.logEntity.setResponseResult(JSON.toJSONString(result));
            this.logEntity.setLogType(LogType.INFO.getStatenum());
            this.logEntity.setFaildMessage(" ");
        } catch (Exception e) {
            this.logEntity.setLogType(LogType.ERROR.getStatenum());
            this.logEntity.setResponseResult(" ");
            this.logEntity.setFaildMessage(e.getMessage());
        }
        this.logEntity.setElapsedTime("" + (System.currentTimeMillis() - startTime) + "ms");
        return result;
    }

    @After("webLog()")
    public void doAfter() throws Throwable{
        if (this.logEntity == null) return;
        String result = this.logEntity.getSplitEntity(jiudLoggerProperties.getSplit(), jiudLoggerProperties.getShowinfo(), jiudLoggerProperties.getSuffix(), jiudLoggerProperties.getPostfix());
        switch (this.logEntity.getLogType()) {
            case 1:
                logger.info(result); break;
            case 2:
                logger.warn(result); break;
            case 3:
                logger.debug(result); break;
            case 4:
                logger.error(result); break;
            default:
                break;
        }
        //释放该对象
        this.logEntity = null;
    }

    public String getAspectLogDescription(JoinPoint joinPoint){
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        SysLog webLog = method.getAnnotation(SysLog.class);
        return webLog.description();
    }

    public static String getClientRealIp(HttpServletRequest request) {
        String realIp = null;
        realIp = request.getHeader("X-Forwarded-For");
        if (!checkRealIp(realIp)) {
            realIp = request.getHeader("Proxy-Client-Ip");
            if (!checkRealIp(realIp)) {
                realIp = request.getHeader("WL-Proxy-Client-Ip");
                if (!checkRealIp(realIp)) {
                    realIp = request.getRemoteAddr();
                    if (realIp.equals("127.0.0.1") || realIp.endsWith("0:0:0:0:0:0:1")) {
                        try {
                            realIp = InetAddress.getLocalHost().getHostAddress();
                        } catch (UnknownHostException e) {
                        }
                    }
                }
            }
        }
        if (realIp != null && realIp.length() > 15) {
            if (realIp.indexOf(",") > 0) {
                realIp = realIp.substring(0,realIp.indexOf(","));
            }
        }
        return realIp;
    }

    /**
     * 获取本机ip
     * @return
     * @throws Exception
     */
    public static String getLocalHostIp() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
        }
        return null;
    }

    /**
     * getClientRealIp使用
     * @param realIp
     * @return
     */
    private static boolean checkRealIp(String realIp) {
        return realIp == null || realIp.length() == 0 || realIp.equalsIgnoreCase("unKnown") ? false : true;
    }
}
