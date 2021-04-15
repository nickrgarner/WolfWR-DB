import java.sql.*;
import java.util.*;
import java.io.*;
import java.sql.Date;
import java.text.ParseException;
import java.sql.SQLException;

public class DiscountSQL {
  private static final String JDBCURL = "";
  private static final String USER = "";
  private static final String PASSWORD = "";

  /** This connects to the database by calling the login file */
  static Connection connection = login.connection;
  static Statement statement = login.statement;
  static ResultSet result = login.result;

  /**
   * Queries Discount relation for all tuples and attributes
   * @return ResultSet containing all tuples
   * @throws ClassNotFoundException
   * @throws SQLException
   * @throws ParseException
   */
  public static ResultSet viewAllDiscounts() throws ClassNotFoundException, SQLException, ParseException
  {
    ResultSet returnSet = null;
    PreparedStatement ps = null;

    try {
      ps = connection.prepareStatement("SELECT * FROM Discount;");
      returnSet = ps.executeQuery();
      ps.close();
    } catch (SQLException e) {
      System.out.println("SQL Exception: " + e.getStackTrace());
      return null;
    }
    return returnSet;
  }

  /**
   * Queries Discount relation for tuple matching the given discountID
   * @param discountID ID of tuple to query for
   * @return ResultSet containing tuple matching given discountnID or null
   * @throws ClassNotFoundException
   * @throws SQLException
   * @throws ParseException
   */
  public static ResultSet viewDiscount(int discountID) throws ClassNotFoundException, SQLException, ParseException
  {
    ResultSet returnSet = null;
    PreparedStatement ps = null;

    try {
      ps = connection.prepareStatement("SELECT * FROM Discount WHERE discountID=?");
      ps.setInt(1, discountID);
      returnSet = ps.executeQuery();
      ps.close();
    } catch (SQLException e) {
      System.out.println("SQL Exception: " + e.getStackTrace());
      return null;
    }
    return returnSet;
  }
  
  /**
   * Adds tuple to Discount relation with the given attribute values
   * @param discountID value to store
   * @param productID value to store
   * @param priceReduction value to store
   * @param startDate value to store
   * @param endDate value to store
   * @throws ParseException
   * @throws SQLException
   */
  public static void addDicount(int discountID, int productID, double priceReduction, Date startDate, Date endDate) throws ParseException, SQLException
  {
    PreparedStatement ps = null;
    int id = 0;

    try {
      ps = connection.prepareStatement("INSERT INTO Discount VALUES (?,?,?,?,?);");
      ps.setInt(1, discountID);
      ps.setInt(2, productID);
      ps.setInt(3, priceReducation);
      ps.setInt(4, startDate);
      ps.setDate(5, endDate);
      id = ps.executeUpdate();
      ps.close();
      System.out.println(id);

      if (id > 0) {
        System.out.println("Discount added successfully.");
      } else {
        System.out.println("Discount not added.");
      }
    } catch (SQLException e) {
      System.out.println("SQL Exception: " + e.getStackTrace());
      connection.rollback();
    }
  }

  /**
   * Changes attribute values to given values of discount tuple that matches discountID
   * @param discountID ID of discount to edit
   * @param productID value to store
   * @param priceReduction value to store
   * @param startDate value to store
   * @param endDate value to store
   * @throws ParseException
   * @throws SQLException
   */
  public static void editDiscount(int discountID, int productID, double priceReducation, Date startDate, Date endDate) throws ParseException, SQLException
  {
    PreparedStatement ps = null;
    int id = 0;

    try {
      ps = connection.prepareStatement("UPDATE Discount SET productID=?, priceReducation=?, startDate=?, endDate=? WHERE discountID=?;");
      ps.setInt(1, productID);
      ps.setDouble(2, priceReduction);
      ps.setDate(3, startDate);
      ps.setDate(4, endDate);
      ps.setInt(5, discountID);

      id = ps.executeUpdate();
      ps.close();
      System.out.println(id);

      if (id > 0) {
        System.out.println("Discount added successfully.");
      } else {
        System.out.println("Discount not added.");
      }
    } catch (SQLException e) {
      System.out.println("SQL Exception: " + e.getStackTrace());
      connection.rollback();
    }
  }

  /**
   * Deletes discount from database that matches given discountID
   * @param discountID ID of discount to delete
   * @throws SQLException
   */
  public static void deleteDiscount(int discountID) throws SQLException
  {
    try {
      PreparedStatement ps = connection.prepareStatement("DELETE FROM Discount WHERE discountID=?;");
      ps.setInt(1, discountID);
      int id = ps.executeUpdate();

      System.out.println(id);

      if (id > 0) {
        System.out.println("Discount deleted.");
      } else {
        System.out.println("Discount not deleted.");
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