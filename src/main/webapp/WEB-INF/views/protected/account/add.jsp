<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ page language="java" trimDirectiveWhitespaces="true"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/WEB-INF/templates/stdhead.jsp">
	<jsp:param value="protected.account.add.title" name="title" />
</jsp:include>
</head>
<body>
	<jsp:include page="/WEB-INF/templates/navbar.jsp">
		<jsp:param value="protected.account" name="section" />
	</jsp:include>
	<div class="jumbotron subhead">
		<div class="container">
			<h1>Account</h1>
		</div>
	</div>
	<div class="container">
		<div class="row">
			<div class="span3 bs-docs-sidebar">
				<jsp:include page="/WEB-INF/templates/accountSidenav.jsp">
					<jsp:param value="protected.account.add" name="subsection" />
				</jsp:include>
			</div>
			<div class="span9">
				<div class="page-header">
					<h1>Add account</h1>
				</div>
				<spring:url value="/protected/account/addAccount" var="addAccount"></spring:url>
				<form:form method="POST" modelAttribute="addAccountForm"
					action="${addAccount}">
					<fieldset>
						<t:input path="username" label="Username" placeholder="username"></t:input>
						<t:password path="password" label="Password" />
						<t:password path="confirmPassword" label="Confirm password" />
						<t:checkbox label="Admin privilege (all privileges)" path="roleAdmin" />
						<t:checkbox label="View privilege" path="roleView" />
						<button class="btn btn-primary" type="submit">Save</button>
					</fieldset>
				</form:form>
			</div>
		</div>
	</div>
</body>
</html>