<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags"%>
<%@ taglib prefix="pa" uri="/WEB-INF/tlds/paserver-funcs.tld"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ page language="java" trimDirectiveWhitespaces="true"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/WEB-INF/templates/stdhead.jsp">
	<jsp:param value="protected.task.show.title" name="title" />
</jsp:include>
<script src="<c:url value="/resources/js/jquery.dataTables.js"/>"></script>
<script src="<c:url value="/resources/js/dataTables.bootstrap.js"/>"></script>
<script src="<c:url value="/resources/js/highcharts/highcharts.js"/>"></script>
<script
	src="<c:url value="/resources/js/highcharts/modules/exporting.js"/>"></script>
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/datatables.bootstrap.css"/>">

</head>
<body>
	<jsp:include page="/WEB-INF/templates/navbar.jsp">
		<jsp:param value="protected.webuser.task" name="section" />
	</jsp:include>
	<div class="jumbotron subhead">
		<div class="container">
			<h1>
				&quot;
				<c:out value="${task.name}" />
				&quot; summary
			</h1>
		</div>
	</div>
	<div class="container">
		<div class="row">
			<div class="span3 bs-docs-sidebar">
				<jsp:include page="/WEB-INF/templates/taskSidenav.jsp">
					<jsp:param value="protected.webuser.task" name="subsection" />
				</jsp:include>
			</div>
			<div class="span9">
				<div class="page-header">
					<h1>
						Sensing task &quot;
						<c:out value="${task.name}" />
						&quot; summary
					</h1>
				</div>
				<t:showtask task="${task}" />

				<c:forEach items="${actionPhotos}" var="entry">
					<p>
						<a class="btn"
							href='<c:url value="/protected/webuser/taskreport/show/assignedtask/${task.id}/action/${entry}/allphotos" />'>Show
							all photos for action <c:out value="${entry}"></c:out>
						</a>
					<p>
				</c:forEach>
				<hr />
				<h2>Task report</h2>
				<table class="table table-striped" id="taskreporttable">
					<thead>
						<tr>
							<th>Current state</th>
							<th>Accepted datetime</th>
							<th>Expiration datetime</th>
							<th></th>
						</tr>
					</thead>
					<tbody>
						<tr>

							<td><c:out value="${taskReport.currentState}"></c:out></td>
							<td><joda:format value="${taskReport.acceptedDateTime}"
									pattern="yyyy-MM-dd HH:mm" /></td>
							<td><joda:format value="${taskReport.expirationDateTime}"
									pattern="yyyy-MM-dd HH:mm" /></td>
							<td><a class="btn"
								href='<c:url value="/protected/webuser/taskreport/show/task/${task.id}/user" />'>View</a></td>
						</tr>
					</tbody>
				</table>


			</div>

		</div>
	</div>



</body>
</html>