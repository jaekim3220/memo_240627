package com.memo.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.memo.domain.Post;

@Mapper
public interface PostMapper {

	public List<Map<String, Object>> selectPostList();
	
	// input : int(userId)
	// output : List<Post>
	public List<Post> selectPostListByUserId(int userId);
}
