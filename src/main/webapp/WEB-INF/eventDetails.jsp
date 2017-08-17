<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Event Details</title>
</head>
<body>
	<div class="header">
		<form id="logoutForm" method="POST" action="/logout">
	        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	        <input type="submit" value="Logout!" />
	    </form>
	    
	</div>
	<h1><c:out value="${event.title}" /></h1>
	<a href="/events">Dashboard</a>
	
	<h4>Host: <c:out value="${event.host.firstName}" /></h4>
	<h4>Date: <fmt:formatDate type="date" dateStyle="long" value="${event.date}" /></h4>
	<h4>Location: <c:out value="${event.city}" /> <c:out value="${event.state}" /></h4>
	<h4>Number of attendees: <c:out value="${event.attendees.size()}" /></h4>
	<c:if test="${event.attendees.size() > 0 }">
		<div class="table">
		<h3>Attendees: </h3>
		<table cellspacing="15">
			<tr>
				<th>Name</th>
				<th>Location</th>
			</tr>
		<c:forEach items="${event.attendees}" var="attendee">
			<tr>
				<td><c:out value="${attendee.firstName}" /> <c:out value="${attendee.lastName}" /></td>
				<td><c:out value="${attendee.city}" /></td>
			</tr>
		</c:forEach>
		</table>
		</div>
	</c:if>	
	<p>Number of comments: <c:out value="${event.comments.size()}" />
	<c:if test="${event.comments.size() > 0}">
 		<div class="list">
			<ul>
			<c:forEach items="${event.comments}" var="comment">
				<li><c:out value="${comment.user.firstName}"/>: <c:out value="${comment.content}" /></li>
			</c:forEach>
			</ul>
		</div>
	</c:if>
	
	<div class="form">
		<p><form:errors path="comment.*"/></p>
		<form:form action="/comments/new/${event.id}" modelAttribute="comment" method="POST">
			<form:label path="content">Add Comment:</form:label>
			<form:input path="content"/>
			
			<input type="submit" value="Submit" />
		</form:form>
	
	
	</div>
	
</body>
</html>