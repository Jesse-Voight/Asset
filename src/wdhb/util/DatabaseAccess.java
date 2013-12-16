/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wdhb.util;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Jesse <24051java1@gmail.com>
 */
public class DatabaseAccess {

    /**
     * @param args the command line arguments
     */
   
    public static ArrayList executeSQL(String inputQuery){
        String url = "jdbc:mysql://localhost:3306/";
        String dbName = "assetDB";
        String driver = "com.mysql.jdbc.Driver";
        String userName = "jessvoig";
        String password = "qzpm9876";
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
                    String assetNum = res.getString("AssetNo");
                    String location = res.getString("idLocation");
                    String pcModel = res.getString("idPCModel");
                    //System.out.println(id + "\t " + name);
                    String colNames[] = {id, name, assetNum, location, pcModel}; 
                    resultList.add(colNames);
                }
                conn.close();
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }
     public static ArrayList loadMonitors(String inputQuery){
        String url = "jdbc:mysql://localhost:3306/";
        String dbName = "assetDB";
        String driver = "com.mysql.jdbc.Driver";
        String userName = "jessvoig";
        String password = "qzpm9876";
        ArrayList resultList = new ArrayList();
        HashMap monitorModelMap = new HashMap();

        try {
            Class.forName(driver).newInstance();
            try (Connection conn = DriverManager.getConnection(url + dbName, userName, password)) {
                Statement querier = conn.createStatement();
                
                
                ResultSet monitorModelResult = querier.executeQuery("Select * FROM monitormodel");
                while (monitorModelResult.next()){
                    monitorModelMap.put(monitorModelResult.getString(1), monitorModelResult.getString(2));
                }
                
                
                
                ResultSet res = querier.executeQuery("Select * FROM monitor");
                ResultSetMetaData columnNames = res.getMetaData();
                for (int i = 1; i < columnNames.getColumnCount() + 1; i++)
                {
                    System.out.println(columnNames.getColumnName(i));
                }
                while (res.next()){
                    String asset = res.getString("AssetNo");
                    String serial = res.getString("SerialNo");
                    String status = res.getString("Status");
                    String idMonitor = res.getString("idMonitor"); 
                    String notes = res.getString("notes");
                    //System.out.println(asset + "\t " + serial);
                    String tempData[] = {asset, serial, status, idMonitor, notes}; 
                    resultList.add(tempData);
                }
                conn.close();
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }
     public static String[] loadMonitorModel(String monitorModel){
        String url = "jdbc:mysql://localhost:3306/";
        String dbName = "assetDB";
        String driver = "com.mysql.jdbc.Driver";
        String userName = "jessvoig";
        String password = "qzpm9876";
        ArrayList resultList = new ArrayList();

        try {
            Class.forName(driver).newInstance();
            try (Connection conn = DriverManager.getConnection(url + dbName, userName, password)) {
                Statement st = conn.createStatement();
                ResultSet res = st.executeQuery("Select * FROM monitormodel Where idMonitorModel = "+monitorModel);
                while (res.next()){
                    String idMonitorModel = res.getString("idMonitorModel");
                    String make = res.getString("Make");
                    String model = res.getString("Model");
                    String tempData[] = {idMonitorModel, make, model}; 
                    return tempData;
                }
                conn.close();
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    // TODO code application logic here
}
