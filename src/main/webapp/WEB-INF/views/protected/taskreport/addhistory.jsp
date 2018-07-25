<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags"%>
<%@ taglib prefix="pa" uri="/WEB-INF/tlds/paserver-funcs.tld"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ page language="java" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/WEB-INF/templates/stdhead.jsp">
	<jsp:param value="protected.taskreport.show.title" name="title" />
</jsp:include>
<script src="<c:url value="/resources/js/jquery.dataTables.js"/>"></script>
<script src="<c:url value="/resources/js/dataTables.bootstrap.js"/>"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/datatables.bootstrap.css"/>">
<script>
	/* Set the defaults for DataTables initialisation */

	$(document).ready(function() {
		$('#taskreporttable').dataTable({
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
			<h1>
				Edit TaskReport
				<c:out value="${taskReport.id}" />
			</h1>
		</div>
	</div>
	<div class="container">
		<div class="row">
			<div class="span3 bs-docs-sidebar">
				<jsp:include page="/WEB-INF/templates/taskSidenav.jsp">
					<jsp:param value="protected.task" name="subsection" />
				</jsp:include>
			</div>
			<div class="span9">
				<div class="page-header">
					<h1>
						Task &quot;<a href='<c:url value="/protected/task/show/${taskReport.task.id}"/>'><c:out
								value="${taskReport.task.name}" /></a>&quot;, user <a
							href='<c:url value="/protected/user/show/${taskReport.user.id}" />'><c:out
								value="${taskReport.user.officialEmail}" /></a>
					</h1>
				</div>
				<h2>Add task history entry</h2>
				<spring:url value="/protected/taskreport/addhistory/${taskReport.id}" var="taskReportHistoryForm" />
				<form:form method="POST" modelAttribute="taskHistory" action="${taskReportHistoryForm}">
					<t:select items="${taskStates}" path="state" label="New task history entry"></t:select>
					<button class="btn btn-primary" type="submit">Ok</button>
					<a class="btn btn-warning" href='<c:url value="/protected/taskreport/show/${taskReport.id}" />' type="button">Cancel</a>
				</form:form>
				<h2>Current task history</h2>
				<table class="table table-striped" id="taskreporttable">
					<thead>
						<tr>
							<th>id</th>
							<th>Timestamp</th>
							<th>Task state</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${taskReport.history}" var="entry">
							<tr>
								<td><c:out value="${entry.id}" /></td>
								<td><joda:format value="${entry.timestamp}" pattern="yyyy-MM-dd HH:mm" /></td>
								<td><c:out value="${entry.state}" /></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</body>
</html>