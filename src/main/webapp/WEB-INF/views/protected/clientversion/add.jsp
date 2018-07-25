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
		<jsp:param value="protected.dashboard" name="section" />
	</jsp:include>
	<div class="jumbotron subhead">
		<div class="container">
			<h1>Add new Client Software Version</h1>
		</div>
	</div>
	<div class="container">
		<div class="row">
			<div class="span3 bs-docs-sidebar">
				<jsp:include page="/WEB-INF/templates/dashboardSidenav.jsp">
					<jsp:param value="protected.dashboard.clientversion" name="subsection" />
				</jsp:include>
			</div>
			<div class="span9">
				<h1>Input new client software version</h1>
				<spring:url value="/protected/clientversion/add" var="formAction" />
				<form:form method="POST" modelAttribute="clientSWVersion" action="${formAction}">
					<t:input path="version" label="Input the new client software version"></t:input>
					<button class="btn btn-primary" type="submit">Submit</button>
					<a class="btn btn-danger" type="submit" href='<c:url value="/protected/clientversion/list"/>'>Cancel</a>
				</form:form>
			</div>
		</div>
	</div>
</body>
</html>