import java.sql.*;
import java.sql.Date;
import java.text.ParseException;
import java.sql.SQLException;

public class TransactionSQL {

  /** This connects to the database by calling the login file */
  static Connection connection = Login.connection;
  static Statement statement = Login.statement;
  static ResultSet result = Login.result;

  /**
   * Queries Transaction relation for all tuples and attributes
   * @return ResultSet containing all tuples
   * @throws ClassNotFoundException
   * @throws SQLException
   * @throws ParseException
   */
  public static ResultSet viewAllTransactions() throws ClassNotFoundException, SQLException, ParseException
  {
    ResultSet returnSet = null;
    PreparedStatement ps = null;

    try {
      ps = connection.prepareStatement("SELECT * FROM Transaction;");
      returnSet = ps.executeQuery();
      ps.close();
    } catch (SQLException e) {
      System.out.println("SQL Exception: " + e.getStackTrace());
      return null;
    }
    return returnSet;
  }

  /**
   * Queries Transaction relation for tuple matching the given transactionID
   * @param transactionID ID of tuple to query for
   * @return ResultSet containing tuple matching given transactionID or null
   * @throws ClassNotFoundException
   * @throws SQLException
   * @throws ParseException
   */
  public static ResultSet viewTransaction(int transactionID) throws ClassNotFoundException, SQLException, ParseException
  {
    ResultSet returnSet = null;
    PreparedStatement ps = null;

    try {
      ps = connection.prepareStatement("SELECT * FROM Transaction WHERE transactionID=?");
      ps.setInt(1, transactionID);
      returnSet = ps.executeQuery();
      ps.close();
    } catch (SQLException e) {
      System.out.println("SQL Exception: " + e.getStackTrace());
      return null;
    }
    return returnSet;
  }
  
  /**
   * Adds tuple to Transaction relation with the given attribute values
   * @param transactionID value to store
   * @param storeID value to store
   * @param memberID value to store
   * @param cashierID value to store
   * @param date value to store
   * @param productID value to store
   * @param price value to store
   * @param quantity value to store
   * @param total value to store
   * @throws ParseException
   * @throws SQLException
   */
  public static void addTransaction(int transactionID, int storeID, int memberID, int cashierID, Date date, int productID, double price, int quantity, double total) throws ParseException, SQLException
  {
    PreparedStatement ps = null;
    int id = 0;

    try {
      ps = connection.prepareStatement("INSERT INTO Transaction VALUES (?,?,?,?,?,?,?,?,?);");
      ps.setInt(1, transactionID);
      ps.setInt(2, storeID);
      ps.setInt(3, memberID);
      ps.setInt(4, cashierID);
      ps.setDate(5, date);
      ps.setInt(6, productID);
      ps.setDouble(7, price);
      ps.setInt(8, quantity);
      ps.setDouble(9, total);

      id = ps.executeUpdate();
      ps.close();
      System.out.println(id);

      if (id > 0) {
        System.out.println("Transaction added successfully.");
      } else {
        System.out.println("Transaction not added.");
      }
    } catch (SQLException e) {
      System.out.println("SQL Exception: " + e.getStackTrace());
      connection.rollback();
    }
  }

  /**
   * Changes attribute values to given values of transaction tuple that matches transactionID
   * @param transactionID ID of transaction to edit
   * @param storeID value to save
   * @param memberID value to save
   * @param cashierID value to save
   * @param date value to save
   * @param productID value to save
   * @param price value to save
   * @param quantity value to save
   * @param total value to save
   * @throws ParseException
   * @throws SQLException
   */
  public static void editTransaction(int transactionID, int storeID, int memberID, int cashierID, Date date, int productID, double price, int quantity, double total) throws ParseException, SQLException
  {
    PreparedStatement ps = null;
    int id = 0;

    try {
      ps = connection.prepareStatement("UPDATE Transaction SET storeID=?, memberID=?, cashierID=?, date=?, productID=?, price=?, quantity=?, total=? WHERE transactionID=?;");
      ps.setInt(1, storeID);
      ps.setInt(2, memberID);
      ps.setInt(3, cashierID);
      ps.setDate(4, date);
      ps.setInt(5, productID);
      ps.setDouble(6, price);
      ps.setInt(7, quantity);
      ps.setDouble(8, total);
      ps.setInt(9, transactionID);

      id = ps.executeUpdate();
      ps.close();
      System.out.println(id);

      if (id > 0) {
        System.out.println("Transaction added successfully.");
      } else {
        System.out.println("Transaction not added.");
      }
    } catch (SQLException e) {
      System.out.println("SQL Exception: " + e.getStackTrace());
      connection.rollback();
    }
  }

  /**
   * Deletes transaction from database that matches given transactionID
   * @param transactionID ID of transaction to delete
   * @throws SQLException
   */
  public static void deleteTransaction(int transactionID) throws SQLException
  {
    try {
      PreparedStatement ps = connection.prepareStatement("DELETE FROM Transaction WHERE transactionID=?;");
      ps.setInt(1, transactionID);
      int id = ps.executeUpdate();

      System.out.println(id);

      if (id > 0) {
        System.out.println("Transaction deleted.");
      } else {
        System.out.println("Transaction not deleted.");
      }
    } catch (SQLException e) {
      System.out.println("SQL Exception: " + e.getStackTrace());
    }
  }

  /**
   * Queries the Transaction relation for total sales revenue between two dates
   * @param start Start date of the range in question
   * @param end End date of the range in question
   * @return ResultSet containing sum total of transactions within the date range
   * @throws ClassNotFoundException
   * @throws SQLException
   * @throws ParseException
   */
  public static ResultSet totalSalesReport(Date start, Date end) throws ClassNotFoundException, SQLException, ParseException
  {
    ResultSet returnSet = null;
    PreparedStatement ps = null;

    try {
      ps = connection.prepareStatement("SELECT SUM(total) FROM Transaction WHERE date > ? AND date < ?;");
      ps.setDate(1, start);
      ps.setDate(2, end);
      returnSet = ps.executeQuery();
      ps.close();
    } catch (SQLException e) {
      System.out.println("SQL Exception: " + e.getStackTrace());
      return null;
    }
    return returnSet;
  }
}
