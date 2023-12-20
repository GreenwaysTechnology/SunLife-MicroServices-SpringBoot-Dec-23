package com.webclient;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
public class WebClientController {
    private final WebClient webClient;
    @Autowired
    private DiscoveryClient client;

    @Autowired
    public WebClientController(WebClient webClient) {
        this.webClient = webClient;
    }

    @GetMapping("/greet")
    public Mono<String> sayGreet() {
        URI uri = client.getInstances("hello-service").stream().map(si -> si.getUri()).findFirst().map(s -> s.resolve("/hello")).get();
        System.out.println(uri.getHost() + uri.getPort());
        return webClient.get().uri(uri).retrieve().bodyToMono(String.class);
    }
}
