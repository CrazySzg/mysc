package com.example.xzq.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class HelloService {

	@Autowired
	private RestTemplate template;
	
	@HystrixCommand(fallbackMethod="helloFallback")
	public String helloService() {
		return template.getForEntity("http://HELLO-SERVICE/hello1", String.class).getBody();
	}
	
	public String helloFallback() {
		return "error";
	}
}
