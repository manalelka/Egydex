/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;


import Modeles.EgyptObject;
import Modeles.Ressource;
import Modeles.Dieu;
import Modeles.Lieu;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.customsearch.*;
import com.google.api.services.customsearch.model.Result;
import com.google.api.services.customsearch.model.Search;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.sparql.engine.http.QueryEngineHTTP;
/**
 *
 * @author jean
 */
public class Services {
    
    /* 
        @param stringQuery : an ASK query in SPARQL
        @return true or false, the result of the ask query in parameter
    */
    public static boolean dbPediaASKQuery(String stringQuery) // Requête dbPedia
    {
        // Create a Query with the given String
        Query query = QueryFactory.create(stringQuery);
        // DBPedia Endpoint
            String szEndpoint = "http://dbpedia.org/sparql";
        // Create the Execution Factory using the given Endpoint
        QueryExecution qexec = QueryExecutionFactory.sparqlService(
                szEndpoint, query);
        // Set Timeout
        ((QueryEngineHTTP)qexec).addParam("timeout", "10000");

        // Execute ASK Query
        boolean result = qexec.execAsk();
        
        return result;
    } // End of Method: dbPediaASKQuery
    
    /* 
        @param stringQuery : an ASK query in SPARQL
        @return true or false, the result of the ask query in parameter
    */
    public static ResultSet dbPediaSELECTQuery(String stringQuery) // Requête dbPedia
    {
        // Create a Query with the given String
        Query query = QueryFactory.create(stringQuery);
        // DBPedia Endpoint
            String szEndpoint = "http://dbpedia.org/sparql";
        // Create the Execution Factory using the given Endpoint
        QueryExecution qexec = QueryExecutionFactory.sparqlService(
                szEndpoint, query);
        // Set Timeout
        ((QueryEngineHTTP)qexec).addParam("timeout", "10000");

        // Execute ASK Query
        ResultSet result = qexec.execSelect();
        
        return result;
    } // End of Method: dbPediaSELECTQuery
    
    public static List<Ressource> search(String keywords) {
        //Get all ressources related to the keywords
        System.out.println("Service search");
        List<Result> liste = new ArrayList<>();
        try {
            liste = getRessourcesFrom(1, keywords);
            //liste.addAll(getRessourcesFrom(11, keywords));
            //liste.addAll(getRessourcesFrom(21, keywords));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //For all found ressource on wikipedia, we verify that its type corresponds to a god or a place on dbpedia.
        List<Ressource> ressources = new ArrayList<>();
        for(Result result : liste){
            String URI = wikiLinkToURI(result.getLink());
            String name = result.getTitle().replace(" - Wikipedia", "");
            Ressource.Type type = null;
                        
            // Requête SPARQL qui renvoie true si la ressource désigne un dieu dont le label contient name
            String searched = "<http://dbpedia.org/resource/"+URI+">";
            
            String godQuery = "PREFIX dbc: <http://dbpedia.org/resource/Category:>\n"+
                "PREFIX searched: "+searched+"\n" +
                "ASK {" +
                " { searched: <http://purl.org/dc/terms/subject> dbc:Egyptian_gods." + "}" +
                " UNION " +
                " { searched: <http://purl.org/dc/terms/subject> dbc:Egyptian_goddesses. } "+ 
                "}"; 
            
            String placeQuery = "PREFIX dbp: <http://dbpedia.org/property/>" +
                "" +
                "ASK {" +
                "?god dbp:cultCenter " + searched + "." +
                "}";

            // Exécution de la requête
            
            if (dbPediaASKQuery(godQuery)) {
                type = Ressource.Type.DIEU;
                Ressource r = new Ressource(URI,name,type);
                ressources.add(r);
            } else if (dbPediaASKQuery(placeQuery)) {
                type = Ressource.Type.LIEU;
                Ressource r = new Ressource(URI,name,type);
                ressources.add(r);
            }
        }
        return ressources;
    }
    
    public static EgyptObject getDetailsFrom(Ressource ressource) {
        String selectQuery = ressource.getSPARQLDetailsRequest();
        System.out.println("selectQuery "+selectQuery);
        ResultSet queryResult = dbPediaSELECTQuery(selectQuery);
        QuerySolution attributes = queryResult.next();
        
        if (ressource.type == Ressource.Type.LIEU) {
            Lieu place = new Lieu(attributes);
            System.out.println("LOOOOOOL"+place);
            return place;
        } else if (ressource.type == Ressource.Type.DIEU) {
            Dieu god = new Dieu(attributes);
            return god;
        }    
        return null;
    }
    
    public static String wikiLinkToURI(String link) {
        return link.substring(link.indexOf("/wiki/")+6);
    }
    
    public static List<Result> getRessourcesFrom(Integer start, String keyword){
        Customsearch customsearch= null;
        if (keyword.equals("")) {
            return new ArrayList<Result>();
        }
        try {
            customsearch = new Customsearch(new NetHttpTransport(),new JacksonFactory(), new HttpRequestInitializer() {
                public void initialize(HttpRequest httpRequest) {
                    httpRequest.setConnectTimeout(1800000);
                    httpRequest.setReadTimeout(1800000);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<Result>();
        }
        List<Result> resultList= new ArrayList<Result>();
        try {
            Customsearch.Cse.List list=customsearch.cse().list(keyword);
            list.setKey("AIzaSyB9uqYo_cAdIjFN3jhOm67KMbN2OSo8ivc"); 
//AIzaSyD_dd3SGeRcF2Ty-Ks6HtGYK5hV8W5F5d4 //AIzaSyB9uqYo_cAdIjFN3jhOm67KMbN2OSo8ivc //AIzaSyAb9EVUmfqdgXvG1yUkadmJ8iYjKmx90lM
            list.setCx("016602383964898489871:3b7rej1dunk"); 
//002215383702559434089:qelc7s30smi //016602383964898489871:3b7rej1dunk //002215383702559434089:weodrvtpoyd 
            list.setNum(new Long(10));
            list.setStart(new Long(start));
            Search results=list.execute();
            resultList=results.getItems();
            if (resultList==null) {
                resultList = new ArrayList<Result>();
            }
        }
        catch (  Exception e) {
            e.printStackTrace();
            return new ArrayList<Result>();
        }
        return resultList;
    }

    public static List<Ressource> getRessourcesFromString(RDFNode text) {
        List<Ressource> ressources = new ArrayList<Ressource>();
        String textToSplit = getNodeValue(text);
        textToSplit = textToSplit.replace("http://dbpedia.org/resource/", "");
        textToSplit = textToSplit.replace("and", ",");
        textToSplit = textToSplit.replace(" , ", ",");
        textToSplit = textToSplit.replace(" ,", ",");
        textToSplit = textToSplit.replace(", ", ",");
        String[] splitText = textToSplit.split(",");
        
        for(String textSource: splitText) {
            System.out.println("ressourceName= "+textSource);
            String URI = textSource;
            String name = textSource.replace("_", " ");
            Ressource.Type type = null;
            String searched = "<http://dbpedia.org/resource/"+textSource+">";
            String godQuery = "PREFIX dbc: <http://dbpedia.org/resource/Category:>\n"+
                "PREFIX searched: "+searched+"\n" +
                "ASK {" +
                " { searched: <http://purl.org/dc/terms/subject> dbc:Egyptian_gods." + "}" +
                " UNION " +
                " { searched: <http://purl.org/dc/terms/subject> dbc:Egyptian_goddesses. } "+ 
                "}"; 
            
            String placeQuery = "PREFIX dbp: <http://dbpedia.org/property/>" +
                "" +
                "ASK {" +
                "?god dbp:cultCenter " + searched + "." +
                "}";
            
            String symbolQuery = "PREFIX dbp: <http://dbpedia.org/property/>" +
                "PREFIX dbo: <http://dbpedia.org/ontology/>" +
                "" +
                "ASK {" +
                "?god dbp:symbol " + searched + "." +
                searched+" dbo:thumbnail ?image ." +
                "}";
            
            
            if (textSource.contains(" ")) {
                type = Ressource.Type.NO;
                name = name.replace("Not applicable;", "");
                URI = "";
            } else if (textSource.equals("")) {
                //Do Nothing
            } else if (dbPediaASKQuery(godQuery)) {
                type = Ressource.Type.DIEU;
            } else if (dbPediaASKQuery(placeQuery)) {
                type = Ressource.Type.LIEU;
            } else if (dbPediaASKQuery(symbolQuery)) {
                System.out.println("Ce symbol a une image");
                type = Ressource.Type.SYMBOL;
                String symbolImageQuery = "PREFIX dbo: <http://dbpedia.org/ontology/>" +
                "" +
                "SELECT ?image {" +
                searched+" dbo:thumbnail ?image ." +
                "}";
                QuerySolution imageRes = dbPediaSELECTQuery(symbolImageQuery).next();
                String imageURI = Services.getNodeValue(imageRes.get("image"));
                URI = imageURI; 
                System.out.println("URI de l'image :"+URI);
            } else {
                type = Ressource.Type.NO;
                URI = "";
            }
            
            
            if (!name.equals("")) {
                Ressource r = new Ressource(URI,name,type);
                ressources.add(r);
            }
        }
        
        return ressources;
    }
    
    public static String getNodeValue(RDFNode node) {
        if (node==null) {
            return "";
        }
	if (node.isLiteral()) {
		Literal lit = node.asLiteral();
		return lit.getLexicalForm();
	}
	if (node.isResource()) {
		Resource res = node.asResource();
		return res.getURI();
	}
	throw new RuntimeException("RDF node " + node + " is neither a literal nor a resource!");
    }
 
}
