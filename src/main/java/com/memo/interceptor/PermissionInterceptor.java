package com.memo.interceptor;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class PermissionInterceptor implements HandlerInterceptor {

	
	// true 리턴 시 수행, false 리턴 시 수행 불가(
	
	// Override : 상위 클래스가 가지고 있는 메서드를 하위 클래스가 재정의해서 사용
	// 즉, 상속받은 메서드의 내용을 변경
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws IOException {
		
		// 요청 url path - breakpoint
		String uri = request.getRequestURI();
		log.info("[!!!!!! preHandle] uri:{}", uri);
		
		// 로그인 여부 확인을 위한 userId 추출 => session - breakpoint
		HttpSession session = request.getSession();
		Integer userId = (Integer)session.getAttribute("userId");
		
		// /post로 시작하고 비로그인일 경우 => 로그인 페이지로 이동, 컨트롤러 수행 방지 - breakpoint
		if(uri.startsWith("/post") && userId == null) {
			// redirect
			response.sendRedirect("/user/sign-in-view");
			
			// 원래 가져고 했던 컨트롤러 수행 방지
			return false;
		}
		
		// /user로 시작하면서 로그인일 경우 => 글 목록 이동, 컨트롤러 방지 - breakpoint
		if(uri.startsWith("/user") && userId != null) {
			// redirect
			response.sendRedirect("/post/post-list-view");
			
			// 원래 가져고 했던 컨트롤러 수행 방지
			return false;
		}
		
		return true;
	}
	
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, 
			Object handler, ModelAndView mav) {
		
		// view와 model이 있다는 것은 html이 해석 되기 전임을 의미
		log.info("[!!!!!! postHandle]");
	}
	
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, 
			Object handler, Exception ex) {

		// html이 렌더링이 끝난 상태
		log.info("[!!!!!! afterCompletion]");
	}
	
	
}
