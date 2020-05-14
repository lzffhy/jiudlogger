package com.lzffhy.hello;
import com.jiud.JiudLoggerProperties;
import com.jiud.annotation.ControllerLogs;
import com.jiud.annotation.ServiceLogs;
import com.jiud.annotation.SysLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    Logger log = LoggerFactory.getLogger(HelloController.class);
    @Autowired
    public JiudLoggerProperties jiudLoggerProperties;


    @GetMapping("hello")
    @SysLog(description = "这是hello")
    public String hello() throws Exception {

        log.warn("warn");
        log.error("error");
        log.trace("trace");
        log.info("info");
        return jiudLoggerProperties.getType() + jiudLoggerProperties.getDescribe();
    }


    @SysLog(description = "say hello")
    public void sayHello() {
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
