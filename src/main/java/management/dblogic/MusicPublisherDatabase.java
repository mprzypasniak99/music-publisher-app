package management.dblogic;

import management.app.MusicPublisherApp;

import java.sql.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MusicPublisherDatabase {
    private Connection conn = null; // connection to the database

    private String connectionString;    // address used to connect to the database, could be read from file
                                        // in the future
    private Properties connectionProps; // container for username and password

    public MusicPublisherDatabase() {
        connectionString = // could be changed to reading from config file file
                "jdbc:oracle:thin:@//admlab2.cs.put.poznan.pl:1521/"+
                        "dblab02_students.cs.put.poznan.pl";
        connectionProps = new Properties();
    }

    public void addLoginData(String user, String pass) { // add user data used to connect to database
        connectionProps.put("user", user);
        connectionProps.put("password", pass);
    }

    public boolean connect() { // try to connect to the database with given username and password
        try {
            conn = DriverManager.getConnection(connectionString,
                    connectionProps);
            conn.setAutoCommit(false);
            return authorisation(); // if succeeded in logging into the database, check if user is allowed to
            // access data related to this application
        } catch (SQLException ex) {
            Logger.getLogger(MusicPublisherApp.class.getName()).log(Level.SEVERE,
                    "Nie udało się połączyć z bazą danych", ex);
            return false; // if failed to connect, return false to inform about it
        }
    }

    private boolean authorisation() {
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM SESSION_ROLES")) {
            boolean check = false;
            while(rs.next()) {
                if(rs.getString("ROLE").equals("USER_ROLE")) { // look for specified role name
                    check = true;
                }
                // if user has been granted this role, he is authorized to access the program
            }
            if(!check) {
                disconnect(); // disconnect from the database if user is not authorized
            }
            return check;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public boolean disconnect() { // close connection with the database
        try {
            conn.close();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }
}
