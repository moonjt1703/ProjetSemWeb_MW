package semweb;
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

public class RdfForTrips {
	
    public static void main(String args[]) {
		
		try {
		    // create a reader
            Reader TripsReader = Files.newBufferedReader(Paths.get("src/main/resources/trips.txt"));
            // create csv reader
            CSVReader csvTripsReader = new CSVReader(TripsReader);
             // read one record at a time
		    String[] Tripsrecord;
		    while ((Tripsrecord = csvTripsReader.readNext()) != null) {
		    	
		        String ex = "http://www.example.com/";
				//String geo = "http://www.w3.org/2003/01/geo/wgs84_pos#";
				String rdfs = "http://www.w3.org/2000/01/rdf-schema#";
				//String xsd = "http://www.w3.org/2001/XMLSchema#";
				String rdf = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
				
				Model model = ModelFactory.createDefaultModel();
				// create properties
				Property a = model.createProperty(rdf + "type");
				Property label = model.createProperty(rdfs + "label");
				Property name = model.createProperty(rdf + "name");
				
						
				String id = ex + Tripsrecord[2];
				Literal nom = model.createLiteral(Tripsrecord[3], "fr");
				Literal englishname = model.createLiteral(Tripsrecord[3], "en");

				
				Resource trips = model.createResource(id);
				trips.addProperty(a, ex + "PublicTransportLine");
				trips.addProperty(name, nom);
				trips.addProperty(label, englishname);

				model.write(System.out, "TURTLE");
				
				String datasetURL = "http://localhost:3030/transportEnCommun-Stetienne";
				String sparqlEndpoint = datasetURL + "/sparql";
				String sparqlUpdate = datasetURL + "/update";
				String graphStore = datasetURL + "/data";
				RDFConnection conneg = RDFConnectionFactory.connect(sparqlEndpoint,sparqlUpdate,graphStore);
				conneg.load(model); // add the content of model to the triplestore
				
            }
               // close readers
		    csvTripsReader.close();
			TripsReader.close();
		} catch (IOException ex) {
		    ex.printStackTrace();
		} catch (CsvValidationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    
}
}
