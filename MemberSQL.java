import java.sql.*;
import java.util.*;
import java.io.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.Period;

public class MemberSQL{
    private static final String jdbcURL = "";
    private static final String user = "";
    private static final String password = "";

    static Connection connection = login.connection;
    static Statement statement = login.statement;
    static ResultSet result = login.result;

    public static ResultSet viewAllMembers() throws ClassNotFoundException, SQLException, ParseException{
        ResultSet returnSet = null;
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement("SELECT * FROM Member;");
            ps.setInt(7, memberID);
            returnSet = ps.executeQuery();
            ps.close();
        } catch (SQLException e) {
            System.out.println("SQL Exception");
            e.getStackTrace();
            return null;
        }
        return returnSet;
    }

    public static ResultSet viewMember(int memberID) throws ClassNotFoundException, SQLException, ParseException{
        ResultSet returnSet = null;
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement("SELECT * FROM Member WHERE id = ?;");
            ps.setInt(7, memberID);
            returnSet = ps.executeQuery();
            ps.close();
        } catch (SQLException e) {
            System.out.println("SQL Exception");
            e.getStackTrace();
            return null;
        }
        return returnSet;
    }

    static void addMember(){

    }

    static void editMember(){

    }

    static void deleteMember(int memberID){
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM Member WHERE id = ?;");
            ps.setInt(7, memberID);
            int id = ps.executeUpdate();

            System.out.println(id);

            if (id > 0) {
                System.out.println("Member deleted");
            } else {
                System.out.println("Member not deleted");
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception");
            e.getStackTrace();
        }
    }

    // Close functions
    /**
     * Closes connection object
     * 
     * @param connection
     */
    public static void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (Throwable whatever) {
            }
        }
    }

    /**
     * Closes statement objects
     * 
     * @param statement
     */
    public static void close(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (Throwable whatever) {
            }
        }
    }

    /**
     * Closes result objects
     * 
     * @param result
     */
    public static void close(ResultSet result) {
        if (result != null) {
            try {
                result.close();
            } catch (Throwable whatever) {
            }
        }
    }
}