import java.sql.*;

public class Login {
    // Update your user info alone here
    private static final String jdbcURL = "jdbc:mariadb://classdb2.csc.ncsu.edu:3306/%USER%";

    // Update your user and password info here!

    private static final String user = "%USER%";
    private static final String password = "%PASSWORD%";

    public static Connection connection = null;
    public static Statement statement = null;
    public static ResultSet result = null;

    public static void main(String[] args) {
        try {
            // Loading the driver. This creates an instance of the driver
            // and calls the registerDriver method to make MySql(MariaDB) Thin available to clients.
            Class.forName("org.mariadb.jdbc.Driver");
            try {
                // Get a connection instance from the first driver in the
                // DriverManager list that recognizes the URL jdbcURL
                connection = DriverManager.getConnection(jdbcURL, user, password);
                statement = connection.createStatement();

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
