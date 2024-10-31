package com.memo.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.memo.mapper.PostMapper;

/*
@Controller + return String => ViewResolver => HTML 파일 렌더링(Model) => HTML
@Controller + @ResponseBody return String => HTTPMessageConverter => HTML
@Controller + @ResponseBody return 객체(map, list) => HTTPMessageConverter => jackson => JSON
@RestController return map => JSON
*/

@Controller
public class TestController {

	
	@Autowired
	private PostMapper postMapper;
	
	@ResponseBody
	@GetMapping("/test1")
    // http:localhost:8080/test1
	public String test1() {
		return "<h1>동작 확인<h1>";
	}
	
	@ResponseBody
	@GetMapping("test2")
    // http:localhost:8080/test2
	public Map<String, Object>  test2() {
		Map<String, Object> map = new HashMap<>();
		map.put("a", 111);
		map.put("b", 222);
		return map;
	}
	
	@GetMapping("/test3")
    // http:localhost:80/test3
	public String test3() {
		return "test/test";
	}
	
	
	@ResponseBody
	@GetMapping("/test4")
    // http:localhost:80/test4
	public List<Map<String, Object>> test4() {
		return postMapper.selectPostList();
	}
	
}
