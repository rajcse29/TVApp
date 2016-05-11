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
	
	 public void createCountryTable() {

	        try {	
	            if (conn != null) {
	                if (stmt == null) {
	                    stmt = conn.createStatement();
	                }
	                try {
	                    String sqlCreate = "CREATE TABLE tblCountry"
	                            + "("
	                            + "ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
	                            + "COUNTRYID VARCHAR(50) NOT NULL,"
	                            + "COUNTRYNAME VARCHAR(50) NOT NULL,"
	                            + "PRIMARY KEY (ID),"
	                            + "CONSTRAINT unique_country UNIQUE (COUNTRYNAME)"
	                            + ")";

	                    stmt.execute(sqlCreate);

	                } catch (Exception e) {
	                	e.printStackTrace();
	                } finally {
	                    if (stmt == null) {
	                        try {
	                            stmt.close();
	                        } catch (SQLException ex) {
	                        }
	                    }
	                }
	            }
	        } catch (SQLException ex) {
	        	ex.printStackTrace();
	        }
	    }
	 
	 public void createCategoryTable() {

	        try {
	            if (conn != null) {
	                if (stmt == null) {
	                    stmt = conn.createStatement();
	                }
	                try {
	                    String sqlCreate = "CREATE TABLE tblCategory"
	                            + "("
	                            + " ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
	                            + " CATEGORYID VARCHAR(50) NOT NULL,"
	                            + " CATEGORYNAME VARCHAR(50) NOT NULL,"
	                            + " PRIMARY KEY (ID),"
	                            + " CONSTRAINT unique_category UNIQUE (CATEGORYNAME)"
	                            + ")";
	                    stmt.execute(sqlCreate);
	                } catch (Exception e) {
	                	e.printStackTrace();
	                } finally {
	                    if (stmt == null) {
	                        try {
	                            stmt.close();
	                        } catch (SQLException ex) {
	                        }
	                    }
	                }
	            }
	        } catch (SQLException ex) {
	        	ex.printStackTrace();
	        }
	    }
	

	 public void createLanguageTable() {

	        try {
	            if (conn != null) {
	                if (stmt == null) {
	                    stmt = conn.createStatement();
	                }
	                try {
	                    String sqlCreate = "CREATE TABLE tblLanguage"
	                            + "("
	                            + " ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
	                            + " LANGUAGEID VARCHAR(50) NOT NULL,"
	                            + " LANGUAGENAME VARCHAR(50) NOT NULL,"
	                            + " PRIMARY KEY (ID),"
	                            + " CONSTRAINT unique_language UNIQUE (LANGUAGENAME)"
	                            + ")";
	                    stmt.execute(sqlCreate);
	                } catch (Exception e) {
	                	e.printStackTrace();
	                } finally {
	                    if (stmt == null) {
	                        try {
	                            stmt.close();
	                        } catch (SQLException ex) {
	                        }
	                    }
	                }
	            }
	        } catch (SQLException ex) {
	        	ex.printStackTrace();
	        }
	    }
}
