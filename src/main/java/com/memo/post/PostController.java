package com.memo.post;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
	public String postListView(Model model, HttpSession session) {
		
		// 로그인 여부 확인(권한 검사) breakpoint 1
		Integer userId = (Integer)session.getAttribute("userId"); // UserRestContoller에서 type 확인 + null 가능성이 있으므로 wrapper class로
		if(userId == null) {
			// 로그인 페이지로 이동
			return "redirect:/user/sign-in-view";
		}
		
		// DB SELECT => 본인((로그인한 사람)이 쓴 글 : session을 통해 수령 breakpoint 2
		List<Post> postList = postBO.getPostListByUserId(userId);
		
		
		// MODEL 데이터 삽입 - breakpoint
		// Controller가 Model에 데이터를 삽입
		// HTML(VIEW)가 Model에서 꺼내서 사용
		model.addAttribute("postList", postList);
		
		
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
	
}
