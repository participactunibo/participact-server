<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags"%>
<%@ taglib prefix="pa" uri="/WEB-INF/tlds/paserver-funcs.tld"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ page language="java" trimDirectiveWhitespaces="true"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/WEB-INF/templates/stdhead.jsp">
	<jsp:param value="protected.data.show.title" name="title" />
</jsp:include>
<script src="<c:url value="/resources/js/jquery.dataTables.js"/>"></script>
<script src="<c:url value="/resources/js/dataTables.bootstrap.js"/>"></script>
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/datatables.bootstrap.css"/>">
</head>
<body>
	<jsp:include page="/WEB-INF/templates/navbar.jsp">
		<jsp:param value="protected.task" name="section" />
	</jsp:include>
	<div class="jumbotron subhead">
		<div class="container">
			<h1>
				&quot;

				<sec:authorize access="hasRole('ROLE_ADMIN')">
					<c:out value="${task.name}" />
				</sec:authorize>
				<sec:authorize access="hasRole('ROLE_USER')">
					<c:out value="${task.name}" />

					<%-- <c:choose>
						<c:when test="${isUserTaskData == true}">
							<c:out value="${task.name}" />
						</c:when>
						<c:otherwise>
							<c:out value="${taskUser.task.name}" />
						</c:otherwise>

					</c:choose> --%>
				</sec:authorize>
				&quot;

			</h1>
		</div>
	</div>
	<div class="container">
		<div class="row">
			<div class="span3 bs-docs-sidebar">
				<sec:authorize access="hasRole('ROLE_ADMIN')">
					<jsp:include page="/WEB-INF/templates/taskSidenav.jsp">
						<jsp:param value="protected.task" name="subsection" />
					</jsp:include>
				</sec:authorize>
				<sec:authorize access="hasRole('ROLE_USER')">
					<jsp:include page="/WEB-INF/templates/taskSidenav.jsp">
						<jsp:param value="protected.webuser.task" name="subsection" />
					</jsp:include>
				</sec:authorize>
			</div>
			<div class="span9">
				<div class="page-header">
					<h1>
						Task &quot;
						<sec:authorize access="hasRole('ROLE_ADMIN')">
							<a href='<c:url value="/protected/task/show/${task.id}"/>'> <c:out
									value="${task.name}" /></a>
						</sec:authorize>
						<sec:authorize access="hasRole('ROLE_USER')">
							<a
								href='<c:url value="/protected/webuser/task/show/${task.id}"/>'>
								<c:out value="${task.name}" />
							</a>
							<%-- <c:choose>
								 <c:when test="${isUserTaskData == true}">
									<a
										href='<c:url value="/protected/webuser/task/show/${task.id}"/>'>
										<c:out value="${task.name}" />
									</a>
								</c:when>
								<c:otherwise>
									<a
										href='<c:url value="/protected/webuser/taskuser/show/${taskUser.id}"/>'>
										<c:out value="${taskUser.task.name}" />
									</a>
								</c:otherwise> 

							</c:choose> --%>


						</sec:authorize>
						&quot;

					</h1>
				</div>
			</div>
		</div>
		<div class="span12">
			<h2>Task data</h2>
			<t:showdataphoto data="${dataToShow}"></t:showdataphoto>
		</div>
	</div>
</body>
</html>