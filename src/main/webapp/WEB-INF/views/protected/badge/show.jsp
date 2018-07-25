<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ page language="java" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/WEB-INF/templates/stdhead.jsp">
	<jsp:param value="protected.badge.show.title" name="title" />
</jsp:include>
<script src="<c:url value="/resources/js/bootstrap-datepicker.js"/>"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/datepicker.css"/>">
</head>
<body>
	<jsp:include page="/WEB-INF/templates/navbar.jsp">
		<jsp:param value="protected.badge" name="section" />
	</jsp:include>
	<div class="jumbotron subhead">
		<div class="container">
			<h1>Badge</h1>
		</div>
	</div>
	<div class="container">
		<div class="row">
			<div class="span3 bs-docs-sidebar">
				<jsp:include page="/WEB-INF/templates/badgeSidenav.jsp">
					<jsp:param value="protected.badge.show" name="subsection" />
				</jsp:include>
			</div>
			<div class="span9">
				<div class="page-header">
					<h1>
						Badge
						<c:out value="${badge.title}" />
						<a class="btn btn-warning" href='<c:url value="/protected/badge/edit/${badge.id}" />' type="button">Edit</a>
					</h1>
				</div>
				
				<c:choose>
      				<c:when test="${isAction==true}">
      					<t:showbadgeaction badge="${badge}"></t:showbadgeaction>
      				</c:when>

      				<c:otherwise>
      					<t:showbadgetask badge="${badge}"></t:showbadgetask>
      				</c:otherwise>
				</c:choose>	
				
			</div>
		</div>
	</div>
</body>
</html>