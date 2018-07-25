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
		<section id="reset">
			<div class="span6">
				<p>
					Ciao
					<c:out value="${recoverPassword.user.name}" />, scegli
					la tua nuova password. Ricorda che dovrai usare la nuova
					password anche sul tuo telefono.
				</p>
				<form:form modelAttribute="resetPasswordForm"
					action="${flowExecutionUrl}" class="well">
					<fieldset>
						<t:password path="password" label="Password*"
							inputprepend="fa fa-key" />
						<t:password path="confirmPassword" label="Conferma password*"
							inputprepend="fa fa-key" />
						<button class="btn btn-primary" type="submit"
							name="_eventId_change">Modifica</button>
						<button class="btn btn-danger" type="submit"
							name="_eventId_cancel">Annulla</button>
					</fieldset>
				</form:form>
			</div>
		</section>
	</div>
</body>
</html>