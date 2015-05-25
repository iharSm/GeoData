function Property(data) {
	this.name = ko.observable(data.name);
	this.address = ko.observable(data.address);
	this.latitude = ko.observable(data.latitude);
	this.longitude = ko.observable(data.longitude);
};

function PropertyModel() {
	var self = this;
	self.items = ko.observableArray([]);
	self.newName = ko.observable();
	self.newAddress = ko.observable();
	self.newLatitude = ko.observable();
	self.newLongitude = ko.observable();

	self.addNewProperty = function() {
		var newProperty = new Property({
			name : this.newName(),
			address : this.newAddress(),
			latitude : this.newLatitude(),
			longitude : this.newLongitude()
		});
		self.items.push(newProperty);

		var loc = [ newProperty.latitude(), newProperty.longitude() ];
		L.marker(loc).addTo(map).bindPopup(newProperty.name()).openPopup();

		$.postJSON("/index", ko.toJSON(newProperty), function(returnedData) {
			alert(returnedData.name);
			var prop = new Property({
				name : returnedData.newName,
				address : returnedData.newAddress,
				latitude : returnedData.newLatitude,
				longitude : returnedData.newLongitude
			});
			//self.items.push(prop);
		});

	};

	self.updateLocation = function(lat, lon) {
		self.newLongitude = ko.observable(lon);
		self.newLatitude = ko.observable(lat);
	};
};

$.postJSON = function(url, data, callback) {
	$.ajax({
		url : url,
		type : "post",
		crossDomain : true,
		data : data,
		success : callback,
		beforeSend : function(xhr) {
			xhr.setRequestHeader("accept", "application/json");
			xhr.setRequestHeader("Content-Type", "application/json");
		}
	});
};
