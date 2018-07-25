<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ page language="java" trimDirectiveWhitespaces="true"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/WEB-INF/templates/stdhead.jsp">
	<jsp:param value="protected.task.title" name="title" />
</jsp:include>

<script src="<c:url value="/resources/js/jquery-1.9.0.js"/>"></script>

<script src="<c:url value="/resources/js/bootstrap.min.js"/>"></script>


<script src="<c:url value="/resources/js/jquery.dataTables.js"/>"></script>
<script src="<c:url value="/resources/js/dataTables.bootstrap.js"/>"></script>
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/datatables.bootstrap.css"/>">




<script>
	/* Set the defaults for DataTables initialisation */

	$(document)
			.ready(
					function() {

						//"sPaginationType" : "bootstrap",
						$('#usertasktable')
								.dataTable(
										{
											"sDom" : "<'row'<'span4'l><'span4'f>r>t<'row'<'span4'i><'span4'p>>",
											"bPaginate" : false,
											"oLanguage" : {
												"sLengthMenu" : "_MENU_ records per page"
											}
										});

					});

	$(document)
			.ready(
					function() {

						//"sPaginationType" : "bootstrap",
						$('#admintasktable')
								.dataTable(
										{
											"sDom" : "<'row'<'span4'l><'span4'f>r>t<'row'<'span4'i><'span4'p>>",
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
		<jsp:param value="protected.task" name="section" />
	</jsp:include>
	<div class="jumbotron subhead">
		<div class="container">
			<h1>Task management</h1>
		</div>
	</div>
	<div class="container">
		<div class="row">
			<div class="span3 bs-docs-sidebar">
				<jsp:include page="/WEB-INF/templates/taskSidenav.jsp">
					<jsp:param value="protected.pipelinetask" name="subsection" />
				</jsp:include>
			</div>
			<div class="span9">
				<div class="page-header">
					<h1>Task management</h1>
				</div>
				<c:if test="${successmessage != null}">
					<div class="alert alert-block alert-success fade in"
						id="successmessage">
						<button type="button" class="close" data-dismiss="alert">&times;</button>
						<c:out value="${successmessage}" />
					</div>
				</c:if>
				<c:if test="${errormessage != null}">
					<div class="alert alert-block alert-error fade in"
						id="errormessage">
						<button type="button" class="close" data-dismiss="alert">&times;</button>
						<c:out value="${errormessage}" />
					</div>
				</c:if>
				<h2>Total tasks: ${totalTasks}</h2>
				<h1>Admin Tasks</h1>
				<table class="table table-striped" id="admintasktable">
					<thead>
						<tr>
							<th>Id</th>
							<th>Start date</th>
							<th>Name</th>
							<th></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${adminTasks}" var="task">
							<tr>
								<td><c:out value="${task.id}" /></td>
								<td><joda:format value="${task.start}"
										pattern="yyyy-MM-dd HH:mm" /></td>
								<td><a
									href='<c:url value="/protected/task/show/${task.id}" />'><c:out
											value="${task.name}" /></a></td>
								<td><c:if test="${not task.isExpired()}">
										<a class="btn"
											href='<c:url value="/protected/task/addUsers?taskId=${task.id}" />'>Add
											users</a>

									</c:if> <c:if test="${task.isExpired()}">
										Expired
									</c:if></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</body>
</html>