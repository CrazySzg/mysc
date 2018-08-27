package com.example.xzq.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekeaServerApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(EurekeaServerApplication.class)
			.web(true)
			.run(args);
	}
}
