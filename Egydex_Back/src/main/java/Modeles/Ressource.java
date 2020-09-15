/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modeles;

/**
 *
 * @author jean
 */
public class Ressource {
    
    public String URI;
    public String name;
    public Type type;
    
    public enum Type {
    LIEU,
    DIEU,
    SYMBOL,
    NO
    }

    public void setURI(String URI) {
        this.URI = URI;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getURI() {
        return URI;
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }
    
    public Ressource(String URI, String name, Type type) {
        this.URI = URI;
        this.name = name;
        this.type = type;
    }
    
    public String getSPARQLDetailsRequest() {
        if (type == Type.DIEU) {
            return getGodSPARQLDetailsRequest();
        } else {
            return getPlaceSPARQLDetailsRequest();
        }
    }
    
    //TODO SPARQL: demander pour chaque liste de ressources toutes les infos (ex sibling = [URI, name, type])
    
    public String getGodSPARQLDetailsRequest() {
        String prefix = "<http://dbpedia.org/resource/"+this.URI+">";
        return "PREFIX searched: "+prefix+"\n" +
            "                \n" +
            "SELECT DISTINCT ?name\n" +
            "                ?intro\n" +
            "                ?gender\n" +
            "                ?description\n" +
            "                ?caption\n" +
            "                ?thumbnail\n" +
            "                ?godOf\n" +
            "                ( group_concat(DISTINCT ?cultCenter;separator=\",\") AS ?cultCenters)\n" +
            "                ( group_concat(DISTINCT ?offSpring;separator=\",\") AS ?offSprings)\n" +
            "                ( group_concat(DISTINCT ?parent;separator=\",\") AS ?parents)\n" +
            "                ( group_concat(DISTINCT ?sibling;separator=\",\") AS ?siblings)\n" +
            "                ( group_concat(DISTINCT ?symbol;separator=\",\") AS ?symbols)\n" +
            "                ( group_concat(DISTINCT ?consort;separator=\",\") AS ?consorts)\n" +
            "                FROM <http://dbpedia.org>\n" +
            "                WHERE {\n" +
            "                      { searched: <http://www.w3.org/2000/01/rdf-schema#label> ?name .\n" +
            "                                  FILTER ( langmatches( lang( ?name), \"EN\"))\n" +
            "                      }\n" +
            "                      OPTIONAL { searched: <http://dbpedia.org/ontology/abstract> ?intro. \n" +
            "                                           FILTER ( langmatches( lang( ?intro), \"EN\"))\n" +
            "                               }\n" +
            "                      OPTIONAL { searched: <http://www.w3.org/2000/01/rdf-schema#comment> ?comment. \n" +
            "                                           FILTER ( langmatches( lang( ?comment), \"EN\"))\n" +
            "                               }\n" +
            "                      OPTIONAL {  searched: <http://xmlns.com/foaf/0.1/gender> ?gender . }\n" +
            "                      OPTIONAL { searched: <http://purl.org/dc/terms/description> ?description . \n" +
            "                                           FILTER ( langmatches( lang( ?description), \"EN\"))\n" +
            "                               }\n" +
            "                      OPTIONAL { searched: <http://dbpedia.org/property/caption> ?caption . }\n" +
            "                      OPTIONAL { searched: <http://dbpedia.org/ontology/thumbnail> ?thumbnail . }\n" +
            "                      OPTIONAL { searched: <http://dbpedia.org/property/godOf> ?godOf . }\n" +
            "                      OPTIONAL { searched: <http://dbpedia.org/property/cultCenter> ?cultCenter . }\n" +
            "                      OPTIONAL { searched: <http://dbpedia.org/property/offspring> ?offSpring . }\n" +
            "                      OPTIONAL { searched: <http://dbpedia.org/property/parents> ?parent . }\n" +
            "                      OPTIONAL { searched: <http://dbpedia.org/property/siblings> ?sibling . }\n" +
            "                      OPTIONAL { searched: <http://dbpedia.org/property/symbol> ?symbol . }\n" +
            "                      OPTIONAL { searched: <http://dbpedia.org/property/consort> ?consort . } }\n" +
            "                GROUP BY  ?name ?intro ?comment ?gender ?description ?caption ?thumbnail ?godOf";
    }
    
    public String getPlaceSPARQLDetailsRequest() {
        String prefix = "<http://dbpedia.org/resource/"+this.URI+">";
        return "PREFIX searched: "+prefix+"\n"+
            "\n" +
            "SELECT DISTINCT  ?name\n" +
            " ?intro\n" +
            " ?thumbnail\n" +
            " ( group_concat(DISTINCT ?god;separator=\",\") AS ?cultCenterOf)\n" +
            " ?latitude\n" +
            " ?longitude\n" +
            " FROM <http://dbpedia.org>\n" +
            " WHERE { \n" +
            "     { searched: <http://www.w3.org/2000/01/rdf-schema#label> ?name .\n" +
            "       FILTER ( langmatches( lang( ?name), \"EN\") ) \n" +
            "     }\n" +
            "\n" +
            "\n" +
            "     OPTIONAL { searched: <http://dbpedia.org/ontology/abstract> ?intro.\n" +
            "                FILTER ( langmatches( lang( ?intro), \"EN\") ) \n" +
            "              }\n" +
            "     OPTIONAL { searched: <http://dbpedia.org/ontology/thumbnail> ?thumbnail . }\n" +
            "     OPTIONAL { searched: <http://www.w3.org/2000/01/rdf-schema#comment> ?comment . \n" +
            "                FILTER ( langmatches( lang( ?comment), \"EN\") ) \n" +
            "              }\n" +
            "     OPTIONAL { ?god <http://dbpedia.org/property/cultCenter> searched: }\n" +
            "     OPTIONAL { searched: <http://www.w3.org/2003/01/geo/wgs84_pos#lat> ?latitude. }\n" +
            "     OPTIONAL { searched: <http://www.w3.org/2003/01/geo/wgs84_pos#long> ?longitude. } \n" +
            "}\n" +
            "\n" +
            "GROUP BY  ?name ?thumbnail ?latitude ?longitude ?intro";
    }
}
