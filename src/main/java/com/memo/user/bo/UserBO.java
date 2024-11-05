package com.memo.user.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import com.memo.common.EncryptUtils;
import com.memo.user.entity.UserEntity;
import com.memo.user.repository.UserRepository;

/*
DB연동 : View영역 <--> Controller영역(Domain) <--> Service(BO)영역 <--> Repository영역(Mapper) <--> DB영역 
*/

//Service(BO)영역

@Service // Spring Bean 등록
public class UserBO {

	@Autowired
	private UserRepository userRepository;
	
	// 컨트롤러한테
	// input : loginId
	// output : UserEntity or null (단건)
	public UserEntity getUserEntityByLoginId(String loginId) {
		return userRepository.findByLoginId(loginId); // 없는 메서드
	}
	
	
	// @PostMapping("/sign-up") DB INSET 용
	// input : 파라미터 4개
	// output : UserEneity
	public UserEntity addUser(
			String loginId, String password, 
			String name, String email) {
		
		return userRepository.save(
				UserEntity.builder()
				.loginId(loginId)
				.password(password)
				.name(name)
				.email(email)
				.build()); // Parameter를 Repository에 저장
	}
	
	
	// @PostMapping("/sign-in") 구현
	// input : loginId, password
	// output : UserEntity
	public UserEntity getUserEntityByLoginIdPassword(String loginId, String password) {
		// 비밀번호 hashing
		String hashedPassword = EncryptUtils.md5(password);
		
		// 조회
		return userRepository.findByLoginIdAndPassword(loginId, hashedPassword);
	}
	
}
