<%--
  Created by IntelliJ IDEA.
  User: Ovidiu
  Date: 5/5/2015
  Time: 1:05 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>

<head>
    <title>Http Session</title>
    <%
        HttpSession httpSession = (HttpSession) session.getAttribute("session");
        String username = (String) session.getAttribute("user");
    %>
</head>

<body>
Username <b><%=username%></b> or password is invalid! Please try again.<br>
Your session id is <%=httpSession.getId()%>
</body>

</html>