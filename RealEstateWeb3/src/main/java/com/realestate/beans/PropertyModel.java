package com.realestate.beans;

import java.io.Serializable;

import com.thinkaurelius.titan.core.attribute.Geoshape;

public class PropertyModel implements Serializable {
	private static final long serialVersionUID = 8034950363474384082L;
	private String name;
	private String address;
	private String latitude;
	private String longitude;
	private String description = "Test";

	public PropertyModel() {

	}

	public void setLocation(Geoshape point) {
		this.latitude = String.valueOf(point.getPoint().getLatitude());
		this.longitude = String.valueOf(point.getPoint().getLongitude());

	}

	public Geoshape createGeoPoint() {
		if (latitude == null || latitude.isEmpty())
			return null;
		if (longitude == null || longitude.isEmpty())
			return null;
		return Geoshape.point(Float.parseFloat(latitude),
				Float.parseFloat(longitude));
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
