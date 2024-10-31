package com.memo.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.memo.user.bo.UserBO;
import com.memo.user.entity.UserEntity;

@RestController
@RequestMapping("/user")
public class UserRestController {
	
	@Autowired
	private UserBO userBO;

	@GetMapping("/is-duplicate-id")
	public Map<String, Object> isDuplicatedId(
			@RequestParam("loginId") String loginId) {
		
		// DB SELECT breakpoint 2. Entity, Repository, XML, BO 생성 전 yml 업데이트
		UserEntity user = userBO.getUserEntityByLoginId(loginId);
		
		
		// 응답값 breakpoint 1(Console 창에서 쿼리문 확인)
		Map<String, Object> result = new HashMap<>();
		result.put("code", 200);
		result.put("is_duplicate_id", false); // (중복, 중복 없음 확인) true일 경우 중복(사용 불가), false일 경우 중복 없음(사용 가능)
		return result;
	}
}
