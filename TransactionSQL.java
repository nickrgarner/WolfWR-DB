import java.sql.*;
import java.util.*;
import java.io.*;
import java.sql.Date;
import java.text.ParseException;
import java.sql.SQLException;

public class TransactionSQL {
  private static final String JDBCURL = "";
  private static final String USER = "";
  private static final String PASSWORD = "";

  /** This connects to the database by calling the login file */
  static Connection connection = login.connection;
  static Statement statement = login.statement;
  static ResultSet result = login.result;

  public static ResultSet viewAllTransactions() throws ClassNotFoundException, SQLException, ParseException
  {

  }

  public static ResultSet viewTransaction(int transactionID) throws ClassNotFoundException, SQLException, ParseException
  {

  }

  public static void addTransaction(int transactionID, int storeID, int memberID, int cashierID, Date date, int productID, double price, int quantity, double total) throws ParseException, SQLException
  {

  }

  public static void editTransaction(int transactionID, int storeID, int memberID, int cashierID, Date date, int productID, double price, int quantity, double total) throws ParseException, SQLException
  {
    
  }

  public static void deleteTransaction(int transactionID) throws SQLException
  {

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
