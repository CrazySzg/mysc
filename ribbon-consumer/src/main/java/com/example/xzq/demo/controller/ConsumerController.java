package com.example.xzq.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.xzq.demo.pojo.User;
import com.example.xzq.demo.service.HelloService;

@RestController
public class ConsumerController {

	private final Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private RestTemplate template;
	
	@Autowired
	private HelloService service;
	
	@GetMapping("/ribbon-consumer")
	public String helloConsumer() {
		String body = template.getForEntity("http://HELLO-SERVICE/hello", String.class).getBody();
		return body;
	}
	
	@GetMapping("/test-hystrix")
	public String helloService() {
		return service.helloService();
	}
	
	@GetMapping("/get-map")
	public Map<String,String> returnMap() {
		Map map = template.getForEntity("http://HELLO-SERVICE/return-map", Map.class).getBody();
		return map;
	}
	
	@GetMapping("/get-list")
	public List<User> returnList() {
		List<User> list = template.getForEntity("http://HELLO-SERVICE/return-list", List.class).getBody();
//		List<String> list = template.exchange("http://HELLO-SERVICE/return-list", HttpMethod.GET,null, new ParameterizedTypeReference<List<String>>() {
//		}).getBody();
		return list;
	}
	
	
	@GetMapping("/get-list1")
	public String postList() {
		List<String> list = new ArrayList<>();
		list.add("111");
		list.add("222");
		list.add("333");
		list.add("444");
		String result = template.postForObject("http://HELLO-SERVICE/post-list", list, String.class);
		return result;
	}
	
	
	@GetMapping("/get-user")
	public User returnUser(String username,String password) {
		Map<String,String> params = new HashMap<>();
		params.put("username", "xuezhiqiang");
		params.put("password", "12345");
		User user = template.getForObject("http://HELLO-SERVICE/return-user?username={username}&password={password}", User.class, params);
		return user;
	}
}
