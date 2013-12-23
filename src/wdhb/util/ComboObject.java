/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package wdhb.util;

/**
 *
 * @author JessVoig
 */
public class ComboObject extends Object{
    String id;
    String description;
    
    public void setID(String newID){
        this.id = newID;
    }
    public void setDescription(String newDescription){
        this.description = newDescription;
    }
    public String getID(){
        return id;
    }
    @Override
    public String toString(){
        return description;
    }
}
