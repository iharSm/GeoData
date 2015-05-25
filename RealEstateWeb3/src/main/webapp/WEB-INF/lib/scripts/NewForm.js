function addForm() {
	var display = document.getElementById("mainLocationMenu").style.display;
	if (display == "none") {
		document.getElementById("mainLocationMenu").style.display = "block";
		document.getElementById("AddLocation").style.display = "none";
	} else {
		document.getElementById("mainLocationMenu").style.display = "none";
		document.getElementById("AddLocation").style.display = "block";
	}
}

function saveLocation() {
	L.marker([ lat, lon ]).addTo(map).bindPopup(loc.innerHTML);
}

$(".nav li").on("click", function() {
	var display = document.getElementById("mainLocationMenu").style.display;
	if (display == "none") {
		document.getElementById("mainLocationMenu").style.display = "block";
		document.getElementById("AddLocation").style.display = "none";
	} else {
		document.getElementById("mainLocationMenu").style.display = "none";
		document.getElementById("AddLocation").style.display = "block";
	}
});
