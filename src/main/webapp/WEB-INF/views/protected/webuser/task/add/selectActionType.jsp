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
					<h1>Add actions to task</h1>
				</div>
				<form:form modelAttribute="task" action="${flowExecutionUrl}">
					<p>
						<button class="btn btn-primary" type="submit"
							name="_eventId_sensingMost">Add a passive sensing action</button>
						<c:if test="${hasActivityDetection == false}">
							<button class="btn btn-primary" type="submit"
								name="_eventId_activityDetection">Add an activity
								detection action</button>
						</c:if>
						<button class="btn btn-primary" type="submit"
							name="_eventId_photo">Add a photo action</button>
						<button class="btn btn-primary" type="submit"
							name="_eventId_questionnaire">Add a questionnaire action</button>
					</p>
					<p>
						<button class="btn btn-primary" type="submit"
							name="_eventId_notification_area">Select a notification
							area</button>
						<button class="btn btn-primary" type="submit"
							name="_eventId_activation_area">Select an activation
							area</button>
					</p>
					<p>
						<button class="btn btn-success" type="submit" name="_eventId_assignUsers">Assign task to users</button>
					</p>
				</form:form>
				<h2>Current task structure</h2>
				<t:showtask task="${task}" />

				<p>
					<a class="btn btn-success"
						href="${flowExecutionUrl}&_eventId_create">Create task</a>

					<a class="btn btn-danger" href="${flowExecutionUrl}&_eventId_cancel">Cancel</a>
				</p>
			</div>
		</div>
	</div>
</body>
</html>