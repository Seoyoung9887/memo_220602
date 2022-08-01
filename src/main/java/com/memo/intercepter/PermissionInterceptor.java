package com.memo.intercepter;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class PermissionInterceptor implements HandlerInterceptor {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws IOException {
		
		//세션이 있는지 확인한다=> 있으면 로그인 된 상태
		HttpSession session = request.getSession();
		String userLoginId = (String)session.getAttribute("userLoginId");
		
		//URL path 확인
		String uri = request.getRequestURI();
		logger.info("######### uri:{}",uri);
		//비로그인 &&/post => 로그인 페이지로 리다이렉트
		if(userLoginId == null && uri.startsWith("/post")){
			response.sendRedirect("/user/sign_in_view");
			return false;
		}
		//로그인 && /user => 글 목록 페이지로 리다이렉트
		if(userLoginId != null && uri.startsWith("/user")) {
			response.sendRedirect("/post/post_list_view");
			return false;
		}
		return true; // 요청된 path로 컨트롤러 수행
	}
	
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler
			,ModelAndView modelAndView){
		//URI 확인
		String uri = request.getRequestURI();
		logger.info("######## postHander:{}",uri);
		
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			Exception ex) {
		String uri = request.getRequestURI();
		logger.info("######## afterCompletion:{}",uri);
	}

}
