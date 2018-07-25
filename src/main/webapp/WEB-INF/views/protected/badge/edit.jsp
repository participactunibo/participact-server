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
	<jsp:param value="protected.badge.edit.title" name="title" />
</jsp:include>
</head>
<body>
	<jsp:include page="/WEB-INF/templates/navbar.jsp">
		<jsp:param value="protected.badge" name="section" />
	</jsp:include>
	<div class="jumbotron subhead">
		<div class="container">
			<h1>Badge</h1>
		</div>
	</div>
	<div class="container">
		<div class="row">
			<div class="span3 bs-docs-sidebar">
				<jsp:include page="/WEB-INF/templates/badgeSidenav.jsp">
					<jsp:param value="protected.badge.edit" name="subsection" />
				</jsp:include>
			</div>
			<div class="span9">
				<div class="page-header">
					<h1>Edit ParticipAct badge</h1>
				</div>
				<c:if test="${not empty formerror}">
					<div class="alert alert-error fade in" id="loginerror">
						<button type="button" class="close" data-dismiss="alert">&times;</button>
						Errore nella creazione del badge. Ricontrollare il form.
					</div>
				</c:if>
				<spring:url value="/protected/badge/edit/${badgeId}" var="editBadgeForm"></spring:url>
				<form:form method="POST" modelAttribute="editBadgeForm" action="${editBadgeForm}" enctype="multipart/form-data">
					<fieldset>
						<div class="span9">
							<div class="span6">
								<h2>Dettagli</h2>
								<t:input path="title" label="Titolo*" placeholder="Titolo" inputprepend="fa fa-info-circle" />
								<t:input path="description" label="Descrizione*" placeholder="descrizione" inputprepend="fa fa-info-circle" />
							</div>
						</div>
						<div class="span2">
							<button class="btn btn-primary" type="submit">Save</button>
							<a class="btn btn-danger" type="submit" href='<c:url value="/protected/badge"/>'>Cancel</a>
						</div>
					</fieldset>
				</form:form>
			</div>
		</div>
	</div>
</body>
</html>