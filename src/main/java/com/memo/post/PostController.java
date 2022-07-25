package com.memo.post;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.memo.post.bo.postBO;
import com.memo.post.model.Post;

@RequestMapping("/post")
@Controller
public class PostController {
	@Autowired
	private postBO postBO;
	/**
	 * 글목록
	 * @param model
	 * @return
	 */
	@RequestMapping("/post_list_view")
	public String postListView(Model model) {
		List<Post> postList = postBO.getPostList();
		model.addAttribute("postList", postList);
		model.addAttribute("viewName", "post/post_list");
		return "template/layout";
	}
	/**
	 * 글쓰기
	 * @param model
	 * @return
	 */
	@RequestMapping("/post_create_view")
	public String postCreateView(Model model) {
		model.addAttribute("viewName", "post/post_create");
		return "template/layout";
	}
	@RequestMapping("/post_detail_view")
	public String postDetailView(
			@RequestParam("postId")int postId,
			Model model) {
		   Post post = postBO.getPostById(postId);
		model.addAttribute("post", post);
		model.addAttribute("viewName","/post/post_detail");
		return"template/layout";
		
	}

}
