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

  /**
   * Queries Store relation for all tuples and attributes
   * @return ResultSet containing all tuples
   * @throws ClassNotFoundException
   * @throws SQLException
   * @throws ParseException
   */
  public static ResultSet viewAllStores() throws ClassNotFoundException, SQLException, ParseException
  {
    ResultSet returnSet = null;
    PreparedStatement ps = null;

    try {
      ps = connection.prepareStatement("SELECT * FROM Store;");
      returnSet = ps.executeQuery();
      ps.close();
    } catch (SQLException e) {
      System.out.println("SQL Exception: " + e.getStackTrace());
      return null;
    }
    return returnSet;
  }

  /**
   * Queries Staff relation for tuple matching the given staffID
   * @param storeID ID of tuple to query for
   * @return ResultSet containing tuple matching given staffID or null
   * @throws ClassNotFoundException
   * @throws SQLException
   * @throws ParseException
   */
  public static ResultSet viewStaff(int storeID) throws ClassNotFoundException, SQLException, ParseException
  {
    ResultSet returnSet = null;
    PreparedStatement ps = null;

    try {
      ps = connection.prepareStatement("SELECT * FROM Store WHERE storeID=?;");
      ps.setInt(1, storeID);
      returnSet = ps.executeQuery();
      ps.close();
    } catch (SQLException e) {
      System.out.println("SQL Exception: " + e.getStackTrace());
      return null;
    }
    return returnSet;
  }

  /**
   * Adds store with the given attribute values
   * @param storeID ID of store to edit
   * @param managerID value to store
   * @param address value to store
   * @param phone value to store
   * @throws ParseException
   * @throws SQLException
   */
  public static void addStaff(int storeID, int managerID, String address, String phone) throws ParseException, SQLException
  {
    PreparedStatement ps = null;
    int id = 0;
    
    try {
      ps = connection.prepareStatement("INSERT INTO Store VALUES (?,?,?,?);");
      ps.setInt(1, storeID);
      ps.setInt(2, managerID);
      ps.setString(3, address);
      ps.setString(4, phone);

      id = ps.executeUpdate();
      ps.close();
      System.out.println(id);

      if (id > 0) {
        System.out.println("Store added successfully.");
      } else {
        System.out.println("Store not added.");
      }
    } catch (SQLException e) {
      System.out.println("SQL Exception: " + e.getStackTrace());
      connection.rollback();
    }
  }

  /**
   * Changes attribute values to given values of staff member that matches given staffID
   * @param storeID ID of store to edit
   * @param managerID value to store
   * @param address value to store
   * @param phone value to store
   * @throws ParseException
   * @throws SQLException
   */
  public static void editStaff(int storeID, int managerID, String address, String phone) throws ParseException, SQLException
  {
    PreparedStatement ps = null;
    int id = 0;
    
    try {
      ps = connection.prepareStatement("UPDATE STORE SET managerID=?, address=?,phone=? WHERE storeID=?;");
      ps.setInt(1, managerID);
      ps.setString(2, address);
      ps.setString(3, phone);
      ps.setInt(4, storeID);

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