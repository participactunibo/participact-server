<%@tag language="java" description="Shows user identifiers in a privacy-sensitive way" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@attribute name="user" required="true" type="it.unibo.paserver.domain.User"%>
<%@attribute name="link" required="false" type="java.lang.Boolean"%>
<%-- Anonymize users id if not administrator --%>
<c:set var="anonymize" value="true" />
<sec:authorize ifAllGranted="ROLE_ADMIN">
	<c:set var="anonymize" value="false" />
</sec:authorize>
<c:if test="${anonymize}">
	<c:out value="${user.id}" />
</c:if>
<c:if test="${!anonymize}">
	<c:if test="${link}">
		<a href='<c:url value="/protected/user/show/${user.id}" />'><c:out value="${user.officialEmail}" /></a>
	</c:if>
	<c:if test="${!link}">
		<c:out value="${user.officialEmail}" />
	</c:if>
</c:if>