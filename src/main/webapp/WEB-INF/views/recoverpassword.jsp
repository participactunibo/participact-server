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
	<jsp:param value="reset.title" name="title" />
</jsp:include>
<c:if test="${not empty param.authenticationNok}">
	<script>
		$(document).ready(function() {
			$(".alert").alert();
			$('#emailerror').hide().fadeIn('slow');
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
				<spring:url value="/recoverpassword" var="recoverpassword" />
				<form:form method="POST" modelAttribute="recoverPasswordForm"
					action="${recoverpassword}" class="well">
					<fieldset>
						<t:input path="email" label="E-mail ufficiale di ateneo"
							placeholder="nome.cognome@studio.unibo.it" stringinputprepend="@" />
						<button class="btn btn-primary" type="submit">Recupera</button>
					</fieldset>
				</form:form>
			</div>
		</section>
	</div>
</body>
</html>