<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<title>Real Estate</title>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<!-- STYLESHEETS -->
<link rel="stylesheet" href="/c/leaflet.css" />
<link rel="stylesheet" href="/c/index.css" />
<link rel="stylesheet" href="/b/css/bootstrap.css" />
<link rel="stylesheet" href="/b/css/bootstrap-theme.min.css" />

<!--[if lte IE 8]><link rel="stylesheet" href="/c/leaflet.ie.css" /><![endif]-->
<!--SCRIPTS-->
<script src="/ext/leaflet.js"></script>
<script src="/s/leaflet-index.js"></script>
<script src="/s/MapModel.js"></script>
<script src="/s/NewForm.js"></script>
<script src="/ext/jquery-1.10.2.js"></script>
<script src="/s/ViewModel.js"></script>
<script src="/ext/knockout-2.3.0.js"></script>
<script src="/b/js/bootstrap.js"></script>
<script src="/b/js/bootstrap.min.js"></script>
<script src="/b/js/npm.js"></script>
<script>
	$(document).ready(init);
</script>

</head>
<body>
	<nav class="navbar navbar-default">
		<div class="container-fluid">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="/">GeoData</a>
			</div>

			<!-- Collect the nav links, forms, and other content for toggling -->
			<div class="collapse navbar-collapse"
				id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav">
					<li class="active"><a href="javascript:addForm()">Add New<span
							class="sr-only">dfsf</span></a></li>
				</ul>
				<form class="navbar-form navbar-left" role="search">
					<div class="form-group">
						<input type="text" class="form-control" placeholder="Search">
					</div>
					<button type="submit" class="btn btn-default">Submit</button>
				</form>
			</div>
			<!-- /.navbar-collapse -->
		</div>
		<!-- /.container-fluid -->
	</nav>
	<!-- 	<table id="menu">
		<tr>
			<td><div class="mainMenu">Appartments</div></td>
			<td><div class="mainMenu">Rentals</div></td>
			<td><div class="mainMenu">Statistics</div></td>
			<td><div class="mainMenu" onclick="addForm()">Add New</div></td>
		</tr>
	</table> -->
	<table>
		<tr>
			<td>
				<div id="mainLocationMenu" class="locationmenu">
					<span>
						<div class='location' lat='42.447724' lon='-76.478072'
							onclick="addLocation(this)">Samuel Curtis Johnson Graduate
							School of Management</div>
					</span> <span>
						<div class='location' lat='42.4434' , lon='-76.4814'
							onclick="addLocation(this)">The School of Operations
							Research and Information Engineering</div>
					</span>
				</div>

				<div id="AddLocation" class="locationmenu" style="display: none">
					<form id='AddLocationForm' data-bind="submit: addNewProperty">
						<span> Name</br> <input data-bind="value: newName" />
						</span> <span> </br>Address</br> <input data-bind="value: newAddress" />
						</span> <span> </br>Latitude</br> <input data-bind="value: newLatitude"
							id='latitude' />
						</span> </br> <span> </br>Longitude</br> <input data-bind="value: newLongitude"
							id='longitude' />
						</span> <input type="submit" value="Add">
					</form>
					<p>Your items:</p>
					<ul data-bind="foreach: items">
						<li><input data-bind="value: name" /> <input
							data-bind="value: address" /> <input data-bind="value: latitude" />
							<input data-bind="value: longitude" /></li>
					</ul>
				</div>
			</td>
			<td>
				<div id="map" style="width: 1100px; height: 650px"></div>
			</td>
		</tr>
	</table>
</body>
</html>
