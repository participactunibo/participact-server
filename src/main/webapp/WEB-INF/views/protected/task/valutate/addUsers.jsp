<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ page language="java" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/WEB-INF/templates/stdhead.jsp">
	<jsp:param value="protected.task.add.title" name="title" />
</jsp:include>

<script>
	$(document).ready(function() {
		$('#btnActiveUsers').click(function() {
			$.getJSON('<c:url value="/protected/activeUsers" />', function(data) {
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
	$(document).ready(function() {
		$('#btnFriendsUsers').click(function() {
			$.getJSON('<c:url value="/protected/friends/" />'+$('#userMail').val(), function(data) {
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
	$(document).ready(function() {
		$('#btnFriendsAddedByOwner').click(function() {
			var txtarea = $('#userList');
			if( ${fn:length(userTask.usersToAssign)} > 0) {
				txtarea.val("${userTask.usersToAssign.toArray()[0].officialEmail}");
				for( var i = 1; i < ${fn:length(userTask.usersToAssign)}; i++) {
					txtarea.val(txtarea.val() + "\n ${userTask.usersToAssign.toArray()[i].officialEmail}");
				}
			}
			else
				txtarea.val("${userTask.owner.officialEmail} has not added any friend");
			
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
			<h1>Add Users to task</h1>
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
					<h1>Add Users to task #<c:out value="${task.id}" /> &quot;<c:out value="${task.name}" />&quot;</h1>
				</div>
				<a class="btn" id="btnActiveUsers">Select all active users</a><br/><br/>
				<input id="userMail" type="text" placeholder="User email" autocomplete="off" data-provide="typeahead" data-items="10" data-source='${allUsersArrayString}'><br/>
				<a class="btn" id="btnFriendsUsers">Select friends</a><br/><br/>
				<a class="btn" id="btnFriendsAddedByOwner"> Add chosen owner's friends</a><br/><br/>
				<form:form modelAttribute="taskAssignedUsersForm" action="${flowExecutionUrl}">
					<t:textarea
						label="List of users to add to the task. Users are identified by their official e-mail. Different users can be separated by a comma, semicolon, newline or any other character matching [^a-zA-Z0-9@.]."
						path="userList" cssClass="input-block-level" />
					<button class="btn btn-danger" type="submit" name="_eventId_cancel">Cancel</button>
					<button class="btn btn-primary" type="submit" name="_eventId_next">Next</button>
				</form:form>
			</div>
		</div>
	</div>
</body>
</html>