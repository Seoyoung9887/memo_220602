package com.memo.post.bo;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.memo.common.FileManagerService;
import com.memo.post.dao.PostDAO;
import com.memo.post.model.Post;

@Service
public class postBO {
	
	//private Logger logger = LoggerFactory.getLogger(PostBO.class);
		private Logger logger = LoggerFactory.getLogger(this.getClass());
	
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
	public List<Post> getPostList() {
		// TODO Auto-generated method stub
		return postDAO.selectPostList();
	}
	
	public Post getPostById(int postId) {
		return  postDAO.selectPostById(postId);
		
		
	}
	public int updatePost(int userId, String userLoginId, 
			int postId, String subject, String content, MultipartFile file) {
		
		// 기존에 저장된 글을 가져와본다.
		logger.info("updatePost  postId:{}, userId:{}", postId, userId);
		
		Post post = getPostById(postId);
		if (post == null) {
			logger.error("[update post] 수정할 게시물이 없습니다. postId:{}", postId);
			return 0;
		}
		
		// 업로드 할 파일이 없는 경우 수정하지 않음
		String imagPath = null;
		if (file != null) {
			// 업로드 할 파일이 있는지 본 후 서버에 업로드하고 imagePath를 받아온다.
			imagPath = fileManager.saveFile(userLoginId, file);
			
			// 새로 업로드된 이미지가 성공하면 기존 이미지 삭제(기존 이미지가 있을 때에만)
			if (imagPath != null && post.getImagPath() != null) {
				// 기존 이미지 삭제
				try {
					fileManager.deleteFile(post.getImagPath());
				} catch (IOException e) {
					logger.error("이미지 삭제 실패. postId:{}", postId);
				}
			}
		}
		
		// update
		
		return 0;
	
	}
	public void deletePostByPostIdAndUserId(int postId, int userId) {
		//삭제 전에 게시글을 먼저 가져와본다.(imagePath가 있을 수 있기 때문에)
		Post post = getPostById(postId);
		if(post == null) {
			logger.error("[delete post] 삭제할 게시글이 존재하지 않습니다. postId{}",postId);
			return;
		}
		//imagePath가 있으면 이미지 삭제(파일) 처리
		if(post.getImagPath() != null) {
			//이미지 삭제
			try {
				fileManager.deleteFile(post.getImagPath());
			} catch (IOException e) {
				logger.error("[delete post] image 삭제 실패. postId{} path{}",postId, post.getImagPath() );
			}
		}
		//db delete 행 삭제
		postDAO.deletePostByPostIdAndUserId(postId, userId);
	}

}
