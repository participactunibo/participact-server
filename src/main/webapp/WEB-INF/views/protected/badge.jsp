<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ page language="java" trimDirectiveWhitespaces="true"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/WEB-INF/templates/stdhead.jsp">
	<jsp:param value="protected.badge.title" name="title" />
</jsp:include>
<script src="<c:url value="/resources/js/jquery.dataTables.js"/>"></script>
<script src="<c:url value="/resources/js/dataTables.bootstrap.js"/>"></script>
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/datatables.bootstrap.css"/>">
<script>
	/* Set the defaults for DataTables initialisation */

	$(document).ready(
		function() {
			$(".alert").alert();
			$('.deletbadge').click(
				function() {
				var id = $(this).data('id');
				var title = $(this).data('title');
				$('#modal-from-dom').bind('show', function() {
					$(this).find('#badgeIdSpan').text(id);
					$(this).find('#titleSpan').text(title);
					var removeBtn = $(this).find('.btn-danger');
					removeBtn.attr('href',function() {
						return $(this).attr('href').replace('{{ID}}',id);
					});
				}).modal({
					backdrop : true
				}).modal('show');
			});

			//"sPaginationType" : "bootstrap",
			$('#badgetable').dataTable(
				{
					"sDom" : "<'row'<'span4'l><'span4'f>r>t<'row'<'span4'i><'span4'p>>",
					"bPaginate": false,
					"oLanguage" : {
						"sLengthMenu" : "_MENU_ records per page"
					}
			});
	});
</script>
</head>
<body>
	<jsp:include page="/WEB-INF/templates/navbar.jsp">
		<jsp:param value="protected.badge" name="section" />
	</jsp:include>
	<div class="jumbotron subhead">
		<div class="container">
			<h1>Badge management</h1>
		</div>
	</div>
	<div class="container">
		<div class="row">
			<div class="span3 bs-docs-sidebar">
				<jsp:include page="/WEB-INF/templates/badgeSidenav.jsp">
					<jsp:param value="protected.badge" name="subsection" />
				</jsp:include>
			</div>
			<div class="span9">
				<div id="modal-from-dom" class="modal hide fade">
					<div class="modal-header">
						<a href="#" class="close" data-dismiss="modal">&times;</a>
						<h3>Delete Badge</h3>
					</div>
					<div class="modal-body">
						<p>
							You are about to delete badge #<span id="badgeIdSpan">0</span>:
							<span id="titleSpan">title</span>
						</p>
						<p>Do you want to proceed?</p>
					</div>
					<div class="modal-footer">
						<a href='<c:url value="/protected/badge/delete?id={{ID}}" />'
							class="btn btn-danger">Delete badge</a> <a href="#"
							class="btn" data-dismiss="modal" aria-hidden="true">Cancel</a>
					</div>
				</div>

				<div class="page-header">
					<h1>Badge management</h1>
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
				<h2>Total badges: ${totalBadges}</h2>
				<table class="table table-striped" id="badgetable">
					<thead>
						<tr>
							<th>Id</th>
							<th>Title</th>
							<th>Description</th>
							<th></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${badges}" var="badge">
							<tr>
								<td><c:out value="${badge.id}" /></td>
								<td><a href='<c:url value="/protected/badge/show/${badge.id}" />'><c:out value="${badge.title}" /></a></td>
								<td><c:out value="${badge.description}" /></td>
								<td><a class="btn editaccount" href='<c:url value="/protected/badge/edit/${badge.id}" />' type="button" >Edit</a>
									<button class="btn btn-warning deletbadge"
										type="button" data-id="${badge.id}"
										data-title="${badge.title}">Delete</button></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</body>
</html>