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
	<jsp:param value="protected.user.add.title" name="title" />
</jsp:include>
</head>
<body>
	<jsp:include page="/WEB-INF/templates/navbar.jsp">
		<jsp:param value="protected.user" name="section" />
	</jsp:include>
	<div class="jumbotron subhead">
		<div class="container">
			<h1>User account confirmation</h1>
		</div>
	</div>
	<div class="container">
		<div class="row">
			<div class="span12">
				<div class="page-header">
					<h1>
						Utente <c:out value="${user.officialEmail}" /> (<c:out value="${user.name} ${user.surname}" />) creato.
					</h1>
				</div>
				<div class="row">
					<a class="btn btn-primary btn-large "
						href='<c:url value="/protected/user" />'>Ok</a>
				</div>
			</div>
		</div>
	</div>
</body>
</html>