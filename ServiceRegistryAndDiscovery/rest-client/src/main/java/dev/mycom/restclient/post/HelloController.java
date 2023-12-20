package dev.mycom.restclient.post;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

@RestController
public class HelloController {

    private final RestClient restClient;
    @Autowired
    private DiscoveryClient client;

    public HelloController() {
        restClient = RestClient.builder()
                .baseUrl("")
                .build();
    }

    @GetMapping("/greet")
    public String sayGreet() {
        URI uri = client.getInstances("hello-service").stream().map(si -> si.getUri()).findFirst()
                .map(s -> s.resolve("/hello")).get();
        System.out.println(uri.getHost() + uri.getPort());
        return restClient.get()
                .uri(uri)
                .retrieve()
                .body(String.class);
    }
}