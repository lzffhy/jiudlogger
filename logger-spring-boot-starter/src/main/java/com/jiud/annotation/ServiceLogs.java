package com.jiud.annotation;

import java.lang.annotation.*;

/**
 * @version: V1.0
 * @author: fendo
 * @className: ControllerLogs
 * @packageName: com.xxxx.commons.web.annotation
 * @description: Service日志记录
 * @data: 2018-05-21 15:58
 **/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ServiceLogs {

    /**
     * 描述
     */
    String description() default "";
}
