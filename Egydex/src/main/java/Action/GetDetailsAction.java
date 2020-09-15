/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Action;

import Modeles.EgyptObject;
import Modeles.Ressource;
import Modeles.Ressource.Type;
import Services.Services;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author colline
 */
public class GetDetailsAction extends Action {

    public boolean executer(HttpServletRequest request) {
        try {
            String name = (String) request.getParameter("name");
            String type = (String) request.getParameter("type");
            String uri = (String) request.getParameter("uri");
            Type t=null;
            if("dieu".equals(type)){
                t=Type.DIEU;
            } else if("lieu".equals(type)){
                t=Type.LIEU;
            }
            Ressource r= new Ressource(uri,name,t);
            EgyptObject obj = Services.getDetailsFrom(r);
            request.setAttribute("egyptObject", obj);
            if (obj!=null) {
                request.setAttribute("message", true);
            } else {
                request.setAttribute("message", false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
