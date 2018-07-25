<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ page language="java" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/WEB-INF/templates/stdhead.jsp">
	<jsp:param value="protected.task.add.title" name="title" />
</jsp:include>
<link rel="stylesheet" href="<c:url value="/resources/js/leaflet/leaflet.css" />">
<link rel="stylesheet" href="<c:url value="/resources/js/leafletdraw/leaflet.draw.css"/>" />
<script src="<c:url value="/resources/js/leaflet/leaflet.js"/>"></script>
<script src="<c:url value="/resources/js/leafletdraw/leaflet.draw.js"/>"></script>
<script src="<c:url value="/resources/js/OpenLayers.js"/>"></script>
<script src="<c:url value="/resources/js/participact/gis.js"/>"></script>
<script>
$(document).ready(function() {
	var map = L.map('map').setView([44.494985, 11.342869], 13);

	// add an OpenStreetMap tile layer
	L.tileLayer('http://{s}.tile.osm.org/{z}/{x}/{y}.png', {
	    attribution: '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
	}).addTo(map);
	
	var drawnItems = new L.FeatureGroup();
	map.addLayer(drawnItems);

	var drawControl = new L.Control.Draw({
		draw: {
			position: 'topleft',
			polygon: {
				allowIntersection: false,
				drawError: {
					color: '#ee0000',
					timeout: 1000
				},
				shapeOptions: {
					color: '#bb0000',
					opacity: 0.7
				}
			},
			circle: {
				shapeOptions: {
					color: '#bb0000',
					opacity: 0.7
				}
			},
			rectangle: false,
			marker: false,
			polyline: false
		},
		edit: {
			featureGroup: drawnItems
		}
	});
	map.addControl(drawControl);

	map.on('draw:edited', function (e) {
		$('#generatewkt').click();
	});
	
	map.on('draw:deleted', function (e) {
		$('#generatewkt').click();
	});

	map.on('draw:created', function (e) {
		if (e.layerType == 'polygon') {
			drawnItems.addLayer(e.layer);
		} else {
			var polycircle = createGeodesicPolygon(e.layer.getLatLng().lng, e.layer.getLatLng().lat, e.layer.getRadius(), 16, 0);
			drawnItems.addLayer(polycircle);
		}
		$('#generatewkt').click();
	});
	
	$('#generatewkt').click(function() {
		$('#activationArea').val("");
		var wkt = "";
		if (drawnItems.getLayers().length > 0) {
			wkt = layerToWKT(drawnItems.getLayers()[0]);
		}
		for (i = 1; i < drawnItems.getLayers().length; i++) {
			wkt = wkt + ";" + layerToWKT(drawnItems.getLayers()[i]);
		}
		$('#activationArea').val(wkt);
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
			<h1>New Task</h1>
		</div>
	</div>
	<div class="container">
		<div class="row">
			<div class="span3 bs-docs-sidebar">
				<jsp:include page="/WEB-INF/templates/taskSidenav.jsp">
					<jsp:param value="protected.task.add" name="subsection" />
				</jsp:include>
			</div>
			<div class="span9">
				<div class="page-header">
					<h1>Draw activation area</h1>
					<p>Use the drawing tools below to draw the activation area. When you are done click on the &quot;generate
						WKT&quot; button to generate the WTK-encoded string that represents the activation area of this task.</p>
					<p>Whenever a user enters this area, the task will be notified to him and he will be able to accept or reject
						it (if the task can be rejected).</p>
				</div>
			</div>
			<div class="span12">
				<div id="map" style="height: 500px"></div>
				<p><a class="btn btn-primary btn-large" id="generatewkt">Generate WKT</a></p>
			</div>
			<div class="span12">
				<p>
				<form:form modelAttribute="task" action="${flowExecutionUrl}">
					<t:textarea
						label="Activation area WKT representation"
						path="activationArea" cssClass="input-block-level" />
						<button class="btn btn-primary" type="submit" name="_eventId_ok">Ok</button>
					</p>
					<p>
						<button class="btn btn-danger" type="submit" name="_eventId_cancel">Cancel</button>
					</p>
				</form:form>
				</p>
			</div>
		</div>
	</div>
</body>
</html>