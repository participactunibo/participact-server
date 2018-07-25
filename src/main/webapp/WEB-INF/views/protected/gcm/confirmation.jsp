<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ page language="java" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/WEB-INF/templates/stdhead.jsp">
	<jsp:param value="protected.dashboard.title" name="title" />
</jsp:include>
</head>
<body>
	<jsp:include page="/WEB-INF/templates/navbar.jsp">
		<jsp:param value="protected.task" name="section" />
	</jsp:include>
	<div class="jumbotron subhead">
		<div class="container">
			<h1>Log request</h1>
		</div>
	</div>
	<div class="container">
		<div class="row">
			<div class="span3 bs-docs-sidebar">
				<jsp:include page="/WEB-INF/templates/dashboardSidenav.jsp">
					<jsp:param value="protected.dashboard.gcm" name="subsection" />
				</jsp:include>
			</div>
			<div class="span9">
				<div class="page-header">
					<h1>GCM request sent</h1>
				</div>
				<spring:url value="/protected/dashboard" var="dashboard" />
				<a class="btn btn-primary" href="<c:url value="/protected/dashboard"/>">Ok</a>
			</div>
		</div>
	</div>
</body>
</html>