/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package wdhb.util;

import java.io.IOException;
import java.net.InetAddress;
import wdhb.gui.GUIManager;

/**
 *
 * @author JessVoig
 */
public abstract class StatusThread implements Runnable{
    public static Boolean run(String workstation) throws IOException{
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
}
