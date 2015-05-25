package com.realestate.database;

import java.util.ArrayList;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.realestate.beans.GeoBox;
import com.realestate.beans.PropertyModel;
import com.realestate.graph.algorithms.GraphAlgorithms;
import com.thinkaurelius.titan.core.TitanGraph;
import com.thinkaurelius.titan.core.attribute.Geoshape;
import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.util.ElementHelper;

@Component
public class DatabaseController {
	@Inject
	TitanDataSource titanDataSource;

	 Logger logger = LoggerFactory.getLogger(DatabaseController.class);
	 
	public void savePropertyClass(PropertyModel property) {

		TitanGraph graph = titanDataSource.graph;
		Vertex realEstate = graph.addVertexWithLabel("property");
		ElementHelper.setProperties(realEstate, "name", property.getName());
		ElementHelper.setProperties(realEstate, "address", property.getAddress());
		//ElementHelper.setProperties(realEstate, "description", property.getDescription());
		Vertex location = graph.addVertexWithLabel("coordinates");
		ElementHelper.setProperties(
				location, "location",
				property.createGeoPoint());
		Edge e = graph.addEdge(null, realEstate, location, "locatedAt");

		graph.commit();

		for (Vertex vertex : graph.getVertices()) {
			System.out.println(vertex.getPropertyKeys());
			for(String key : vertex.getPropertyKeys() )
			System.out.println(vertex.getProperty(key));
		}
	}

	public ArrayList<PropertyModel> loadPropertyClass(){
		TitanGraph graph = titanDataSource.graph;
		ArrayList<PropertyModel> propList = new ArrayList<PropertyModel>();
		  for (Vertex vertex : graph.getVertices()) {
			  PropertyModel property = new PropertyModel();
			  if (vertex.getProperty("name") == null) continue; 
			  property.setName((String) vertex.getProperty("name"));
			  
			  for(Edge edge : vertex.getEdges(Direction.OUT, "locatedAt")) {
				    System.out.println(edge);
					  property.setLocation((Geoshape)edge.getVertex(Direction.IN).getProperty("location"));
				  }
			  propList.add(property);
			  }
		  return propList;
	}
	
	public ArrayList<PropertyModel> loadPropertiesinBox(GeoBox box){
		TitanGraph graph = titanDataSource.graph;

		double start = System.nanoTime();
		double finish = System.nanoTime();
		logger.info("[OPEN GRAPH START] loadPropertiesinBox at {}", start);		

		finish = System.nanoTime();
		logger.info("[END OPEN GRAPH] loadPropertiesinBox. at : {}", finish);
		logger.info("[DELTA] loadPropertiesinBox. took : {}", (double)(finish-start) / 1000000000.0);
		
		start = System.nanoTime();
		logger.info("[OPEN GraphAlgorithms] loadPropertiesinBox at {}", start);	
		ArrayList<PropertyModel> propList = GraphAlgorithms.getPropertiesInBox(graph, box.getBox());
		finish = System.nanoTime();
		logger.info("[END GraphAlgorithms] loadPropertiesinBox. at : {}", finish);
		logger.info("[DELTA] loadPropertiesinBox. took : {}", (double)(finish-start) / 1000000000.0);

		start = System.nanoTime();
		logger.info("[START SHUTDOWN] loadPropertiesinBox at {}", start);	
		finish = System.nanoTime();
		logger.info("[END SHUTDOWN] loadPropertiesinBox. at : {}", finish);
		logger.info("[DELTA] loadPropertiesinBox. took : {}", (double)(finish-start) / 1000000000.0);
		
		return propList;
		
	}
}
