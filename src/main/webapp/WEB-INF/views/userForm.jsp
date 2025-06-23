<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>
        <c:choose>
            <c:when test="${not empty user.id}">사용자 수정</c:when>
            <c:otherwise>사용자 등록</c:otherwise>
        </c:choose>
    </title>
    <style>
        body {
            font-family: Arial, sans-serif;
            padding: 40px;
            background-color: #f9f9f9;
        }
        .form-container {
            max-width: 600px;
            margin: 0 auto;
            background: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        .header {
            text-align: center;
            margin-bottom: 30px;
        }
        .form-group {
            margin-bottom: 20px;
        }
        .form-group label {
            display: block;
            margin-bottom: 5px;
            color: #555;
            font-weight: bold;
        }
        .form-group input, .form-group select {
            width: 100%;
            padding: 12px;
            border: 1px solid #ddd;
            border-radius: 5px;
            font-size: 16px;
            box-sizing: border-box;
        }
        .form-group input:focus, .form-group select:focus {
            outline: none;
            border-color: #007bff;
            box-shadow: 0 0 5px rgba(0,123,255,0.3);
        }
        .btn {
            padding: 12px 24px;
            border: none;
            border-radius: 5px;
            font-size: 16px;
            cursor: pointer;
            text-decoration: none;
            display: inline-block;
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
        .btn:hover {
            opacity: 0.9;
        }
        .button-group {
            text-align: center;
            margin-top: 30px;
        }
        .required {
            color: red;
        }
        .help-text {
            font-size: 12px;
            color: #666;
            margin-top: 5px;
        }
    </style>
</head>
<body>

<div class="form-container">
    <div class="header">
        <h1>
            <c:choose>
                <c:when test="${not empty user.id}">👤 사용자 수정</c:when>
                <c:otherwise>👤 사용자 등록</c:otherwise>
            </c:choose>
        </h1>
    </div>

    <!-- 에러 메시지 표시 -->
    <c:if test="${not empty error}">
        <div style="background-color: #f8d7da; color: #721c24; padding: 10px; border-radius: 5px; margin-bottom: 20px; border: 1px solid #f5c6cb;">
            ${error}
        </div>
    </c:if>

    <c:choose>
        <c:when test="${not empty user.id}">
            <c:url var="formActionUrl" value="/users/edit/${user.id}" />
        </c:when>
        <c:otherwise>
            <c:url var="formActionUrl" value="/users/new" />
        </c:otherwise>
    </c:choose>

    <form action="${formActionUrl}" method="post">

        <div class="form-group">
            <label for="username">아이디 <span class="required">*</span></label>
            <input type="text" id="username" name="username" value="${user.username}"
                   required placeholder="영문, 숫자 조합 4-20자" maxlength="20"
                   <c:if test="${not empty user.id}">readonly</c:if>>
            <c:if test="${not empty user.id}">
                <div class="help-text">아이디는 수정할 수 없습니다.</div>
            </c:if>
        </div>

        <div class="form-group">
            <label for="password">비밀번호 <span class="required">*</span></label>
            <input type="password" id="password" name="password" value="${user.password}"
                   required placeholder="6자 이상 입력하세요" minlength="6">
            <div class="help-text">보안을 위해 6자 이상 입력해주세요.</div>
        </div>

        <div class="form-group">
            <label for="name">이름 <span class="required">*</span></label>
            <input type="text" id="name" name="name" value="${user.name}"
                   required placeholder="실명을 입력하세요" maxlength="50">
        </div>

        <div class="form-group">
            <label for="roles">권한 <span class="required">*</span></label>
            <c:choose>
                <c:when test="${sessionScope.currentUser.roles == 'ADMIN'}">
                    <!-- 관리자는 모든 권한 설정 가능 -->
                    <select id="roles" name="roles" required>
                        <option value="USER" ${user.roles == 'USER' ? 'selected' : ''}>일반사용자</option>
                        <option value="ADMIN" ${user.roles == 'ADMIN' ? 'selected' : ''}>관리자</option>
                    </select>
                    <div class="help-text">관리자는 모든 권한을 설정할 수 있습니다.</div>
                </c:when>
                <c:when test="${isOwner}">
                    <!-- 본인 정보 수정 시 권한 변경 불가 -->
                    <input type="text" value="${user.roles == 'ADMIN' ? '관리자' : '일반사용자'}" readonly style="background-color: #f8f9fa;" />
                    <input type="hidden" name="roles" value="${user.roles}" />
                    <div class="help-text">본인의 권한은 변경할 수 없습니다.</div>
                </c:when>
                <c:otherwise>
                    <!-- 일반 사용자 가입 시 -->
                    <input type="text" value="일반사용자" readonly style="background-color: #f8f9fa;" />
                    <input type="hidden" name="roles" value="USER" />
                    <div class="help-text">일반사용자로만 등록할 수 있습니다.</div>
                </c:otherwise>
            </c:choose>
        </div>

        <div class="button-group">
            <button type="submit" class="btn btn-primary">
                <c:choose>
                    <c:when test="${not empty user.id}">✏️ 수정 완료</c:when>
                    <c:otherwise>👤 등록하기</c:otherwise>
                </c:choose>
            </button>
            <c:url var="cancelUrl" value="/users" />
            <a href="${cancelUrl}" class="btn btn-secondary">취소</a>
        </div>
    </form>
</div>

<script>
// 폼 유효성 검사
document.querySelector('form').addEventListener('submit', function(e) {
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;
    const name = document.getElementById('name').value;

    if (username.length < 4) {
        alert('아이디는 4자 이상 입력해주세요.');
        e.preventDefault();
        return;
    }

    if (password.length < 6) {
        alert('비밀번호는 6자 이상 입력해주세요.');
        e.preventDefault();
        return;
    }

    if (name.trim().length < 2) {
        alert('이름은 2자 이상 입력해주세요.');
        e.preventDefault();
        return;
    }
});
</script>

</body>
</html>