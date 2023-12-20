package com.hello;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreeterController {
    @Value("${eureka.instance.instance-id}")
    private String instanceId;

    @GetMapping("/hello")
    public String sayHello() {
        System.out.println(instanceId);
        return "Hello =>" + instanceId;
    }
}
