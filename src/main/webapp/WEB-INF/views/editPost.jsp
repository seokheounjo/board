<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title><c:choose>
    <c:when test="${not empty post.id}">글 수정</c:when>
    <c:otherwise>새 글 작성</c:otherwise>
  </c:choose></title>
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

<!-- 뒤로가기 -->
<c:choose>
    <c:when test="${not empty post.id}">
        <c:url var="backUrl" value="/posts/${post.id}" />
    </c:when>
    <c:otherwise>
        <c:url var="backUrl" value="/posts" />
    </c:otherwise>
</c:choose>
<a href="${backUrl}">← 돌아가기</a>

<h2><c:choose>
  <c:when test="${not empty post.id}">✏️ 글 수정</c:when>
  <c:otherwise>📝 새 글 작성</c:otherwise>
</c:choose></h2>

<!-- 등록/수정 Form -->
<c:choose>
  <c:when test="${not empty post.id}">
    <c:url var="formActionUrl" value="/posts/edit/${post.id}" />
  </c:when>
  <c:otherwise>
    <c:url var="formActionUrl" value="/posts/new" />
  </c:otherwise>
</c:choose>

<form action="${formActionUrl}" method="post">
    <input type="hidden" name="id" value="${post.id}" />

    <p>
      <label>제목</label><br>
      <input type="text" name="title" value="${post.title}" placeholder="제목" required />
    </p>

    <p>
      <label>내용</label><br>
      <textarea name="content" placeholder="내용" required>${post.content}</textarea>
    </p>

    <!-- 수정 시에만 작성자 ID 표시 (변경 불가) -->
    <c:if test="${not empty post.id}">
        <p>
          <label>작성자 ID</label><br>
          <input type="text" value="${post.userId}" readonly style="background-color: #f8f9fa;" />
          <input type="hidden" name="userId" value="${post.userId}" />
        </p>
    </c:if>

    <p>
      <label>게시판 ID</label><br>
      <input type="number" name="boardId" value="${post.boardId}" placeholder="게시판 ID" required />
    </p>

    <p>
      <button type="submit">
        <c:choose>
          <c:when test="${not empty post.id}">수정 완료</c:when>
          <c:otherwise>등록</c:otherwise>
        </c:choose>
      </button>
    </p>
</form>

</body>
</html>