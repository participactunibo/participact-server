<%@tag language="java" description="Extended input tag to allow for sophisticated errors" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags"%>
<%@ taglib prefix="pa" uri="/WEB-INF/tlds/paserver-funcs.tld"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@attribute name="data" required="true" type="java.lang.Object"%>
<%@attribute name="tableid" required="true" type="java.lang.String"%>
<table class="table table-striped" id="${tableid}">
	<thead>
		<tr>
			<th>User</th>
			<th>Start time</th>
			<th>End time</th>
			<th>App name</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${data}" var="entry">
			<tr>
				<td><t:userid user="${entry.user}" /></td>
				<td><joda:format value="${entry.startTime}" pattern="yyyy-MM-dd HH:mm" /></td>
				<td><joda:format value="${entry.endTime}" pattern="yyyy-MM-dd HH:mm" /></td>
				<td><c:out value="${entry.appName}"/></td>
			</tr>
		</c:forEach>
	</tbody>
</table>