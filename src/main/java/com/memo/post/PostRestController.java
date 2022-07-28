package com.memo.post;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.memo.post.bo.postBO;

@RequestMapping("/post")
@RestController
public class PostRestController {
	@Autowired
	private postBO postBO; 	
	@PostMapping("/create")
	public Map<String,Object> create(
			@RequestParam("subject")String subject,
			@RequestParam("content")String content,
			@RequestParam(value="file", required = false) MultipartFile file,
			HttpSession session){
		Map<String, Object> result = new HashMap<>();
		result.put("result", "success");
		
		//글쓴이 정보를 세션에서 꺼낸다
		Object userIdObject = session.getAttribute("userId");
		if(userIdObject == null) {// 로그인 되어있지않음
			result.put("result", "error");
			result.put("errorMessage", "로그인 후 사용 가능합니다.");
			return result;
		}
		//로그인 된 사용자
		int userId = (int)userIdObject;
		String userLoginId = (String)session.getAttribute("userLoginId");
		    //글쓰기 db insert
 		  postBO.addpost(userId, userLoginId, subject, content, file);
		
		return result;
	}
	/**
	 * 수정 api
	 * @param postId
	 * @param subject
	 * @param content
	 * @param file
	 * @param session
	 * @return
	 */
	@PutMapping("/update")
	public Map<String, Object> update(
			@RequestParam("postId")int postId,
			@RequestParam("subject")String subject,
			@RequestParam("content")String content,
			@RequestParam(value="file", required=false) MultipartFile file,
			HttpSession session){
		// 로그인 된 사람만 도달했는지 검사 -> 나중에
				String userLoginId = (String)session.getAttribute("userLoginId");
				int userId = (int)session.getAttribute("userId");
				
				// db update
				postBO.updatePost(userId, userLoginId, postId, subject, content, file);
				
				// 성공 여부
				Map<String, Object> result = new HashMap<>();
				result.put("result", "success");
				
				return result;
		
	}
	@DeleteMapping("/delete")
	public Map<String, Object> delete(
			@RequestParam("postId")int postId,
			HttpSession session){
		
		int userId = (int)session.getAttribute("userId");
		//db delelte
		postBO.deletePostByPostIdAndUserId(postId, userId);
		
		Map<String, Object> result = new HashMap<>();
		result.put("result", "success");
		return result;
	}
	
	
	

}
