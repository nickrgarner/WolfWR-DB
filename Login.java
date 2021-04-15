// This example is created by Seokyong Hong
// modified by Shrikanth N C to support MySQL(MariaDB)

// Relpace all $USER$ with your unity id and $PASSWORD$ with your 9 digit student id or updated password (if changed)

import java.sql.*;

public class Login {


// Update your user info alone here
private static final String jdbcURL = "jdbc:mariadb://classdb2.csc.ncsu.edu:3306/$USER$"; // Using SERVICE_NAME

// Update your user and password info here!

private static final String user = "$USER$";
private static final String password = "$PASSWORD$";

public static void main(String[] args) {
  try {
    // Loading the driver. This creates an instance of the driver
    // and calls the registerDriver method to make MySql(MariaDB) Thin available to clients.
    Class.forName("org.mariadb.jdbc.Driver");

    Connection connection = null;
    Statement statement = null;
    ResultSet result = null;

    try {
    // Get a connection instance from the first driver in the
    // DriverManager list that recognizes the URL jdbcURL
    connection = DriverManager.getConnection(jdbcURL, user, password);

    MainMenu.initiate();

    } finally {
      close(result);
      close(statement);
      close(connection);
    }
  } catch(Throwable oops) {
      oops.printStackTrace();
  }
}

static void close(Connection connection) {
        if(connection != null) {
            try {
            connection.close();
            } catch(Throwable whatever) {}
        }
    }
    static void close(Statement statement) {
        if(statement != null) {
            try {
            statement.close();
            } catch(Throwable whatever) {}
        }
    }
    static void close(ResultSet result) {
        if(result != null) {
            try {
            result.close();
            } catch(Throwable whatever) {}
        }
    }
}
