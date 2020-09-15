/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modeles;
import Services.Services;
import java.util.*; 
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import static java.lang.Float.parseFloat;
import org.apache.jena.query.QuerySolution;

/**
 *
 * @author alice
 */
public class Lieu extends EgyptObject{

    String label;
    String thumbnail;
    String intro;
    List<Ressource> gods; //les dieux dont c'est le lieu de culte
    String latitude;
    String longitude;

    public Lieu(QuerySolution attributes) {
        this.label = Services.getNodeValue(attributes.get("name"));
        this.thumbnail = Services.getNodeValue(attributes.get("thumbnail"));
        this.intro = Services.getNodeValue(attributes.get("intro"));
        this.gods = Services.getRessourcesFromString(attributes.get("cultCenterOf"));
        this.latitude = Services.getNodeValue(attributes.get("latitude"));
        this.longitude = Services.getNodeValue(attributes.get("longitude"));
        
    }

    public String getLabel() {
        return label;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public List<Ressource> getGods() {
        List<Ressource> g = null;
        for (int i = 0; i < gods.size(); i++) {
            g.add(i, gods.get(i));
        }
        return g;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLabel(String l) {
        this.label = l;
    }

    public void setThumbnail(String t) {
        this.thumbnail = t;
    }

    public void addGod(Ressource d) {
        this.gods.add(d);
    }

    @Override
    public JsonObject getSerialisation() {
        JsonObject jsonContainer = new JsonObject();
        JsonArray jsonArray = new JsonArray();
        jsonContainer.addProperty("label", this.label);
        jsonContainer.addProperty("thumbnail", this.thumbnail);
        jsonContainer.addProperty("intro", this.intro);
        for (Ressource r : this.gods) {
            JsonObject jsonObj = new JsonObject();
            jsonObj.addProperty("name", r.getName());
            jsonObj.addProperty("type", r.getType().toString());
            jsonObj.addProperty("uri", r.getURI());
            jsonArray.add(jsonObj);
        }
        jsonContainer.add("gods", jsonArray);
        jsonContainer.addProperty("longitude", this.longitude);
        jsonContainer.addProperty("latitude", this.latitude);
        return jsonContainer;
    }

}
