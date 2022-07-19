package com.memo.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.memo.common.EncryptUtils;
import com.memo.user.bo.UserBO;
import com.memo.user.model.User;

//데이터 API
@RequestMapping("/user")
@RestController
public class UserRestController {
	
	@Autowired
	private UserBO userBO;
	@RequestMapping("/user_list")
	public List<User> userList(){
		return userBO.getuser();
	}
	/**
	 * 아이디 중복확인
	 * @param loginId
	 * @return
	 */
	@RequestMapping("/is_duplicated_id")
	public Map<String , Object> isDuplicatedId(
			@RequestParam("loginId")String loginId){
		boolean existLoginId = userBO.existLoginId(loginId);
		Map<String, Object> result = new HashMap<>();
		result.put("result", existLoginId);
		
		return result;
	}
	@PostMapping("/sign_up")
	public Map<String , Object> signUp(
			@RequestParam("loginId")String loginId,
			@RequestParam("password")String password,
			@RequestParam("name")String name,
			@RequestParam("email")String email){
		
		//비밀번호 해싱(md5, SHA256, 512)
		String encryptPassword = EncryptUtils.md5(password);
		//db insert
		 userBO.addUser(loginId, encryptPassword, name, email);
		//결과 리턴
		Map<String, Object> result = new HashMap<>();
		result.put("result", "success");
		return result;
	}

}
