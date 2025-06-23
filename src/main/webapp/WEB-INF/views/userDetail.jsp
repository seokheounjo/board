<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>👤 사용자 정보</title>
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
        .profile-card {
            background: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            max-width: 600px;
            margin: 0 auto;
        }
        .profile-header {
            text-align: center;
            margin-bottom: 30px;
            padding-bottom: 20px;
            border-bottom: 1px solid #eee;
        }
        .profile-avatar {
            width: 80px;
            height: 80px;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 24px;
            color: white;
            margin: 0 auto 15px auto;
        }
        .info-group {
            margin-bottom: 20px;
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 15px 0;
            border-bottom: 1px solid #f0f0f0;
        }
        .info-label {
            font-weight: bold;
            color: #555;
            width: 120px;
        }
        .info-value {
            flex: 1;
            font-size: 16px;
        }
        .role-badge {
            padding: 4px 12px;
            border-radius: 15px;
            font-size: 12px;
            font-weight: bold;
        }
        .role-admin {
            background-color: #dc3545;
            color: white;
        }
        .role-user {
            background-color: #28a745;
            color: white;
        }
        .btn {
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            text-decoration: none;
            display: inline-block;
            font-size: 14px;
            margin-right: 10px;
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
        .actions {
            text-align: center;
            margin-top: 30px;
            padding-top: 20px;
            border-top: 1px solid #eee;
        }
    </style>
</head>
<body>

<div class="header">
    <h1>👤 사용자 정보</h1>
    <div>
        <c:if test="${sessionScope.currentUser.roles == 'ADMIN'}">
            <c:url var="userListUrl" value="/users" />
            <a href="${userListUrl}" class="btn btn-secondary">← 사용자 목록</a>
        </c:if>
        <c:url var="mainUrl" value="/posts" />
        <a href="${mainUrl}" class="btn btn-secondary">메인으로</a>
    </div>
</div>

<div class="profile-card">
    <div class="profile-header">
        <div class="profile-avatar">
            ${user.name.substring(0, 1)}
        </div>
        <h2>${user.name}</h2>
        <span class="role-badge ${user.roles == 'ADMIN' ? 'role-admin' : 'role-user'}">
            ${user.roles == 'ADMIN' ? '관리자' : '일반사용자'}
        </span>
    </div>

    <div class="info-group">
        <span class="info-label">🆔 사용자 ID</span>
        <span class="info-value">${user.id}</span>
    </div>

    <div class="info-group">
        <span class="info-label">👤 아이디</span>
        <span class="info-value">${user.username}</span>
    </div>

    <div class="info-group">
        <span class="info-label">📝 이름</span>
        <span class="info-value">${user.name}</span>
    </div>

    <div class="info-group">
        <span class="info-label">🔐 권한</span>
        <span class="info-value">
            <span class="role-badge ${user.roles == 'ADMIN' ? 'role-admin' : 'role-user'}">
                ${user.roles == 'ADMIN' ? '관리자' : '일반사용자'}
            </span>
        </span>
    </div>

    <div class="info-group">
        <span class="info-label">📅 가입일</span>
        <span class="info-value">${user.joindate}</span>
    </div>

    <div class="actions">
        <!-- 본인이거나 관리자인 경우 수정 가능 -->
        <c:if test="${sessionScope.currentUser.id == user.id || sessionScope.currentUser.roles == 'ADMIN'}">
            <c:url var="editUrl" value="/users/edit/${user.id}" />
            <a href="${editUrl}" class="btn btn-primary">✏️ 정보 수정</a>
        </c:if>

        <!-- 관리자만 삭제 가능 (본인 제외) -->
        <c:if test="${sessionScope.currentUser.roles == 'ADMIN' && sessionScope.currentUser.id != user.id}">
            <c:url var="deleteUrl" value="/users/delete/${user.id}" />
            <form action="${deleteUrl}" method="post" style="display:inline;">
                <button type="submit" class="btn btn-danger"
                        onclick="return confirm('정말 이 사용자를 삭제하시겠습니까?');">
                    🗑️ 사용자 삭제
                </button>
            </form>
        </c:if>
    </div>
</div>

<c:if test="${sessionScope.currentUser.roles == 'ADMIN'}">
    <div style="max-width: 600px; margin: 20px auto; padding: 15px; background: #f8f9fa; border-radius: 8px;">
        <h4>📊 관리자 정보</h4>
        <ul style="color: #666; margin: 10px 0;">
            <li>사용자 고유 번호: ${user.id}</li>
            <li>계정 상태: 활성</li>
            <li>최근 로그인: 정보 없음</li>
            <li>작성한 게시글: 정보 없음</li>
        </ul>
    </div>
</c:if>

</body>
</html>