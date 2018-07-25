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
			<th>Timestamp</th>
			<th>GSM Cell ID</th>
			<th>GSM LAC</th>
			<th>Base Station ID</th>
			<th>Base Station Lat</th>
			<th>Base Station Long</th>
			<th>Network ID</th>
			<th>System ID</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${data}" var="entry">
			<tr>
				<td><t:userid user="${entry.user}" /></td>
				<td><joda:format value="${entry.sampleTimestamp}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
				<td><c:out value="${entry.gsmCellId}" /></td>
				<td><c:out value="${entry.gsmLac}" /></td>
				<td><c:out value="${entry.baseStationId}" /></td>
				<td><c:out value="${entry.baseStationLatitude}" /></td>
				<td><c:out value="${entry.baseStationLongitude}" /></td>
				<td><c:out value="${entry.baseNetworkId}" /></td>
				<td><c:out value="${entry.baseSystemId}" /></td>
			</tr>
		</c:forEach>
	</tbody>
</table>