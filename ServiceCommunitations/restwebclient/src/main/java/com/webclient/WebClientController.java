package com.webclient;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
public class WebClientController
{
     private final WebClient webClient;

     @Autowired
     public WebClientController(WebClient webClient){
         this.webClient=webClient;
     }
    @GetMapping("/greet")
    public Mono<String> sayGreet(){

        return webClient.get().uri("/hello").retrieve().bodyToMono(String.class);
    }
}
