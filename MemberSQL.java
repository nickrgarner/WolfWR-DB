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
            ps.setInt(1, memberID);
            returnSet = ps.executeQuery();
            ps.close();
        } catch (SQLException e) {
            System.out.println("SQL Exception");
            e.getStackTrace();
            return null;
        }
        return returnSet;
    }

    static void addMember(int memberID, String firstName, String lastName, String level, String email, String phone, String address, boolean activeStatus, double rewardAmount) throws ParseException{
        PreparedStatement ps = null;
        int id = 0;
        try{
            ps = connection.prepareStatement("INSERT INTO Member (memberID, firstname, lastname, level, email, phone, address, activeStatus, rewardAmount) VALUES (?,?,?,?,?,?,?,?,?);");
            ps.setInt(1,memberID);
            ps.setString(2,firstName);
            ps.setString(3,lastName);
            ps.setString(4,level);
            ps.setString(5, email);
            ps.setString(6,phone);
            ps.setString(7,address);
            ps.setBoolean(8, activeStatus);
            ps.setDouble(9, rewardAmount);
            
            id = ps.executeUpdate();
            connection.commit();
            ps.close();
            System.out.println(id);
            
            if(id > 0){
                System.out.println("Member added successfully");
            } else{
                System.out.println("Member not added");
            }
        }
        catch (SQLException e) {
            System.out.println("SQL Exception");
            connection.rollback();
            e.getStackTrace();
        }
    }

    static void editMember( int memberID,String firstName, String lastName, String level, String email, String phone, String address,  boolean activeStatus, double rewardAmount) throws ParseException{
        PreparedStatement ps = null;
        int id = 0;
        try{
            ps = connection.prepareStatement("UPDATE Member SET firstname = ?, lastname = ?, level = ?, email = ?, phone = ?, address = ?, rewardAmount = ?, activeStatus = ? WHERE memberID = ?;");
            
            ps.setString(1,firstName);
            ps.setString(2,lastName);
            ps.setString(3,level);
            ps.setString(4, email);
            ps.setString(5,phone);
            ps.setString(6,address);
            ps.setBoolean(7, activeStatus);
            ps.setDouble(8, rewardAmount);
            ps.setInt(9,memberID);

            id = ps.executeUpdate();
            connection.commit();
            ps.close();
            System.out.println(id);

            
            if(id > 0){
                System.out.println("Member updated successfully");
            } else{
                System.out.println("Member not updated");
            }
        }
        catch (SQLException e) {
            System.out.println("SQL Exception");
            connection.rollback();
            e.getStackTrace();
        }
    }

    static void deleteMember(int memberID){
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM Member WHERE id = ?;");
            ps.setInt(1, memberID);
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