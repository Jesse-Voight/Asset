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
    public static ArrayList executeSQL(String inputQuery) {
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
                while (res.next()) {
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

    public static ArrayList loadMonitors(String inputQuery) {
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
                while (monitorModelResult.next()) {
                    monitorModelMap.put(monitorModelResult.getString(1), monitorModelResult.getString(2) + " " + monitorModelResult.getString(3));
                }



                ResultSet res = querier.executeQuery("Select * FROM monitor order by AssetNo");
                /*ResultSetMetaData columnNames = res.getMetaData();
                for (int i = 1; i < columnNames.getColumnCount() + 1; i++) {
                    //System.out.println(columnNames.getColumnName(i));           //Test for dynamic column creation
                }*/
                while (res.next()) {
                    String asset = res.getString("AssetNo");
                    String serial = res.getString("SerialNo");
                    String status = res.getString("Status");
                    String idMonitorModel = (String) monitorModelMap.get(res.getString("idMonitorModel"));
                    
                    String notes = res.getString("notes");
                    
                    int dateCode = res.getInt("DateInstalled");
                    Date dateFormatted = new Date((long)dateCode*1000);          //Date conversion from 10 digit unix number to correct date FML
                    String dateInstalled = dateFormatted.toString();
                    
                    //System.out.println(asset + "\t " + serial);
                    String tempData[] = {asset, serial, status, notes, idMonitorModel, dateInstalled};
                    resultList.add(tempData);
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
