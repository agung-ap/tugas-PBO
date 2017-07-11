/**
 * databse class
 */
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Agungaprian
 * on 16/04/2017.
 */

public class DBConnection {
    private Connection connection;
    private String url = "jdbc:mysql://localhost/studentrecordsystem";
    private String user = "root";
    private String pass = "";

    public Connection getConnection() throws SQLException {
        connection = DriverManager.getConnection(url,user,pass);
        return connection;
    }

}
