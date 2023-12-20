package com.resttemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@RestController
public class HelloController {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private DiscoveryClient client;

    @GetMapping("/greet")
    public ResponseEntity<String> sayGreet() {
        URI uri = client.getInstances("hello-service").stream().map(si -> si.getUri()).findFirst()
                .map(s -> s.resolve("/hello")).get();
        System.out.println(uri.getHost() + uri.getPort());
        ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
        return response;
    }
}
