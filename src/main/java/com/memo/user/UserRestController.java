package com.memo.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.memo.common.EncryptUtils;
import com.memo.user.bo.UserBO;
import com.memo.user.model.User;
import com.mysql.cj.xdevapi.Result;

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
	/**
	 * 회원가입 API
	 * @param loginId
	 * @param password
	 * @param name
	 * @param email
	 * @return
	 */
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
	
	@PostMapping("/sign_in")
	public Map<String, Object> signIn(
			@RequestParam("loginId")String loginId,
			@RequestParam("password")String password,
			HttpServletRequest request){
		
		//비밀번호 해싱
		String encryptPassword = EncryptUtils.md5(password);
		
		//loginId,해싱비번 => DB 셀렉트 => 존재유무
		User user =userBO.getUserByLoginIdPassword(loginId,encryptPassword);
		
		Map<String,Object> result = new HashMap<>();
		if(user != null) {// 성공
			//로그인 성공하면 세션에 정보를 담는다
			HttpSession session = request.getSession();
			session.setAttribute("userId",user.getId());
			session.setAttribute("userLoginId",user.getLoginId());
			session.setAttribute("userName",user.getName());
			result.put("result","success");
		}else {//실패
			//실패하면 실패 응답
			result.put("errorMessage","존재하지 않음");
		}
		//로그인 성공 유무 response
		
		
		
		return result;
	}

}
