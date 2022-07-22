package com.memo.post;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.memo.post.bo.postBO;

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

}
