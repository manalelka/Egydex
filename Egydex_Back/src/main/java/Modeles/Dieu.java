/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modeles;

import java.util.*;
import org.apache.jena.query.*;
import Modeles.Lieu;
import Services.Services;
import org.apache.jena.rdf.model.RDFNode;
import java.lang.Class;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * @author alice
 */
public class Dieu extends EgyptObject {

    String label;
    String gender;
    String description;
    String caption;
    String thumbnail;
    String godOf;
    String intro;

    List<Ressource> symbol;
    List<Ressource> cultCenters;
    List<Ressource> children;
    List<Ressource> parents;
    List<Ressource> siblings;
    List<Ressource> friends;

//dbo:thumbnail (image à afficher)
//Grosse description :
//dbo:abstract
    public Dieu(QuerySolution attributes) {
        
//        System.out.println(Services.getNodeValue(attributes.get("name")));
//        System.out.println(Services.getNodeValue(attributes.get("gender")));
//        System.out.println(Services.getNodeValue(attributes.get("description")));
//        System.out.println(Services.getNodeValue(attributes.get("caption")));
//        System.out.println(Services.getNodeValue(attributes.get("thumbnail")));
//        System.out.println(Services.getNodeValue(attributes.get("godOf")));
//        System.out.println(Services.getNodeValue(attributes.get("symbol")));
//        System.out.println(Services.getNodeValue(attributes.get("comment")));
//        System.out.println(Services.getNodeValue(attributes.get("intro")));
//        
//        System.out.println(Services.getNodeValue(attributes.get("cultCenters")));
//        System.out.println(Services.getNodeValue(attributes.get("offSprings")));
//        System.out.println(Services.getNodeValue(attributes.get("siblings")));
//        System.out.println(Services.getNodeValue(attributes.get("consorts")));
//        System.out.println(Services.getNodeValue(attributes.get("parents")));
        
        this.label = Services.getNodeValue(attributes.get("name"));
        this.gender = Services.getNodeValue(attributes.get("gender"));
        this.description = Services.getNodeValue(attributes.get("description"));
        this.caption = Services.getNodeValue(attributes.get("caption"));
        this.thumbnail = Services.getNodeValue(attributes.get("thumbnail"));
        this.godOf = Services.getNodeValue(attributes.get("godOf"));
        this.intro = Services.getNodeValue(attributes.get("intro"));
        
        this.symbol = Services.getRessourcesFromString(attributes.get("symbols"));
        this.cultCenters = Services.getRessourcesFromString(attributes.get("cultCenters"));
        this.children = Services.getRessourcesFromString(attributes.get("offSprings"));
        this.siblings = Services.getRessourcesFromString(attributes.get("siblings"));
        this.friends = Services.getRessourcesFromString(attributes.get("consorts"));
        this.parents = Services.getRessourcesFromString(attributes.get("parents"));

    }

    public String getLabel() {
        return label;
    }

    public String getGender() {
        return gender;
    }

    public String getDescription() {
        return description;
    }

    public String getCaption() {
        return caption;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getGodOf() {
        return godOf;
    }

    public List<Ressource> getCultCenters() {
        return cultCenters;
    }

    public List<Ressource> getSymbol() {
        return symbol;
    }

    public List<Ressource> getParents() {
        List<Ressource> p = null;
        for (int i = 0; i < parents.size(); i++) {
            p.add(i, parents.get(i));
        }
        return p;
    }

    public List<Ressource> getChildren() {
        List<Ressource> c = null;
        for (int i = 0; i < children.size(); i++) {
            c.add(i, children.get(i));
        }
        return c;
    }

    public List<Ressource> getSiblings() {
        List<Ressource> s = null;
        for (int i = 0; i < siblings.size(); i++) {
            s.add(i, siblings.get(i));
        }
        return s;
    }

    public List<Ressource> getFriends() {
        List<Ressource> f = null;
        for (int i = 0; i < friends.size(); i++) {
            f.add(i, friends.get(i));
        }
        return f;
    }

    public void setLabel(String l) {
        this.label = l;
    }

    public void setGender(String g) {
        this.gender = g;
    }

    public void setDescription(String d) {
        this.description = d;
    }

    public void setCaption(String c) {
        this.caption = c;
    }

    public void setThumbnail(String t) {
        this.thumbnail = t;
    }

    public void setGodOf(String g) {
        this.godOf = g;
    }

    public void setCultCenters(List<Ressource> c) {
        this.cultCenters = c;
    }

    public void setSymbol(List<Ressource> s) {
        this.symbol = s;
    }

    public void setChildren(List<Ressource> c) {
        this.children = c;
    }

    public void setParents(List<Ressource> p) {
        this.parents = p;
    }

    public void setSiblings(List<Ressource> s) {
        this.siblings = s;
    }

    public void setFriends(List<Ressource> f) {
        this.friends = f;
    }

    public void addChild(Ressource c) {
        this.children.add(c);
    }

    public void addParent(Ressource p) {
        this.parents.add(p);
    }

    public void addSibling(Ressource s) {
        this.siblings.add(s);
    }

    public void addFriend(Ressource f) {
        this.friends.add(f);
    }

    @Override
    public JsonObject getSerialisation() {
        JsonObject jsonContainer = new JsonObject();
        jsonContainer.addProperty("label", this.label);
        jsonContainer.addProperty("thumbnail", this.thumbnail);
        jsonContainer.addProperty("gender", this.gender);
        jsonContainer.addProperty("caption", this.caption);
        jsonContainer.addProperty("description", this.description);
        jsonContainer.addProperty("intro", this.intro);
        jsonContainer.addProperty("godOf", this.godOf);
        JsonArray jsonArraySymbols = new JsonArray();
        for (Ressource s : this.symbol) {
            JsonObject obj = new JsonObject();
            obj.addProperty("name", s.getName());
            obj.addProperty("type", s.getType().toString());
            obj.addProperty("uri", s.getURI());
            jsonArraySymbols.add(obj);
        }
        JsonArray jsonArrayChildren = new JsonArray();
        for (Ressource s : this.children) {
            JsonObject obj = new JsonObject();
            obj.addProperty("name", s.getName());
            obj.addProperty("type", s.getType().toString());
            obj.addProperty("uri", s.getURI());
            jsonArrayChildren.add(obj);
        }
        JsonArray jsonArrayFriends = new JsonArray();

        for (Ressource s : this.friends) {
            JsonObject obj = new JsonObject();
            obj.addProperty("name", s.getName());
            obj.addProperty("type", s.getType().toString());
            obj.addProperty("uri", s.getURI());
            jsonArrayFriends.add(obj);
        }
        JsonArray jsonArrayParents = new JsonArray();

        for (Ressource s : this.parents) {
            JsonObject obj = new JsonObject();
            obj.addProperty("name", s.getName());
            obj.addProperty("type", s.getType().toString());
            obj.addProperty("uri", s.getURI());
            jsonArrayParents.add(obj);
        }
        JsonArray jsonArraySiblings = new JsonArray();

        for (Ressource s : this.siblings) {
            JsonObject obj = new JsonObject();
            obj.addProperty("name", s.getName());
            obj.addProperty("type", s.getType().toString());
            obj.addProperty("uri", s.getURI());
            jsonArraySiblings.add(obj);
        }
        JsonArray jsonArrayCultCenters = new JsonArray();

        for (Ressource s : this.cultCenters) {
            JsonObject obj = new JsonObject();
            obj.addProperty("name", s.getName());
            obj.addProperty("type", s.getType().toString());
            obj.addProperty("uri", s.getURI());
            jsonArrayCultCenters.add(obj);
        }        
        jsonContainer.add("symbols", jsonArraySymbols);
        jsonContainer.add("children", jsonArrayChildren);
        jsonContainer.add("parents", jsonArrayParents);
        jsonContainer.add("siblings", jsonArraySiblings);
        jsonContainer.add("friends", jsonArrayFriends);
        jsonContainer.add("cultCenters", jsonArrayCultCenters);
        return jsonContainer;
    }

}
