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

    public static ArrayList loadPCs(String inputQuery) {
        String url = "jdbc:mysql://localhost:3306/";
        String dbName = "assetDB";
        String driver = "com.mysql.jdbc.Driver";
        String userName = "jessvoig";
        String password = "qzpm9876";
        ArrayList resultList = new ArrayList();
        HashMap locationMap = new HashMap();
        HashMap pcModelMap = new HashMap();

        try {
            Class.forName(driver).newInstance();
            try (Connection conn = DriverManager.getConnection(url + dbName, userName, password)) {
                Statement st = conn.createStatement();

                ResultSet locationResult = st.executeQuery("Select * FROM location");
                while (locationResult.next()) {                                                             //location foreign key connection
                    locationMap.put(locationResult.getString(1), locationResult.getString(2));
                }

                ResultSet pcModelResult = st.executeQuery("Select * FROM pcmodel");
                while (pcModelResult.next()) {                                                             //location foreign key connection
                    pcModelMap.put(pcModelResult.getString(1), pcModelResult.getString(2));
                }

                ResultSet pcResult = st.executeQuery("Select * FROM PC WHERE Name like 'WSC%' or Name like 'LTC%' order by Name");
                while (pcResult.next()) {
                    String id = pcResult.getString("idPC");
                    String name = pcResult.getString("Name");

                    //String location = pcResult.getString("idLocation");
                    String location = (String) locationMap.get(pcResult.getString("idLocation"));  //monitor model

                    String pcModel = (String) pcModelMap.get(pcResult.getString("idPCModel"));

                    String serialNumber = pcResult.getString("SerialNo");
                    String monitor1 = pcResult.getString("Monitor1");
                    String monitor2 = pcResult.getString("Monitor2");
                    String assetNum = pcResult.getString("AssetNo");

                    //String lastLogin = pcResult.getString("LastLogin");
                    int loginDateCode = pcResult.getInt("LastLogin");
                    Date loginDateFormatted = new Date((long) loginDateCode * 1000);          //Date conversion from 10 digit unix number to correct date FML
                    String lastLogin = loginDateFormatted.toString();

                    String notes = pcResult.getString("Notes");
                    String status = pcResult.getString("Status");

                    //String repDate = pcResult.getString("RepDate");
                    int repDateCode = pcResult.getInt("RepDate");
                    Date repDateFormatted = new Date((long) repDateCode * 1000);          //Date conversion from 10 digit unix number to correct date FML
                    String repDate = repDateFormatted.toString();

                    String colNames[] = {id, name, location, pcModel, serialNumber, monitor1, monitor2, assetNum, lastLogin,
                        notes, status, repDate};
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
                while (monitorModelResult.next()) {                                                             //monitormodel foreign key connection
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
                    Date dateFormatted = new Date((long) dateCode * 1000);          //Date conversion from 10 digit unix number to correct date FML
                    String dateInstalled = dateFormatted.toString();

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

    public static ArrayList loadMonitorModels(String inputQuery) {
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

                ResultSet res = st.executeQuery("Select * FROM monitormodel order by idMonitorModel asc");

                while (res.next()) {
                    String idMonitor = res.getString("idMonitorModel");
                    String make = res.getString("Make");
                    String model = res.getString("Model");
                    String colNames[] = {idMonitor, make, model};
                    resultList.add(colNames);

                }
                conn.close();
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    public static ArrayList loadPCModels() {
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
                ResultSet res = st.executeQuery("Select * FROM pcmodel order by idPCModel asc");

                while (res.next()) {
                    String idPCModel = res.getString("idPCModel");
                    String make = res.getString("Make");
                    String model = res.getString("Model");
                    String subModel = res.getString("SubModel");
                    String colNames[] = {idPCModel, make, model, subModel};
                    resultList.add(colNames);

                }
                conn.close();
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e) {
            e.printStackTrace();
        }
        return resultList;

    }

    public static ArrayList loadLocations(String inputQuery) {
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

                ResultSet res = st.executeQuery("Select * FROM location order by idLocation asc");

                while (res.next()) {
                    String idLocation = res.getString("idLocation");
                    String building = res.getString("Building");
                    String department = res.getString("Department");
                    String address1 = res.getString("Address1");
                    String address2 = res.getString("Address2");
                    String town = res.getString("Town");
                    String rc = res.getString("RC");
                    String colNames[] = {idLocation, building, department, address1, address2, town, rc};
                    resultList.add(colNames);

                }
                conn.close();
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    public static ArrayList loadUsers() {
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
                ResultSet res = st.executeQuery("Select distinct Name FROM userhistory order by Name asc");

                while (res.next()) {
                    String name = res.getString("Name");
                    String idPC = "PC newest";//res.getString("idPC");
                    String colNames[] = {name, idPC};
                    resultList.add(colNames);

                }
                conn.close();
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e) {
            e.printStackTrace();
        }
        return resultList;

    }
}
