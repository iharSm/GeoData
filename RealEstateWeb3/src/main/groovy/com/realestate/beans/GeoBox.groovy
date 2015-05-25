package com.realestate.beans

import com.thinkaurelius.titan.core.attribute.Geoshape

class GeoBox {
	LatLng _northEast;
	LatLng _southWest;

	class LatLng{
		String lat;
		String lng;
	}

	public Geoshape getBox(){
		return Geoshape.box(Float.valueOf(_southWest.lat),Float.valueOf(_southWest.lng)
		,Float.valueOf( _northEast.lat),Float.valueOf( _northEast.lng));
	}
}
