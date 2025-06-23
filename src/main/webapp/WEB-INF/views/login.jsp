<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>로그인</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f8f9fa;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            margin: 0;
        }
        .login-container {
            background: white;
            padding: 40px;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            width: 100%;
            max-width: 400px;
        }
        .login-header {
            text-align: center;
            margin-bottom: 30px;
        }
        .login-header h1 {
            color: #333;
            margin-bottom: 10px;
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
        .form-group input {
            width: 100%;
            padding: 12px;
            border: 1px solid #ddd;
            border-radius: 5px;
            font-size: 16px;
            box-sizing: border-box;
        }
        .form-group input:focus {
            outline: none;
            border-color: #007bff;
            box-shadow: 0 0 5px rgba(0,123,255,0.3);
        }
        .btn {
            width: 100%;
            padding: 12px;
            background-color: #007bff;
            color: white;
            border: none;
            border-radius: 5px;
            font-size: 16px;
            cursor: pointer;
            transition: background-color 0.3s;
        }
        .btn:hover {
            background-color: #0056b3;
        }
        .error-message {
            background-color: #f8d7da;
            color: #721c24;
            padding: 10px;
            border-radius: 5px;
            margin-bottom: 20px;
            border: 1px solid #f5c6cb;
        }
        .links {
            text-align: center;
            margin-top: 20px;
        }
        .links a {
            color: #007bff;
            text-decoration: none;
            margin: 0 10px;
        }
        .links a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>

<div class="login-container">
    <div class="login-header">
        <h1>🔐 로그인</h1>
        <p>게시판을 이용하려면 로그인해주세요</p>
    </div>

    <!-- 에러 메시지 표시 -->
    <c:if test="${not empty error}">
        <div class="error-message">
            ${error}
        </div>
    </c:if>

    <c:url var="loginActionUrl" value="/users/login" />
    <form action="${loginActionUrl}" method="post">
        <div class="form-group">
            <label for="username">아이디</label>
            <input type="text" id="username" name="username" required
                   placeholder="아이디를 입력하세요" autocomplete="username">
        </div>

        <div class="form-group">
            <label for="password">비밀번호</label>
            <input type="password" id="password" name="password" required
                   placeholder="비밀번호를 입력하세요" autocomplete="current-password">
        </div>

        <button type="submit" class="btn">로그인</button>
    </form>

    <div class="links">
        <c:url var="signupUrl" value="/users/new" />
        <c:url var="mainUrl" value="/posts" />
        <a href="${signupUrl}">회원가입</a>
        <a href="${mainUrl}">메인페이지로</a><br><br>
        <a>관리자 ID : admin01, pw : admin01</a><br>
        <a>사용자 ID : user1000, pw user1000</a>
    </div>
</div>

</body>
</html>