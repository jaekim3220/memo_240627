package com.memo.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

/*
@Controller + return String => ViewResolver => HTML 파일 렌더링(Model) => HTML
@Controller + @ResponseBody return String => HTTPMessageConverter => HTML
@Controller + @ResponseBody return 객체(map, list) => HTTPMessageConverter => jackson => JSON
@RestController return map => JSON JSON (API 일 경우)
*/

@Controller // view 영역
@RequestMapping("/user")
public class UserController {

	/**
	 * 
	 * @return
	 */
	@GetMapping("/sign-up-view")
	// http:localhost/user/sign-up-view
	public String signUpView() {
		// 가운데 Layout(section) 조각만 내려주면
		// 전체 Layout과 함께 구성된다
		return "user/signUp";
	}
	
	
	/**
	 * 
	 * @return
	 */
	@GetMapping("/sign-in-view")
	// http:localhost/user/sign-in-view
	public String signInView() {
		// 가운데 Layout(section) 조각만 내려주면
		// 전체 Layout과 함께 구성된다
		return "user/signIn";
	}
	
	
	@GetMapping("/sign-out")
	public String signOut(HttpSession session) {
		// session 내용 초기화
		session.removeAttribute("userId");
		session.removeAttribute("userLoginId");
		session.removeAttribute("userName");
		
		// 로그인 페이지로 이동(redirect)
		return "redirect:/user/sign-in-view";
	}
	
	
}
