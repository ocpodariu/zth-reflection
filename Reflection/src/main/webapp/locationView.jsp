<%--
  Created by IntelliJ IDEA.
  User: Ovidiu
  Date: 5/5/2015
  Time: 10:11 AM
  To change this template use File | Settings | File Templates.
--%>

<%@ page import="ro.teamnet.zth.appl.dao.LocationDao" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Enumeration" %>
<%@ page import="ro.teamnet.zth.appl.domain.Location" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>

<head>
    <title>Locations List</title>
</head>

<body>

<% LocationDao locationDao = new LocationDao(); %>
<% Location location = null; %>

<%
    Long locationId = null;

    Enumeration parameterNames = request.getParameterNames();
    while (parameterNames.hasMoreElements()) {
        String paramName = (String) parameterNames.nextElement();
        System.out.println(paramName);
        if (paramName.equalsIgnoreCase("id")) {
            locationId = Long.parseLong(request.getParameter(paramName));
            System.out.println("Found ID: " + locationId);
            break;
        }
    }

    if (locationId != null)
        location = locationDao.findById(locationId);
%>

<table border="1">
    <thead>
    <tr>
        <td>Id</td>
        <td>Street address</td>
        <td>Postal code</td>
        <td>City</td>
        <td>State province</td>
    </tr>
    </thead>
    <tbody>
    <%
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

    %>
    <tr>
        <td>
            <%= location.getId() %>
        </td>

        <td>
            <%= location.getStreetAddress() %>
        </td>

        <td>
            <%= location.getPostalCode() %>
        </td>

        <td>
            <%= location.getCity() %>
        </td>

        <td>
            <%= location.getStateProvince() %>
        </td>


    </tr>

    </tbody>
</table>
<a href="locationList.jsp">Locations List</a>
</body>
</html>

