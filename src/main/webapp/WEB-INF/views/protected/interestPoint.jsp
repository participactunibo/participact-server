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
	<jsp:param value="protected.interestPoint.title" name="title" />
</jsp:include>
<script src="<c:url value="/resources/js/jquery.dataTables.js"/>"></script>
<script src="<c:url value="/resources/js/dataTables.bootstrap.js"/>"></script>
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/datatables.bootstrap.css"/>">

<script>
	$(document).ready(function() {
		$(".alert").alert();
		$('.deletinterestpoint').click(function() {

			var id = $(this).data('id');
			var description = $(this).data('description');

			$('#modal-from-dom').bind('show', function() {
				$(this).find('#interestPointIdSpan').text(id)
				$(this).find('#descriptionSpan').text(description)
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
		<jsp:param value="protected.interestPoint" name="section" />
	</jsp:include>
	<div class="jumbotron subhead">
		<div class="container">
			<h1>Interest Point</h1>
		</div>
	</div>
	<div class="container">
		<div class="row">
			<div class="span3 bs-docs-sidebar">
				<jsp:include page="/WEB-INF/templates/interestPointSidenav.jsp">
					<jsp:param value="protected.interestPoint" name="subsection" />
				</jsp:include>
			</div>
			<div class="span9">
				<div id="modal-from-dom" class="modal hide fade">
					<div class="modal-header">
						<a href="#" class="close" data-dismiss="modal">&times;</a>
						<h3>Delete Interest Point</h3>
					</div>
					<div class="modal-body">
						<p>
							You are about to delete Interest Point #<span
								id="interestPointIdSpan">0</span>: <span id="descriptionSpan">description</span>
						</p>
						<p>Do you want to proceed?</p>
					</div>
					<div class="modal-footer">
						<a
							href='<c:url value="/protected/interestPoint/delete?id={{ID}}" />'
							class="btn btn-danger">Delete interest point</a> <a href="#"
							class="btn" data-dismiss="modal" aria-hidden="true">Cancel</a>
					</div>
				</div>

				<div class="page-header">
					<h1>Interest Point management</h1>
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
				<h2>Total Interest Point: ${totalInterestPoint}</h2>
				<table class="table table-striped" id="interestPointtable">
					<thead>
						<tr>
							<th>Id</th>
							<th>Description</th>
							<th>lat</th>
							<th>lon</th>
							<th></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${interestPoint}" var="interestPoint">
							<tr>
								<td><c:out value="${interestPoint.id}" /></td>
								<td><c:out value="${interestPoint.description}" /></td>
								<td><c:out value="${interestPoint.lat}" /></td>
								<td><c:out value="${interestPoint.lon}" /></td>
								<td><%-- <a class="btn editaccount"
									href='<c:url value="/protected/badge/edit/${interestPoint.id}" />'
									type="button">Edit</a> --%>
									<button class="btn btn-warning deletinterestpoint"
										type="button" data-id="${interestPoint.id}"
										data-description="${interestPoint.description}">Delete</button></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</body>
</html>