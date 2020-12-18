package semweb;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionFactory;
import org.apache.jena.vocabulary.XSD;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.lang.System;




public class RDFforStops {
    
	public static void main(String args[]) {
		
		try {
		    // create a reader
			Reader StopsReader = Files.newBufferedReader(Paths.get("src/main/resources/stops.txt"));
		    // create csv reader
			CSVReader csvStopsReader = new CSVReader(StopsReader);
		
		    // read one record at a time from Stops
		    String[] Stopsrecord;
		    while ((Stopsrecord = csvStopsReader.readNext()) != null) {
		    	
		        String ex = "http://www.example.com/";
				String geo = "http://www.w3.org/2003/01/geo/wgs84_pos#";
				String rdfs = "http://www.w3.org/2000/01/rdf-schema#";
				//String xsd = "http://www.w3.org/2001/XMLSchema#";
				String rdf = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
				
				Model model = ModelFactory.createDefaultModel();
				// create properties
				Property a = model.createProperty(rdf + "type");
				Property nom = model.createProperty(rdf + "stop");
				Property longitude=model.createProperty(rdf + "longitude");
				Property latitude=model.createProperty(rdf + "latitude");

				
						
				String id = ex + Stopsrecord[0];
				Literal name = model.createLiteral(Stopsrecord[1], "fr");
				String longi = Stopsrecord[2];
				String lati = Stopsrecord[3];
				
				
				Resource Stops = model.createResource(id);
				Stops.addProperty(a, ex + "PublicTransportStops");
				Stops.addProperty(nom, name);
				Stops.addProperty(longitude,longi, XSDDatatype.XSDdecimal);
				Stops.addProperty(latitude,lati, XSDDatatype.XSDdecimal);
				
				model.write(System.out, "TURTLE");
				
				String datasetURL = "http://localhost:3030/transportEnCommun-Stetienne";
				String sparqlEndpoint = datasetURL + "/sparql";
				String sparqlUpdate = datasetURL + "/update";
				String graphStore = datasetURL + "/data";
				RDFConnection conneg = RDFConnectionFactory.connect(sparqlEndpoint,sparqlUpdate,graphStore);
				conneg.load(model); // add the content of model to the triplestore
				
			}
			

		    // close readers
			csvStopsReader.close();
			StopsReader.close();

		} catch (IOException ex) {
		    ex.printStackTrace();
		} catch (CsvValidationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		
	}
	
}

