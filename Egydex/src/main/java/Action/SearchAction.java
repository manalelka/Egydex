/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Action;
 
/**
 *
 * @author jean
 */
import Modeles.Ressource;
import Services.Services;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
public class SearchAction extends Action{
    
    @Override
    public boolean executer(HttpServletRequest request){
        System.out.println("****************************************************SearchAction");
        try{
            String keywords = (String)request.getParameter("keywords");
            System.out.println("Recherche de :"+keywords);
            List<Ressource> ressources = Services.search(keywords);
            request.setAttribute("ressources", ressources);
            if(!ressources.isEmpty()){
                request.setAttribute("message", true);
                System.out.println("IL Y A UN RESULTAT");
            }else{
                request.setAttribute("message", false);
                System.out.println("PAS DE RESULTAT");
            }
        }catch(Exception e){
            return false;
        }
        return true; 
    }

}
