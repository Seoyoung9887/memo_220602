package com.memo.post.bo;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.memo.common.FileManagerService;
import com.memo.post.dao.PostDAO;

@Service
public class postBO {
	@Autowired
	private FileManagerService fileManager;
	@Autowired
	private PostDAO postDAO;
	public void addpost(int userId, String userLoginId, String subject, String content, MultipartFile file) {
		
		String imagePath = null;
		//파일이 있으면 파일 업로드 => path 리턴 받음
		if(file != null) {
			  imagePath = fileManager.saveFile(userLoginId, file);
		}
		//dao insert
		postDAO.insertPost(userId, subject, content, imagePath);
	}
	
	

}
