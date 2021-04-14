import java.sql.*;
import java.util.*;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;

public class Store {
  /** Reads user input from stdin */
  static Scanner scan = new Scanner(System.in);
  /** Current menu choice */
  static int input = 0;
  /** Return value of SQL queries */
  static ResultSet rs = null;

  /** Staff table schema */
  static int storeID = -1;
  static int managerID = -1;
  static String address = "";
  static String phone = "";

  /**
   * Displays main menu for Staff table and delegates user input to Staff action methods.
   * @throws ClassNotFoundException
   * @throws SQLException
   * @throws ParseException
   */
  public static void storeMenu() throws ClassNotFoundException, SQLException, ParseException 
  {
    do {
      System.out.println("Staff Menu");
      System.out.println();
      System.out.println("0. Return to Main Menu");
      System.out.println("1. View All Stores");
      System.out.println("2. View Store by ID");
      System.out.println("3. Add Store");
      System.out.println("4. Edit Store");
      System.out.println("5. Delete Store");
      System.out.println();
      System.out.println("Please choose an option from the menu: ");

      input = scan.nextInt();

      switch(input) {
        case 0:
          System.out.println("Going back to Main Menu");
          scan.close();
          break;
        case 1:
          viewAllStores();
          break;
        case 2:
          viewStores();
          break;
        case 3:
          addOrEdit("add");
          break;
        case 4:
          edit();
          break;
        case 5:
          delete();
          break;
        default:
          System.out.println("Invalid input");
          break;
      }
    } while(input != 0);
  }

  /**
   * Displays menu and calls SQL for viewing all Store tuples
   * @throws ClassNotFoundException
   * @throws SQLException
   * @throws ParseException
   */
  public static void viewAllStores() throws ClassNotFoundException, SQLException, ParseException 
  {
    try {
      ResultSet rs = StoreSQL.viewAllStores();
      printStore(rs);
    } catch(SQLException e) {
      System.out.println("SQL Exception: " + e.getMessage());
    }
  }

  /**
   * Displays menu and calls SQL for viewing a single Store tuple
   * @throws ClassNotFoundException
   * @throws SQLException
   * @throws ParseException
   */
  public static void viewStore() throws ClassNotFoundException, SQLException, ParseException {
    do {
      System.out.println("In order to go back to Store Menu, enter 0");
      System.out.println("Please enter a Store ID to view a store");
      System.out.println();
      System.out.println("Store ID: ");

      input = scan.nextInt();
      scan.nextLine();

      if (input > 0) {
        ResultSet rs = StoreSQL.viewStore(input);
        printStore(rs);
        rs.close();
      } else if (input < 0) {
        System.out.println("Invalid input");
      } else if (input == 0) {
        System.out.println("Going back to Store Menu");
        break;
      }
    } while (input != 0);
  }

  /**
   * Displays menu and calls SQL for editing a Store tuple
   * @throws ClassNotFoundException
   * @throws SQLException
   * @throws ParseException
   */
  public static void edit() throws ClassNotFoundException, SQLException, ParseException
  {
    input = 0;
    do {
      System.out.println("Enter a Store ID to edit a Store or enter 0 to return to Store Menu");
      System.out.println();
      System.out.println("Store ID: ");

      input = scan.nextInt();
      scan.nextLine();
      System.out.println();

      if (input > 0) {
        rs = StoreSQL.viewStore(storeID);
        if (!rs.next()) {
          System.out.println("Store ID does not exist.");
        } else {
          addOrEdit("edit");
        }
      } else if (input == 0) {
        return;
      } else {
        System.out.println("Invalid Store ID");
      }
    } while (input != 0);
  }

  /**
   * Displays menu and calls SQL for deleting a Staff tuple
   * @throws ClassNotFoundException
   * @throws SQLException
   * @throws ParseException
   */
  public static void delete() throws ClassNotFoundException, SQLException, ParseException
  {
    do{
      System.out.println("Delete Store Menu:");
      System.out.println("Enter Store ID to delete Store or enter 0 to return to Store Menu");
      System.out.println();
      System.out.println("Store ID: ");

      input = scan.nextInt();
      scan.nextLine();
      System.out.println();

      if (input > 0) {
        try {
          StoreSQL.deleteStore(input);
        } catch (SQLException e) {
          e.printStackTrace();
        }
      } else if (input < 0) {
        System.out.println("Invalid input");
      } else if (input == 0) {
        System.out.println("Returning to Store Menu");
      }
    } while (input != 0);
  }

  /**
   * Prints to stdout all tuples held in the static ResultSet
   * @param rs ResultSet containing the Store tuples to print to stdout
   * @throws SQLException
   */
  public static void printStaff(ResultSet rs) throws SQLException 
  {
    if (!rs.next()) {
      System.out.println("There is no Store with this storeID.");
    } else {
      do {
        storeID = rs.getInt("storeID");
        managerID = rs.getInt("managerID");
        address = rs.getString("address");
        phone = rs.getString("phone");
        System.out.println("Store ID: " + storeID + ", Manager ID: " + managerID + ", Address: " + address + ", Phone: " + phone);
      } while (rs.next());
    }
  }

  /**
   * Parses user input to allow user to set desired attribute for either INSERT or UPDATE operation.
   * @param mode Determines whether to "add" new tuple or "edit" existing tuple
   * @throws ClassNotFoundException
   * @throws SQLException
   * @throws ParseException
   */
  public static void addOrEdit(String mode) throws ClassNotFoundException, SQLException, ParseException
  {
    int input = 0;

    if (mode.equals("edit")) {
      storeID = rs.getInt("storeID");
      managerID = rs.getInt("managerID");
      address = rs.getString("address");
      phone = rs.getString("phone");
    }

    do {
      System.out.println("Enter a number to " + mode + " store attributes or enter 0 to return to Store Menu");
      System.out.println("0. Return to Store Menu");
      System.out.println("1. Store ID: " + storeID);
      System.out.println("2. Manager ID: " + managerID);
      System.out.println("3. Address: " + address);
      System.out.println("4. Phone: " + phone);
      System.out.println("5. After all attributes have been entered, select 5 to " + mode + " Store");

      input = scan.nextInt();
      scan.nextLine();
      System.out.println();

      switch(input) {
        case 0:
          System.out.println("Going back to Store Menu");
          System.out.println();
          resetAttributes();
          break;
        case 1:
          System.out.println("Enter Store ID: ");
          storeID = scan.nextInt();
          break;
        case 2:
          System.out.println("Enter Manager ID: ");
          managerID = scan.nextLine();
          break;
        case 3:
          System.out.println("Enter Address: ");
          address = scan.nextLine();
          break;
        case 4:
          System.out.println("Enter Phone Number: ");
          phone = scan.nextLine();
          break;
        case 5:
          try{
            if(mode.equals("add")) {
              StoreSQL.addStore(storeID, managerID, address, phone);
            } else if (mode.equals("edit")) {
              StoreSQL.editStore(storeID, managerID, address, phone);
            }
          } catch(SQLException e) {
            System.out.println("SQL Exception: " + e.getStackTrace());
            break;
          }
          ResultSet rs = StoreSQL.viewStore(storeID);
          printStore(rs);
          resetAttributes();
          input = 0; // We're done, back to Store Menu
          break;
        default:
          System.out.println("Invalid input");
          break;
      }
    } while(input != 0);
  }

  /**
   * Resets store attributes back to default.
   */
  public static void resetAttributes()
  {
    storeID = -1;
    managerID = -1;
    address = "";
    phone = "";
  }
}