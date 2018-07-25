<%@tag language="java" description="Extended input tag to allow for sophisticated errors" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="joda" uri="http://www.joda.org/joda/time/tags"%>
<%@taglib prefix="pa" uri="/WEB-INF/tlds/paserver-funcs.tld"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@attribute name="badge" required="true" type="it.unibo.paserver.domain.BadgeActions"%>
<dl>
	<dt>Title</dt>
	<dd>
		<c:out value="${badge.title}" />
	</dd>
	<dt>Description</dt>
	<dd>
		<c:out value="${badge.description}" />
	</dd>
	<dt>Action Type</dt>
	<dd>
		<c:out value="${badge.actionType}" />
	</dd>
	<dt>Quantity</dt>
	<dd>
		<c:out value="${badge.quantity}" />
	</dd>
</dl>