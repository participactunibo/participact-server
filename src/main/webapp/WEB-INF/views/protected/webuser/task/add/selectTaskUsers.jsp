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

<script>
	
	$(document).ready(function() {
		$('#btnFriendsUsers').click(function() {
			$.getJSON('<c:url value="/protected/friends/me"/>', function(data) {
				data.sort();
				var txtarea = $('#userList');
				if (data.length > 0) {
					txtarea.val(data[0]);
					for (var i = 1; i < data.length; i++) {
						txtarea.val(txtarea.val() + "\n"+ data[i]);
					}
				}
			});
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
					<h1>Assign users to task</h1>
				</div>
				<form:form modelAttribute="taskAssignedUsersForm"
					action="${flowExecutionUrl}">
										
				<a class="btn" id="btnFriendsUsers">Select friends</a><br/><br/>
		
					<t:textarea
						label="List of users this task should be assigned to. Users are identified by their official e-mail. Different users can be separated by a comma, semicolon, newline or any other character matching [^a-zA-Z0-9@.]."
						path="userList" cssClass="input-block-level" />
					<button class="btn btn-danger" type="submit" name="_eventId_cancel">Cancel</button>
					<button class="btn btn-primary" type="submit" name="_eventId_next">Next</button>
				</form:form>
			</div>
		</div>
	</div>
</body>
</html>