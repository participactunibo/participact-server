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
	var loading = false;
	$(document).ready(function() {
		$('#taskreporttable').dataTable({
			"bPaginate" : false,
			"oLanguage" : {
				"sLengthMenu" : "_MENU_ records per page"
			}
		});
		
		$('#updateResult').click(function(e) {
			if (loading)
				return;
			loading = true;
		    e.preventDefault();
		    //do other stuff when a click happens
		    $('#updateResult').toggleClass("disabled", true);
		    window.location.href = '<c:url value="/protected/taskreport/updateresult/${taskReport.taskResult.id}"/>';
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
				&quot;<c:out value="${taskReport.task.name}" />&quot; summary
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
								value="${taskReport.task.name}" /></a>&quot;, user
						<t:userid user="${taskReport.user}" link="true" />
					</h1>
				</div>
				<h2>Task history</h2>
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
				<p>
					<a class="btn btn-primary" href='<c:url value="/protected/taskreport/addhistory/${taskReport.id}" />'>Add history entry</a>
				</p>
				<p>Last data update: <joda:format value="${taskReport.taskResult.lastDataUpdate}" pattern="yyyy-MM-dd HH:mm:ss" /></p>
				<p><a id="updateResult" class="btn">Force data update</a></p>
				<h2>Task actions</h2>
				<ul>
					<c:forEach items="${orderedActions}" var="action">
						<li><t:showaction action="${action}" compact="false" /> <a class="btn"
							href='<c:url value="/protected/taskreport/show/task/${taskReport.task.id}/user/${taskReport.user.id}/action/${action.id}" />'>Show
								data</a></li>
					</c:forEach>
				</ul>
			</div>
		</div>
	</div>
</body>
</html>