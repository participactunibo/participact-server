<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags"%>
<%@ page language="java" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/WEB-INF/templates/stdhead.jsp">
	<jsp:param value="protected.dashboard.logs.title" name="title" />
</jsp:include>
<script src="<c:url value="/resources/js/jquery.dataTables.js"/>"></script>
<script src="<c:url value="/resources/js/dataTables.bootstrap.js"/>"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/datatables.bootstrap.css"/>">
<script>
	/* Set the defaults for DataTables initialisation */

	$(document).ready(function() {
		$('#logstable').dataTable({
			"bPaginate" : false,
			"oLanguage" : {
				"sLengthMenu" : "_MENU_ records per page"
			}
		});

		$('.deletelog').click(function() {
			var id = $(this).data('id');
			var filename = $(this).data('filename');
			$('#modal-from-dom').bind('show', function() {
				$(this).find('#logIdSpan').text(id);
				$(this).find('#logSpan').text(filename);
				var removeBtn = $(this).find('.btn-danger');
				removeBtn.attr('href', function() {
					return $(this).attr('href').replace('{{ID}}', id);
				});
			}).modal({
				backdrop : true
			}).modal('show');
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
					<jsp:param value="protected.dashboard.logs.list" name="subsection" />
				</jsp:include>
			</div>
			<div class="span9">
				<div id="modal-from-dom" class="modal hide fade">
					<div class="modal-header">
						<a href="#" class="close" data-dismiss="modal">&times;</a>
						<h3>Delete Log</h3>
					</div>
					<div class="modal-body">
						<p>
							You are about to delete log #<span id="logIdSpan">0</span>: <span id="logSpan">filename</span>
						</p>
						<p>Do you want to proceed?</p>
					</div>
					<div class="modal-footer">
						<a href='<c:url value="/protected/logs/delete?id={{ID}}" />' class="btn btn-danger deletelog">Delete log</a> <a
							href="#" class="btn" data-dismiss="modal" aria-hidden="true">Cancel</a>
					</div>
				</div>
				<h1>List of logs.</h1>
				<table class="table table-striped" id="logstable">
					<thead>
						<tr>
							<th>Id</th>
							<th>User</th>
							<th>Filename</th>
							<th>Upload time</th>
							<th></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${logs}" var="log">
							<tr>
								<td><a href='<c:url value="/protected/logs/download/${log.id}" />'><c:out value="${log.id}" /></a></td>
								<td><c:out value="${log.user.officialEmail}" /></td>
								<td><c:out value="${log.binaryDocument.filename}" /></td>
								<td><joda:format value="${log.uploadTime}" pattern="yyyy-MM-dd HH:mm" /></td>
								<td><a class="btn btn-warning deletelog" type="button" data-id="${log.id}"
									data-filename="${log.binaryDocument.filename}">Delete</a>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</body>
</html>