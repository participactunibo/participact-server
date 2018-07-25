<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ page language="java" trimDirectiveWhitespaces="true"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/WEB-INF/templates/stdhead.jsp">
	<jsp:param value="index.title" name="title"/>
</jsp:include>
</head>
<body>
	<jsp:include page="/WEB-INF/templates/navbar.jsp">
		<jsp:param value="index" name="section"/>
	</jsp:include>
	<div class="jumbotron subhead">
		<div class="container">
			<h1>UniBo ParticipAct</h1>
		</div>
	</div>
	<div class="container">
		<div class="row">
			<h2>
				User with username <c:out value="${user.email}" /> autenticated.
			</h2>
		</div>
	</div>
</body>
</html>