/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package databaseaccess;

import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author Jesse <24051java1@gmail.com>
 */
public class DatabaseAccess {

    /**
     * @param args the command line arguments
     */
    String lastResult = "";
    
    public static void main(String[] args) {
        executeSQL("hi");
        
    }
    public static ArrayList executeSQL(String inputQuery){
        String url = "jdbc:mysql://localhost:3306/";
        String dbName = "assetDB";
        String driver = "com.mysql.jdbc.Driver";
        String userName = "jessvoig";
        String password = "qzpm9876";
        String unk = "";
        ArrayList resultList = new ArrayList();

        try {
            Class.forName(driver).newInstance();
            try (Connection conn = DriverManager.getConnection(url + dbName, userName, password)) {
                Statement st = conn.createStatement();
                //ResultSet res = st.executeQuery("inputQuery");
                ResultSet res = st.executeQuery("Select * FROM PC WHERE Name like 'WSC%' or Name like 'LTC%' order by Name");
                while (res.next()){
                    String name = res.getString("Name");
                    String id = res.getString("idPC");
                    System.out.println(id + "\t " + name);
                    resultList.add(id + " " + name + "\n");
                }
                conn.close();
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }
    // TODO code application logic here
}
