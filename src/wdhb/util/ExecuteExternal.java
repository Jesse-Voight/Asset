/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wdhb.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author jessvoig
 */
public class ExecuteExternal {

    public static Boolean checkPC(String workstation) {
        //Not implemented, too slow - Needs Multithreading Ability
        String ipString = "";
        try {
            InetAddress ia = InetAddress.getByName(workstation);
            ipString = ia.getHostAddress();
            System.out.println("passed getbyname");
            try {
                ia.isReachable(2000);
                System.out.println("passed isReacheable");
                return true;
            } catch (IOException e) {
                System.out.println("failed isReacheable");
                return false;
            }
        } catch (UnknownHostException ex) {
            System.out.println("failed getbyname");
            return false;
        }
    }

    public static Boolean newCheck(String workstation) throws IOException {
        //Not implemented, too slow - Needs Multithreading Ability
        InetAddress address = InetAddress.getByName(workstation);

        if (address == null) {
            System.out.println("Is null");
            return false;
        } else if (!address.isReachable(1500)) {
            System.out.println("Cant connect");
            return false;
        } else {
            System.out.println("Host is reachable");
            return true;
        }

    }

    public static void vncStart(String workstation) {
        try {
            Runtime.getRuntime().exec("//fs1/apps$/apps/UltraVNCWIN7/Files/UltraVNC/vncviewer.exe connect " + workstation + ":7000 -user jessvoig -password lemonchiffoN5");
        } catch (IOException ex) {
            Logger.getLogger(ExecuteExternal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static Boolean pingStart(String workstation){
        //System.out.println(workstation);
         try{
            String cmd = "";
               
                    // For Windows
                    cmd = "ping -n 1 " + workstation;
            

            Process myProcess = Runtime.getRuntime().exec(cmd);
            myProcess.waitFor();
            OutputStream os = myProcess.getOutputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(myProcess.getInputStream()));
            String temp;
            while ((temp = reader.readLine()) != null){
                System.out.println(temp);
                if(temp.indexOf("unreachable") != -1){
                    return false;
                }
            }
            System.out.println(temp);
            if(myProcess.exitValue() == 0) {
                return (true);
            } else {
                return (false);
            }

    } catch( Exception e ) {
            e.printStackTrace();
            return (false);
    }
    }
    public static void cDriveStart(String workstation) {
        try {
            Runtime.getRuntime().exec("explorer.exe \\\\" + workstation + "\\c$");
        } catch (IOException ex) {
            Logger.getLogger(ExecuteExternal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void compMgmt(String workstation) {
        try {
            Runtime.getRuntime().exec("mmc.exe compmgmt.msc /computer:" + workstation);

        } catch (IOException ex) {
            Logger.getLogger(ExecuteExternal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
