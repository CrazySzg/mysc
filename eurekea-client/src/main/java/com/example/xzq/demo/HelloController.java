package com.example.xzq.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.RequestEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.xzq.demo.pojo.User;

@RestController
public class HelloController {

	private final Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private DiscoveryClient client;
	
	@GetMapping("/hello")
	public String index() {
		ServiceInstance serviceInstance = client.getLocalServiceInstance();
		logger.info("/hello,host:" + serviceInstance.getHost() + ",service_id: " + serviceInstance.getServiceId());
		return "hello world";
	}
	
	
	@GetMapping("/hello1")
	public String index1() throws InterruptedException {
		ServiceInstance serviceInstance = client.getLocalServiceInstance();
		logger.info("/hello,host:" + serviceInstance.getHost() + ",service_id: " + serviceInstance.getServiceId());
		Thread.sleep(new Random().nextInt(100));
		return "hello world";
	}
	
	@GetMapping("/return-map")
	public Map<String,String> returnMap() {
		Map<String,String> map = new HashMap();
		map.put("1", "111");
		map.put("2", "222");
		map.put("3", "333");
		map.put("4", "444");
		return map;
	}
	
	@GetMapping("/return-list")
	public List<User> returnList() {
		List<User> list = new ArrayList<>();
		list.add(new User("111","111"));
		list.add(new User("222","222"));
		list.add(new User("333","333"));
		list.add(new User("444","444"));
		return list;
	}
	
	
	@PostMapping("/post-list")
	public String postList(@RequestBody List<String> list) {
		logger.info("the list content are : " + list);
		return "success-return-list";
	}
	
	
	@GetMapping("/return-user")
	public User returnUser(String username,String password) {
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		return user;
	}
	
	@RequestMapping(value = "/setuser",method = RequestMethod.GET)
	public String setuser(User user) {
		logger.info(user);
		return "success-set-user";
	}
	
	@PostMapping("/testHeader")
	String testHeader(@RequestHeader("accesstoken") String accesstoken,User user) {
		logger.info(user);
		logger.info(accesstoken);
		return "testHeader";
	}
		
	@PostMapping(value = "/sendMap")
	public String sendMap(@RequestBody Map<String,User> map) {
		return map.toString();
	}
}
