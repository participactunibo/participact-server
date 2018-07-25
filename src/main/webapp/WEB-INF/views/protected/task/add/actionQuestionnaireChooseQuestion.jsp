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
					<h1>Add a question to task &quot;<c:out value="${task.name}" />&quot;</h1>
				</div>
				<form:form modelAttribute="actionQuestionaire" action="${flowExecutionUrl}">
					<button class="btn btn-danger" type="submit" name="_eventId_cancel">Cancel</button>
					<button class="btn btn-primary" type="submit" name="_eventId_openquestion">Add an open question</button>
					<button class="btn btn-primary" type="submit" name="_eventId_closedquestionsin">Add a closed question (single choice)</button>
					<button class="btn btn-primary" type="submit" name="_eventId_closedquestionmul">Add a closed question (multiple choices)</button>
					<button class="btn btn-success" type="submit" name="_eventId_done">Done</button>
				</form:form>
			</div>
		</div>
	</div>
</body>
</html>