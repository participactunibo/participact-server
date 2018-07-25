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
	<jsp:param value="protected.user.title" name="title" />
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
			$('.deleteaccount').click(
				function() {
				var id = $(this).data('id');
				var email = $(this).data('email');
				$('#modal-from-dom').bind('show', function() {
					$(this).find('#userIdSpan').text(id);
					$(this).find('#emailSpan').text(email);
					var removeBtn = $(this).find('.btn-danger');
					removeBtn.attr('href',function() {
						return $(this).attr('href').replace('{{ID}}',id);
					});
				}).modal({
					backdrop : true
				}).modal('show');
			});

			//"sPaginationType" : "bootstrap",
			$('#usertable').dataTable(
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
		<jsp:param value="protected.user" name="section" />
	</jsp:include>
	<div class="jumbotron subhead">
		<div class="container">
			<h1>User management</h1>
		</div>
	</div>
	<div class="container">
		<div class="row">
			<div class="span3 bs-docs-sidebar">
				<jsp:include page="/WEB-INF/templates/userSidenav.jsp">
					<jsp:param value="protected.user" name="subsection" />
				</jsp:include>
			</div>
			<div class="span9">
				<div id="modal-from-dom" class="modal hide fade">
					<div class="modal-header">
						<a href="#" class="close" data-dismiss="modal">&times;</a>
						<h3>Delete User Account</h3>
					</div>
					<div class="modal-body">
						<p>
							You are about to delete account #<span id="userIdSpan">0</span>:
							<span id="emailSpan">username</span>
						</p>
						<p>Do you want to proceed?</p>
					</div>
					<div class="modal-footer">
						<a href='<c:url value="/protected/user/delete?id={{ID}}" />'
							class="btn btn-danger">Delete user account</a> <a href="#"
							class="btn" data-dismiss="modal" aria-hidden="true">Cancel</a>
					</div>
				</div>

				<div class="page-header">
					<h1>User management</h1>
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
				<h2>Total accounts: ${totalUsers}</h2>
				<table class="table table-striped" id="usertable">
					<thead>
						<tr>
							<th>Id</th>
							<th>E-mail</th>
							<th>Name</th>
							<th>Surname</th>
							<th></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${users}" var="user">
							<tr>
								<td><c:out value="${user.id}" /></td>
								<td><a href='<c:url value="/protected/user/show/${user.id}" />'><c:out value="${user.officialEmail}" /></a></td>
								<td><c:out value="${user.name}" /></td>
								<td><c:out value="${user.surname}" /></td>
								<td><a class="btn editaccount" href='<c:url value="/protected/user/edit/${user.id}" />' type="button" >Edit</a>
									<button class="btn btn-warning deleteaccount"
										type="button" data-id="${user.id}"
										data-email="${user.officialEmail}">Delete</button></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</body>
</html>