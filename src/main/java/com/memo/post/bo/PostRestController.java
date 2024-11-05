package com.memo.post.bo;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/*
DB연동 : View영역 <--> Controller영역(Domain) <--> Service(BO)영역 <--> Repository영역(Mapper) <--> DB영역 
*/

// View영역

/*
<Response 방법 - 서버 기준>
@Controller + return String => ViewResolver => HTML 파일 렌더링(Model) => HTML
@Controller + @ResponseBody return String => HTTPMessageConverter => HTML
@Controller + @ResponseBody return 객체(map, list) => HTTPMessageConverter => jackson => JSON
@RestController return map => JSON
*/

@RequestMapping("/post")
@RestController
public class PostRestController {
	
	

	@PostMapping("/create")
	public Map<String, Object> create(
			// 필수 파라미터 불러오기1 : value, required 생략 (추천) - null이 아닌 column - lesson03 참고
			@RequestParam("subject") String subject,
			@RequestParam("content") String content,
			// 비필수 파라미터 불러오기2 : 기본값 설정 (추천) - lesson03 참고
			@RequestParam(value = "file", required = false) MultipartFile file) {
		
		// DB INSERT
		
		
		
		// Response(응답값)
		
		
	}
}
