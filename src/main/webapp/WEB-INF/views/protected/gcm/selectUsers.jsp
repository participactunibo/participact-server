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
			<h1>Select users for GCM message</h1>
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
				<h1>Select users for GCM message</h1>
				<spring:url value="/protected/gcm/send" var="formAction" />
				<form:form method="POST" modelAttribute="selectUsersGCMForm" action="${formAction}">
					<fieldset>
						<t:select items="${gcmTypes}" path="gcmType" />
						<t:textarea path="userList" cssClass="input-block-level"
							label="List of target users. Users are identified by their official e-mail. Different users can be separated by a comma, semicolon, newline or any other character matching [^a-zA-Z0-9@.]." />
						<button class="btn btn-primary" type="submit">Submit</button>
						<a class="btn btn-danger" type="submit" href='<c:url value="/protected/dashboard"/>'>Cancel</a>
					</fieldset>
				</form:form>
			</div>
		</div>
	</div>
</body>
</html>