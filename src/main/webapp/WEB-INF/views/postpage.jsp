<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>게시글 상세</title>
  <style>
      body {
          font-family: Arial, sans-serif;
          padding: 40px;
          background-color: #f9f9f9;
      }
      .header {
          display: flex;
          justify-content: space-between;
          align-items: center;
          margin-bottom: 20px;
      }
      .post-content {
          background: white;
          padding: 20px;
          border-radius: 8px;
          margin-bottom: 20px;
      }
      .post-meta {
          color: #666;
          font-size: 14px;
          margin-bottom: 15px;
          border-bottom: 1px solid #eee;
          padding-bottom: 10px;
      }
      .post-stats {
          display: flex;
          gap: 15px;
          margin: 10px 0;
      }
      .post-actions {
          margin: 20px 0;
          display: flex;
          gap: 10px;
          align-items: center;
      }
      .like-button {
          background: none;
          border: 2px solid #ff6b6b;
          color: #ff6b6b;
          padding: 10px 20px;
          border-radius: 25px;
          cursor: pointer;
          font-size: 16px;
          transition: all 0.3s;
      }
      .like-button.liked {
          background: #ff6b6b;
          color: white;
      }
      .like-button:hover {
          background: #ff6b6b;
          color: white;
      }
      .btn {
          padding: 8px 16px;
          text-decoration: none;
          border: none;
          border-radius: 4px;
          cursor: pointer;
          font-size: 14px;
      }
      .btn-primary {
          background-color: #007bff;
          color: white;
      }
      .btn-secondary {
          background-color: #6c757d;
          color: white;
      }
      .btn-danger {
          background-color: #dc3545;
          color: white;
      }
      .comment-section {
          background: white;
          padding: 20px;
          border-radius: 8px;
          margin-top: 20px;
      }
      .comment-item {
          border: 1px solid #eee;
          padding: 15px;
          margin-bottom: 10px;
          border-radius: 5px;
          background: #fafafa;
      }
      .comment-meta {
          color: #666;
          font-size: 12px;
          margin-bottom: 5px;
      }
      .comment-actions {
          margin-top: 10px;
      }
      .comment-form {
          background: #f8f9fa;
          padding: 15px;
          border-radius: 5px;
          margin-top: 20px;
      }
      .comment-form input, .comment-form textarea {
          width: 100%;
          padding: 8px;
          margin: 5px 0;
          border: 1px solid #ddd;
          border-radius: 4px;
      }
  </style>
  <script>
  function toggleLike() {
      const postId = ${postpage.id};
      const contextPath = '${pageContext.request.contextPath}';

      fetch(contextPath + '/likes/toggle/' + postId, {
          method: 'POST',
          headers: {
              'Content-Type': 'application/json',
          }
      })
      .then(response => response.json())
      .then(data => {
          if (data.success) {
              const likeButton = document.getElementById('likeButton');
              const likeCount = document.getElementById('likeCount');

              if (data.liked) {
                  likeButton.classList.add('liked');
                  likeButton.innerHTML = '❤️ 좋아요 ' + data.likeCount;
              } else {
                  likeButton.classList.remove('liked');
                  likeButton.innerHTML = '🤍 좋아요 ' + data.likeCount;
              }

              likeCount.textContent = data.likeCount;
          } else {
              alert(data.message || '오류가 발생했습니다.');
              if (data.message === '로그인이 필요합니다.') {
                  window.location.href = contextPath + '/users/login';
              }
          }
      })
      .catch(error => {
          console.error('Error:', error);
          alert('오류가 발생했습니다.');
      });
  }
  </script>
</head>
<body>

<div class="header">
    <c:url var="listUrl" value="/posts" />
    <a href="${listUrl}" class="btn btn-secondary">← 목록으로</a>
    <div>
        <c:if test="${sessionScope.currentUser != null}">
            <span>안녕하세요,
                <c:url var="userDetailUrl" value="/users/${sessionScope.currentUser.id}" />
                <a href="${userDetailUrl}" style="color: #007bff; text-decoration: none;">
                    ${sessionScope.currentUser.name}
                </a>님!
                <c:if test="${sessionScope.currentUser.roles == 'ADMIN'}">
                    <span style="background: #dc3545; color: white; padding: 2px 6px; border-radius: 10px; font-size: 11px; margin-left: 5px;">ADMIN</span>
                </c:if>
            </span>
        </c:if>
    </div>
</div>

<div class="post-content">
    <h2>${postpage.title}</h2>

    <div class="post-meta">
        <div class="post-stats">
            <span>👤 작성자: ${postAuthor}</span>
            <span>👁️ 조회수: ${postpage.viewCount != null ? postpage.viewCount : 0}</span>
            <span>❤️ 좋아요: <span id="likeCount">${postpage.likeCount != null ? postpage.likeCount : 0}</span></span>
            <span>📅 작성일: ${postpage.createdAt}</span>
            <c:if test="${postpage.createdAt ne postpage.updatedAt}">
                <span>✏️ 수정일: ${postpage.updatedAt}</span>
            </c:if>
        </div>
    </div>

    <div style="line-height: 1.6; margin: 20px 0;">
        ${postpage.content}
    </div>

    <div class="post-actions">
        <!-- 좋아요 버튼 -->
        <c:choose>
            <c:when test="${sessionScope.currentUser != null}">
                <button id="likeButton" class="like-button ${liked ? 'liked' : ''}" onclick="toggleLike()">
                    ${liked ? '❤️' : '🤍'} 좋아요 ${postpage.likeCount != null ? postpage.likeCount : 0}
                </button>
            </c:when>
            <c:otherwise>
                <span class="like-button" style="cursor: not-allowed; opacity: 0.6;">
                    🤍 좋아요 ${postpage.likeCount != null ? postpage.likeCount : 0}
                </span>
            </c:otherwise>
        </c:choose>

        <!-- 수정/삭제 버튼 (권한 확인) -->
        <c:if test="${sessionScope.currentUser != null &&
                     (sessionScope.currentUser.id == postpage.userId || sessionScope.currentUser.roles == 'ADMIN')}">
            <c:url var="editUrl" value="/posts/edit/${postpage.id}" />
            <a href="${editUrl}" class="btn btn-primary">✏️ 수정</a>
            <c:url var="deleteUrl" value="/posts/delete/${postpage.id}" />
            <form action="${deleteUrl}" method="post" style="display:inline;">
                <button type="submit" class="btn btn-danger" onclick="return confirm('정말 삭제하시겠습니까?');">🗑️ 삭제</button>
            </form>
        </c:if>
    </div>
</div>

<div class="comment-section">
    <h3>💬 댓글 (${comments.size()}개)</h3>

    <!-- 댓글 목록 -->
    <c:forEach var="cm" items="${comments}">
        <div class="comment-item">
            <div class="comment-meta">
                <strong>${cm.title}</strong>
                (<c:choose>
                    <c:when test="${commentUserMap[cm.userId] != null}">
                        ${commentUserMap[cm.userId]}
                    </c:when>
                    <c:otherwise>
                        알 수 없음
                    </c:otherwise>
                </c:choose>)
                | ${cm.createdAt}
                <c:if test="${cm.createdAt ne cm.updatedAt}">
                    | 수정됨: ${cm.updatedAt}
                </c:if>
            </div>
            <div style="margin: 10px 0;">
                ${cm.content}
            </div>

            <!-- 댓글 수정/삭제 버튼 (권한 확인) -->
            <c:if test="${sessionScope.currentUser != null &&
                         (sessionScope.currentUser.id == cm.userId || sessionScope.currentUser.roles == 'ADMIN')}">
                <div class="comment-actions">
                    <c:url var="editCommentUrl" value="/comments/edit/${cm.id}" />
                    <a href="${editCommentUrl}" class="btn btn-secondary">수정</a>
                    <c:url var="deleteCommentUrl" value="/comments/delete/${cm.id}" />
                    <form action="${deleteCommentUrl}" method="post" style="display:inline;">
                        <button type="submit" class="btn btn-danger" onclick="return confirm('댓글을 삭제하시겠습니까?');">삭제</button>
                    </form>
                </div>
            </c:if>
        </div>
    </c:forEach>

    <!-- 댓글이 없을 때 -->
    <c:if test="${empty comments}">
        <div style="text-align: center; padding: 40px; color: #999;">
            <p>첫 번째 댓글을 작성해보세요! 💭</p>
        </div>
    </c:if>

    <!-- 댓글 작성 폼 -->
    <c:choose>
        <c:when test="${sessionScope.currentUser != null}">
            <div class="comment-form">
                <h4>댓글 작성하기</h4>
                <c:url var="createCommentUrl" value="/comments/create" />
                <form action="${createCommentUrl}" method="post">
                    <input type="hidden" name="postId" value="${postpage.id}">
                    <input type="text" name="title" placeholder="제목 (선택사항)" maxlength="100">
                    <textarea name="content" placeholder="댓글 내용을 입력하세요..." required rows="4"></textarea>
                    <button type="submit" class="btn btn-primary">💬 댓글 작성</button>
                </form>
            </div>
        </c:when>
        <c:otherwise>
            <div class="comment-form" style="text-align: center; color: #666;">
                <c:url var="loginUrl" value="/users/login" />
                <p>댓글을 작성하려면 <a href="${loginUrl}" style="color: #007bff;">로그인</a>해주세요.</p>
            </div>
        </c:otherwise>
    </c:choose>
</div>

</body>
</html>