import java.sql.*;
import java.util.*;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;

public class Discount {
  /** Reads user input from stdin */
  static Scanner scan = new Scanner(System.in);
  /** Current menu choice */
  static int input = 0;
  /** Return value of SQL queries */
  static ResultSet rs = null;

  /** Discount table schema */
  static int discountID = -1;
  static int productID = -1;
  static double priceReduction = -1.00;
  static Date startDate = Date.valueOf("0001-01-01");
  static Date endDate = Date.valueOf("0001-01-01");

  /**
   * Displays main menu for Discount table and delegates user input to Discount action methods
   * @throws ClassNotFoundException
   * @throws SQLException
   * @throws ParseException
   */
  public static void discountMenu() throws ClassNotFoundException, SQLException, ParseException
  {
    do {
      System.out.println("Discount Menu");
      System.out.println();
      System.out.println("0. Return to Main Menu");
      System.out.println("1. View All Discounts");
      System.out.println("2. View Discount by ID");
      System.out.println("3. Add Discount");
      System.out.println("4. Edit Discount");
      System.out.println("5. Delete Discount");
      System.out.println();
      System.out.println("Please choose an option from the menu: ");

      input = scan.nextInt();

      switch(input) {
        case 0:
          System.out.println("Going back to Main Menu");
          return;
        case 1:
          viewAllDiscounts();
          break;
        case 2:
          viewDiscount();
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
      input = -1;
    } while(input != 0);
  }

  /**
   * Displays menu and calls SQL for viewing all Discount tuples
   * @throws ClassNotFoundException
   * @throws SQLException
   * @throws ParseException
   */
  public static void viewAllDiscounts() throws ClassNotFoundException, SQLException, ParseException
  {
    try {
      ResultSet rs = DiscountSQL.viewAllDiscounts();
      printDiscount(rs);
    } catch(SQLException e) {
      System.out.println("SQL Exception: " + e.getMessage());
    }
  }

  /**
   * Displays menu and calls SQL for viewing a single Discount tuple
   * @throws ClassNotFoundException
   * @throws SQLException
   * @throws ParseException
   */
  public static void viewDiscount() throws ClassNotFoundException, SQLException, ParseException
  {
    do {
      System.out.println("In order to go back to Discount Menu, enter 0");
      System.out.println("Please enter a Discount ID to view a Discount");
      System.out.println();
      System.out.println("Discount ID: ");

      input = scan.nextInt();
      scan.nextLine();

      if (input > 0) {
        ResultSet rs = DiscountSQL.viewDiscount(input);
        printDiscount(rs);
        rs.close();
      } else if (input < 0) {
        System.out.println("Invalid input");
      } else if (input == 0) {
        System.out.println("Going back to Discount Menu");
        return;
      }
    } while (input != 0);
  }

  /**
   * Displays menu and calls SQL for editing a Discount tuple
   * @throws ClassNotFoundException
   * @throws SQLException
   * @throws ParseException
   */
  public static void edit() throws ClassNotFoundException, SQLException, ParseException
  {
    input = 0;
    do {
      System.out.println("Enter a Discount ID to edit a Discount Member or enter 0 to return to Discount Menu");
      System.out.println();
      System.out.println("Discount ID: ");

      input = scan.nextInt();
      scan.nextLine();
      System.out.println();

      if (input > 0) {
        rs = DiscountSQL.viewDiscount(discountID);
        if (!rs.next()) {
          System.out.println("Discount ID does not exist.");
        } else {
          addOrEdit("edit");
        }
      } else if (input == 0) {
        System.out.println("Going back to Discount Menu");
        return;
      } else {
        System.out.println("Invalid Discount ID");
      }
    } while (input != 0);
  }

  /**
   * Displays menu and calls SQL for deleting a Discount tuple
   * @throws ClassNotFoundException
   * @throws SQLException
   * @throws ParseException
   */
  public static void delete() throws ClassNotFoundException, SQLException, ParseException
  {
    do{
      System.out.println("Delete Discount Menu:");
      System.out.println("Enter Discount ID to delete a Discount or enter 0 to return to Discount Menu");
      System.out.println();
      System.out.println("Discount ID: ");

      input = scan.nextInt();
      scan.nextLine();
      System.out.println();

      if (input > 0) {
        try {
          DiscountSQL.deleteDiscount(input);
        } catch (SQLException e) {
          e.printStackTrace();
        }
      } else if (input < 0) {
        System.out.println("Invalid input");
      } else if (input == 0) {
        System.out.println("Returning to Discount Menu");
        return;
      }
    } while (input != 0);
  }

  /**
   * Prints to stdout all tuples held in the static ResultSet
   * @param rs
   * @throws SQLException
   */
  public static void printDiscount(ResultSet rs) throws SQLException
  {
    if (!rs.next()) {
      System.out.println("There is no Discount with this Discount ID.");
    } else {
      do {
        discountID = rs.getInt("discountID");
        productID = rs.getInt("productID");
        priceReduction = rs.getDouble("priceReduction");
        startDate = rs.getDate("startDate");
        endDate = rs.getDate("endDate");
        System.out.println("Discount ID: " + discountID + ", Product ID: " + productID + ", Price Reduction: " + priceReduction + ", Start Data: " + startDate + ", End Date: " + endDate);
      } while (rs.next());
    }
  }

  /**
   * Parses input to allow user to set desired attributes for either INSERT or UPDATE operation.
   * @param mode Determines whether to "add" new tuple or "edit" existing tuple
   * @throws ClassNotFoundException
   * @throws SQLException
   * @throws ParseException
   */
  public static void addOrEdit(String mode) throws ClassNotFoundException, SQLException, ParseException
  {
    int input = -1;

    if (mode.equals("edit")) {
      discountID = rs.getInt("discountID");
      productID = rs.getInt("productID");
      priceReduction = rs.getDouble("priceReduction");
      startDate = rs.getDate("startDate");
      endDate = rs.getDate("endDate");
    }

    do {
      System.out.println("Enter a number to " + mode + " discount attributes or enter 0 to return to Discount Menu");
      System.out.println("0. Return to Discount Menu");
      System.out.println("1. Discount ID: " + discountID);
      System.out.println("2. Product ID: " + productID);
      System.out.println("3. Price Reduction: " + priceReduction);
      System.out.println("4. Start Date: " + startDate);
      System.out.println("5. End Date: " + endDate);
      System.out.println("6. After all attributes have been entered, select 6 to " + mode + " Discount");

      input = scan.nextInt();
      scan.nextLine();
      System.out.println();

      switch(input) {
        case 0:
          System.out.println("Going back to Discount Menu");
          System.out.println();
          resetAttributes();
          return;
        case 1:
          System.out.println("Enter Discount ID: ");
          discountID = scan.nextInt();
          break;
        case 2:
          System.out.println("Enter Product ID: ");
          productID = scan.nextInt();
          break;
        case 3:
          System.out.println("Enter Price Reduction: ");
          priceReduction = scan.nextDouble();
          break;
        case 4:
          System.out.println("Enter Start Date in format YYYY-MM-DD: ");
          try{
            startDate = Date.valueOf(scan.nextLine());
          } catch (IllegalArgumentException e) {
            System.out.println("Date format incorrect, please try again.");
          }
          break;
        case 5:
          System.out.println("Enter End Date in format YYYY-MM-DD: ");
          try{
            endDate = Date.valueOf(scan.nextLine());
          } catch (IllegalArgumentException e) {
            System.out.println("Date format incorrect, please try again.");
          }
          break;
        case 6:
          try{
            if (mode.equals("add")) {
              DiscountSQL.addDiscount(discountID, productID, priceReduction, startDate, endDate);
            } else if (mode.equals("edit")) {
              DiscountSQL.editDiscount(discountID, productID, priceReduction, startDate, endDate);
            }
          } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getStackTrace());
            break;
          }
          rs = DiscountSQL.viewDiscount(discountID);
          printDiscount(rs);
          resetAttributes();
          input = -1; // We're done, back to Discount Menu
          return;
        default:
          System.out.println("Invalid input");
          break;
      }
    } while(input != 0);
  }

  /**
   * Resets Discount attributes back to default.
   */
  public static void resetAttributes()
  {
  discountID = -1;
  productID = -1;
  priceReduction = -1.00;
  startDate = Date.valueOf("0001-01-01");
  endDate = Date.valueOf("0001-01-01");
  }
}
