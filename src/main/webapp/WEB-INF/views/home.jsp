<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
</head>
<body>
<h1>
	Hello world!  
</h1>

<P>  The time on the server is ${serverTime}. </P>
<P>  Authorization link	<a href=${authorizationLink}>Link</a>  </P>
<form:form method = "POST" action = "/springmvc/login" >
<table>
<tr>
	<td><form:label path = "PIN"> PIN </form:label></td>
	<td><form:input path = "PIN" /> </td>
</tr>
     <tr>
               <td colspan = "2">
                  <input type = "submit" value = "Submit"/>
               </td>
            </tr>
</table>
</form:form>
</body>
</html>
