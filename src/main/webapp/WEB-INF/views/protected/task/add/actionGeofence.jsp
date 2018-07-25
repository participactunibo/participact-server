<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ page language="java" trimDirectiveWhitespaces="true"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/WEB-INF/templates/stdhead.jsp">
	<jsp:param value="protected.task.add.title" name="title" />
</jsp:include>
</head>
<body>
	<jsp:include page="/WEB-INF/templates/navbar.jsp">
		<jsp:param value="protected.task" name="section" />
	</jsp:include>
	<div class="jumbotron subhead">
		<div class="container">
			<h1>New Task</h1>
		</div>
	</div>
	<div class="container">
		<div class="row">
			<div class="span3 bs-docs-sidebar">
				<jsp:include page="/WEB-INF/templates/taskSidenav.jsp">
					<jsp:param value="protected.task.add" name="subsection" />
				</jsp:include>
			</div>
			<div class="span9">
				<div class="page-header">
					<h1>Add interest point action to task &quot;<c:out value="${task.name}" />&quot;</h1>
				</div>
				 
				   <%-- <table class="table table-striped" id="interestPointTable">
					<thead>
						<tr>
							<th>Choose</th>
							<th>Description</th>
							<th>Points</th>
							<th></th>
						</tr>
					</thead>
					<tbody>
					 <c:forEach items="${actionGeofence}" var="interestPoint">
					 <form:form modelAttribute="actionGeofenceChecked"  action="${flowExecutionUrl}">
							<tr>
							
							<td> <button class="btn btn-primary" type="submit" name="_eventId_check">Next</button>
							 <t:checkbox path="checked" label="check" checked="true"/>  </td>
								<td><c:out value="${interestPoint.description}" /></td>
								<td><c:out value="${interestPoint.interestPoints}" /></td>
							
							</form:form>
						</c:forEach> 
						
					</tbody>
				</table>   --%>

				<form:form modelAttribute="actionGeofence" action="${flowExecutionUrl}">
					<t:select items="${actionGeofenceList}" path="description" label="Point of Interest"></t:select>
					<button class="btn btn-danger" type="submit" name="_eventId_cancel">Cancel</button>
					<button class="btn btn-primary" type="submit" name="_eventId_next">Next</button>
				</form:form>
			</div>
		</div>
	</div>
</body>
</html>