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
		<td><label name = "author"> Enter the author: </label></td>
		<td><input name = "author" /> </td>
	</tr>
	<tr>
		<td><label name = "key-words"> Enter the key-words: </label></td>
		<td><input name = "key-words" /> </td>
	</tr>
	<tr>
		<td><label name = "date-since"> Enter since date: </label></td>
		<td><input name = "date-since" /> </td>
		<td><label name = "date-until"> Enter until date: </label></td>
		<td><input name = "date-until" /> </td>
	</tr>
	<tr>
		<td><label name = "pages-num"> Enter number of pages: </label></td>
		<td><input name = "pages-num" /> </td>
	</tr>
	<tr>
		<td><form:label path="filters"> Choose filter </form:label></td>
		<td><form:checkboxes items="${filterList}" path="filters" /> </td>
	</tr>
    <tr>
		<td colspan = "4">
    		<input style="width:100px;height:50px" type = "submit" value = "Filter"/>
    	</td>
		
    </tr>
</table>
</form:form>
<P> Posts: </P>
<c:forEach items="${tweets}" var="tweet">
	<tr>
		<td> Author: ${tweet.author} @${tweet.screenName} </td>
		<br />
	</tr>
	<tr>
		<td> ${tweet.message} </td>
	</tr>
	<P />
</c:forEach>
<P> The time on the server is ${serverTime}. </P>
</body>
</html>