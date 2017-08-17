<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Edit Event</title>
</head>
<body>


	<div class="header">
		<form id="logoutForm" method="POST" action="/logout">
	        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	        <input type="submit" value="Logout!" />
	    </form>
	    
	
	<h1>Edit your event!</h1>
	<a href="/events">Dashboard</a>
	</div>

	<div id="createEvent">
	
		<p><form:errors path="editEvent.*"/></p>
		
		<form:form action="/events/edit/${editEvent.id}" modelAttribute="editEvent" method="POST">
			<p>
				<form:label path="title">Title: </form:label>
				<form:input path="title" value="${editEvent.title}"/>
			</p>
			<p>
				<label for="date">Date:</label>
				<input value="${dateStr}" name="editDate" type="date" />
			</p>			
			<p>
				<form:label path="city">Location: </form:label>
				<form:input path="city" value="${editEvent.city}" />
 				<form:select path="state">
					<c:forEach items="${states}" var="stateAbrv">
						<c:choose>
							<c:when test="${stateAbrv.equals(editEvent.state)}">
								<form:option selected="selected" value="${stateAbrv}" label="${stateAbrv}" />
							</c:when>
							<c:otherwise>
								<form:option value="${stateAbrv}" label="${stateAbrv}" />
							</c:otherwise>
						</c:choose>
					</c:forEach>
	            </form:select>
			</p>
			<input type="submit" value="Update Event" />
		</form:form>
	
	</div>	
</body>
</html>