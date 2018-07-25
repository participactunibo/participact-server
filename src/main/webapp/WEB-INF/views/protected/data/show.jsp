<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags"%>
<%@ taglib prefix="pa" uri="/WEB-INF/tlds/paserver-funcs.tld"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ page language="java" trimDirectiveWhitespaces="true"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/WEB-INF/templates/stdhead.jsp">
	<jsp:param value="protected.data.show.title" name="title" />
</jsp:include>
<script src="<c:url value="/resources/js/jquery.dataTables.js"/>"></script>
<script src="<c:url value="/resources/js/dataTables.bootstrap.js"/>"></script>
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/datatables.bootstrap.css"/>">
<c:if test="${dataName eq 'DataLocation'}">
	<link rel="stylesheet"
		href="<c:url value="/resources/js/leaflet/leaflet.css" />">
	<script src="<c:url value="/resources/js/leaflet/leaflet.js"/>"></script>
</c:if>

<c:set var="task" value="${taskReport.task}" />
<script>
	/* Set the defaults for DataTables initialisation */

	$(document).ready(
			function() {
				$('#datatable').dataTable(
						{
							"bPaginate" : true,
							"aLengthMenu" : [ [ 500, 1000, 5000, 10000, -1 ],
									[ 500, 1000, 5000, 10000, "All" ] ],
							"oLanguage" : {
								"sLengthMenu" : "_MENU_ records per page"
							}
						});
			});
</script>
</head>
<body>
	<jsp:include page="/WEB-INF/templates/navbar.jsp">
		<jsp:param value="protected.task" name="section" />
	</jsp:include>
	<div class="jumbotron subhead">
		<div class="container">
			<h1>
				&quot;
				<c:out value="${dataName}" />
				&quot;
			</h1>
		</div>
	</div>
	<div class="container">
		<div class="row">
			<div class="span3 bs-docs-sidebar">
				<sec:authorize access="hasRole('ROLE_ADMIN')">
					<jsp:include page="/WEB-INF/templates/taskSidenav.jsp">
						<jsp:param value="protected.task" name="subsection" />
					</jsp:include>
				</sec:authorize>
				<sec:authorize access="hasRole('ROLE_USER')">
					<jsp:include page="/WEB-INF/templates/taskSidenav.jsp">
						<jsp:param value="protected.webuser.task" name="subsection" />
					</jsp:include>
				</sec:authorize>


			</div>
			<div class="span9">
				<div class="page-header">
					<h1>
						Task &quot;

						<sec:authorize access="hasRole('ROLE_ADMIN')">
							<a
								href='<c:url value="/protected/task/show/${taskReport.task.id}"/>'><c:out
									value="${taskReport.task.name}" /></a>&quot;
										, user <t:userid user="${taskReport.user}" link="true" />

						</sec:authorize>

						<sec:authorize access="hasRole('ROLE_USER')">
							<a
								href='<c:url value="/protected/webuser/task/show/${taskReport.task.id}"/>'><c:out
									value="${taskReport.task.name}" /></a>&quot;	

							<%-- <c:choose>

								<c:when test="${isUserTaskData == false}">
									<a
										href='<c:url value="/protected/webuser/taskuser/show/${taskUserId}"/>'><c:out
											value="${taskReport.task.name}" /></a>&quot;
										, user <t:userid user="${taskReport.user}" link="false" />
								</c:when>
								<c:otherwise>
									<a
										href='<c:url value="/protected/webuser/task/show/${taskReport.task.id}"/>'><c:out
											value="${taskReport.task.name}" /></a>&quot;	
							</c:otherwise>
							</c:choose> --%>
						</sec:authorize>

					</h1>
				</div>
			</div>
		</div>
		<div class="span12">
			<h2>Task data</h2>
			<c:choose>
				<c:when test="${dataName eq 'DataDR'}">
					<t:showdatadr tableid="datatable" data="${dataToShow}"></t:showdatadr>
				</c:when>
				<c:when test="${dataName eq 'DataAccelerometer'}">
					<t:showdataaccelerometer tableid="datatable" data="${dataToShow}" />
				</c:when>
				<c:when test="${dataName eq 'DataAccelerometerClassifier'}">
					<t:showdataaccelerometerclassifier tableid="datatable"
						data="${dataToShow}" />
				</c:when>
				<c:when test="${dataName eq 'DataActivityRecognitionCompare'}">
					<t:showdataactivityrecognitioncompare tableid="datatable"
						data="${dataToShow}" />
				</c:when>
				<c:when test="${dataName eq 'DataAppNetTraffic'}">
					<t:showdataappnettraffic tableid="datatable" data="${dataToShow}" />
				</c:when>
				<c:when test="${dataName eq 'DataAppOnScreen'}">
					<t:showdataapponscreen tableid="datatable" data="${dataToShow}" />
				</c:when>
				<c:when test="${dataName eq 'DataBattery'}">
					<t:showdatabattery tableid="datatable" data="${dataToShow}" />
				</c:when>
				<c:when test="${dataName eq 'DataBluetooth'}">
					<t:showdatabluetooth tableid="datatable" data="${dataToShow}" />
				</c:when>
				<c:when test="${dataName eq 'DataCell'}">
					<t:showdatacell tableid="datatable" data="${dataToShow}" />
				</c:when>
				<c:when test="${dataName eq 'DataConnectionType'}">
					<t:showdataconnectiontype tableid="datatable" data="${dataToShow}" />
				</c:when>
				<c:when test="${dataName eq 'DataDeviceNetTraffic'}">
					<t:showdatadevicenettraffic tableid="datatable"
						data="${dataToShow}" />
				</c:when>
				<c:when test="${dataName eq 'DataGoogleActivityRecognition'}">
					<t:showdatagoogleactivityrecognition tableid="datatable"
						data="${dataToShow}" />
				</c:when>
				<c:when test="${dataName eq 'DataGyroscope'}">
					<t:showdatagyroscope tableid="datatable" data="${dataToShow}" />
				</c:when>
				<c:when test="${dataName eq 'DataInstalledApps'}">
					<t:showdatainstalledapps tableid="datatable" data="${dataToShow}" />
				</c:when>
				<c:when test="${dataName eq 'DataLocation'}">
					<t:showdatalocation tableid="datatable" data="${dataToShow}"
						geojsonurl="${geojsonurl}" />
				</c:when>
				<c:when test="${dataName eq 'DataPhoto'}">
					<t:showdataphoto data="${dataToShow}" />
				</c:when>
				<c:when test="${dataName eq 'DataSystemStats'}">
					<t:showdatasystemstats tableid="datatable" data="${dataToShow}" />
				</c:when>
				<c:when test="${dataName eq 'DataWifiScan'}">
					<t:showdatawifiscan tableid="datatable" data="${dataToShow}" />
				</c:when>
				<c:when test="${dataName eq 'questionnaire'}">
					<t:showquestionnaireanswers data="${dataToShow}"
						questionnaire="${questionnaire}"
						questionnairedescription="${questionnairedescription}" />
				</c:when>
			</c:choose>
		</div>
	</div>
</body>
</html>