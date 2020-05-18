package com.lzffhy.hello;

import com.jiud.annotation.SysLog;
import org.springframework.stereotype.Service;

@Service
public class HelloService {

    @SysLog(description = "this is a service")
    public void say(String say) {
        throw new RuntimeException("there is an exception");
        //System.out.println("helloService say: " + say);
    }
}
