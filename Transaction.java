import java.sql.*;
import java.util.*;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;

public class Transaction {
  /** Reads user input from stdin */
  static Scanner scan = new Scanner(System.in);
  /** Current menu choice */
  static int input = 0;
  /** Return value of SQL queries */
  static ResultSet rs = null;

  /** Transaction table schema */
  static int transactionID = -1;
  static int storeID = -1;
  static int memberID = -1;
  static int cashierID = -1;
  static Date date = Date.valueOf("0001-01-01");
  static int productID = -1;
  static double price = -1.00;
  static int quantity = -1;
  static double total = -1.00;

  /**
   * Displays main menu for Transaction table and delegates user input to Transaction action methods
   * @throws ClassNotFoundException
   * @throws SQLException
   * @throws ParseException
   */
  public static void transactionMenu() throws ClassNotFoundException, SQLException, ParseException
  {
    do {
      System.out.println("Transaction Menu");
      System.out.println();
      System.out.println("0. Return to Main Menu");
      System.out.println("1. View All Transactions");
      System.out.println("2. View Transaction by ID");
      System.out.println("3. Add Transaction");
      System.out.println("4. Edit Transaction");
      System.out.println("5. Delete Transaction");
      System.out.println();
      System.out.println("Please choose an option from the menu: ");

      input = scan.nextInt();

      switch(input) {
        case 0:
          System.out.println("Going back to Main Menu");
          return;
        case 1:
          viewAllTransactions();
          break;
        case 2:
          viewTransaction();
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
   * Displays menu and calls SQL for viewing all Transaction tuples
   * @throws ClassNotFoundException
   * @throws SQLException
   * @throws ParseException
   */
  public static void viewAllTransactions() throws ClassNotFoundException, SQLException, ParseException
  {
    try {
      ResultSet rs = TransactionSQL.viewAllTransactions();
      printTransaction(rs);
    } catch(SQLException e) {
      System.out.println("SQL Exception: " + e.getMessage());
    }
  }

  /**
   * Displays menu and calls SQL for viewing a single Transaction tuple
   * @throws ClassNotFoundException
   * @throws SQLException
   * @throws ParseException
   */
  public static void viewTransaction() throws ClassNotFoundException, SQLException, ParseException
  {
    do {
      System.out.println("In order to go back to Transaction Menu, enter 0");
      System.out.println("Please enter a Transaction ID to view a Transaction");
      System.out.println();
      System.out.println("Transaction ID: ");

      input = scan.nextInt();
      scan.nextLine();

      if (input > 0) {
        ResultSet rs = TransactionSQL.viewTransaction(input);
        printTransaction(rs);
        rs.close();
      } else if (input < 0) {
        System.out.println("Invalid input");
      } else if (input == 0) {
        System.out.println("Going back to Transaction Menu");
        return;
      }
    } while (input != 0);
  }

  /**
   * Displays menu and calls SQL for editing a Transaction tuple
   * @throws ClassNotFoundException
   * @throws SQLException
   * @throws ParseException
   */
  public static void edit() throws ClassNotFoundException, SQLException, ParseException
  {
    input = 0;
    do {
      System.out.println("Enter a Transaction ID to edit a Transaction or enter 0 to return to Transaction Menu");
      System.out.println();
      System.out.println("Transaction ID: ");

      input = scan.nextInt();
      scan.nextLine();
      System.out.println();

      if (input > 0) {
        rs = TransactionSQL.viewTransaction(transactionID);
        if (!rs.next()) {
          System.out.println("Transaction ID does not exist.");
        } else {
          addOrEdit("edit");
        }
      } else if (input == 0) {
        System.out.println("Going back to Transaction Menu");
        return;
      } else {
        System.out.println("Invalid Transaction ID");
      }
    } while (input != 0);
  }

  /**
   * Displays menu and calls SQL for deleting a Transaction tuple
   * @throws ClassNotFoundException
   * @throws SQLException
   * @throws ParseException
   */
  public static void delete() throws ClassNotFoundException, SQLException, ParseException
  {
    do{
      System.out.println("Delete Transaction Menu:");
      System.out.println("Enter Transaction ID to delete a Transaction or enter 0 to return to Transaction Menu");
      System.out.println();
      System.out.println("Transaction ID: ");

      input = scan.nextInt();
      scan.nextLine();
      System.out.println();

      if (input > 0) {
        try {
          TransactionSQL.deleteTransaction(input);
        } catch (SQLException e) {
          e.printStackTrace();
        }
      } else if (input < 0) {
        System.out.println("Invalid input");
      } else if (input == 0) {
        System.out.println("Returning to Transaction Menu");
        return;
      }
    } while (input != 0);
  }

  /**
   * Prints to stdout all tuples held in the static ResultSet
   * @param rs
   * @throws SQLException
   */
  public static void printTransaction(ResultSet rs) throws SQLException
  {
    if (!rs.next()) {
      System.out.println("There is no Transaction with this transactionID.");
    } else {
      do {
        transactionID = rs.getInt("transactionID");
        storeID = rs.getInt("storeID");
        memberID = rs.getInt("memberID");
        cashierID = rs.getInt("cashierID");
        date = rs.getDate("date");
        productID = rs.getInt("productID");
        price = rs.getDouble("price");
        quantity = rs.getInt("quantity");
        total = rs.getDouble("total");
        System.out.println("Transaction ID: " + transactionID + ", Store ID: " + storeID + ", MemberID: " + memberID + ", Cashier ID: " + cashierID + ", Date: " + date + ", Product ID: " + productID + ", Price: " + price + ", Quantity: " + quantity + ", Total: " + total);
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
    int input = 0;

    if (mode.equals("edit")) {
      transactionID = rs.getInt("transactionID");
      storeID = rs.getInt("storeID");
      memberID = rs.getInt("memberID");
      cashierID = rs.getInt("cashierID");
      date = rs.getDate("date");
      productID = rs.getInt("productID");
      price = rs.getDouble("price");
      quantity = rs.getInt("quantity");
      total = rs.getDouble("total");
    }

    do {
      System.out.println("Enter a number to " + mode + " transaction attributes or enter 0 to return to Transaction Menu");
      System.out.println("0. Return to Transaction Menu");
      System.out.println("1. Transaction ID: " + transactionID);
      System.out.println("2. Store ID: " + storeID);
      System.out.println("3. Member ID: " + memberID);
      System.out.println("4. Cashier ID: " + cashierID);
      System.out.println("5. Date: " + date);
      System.out.println("6. Product ID: " + productID);
      System.out.println("7. Price: " + price);
      System.out.println("8: Quantity: " + quantity);
      System.out.println("9. Total: " + total);
      System.out.println("10. After all attributes have been entered, select 10 to " + mode + " Transaction");

      input = scan.nextInt();
      scan.nextLine();
      System.out.println();

      switch(input) {
        case 0:
          System.out.println("Going back to Transaction Menu");
          System.out.println();
          resetAttributes();
          return;
        case 1:
          System.out.println("Enter Transaction ID: ");
          transactionID = scan.nextInt();
          break;
        case 2:
          System.out.println("Enter Store ID: ");
          storeID = scan.nextInt();
          break;
        case 3:
          System.out.println("Enter Member ID: ");
          memberID = scan.nextInt();
          break;
        case 4:
          System.out.println("Enter Cashier ID: ");
          cashierID = scan.nextInt();
          break;
        case 5:
          System.out.println("Enter Date in format YYYY-MM-DD: ");
          try{
            date = Date.valueOf(scan.nextLine());
          } catch (IllegalArgumentException e) {
            System.out.println("Date format incorrect, please try again.");
          }
          break;
        case 6:
          System.out.println("Enter Product ID: ");
          productID = scan.nextInt();
          break;
        case 7:
          System.out.println("Enter Price: ");
          price = scan.nextDouble();
          break;
        case 8:
          System.out.println("Enter Quantity: ");
          quantity = scan.nextInt();
          break;
        case 9:
          System.out.println("Enter Total: ");
          total = scan.nextDouble();
          break;
        case 10:
          try{
            if (mode.equals("add")) {
              TransactionSQL.addTransaction(transactionID, storeID, memberID, cashierID, date, productID, price, quantity, total);
            } else if (mode.equals("edit")) {
              TransactionSQL.editTransaction(transactionID, storeID, memberID, cashierID, date, productID, price, quantity, total);
            }
          } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getStackTrace());
            break;
          }
          rs = TransactionSQL.viewTransaction(transactionID);
          printTransaction(rs);
          resetAttributes();
          input = -1; // We're done, back to Transaction Menu
          return;
        default:
          System.out.println("Invalid input");
          break;
      }
    } while(input != 0);
  }

  /**
   * Menu for Total Sales report. Prompts user for date range then calls SQL method to get sum.
   * @throws ClassNotFoundException
   * @throws SQLException
   * @throws ParseException
   */
  public static void totalSales() throws ClassNotFoundException, SQLException, ParseException
  {
    do {
      String rangeStart = "0001-01-01";
      String rangeEnd = "0001-01-01";
      int id = -1;
      boolean isStore = false;
      boolean isMember = false;

      System.out.println("Report: Sales and Purchase Reports");
      System.out.println();
      System.out.println("0. Return to Reports Menu");
      System.out.println("1. View total sales by day");
      System.out.println("2. View total sales by month");
      System.out.println("3. View total sales by year");
      System.out.println();
      System.out.print("Please choose an option from the menu: ");

      input = scan.nextInt();

      if (input != 0) {
        System.out.println("Is this for a specific Store? (y/n)");
        isStore = scan.next().toLowerCase().equals("y");
        if (!isStore) {
          System.out.println("Do you want a Member purchase report?(y/n)");
          isMember = scan.next().toLowerCase().equals("y");
        }
      }

      if (isStore) {
        System.out.println("Please enter the ID of the store: ");
        id = scan.nextInt();
      } else if (isMember) {
        System.out.println("Please enter the ID of the member: ");
        id = scan.nextInt();
      }

      switch(input) {
        case 0:
          System.out.println("Going back to Reports Menu");
          return;
        case 1:
          System.out.println("Please input Start Date in the format YYYY-MM-DD:");
          rangeStart = scan.next();
          System.out.println("Please input End Date in the format YYYY-MM-DD:");
          rangeEnd = scan.next();
          break;
        case 2:
          System.out.println("Please input Start year in the format YYYY");
          rangeStart = String.valueOf(scan.nextInt()) + rangeStart.substring(4);
          System.out.println("Please input Start month in the format MM");
          rangeStart = rangeStart.toString().substring(0, 5) + String.valueOf(scan.nextInt()) + "-01";
          System.out.println("Please input End year in the format YYYY");
          rangeEnd = String.valueOf(scan.nextInt()) + rangeEnd.substring(4);
          System.out.println("Please input End month in the format MM");
          rangeEnd = rangeEnd.substring(0, 5) + String.valueOf(scan.nextInt()) + "-01";
          break;
        case 3:
          System.out.println("Please input Start year in the format YYYY");
          rangeStart = String.valueOf(scan.nextInt()) + "-01-01";
          System.out.println("Please input End year in the format YYYY");
          rangeEnd = String.valueOf(scan.nextInt()) + "-01-01";
          break;
      }

      rs = TransactionSQL.totalSalesReport(isStore, isMember, id, rangeStart, rangeEnd);
      rs.next();
      if (isStore) {
        System.out.println("Total Sales for Store " + id + " for period between " + rangeStart + " and " + rangeEnd + ": $" + rs.getDouble("SUM(total)"));
      } else if (isMember) {
        System.out.println("Total Sales for Member " + id + " for period between " + rangeStart + " and " + rangeEnd + ": $" + rs.getDouble("SUM(total)"));
      } else {
        System.out.println("Total Sales for period between " + rangeStart + " and " + rangeEnd + ": $" + rs.getDouble("SUM(total)"));
      }
      System.out.println();

    } while (input != 0);
  }

  /**
     * Method is called in the MainMenu file under maintainBillingTransactions. This method will prompt user
     * for a transactionID to be passed to transactionTotal in TransactionSQL to generate the query.
     */
    public static void transactionTotal() throws ParseException, ClassNotFoundException, SQLException{
      do {
          System.out.println("In order to go back to Main Menu, enter 0");
          System.out.println("Generate total price for each transaction, enter 1");
          input = scan.nextInt();

          if(input == 0){
              return;
          } else if (input == 1){
              System.out.println("In order to generate the total price of a transaction, please enter a transactionID");
              transactionID = scan.nextInt();

              rs = TransactionSQL.transactionTotal(transactionID);

              if(!rs.next()){
                System.out.println("There is no transaction with this transactionID");
              } else{
                  rs.next();
                  System.out.println("Total Price for Transaction: " + rs.getInt("SUM(total)"));
              }
              rs.close();

          } else{
              System.out.println("Invalid input");
          }
      } while(input != 0);
  }

  /**
   * Resets Transaction attributes back to default.
   */
  public static void resetAttributes()
  {
    transactionID = -1;
    storeID = -1;
    memberID = -1;
    cashierID = -1;
    date = Date.valueOf("0001-01-01");
    productID = -1;
    price = -1.00;
    quantity = -1;
    total = -1.00;
  }
}
