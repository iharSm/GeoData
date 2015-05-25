# GeoData

GeoData project uses maven to generate executable .jar file. To run the project start cassandra db on your local host first and then run RealEstateWeb.jar. Spring Boot will start a local server and connect to the database backend through Titan. To access the project use localhost:8080/ URL.

On the firt run database schema, defined in TitanDataSource.groovy, will be created. Currently schema contains two vertices connected with 'locatedAt' edge. First Vertex has 'name', 'address', 'description' and 'propertyType' property keys. The second vertex is a geoshape point with 'location property key.



