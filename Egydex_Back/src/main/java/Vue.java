/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import Modeles.EgyptObject;
import Modeles.Ressource;
import Services.Services;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.customsearch.*;
import com.google.api.services.customsearch.model.Query;
import com.google.api.services.customsearch.model.Result;
import com.google.api.services.customsearch.model.Search;
import java.util.ArrayList;
import java.util.List;

import org.apache.jena.query.*;
import org.apache.jena.sparql.engine.http.QueryEngineHTTP;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.util.Iterator;
import org.apache.jena.rdf.model.Literal;

/**
 *
 * @author Roxane
 */
public class Vue {
 
    
    public static void main(String[] args){
       
        String keywords = "Horus";
        try {
            //System.out.println(Services.search(keywords).toString());
            Ressource r = new Ressource("New_Kingdom", "New_Kingdom", Ressource.Type.LIEU);
            Services.getDetailsFrom(r);
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }
    
}