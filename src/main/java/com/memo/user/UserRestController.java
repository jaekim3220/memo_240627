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

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

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
	/**
	 * 회원가입
	 * @param loginId
	 * @param password
	 * @param name
	 * @param email
	 * @return
	 */
	@PostMapping("/sign-up")
	public Map<String, Object> signUp(
			@RequestParam("loginId") String loginId,
			@RequestParam("password") String password,
			@RequestParam("name") String name,
			@RequestParam("email") String email) {
		
		// md5 알고리즘 - hashing(복호화 불가로 암호화 아님) => 프로젝트에는 다른 암호화 알고리즘 사용
		// 아이디 : aaaa => 74b8733745420d4d33f80c4663dc5e5
		// 비밀번호 : aaaa => 74b8733745420d4d33f80c4663dc5e5
		// breakpoint 2
		String hashedPassword = EncryptUtils.md5(password);
		
		// DB INSERT
		UserEntity user = userBO.addUser(loginId, hashedPassword, name, email);
		
		// 응답 값 breakpoint 1
		Map<String, Object> result = new HashMap<>();
		if(user != null) {
			result.put("code", 200);
			result.put("result", "성공");
		} else {
			result.put("code", 500);
			result.put("error_message", "회원가입에 실패했습니다.");
		}
		return result;
	}
	
	
	@PostMapping("/sign-in")
	public Map<String, Object> signIn(
			@RequestParam("loginId") String loginId,
			@RequestParam("password") String password,
			HttpServletRequest request) {
		
		// DB SELECT breakpoint 2(데이터가 있는 경우 : user, 없는 경우 : null)
		UserEntity user = userBO.getUserEntityByLoginIdPassword(loginId, password);
		
		
		// 응답값 breakpoint 1(Console 창에서 쿼리문 확인)
		Map<String, Object> result = new HashMap<>();
		if(user != null) {
			// session에 사용자 정보를 담는다(사용자 각각 담는다) => 로그인한 사용자 만큼 생성됨
			HttpSession session = request.getSession(); // session 생성
			session.setAttribute("userId", user.getId()); // session에 삽입
			session.setAttribute("userLoginId", user.getLoginId()); // session에 삽입
			session.setAttribute("userName", user.getName()); // session에 삽입
			
			result.put("code", 200);
			result.put("result", "성공");
		} else {
			result.put("code", 300);
			result.put("error_message", "존재하지 않는 사용자입니다.");
		}
		return result;
	}
	
}
