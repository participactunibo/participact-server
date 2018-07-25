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
	<jsp:param value="login.title" name="title" />
</jsp:include>
<c:if test="${not empty param.authenticationNok}">
	<script>
		$(document).ready(function() {
			$(".alert").alert();
			$('#loginerror').hide().fadeIn('slow');
		});
	</script>
</c:if>
</head>
<body>
	<jsp:include page="/WEB-INF/templates/navbar.jsp">
		<jsp:param value="login" name="section" />
	</jsp:include>
	<div class="jumbotron subhead">
		<div class="container">
			<h1>UniBo ParticipAct</h1>
		</div>
	</div>
	<div class="container">
		<section id="login">
			<div class="span4">
				<spring:url value="/j_spring_security_check" var="login" />
				<form action="${login}" method="post" class="well">
					<c:if test="${not empty param.authenticationNok}">
						<div class="alert alert-error fade in" id="loginerror">
							<button type="button" class="close" data-dismiss="alert">&times;</button>
							<spring:message code="label.login.failed"
								arguments="${SPRING_SECURITY_LAST_EXCEPTION.message}" />
						</div>
					</c:if>
					<fieldset>
						<div class="input-prepend">
							<span class="add-on"><i class="fa fa-user"></i></span> <input
								type="text" name="j_username"
								placeholder="nome.cognome@studio.unibo.it" />
						</div>
						<div class="input-prepend">
							<span class="add-on"><i class="fa fa-key"></i></span> <input
								type="password" name="j_password" placeholder="Password" />
						</div>
						<p>
							<a href='<c:url value="/recoverpassword"/>'>Hai dimenticato la password?</a>
						</p>
						<button class="btn btn-primary" type="submit">Login</button>
					</fieldset>
				</form>
			</div>
		</section>
	</div>
</body>
</html>