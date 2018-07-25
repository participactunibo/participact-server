<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page language="java" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<c:set var="subSectionKey">${param.subsection}</c:set>
<ul class="nav nav-list bs-docs-sidenav affix-top" data-spy="affix" data-offset-top="200">
	<sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_VIEW')">
		<c:choose>
			<c:when test="${subSectionKey eq 'protected.task'}">
				<li class="active"><a href='<c:url value="/protected/task"/>'>Management</a></li>
			</c:when>
			<c:otherwise>
				<li><a href='<c:url value="/protected/task"/>'>Management</a></li>
			</c:otherwise>
		</c:choose>
	</sec:authorize>
	
	<sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_VIEW')">
		<c:choose>
			<c:when test="${subSectionKey eq 'protected.pipelinetask'}">
				<li class="active"><a href='<c:url value="/protected/pipelinetask"/>'>Management Pipeline</a></li>
			</c:when>
			<c:otherwise>
				<li><a href='<c:url value="/protected/pipelinetask"/>'>Management Pipeline</a></li>
			</c:otherwise>
		</c:choose>
	</sec:authorize>
	
	<sec:authorize access="hasAnyRole('ROLE_ADMIN')">
		<c:choose>
			<c:when test="${subSectionKey eq 'protected.task.add'}">
				<li class="active"><a href='<c:url value="/protected/task/add"/>'>Add</a></li>
			</c:when>
			<c:otherwise>
				<li><a href='<c:url value="/protected/task/add"/>'>Add</a></li>
			</c:otherwise>
		</c:choose>
	</sec:authorize>
	
	
	<sec:authorize access="hasRole('ROLE_USER')">
		<c:choose>
			<c:when test="${subSectionKey eq 'protected.webuser.task'}">
				<li class="active"><a href='<c:url value="/protected/webuser/task"/>'>Management</a></li>
			</c:when>
			<c:otherwise>
				<li><a href='<c:url value="/protected/webuser/task"/>'>Management</a></li>
			</c:otherwise>
		</c:choose>
	</sec:authorize>
	<sec:authorize access="hasRole('ROLE_USER')">
		<c:choose>
			<c:when test="${subSectionKey eq 'protected.task.add'}">
				<li class="active"><a href='<c:url value="/protected/webuser/task/add"/>'>Add</a></li>
			</c:when>
			<c:otherwise>
				<li><a href='<c:url value="/protected/webuser/task/add"/>'>Add</a></li>
			</c:otherwise>
		</c:choose>
	</sec:authorize>
	
	
	
</ul>
