/*
 * Requires:
 * <script src="<c:url value="/resources/js/OpenLayers.js"/>"></script>
 */

function layerToWKT(layer) {
	latlngs = layer.getLatLngs();
	if (latlngs.length < 3) {
		return "POLYGON(())";
	}
	currpol = "POLYGON((";
	for (var i = 0; i < latlngs.length; i++) {
		currpol = currpol + latlngs[i].lng + " " + latlngs[i].lat + ",";
	}
	currpol = currpol + latlngs[0].lng + " " + latlngs[0].lat + "))";
	return currpol;
}

//courtesy of
//http://gis.stackexchange.com/questions/23661/how-to-make-a-predetermined-polygon-circle-from-4-coordinates-the-radius-and
function createGeodesicPolygon(x, y, radius, sides, rotation) {

 var latlon = new OpenLayers.LonLat(x, y);

 var angle;
 var new_lonlat, geom_point;
 var points = [];
 var approxPolygon;

 for (var i = 0; i < sides; i++) {
     angle = (i * 360 / sides) + rotation;
     new_lonlat = OpenLayers.Util.destinationVincenty(latlon, angle, radius);
     points.push(L.latLng(new_lonlat.lat, new_lonlat.lon));
 }
 points.push(points[0]);
 approxPolygon = L.polygon(points);
 return approxPolygon;
}