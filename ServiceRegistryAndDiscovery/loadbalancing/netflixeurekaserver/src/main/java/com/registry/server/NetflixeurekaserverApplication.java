package com.registry.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class NetflixeurekaserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(NetflixeurekaserverApplication.class, args);
	}

}
