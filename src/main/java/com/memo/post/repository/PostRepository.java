package com.memo.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.memo.post.entity.PostEntity;

public interface PostRepository extends JpaRepository<PostEntity, Integer> {

	
}
