package com.memo.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.memo.domain.Post;

@Mapper
public interface PostMapper {

	public List<Map<String, Object>> selectPostList();
	
	// input : int(userId)
	// output : List<Post>
	public List<Post> selectPostListByUserId(int userId);
	
	
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
	
}
