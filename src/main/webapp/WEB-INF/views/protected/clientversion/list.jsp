<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags"%>
<%@ page language="java" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/WEB-INF/templates/stdhead.jsp">
	<jsp:param value="protected.dashboard.clientversion.title" name="title" />
</jsp:include>
<script src="<c:url value="/resources/js/jquery.dataTables.js"/>"></script>
<script src="<c:url value="/resources/js/dataTables.bootstrap.js"/>"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/datatables.bootstrap.css"/>">
<script>
	/* Set the defaults for DataTables initialisation */

	$(document).ready(function() {
		$('#csvtable').dataTable({
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
			<h1>Dashboard</h1>
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
				<h1>Current latest release: <c:out value="${latestVersion}"/></h1>
				<p>
				<a class="btn btn-primary" type="button" href='<c:url value="/protected/clientversion/add" />'>Add new Client Software Version</a>
				</p>
				<c:if test="${not empty successcreation}">
					<div class="alert alert-success fade in" id="success">
						<button type="button" class="close" data-dismiss="alert">&times;</button>
						New Client Software Version successfully created.
					</div>
				</c:if>
				<h2>List of client software releases.</h2>
				<table class="table table-striped" id="csvtable">
					<thead>
						<tr>
							<th>Id</th>
							<th>Release date</th>
							<th>Version number</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${csvs}" var="csv">
							<tr>
								<td><c:out value="${csv.id}" /></td>
								<td><joda:format value="${csv.creationDate}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
								<td><c:out value="${csv.version}" /></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</body>
</html>