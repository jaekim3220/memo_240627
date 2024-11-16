package com.memo.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.memo.domain.Post;

@Mapper
public interface PostMapper {

	public List<Map<String, Object>> selectPostList();
	
	// input : int(userId) + 페이징 정보
	// output : List<Post>
	/*
	`내림차순`으로 정렬한 postList를 `다음버튼` 클릭으로 내림차순으로 나열, 
 	`이전버튼` 클릭으로  이전 목록이 있는 postList를 역순으로 정렬해 나열하는 기능, 
 	아무 버튼을 누르지 않았을 때의 기능을 구현하기 위한 메서드
 	*/
	public List<Post> selectPostListByUserId(
			@Param("userId") int userId, 
			@Param("standardId") Integer standardId, 
			@Param("direction") String direction, 
			@Param("limit") int limit);
	
	
	/* 페이징 버튼 말소 기능을 위한 postId(글 id) 추출 메서드 */
	public int selectPostIdByUserIdAsSort(
			@Param("userId") int userId,
			@Param("sort") String sort);
	
	
	// input : params
	// output : int or void
	// @PostMapping("/create") 구현
	public int insertPost(
			@Param("userId") int userId,
			@Param("subject") String subject,
			@Param("content") String content,
			@Param("imagePath") String imagePath);
	
	
	// input : userId, postId
	// output : Post or null (단건)
	// @GetMapping("/post-detail-view") 구현
	public Post selectPostByPostIdUserId(@Param("postId") int postId, @Param("userId") int userId);

	
	// input : postId, subject, content, imagePath
	// output : int or void
	// @PutMapping("/update") 구현
	public void updatePostByPostId(
			@Param("postId") int postId,
			@Param("subject") String subject,
			@Param("content") String content,
			@Param("imagePath") String imagePath);
	
	
	// input: postId
	// output: int(삭제된 행 개수)
	// @DeleteMapping("/delete")
	public int deletePostByPostId(int postId);
	
}
