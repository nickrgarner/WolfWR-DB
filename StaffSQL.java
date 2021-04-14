import java.sql.*;
import java.util.*;
import java.io.*;
import java.sql.Date;
import java.text.ParseException;
import java.sql.SQLException;

public class StaffSQL {
  private static final String JDBCURL = "";
  private static final String USER = "";
  private static final String PASSWORD = "";

  /** This connects to the database by calling the login file */
  static Connection connection = login.connection;
  static Statement statement = login.statement;
  static ResultSet result = login.result;

  public static ResultSet viewAllStaff() throws ClassNotFoundException, SQLException, ParseException
  {
    ResultSet returnSet = null;
    PreparedStatement ps = null;

    try {
      ps = connection.prepareStatement("SELECT * FROM Staff;");
      returnSet = ps.executeQuery();
      ps.close();
    } catch (SQLException e) {
      System.out.println("SQL Exception: " + e.getStackTrace());
      return null;
    }
    return returnSet;
  }

  public static ResultSet viewStaff(int staffID) throws ClassNotFoundException, SQLException, ParseException
  {
    ResultSet returnSet = null;
    PreparedStatement ps = null;

    try {
      ps = connection.prepareStatement("SELECT * FROM Staff WHERE staffID=?;");
      ps.setInt(1, staffID);
      returnSet = ps.executeQuery();
      ps.close();
    } catch (SQLException e) {
      System.out.println("SQL Exception: " + e.getStackTrace());
      return null;
    }
    return returnSet;
  }

  /**
   * Adds staff member with the given attribute values
   * @param staffID value to store
   * @param storeID value to store
   * @param name value to store
   * @param age value to store
   * @param address value to store
   * @param title value to store
   * @param phone value to store
   * @param email value to store
   * @param employmentTime value to store
   * @throws ParseException
   * @throws SQLException
   */
  public static void addStaff(int staffID, int storeID, String name, int age, String address, String title, String phone, String email, Date employmentTime) throws ParseException, SQLException
  {
    PreparedStatement ps = null;
    int id = 0;
    
    try {
      ps = connection.prepareStatement("INSERT INTO Staff VALUES (?,?,?,?,?,?,?,?,?);");
      ps.setInt(1, staffID);
      ps.setInt(2, storeID);
      ps.setString(3, name);
      ps.setInt(4, age);
      ps.setString(5, address);
      ps.setString(6, title);
      ps.setString(7, phone);
      ps.setString(8, email);
      ps.setDate(9, employmentTime);

      id = ps.executeUpdate();
      ps.close();
      System.out.println(id);

      if (id > 0) {
        System.out.println("Staff member added successfully.");
      } else {
        System.out.println("Staff member not added.");
      }
    } catch (SQLException e) {
      System.out.println("SQL Exception: " + e.getStackTrace());
      connection.rollback();
    }
  }

  /**
   * Changes attribute values to given values of staff member that matches given staffID
   * @param staffID ID of staff member to edit
   * @param storeID value to save
   * @param name value to save
   * @param age value to save
   * @param address value to save
   * @param title value to save
   * @param phone value to save
   * @param email value to save
   * @param employmentTime value to save
   * @throws ParseException
   * @throws SQLException
   */
  public static void editStaff(int staffID, int storeID, String name, int age, String address, String title, String phone, String email, Date employmentTime) throws ParseException, SQLException
  {
    PreparedStatement ps = null;
    int id = 0;
    
    try {
      ps = connection.prepareStatement("UPDATE STAFF SET storeID=?, name=?, age=?, address=?, title=?, phone=?, email=?, employmentTime=? WHERE staffID=?;");
      ps.setInt(1, storeID);
      ps.setString(2, name);
      ps.setInt(3, age);
      ps.setString(4, address);
      ps.setString(5, title);
      ps.setString(6, phone);
      ps.setString(7, email);
      ps.setDate(8, employmentTime);
      ps.setInt(9, staffID);

      id = ps.executeUpdate();
      ps.close();
      System.out.println(id);

      if (id > 0) {
        System.out.println("Staff member added successfully.");
      } else {
        System.out.println("Staff member not added.");
      }
    } catch (SQLException e) {
      System.out.println("SQL Exception: " + e.getStackTrace());
      connection.rollback();
    }
  }

  /**
   * Deletes staff from database that matches supplied staffID
   * @param staffID ID of staff member to delete
   * @throws SQLException
   */
  public static void deleteStaff(int staffID) throws SQLException
  {
    try {
      PreparedStatement ps = connection.prepareStatement("DELETE FROM Staff WHERE staffID = ?;");
      ps.setInt(1, staffID);
      int id = ps.executeUpdate();

      System.out.println(id);

      if (id > 0) {
        System.out.println("Staff member deleted.");
      } else {
        System.out.println("Staff member not deleted.");
      }
    } catch (SQLException e) {
      System.out.println("SQL Exception: " + e.getStackTrace());
    }
  }

  // Close methods
  /**
   * Closes connection object
   * @param connection
   */
  public static void close(Connection connection)
  {
    if (connection != null) {
      try {
        connection.close();
      } catch (Throwable whatever) {

      }
    }
  }

  /**
   * Closes statement object
   * @param statement
   */
  public static void close(Statement statement)
  {
    if (statement != null) {
      try {
        statement.close();
      } catch (Throwable whatever) {

      }
    }
  }

  /**
   * Closes result objects
   * @param result
   */
  public static void close(ResultSet result)
  {
    if (result != null) {
      try {
        result.close();
      } catch (Throwable whatever) {

      }
    }
  }
}
