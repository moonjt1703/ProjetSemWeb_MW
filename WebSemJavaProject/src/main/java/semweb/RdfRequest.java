package semweb;

import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
//import org.apache.jena.riot.Lang;

public class RdfRequest {
    
    private static final String sparqlEndPoint = "http://localhost:3030/transportEnCommun-Stetienne/sparql";

    public static void main(String args[]) {
        String queryString = "SELECT ?s ?p ?o WHERE {?s ?p ?o}";
        QueryExecution qexec = QueryExecutionFactory.sparqlService(sparqlEndPoint, queryString);
        try {
            ResultSet results = qexec.execSelect();
            ResultSetFormatter.outputAsJSON(System.out, results);


            // while(results.hasNext()) {
            //     QuerySolution qs = results.next();
            //     Resource stop = qs.getResource("stop");
            //     Literal lattitude = qs.getLiteral("latitude");
            //     Literal longitude = qs.getLiteral("longitude");
            //     Resource url = qs.getResource("url");
            //     Resource wikidataEntityId = qs.getResource("wikidataEntityId");
            //     Literal name = qs.getLiteral("name");
            //     cities.add(new City(city.getURI(), name.getString(), lattitude.getDouble(), longitude.getDouble(), url.getURI(), wikidataEntityId.getURI()));
            // }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            qexec.close();
        }
    }
}
