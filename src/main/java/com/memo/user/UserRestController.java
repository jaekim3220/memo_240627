package com.memo.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.memo.common.EncryptUtils;
import com.memo.user.bo.UserBO;
import com.memo.user.entity.UserEntity;

@RestController
@RequestMapping("/user")
public class UserRestController {
	
	@Autowired
	private UserBO userBO;
	
	/**
	 * 아이디 중복 확인
	 * @param loginId
	 * @return
	 */
	@GetMapping("/is-duplicate-id")
	public Map<String, Object> isDuplicatedId(
			@RequestParam("loginId") String loginId) {
		
		// DB SELECT breakpoint 2. Entity, Repository, XML, BO 생성 전 yml 업데이트
		UserEntity user = userBO.getUserEntityByLoginId(loginId);
		
		// 중복인 경우/아닌 경우 구분
		boolean isDuplicate = false; // false : 중복 아님
		if (user != null) { // 기존 값이 존재한다면 `중복`
			isDuplicate = true;
		}
		
		
		// 응답값 breakpoint 1(Console 창에서 쿼리문 확인)
		Map<String, Object> result = new HashMap<>();
		result.put("code", 200);
		result.put("is_duplicate_id", false); // (중복, 중복 없음 확인) true일 경우 중복(사용 불가), false일 경우 중복 없음(사용 가능)
		return result;
	}
	
	
	// AJAX 통신 성공, 실패 여부 (JSON) return
	@PostMapping("/sign-up")
	public Map<String, Object> signUp(
			@RequestParam("loginId") String loginId,
			@RequestParam("password") String password,
			@RequestParam("name") String name,
			@RequestParam("email") String email) {
		
		// md5 알고리즘 - hashing(복호화 불가로 암호화 아님) => 프로젝트에는 다른 암호화 알고리즘 사용
		// 아이디 : aaaa => 74b8733745420d4d33f80c4663dc5e5
		// 비밀번호 : aaaa => 74b8733745420d4d33f80c4663dc5e5
		String hashedPassword = EncryptUtils.md5(password);
		
		// DB INSERT
		
		
		// 응답 값 breakpoint 1
		Map<String, Object> result = new HashMap<>();
		result.put("code", 200);
		result.put("result", "성공");
		return result;
	}
	
}
