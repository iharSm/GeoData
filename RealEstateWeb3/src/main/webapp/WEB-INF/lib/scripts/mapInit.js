var options, map, layer, speciesLayer;

function init() {

	options = {
		projection : "EPSG:900913",
		units : "m",
		maxExtent : new OpenLayers.Bounds(-20037508, -20037508, 20037508,
				20037508)
	};

	map = new OpenLayers.Map('map', options, {
		numZoomLevels : 12
	});
	layer = new OpenLayers.Layer.Google("Google Physical", {
		type : google.maps.MapTypeId.TERRAIN
	});
	speciesLayer = new OpenLayers.Layer.WMS("Australian Magpie",
			"http://biocache.ala.org.au/ws/webportal/wms/reflect", {
				layers : 'ALA:occurrences',
				srs : 'EPSG:900913',
				format : 'image/png',
				cql_filter : "Australian Magpie",
				transparent : true,
				env : 'color:22a467;name:circle;size:3;opacity:0.8',
				exceptions : 'application-vnd.ogc.se_inimage'
			});
	map.addLayer(layer);
	map.addLayer(speciesLayer);
	map.addControl(new OpenLayers.Control.LayerSwitcher());

	var proj = new OpenLayers.Projection("EPSG:4326");
	var point = new OpenLayers.LonLat(133, -28);
	point.transform(proj, map.getProjectionObject());
	map.setCenter(point, 4);
}
