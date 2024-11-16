package com.memo.post;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.memo.domain.Post;
import com.memo.post.bo.PostBO;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/post")
public class PostController {
	
	@Autowired
	private PostBO postBO;

	/**
	 * 글 목록
	 * @param model
	 * @param session
	 * @return
	 */
	@GetMapping("/post-list-view")
	// http:localhost/post/post-list-view
	public String postListView(
			// 비필수 parameter
			@RequestParam(value = "prevId", required = false) Integer prevIdParam,
			@RequestParam(value = "nextId", required = false) Integer nextIdParam,
			Model model, HttpSession session) {
		
		// 로그인 여부 확인(권한 검사) breakpoint 1
		Integer userId = (Integer)session.getAttribute("userId"); // UserRestContoller에서 type 확인 + null 가능성이 있으므로 wrapper class로
		if(userId == null) {
			// 로그인 페이지로 이동
			return "redirect:/user/sign-in-view";
		}
		
		// DB SELECT => 본인((로그인한 사람)이 쓴 글 : session을 통해 수령 breakpoint 2
		List<Post> postList = postBO.getPostListByUserId(userId, prevIdParam, nextIdParam);
		int prevId = 0;
		int nextId = 0;
		
		// 공백 처리 - breakpoint
		if(postList.isEmpty() == false) { // postList가 비어있지 않을 때 페이징 정보 세팅
			// 버튼을 클릭하면 이전/다음 버튼에 따라 서로 다른 Id 값 할당
			nextId = postList.get(postList.size() - 1).getId(); // 가장 마지막 칸의 post 객체(행) 추출 + 해당 글 번호(id) 추출
			prevId = postList.get(0).getId(); // 첫 번째 칸 id
			
		}
		
		
		// MODEL 데이터 삽입 - breakpoint
		// Controller가 Model에 데이터를 삽입
		// HTML(VIEW)가 Model에서 꺼내서 사용
		model.addAttribute("postList", postList);
		model.addAttribute("nextId", nextId);
		model.addAttribute("prevId", prevId);
		
		
		return "post/postList";
	}
	
	
	/**
	 * 글쓰기 화면
	 * @return
	 */
	@GetMapping("/post-create-view") 
	// http:localhost/post/post-create-view
	public String postCreateView() {
		return "post/postCreate";
	}
	
	
	/**
	 * 글 상세 화면
	 * @param postId
	 * @param session
	 * @param model
	 * @return
	 */
	@GetMapping("/post-detail-view")
	// http:localhost/post/post-detail-view
	public String postDetailView(
			@RequestParam("postId") int postId, // postId와 일치하는 글 추출
			HttpSession session,
			Model model) {
		
		// DB DELECT(postId, userId) - breakpoint
		int userId = (int)session.getAttribute("userId"); // UserRestController에서 정의한 변수
		Post post = postBO.getPostByPostIdUserId(postId, userId);
		
		
		// MODEL 데이터 삽입 - breakpoint
		// Controller가 Model에 데이터를 삽입
		// HTML(VIEW)가 Model에서 꺼내서 사용
		model.addAttribute("post", post);
		
		return "post/postDetail";
	}
	
}
