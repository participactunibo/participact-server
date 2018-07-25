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
			<th>BOOT_TIME</th>
			<th>CONTEXT_SWITCHES</th>
			<th>CPU_FREQUENCY</th>
			<th>CPU_HARDIRQ</th>
			<th>CPU_IDLE</th>
			<th>CPU_IOWAIT</th>
			<th>CPU_NICED</th>
			<th>CPU_SOFTIRQ</th>
			<th>CPU_SYSTEM</th>
			<th>CPU_USER</th>
			<th>MEM_ACTIVE</th>
			<th>MEM_FREE</th>
			<th>MEM_INACTIVE</th>
			<th>MEM_TOTAL</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${data}" var="entry">
			<tr>
				<td><t:userid user="${entry.user}" /></td>
				<td><joda:format value="${entry.sampleTimestamp}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
				<td><c:out value="${entry.BOOT_TIME}" /></td>
				<td><c:out value="${entry.CONTEXT_SWITCHES}" /></td>
				<td><c:out value="${entry.CPU_FREQUENCY}" /></td>
				<td><c:out value="${entry.CPU_HARDIRQ}" /></td>
				<td><c:out value="${entry.CPU_IDLE}" /></td>
				<td><c:out value="${entry.CPU_IOWAIT}" /></td>
				<td><c:out value="${entry.CPU_NICED}" /></td>
				<td><c:out value="${entry.CPU_SOFTIRQ}" /></td>
				<td><c:out value="${entry.CPU_SYSTEM}" /></td>
				<td><c:out value="${entry.CPU_USER}" /></td>
				<td><c:out value="${entry.MEM_ACTIVE}" /></td>
				<td><c:out value="${entry.MEM_FREE}" /></td>
				<td><c:out value="${entry.MEM_INACTIVE}" /></td>
				<td><c:out value="${entry.MEM_TOTAL}" /></td>
			</tr>
		</c:forEach>
	</tbody>
</table>