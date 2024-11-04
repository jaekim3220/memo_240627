package com.memo.post.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.memo.domain.Post;
import com.memo.mapper.PostMapper;

@Service
public class PostBO {

	
	@Autowired
	private PostMapper postMapper;
	
	// input : int(userId)
	// output : List<Post>
	public List<Post> getPostListByUserId(int userId) {
		return postMapper.selectPostListByUserId(userId);
	}
}
