package com.jiud.annotation;

import java.lang.annotation.*;

/**
 * @version: V1.0
 * @author: fendo
 * @className: ControllerLogs
 * @packageName: com.xx.commons.web.annotation
 * @description:  Controller日志记录
 * @data: 2018-05-21 15:58
 **/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ControllerLogs {

    /**
     * 描述
     */
    String description() default "";
}
