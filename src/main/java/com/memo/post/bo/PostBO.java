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
	
	
    // 페이징 정보 필드(limit) 
    // 화면에 보여줄 post의 개수 설정
    private static final int POST_MAX_SIZE = 3; 
    
	
	// input : int(userId)
	// output : List<Post>
	// @GetMapping("/post-list-view") 구현
	public List<Post> getPostListByUserId(int userId, Integer prevId, Integer nextId) {
		// 게시글 번호 : 1 9 10 | 11 12 13 | 14 15 16 | 17 18
		// 만약 14 15 16 페이지에 있을 때
		// 1) 다음버튼(nextId가 존재) : 16 보다 작은 3개 desc
		// 2) 이전버튼(prevId가 존재) : 14 보다 큰 3개 asc => 13 12 11으로 역순 나열 => BO에서 리스크 reverse 기능으로 11 12 13으로 정방향 나여
		// 3) 페이징 없음(nextId, prevId 모두 부재) : 최신 순서로 3개 desc
		
		// XML에서 하나의 쿼리로 만들기 위해 변수를 정제 - breakpoint
		
		// 이전버튼
		
		// 다음버튼
		
		// 페이징 없음
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
		
		// 기존 이미지(경로) 추출 - breakpoint
		// 1. 이미지 교체 시 기존 이미지 삭제
		// 2. 업데이트 대상 존재 확인
		// 기존 이미지 경로
		Post post = postMapper.selectPostByPostIdUserId(postId, userId);
		if(post == null) { // null 검사로 NPE 방지
			// logging을 사용한 확인
			log.info("[글 수정] post is null. postId:{}, userId:{}", postId, userId);
			return;
		}

		log.info("[글 수정 테스트] postId:{}, userId:{}", postId, userId);
		
		
		// 파일 존재 시 새 이미지 업로드 - breakpoint
		/*
		기존 글에 이미지가 부재
		- 파일 업로드 => 성공 시 DB 저장
				=> 실패 시 DB 저장 X

		기존 글에 이미지가 존재
		- 파일 업로드 => 성공 시 기존 이미지 제거 후 DB 저장
				=> 실패 시 기존 이미지 그대로, DB 저장 X
		*/
		String imagePath = null;
		if (file != null) { // 새로 업로드 할 이미지가 존재할 경우
			// 새로 업로드할 이미지 주소
			imagePath = fileManager.uploadFile(file, loginId);
			
			// 새로운 이미지 업로드 성공 && 기존 이미지가 존재 시 삭제
			if (imagePath != null && post.getImagePath() != null) {
				// 폴더, 이미지 제거(컴퓨터-서버에서)
				fileManager.deleteFile(post.getImagePath());
			}
		}
		
		
		// DB Update - breakpoint
		postMapper.updatePostByPostId(postId, subject, content, imagePath);
		
	}
	
	
	
	// 글 1개 삭제
	// input: postId, userId
	// output: X
	// @DeleteMapping("/delete")
	public void deletePostByPostIdUserId(int postId, int userId) {
		
		// 기존글 가져오기(postId, userId) => 이미지 존재 시 삭제 위해서
		// DB 행 삭제 전 이미지 경로 확인 - breakpoint
		Post post = postMapper.selectPostByPostIdUserId(postId, userId);
		
		if (post == null) {
			log.info("[글 삭제] post is null. postId:{}, userId:{}", postId, userId);
			return;
		}
		
		
		// DB 행 삭제 - breakpoint
		int rowCount = postMapper.deletePostByPostId(postId);
		
		
		// 기존글에 이미지가 있다면 폴더/파일 삭제 - breakpoint
		if (rowCount > 0 && post.getImagePath() != null) {
			// DB삭제 완료 && 기존글 이미지 존재 => 이미지 삭제
			fileManager.deleteFile(post.getImagePath());
		}
	}
	
}
