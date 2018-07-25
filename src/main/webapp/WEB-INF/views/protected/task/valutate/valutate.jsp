<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags"%>
<%@ taglib prefix="pa" uri="/WEB-INF/tlds/paserver-funcs.tld"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ page language="java" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<jsp:include page="/WEB-INF/templates/stdhead.jsp">
	<jsp:param value="protected.task.show.title" name="title" />
</jsp:include>

<script src="<c:url value="/resources/js/dataTables.bootstrap.js"/>"></script>


</head>

<body>
	<jsp:include page="/WEB-INF/templates/navbar.jsp">
		<jsp:param value="protected.task" name="section" />
	</jsp:include>
	<div class="jumbotron subhead">
		<div class="container">
			<h1>
				&quot;<c:out value="${userTask.task.name}" />&quot; 
			</h1>
		</div>
	</div>
	
	<div class="container">
		<div class="row">
			<div class="span3 bs-docs-sidebar">
				<jsp:include page="/WEB-INF/templates/taskValutateSidenav.jsp">
 					<jsp:param value="protected.userTask.approve" name="subsection" /> 
				</jsp:include>
			</div>
			<div class="span9">
				<div class="page-header">
					<h1>
						Sensing task &quot;<c:out value="${userTask.task.name}" />&quot; valutate
					</h1>
				</div>
				<t:showtask task="${userTask.task}" />
			</div>
		</div>
	</div>
	
</body>
</html>