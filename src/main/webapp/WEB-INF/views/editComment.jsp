<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>댓글 수정</title>
      <style>
          body {
              font-family: Arial, sans-serif;
              padding: 40px;
              background-color: #f9f9f9;
          }
          h1 {
              color: #333;
          }
          .search-bar {
              margin-bottom: 20px;
          }
          .search-bar form input[type="text"] {
              padding: 5px;
              width: 300px;
          }
          .search-bar form button {
              padding: 6px 10px;
          }
          .actions {
              margin-bottom: 10px;
          }
          .actions a {
              margin-right: 10px;
              text-decoration: none;
              color: #007bff;
          }
          table {
              width: 100%;
              border-collapse: collapse;
              background: #fff;
              margin-top: 10px;
          }
          th, td {
              border: 1px solid #ddd;
              padding: 10px;
              text-align: center;
          }
          th {
              background-color: #f0f0f0;
          }
          .pagination {
              margin-top: 20px;
          }
          .pagination a {
              margin: 0 5px;
              text-decoration: none;
              color: #333;
          }
          .pagination a.active {
              font-weight: bold;
              color: #007bff;
          }
      </style>
</head>
<body>

<c:url var="backUrl" value="/posts/${comment.post.id}" />
<a href="${backUrl}">← 게시글로 돌아가기</a>
<h3>댓글 수정</h3>

<c:url var="editCommentUrl" value="/comments/edit/${comment.id}" />
<form action="${editCommentUrl}" method="post">
  <p><input type="text" name="title" value="${comment.title}" placeholder="댓글 제목 (선택사항)" /></p>
  <p><textarea name="content" placeholder="댓글 내용" required>${comment.content}</textarea></p>
  <p><button type="submit">수정 완료</button></p>
</form>

</body>
</html>