package com.realestate.graph.algorithms

import java.util.ArrayList

import org.slf4j.Logger
import org.slf4j.LoggerFactory

import com.realestate.beans.GeoBox
import com.realestate.beans.PropertyModel
import com.realestate.database.DatabaseController
import com.thinkaurelius.titan.core.attribute.Geoshape
import com.tinkerpop.blueprints.Compare
import com.tinkerpop.blueprints.Graph
import com.tinkerpop.blueprints.Vertex
import com.tinkerpop.gremlin.groovy.Gremlin
import com.thinkaurelius.titan.core.attribute.Geo

class GraphAlgorithms {
	static Logger logger = LoggerFactory.getLogger(GraphAlgorithms.class)
	static {
		Gremlin.load()
	}

	@Deprecated
	public static ArrayList<PropertyModel> getPropertiesInBox(Graph g, GeoBox box) {
		def results = []
		ArrayList<PropertyModel> propertyList = new ArrayList<PropertyModel>()
		def neLat = box.get_northEast().getLat().toBigDecimal()
		def neLng =	box.get_northEast().getLng().toBigDecimal()
		def swLat =	box.get_southWest().getLat().toBigDecimal()
		def swLng = box.get_southWest().getLng().toBigDecimal()

		g.V.hasNot('name',null).out.transform{[lng: it.latitude.toBigDecimal(), lat: it.longitude.toBigDecimal()]}
		.filter{it.lat > swLat && it.lat < neLat && it.lng > swLng && it.lng < neLng}
		.path{it.name}{}{it}
		.fill(results)
		println results
		//	for(int i = 0; i<50; i++)
		results.eachWithIndex { item, index ->
			PropertyModel p = new PropertyModel()
			p.name = item.getAt(0)
			p.latitude = item.getAt(2).lng
			p.longitude = item.getAt(2).lat
			propertyList.add(p)
		}

		return  propertyList
	}

	public static ArrayList<PropertyModel> getPropertiesInBox(Graph g, Geoshape box) {
		def results = []
		ArrayList<PropertyModel> propertyList = new ArrayList<PropertyModel>()
		g.V.has('location', Geo.WITHIN, box).as('location').in('locatedAt').as('property').table().cap().scatter().fill(results)

		logger.info('GraphAlgorithms#getPropertiesInBox returned:' + results)

		results.eachWithIndex { item, index ->
			PropertyModel p = new PropertyModel()
			p.name = item.getAt(1).name
			p.address = item.getAt(1).address
			p.latitude = item.getAt(0).location.getPoint().latitude
			p.longitude = item.getAt(0).location.getPoint().longitude
			propertyList.add(p)
		}

		return  propertyList
	}


}