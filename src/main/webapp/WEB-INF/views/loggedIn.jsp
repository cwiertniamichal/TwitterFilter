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
<P> Please do not forget to enter number of pages. </P>	
<P> Date format is: yyyy-mm-dd. Date have to be used with other filters. </P>
<P> When using none of this words filter you have to use other filter to get some tweets </P>
<form:form method = "POST" action = "/springmvc/login/getUserTimeline" >
<table>
	<tr>
		<td><label name = "all-words"> Containing all of this words: </label></td>
		<td><input name = "all-words" /> </td>
	</tr>
	<tr>
		<td><label name = "exact-words"> Containing exact this query: </label></td>
		<td><input name = "exact-words" /> </td>
	</tr>
	<tr>
		<td><label name = "any-words"> Containing any of this words: </label></td>
		<td><input name = "any-words" /> </td>
	</tr>
	<tr>
		<td><label name = "no-words"> Containing none of this words: </label></td>
		<td><input name = "no-words" /> </td>
	</tr>
	<tr>
		<td><label name = "hashes"> Containing this hashes: </label></td>
		<td><input name = "hashes" /> </td>
	</tr>
	
 	<tr>
		<td><label name = "author"> Tweet's author: </label></td>
		<td><input name = "author" /> </td>
	</tr>
	<tr>
		<td><label name = "recipient"> Tweet's recipient: </label></td>
		<td><input name = "recipient" /> </td> 
	</tr>
	<tr>
		<td><label name = "mentioned"> Accounts mentioned: </label></td>
		<td><input name = "mentioned" /> </td>
	</tr>
	<tr>
		<td><label name = "date-since"> Since date: </label></td>
		<td><input name = "date-since" /> </td>
		<td><label name = "date-until"> Until date: </label></td>
		<td><input name = "date-until" /> </td>
	</tr>
	<tr>
		<td><label name = "tweets-per-page"> Enter number of tweets per page: </label></td>
		<td><input name = "tweets-per-page" value = "10" /> </td>
	</tr>
	<tr>
		<td><label name = "pages-num"> Enter number of pages: </label></td>
		<td><input name = "pages-num" value = "100" /> </td>
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