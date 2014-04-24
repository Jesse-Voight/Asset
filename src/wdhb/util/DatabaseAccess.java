/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wdhb.util;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Jesse <24051java1@gmail.com>
 */
public class DatabaseAccess {

    public static ArrayList loadPCs(Boolean loadDecommision) {

        String url = "jdbc:mysql://websrv:3306/";
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

                ResultSet locationResult = st.executeQuery("Select * FROM Location");
                while (locationResult.next()) {                                                             //location foreign key connection
                    locationMap.put(locationResult.getString(1), (locationResult.getString(2) + " " + locationResult.getString(3)));
                }

                ResultSet pcModelResult = st.executeQuery("Select * FROM PCModel");
                while (pcModelResult.next()) {                                                             //location foreign key connection
                    pcModelMap.put(pcModelResult.getString(1), (pcModelResult.getString(2) + " " + pcModelResult.getString(3)));
                }
                ResultSet pcResult;
                if (loadDecommision == true) {
                    //System.out.println(loadDecommision);
                    pcResult = st.executeQuery("Select * FROM PC WHERE Name like 'WSC%' or Name like 'LTC%' or Name like 'JULIE%' or Name like 'TAB%'order by Name");
                } else {
                    //System.out.println(loadDecommision);
                    pcResult = st.executeQuery("Select * FROM PC WHERE Status = 'A' AND Name like 'WSC%'or Status = 'A' AND Name like 'JULIE%' or Status = 'A' AND Name like 'TAB%' or Status = 'A' AND Name like 'LTC%' order by Name;");
                }
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
                    
                    String commUsers = pcResult.getString("CommUsers");

                    String colNames[] = {id, name, location, pcModel, serialNumber, monitor1, monitor2, assetNum, lastLogin,
                        notes, status, commUsers};
                    resultList.add(colNames);
                }
                conn.close();
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    public static String[] loadPCData(String named) {
        String url = "jdbc:mysql://websrv:3306/";
        String dbName = "assetDB";
        String driver = "com.mysql.jdbc.Driver";
        String userName = "jessvoig";
        String password = "qzpm9876";
        String[] resultList = null;
        try {
            Class.forName(driver).newInstance();
            try (Connection conn = DriverManager.getConnection(url + dbName, userName, password)) {
                Statement st = conn.createStatement();

                ResultSet res = st.executeQuery("Select * FROM PC Where Name = '" + named + "'");

                while (res.next()) {
                    String id = res.getString("idPC");
                    String name = res.getString("Name");

                    String serialNumber = res.getString("SerialNo");
                    String monitor1 = res.getString("Monitor1");
                    String monitor2 = res.getString("Monitor2");
                    String assetNum = res.getString("AssetNo");

                    //String lastLogin = pcResult.getString("LastLogin");
                    int loginDateCode = res.getInt("LastLogin");
                    Date loginDateFormatted = new Date((long) loginDateCode * 1000);          //Date conversion from 10 digit unix number to correct date FML
                    String lastLogin = loginDateFormatted.toString();

                    String notes = res.getString("Notes");
                    String status = res.getString("Status");
                    String location = res.getString("idLocation");
                    String pcModel = res.getString("idPCModel");

                    //String repDate = pcResult.getString("RepDate");
                    int repDateCode = res.getInt("RepDate");
                    Date repDateFormatted = new Date((long) repDateCode * 1000);          //Date conversion from 10 digit unix number to correct date FML
                    String repDate = repDateFormatted.toString();

                    String commUsers = res.getString("CommUsers");

                    String colNames[] = {id, name, serialNumber, monitor1, monitor2, assetNum, lastLogin,
                        notes, status, repDate, location, pcModel, commUsers};
                    resultList = colNames;
                }
                conn.close();
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    public static String getMaxMonitor() {
        String url = "jdbc:mysql://websrv:3306/";
        String dbName = "assetDB";
        String driver = "com.mysql.jdbc.Driver";
        String userName = "jessvoig";
        String password = "qzpm9876";
        String result = "";

        try {
            Class.forName(driver).newInstance();
            try (Connection conn = DriverManager.getConnection(url + dbName, userName, password)) {
                Statement querier = conn.createStatement();

                ResultSet monitorIDResult = querier.executeQuery("select max(idMonitor) from Monitor");

                while (monitorIDResult.next()) {
                    int maxPlusOne = monitorIDResult.getInt("max(idMonitor)") + 1;
                    result = String.valueOf(maxPlusOne);
                    //System.out.println(" " + result);
                }

                conn.close();
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String getMaxMonitorModel() {
        String url = "jdbc:mysql://websrv:3306/";
        String dbName = "assetDB";
        String driver = "com.mysql.jdbc.Driver";
        String userName = "jessvoig";
        String password = "qzpm9876";
        String result = "";

        try {
            Class.forName(driver).newInstance();
            try (Connection conn = DriverManager.getConnection(url + dbName, userName, password)) {
                Statement querier = conn.createStatement();

                ResultSet monitorIDResult = querier.executeQuery("select max(idMonitorModel) from MonitorModel");

                while (monitorIDResult.next()) {
                    int maxPlusOne = monitorIDResult.getInt("max(idMonitorModel)") + 1;
                    result = String.valueOf(maxPlusOne);
                    //System.out.println(" " + result);
                }

                conn.close();
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String getMaxLocation() {
        String url = "jdbc:mysql://websrv:3306/";
        String dbName = "assetDB";
        String driver = "com.mysql.jdbc.Driver";
        String userName = "jessvoig";
        String password = "qzpm9876";
        String result = "";

        try {
            Class.forName(driver).newInstance();
            try (Connection conn = DriverManager.getConnection(url + dbName, userName, password)) {
                Statement querier = conn.createStatement();

                ResultSet monitorIDResult = querier.executeQuery("select max(idLocation) from Location");

                while (monitorIDResult.next()) {
                    int maxPlusOne = monitorIDResult.getInt("max(idLocation)") + 1;
                    result = String.valueOf(maxPlusOne);
                    //System.out.println(" " + result);
                }

                conn.close();
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String getMaxPCModel() {
        String url = "jdbc:mysql://websrv:3306/";
        String dbName = "assetDB";
        String driver = "com.mysql.jdbc.Driver";
        String userName = "jessvoig";
        String password = "qzpm9876";
        String result = "";

        try {
            Class.forName(driver).newInstance();
            try (Connection conn = DriverManager.getConnection(url + dbName, userName, password)) {
                Statement querier = conn.createStatement();

                ResultSet monitorIDResult = querier.executeQuery("select max(idPCModel) from PCModel");

                while (monitorIDResult.next()) {
                    int maxPlusOne = monitorIDResult.getInt("max(idPCModel)") + 1;
                    result = String.valueOf(maxPlusOne);
                    //System.out.println(" " + result);
                }

                conn.close();
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static ArrayList loadUserInfo(String user) {
        String url = "jdbc:mysql://websrv:3306/";
        String dbName = "assetDB";
        String driver = "com.mysql.jdbc.Driver";
        String userName = "jessvoig";
        String password = "qzpm9876";
        ArrayList resultList = new ArrayList();
        HashMap pcMap = new HashMap();

        try {
            Class.forName(driver).newInstance();
            try (Connection conn = DriverManager.getConnection(url + dbName, userName, password)) {
                Statement querier = conn.createStatement();

                ResultSet res = querier.executeQuery("select * from PC");
                while (res.next()) {
                    pcMap.put(res.getString(1), res.getString(2));
                }

                ResultSet userResult = querier.executeQuery("select * from UserHistory where Name ='" + user + "'" + " order by Date desc");

                while (userResult.next()) {
                    String pcId = (String) pcMap.get(userResult.getString("idPC"));
                    String name = userResult.getString("Name");

                    int dateCode = userResult.getInt("Date");
                    Date dateFormatted = new Date((long) dateCode * 1000);          //Date conversion from 10 digit unix number to correct date FML
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy kk:mm:ss");
                    
                    String dateInstalled = sdf.format(dateFormatted);

                    String tempData[] = {pcId, name, dateInstalled};
                    resultList.add(tempData);
                }
                conn.close();
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }
    public static ArrayList loadPCUserDetails(String workstationNumber){
        String url = "jdbc:mysql://websrv:3306/";
        String dbName = "assetDB";
        String driver = "com.mysql.jdbc.Driver";
        String userName = "jessvoig";
        String password = "qzpm9876";
        ArrayList resultList = new ArrayList();
        String idPC = "";
        try {
            Class.forName(driver).newInstance();
            try (Connection conn = DriverManager.getConnection(url + dbName, userName, password)) {
                Statement querier = conn.createStatement();
                ResultSet getID = querier.executeQuery("Select idPC from PC where Name = '"+workstationNumber+"'");
                while (getID.next()){
                    idPC = getID.getString("idPC");
                }
                ResultSet res = querier.executeQuery("Select * FROM UserHistory Where idPC = '"+idPC+"' order by Date DESC");
                
                while (res.next()) {
                    String name = res.getString("Name");
                    int dateCode = res.getInt("Date");
                    Date dateFormatted = new Date((long) dateCode * 1000);          //Date conversion from 10 digit unix number to correct date FML
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy kk:mm:ss");
                    
                    String dateInstalled = sdf.format(dateFormatted);

                    String tempData[] = {name, dateInstalled};
                    resultList.add(tempData);
                }
                conn.close();
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }
    public static String[] loadPCSpecs(String workstationNumber){
        String url = "jdbc:mysql://websrv:3306/";
        String dbName = "assetDB";
        String driver = "com.mysql.jdbc.Driver";
        String userName = "jessvoig";
        String password = "qzpm9876";
        String[] resultList = {};

        try {
            Class.forName(driver).newInstance();
            try (Connection conn = DriverManager.getConnection(url + dbName, userName, password)) {
                Statement querier = conn.createStatement();
                ResultSet res = querier.executeQuery("Select * FROM PC Where Name = '"+workstationNumber+"'");
                
                while (res.next()) {
                    String memory = res.getString("Memory");
                    String cpu = res.getString("CPU");
                    String hdd = res.getString("HDD");
                    String mac = res.getString("MAC");
                    

                    String[] tempList = {memory,cpu,hdd,mac};
                    resultList = tempList;
                }
                conn.close();
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }
    
    public static ArrayList loadMonitors() {
        String url = "jdbc:mysql://websrv:3306/";
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

                ResultSet monitorModelResult = querier.executeQuery("Select * FROM MonitorModel");
                while (monitorModelResult.next()) {                                                             //monitormodel foreign key connection
                    monitorModelMap.put(monitorModelResult.getString(1), monitorModelResult.getString(2) + " " + monitorModelResult.getString(3));
                }

                ResultSet res = querier.executeQuery("Select * FROM Monitor order by idMonitor");
                /*ResultSetMetaData columnNames = res.getMetaData();
                 for (int i = 1; i < columnNames.getColumnCount() + 1; i++) {
                 //System.out.println(columnNames.getColumnName(i));           //Test for dynamic column creation
                 }*/
                while (res.next()) {
                    String monitorID = res.getString("idMonitor");
                    String asset = res.getString("AssetNo");
                    String serial = res.getString("SerialNo");
                    String status = res.getString("Status");

                    String idMonitorModel = (String) monitorModelMap.get(res.getString("idMonitorModel"));

                    String notes = res.getString("notes");

                    int dateCode = res.getInt("DateInstalled");
                    Date dateFormatted = new Date((long) dateCode * 1000);          //Date conversion from 10 digit unix number to correct date FML
                    String dateInstalled = dateFormatted.toString();

                    String tempData[] = {monitorID, asset, serial, status, notes, idMonitorModel, dateInstalled};
                    resultList.add(tempData);
                }
                conn.close();
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }
    
    public static String[] loadMonitorDetails(String assetNo){
        String url = "jdbc:mysql://websrv:3306/";
        String dbName = "assetDB";
        String driver = "com.mysql.jdbc.Driver";
        String userName = "jessvoig";
        String password = "qzpm9876";
        String[] resultString = {};

        try {
            Class.forName(driver).newInstance();
            try (Connection conn = DriverManager.getConnection(url + dbName, userName, password)) {
                Statement st = conn.createStatement();

                ResultSet res = st.executeQuery("Select * FROM Monitor WHERE AssetNo = '"+assetNo+"' order by idMonitorModel asc");

                while (res.next()) {
                    //String idMonitor = res.getString("idMonitorModel");
                    String assetNumber = res.getString("AssetNo");
                    String serialNumber = res.getString("SerialNo");
                    String status = res.getString("Status");
                    String notes = res.getString("Notes");
                    String modelMake = res.getString("idMonitorModel");
                    int dateCode = res.getInt("DateInstalled");
                    Date dateFormatted = new Date((long) dateCode * 1000);          //Date conversion from 10 digit unix number to correct date FML
                    String dateInstalled = dateFormatted.toString();
                    
                    String[] colNames = {assetNumber, serialNumber, status, notes, modelMake, dateInstalled.toString()};
                    resultString = colNames;
                }
                conn.close();
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e) {
            e.printStackTrace();
        }
        return resultString;
    }

    public static ArrayList loadMonitorModels() {
        String url = "jdbc:mysql://websrv:3306/";
        String dbName = "assetDB";
        String driver = "com.mysql.jdbc.Driver";
        String userName = "jessvoig";
        String password = "qzpm9876";
        ArrayList resultList = new ArrayList();

        try {
            Class.forName(driver).newInstance();
            try (Connection conn = DriverManager.getConnection(url + dbName, userName, password)) {
                Statement st = conn.createStatement();

                ResultSet res = st.executeQuery("Select * FROM MonitorModel order by idMonitorModel asc");

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

    public static void executeQuery(String inputQuery) {
        String url = "jdbc:mysql://websrv:3306/";
        String dbName = "assetDB";
        String driver = "com.mysql.jdbc.Driver";
        String userName = "jessvoig";
        String password = "qzpm9876";
        try {
            Class.forName(driver).newInstance();
            try (Connection conn = DriverManager.getConnection(url + dbName, userName, password)) {
                Statement st = conn.createStatement();
                st.addBatch(inputQuery);
                st.executeBatch();
                conn.close();
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e) {
            e.printStackTrace();
        }

    }
    public static Boolean editPCDetails(String location, String idPCModel, String serialNo, String monitor1, String monitor2, String assetNumber,
                String notes, String status, String commonUsers, String replacementDate, String idPC){
        String url = "jdbc:mysql://websrv:3306/";
        String dbName = "assetDB";
        String driver = "com.mysql.jdbc.Driver";
        String userName = "jessvoig";
        String password = "qzpm9876";
        try {
            Class.forName(driver).newInstance();
            try (Connection conn = DriverManager.getConnection(url + dbName, userName, password)) {
                PreparedStatement st = conn.prepareStatement("UPDATE PC SET `idLocation` = ?, `idPCModel` = ?, `SerialNo` = ?, `Monitor1` = ?, `Monitor2` = ?, `AssetNo` = ?, `Notes` = ?, `Status` = ?, `CommUsers` = ?, `RepDate` = ? WHERE `idPC` = ?");
                st.setString(1, location);
                st.setString(2, idPCModel);
                st.setString(3, serialNo);
                st.setString(4, monitor1);
                st.setString(5, monitor2);
                st.setString(6, assetNumber);
                st.setString(7, notes);
                st.setString(8, status);
                st.setString(9, commonUsers);
                st.setString(10, replacementDate);
                st.setString(11, idPC);
                st.executeUpdate();
                conn.close();
                return true;
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e) {
            e.printStackTrace();
            return false;
        }

    }
    
    public static ArrayList loadPCModels() {
        String url = "jdbc:mysql://websrv:3306/";
        String dbName = "assetDB";
        String driver = "com.mysql.jdbc.Driver";
        String userName = "jessvoig";
        String password = "qzpm9876";
        ArrayList resultList = new ArrayList();

        try {
            Class.forName(driver).newInstance();
            try (Connection conn = DriverManager.getConnection(url + dbName, userName, password)) {
                Statement st = conn.createStatement();
                ResultSet res = st.executeQuery("Select * FROM PCModel order by idPCModel asc");

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

    public static ArrayList loadLocations() {
        String url = "jdbc:mysql://websrv:3306/";
        String dbName = "assetDB";
        String driver = "com.mysql.jdbc.Driver";
        String userName = "jessvoig";
        String password = "qzpm9876";
        ArrayList resultList = new ArrayList();

        try {
            Class.forName(driver).newInstance();
            try (Connection conn = DriverManager.getConnection(url + dbName, userName, password)) {
                Statement st = conn.createStatement();

                ResultSet res = st.executeQuery("Select * FROM Location order by idLocation asc");

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
        String url = "jdbc:mysql://websrv:3306/";
        String dbName = "assetDB";
        String driver = "com.mysql.jdbc.Driver";
        String userName = "jessvoig";
        String password = "qzpm9876";
        ArrayList resultList = new ArrayList();

        try {
            Class.forName(driver).newInstance();
            try (Connection conn = DriverManager.getConnection(url + dbName, userName, password)) {
                Statement st = conn.createStatement();
                ResultSet res = st.executeQuery("Select idPC, Name, max(Date) FROM UserHistory group by Name");

                while (res.next()) {
                    String name = res.getString("Name");

                    int dateCode = res.getInt("max(Date)");
                    Date dateFormatted = new Date((long) dateCode * 1000);          //Date conversion from 10 digit unix number to correct date FML
                    String idPC = dateFormatted.toString();

                    //String idPC = res.getString("max(Date)");//res.getString("idPC");
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
