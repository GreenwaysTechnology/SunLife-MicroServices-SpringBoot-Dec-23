package com.sunlife;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Supplier;

@SpringBootApplication
public class SpringCloudStreamApp {

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudStreamApp.class, args);
	}

	//producer which sends messages via functional
	//stringSupplier is function name , if you dont configure, then function name would be topic
	//name
	@Bean
	public Supplier<UUID> stringSupplier(){
		return ()->{
			 var uuid= UUID.randomUUID();
			 return uuid;
		};
	}
	//consumer-reading message from the kafka topic
	@Bean
	public Consumer<String> stringConsumer(){
		return result->{
			System.out.println("String Consumer => "+ result);
		};
	}

	@KafkaListener(id="randomUUid-topic",topics = "randomUUid-topic")
	public void listen(String result){
		System.out.println("listen "+ result);

	}

}
