package com.memo.post.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import com.memo.common.FileManagerService;
import com.memo.domain.Post;
import com.memo.mapper.PostMapper;

/*
DB연동 : View영역 <--> Controller영역(Domain) <--> Service(BO)영역 <--> Repository영역(Mapper) <--> DB영역 
*/

@Service
public class PostBO {

	
	@Autowired
	private PostMapper postMapper;
	
	@Autowired
	private FileManagerService fileManager;
	
	
	// input : int(userId)
	// output : List<Post>
	// @GetMapping("/post-list-view") 구현
	public List<Post> getPostListByUserId(int userId) {
		return postMapper.selectPostListByUserId(userId);
	}
	

	// input : userId, subject, content, file
	// output : int(성공한 행의 개수)
	// @PostMapping("/create") 구현
	public int addPost(int userId, String userLoginId,
			String subject, 
			String content, MultipartFile file) {
		
		String imagePath = null;
		// 파일이 있을 경우에만 업로드 => imagePath 추출 - breakpoint
		if(file != null) {
			imagePath = fileManager.uploadFile(file, userLoginId); // 파일이 있는 경우에만 파일을 넘긴다
		}
			
		// return 0;
		return postMapper.insertPost(userId, subject, content, imagePath);
	}
	
}
