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
   * Adds tuple to Transaction relation with the given attribute values. Searches for applicable discount and applies to price if found.
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
    ResultSet rs = null;
    int id = 0;

    try {
      ps = connection.prepareStatement("SELECT * FROM Discount WHERE productID = ? AND ? BETWEEN startDate AND endDate;");
      ps.setInt(1, productID);
      ps.setDate(2, date);
      rs = ps.executeQuery();
      if (!rs.next()) {
        System.out.println("No discount available");
      } else {
        double priceReduction = rs.getDouble("priceReduction");
        price = (100.0 - priceReduction) / 100.0 * price;
        total = price * quantity;
        System.out.println("Discount of " + priceReduction + "% applied");
      }
      ps.close();

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

      // Update rewards if platinum member
      PreparedStatement memberSearch = connection.prepareStatement("SELECT * FROM Member WHERE memberID = ?;");
      memberSearch.setInt(1, memberID);
      ResultSet member = memberSearch.executeQuery();
      member.next();
      boolean isPlatinum = member.getString("level").toLowerCase().equals("platinum");

      if (isPlatinum) {
        ps = connection.prepareStatement("UPDATE Member SET rewardAmount = rewardAmount + ?;");
        ps.setDouble(1, 0.02 * total);
        id = ps.executeUpdate();
        if (id > 0) {
          System.out.println("Reward amount of $" + 0.02 * total + " earned");
        }
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
   * @param isStore Are we querying for a specific store?
   * @param isMember Are we getting purchase data for a member?
   * @param start Start date of the range in question
   * @param end End date of the range in question
   * @return ResultSet containing sum total of transactions within the date range
   * @throws ClassNotFoundException
   * @throws SQLException
   * @throws ParseException
   */
  public static ResultSet totalSalesReport(boolean isStore, boolean isMember, int id, String start, String end) throws ClassNotFoundException, SQLException, ParseException
  {
    ResultSet returnSet = null;
    PreparedStatement ps = null;

    try {
      if (isStore) {
        ps = connection.prepareStatement("SELECT SUM(total) FROM Transaction WHERE storeID = ? AND date BETWEEN ? AND ?;");
        ps.setInt(1, id);
        ps.setString(2, start);
        ps.setString(3, end);
      } else if (isMember) {
        ps = connection.prepareStatement("SELECT SUM(total) FROM Transaction WHERE memberID = ? AND date BETWEEN ? AND ?;");
        ps.setInt(1, id);
        ps.setString(2, start);
        ps.setString(3, end);
      } else {
        ps = connection.prepareStatement("SELECT SUM(total) FROM Transaction WHERE date BETWEEN ? AND ?;");
        ps.setString(1, start);
        ps.setString(2, end);
      }
      returnSet = ps.executeQuery();
      ps.close();
    } catch (SQLException e) {
      System.out.println("SQL Exception: " + e.getStackTrace());
      return null;
    }
    return returnSet;
  }
  
  /**
   * Queries transaction relation for all tuples matching given ID and calculates total dollar amount spent.
   * @param transactionID ID of transaction to calculate total for
   * @return ResultSet containing sum(total) of transaction
   * @throws ClassNotFoundException
   * @throws SQLException
   * @throws ParseException
   */
  public static ResultSet transactionTotal(int transactionID)  throws ClassNotFoundException, SQLException, ParseException{
    //A table of data representing a database result set, which is usually generated by executing a 
    //statement that queries the database.
    ResultSet returnSet = null;
    //Object that represents a precompiled SQL statement
    PreparedStatement ps = null;

    try {
      ps = connection.prepareStatement("SELECT SUM(total) FROM Transaction WHERE transactionID = ?;");
      ps.setInt(1, transactionID);
      returnSet = ps.executeQuery();
      ps.close();
    } catch (SQLException e) {
        System.out.println("SQL Exception");
        e.getStackTrace();
        return null;
    }
    return returnSet;
  }

  /**
   * Checks inner join of Transaction and Discount for any applicable discounts to the transaction matching the passed-in ID. Transaction date must be between start and end of Discount.
   * @param transactionID ID of transaction we want to check Discounts for
   * @return ResultSet of discounts applicable for target transaction
   * @throws ClassNotFoundException
   * @throws SQLException
   * @throws ParseException
   */
  public static ResultSet checkDiscounts(int transactionID) throws ClassNotFoundException, SQLException, ParseException
  {
    PreparedStatement ps = null;
    ResultSet returnSet = null;

    try {
      ps = connection.prepareStatement("SELECT Transaction.transactionID, Transaction.productID, Discount.priceReduction FROM Transaction INNER JOIN Discount ON Transaction.productID = Discount.productID WHERE Transaction.transactionID = ? AND Transaction.date BETWEEN Discount.startDate AND Discount.endDate;");
      ps.setInt(1, transactionID);
      returnSet = ps.executeQuery();
      ps.close();
    } catch (SQLException e) {
      System.out.println("SQL Exception");
      e.printStackTrace();
      return null;
    }
    return returnSet;
  }
}
