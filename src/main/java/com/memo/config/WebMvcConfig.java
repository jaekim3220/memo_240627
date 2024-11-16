package com.memo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.memo.common.FileManagerService;
import com.memo.interceptor.PermissionInterceptor;

import lombok.RequiredArgsConstructor;

@Configuration // Spring Bean 등록
@RequiredArgsConstructor // 생성자 상속
public class WebMvcConfig implements WebMvcConfigurer{
	
	
	// 인터셉터 상속
	private final PermissionInterceptor interceptor;
	
	// 인터셉터 설정
	// Override : 상위 클래스가 가지고 있는 메서드를 하위 클래스가 재정의해서 사용
	// 즉, 상속받은 메서드의 내용을 변경
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry
		.addInterceptor(interceptor)
		.addPathPatterns("/**") // 모든 주소 설정
		.excludePathPatterns("/css/**", "/img/**", "/images/**", "/user/sign-out"); // 예외 설정
	}
	

	// 지정(예언)한 imagePath와 서버에 업로드 된 실제 imagePath와 매핑
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry
		.addResourceHandler("/images/**") // path(주소) : http://localhost/images/aaaa_1730889245989/idPhoto.jpg
		.addResourceLocations("file:///" + FileManagerService.FILE_UPLOAD_PATH); // 실제 이미지 위치 : FileManagerService에서 생성한 위치
	}
	
}
