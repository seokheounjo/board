<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login</title>
</head>
<body>

<h1>Login</h1>

<form action="/doLogin" method="post">
    <div>
        <label for="username">Username:</label>
        <input type="text" id="username" name="username"/>
    </div>
    <div>
        <label for="password">Password:</label>
        <input type="password" id="password" name="password"/>
    </div>
    <div>
        <input type="submit" value="Log in"/>
    </div>
</form>

<a href="/">Go to Home</a>

</body>
</html>
