package com.example.xzq.demo;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.xzq.demo.pojo.User;

@FeignClient("${service}")//指定服务名service-id
public interface HelloService {

	@GetMapping("/hello")
	String hello();
	
	
	@GetMapping("/return-map")
	Map<String,String> returnMap();
	
	@GetMapping("/return-list")
	List<User> returnList();
	
	@PostMapping("/post-list")
	String postList(@RequestBody List<String> list);
	
	@GetMapping("/return-user")
	User returnUser(@RequestParam("username") String username,@RequestParam("password")String password);
	
	@GetMapping("/testHeader")
	String testHeader(@RequestHeader("accesstoken") String accesstoken,
			@RequestBody User user);
	
	@RequestMapping(value = "/setuser",method = RequestMethod.GET)
	String setuser(User user);
	
	@GetMapping(value = "/sendMap")
	String sendMap(@RequestBody Map<String,User> map);
	
}
