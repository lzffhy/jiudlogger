package com.jiud.listener;

import org.jboss.logging.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.context.event.ApplicationFailedEvent;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.GenericApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.core.ResolvableType;
import org.springframework.core.env.Environment;

public class ApplicationStartedEventListener implements GenericApplicationListener {

    private static final int DEFAULT_ORDER = Ordered.HIGHEST_PRECEDENCE + 10;

    private static Class<?>[] EVENT_TYPES = {ApplicationStartingEvent.class,
            ApplicationEnvironmentPreparedEvent.class, ApplicationPreparedEvent.class,
            ContextClosedEvent.class, ApplicationFailedEvent.class};

    private static Class<?>[] SOURCE_TYPES = {SpringApplication.class,
            ApplicationContext.class};


    @Autowired
    private Environment env;

    @Override
    public boolean supportsEventType(ResolvableType resolvableType) {
        return true;
    }

    @Override
    public boolean supportsSourceType(Class<?> sourceType) {
        return true;
    }

    @Override
    public int getOrder() {
        return DEFAULT_ORDER;
    }

//
//    @Override
//    public boolean supportsEventType(ResolvableType resolvableType) {
//        return isAssignableFrom(resolvableType.getRawClass(), EVENT_TYPES);
//    }


    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        String type = env.getProperty("jiudlogger.type");
        System.out.println("================type==================" + type);
        String describe = env.getProperty("jiudlogger.describe");
        MDC.put("jiudlogger_type", type);
        MDC.put("jiudlogger_describe", describe);
    }

    private boolean isAssignableFrom(Class<?> type, Class<?>... supportedTypes) {
        if (type != null) {
            for (Class<?> supportedType : supportedTypes) {
                if (supportedType.isAssignableFrom(type)) {
                    return true;
                }
            }
        }
        return false;
    }
}
