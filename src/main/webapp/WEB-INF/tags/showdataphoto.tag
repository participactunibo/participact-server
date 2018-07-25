<%@tag language="java"
	description="Extended input tag to allow for sophisticated errors"
	pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="joda" uri="http://www.joda.org/joda/time/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="pa" uri="/WEB-INF/tlds/paserver-funcs.tld"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@attribute name="data" required="true" type="java.lang.Object"%>
<%@attribute name="taskId" required="false" type="java.lang.Long"%>
<%@attribute name="userId" required="false" type="java.lang.Long"%>



<sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_VIEW')">
	<c:forEach items="${data}" var="entry">
		<h3>
			User
			<t:userid user="${entry.user}" link="true" />
			&mdash;
			<joda:format value="${entry.sampleTimestamp}"
				pattern="yyyy-MM-dd HH:mm" />
		</h3>
		<img src='<c:url value="/protected/photo/${entry.id}" />' />
		<br />
	</c:forEach>
</sec:authorize>
<sec:authorize access="hasRole('ROLE_USER')">
	<c:forEach items="${data}" var="entry">
		<h3>
			User <a href='<c:url value="/protected/user/show" />'><c:out
					value="${entry.user.getName()}" /></a> &mdash;
			<joda:format value="${entry.sampleTimestamp}"
				pattern="yyyy-MM-dd HH:mm" />
		</h3>
		<img
			src='<c:url value="/protected/webuser/taskreport/show/task/${entry.getTaskId()}/photo/${entry.id}" />' />


		<%-- <c:choose>
			<c:when test="${isUserTaskData == true}">
				<h3>
					User <a href='<c:url value="/protected/user/show" />'><c:out
							value="${entry.user.getName()}" /></a> &mdash;
					<joda:format value="${entry.sampleTimestamp}"
						pattern="yyyy-MM-dd HH:mm" />
				</h3>
				<img
					src='<c:url value="/protected/webuser/taskreport/show/task/${entry.getTaskId()}/photo/${entry.id}" />' />
			</c:when>


			<c:otherwise>
				<h3>
					User
					<t:userid user="${entry.user}" link="true" />
					&mdash;
					<joda:format value="${entry.sampleTimestamp}"
						pattern="yyyy-MM-dd HH:mm" />
				</h3>
				<img
					src='<c:url value="/protected/webuser/taskreport/show/task/${entry.getTaskId()}/user/${entry.user.id}/photo/${entry.id}" />' />
			</c:otherwise>


		</c:choose> --%>

		<br />
	</c:forEach>






</sec:authorize>
