<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags"%>
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
				<h1>List of monthly scores</h1>
				<p>
					<a class="btn" type="button" href='<c:url value="/protected/scores/add" />'">Add a new score</a>
				</p>
				<table class="table table-striped" id="scorestable">
					<thead>
						<tr>
							<th>Id</th>
							<th>Year</th>
							<th>Month</th>
							<th>Target score</th>
							<th></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${scores}" var="score">
							<tr>
								<td><c:out value="${score.id}" /></td>
								<td><c:out value="${score.year}" /></td>
								<td><c:out value="${score.month}" /></td>
								<td><c:out value="${score.targetScore}" /></td>
								<td><a class="btn btn-warning deletelog" type="button" data-id="${score.id}">Delete</a>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</body>
</html>