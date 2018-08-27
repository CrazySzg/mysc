package com.example.xzq.demo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.xzq.demo.pojo.User;

@RestController
public class ConsumerController {

	@Autowired
	HelloService service;
	
	@GetMapping("/feign-consumer")
	public String helloConsumer() {
		return service.hello();
	}
	
	@GetMapping("/getmap")
	public Map<String,String> getMap() {
		return service.returnMap();
	}
	
	@GetMapping("/getList")
	public List<User> getList() {
		return service.returnList();
	}
	
	@PostMapping("/getList1")
	public String postList(@RequestBody List<String> list) {
		return service.postList(list);
	}
	
	@GetMapping("/getUser")
	public User returnUser(String username,String password) {
		return service.returnUser(username, password);
	}
	
	@GetMapping("/set-user")
	public String setUser(String username,String password) {
		return service.setuser(new User(username,password));
	}
	
	
	@GetMapping("/test-header")
	public String testHeader(String username,String password) {
		User user = new User(username,password);
		String accesstoken = "1234556";
		return service.testHeader(accesstoken,user);
	}
	
	
	@GetMapping("/send-map")
	public String sendMap() {
		Map<String,User> map = new HashMap<String, User>();
		User user1 = new User("1","1");
		User user2 = new User("2","2");
		User user3 = new User("3","3");
		User user4 = new User("4","4");
		map.put("1", user1);
		map.put("2", user2);
		map.put("3", user3);
		map.put("4", user4);
		return service.sendMap(map);
	}
}
