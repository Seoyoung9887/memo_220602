<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="d-flex justify-content-center">
	<div class="w-50">
		<h1>글 상세/수정</h1>
		
		<input type="text" id="subject" class="form-control" placeholder="제목을 입력하세요" value="${post.subject}">
		<textarea id="content" class="form-control" placeholder="내용을 입력하세요" rows="15">${post.content}</textarea>
		
		<div class="d-flex justify-content-end my-4">
			<input type="file" id="file" accept=".jpg,.png,.gif,.jpeg">
		</div>
		
		<%-- 이미지가 있는 경우에만 노출 --%>
		<c:if test="${not empty post.imagPath}">
		<div class="image-area mb-3">
			<img src="${post.imagPath}" alt="업로드 이미지" width="400">
		</div>
		</c:if>
		
		<div class="d-flex justify-content-between">
			<button type="button" id="postDeleteBtn" class="btn btn-secondary" data-post-id="${post.id}">삭제</button>
			
			<div>
				<a href="/post/post_list_view" id="postListBtn" class="btn btn-dark">목록으로</a>
				<button type="button" id="saveBtn" class="btn btn-primary" data-post-id="${post.id}">저장</button>
			</div>
		</div>
	</div>
</div>

<script>
$(document).ready(function() {
	$('#saveBtn').on('click', function() {
		// validation
		let subject = $('#subject').val().trim();
		if (subject == "") {
			alert("제목을 입력하세요");
			return;
		}
		
		let content = $('#content').val().trim();
		if (content == "") {
			alert("내용을 입력하세요");
			return;
		}
		
		// 이미지 확장자 검증 - 파일이 업로드 된 경우
		let file = $('#file').val();  // 파일 경로만 가져온다.
		console.log(file); // C:\fakepath\20220629_140829.jpg
		if (file != "") {
			let ext = file.split(".").pop().toLowerCase(); // 파일경로에서 .으로 나누고 배열에 저장 후 마지막 문자열 가져오고 소문자로 변경
			//alert(ext);
			if ($.inArray(ext, ["gif", "jpeg", "jpg", "png"]) == -1) {
				alert("gif, jpeg, jpg, png 파일만 업로드 할 수 있습니다.");
				$("#file").val(""); // 파일을 비운다.
				return;
			}
		}
		
		let postId = $(this).data("post-id");
		//alert(postId);
		
		// 이미지를 보내야 하므로 form 객체를 구성한다.
		let formData = new FormData();
		formData.append("postId", postId);
		formData.append("subject", subject);
		formData.append("content", content);
		formData.append("file", $('#file')[0].files[0]);
		
		// ajax
		$.ajax({
			// request
			type:"PUT"
			, url: "/post/update"
			, data: formData
			, enctype: "multipart/form-data"  // 파일 업로드 위한 필수 설정
			, processData: false   // 파일 업로드 위한 필수 설정
			, contentType: false   // 파일 업로드 위한 필수 설정
			
			// response
			, success:function(data) {
				if (data.result == "success") {
					alert("수정 되었습니다.");
					location.reload(true); // 새로고침
				}
			}
			, error:function(e) {
				alert("메모 저장 실패");
			}
		});
	});
	
	$('#postDeleteBtn').on('click',function(){
		let postId = $(this).data('post-id');
		$.ajax({
			type:"DELETE"
			,url:"/post/delete"
			,data:{"postId":postId}
		    ,success:function(data){
		    	if(data.result == "success"){
		    		alert("삭제 되었습니다.");
		    		location.href = "/post/post_list_view";
		    	}else{
		    		alert(data.errorMessage);
		    	}
		    }
		    ,error:function(e){
		    	alert("메모를 삭제하는데 실패했습니다.")
		    }
		});
	});
	
});
</script>


