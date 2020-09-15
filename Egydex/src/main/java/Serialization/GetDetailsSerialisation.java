/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Serialization;

import Modeles.EgyptObject;
import Modeles.Ressource;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Manal El Karchouni
 */
public class GetDetailsSerialisation extends Serialization {

    @Override
    public void serialiser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        EgyptObject object = (EgyptObject) request.getAttribute("egyptObject");
        JsonObject jsonContainer = object.getSerialisation();
        PrintWriter out = this.getWriterWithJsonHeader(response);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        gson.toJson(jsonContainer, out);
    }
}
