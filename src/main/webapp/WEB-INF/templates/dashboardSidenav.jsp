<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ page language="java" trimDirectiveWhitespaces="true"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<c:set var="subSectionKey">${param.subsection}</c:set>
<ul class="nav nav-list bs-docs-sidenav affix-top" data-spy="affix"
	data-offset-top="200">
	<sec:authorize access="hasAnyRole('ROLE_ADMIN')">
		<c:choose>
			<c:when test="${subSectionKey eq 'protected.dashboard.logs.get'}">
				<li class="active"><a
					href='<c:url value="/protected/logs/get"/>'>Get logs</a></li>
			</c:when>
			<c:otherwise>
				<li><a href='<c:url value="/protected/logs/get"/>'>Get logs</a></li>
			</c:otherwise>
		</c:choose>
		<c:choose>
			<c:when test="${subSectionKey eq 'protected.dashboard.logs.list'}">
				<li class="active"><a
					href='<c:url value="/protected/logs/list"/>'>List logs</a></li>
			</c:when>
			<c:otherwise>
				<li><a href='<c:url value="/protected/logs/list"/>'>List
						logs</a></li>
			</c:otherwise>
		</c:choose>
		<c:choose>
			<c:when test="${subSectionKey eq 'protected.dashboard.scores'}">
				<li class="active"><a
					href='<c:url value="/protected/scores/list"/>'>Scores</a></li>
			</c:when>
			<c:otherwise>
				<li><a href='<c:url value="/protected/scores/list"/>'>Scores</a></li>
			</c:otherwise>
		</c:choose>
		<c:choose>
			<c:when test="${subSectionKey eq 'protected.dashboard.gcm'}">
				<li class="active"><a
					href='<c:url value="/protected/gcm/send"/>'>Send GCM</a></li>
			</c:when>
			<c:otherwise>
				<li><a href='<c:url value="/protected/gcm/send"/>'>Send GCM</a></li>
			</c:otherwise>
		</c:choose>
		<c:choose>
			<c:when
				test="${subSectionKey eq 'protected.dashboard.clientversion'}">
				<li class="active"><a
					href='<c:url value="/protected/clientversion/list"/>'>Client
						version</a></li>
			</c:when>
			<c:otherwise>
				<li><a href='<c:url value="/protected/clientversion/list"/>'>Client
						version</a></li>
			</c:otherwise>
		</c:choose>
		<c:choose>
			<c:when test="${subSectionKey eq 'protected.dashboard.tper'}">
				<li class="active"><a href='<c:url value="/protected/tper"/>'>Update
						TPer data</a></li>
			</c:when>
			<c:otherwise>
				<li><a href='<c:url value="/protected/tper"/>'>Update TPer
						data</a></li>
			</c:otherwise>
		</c:choose>
		<c:choose>
			<c:when test="${subSectionKey eq 'protected.dashboard.statistics'}">
				<li class="active"><a
					href='<c:url value="/protected/statistics"/>'>Statistics</a></li>
			</c:when>
			<c:otherwise>
				<li><a href='<c:url value="/protected/statistics"/>'>Statistics</a></li>
			</c:otherwise>
		</c:choose>

		<%-- Codice Gioia Leo --%>
		<c:choose>
			<c:when test="${subSectionKey eq 'protected.dashboard.groups'}">
				<li class="active"><a
					href='<c:url value="/protected/groups"/>'>Groups</a></li>
			</c:when>
			<c:otherwise>
				<li><a href='<c:url value="/protected/groups"/>'>Groups</a></li>
			</c:otherwise>
		</c:choose>
	</sec:authorize>
</ul>