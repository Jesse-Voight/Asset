/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package databaseaccess;

import java.sql.*;

/**
 *
 * @author Jesse <24051java1@gmail.com>
 */
public class DatabaseAccess {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        executeSQL("hi");
        
    }
    public static String executeSQL(String result){
        String url = "jdbc:mysql://localhost:3306/";
        String dbName = "people";
        String driver = "com.mysql.jdbc.Driver";
        String userName = "root";
        String password = "root";
        try {
            Class.forName(driver).newInstance();
            try (Connection conn = DriverManager.getConnection(url + dbName, userName, password)) {
                Statement st = conn.createStatement();
                ResultSet res = st.executeQuery("Select * FROM peoples");
                while (res.next()){
                    int id = res.getInt("people_id");
                    String name = res.getString("people_name");
                    System.out.println(id + "\t" + name);
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    // TODO code application logic here
}
