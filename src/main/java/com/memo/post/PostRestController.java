package com.memo.post;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.memo.post.bo.PostBO;
import com.memo.post.entity.PostEntity;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpSession;

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
	
	@Autowired // annotation
	private PostBO postBO; // DI(Dependency Injection)의존성 주입
	
	/**
	 * 글쓰기 API
	 * @param subject
	 * @param content
	 * @param file
	 * @param session
	 * @return
	 */
	@PostMapping("/create")
	// http:localhost/post/create
	public Map<String, Object> create(
			// 필수 파라미터 불러오기1 : value, required 생략 (추천) - null이 아닌 column - lesson03 참고
			@RequestParam("subject") String subject,
			@RequestParam("content") String content,
			// 비필수 파라미터 불러오기2 : 기본값 설정 (추천) - lesson03 참고
			@RequestParam(value = "file", required = false) MultipartFile file,
			HttpSession session) {
		
		// 글쓴이 번호 추출(session) - 비로그인일 경우 에러 발생 - breakpoint
		int userId = (int)session.getAttribute("userId");
		String userLoginId = (String)session.getAttribute("userLoginId");
		
		
		// DB INSERT (Entity 사용) - 성공한 행 수 - breakpoint
		 int rowCount = postBO.addPost(userId, userLoginId, subject, content, file);
		// int rowCount = 0; // 임시로 `실패` 형식 -> parameter 검증 작업(그림 유무에 따른 결과 확인 : null 유무)
		
		// Response(응답값) - breakpoint
		Map<String, Object> result = new HashMap<>();
		if(rowCount > 0) {
			result.put("code", 200);
			result.put("result", "성공");			
		} else {
			result.put("code", 500);
			result.put("error_message", "글을 저장하는데 실패했습니다. 관리자에게 문의하세요.");						
		}
		
		return result;
		
	}
}
