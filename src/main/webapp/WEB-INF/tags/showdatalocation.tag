<%@tag language="java" description="Extended input tag to allow for sophisticated errors" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags"%>
<%@ taglib prefix="pa" uri="/WEB-INF/tlds/paserver-funcs.tld"%>
<%@attribute name="data" required="true" type="java.lang.Object"%>
<%@attribute name="tableid" required="true" type="java.lang.String"%>
<%@attribute name="geojsonurl" required="true" type="java.lang.String"%>
<div id="map" class="span12" style="height: 500px"></div>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>


<script>
	var map = L.map('map').setView([ 44.494985, 11.342869 ], 10);
	L
			.tileLayer(
					'http://{s}.tile.osm.org/{z}/{x}/{y}.png',
					{
						attribution : '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
					}).addTo(map);
	$.ajax({
		url : "${contextPath}/${geojsonurl}",
		dataType : "json"
	}).done(function(msg) {
		L.geoJson(msg).addTo(map);
	});
</script>
<p>
	<c:out value="${fn:length(data)}" />
	points.
</p>