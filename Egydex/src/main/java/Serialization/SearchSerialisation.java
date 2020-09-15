/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Serialization;

import Modeles.Ressource;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author jean
 */
public class SearchSerialisation extends Serialization {

    @Override
    public void serialiser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("****************************************************SearchSerialization");
        JsonObject jsonContainer = new JsonObject();
        JsonArray jsonArrayRessources = new JsonArray();
        ArrayList<Ressource> ressources = (ArrayList<Ressource>) request.getAttribute("ressources");
        System.out.println("ressource = "+ressources);
        for (Ressource r : ressources) {
            JsonObject jsonRessource = new JsonObject();
            jsonRessource.addProperty("name", r.getName());
            jsonRessource.addProperty("type", r.getType().toString());
            jsonRessource.addProperty("uri", r.getURI());
            jsonArrayRessources.add(jsonRessource);
        }
        System.out.println("ressource = "+jsonArrayRessources);
        jsonContainer.add("ressources", jsonArrayRessources);
        jsonContainer.addProperty("message", (Boolean) request.getAttribute("message"));
        PrintWriter out = this.getWriterWithJsonHeader(response);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        gson.toJson(jsonContainer, out);
    }

}
