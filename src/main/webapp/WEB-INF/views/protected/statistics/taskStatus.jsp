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
	<jsp:param value="protected.statistics.title" name="title" />
</jsp:include>
</head>
<body>
	<jsp:include page="/WEB-INF/templates/navbar.jsp">
		<jsp:param value="protected.statistics" name="section" />
	</jsp:include>
	<div class="jumbotron subhead">
		<div class="container">
			<h1>Task Statistics</h1>
		</div>
	</div>
	<div class="container">
		<div class="row">
			<div class="span3 bs-docs-sidebar">
				<jsp:include page="/WEB-INF/templates/dashboardSidenav.jsp">
					<jsp:param value="protected.statistics" name="subsection" />
				</jsp:include>
			</div>
			<div class="span9">
				<div class="span9">
					<h2>Task created by user from 15/02/2015</h2>
					<br />
					<p>This section contains statistics on task created by user</p>
					<p>Mean of task user accepted: ${meantaskUserAccepted}</p>
					<p>Mean of task user ignored: ${meantaskUserIgnored}</p>
					<p>Mean of task user refused: ${meantaskUserRefused}</p>
				</div>

				<div class="span9">
					<h2>Task created by administrator from 15/02/2015</h2>
					<br />
					<p>This section contains statistics on task created by
						administrator</p>
					<p>Mean of task accepted: ${meanTaskAccepted}</p>
					<p>Mean of task ignored: ${meanTaskIgnored}</p>
					<p>Mean of task refused: ${meanTaskRefused}</p>
				</div>

				<div class="span9">
					<h2>All task assigned before 15/02/2015</h2>
					<br />
					<p>This section contains statistics on task assigned to users
						before 15/02/2015</p>
					<p>Mean of task accepted: ${meanTaskOldAccepted}</p>
					<p>Mean of task ignored: ${meanTaskOldIgnored}</p>
					<p>Mean of task refused: ${meanTaskOldRefused}</p>
				</div>

				<div class="span9">
					<h2>All task assigned after 15/02/2015</h2>
					<br />
					<p>This section contains statistics on task assigned to users
						after 15/02/2015</p>
					<p>Mean of task accepted: ${meanTaskNewAccepted}</p>
					<p>Mean of task ignored: ${meanTaskNewIgnored}</p>
					<p>Mean of task refused: ${meanTaskNewRefused}</p>
				</div>
			</div>

		</div>
	</div>
</body>
</html>