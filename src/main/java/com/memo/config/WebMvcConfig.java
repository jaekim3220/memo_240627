package com.memo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.memo.common.FileManagerService;

@Configuration // Spring Bean 등록
public class WebMvcConfig implements WebMvcConfigurer{

	// 지정(예언)한 imagePath와 서버에 업로드 된 실제 imagePath와 매핑
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry
		.addResourceHandler("/images/**") // path(주소) : http://localhost/images/aaaa_1730889245989/idPhoto.jpg
		.addResourceLocations("file:///" + FileManagerService.FILE_UPLOAD_PATH); // 실제 이미지 위치 : FileManagerService에서 생성한 위치
	}
	
}
