package com.jiud.aop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.jiud.annotation.SysLog;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

@Aspect
@Component
public class SysLogAspect {

    protected Logger logger = LoggerFactory.getLogger(SysLogAspect.class);
    private final static String LINE_SEPARATOR = System.lineSeparator();

    @Pointcut("@annotation(com.jiud.annotation.SysLog)")
    public void webLog(){}

    @Around("webLog()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
        long startTime = System.currentTimeMillis();
        Object result = null;
        try {
            result = proceedingJoinPoint.proceed();
        } catch (Exception e) {
            logger.error("FAILED");
            logger.error("FAILED Message：" + e.getMessage());
        } finally {
            try {
                MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
                ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                HttpServletRequest request = attributes.getRequest();
                logger.info(signature.getDeclaringTypeName() + "." + signature.getName());
                logger.info(JSON.toJSONString(proceedingJoinPoint.getArgs()));
                logger.info(JSON.toJSONString(result));
                logger.info(String.valueOf(System.currentTimeMillis()));
                logger.info(request.getRequestURL().toString());
                logger.info(getClientRealIp(request));
                logger.info(new Date().toString());
                logger.info(request.getHeader("User-Agent"));
            } catch (Exception ex) {
                logger.error("LogAspect 操作失败：" + ex.getMessage());
                ex.printStackTrace();
            }
        }
        logger.info("Response Args  : "+ JSON.toJSONString(result) );
        logger.info("Time-Consuming : ms " + (System.currentTimeMillis() - startTime));
        return result;
    }

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable{
        //logger.info("===========================================================");
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String methodDesription = getAspectLogDescription(joinPoint);
        // 打印请求相关参数
        logger.info("**************************** Start ****************************");
        // 打印请求url
        logger.info("URL            : " + request.getRequestURL().toString());
        // 打印描述信息
        logger.info("Description    : " + methodDesription);
        // 打印 Http method
        logger.info("Http Method    : " + request.getMethod());
        // 打印调用Controller的全路径以及执行方法
        logger.info("Class Method   : " + joinPoint.getSignature().getDeclaringTypeName()+" "+joinPoint.getSignature().getName());
        // 打印请求的IP
        //logger.info("IP             : " + request.getRemoteAddr());
        logger.info("IP             : " + getClientRealIp(request));
        // 打印请求入参
        logger.info("Request Args   : " + JSON.toJSONString(joinPoint.getArgs()));
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

    @After("webLog()")
    public void doAfter() throws Throwable{
        logger.info("**************************** End ****************************" + LINE_SEPARATOR);
    }

    public String getAspectLogDescription(JoinPoint joinPoint){
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        SysLog webLog = method.getAnnotation(SysLog.class);
        return webLog.description();
    }

}
