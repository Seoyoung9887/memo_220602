<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="d-flex justify-content-center">
   <div class="w-50">
      <h1>글 목록</h1>
      
      <table class="table table-hover">
          <thead>
               <tr>
                 <th>번호</th>
                 <th>제목</th>
                 <th>작성날짜</th>
                 <th>수정날짜</th>
               </tr>
          </thead>
          <tbody>
              <tr>
                <td>${id}</td>
                <td>${subject}</td>
                <td>${createdAt}</td>
                <td>${updatedAt}</td>
              </tr>
          </tbody>
      </table>
      
      <div class="float-right">
         <a href="/post/post_create_view" class="btn btn-primary">글쓰기</a>
      </div>
   </div>
</div>