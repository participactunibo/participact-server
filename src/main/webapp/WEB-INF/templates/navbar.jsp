<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page language="java" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<c:set var="sectionKey">${param.section}</c:set>
<div class="navbar navbar-inverse navbar-fixed-top">
	<div class="navbar-inner">
		<div class="container">
			<button type="button" class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
				<span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span>
			</button>
			<a class="brand" href='<c:url value="/"/>'>ParticipAct</a>
			<div class="nav-collapse collapse">
				<ul class="nav">
					<c:choose>
						<c:when test="${sectionKey eq 'index'}">
							<li class="active"><a href='<c:url value="/"/>'>Home</a></li>
						</c:when>
						<c:otherwise>
							<li><a href='<c:url value="/"/>'>Home</a></li>
						</c:otherwise>
					</c:choose>
					<sec:authorize access="isAuthenticated()">
						<sec:authorize access="hasAnyRole('ROLE_ADMIN')">
							<c:choose>
								<c:when test="${sectionKey eq 'protected.dashboard'}">
									<li class="active"><a href='<c:url value="/protected/dashboard"/>'>Dashboard</a></li>
								</c:when>
								<c:otherwise>
									<li><a href='<c:url value="/protected/dashboard"/>'>Dashboard</a></li>
								</c:otherwise>
							</c:choose>
						</sec:authorize>
						<sec:authorize access="hasAnyRole('ROLE_ADMIN')">
							<c:choose>
								<c:when test="${sectionKey eq 'protected.account'}">
									<li class="active"><a href='<c:url value="/protected/account"/>'>Admin Account</a></li>
								</c:when>
								<c:otherwise>
									<li><a href='<c:url value="/protected/account"/>'>Admin Account</a></li>
								</c:otherwise>
							</c:choose>
						</sec:authorize>
						<sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_VIEW')">
							<c:choose>
								<c:when test="${sectionKey eq 'protected.task'}">
									<li class="active"><a href='<c:url value="/protected/task"/>'>Tasks</a></li>
								</c:when>
								<c:otherwise>
									<li><a href='<c:url value="/protected/task"/>'>Tasks</a></li>
								</c:otherwise>
							</c:choose>
						</sec:authorize>
						<sec:authorize access="hasAnyRole('ROLE_ADMIN')">
							<c:choose>
								<c:when test="${sectionKey eq 'protected.user'}">
									<li class="active"><a href='<c:url value="/protected/user"/>'>User Account</a></li>
								</c:when>
								<c:otherwise>
									<li><a href='<c:url value="/protected/user"/>'>User Account</a></li>
								</c:otherwise>
							</c:choose>
						</sec:authorize>
						<sec:authorize access="hasAnyRole('ROLE_ADMIN')">
							<c:choose>
								<c:when test="${sectionKey eq 'protected.badge'}">
									<li class="active"><a href='<c:url value="/protected/badge"/>'>Badges</a></li>
								</c:when>
								<c:otherwise>
									<li><a href='<c:url value="/protected/badge"/>'>Badges</a></li>
								</c:otherwise>
							</c:choose>
						</sec:authorize>
						<sec:authorize access="hasAnyRole('ROLE_ADMIN')">
							<c:choose>
								<c:when test="${sectionKey eq 'protected.interestPoint'}">
									<li class="active"><a href='<c:url value="/protected/interestPoint"/>'>Interest Point</a></li>
								</c:when>
								<c:otherwise>
									<li><a href='<c:url value="/protected/interestPoint"/>'>Interest Point</a></li>
								</c:otherwise>
							</c:choose>
						</sec:authorize>
						
						<sec:authorize access="hasAnyRole('ROLE_ADMIN')">
							<c:choose>
								<c:when test="${sectionKey eq 'protected.points'}">
									<li class="active"><a href='<c:url value="/protected/points"/>'>Points</a></li>
								</c:when>
								<c:otherwise>
									<li><a href='<c:url value="/protected/points"/>'>Points</a></li>
								</c:otherwise>
							</c:choose>
						</sec:authorize>
						<sec:authorize access="hasRole('ROLE_USER')">
							<c:choose>
								<c:when test="${sectionKey eq 'protected.webuser.user' }">
									<li class="active"><a href='<c:url value="/protected/user/show"/>'>Profile</a></li>
								</c:when>
								<c:otherwise>
									<li><a href='<c:url value="/protected/user/show"/>'>Profile</a></li>
								</c:otherwise>

							</c:choose>
							<li><a></a></li>
						</sec:authorize>
						
						<sec:authorize access="hasRole('ROLE_USER')">
							<c:choose>
								<c:when test="${sectionKey eq 'protected.webuser.task' }">
									<li class="active"><a href='<c:url value="/protected/webuser/task"/>'>Task</a></li>
								</c:when>
								<c:otherwise>
									<li><a href='<c:url value="/protected/webuser/task"/>'>Task</a></li>
								</c:otherwise>

							</c:choose>
							<li><a></a></li>
						</sec:authorize>
						<li><a href='<c:url value="/logout"/>'>Logout</a></li>
					</sec:authorize>
					<sec:authorize access="isAuthenticated() == false">
						<c:choose>
							<c:when test="${sectionKey eq 'login'}">
								<li class="active"><a href='<c:url value="/login"/>'>Login</a></li>
							</c:when>
							<c:otherwise>
								<li><a href='<c:url value="/login"/>'>Login</a></li>
							</c:otherwise>
						</c:choose>
					</sec:authorize>
				</ul>
			</div>
		</div>
	</div>
</div>