package com.openfeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "hello-service",url="http://localhost:8081")
public interface HelloServiceFeignClient {
    //api
    @GetMapping("/hello")
    ResponseEntity<String> hello();
}
