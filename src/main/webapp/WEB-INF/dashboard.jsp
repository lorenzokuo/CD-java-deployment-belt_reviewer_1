<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Events Dashboard</title>
</head>
<body>
	<div class="header">
		<form id="logoutForm" method="POST" action="/logout">
	        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	        <input type="submit" value="Logout!" />
	    </form>
		<h1>Welcome <c:out value="${currentUser.firstName}"/></h1>
	</div>
	
	<div id="mainEvents">
		<h3>Here are some of the events in your state:</h3>
		<table cellspacing="15">
			<tr>
				<th>Name</th>
				<th>Date</th>
				<th>Location</th>
				<th>Host</th>
				<th>Action/Status</th>
			</tr>
		<c:forEach items="${mainEvents}" var="event">
			<tr>
				<td><a href="/events/${event.id}"><c:out value="${event.title}" /></a></td>
				<td><fmt:formatDate type="date" dateStyle="long" value="${event.date}" /></td>
				<td><c:out value="${event.city}" /></td>
				<td><c:out value="${event.host.firstName}" /></td>
				<td>
					<c:choose>
						<c:when test="${event.host.id == currentUser.id }">
							<a href="/events/edit/${event.id}">Edit</a>
							<a href="/events/delete/${event.id}">Delete</a>
						</c:when>
						<c:when test="${event.attendees.contains(currentUser)}">
							Joined!
							<a href="/events/leave/${event.id}">Cancel</a>
						</c:when>
						<c:otherwise>
							<a href="/events/join/${event.id}">Join</a>
						</c:otherwise>
					</c:choose>
				</td>
				
			</tr>	
		</c:forEach>
		</table>
	</div>
	<div id="otherEvents">
		<h3>Here are some of the events in your state:</h3>
		<table cellspacing="15">
			<tr>
				<th>Name</th>
				<th>Date</th>
				<th>Location</th>
				<th>State</th>
				<th>Host</th>
				<th>Action/Status</th>
			</tr>
		<c:forEach items="${otherEvents}" var="otherEvent">
			<tr>
				<td><a href="/events/${otherEvent.id}"><c:out value="${otherEvent.title}" /></a></td>
				<td><fmt:formatDate type="date" dateStyle="long" value="${otherEvent.date}" /></td>
				<td><c:out value="${otherEvent.city}" /></td>
				<td><c:out value="${otherEvent.state}" /></td>
				<td><c:out value="${otherEvent.host.firstName}" /></td>
				<td>
					<c:choose>
						<c:when test="${otherEvent.host.id == currentUser.id }">
							<a href="/events/edit/${otherEvent.id}">Edit</a>
							<a href="/events/delete/${otherEvent.id}">Delete</a>
						</c:when>
						<c:when test="${otherEvent.attendees.contains(currentUser)}">
							Joined!
							<a href="/events/leave/${otherEvent.id}">Cancel</a>
						</c:when>
						<c:otherwise>
							<a href="/events/join/${otherEvent.id}">Join</a>
						</c:otherwise>
					</c:choose>
				</td>
			</tr>	
		</c:forEach>
		</table>
	</div>
	
	<div id="createEvent">
		<h2>Create an Event</h2>
	
		<p><form:errors path="event.*"/></p>
		
		<form:form action="events/new" modelAttribute="event" method="POST">
			<p>
				<form:label path="title">Title: </form:label>
				<form:input path="title" />
			</p>
			<p>
				<label for="date">Date:</label>
				<input name="date" type="date"/>
			</p>			
			<p>
				<form:label path="city">Location: </form:label>
				<form:input path="city" />
 				<form:select path="state">
					<c:forEach items="${states}" var="stateAbrv">
						<form:option value="${stateAbrv}" label="${stateAbrv}"  />
					</c:forEach>
	            </form:select>
			</p>
			<input type="submit" value="Create Event" />
		</form:form>
	
	</div>
	
</body>
</html>