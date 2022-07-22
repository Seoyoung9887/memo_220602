package com.memo.post.dao;

import java.util.Date;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostDAO {
	public void insertPost(
			@Param("userId")int userId,
			@Param("subject")String subject,
			@Param("content")String content,
			@Param("imagePath")String imagePath);
	
	
}
