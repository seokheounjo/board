<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>👥 사용자 관리</title>
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
        table {
            width: 100%;
            border-collapse: collapse;
            background: white;
            border-radius: 8px;
            overflow: hidden;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        th, td {
            padding: 12px;
            text-align: left;
            border-bottom: 1px solid #eee;
        }
        th {
            background-color: #f8f9fa;
            font-weight: bold;
            color: #495057;
        }
        tr:hover {
            background-color: #f8f9fa;
        }
        .role-badge {
            padding: 4px 8px;
            border-radius: 12px;
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
        .actions {
            display: flex;
            gap: 5px;
        }
    </style>
</head>
<body>

<div class="header">
    <h1>👥 사용자 관리</h1>
    <div>
        <c:url var="mainUrl" value="/posts" />
        <c:url var="newUserUrl" value="/users/new" />
        <c:url var="cleanupUrl" value="/admin/cleanup" />
        <a href="${mainUrl}" class="btn btn-secondary">← 메인으로</a>
        <a href="${newUserUrl}" class="btn btn-primary">👤 사용자 추가</a>
        <c:if test="${sessionScope.currentUser.roles == 'ADMIN'}">
            <a href="${cleanupUrl}" class="btn btn-secondary">🔧 데이터 정리</a>
        </c:if>
    </div>
</div>

<c:choose>
    <c:when test="${not empty users}">
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>아이디</th>
                    <th>이름</th>
                    <th>권한</th>
                    <th>가입일</th>
                    <th>관리</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="user" items="${users}">
                    <tr>
                        <td>${user.id}</td>
                        <td>
                            <c:url var="userDetailUrl" value="/users/${user.id}" />
                            <a href="${userDetailUrl}" style="color: #007bff; text-decoration: none;">
                                ${user.username}
                            </a>
                        </td>
                        <td>${user.name}</td>
                        <td>
                            <span class="role-badge ${user.roles == 'ADMIN' ? 'role-admin' : 'role-user'}">
                                ${user.roles == 'ADMIN' ? '관리자' : '일반사용자'}
                            </span>
                        </td>
                        <td>${user.joindate}</td>
                        <td>
                            <div class="actions">
                                <c:url var="viewUrl" value="/users/${user.id}" />
                                <c:url var="editUrl" value="/users/edit/${user.id}" />
                                <a href="${viewUrl}" class="btn btn-secondary">보기</a>
                                <a href="${editUrl}" class="btn btn-primary">수정</a>
                                <c:if test="${sessionScope.currentUser.roles == 'ADMIN' && user.id != sessionScope.currentUser.id}">
                                    <c:url var="deleteUrl" value="/users/delete/${user.id}" />
                                    <form action="${deleteUrl}" method="post" style="display:inline;">
                                        <button type="submit" class="btn btn-danger"
                                                onclick="return confirm('정말 삭제하시겠습니까?');">삭제</button>
                                    </form>
                                </c:if>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:when>
    <c:otherwise>
        <div style="text-align: center; padding: 60px; background: white; border-radius: 8px;">
            <h3>등록된 사용자가 없습니다</h3>
            <p>첫 번째 사용자를 추가해보세요!</p>
            <c:url var="addUserUrl" value="/users/new" />
            <a href="${addUserUrl}" class="btn btn-primary">사용자 추가</a>
        </div>
    </c:otherwise>
</c:choose>

<div style="margin-top: 20px; padding: 15px; background: white; border-radius: 8px;">
    <h4>📊 통계</h4>
    <p>총 사용자 수: <strong>${users.size()}명</strong></p>
    <p>관리자: <strong>
        <c:set var="adminCount" value="0" />
        <c:forEach var="user" items="${users}">
            <c:if test="${user.roles == 'ADMIN'}">
                <c:set var="adminCount" value="${adminCount + 1}" />
            </c:if>
        </c:forEach>
        ${adminCount}명
    </strong></p>
    <p>일반사용자: <strong>${users.size() - adminCount}명</strong></p>
</div>

</body>
</html>