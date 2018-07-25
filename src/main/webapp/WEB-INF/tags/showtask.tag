<%@tag language="java" description="Extended input tag to allow for sophisticated errors" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="joda" uri="http://www.joda.org/joda/time/tags"%>
<%@taglib prefix="pa" uri="/WEB-INF/tlds/paserver-funcs.tld"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@attribute name="task" required="true" type="it.unibo.paserver.domain.Task"%>
<dl>
	<dt>Name</dt>
	<dd>
		<c:out value="${task.name}" />
	</dd>
	<dt>Description</dt>
	<dd>
		<c:out value="${task.description}" />
	</dd>
	<dt>Available from</dt>
	<dd>
		Date:
		<joda:format value="${task.start}" pattern="yyyy-MM-dd" />
		<br />Time:
		<joda:format value="${task.start}" pattern="HH:mm" />
	</dd>
	<dt>Until</dt>
	<dd>
		Date:
		<joda:format value="${task.deadline}" pattern="yyyy-MM-dd" />
		<br />Time:
		<joda:format value="${task.deadline}" pattern="HH:mm" />
	</dd>
	<dt>Duration</dt>
	<dd>${task.duration}minutes (${pa:minutesToReadableTime(task.duration)})</dd>
	<c:if test="${not (task.sensingDuration eq null)}">
		<dt>Sensing duration</dt>
		<dd>${task.sensingDuration}minutes (${pa:minutesToReadableTime(task.sensingDuration)})</dd>
	</c:if>
	<dt>Geolocalized notification</dt>
	<dd>
		<c:out value="${task.notificationArea}" />
	</dd>
	<dt>Geolocalized activation</dt>
	<dd>
		<c:out value="${task.activationArea}" />
	</dd>
	<dt>Actions</dt>
	<dd>
		<ul>
			<c:forEach items="${task.actions}" var="action">
				<li><t:showaction action="${action}" /></li>
			</c:forEach>
		</ul>
	</dd>
</dl>
