package com.lzffhy.hello;

import com.jiud.JiudLoggerProperties;
import com.jiud.annotation.SysLog;
import com.jiud.util.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    Logger log = LoggerFactory.getLogger(HelloController.class);
//    @Autowired
//    public JiudLoggerProperties jiudLoggerProperties;

    @Autowired
    private HelloService helloService;

    @GetMapping("hello/{param}")
    //@SysLog(description = "这是hello")
    public String hello(@PathVariable  String param) throws Exception {
    sayHello();
    helloService.say("this is a service");
//        log.warn("warn");
//        log.error("error");
//        log.trace("trace");
//        log.info("info");
        return /*jiudLoggerProperties.getSplit() + */"======================" + param;
    }


    @SysLog(description = "say hello")
    public void sayHello() {
        Log.info("=============================================");
        System.out.println("say hello");
    }

    @GetMapping("hello1")
    @SysLog(description = "这是hello1")
    public String hello1() {
        if (b()) {
            throw new RuntimeException("there is a exception");
        }
        return "hello1";
    }

    public boolean b () {
        return true;
    }
}
