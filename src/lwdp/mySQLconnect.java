/*
 * Programmer: Real Bert Magallanes
 * Email Address: realbertmagallanes@gmail.com
 */
package lwdp;

/**
 *
 * @author Real Bert
 */
import java.sql.*;
import javax.swing.*;
import java.util.*;
public class mySQLconnect  {

  
    // init database constants
    private static final String DATABASE_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DATABASE_URL = "jdbc:mysql://127.0.0.1:3306/lwdatabase?useSSL=false";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";//waterdistrict1993
    private static final String MAX_POOL = "250";

    // init connection object
    private Connection connection;
    // init properties object
    private Properties properties;

    //default constructor
    //public mySQLconnect(){};
    
    // create properties
    private Properties getProperties() {
        if (properties == null) {
            properties = new Properties();
            properties.setProperty("user", USERNAME);
            properties.setProperty("password", PASSWORD);
            properties.setProperty("MaxPooledStatements", MAX_POOL);
        }
        return properties;
    }

    // connect database
    public Connection connect() {
        if (connection == null) {
            try {
                Class.forName(DATABASE_DRIVER);
                connection = DriverManager.getConnection(DATABASE_URL, getProperties());
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    // disconnect database
    public void disconnect() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
}
