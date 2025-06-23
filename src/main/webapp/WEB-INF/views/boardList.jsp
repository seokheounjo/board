<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>📂 게시판 관리</title>
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
        .btn-disabled {
            background-color: #ccc;
            color: #666;
            cursor: not-allowed;
        }
        .board-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
            gap: 20px;
        }
        .board-card {
            background: white;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            transition: transform 0.2s;
        }
        .board-card:hover {
            transform: translateY(-2px);
            box-shadow: 0 4px 8px rgba(0,0,0,0.15);
        }
        .board-title {
            font-size: 18px;
            font-weight: bold;
            color: #333;
            margin-bottom: 10px;
        }
        .board-meta {
            color: #666;
            font-size: 14px;
            margin-bottom: 15px;
        }
        .board-actions {
            display: flex;
            gap: 10px;
            justify-content: flex-end;
        }
        .empty-state {
            text-align: center;
            padding: 60px;
            background: white;
            border-radius: 10px;
            color: #666;
        }
    </style>
    <script>
        function showNotAvailable() {
            alert('현재는 메인으로 버튼만 활성화되어 있습니다.');
            return false;
        }
    </script>
</head>
<body>

<div class="header">
    <h1>📂 게시판 관리</h1>
    <div>
        <c:url var="mainUrl" value="/posts" />
        <a href="${mainUrl}" class="btn btn-secondary">← 메인으로</a>
        <c:if test="${sessionScope.currentUser != null}">
            <a href="#" onclick="showNotAvailable()" class="btn btn-disabled">📂 게시판 추가</a>
        </c:if>
    </div>
</div>

<c:choose>
    <c:when test="${not empty boards}">
        <div class="board-grid">
            <c:forEach var="board" items="${boards}">
                <div class="board-card">
                    <div class="board-title">
                        📂 ${board.postname}
                    </div>
                    <div class="board-meta">
                        👤 생성자 ID: ${board.userId}<br>
                        🆔 게시판 ID: ${board.id}
                    </div>
                    <div class="board-actions">
                        <a href="#" onclick="showNotAvailable()" class="btn btn-disabled">보기</a>

                        <!-- 권한 확인: 생성자 또는 ADMIN만 수정/삭제 가능 -->
                        <c:if test="${sessionScope.currentUser != null &&
                                     (sessionScope.currentUser.id == board.userId || sessionScope.currentUser.roles == 'ADMIN')}">
                            <a href="#" onclick="showNotAvailable()" class="btn btn-disabled">수정</a>
                            <button type="button" class="btn btn-disabled" onclick="showNotAvailable()">삭제</button>
                        </c:if>
                    </div>
                </div>
            </c:forEach>
        </div>
    </c:when>
    <c:otherwise>
        <div class="empty-state">
            <h3>📂 등록된 게시판이 없습니다</h3>
            <p>첫 번째 게시판을 만들어보세요!</p>
            <c:if test="${sessionScope.currentUser != null}">
                <a href="#" onclick="showNotAvailable()" class="btn btn-disabled">게시판 추가하기</a>
            </c:if>
            <c:if test="${sessionScope.currentUser == null}">
                <c:url var="loginUrl" value="/users/login" />
                <p><a href="${loginUrl}" style="color: #007bff;">로그인</a>하시면 게시판을 만들 수 있습니다.</p>
            </c:if>
        </div>
    </c:otherwise>
</c:choose>

<div style="margin-top: 30px; padding: 20px; background: white; border-radius: 10px;">
    <h4>💡 게시판 관리 가이드</h4>
    <ul style="color: #666; line-height: 1.6;">
        <li><strong>게시판 생성:</strong> 로그인한 사용자만 게시판을 만들 수 있습니다.</li>
        <li><strong>수정/삭제 권한:</strong> 게시판을 만든 사용자 또는 관리자만 수정/삭제할 수 있습니다.</li>
        <li><strong>게시글 작성:</strong> 각 게시판에 게시글을 작성할 때는 해당 게시판 ID를 지정해야 합니다.</li>
        <li><strong>권한 관리:</strong> 관리자(ADMIN)는 모든 게시판을 관리할 수 있습니다.</li>
    </ul>

    <div style="margin-top: 20px; padding: 15px; background: #fff3cd; border: 1px solid #ffeaa7; border-radius: 5px;">
        <p style="margin: 0; color: #856404;">
            <strong>⚠️ 안내:</strong> 현재는 메인으로 이동하는 기능만 활성화되어 있습니다.
        </p>
    </div>
</div>

</body>
</html>