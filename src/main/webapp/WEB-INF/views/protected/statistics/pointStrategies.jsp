<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
		<jsp:param value="protected.statistics" name="section" />
	</jsp:include>
	<div class="jumbotron subhead">
		<div class="container">
			<h1>Point Strategies</h1>
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
				<h3>Average Level</h3>
				<h4>Accepted: ${averageLevelOk}   (${averageLevelOkRatio}%)    Rejected: ${averageLevelRejected}   (${averageLevelRejectedRatio}%)    Ignored: ${averageLevelIgnored}   (${averageLevelIgnoredRatio}%)</h4>
				
				<h3>Average Reputation</h3>
				<h4>Accepted: ${averageReputationOk}   (${averageReputationOkRatio}%)    Rejected: ${averageReputationRejected}   (${averageReputationRejectedRatio}%)    Ignored: ${averageReputationIgnored}   (${averageReputationIgnoredRatio}%)</h4>
				
				<h3>Sum Level</h3>
				<h4>Accepted: ${sumLevelOk}   (${sumLevelOkRatio}%)    Rejected: ${sumLevelRejected}   (${sumLevelRejectedRatio}%)    Ignored: ${sumLevelIgnored}   (${sumLevelIgnoredRatio}%)</h4>
				
				<h3>Sum Reputation</h3>
				<h4>Accepted: ${sumReputationOk}   (${sumReputationOkRatio}%)    Rejected: ${sumReputationRejected}   (${sumReputationRejectedRatio}%)    Ignored: ${sumReputationIgnored}   (${sumReputationIgnoredRatio}%)</h4>
				
			</div>
		</div>
	</div>
</body>
</html>