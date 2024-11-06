package com.memo.common;

import java.io.File;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component // Spring Bean 등록
public class FileManagerService {

	// 실제 업로드 된 이미지가 저장될 서버 경로
	public static final String FILE_UPLOAD_PATH = "D:\\JAVA\\6_spring_project\\memo\\memo_workspace\\images/";
	
	
	// 메서드 생성
	// input : MultipartFile, userLoginId
	// output : String(이미지 경로)
	public String uploadFile(MultipartFile file, String loginId) {
		// 폴더(directory) 생성 - breakpoint
		// 예 : aaaa_17237482234/sun.png
		String directoryName = loginId + "_" + System.currentTimeMillis();
		String filePath = FILE_UPLOAD_PATH + directoryName + "/"; // D:\\JAVA\\6_spring_project\\memo\\memo_workspace\\images/aaaa_17237482234/
		
		// 폴더 생성 - breakpoint
		File directory = new File(filePath);
		if(directory.mkdir() == false) { // 폴더 생성 실패
			return null; // 폴더 생성 시 실패할 경우 경로를 null로 리턴(에러를 방지해 나머지 데이터가 DB에 들어가도록 설정)
		}
		
		// 파일 업로드
		
		return null; // 여기로 오면 성공
	}
	
}
