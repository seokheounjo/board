<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>🔧 관리자 - 데이터 정리</title>
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
        .alert {
            padding: 15px;
            border-radius: 5px;
            margin-bottom: 20px;
        }
        .alert-success {
            background-color: #d4edda;
            color: #155724;
            border: 1px solid #c3e6cb;
        }
        .alert-danger {
            background-color: #f8d7da;
            color: #721c24;
            border: 1px solid #f5c6cb;
        }
        .alert-warning {
            background-color: #fff3cd;
            color: #856404;
            border: 1px solid #ffeaa7;
        }
        .card {
            background: white;
            padding: 20px;
            border-radius: 8px;
            margin-bottom: 20px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        .btn {
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            text-decoration: none;
            display: inline-block;
            font-size: 14px;
        }
        .btn-primary {
            background-color: #007bff;
            color: white;
        }
        .btn-danger {
            background-color: #dc3545;
            color: white;
        }
        .btn-secondary {
            background-color: #6c757d;
            color: white;
        }
        .duplicate-list {
            background-color: #f8f9fa;
            padding: 10px;
            border-radius: 5px;
            margin: 10px 0;
        }
    </style>
</head>
<body>

<div class="header">
    <h1>🔧 관리자 - 데이터 정리</h1>
    <div>
        <c:url var="mainUrl" value="/posts" />
        <c:url var="usersUrl" value="/users" />
        <a href="${mainUrl}" class="btn btn-secondary">← 메인으로</a>
        <a href="${usersUrl}" class="btn btn-secondary">사용자 관리</a>
    </div>
</div>

<!-- 성공/에러 메시지 -->
<c:if test="${not empty success}">
    <div class="alert alert-success">
        ✅ ${success}
    </div>
</c:if>

<c:if test="${not empty error}">
    <div class="alert alert-danger">
        ❌ ${error}
    </div>
</c:if>

<!-- 중복 사용자 정리 -->
<div class="card">
    <h3>👥 중복 사용자 정리</h3>

    <c:choose>
        <c:when test="${not empty duplicateUsernames}">
            <div class="alert alert-warning">
                ⚠️ 중복된 username이 발견되었습니다!
            </div>

            <h4>중복된 사용자명 목록:</h4>
            <div class="duplicate-list">
                <c:forEach var="username" items="${duplicateUsernames}">
                    <div>• ${username}</div>
                </c:forEach>
            </div>

            <p style="color: #666; margin: 15px 0;">
                <strong>정리 방법:</strong> 각 중복된 username에서 가장 낮은 ID를 가진 사용자를 남기고 나머지를 삭제합니다.
            </p>

            <c:url var="cleanupUsersUrl" value="/admin/cleanup/users" />
            <form action="${cleanupUsersUrl}" method="post" style="margin-top: 20px;">
                <button type="submit" class="btn btn-danger"
                        onclick="return confirm('정말로 중복 사용자를 정리하시겠습니까?');">
                    🗑️ 중복 사용자 정리 실행
                </button>
            </form>
        </c:when>
        <c:otherwise>
            <div class="alert alert-success">
                ✅ 중복된 사용자가 없습니다. 모든 username이 유니크합니다.
            </div>
        </c:otherwise>
    </c:choose>
</div>

<!-- 데이터베이스 정리 가이드 -->
<div class="card">
    <h3>📋 수동 데이터베이스 정리 가이드</h3>
    <p>더 강력한 정리를 위해 직접 SQL을 실행할 수 있습니다:</p>

    <div style="background: #f8f9fa; padding: 15px; border-radius: 5px; font-family: monospace; margin: 10px 0;">
        <strong>1. 중복 사용자 확인:</strong><br>
        SELECT username, COUNT(*) FROM user GROUP BY username HAVING COUNT(*) > 1;<br><br>

        <strong>2. 중복 사용자 정리:</strong><br>
        DELETE u1 FROM user u1 INNER JOIN user u2 WHERE u1.id > u2.id AND u1.username = u2.username;<br><br>

        <strong>3. UNIQUE 제약조건 추가:</strong><br>
        ALTER TABLE user ADD UNIQUE KEY unique_username (username);
    </div>

    <div class="alert alert-warning">
        ⚠️ <strong>주의:</strong> 위의 SQL은 데이터를 영구적으로 삭제합니다. 실행 전에 백업을 권장합니다.
    </div>
</div>

<!-- 시스템 상태 -->
<div class="card">
    <h3>📊 시스템 상태</h3>
    <div style="display: grid; grid-template-columns: repeat(auto-fit, minmax(200px, 1fr)); gap: 15px;">
        <div style="background: #e3f2fd; padding: 15px; border-radius: 5px; text-align: center;">
            <h4 style="margin: 0; color: #1976d2;">현재 로그인</h4>
            <p style="margin: 5px 0 0 0; font-size: 18px;">${sessionScope.currentUser.name}</p>
        </div>
        <div style="background: #f3e5f5; padding: 15px; border-radius: 5px; text-align: center;">
            <h4 style="margin: 0; color: #7b1fa2;">권한</h4>
            <p style="margin: 5px 0 0 0; font-size: 18px;">${sessionScope.currentUser.roles}</p>
        </div>
        <div style="background: #e8f5e8; padding: 15px; border-radius: 5px; text-align: center;">
            <h4 style="margin: 0; color: #388e3c;">중복 발견</h4>
            <p style="margin: 5px 0 0 0; font-size: 18px;">${duplicateUsernames.size()}개</p>
        </div>
    </div>
</div>

</body>
</html>