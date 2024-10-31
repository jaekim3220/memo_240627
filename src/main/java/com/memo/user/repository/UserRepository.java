package com.memo.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.memo.user.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

	// 메서드 생성
	public UserEntity findByLoginId(String loginId);// 단건은 Optional. List는 Optional 불필요
}
