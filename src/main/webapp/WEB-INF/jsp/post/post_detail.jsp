<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<div class="d-flex justify-content-center">
   <div class="w-50">
        <h1>글상세/수정</h1>
        
        <input type="text" id="subject" class="form-control" placeholder="제목을 입력해주세요" value="${post.subject}">
        <textarea id="content" class="form-control" placeholder="내용을 입력하세요" rows="15" cols="" >${post.content}</textarea>
        
        <div class="d-flex justify-content-end my-4">
           <input type="file" id="file" accept=".jpg,.png,.gif,.jpeg">
        </div>
        <%--이미지가 있는 경우에만 노출 --%>
        <c:if test="${not empty post.imagPath}">
        <div class = "image-area mb-4">
            <img src="${post.imagPath}" alt="업로드 이미지" width="400">
        </div>
        </c:if>
        <div class="d-flex justify-content-between my-2">
              <button type="button" id="postSeleteBtn" class="btn btn-secondary">삭제</button>
           <div>
              <a href="/post/post_list_view" id="postListBtn" class="btn btn-dark">목록으로</a>
              <button type="button" id="saveBtn" class="btn btn-primary">저장</button>
           </div>
        </div>
   </div>
</div>    
<script>
$(document).ready(function(){
	
	
});
</script>				
					
				
				