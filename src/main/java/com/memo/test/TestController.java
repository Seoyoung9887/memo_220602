package com.memo.test;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {
	
	@ResponseBody
	@RequestMapping("/test/1")
	public String test1() {
		return "Hello world";
	}
	
	@ResponseBody
	@RequestMapping("/test/2")
	public Map<String,Object> test2(){
		Map<String,Object> map = new HashMap<>();
		map.put("aaa", 123);
		return map;
	}
	
	@RequestMapping("/test/3")
	public String test3() {
		return"test/test";
	}
	
	
}
