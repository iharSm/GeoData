function MapModel() {
	var self = this;
	self.map = L.map('map');

	self.setMapCenter = function(lat, lon) {
		self.map.setView([ lat, lon ], 13);
		return self;
	};

	self.setDefaultLayer = function() {
		L
				.tileLayer(
						'http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png',
						{
							maxZoom : 18,
							attribution : 'Map data &copy; <a href="http://openstreetmap.org">OpenStreetMap</a> contributors, <a href="http://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, Imagery © <a href="http://cloudmade.com">CloudMade</a>'
						}).addTo(self.map);
		return self;
	};

	self.addMapListener = function(listenerName, callback) {
		self.map.on(listenerName, callback);
		return self;
	};
};

function onMapClick(e) {
	var ll = e.latlng.toString();
	ll = ll.slice(7, ll.length - 1);
	var llArray = ll.split(",");

	$("#latitude").val(llArray[0]);
	$("#longitude").val(llArray[1]);
	viewModel.updateLocation(llArray[0], llArray[1]);
};

function onMapDrag() {
	loadPropertiesInBox(map.getBounds());
}

function init() {
	viewModel = new PropertyModel();
	ko.applyBindings(viewModel);
	var mapModel = (new MapModel()).setMapCenter(42.4405556, -76.4969444)
			.setDefaultLayer().addMapListener('click', onMapClick)
			.addMapListener('dragend', onMapDrag).addMapListener('zoomend',
					onMapDrag);
	map = mapModel.map;

	var MyControl = L.Control.extend({
		options : {
			position : 'topright'
		},

		onAdd : function(map) {
			var container = L.DomUtil.create('div', 'my-custom-control');
			return container;
		}
	});

	map.addControl(new MyControl());
	map.addControl(new MyControl('bar', {
		position : 'bottomleft'
	}));

	loadPropertiesInBox(map.getBounds());

};

function loadPropertiesInBox(box) {
	$.postJSON("/box", ko.toJSON(box), function(returnedData) {

		viewModel.items.removeAll();

		for (i = 0; i < returnedData.propertyList.length; i++) {
			var returnedObj = returnedData.propertyList[i];
			var prop = new Property({
				name : returnedObj.name,
				address : returnedObj.address,
				latitude : returnedObj.latitude,
				longitude : returnedObj.longitude
			});
			/*
			 * temp fix. don't send duplicate elements from the server or at
			 * least improve complexity
			 * 
			 * var alreadyAdded = false; for (j = 0; j
			 * <viewModel.items().length; j++) { if
			 * ((viewModel.items()[j].latitude() == prop.latitude()) &&
			 * (viewModel.items()[j].longitude() == prop.longitude())) {
			 * alreadyAdded = true; } } if (!alreadyAdded) {
			 * viewModel.items.push(prop); }
			 */

			viewModel.items.push(prop);
			var loc = [ prop.latitude(), prop.longitude() ];
			L.marker(loc).addTo(map).bindPopup(prop.name()).openPopup();
		}
	});
}

function loadAllProperties() {

	var prop = new Property({
		name : 'test',
		address : 'test',
		latitude : 'test',
		longitude : 'test'
	});

	$.postJSON("/ind", ko.toJSON(prop), function(returnedData) {

		for (i = 0; i < returnedData.propertyList.length; i++) {
			var returnedObj = returnedData.propertyList[i];
			var prop = new Property({
				name : returnedObj.name,
				address : returnedObj.address,
				latitude : returnedObj.latitude,
				longitude : returnedObj.longitude
			});
			viewModel.items.push(prop);
			var loc = [ prop.latitude(), prop.longitude() ];
			L.marker(loc).addTo(map).bindPopup(prop.name()).openPopup();
		}
	});
}
