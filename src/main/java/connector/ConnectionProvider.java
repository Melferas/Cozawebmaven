package connector;

import java.sql.*;
import static connector.Provider.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionProvider {

    private static Connection con = null;

    static {
        try {
            Class.forName(DRIVER).newInstance();
            con = DriverManager.getConnection(CONNECTION_URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            System.out.println(ex.getMessage());
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }

    public static Connection getCon() {
        return con;
    }

}
