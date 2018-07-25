<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ page language="java" trimDirectiveWhitespaces="true"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/WEB-INF/templates/stdhead.jsp">
	<jsp:param value="protected.interestPoint.add.title" name="title" />
</jsp:include>
<script src="<c:url value="/resources/js/leaflet/leaflet.js"/>"></script>
<script src="<c:url value="/resources/js/leafletdraw/leaflet.draw.js"/>"></script>
<script src="<c:url value="/resources/js/OpenLayers.js"/>"></script>
<script src="<c:url value="/resources/js/participact/gis.js"/>"></script>
<script>
$(document)
.ready(
		function() {
				$('#generatewkt')
					.click(
						function() {
						var drawnItems = new L.FeatureGroup();
						var wkt = "";
						if($('#lat').val()== "" || $('#lon').val()== ""){
							alert("Missing values latitude longitude!!!");
						}else{
							var lat = $('#lat').val();
							polycircle = createGeodesicPolygon($('#lat').val(),
									$('#lon').val(),100 , 16, 0);
							drawnItems.addLayer(polycircle);
							if (drawnItems.getLayers().length > 0) {
								wkt = layerToWKT(drawnItems
										.getLayers()[0]);
							}
							for (i = 1; i < drawnItems
									.getLayers().length; i++) {
								wkt = wkt
										+ ";"
										+ layerToWKT(drawnItems
												.getLayers()[i]);
							}
							$('#interestPointArea').val(wkt);
							$('#saveButton').removeAttr('disabled');
							$('#generatewkt').prop('disabled', true);
						}
					});
		});
</script>

</head>
<body>
	<jsp:include page="/WEB-INF/templates/navbar.jsp">
		<jsp:param value="protected.interestPoint" name="section" />
	</jsp:include>
	<div class="jumbotron subhead">
		<div class="container">
			<h1>New Interest Point</h1>
		</div>
	</div>
	<div class="container">
		<div class="row">
			<div class="span3 bs-docs-sidebar">
				<jsp:include page="/WEB-INF/templates/interestPointSidenav.jsp">
					<jsp:param value="protected.interestPoint.add" name="subsection" />
				</jsp:include>
			</div>
			<div class="span9">
				<div class="page-header">



					<c:if test="${errormessage != null}">
						<div class="alert alert-block alert-error fade in"
							id="errormessage">
							<button type="button" class="close" data-dismiss="alert">&times;</button>
							<c:out value="${errormessage}" />
						</div>
					</c:if>



					<h1>Add Interest Point</h1>
				</div>
				<spring:url value="/protected/interestPoint/addInterestPoint"
					var="addInterestPoint"></spring:url>
				<form:form method="POST" modelAttribute="addInterestPointForm"
					action="${addInterestPoint}">
					<fieldset>
						<t:input path="lat" label="Latitudine" placeholder="Latitudine"></t:input>
						<t:input path="lon" label="Longitudine" placeholder="Longitudine" />
						
					<p>	<t:textarea path="description" label="Description" />
						<button class="btn btn-primary" type="button" id="generatewkt">Generete
							WKT Polygon</button>
					</p>
						<t:textarea label="Interest Point area WKT representation"
							path="interestPointArea" cssClass="input-block-level" />
						<button id="saveButton" class="btn btn-primary" type="submit"
							disabled>Save</button>
					</fieldset>
				</form:form>
			</div>
		</div>
	</div>
</body>
</html>