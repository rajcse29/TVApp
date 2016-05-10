package com.iptv.DBActivity;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBActivityDAO {
	
	public static String DB_NAME = "TVApp";
    public static Connection conn = null;
    public static DatabaseMetaData dbm;
    public static DBActivityDAO instance;
    public static Statement stmt = null;
    
	public static DBActivityDAO getinstance() {
        if (instance == null) {
            instance = new DBActivityDAO();
        }
        return instance;
    }
	
	public void createDatabaseConnection() {
        try {
            String driver = "org.apache.derby.jdbc.EmbeddedDriver";
            String url = "jdbc:derby:/home/raj/" + DB_NAME + ";create=true";
            Class.forName(driver);
            if (conn == null) {
                try {
                    conn = DriverManager.getConnection(url);
                    dbm = conn.getMetaData();

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }

        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }
	

}
