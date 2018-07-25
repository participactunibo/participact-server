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
	<jsp:param value="protected.account.title" name="title" />
</jsp:include>
<script>
	$(document).ready(function() {
		$(".alert").alert();
		$('.deleteaccount').click(function() {

			var id = $(this).data('id');
			var username = $(this).data('username');

			$('#modal-from-dom').bind('show', function() {
				$(this).find('#accountIdSpan').text(id)
				$(this).find('#accountUsernameSpan').text(username)
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
		<jsp:param value="protected.account" name="section" />
	</jsp:include>
	<div class="jumbotron subhead">
		<div class="container">
			<h1>Account</h1>
		</div>
	</div>
	<div class="container">
		<div class="row">
			<div class="span3 bs-docs-sidebar">
				<jsp:include page="/WEB-INF/templates/accountSidenav.jsp">
					<jsp:param value="protected.account" name="subsection" />
				</jsp:include>
			</div>
			<div class="span9">
				<div id="modal-from-dom" class="modal hide fade">
					<div class="modal-header">
						<a href="#" class="close" data-dismiss="modal">&times;</a>
						<h3>Delete Account</h3>
					</div>
					<div class="modal-body">
						<p>
							You are about to delete account #<span id="accountIdSpan">0</span>:
							<span id="accountUsernameSpan">username</span>
						</p>
						<p>Do you want to proceed?</p>
					</div>
					<div class="modal-footer">
						<a href='<c:url value="/protected/account/delete?id={{ID}}" />'
							class="btn btn-danger">Delete account</a> <a href="#" class="btn"
							data-dismiss="modal" aria-hidden="true">Cancel</a>
					</div>
				</div>

				<div class="page-header">
					<h1>Account management</h1>
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
				<h2>Total accounts: ${totalAccounts}</h2>
				<table class="table table-striped">
					<thead>
						<tr>
							<td>Id</td>
							<td>Username</td>
							<td>Creation date</td>
							<td></td>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${accounts}" var="account">
							<tr>
								<td><c:out value="${account.id}" /></td>
								<td><c:out value="${account.username}" /></td>
								<td><c:out value="${account.creationDate}" /></td>
								<td><button class="btn btn-warning deleteaccount"
										type="button" data-id="${account.id}"
										data-username="${account.username}">Delete</button></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</body>
</html>