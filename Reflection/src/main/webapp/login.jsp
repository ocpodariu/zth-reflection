<%--
  Created by IntelliJ IDEA.
  User: Ovidiu
  Date: 5/5/2015
  Time: 1:03 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>

<head>
    <title>Http Session</title>
</head>

<body>
<form name="login" method="post" action="login">
    <label for="username">Username:</label>
    <input type="text" id="username" name="username"/>
    <label for="password">Password:</label>
    <input type="password" id="password" name="password"/>
    <input type="submit" id="submitButton"/>
</form>
</body>
</html>