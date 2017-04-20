<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page session="false" %>
<html>
<head>
	<title>TwitterFilter</title>
</head>
<body>
<h1>
	Successfully logged in.
</h1>
<form:form method = "POST" action = "/springmvc/login/getUserTimeline" >
<table>
	<tr>
		<td><label name = "username"> Enter username: </label></td>
		<td><input name = "username" /> </td>
	</tr>
    <tr>
    	<td colspan = "4">
    		<input style="width:100px;height:50px" type = "submit" value = "Filter"/>
    	</td>
    </tr>
</table>
</form:form>
<P> Posts: </P>
<c:forEach items="${statuses}" var="status">
	<tr>
		<td> ${status} </td>
		<td />
		<P />
	</tr>
</c:forEach>
<P> The time on the server is ${serverTime}. </P>
</body>
</html>