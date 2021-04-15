import java.sql.*;
import java.text.ParseException;
import java.sql.SQLException;

public class StoreSQL {

  /** This connects to the database by calling the login file */
  static Connection connection = Login.connection;
  static Statement statement = Login.statement;
  static ResultSet result = Login.result;

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
   * Queries Store relation for tuple matching the given storeID
   * @param storeID ID of tuple to query for
   * @return ResultSet containing tuple matching given storeID or null
   * @throws ClassNotFoundException
   * @throws SQLException
   * @throws ParseException
   */
  public static ResultSet viewStore(int storeID) throws ClassNotFoundException, SQLException, ParseException
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
  public static void addStore(int storeID, int managerID, String address, String phone) throws ParseException, SQLException
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
   * Changes attribute values to given values of store that matches given storeID
   * @param storeID ID of store to edit
   * @param managerID value to store
   * @param address value to store
   * @param phone value to store
   * @throws ParseException
   * @throws SQLException
   */
  public static void editStore(int storeID, int managerID, String address, String phone) throws ParseException, SQLException
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
   * Deletes store from database that matches supplied storeID
   * @param storeID ID of store member to delete
   * @throws SQLException
   */
  public static void deleteStore(int storeID) throws SQLException
  {
    try {
      PreparedStatement ps = connection.prepareStatement("DELETE FROM Store WHERE storeID = ?;");
      ps.setInt(1, storeID);
      int id = ps.executeUpdate();

      System.out.println(id);

      if (id > 0) {
        System.out.println("Store deleted.");
      } else {
        System.out.println("Store not deleted.");
      }
    } catch (SQLException e) {
      System.out.println("SQL Exception: " + e.getStackTrace());
    }
  }
}
