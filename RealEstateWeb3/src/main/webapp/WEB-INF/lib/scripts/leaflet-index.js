function addLocation(loc) {
	var lat;
	var lon;
	lat = loc.getAttribute('lat');
	lon = loc.getAttribute('lon');
	L.marker([ lat, lon ]).addTo(map).bindPopup(loc.innerHTML);
}

function addMarker() {
	L.marker([ 53.93055, , ]).addTo(map).bindPopup(
			"<b>My House!</b><br />I'm Lovin' it.").openPopup();
}

function prepareMap(){
map = L.map('map').setView([ 53.9, 27.566667 ], 12);

L.tileLayer('http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png',
	{
		maxZoom : 18,
		attribution : 'Map data &copy; <a href="http://openstreetmap.org">OpenStreetMap</a> contributors, <a href="http://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, Imagery © <a href="http://cloudmade.com">CloudMade</a>'
	}).addTo(map);

map.on('click', onMapClick);
}
