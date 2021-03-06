<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page session="false" %>
<html>
<head>
	<title>TwitterFilter</title>
	<link href="webjars/bootstrap/3.2.0/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<h1>
	Successfully logged in.
</h1>
<h2> 
	You can get your home timeline.
</h2>
<form:form method = "POST" action = "/springmvc/login/getHomeTimeline" >
<table>
	<tr>
		<td><label name = "tweets-num"> Enter number of tweets: </label></td>
		<td><input name = "tweets-num" value = "100" /> </td>
	</tr>
	<tr>
		<td><input style="width:150px;height:50px" type = "submit" value = "Get home timeline"/></td>
	</tr>
</table>
</form:form>

<h2>
	Or you can search for tweets using different filters.
</h2>
<P> Please do not forget to enter number of pages. </P>	
<P> Date format is: yyyy-mm-dd. Date have to be used with other filters. </P>
<P> When using none of this words filter you have to use other filter to get some tweets </P>
<form:form method = "POST" action = "/springmvc/login/filterTweets" >
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

<h1>Tweets:</h1>  
<table class="table">
  <thead>
    <tr>
      <th>Content</th>
      <th>User</th>
      <th>Date</th>
    </tr>
  </thead>
  <tbody>
   <c:forEach var="tweet" items="${tweets}">   
   <tr>  
   <td>${tweet.message}</td>  
   <td>${tweet.author} @${tweet.screenName}</td>  
   <td>${tweet.date}</td>  
   </tr>  
   </c:forEach> 
  </tbody>
</table>

<h2> Save tweets to a file: </h2>
 <a href="<c:url value='/login/download/txt' />">Download tweets as text file.</a>

</body>
</html>