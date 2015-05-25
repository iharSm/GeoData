package com.realestate.database

import org.springframework.stereotype.Component

import com.thinkaurelius.titan.core.PropertyKey
import com.thinkaurelius.titan.core.TitanFactory
import com.thinkaurelius.titan.core.TitanGraph
import com.thinkaurelius.titan.core.attribute.Geoshape
import com.thinkaurelius.titan.core.schema.TitanManagement
import com.tinkerpop.blueprints.Vertex
import com.tinkerpop.gremlin.groovy.Gremlin

@Component
public class TitanDataSource {
	static {
		Gremlin.load()
	}

	public boolean isNew
	public TitanGraph graph
	public TitanManagement mgmt
	public static final String INDEX_NAME = "search"

	public TitanDataSource() {
		
		graph = TitanFactory.open("graph.properties")
		mgmt = graph.getManagementSystem()

		//temp solution;
		//Iterator<Vertex> i = graph.getVertices("isNew", false).iterator()
		//if(!(i.hasNext() && i.next().getProperty("isNew").equals(false)))
		//	defineDataModel()
		if( graph.V.has('isNew', false) == null ) defineDataModel()
	}

	private void defineDataModel() {
		
		//temp solution;
		//checks whether database has been created.
		 PropertyKey isNew = mgmt.makePropertyKey("isNew").dataType(Boolean.class).make()
		 	
		 // property keys
		 //property
		 PropertyKey name = mgmt.makePropertyKey("name").dataType(String.class).make()
		 PropertyKey address = mgmt.makePropertyKey("address").dataType(String.class).make()
		 PropertyKey description = mgmt.makePropertyKey("description").dataType(String.class).make()
		 PropertyKey propertyType = mgmt.makePropertyKey("propertyType").dataType(String.class).make()
		 //coordinates
		 PropertyKey location = mgmt.makePropertyKey("location").dataType(Geoshape.class).make()
		 
		 //graph indexes
		 mgmt.buildIndex('byNameAndLocation', Vertex.class).addKey(name).addKey(location).buildCompositeIndex()
		 mgmt.buildIndex('byNameLocationAndPropertyType', Vertex.class).addKey(name).addKey(location).addKey(propertyType).buildCompositeIndex()
		 mgmt.buildIndex('byLocationAndPropertyType', Vertex.class).addKey(location).addKey(propertyType).buildCompositeIndex()
		 mgmt.buildIndex('byLocation', Vertex.class).addKey(location).buildCompositeIndex()
		 mgmt.buildIndex('byIsName', Vertex.class).addKey(isNew).buildCompositeIndex()
		 mgmt.commit()
		 
		 graph.addVertex(null).setProperty("isNew", false)
		 graph.commit()
	}
}
