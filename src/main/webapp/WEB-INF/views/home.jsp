<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page session="false" %>
<html>
<head>
	<title>TwitterFilter</title>
</head>
<body>
<h1>
	Welcome in TwitterFilter.  
</h1>
<P> Here you can list posts from Twitter and filter them using different conditions. </P>
<P> First we have to authorize you. Please go to given link and copy PIN number to input box.
Then just click Login button.</P>
<P> Authorization link <a href=${authorizationLink}>${authorizationLink}</a>  </P>
<form:form method = "POST" action = "/springmvc/login" >
<table>
	<tr>
		<td><label name = "PIN"> Your PIN number: </label></td>
		<td><input name = "PIN" /> </td>
	</tr>
    <tr>
    	<td colspan = "4">
    		<input style="width:100px;height:50px" type = "submit" value = "Login"/>
    	</td>
    </tr>
</table>
</form:form>
<P> The time on the server is ${serverTime}. </P>
</body>
</html>
