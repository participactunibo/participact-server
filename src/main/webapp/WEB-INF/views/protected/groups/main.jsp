<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ page language="java" trimDirectiveWhitespaces="true"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html>
<head>
<jsp:include page="/WEB-INF/templates/stdhead.jsp">
	<jsp:param value="protected.groups.title" name="title" />
</jsp:include>

<script>
	var myApp;
	myApp = myApp
			|| (function() {
				var pleaseWaitDiv = $('<div class="modal hide" id="pleaseWaitDialog" data-backdrop="static" data-keyboard="false"><div class="modal-header"><h1>Processing...</h1></div><div class="modal-body"><div class="progress progress-striped active"><div class="bar" style="width: 100%;"></div></div></div></div>');
				return {
					showPleaseWait : function() {
						pleaseWaitDiv.modal();
					},
					hidePleaseWait : function() {
						pleaseWaitDiv.modal('hide');
					},

				};
			})();
</script>
</head>
<body>
	<jsp:include page="/WEB-INF/templates/navbar.jsp">
		<jsp:param value="protected.dashboard" name="section" />
	</jsp:include>
	<div class="jumbotron subhead">
		<div class="container">
			<h1>Groups</h1>
		</div>
	</div>
	<div class="container">
		<div class="row">
			<div class="span3 bs-docs-sidebar">
				<jsp:include page="/WEB-INF/templates/dashboardSidenav.jsp">
					<jsp:param value="protected.dashboard.groups" name="subsection" />
				</jsp:include>
			</div>


			<div class="span9">
				<h1>Groups</h1>
				<form:form method="POST" modelAttribute="groupsForm" action="/protected/groups">
					<fieldset>
						<t:input path="start" label="Start Date (AAAA-MM-GG):"
							placeholder="AAAA-MM-GG" inputprepend="fa fa-calendar" />

						<t:input path="end" label="End Date (AAAA-MM-GG):"
							placeholder="AAAA-MM-GG" inputprepend="fa fa-calendar" />

						<button class="btn btn-primary" type="submit"
							onclick="myApp.showPleaseWait()">Calculate groups</button>

					</fieldset>
				</form:form>
				
				<h2>${esito}</h2>
				
				<a class="btn" type="button"
					href='<c:url value="/protected/groups/listofuser" />'>View
					Groups</a>
			</div>
			<div class="modal hide" id="pleaseWaitDialog" data-backdrop="static"
				data-keyboard="false">
				<div class="modal-header">
					<h1>Processing...</h1>
				</div>
				<div class="modal-body">
					<div class="progress progress-striped active">
						<div class="bar" style="width: 100%;"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>