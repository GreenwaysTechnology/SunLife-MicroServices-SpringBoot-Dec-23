package com.webclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class RestwebclientApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestwebclientApplication.class, args);
	}

}
