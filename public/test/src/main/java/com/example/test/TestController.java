package com.example.test;

import com.jiud.JiudLoggerProperties;
import com.jiud.annotation.SysLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private JiudLoggerProperties jiudLoggerProperties;

    @GetMapping("test")
    @SysLog(description = "this is test")
    public String test() {
        return jiudLoggerProperties.getType() + jiudLoggerProperties.getDescribe();
    }
}
