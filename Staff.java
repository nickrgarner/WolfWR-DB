import java.sql.*;
import java.util.*;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;

public class Staff {
  /** Reads user input from stdin */
  static Scanner scan = new Scanner(System.in);
  /** Current menu choice */
  static int input = 0;
  /** Return value of SQL queries */
  static ResultSet rs = null;

  /** Staff table schema */
  static int staffID = -1;
  static int storeID = -1;
  static String name = "";
  static int age = -1;
  static String address = "";
  static String title = "";
  static String phone = "";
  static String email = "";
  static Date employmentTime = Date.valueOf("0001-01-01");

  /**
   * Displays main menu for Staff table and delegates user input to Staff action methods.
   * @throws ClassNotFoundException
   * @throws SQLException
   * @throws ParseException
   */
  public static void staffMenu() throws ClassNotFoundException, SQLException, ParseException 
  {
    do {
      System.out.println("Staff Menu");
      System.out.println();
      System.out.println("0. Return to Main Menu");
      System.out.println("1. View All Staff");
      System.out.println("2. View Staff by ID");
      System.out.println("3. Add Staff");
      System.out.println("4. Edit Staff");
      System.out.println("5. Delete Staff");
      System.out.println();
      System.out.println("Please choose an option from the menu: ");

      input = scan.nextInt();

      switch(input) {
        case 0:
          System.out.println("Going back to Main Menu");
          return;
        case 1:
          viewAllStaff();
          break;
        case 2:
          viewStaff();
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
   * Displays menu and calls SQL for viewing all Staff tuples
   * @throws ClassNotFoundException
   * @throws SQLException
   * @throws ParseException
   */
  public static void viewAllStaff() throws ClassNotFoundException, SQLException, ParseException 
  {
    try {
      ResultSet rs = StaffSQL.viewAllStaff();
      printStaff(rs);
    } catch(SQLException e) {
      System.out.println("SQL Exception: " + e.getMessage());
    }
  }

  /**
   * Displays menu and calls SQL for viewing a single Staff tuple
   * @throws ClassNotFoundException
   * @throws SQLException
   * @throws ParseException
   */
  public static void viewStaff() throws ClassNotFoundException, SQLException, ParseException {
    do {
      System.out.println("In order to go back to Staff Menu, enter 0");
      System.out.println("Please enter a Staff ID to view a staff member");
      System.out.println();
      System.out.println("Staff ID: ");

      input = scan.nextInt();
      scan.nextLine();

      if (input > 0) {
        ResultSet rs = StaffSQL.viewStaff(input);
        printStaff(rs);
        rs.close();
      } else if (input < 0) {
        System.out.println("Invalid input");
      } else if (input == 0) {
        System.out.println("Going back to Staff Menu");
        return;
      }
    } while (input != 0);
  }

  /**
   * Displays menu and calls SQL for editing a Staff tuple
   * @throws ClassNotFoundException
   * @throws SQLException
   * @throws ParseException
   */
  public static void edit() throws ClassNotFoundException, SQLException, ParseException
  {
    input = 0;
    do {
      System.out.println("Enter a Staff ID to edit a Staff Member or enter 0 to return to Staff Menu");
      System.out.println();
      System.out.println("Staff ID: ");

      input = scan.nextInt();
      scan.nextLine();
      System.out.println();

      if (input > 0) {
        rs = StaffSQL.viewStaff(staffID);
        if (!rs.next()) {
          System.out.println("Staff ID does not exist.");
        } else {
          addOrEdit("edit");
        }
      } else if (input == 0) {
        System.out.println("Going back to Staff Menu");
        return;
      } else {
        System.out.println("Invalid Staff ID");
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
      System.out.println("Delete Staff Menu:");
      System.out.println("Enter Staff ID to delete Staff or enter 0 to return to Staff Menu");
      System.out.println();
      System.out.println("Staff ID: ");

      input = scan.nextInt();
      scan.nextLine();
      System.out.println();

      if (input > 0) {
        try {
          StaffSQL.deleteStaff(input);
        } catch (SQLException e) {
          e.printStackTrace();
        }
      } else if (input < 0) {
        System.out.println("Invalid input");
      } else if (input == 0) {
        System.out.println("Going back to Staff Menu");
        return;
      }
    } while (input != 0);
  }

  /**
   * Prints to stdout all tuples held in the static ResultSet
   * @param rs ResultSet containing the Staff tuples to print to stdout
   * @throws SQLException
   */
  public static void printStaff(ResultSet rs) throws SQLException 
  {
    if (!rs.next()) {
      System.out.println("There is no Staff with this staffID.");
    } else {
      do {
        staffID = rs.getInt("staffID");
        storeID = rs.getInt("storeID");
        name = rs.getString("name");
        age = rs.getInt("age");
        address = rs.getString("address");
        title = rs.getString("title");
        phone = rs.getString("phone");
        email = rs.getString("email");
        employmentTime = rs.getDate("employmentTime");
        System.out.println("Staff ID: " + staffID + ", Store ID: " + storeID + ", Name: " + name + ", Age: " + age + ", Address: " + address + ", Title: " + title + ", Phone: " + phone + ", Email: " + email + ", Employment Time: " + employmentTime);
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
      staffID = rs.getInt("staffID");
      storeID = rs.getInt("storeID");
      name = rs.getString("name");
      age = rs.getInt("age");
      address = rs.getString("address");
      title = rs.getString("title");
      phone = rs.getString("phone");
      email = rs.getString("email");
      employmentTime = rs.getDate("employmentTime");
    }

    do {
      System.out.println("Enter a number to " + mode + " staff attributes or enter 0 to return to Staff Menu");
      System.out.println("0. Return to Staff Menu");
      System.out.println("1. Staff ID: " + staffID);
      System.out.println("2. Store ID: " + storeID);
      System.out.println("3. Name " + name);
      System.out.println("4. Age: " + age);
      System.out.println("5. Address: " + address);
      System.out.println("6. Title: " + title);
      System.out.println("7. Phone: " + phone);
      System.out.println("8. Email: " + email);
      System.out.println("9. Employment Time: " + employmentTime);
      System.out.println("10. After all attributes have been entered, select 10 to " + mode + " Staff Member");

      input = scan.nextInt();
      scan.nextLine();
      System.out.println();

      switch(input) {
        case 0:
          System.out.println("Going back to Staff Menu");
          System.out.println();
          resetAttributes();
          return;
        case 1:
          System.out.println("Enter Staff ID: ");
          staffID = scan.nextInt();
          break;
        case 2:
          System.out.println("Enter Store ID: ");
          storeID = scan.nextInt();
          break;
        case 3:
          System.out.println("Enter Name: ");
          name = scan.nextLine();
          break;
        case 4:
          System.out.println("Enter Age: ");
          age = scan.nextInt();
          break;
        case 5:
          System.out.println("Enter Address: ");
          address = scan.nextLine();
          break;
        case 6:
          System.out.println("Enter Title: ");
          title = scan.nextLine();
          break;
        case 7:
          System.out.println("Enter Phone Number: ");
          phone = scan.nextLine();
          break;
        case 8:
          System.out.println("Enter Email Address: ");
          email = scan.nextLine();
          break;
        case 9:
          System.out.println("Enter Employment Time in format YYYY-MM-DD: ");
          try {
            employmentTime = Date.valueOf(scan.nextLine());
          } catch (IllegalArgumentException e) {
            System.out.println("Date format incorrect, please try again.");
          }
          break;
        case 10:
          try{
            if(mode.equals("add")) {
              StaffSQL.addStaff(staffID, storeID, name, age, address, title, phone, email, employmentTime);
            } else if (mode.equals("edit")) {
              StaffSQL.editStaff(staffID, storeID, name, age, address, title, phone, email, employmentTime);
            }
          } catch(SQLException e) {
            System.out.println("SQL Exception: " + e.getStackTrace());
            break;
          }
          ResultSet rs = StaffSQL.viewStaff(staffID);
          printStaff(rs);
          resetAttributes();
          input = -1; // We're done, back to Staff Menu
          return;
        default:
          System.out.println("Invalid input");
          break;
      }
    } while(input != 0);
  }

  /**
   * Resets staff attributes back to default.
   */
  public static void resetAttributes()
  {
    staffID = -1;
    storeID = -1;
    name = "";
    age = -1;
    address = "";
    title = "";
    phone = "";
    email = "";
    employmentTime = Date.valueOf("0001-01-01");
  }
}
