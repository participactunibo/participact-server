<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ page language="java" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/WEB-INF/templates/stdhead.jsp">
	<jsp:param value="protected.dashboard.title" name="title" />
</jsp:include>
<script src="<c:url value="/resources/js/jquery.dataTables.js"/>"></script>
<script src="<c:url value="/resources/js/dataTables.bootstrap.js"/>"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/datatables.bootstrap.css"/>">
<script>
	/* Set the defaults for DataTables initialisation */

	$(document).ready(function() {
		$('#scorestable').dataTable({
			"bPaginate" : false,
			"oLanguage" : {
				"sLengthMenu" : "_MENU_ records per page"
			}
		});
	});
</script>
</head>
<body>
	<jsp:include page="/WEB-INF/templates/navbar.jsp">
		<jsp:param value="protected.dashboard" name="section" />
	</jsp:include>
	<div class="jumbotron subhead">
		<div class="container">
			<h1>Monthly score</h1>
		</div>
	</div>
	<div class="container">
		<div class="row">
			<div class="span3 bs-docs-sidebar">
				<jsp:include page="/WEB-INF/templates/dashboardSidenav.jsp">
					<jsp:param value="protected.dashboard.scores" name="subsection" />
				</jsp:include>
			</div>
			<div class="span9">
				<h1>Add a new monthly score</h1>
				<spring:url value="/protected/scores/add" var="addScore" />
				<form:form method="POST" modelAttribute="monthlyTargetScore" action="${addScore}">
					<fieldset>
						<t:input path="year" label="Year" />
						<t:input path="month" label="Month" />
						<t:input path="targetScore" label="Target score" />
						<button class="btn btn-primary" type="submit">Save</button>
						<a class="btn btn-danger" type="submit" href='<c:url value="/protected/scores/list"/>'>Cancel</a>
					</fieldset>
				</form:form>
			</div>
		</div>
	</div>
</body>
</html>