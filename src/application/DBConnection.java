package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

class DBConnection {

    private Connection con;
    private String dbURL;
    private String dbUsername;
    private String dbPassword;
    private String URL;
    private String port;
    private String dbName;

    DBConnection(String URL, String port, String dbName, String dbUsername, String dbPassword) {
        this.URL = URL;
        this.port = port;
        this.dbName = dbName;
        this.dbUsername = dbUsername;
        this.dbPassword = dbPassword;
    }

    public Connection connectDB() throws ClassNotFoundException, SQLException {

        dbURL = "jdbc:mysql://" + URL + ":" + port + "/" + dbName + "?verifyServerCertificate=false";

        Properties p = new Properties();
        p.setProperty("user", dbUsername);
        p.setProperty("password", dbPassword);
        p.setProperty("useSSL", "false");
        p.setProperty("autoReconnect", "true");

        Class.forName("com.mysql.jdbc.Driver");



        con = DriverManager.getConnection (dbURL, p);


        return con;
    }


}