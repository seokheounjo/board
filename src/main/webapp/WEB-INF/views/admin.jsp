<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>관리자 페이지</title>
</head>
<body>
    <div class="container">
        <h1>관리자 전용 페이지</h1>

        <p>
            안녕하세요, <sec:authentication property="principal.username" /> 님!
            이 페이지는 <span class="highlight">관리자(ROLE_ADMIN) 권한</span>을 가진 사용자만 접근할 수 있습니다.
        </p>

        <div class="user-info">
            <h2>현재 로그인 사용자 정보</h2>
            <p>
                <span class="highlight">사용자 이름 (ID):</span> <sec:authentication property="principal.username" /><br>
                <span class="highlight">사용자 비밀번호 (인코딩됨):</span> <sec:authentication property="principal.password" /><br>
                <span class="highlight">권한:</span>
                <ul>
                    <sec:authentication property="principal.authorities" var="authorities" />
                    <c:forEach items="${authorities}" var="authority">
                        <li>${authority.authority}</li>
                    </c:forEach>
                </ul>
            </p>
            <p>
                이 정보는 Spring Security의 <span class="highlight">`Authentication.getPrincipal()`</span> 객체에서 가져온 것입니다.
            </p>
        </div>

        <p>
            여기에 관리자 전용 콘텐츠, 기능, 대시보드 등을 추가할 수 있습니다.
            예를 들어, 사용자 관리, 시스템 설정, 통계 보기 등의 기능을 제공할 수 있습니다.
        </p>

        <div class="logout-form">
            <form action="${pageContext.request.contextPath}/logout" method="post">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <button type="submit">로그아웃</button>
            </form>
        </div>

    </div>
</body>
</html>
