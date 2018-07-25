<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page language="java" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/WEB-INF/templates/stdhead.jsp">
	<jsp:param value="protected.statistics.title" name="title" />
</jsp:include>
</head>
<body>
	<jsp:include page="/WEB-INF/templates/navbar.jsp">
		<jsp:param value="protected.dashboard" name="section" />
	</jsp:include>
	<div class="jumbotron subhead">
		<div class="container">
			<h1>Statistics</h1>
		</div>
	</div>
	<div class="container">
		<div class="row">
			<div class="span3 bs-docs-sidebar">
				<jsp:include page="/WEB-INF/templates/dashboardSidenav.jsp">
					<jsp:param value="protected.dashboard.statistics" name="subsection" />
				</jsp:include>
			</div>
			<div class="span9">
				<h1>Select an action.</h1>
				<a class="btn btn-primary" type="submit"	href='<c:url value="/protected/statistics/tasksCount"/>'>Tasks Count</a>
				<a class="btn btn-primary" type="submit"	href='<c:url value="/protected/statistics/taskStatus"/>'>Task status</a>
				<a class="btn btn-primary" type="submit"	href='<c:url value="/protected/statistics/actionsForUserTask"/>'>Actions for user Task</a>
				<a class="btn btn-primary" type="submit"	href='<c:url value="/protected/statistics/averagePoints"/>'>Points</a>
				<a class="btn btn-primary" type="submit"	href='<c:url value="/protected/statistics/pointStrategies"/>'>Point Strategies</a>
				<a class="btn btn-primary" type="submit"	href='<c:url value="/protected/statistics/friendships"/>'>Friendships</a>
				<a class="btn btn-primary" type="submit"	href='<c:url value="/protected/statistics/socialLogin"/>'>Social Login</a>	
				<a class="btn btn-primary" type="submit"	href='<c:url value="/protected/statistics/reputations"/>'>Reputations</a>			
							
							
			</div>
		</div>
	</div>
</body>
</html>