package com.memo.post.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.memo.common.FileManagerService;
import com.memo.domain.Post;
import com.memo.mapper.PostMapper;

import lombok.extern.slf4j.Slf4j;

/*
DB연동 : View영역 <--> Controller영역(Domain) <--> Service(BO)영역 <--> Repository영역(Mapper) <--> DB영역 
*/

@Service
@Slf4j // slf4j logger
public class PostBO {
	
	// log 사용법
	// 1)
	// private Logger log = LoggerFactory.getLogger(PostBO.class);
	
	// 2)
	// private Logger log = LoggerFactory.getLogger(this.getClass());
	
	

	
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
	

	// input : userId, postId
	// output : Post or null (단건)
	// @GetMapping("/post-detail-view") 구현
	public Post getPostByPostIdUserId(int postId, int userId) {
		return postMapper.selectPostByPostIdUserId(postId, userId);
	}
	
	
	// input : userLoginId(파일), postId, userId, subject, content, file
	// output : X
	// @PutMapping("/update")
	public void updatePostByPostIdUserId(String loginId, int postId, int userId, 
			String subject, String content, MultipartFile file) {
		
		// 기존 글을 추출
		// 1. 이미지 교체 시 기존 이미지 삭제
		// 2. 업데이트 대상 존재 확인
		Post post = postMapper.selectPostByPostIdUserId(postId, userId);
		if(post == null) { // null 검사로 NPE 방지
			// logging을 사용한 확인
			log.info("[글 수정] post is null. postId:{}, userId:{}", postId, userId);
			return;
		}

		log.info("[글 수정 테스트] postId:{}, userId:{}", postId, userId);
		
		
		// 파일 존재 시 새 이미지 업로드
		
		
		// DB Update
	}
	
}
