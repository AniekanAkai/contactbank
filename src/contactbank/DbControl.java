package contactbank;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author anearcan
 */
public class DbControl {
    String dBUrl;
    String dBName; 
    String dBUser;
    String dBpass;
    String driver;
    Statement stmt;
    Connection con = null;

    DbControl(){
        dBUrl = "jdbc:mysql://localhost:3306/";
        dBName = "contact_bank"; 
        dBUser = "root";
        dBpass = "root";
        driver = "com.mysql.jdbc.Driver";
    }
    
    DbControl(String dburl, String dbname, String dbuser, String dbpass){
        dBUrl = dburl;
        dBName = dbname;
        dBUser = dbuser;
        dBpass = dbpass;
    }
    
    void connect(){
            try {
                Class.forName(driver).newInstance();
                con = DriverManager.getConnection(dBUrl+dBName, dBUser, dBpass);
                System.out.println("Connection to database successful");    

            } catch (SQLException ex) {
                System.err.println("Failed Connection to database");
                Logger.getLogger(DbControl.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
                Logger.getLogger(DbControl.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(DbControl.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(DbControl.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    void connectToTable(String tableName)
    {
            try {
                Class.forName(driver).newInstance();
                con = DriverManager.getConnection(dBUrl+dBName+"/"+tableName, dBUser, dBpass);
                System.out.println("Connection successful");    

            } catch (SQLException ex) {
                System.err.println("Failed Connection to database");
                Logger.getLogger(DbControl.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
                Logger.getLogger(DbControl.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(DbControl.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(DbControl.class.getName()).log(Level.SEVERE, null, ex);
            }    
    }
}
