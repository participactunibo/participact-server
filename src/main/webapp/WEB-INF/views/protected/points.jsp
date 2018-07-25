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
	<jsp:param value="protected.points.title" name="title" />
</jsp:include>
<script src="<c:url value="/resources/js/jquery.dataTables.js"/>"></script>
<script src="<c:url value="/resources/js/dataTables.bootstrap.js"/>"></script>
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/datatables.bootstrap.css"/>">
<script>
	/* Set the defaults for DataTables initialisation */

	$(document).ready(
		function() {
			//"sPaginationType" : "bootstrap",
			$('#pointstable').dataTable(
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
		<jsp:param value="protected.points" name="section" />
	</jsp:include>
	<div class="jumbotron subhead">
		<div class="container">
			<h1>Points</h1>
		</div>
	</div>
	<div class="container">
		<div class="row">
			<div class="span3 bs-docs-sidebar">
				<jsp:include page="/WEB-INF/templates/pointsSidenav.jsp">
					<jsp:param value="protected.badge" name="subsection" />
				</jsp:include>
			</div>
			<div class="span9">

				<div class="page-header">
					<h1>Points</h1>
				</div>
				<h2>Points</h2>
				<table class="table table-striped" id="pointstable">
					<thead>
						<tr>
							<th>User</th>
							<th>Value</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${scores}" var="score">
							<tr>
								<td><a href='<c:url value="/protected/user/show/${score.user.id}" />'><c:out value="${score.user.officialEmail}" /></a></td>
								<td><c:out value="${score.value}" /></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</body>
</html>