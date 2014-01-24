/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package wdhb.util;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jessvoig
 */
public  class ExecuteVNC {
    public static void vncStart(String workstation){
        try {
            Runtime.getRuntime().exec("O:/UltraVNCWIN7/Files/UltraVNC/vncviewer.exe connect "+workstation+":7000");
        } catch (IOException ex) {
            Logger.getLogger(ExecuteVNC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void cDriveStart(String workstation){
        try {
            Runtime.getRuntime().exec("explorer.exe \\\\"+workstation+"\\c$");
        } catch (IOException ex) {
            Logger.getLogger(ExecuteVNC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
