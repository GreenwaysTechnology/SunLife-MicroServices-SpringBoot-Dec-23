package com.hello;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreeterController {

    //@Value("${spring.cloud.consul.discovery.instanceId}")
    private String instanceId;

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello Consul";
    }
}
