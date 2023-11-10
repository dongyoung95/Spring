<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<h1>Error! ${exception.getMessage() }</h1>

<ul>
	<c:forEach var="stack" items="${exception.getStackTrace() }">
		<li>${stack.toString() }</li>
	</c:forEach>
</ul>