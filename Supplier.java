import java.sql.*;
import java.util.*;
import java.sql.SQLException;
import java.text.ParseException;

public class Supplier {
    static Scanner scan = new Scanner(System.in);
    static int input = 0;
    static ResultSet rs = null;

    //Declaring and Instantiating all the attribute of a Supplier
    static int supplierID = -1;
    static String name = "";
    static String phone = "";
    static String email = "";
    static String location = "";
    static double amountOwed = -1.00;

    /**
     * This method is called in the MainMenu file when the user wants to add/edit/delete/view suppliers in the database.
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws ParseException
     */
    public static void supplierMenu() throws ClassNotFoundException, SQLException, ParseException{
        do{
            System.out.println("Supplier Menu");
            System.out.println();
            System.out.println("0. Return to Information Processing Menu");
            System.out.println("1. View All Suppliers");
            System.out.println("2. View Supplier by ID");
            System.out.println("3. Add Supplier");
            System.out.println("4. Edit Supplier");
            System.out.println("5. Delete Supplier");
            System.out.println();
            System.out.println("Please choose an option from the menu: ");

            input = scan.nextInt();

            switch(input){
                case 0:
                    System.out.println("Going back to Information Processing Menu");
                    return;
                case 1:
                    viewAllSuppliers();
                    break;
                case 2:
                    viewSupplier();
                    break;
                case 3:
                    addSupplier();
                    break;
                case 4:
                    editSupplier();
                    break;
                case 5:
                    deleteSupplier();
                    break;
                default:
                    System.out.println("Invalid input");
                    break;
            }
            input = -1;
        } while(input != 6);
    }
    /**
     * Method displays all suppliers in the database
     */
    public static void viewAllSuppliers() throws ClassNotFoundException, SQLException, ParseException{
        try{
            rs = SupplierSQL.viewAllSuppliers();
            printSupplier(rs);
        }
        catch(SQLException e){
            System.out.println("SQL Exception");
            e.getStackTrace();
        }
    }
    /**
     * Method displays information of a single supplier
     */
    public static void viewSupplier() throws ClassNotFoundException, SQLException, ParseException {
        do{
            System.out.println("In order to go back to Supplier Menu, enter 0");
            System.out.println("Please enter a supplier ID to view a supplier");
            System.out.println();
            System.out.println("Supplier ID: ");

            input = scan.nextInt();
            scan.nextLine();

            if(input > 0){
                rs = SupplierSQL.viewSupplier(input);
                printSupplier(rs);
                rs.close();

            } else if(input < 0){
                System.out.println("Invalid input");
            } else if(input == 0){
                System.out.println("Going back to Supplier Menu");
                return;
            }
        } while(input != 0);
    }
    /**
     * Method adds a suppliers information to database by calling the addSupplier in the SupplierSQl file
     */
    public static void addSupplier() throws ParseException, ClassNotFoundException, SQLException{
        //All the current attributes on the first iteration will be empty, as the user begins to fill in attributes
        //of the supplier the display will be updated. Before the user selects 10, all the attributes must be filled
        //so that the supplier can be created in the database.
        do{
            System.out.println("0. Go back to Supplier menu: ");
            System.out.println("1. Supplier ID: " + supplierID);
            System.out.println("2. Supplier Name: " + name);
            System.out.println("3. Phone Number: " + phone);
            System.out.println("4. Email: " + email);
            System.out.println("5. Location: " + location);
            System.out.println("6. Amount Owed: " + amountOwed);
            System.out.println("7. After all attributes have been entered, select 7 to create the new supplier:");
            System.out.println();
            System.out.println("Select a number to add to supplier information: ");

            input = scan.nextInt();
            scan.nextLine();

            updateAttributes(input, "a");
        } while(input != 0);
        rs.close();
    }
    /**
     * Method edits a supplier information to database by calling the editSupplier in the SupplierSQl file
     */
    public static void editSupplier() throws ParseException, ClassNotFoundException, SQLException{
        int input = 0;
        do{
            System.out.println("In order to go back to Supplier Menu, enter 0");
            System.out.println("Please enter a supplier ID to edit a supplier");

            input = scan.nextInt();
            scan.nextLine();
            System.out.println();
            //SupplierID must be a number greater than 0 so that the application can search for that supplier.
            if(input > 0){
                rs = SupplierSQL.viewSupplier(supplierID);
                //Checks if viewSupplier was able to find an existing Supplier
                if(!rs.next()){
                    System.out.println("Supplier does not exist");
                } else{
                    //The supplier exist and all the attributes of that supplier are being stored into variables so
                    //that they can be displayed to the user. In order for them to choose which attribute to edit.
                    supplierID = rs.getInt("supplierID");
                    name = rs.getString("name");
                    phone = rs.getString("phone");
                    email = rs.getString("email");
                    location =rs.getString("location");
                    amountOwed = rs.getDouble("amountOwed");
                    do{
                        //Attributes are displayed to the user and they must choose which one to edit.
                        //User will be reprompted these options until they decide to go back to Supplier Menu
                        System.out.println("0. Go back to Supplier Menu: ");
                        System.out.println("1. Supplier ID: " + supplierID);
                        System.out.println("2. Supplier Name: " + name);
                        System.out.println("3. Supplier Phone: " + phone);
                        System.out.println("4. Email: " + email);
                        System.out.println("5. Location: " + location);
                        System.out.println("6. Amount Owed: " + amountOwed);
                        System.out.println("7. After all attributes have been entered, select 7 to edit supplier:");
                        System.out.println();
                        System.out.println("Select a number to edit supplier information: ");

                        input = scan.nextInt();
                        scan.nextLine();

                        updateAttributes(input, "e");
                    } while(input != 0);
                }
            }
        }while(input != 0);
    }
    /**
     * Method deletes a supplier information in the database by calling the deleteSupplier in the SupplierSQl file
     */
    public static void deleteSupplier(){
        do {
            System.out.println("In order to go back to Supplier Menu, enter 0");
            System.out.println("Please enter a supplier ID to delete a supplier");
            System.out.println();
            System.out.println("Supplier ID: ");

            input = scan.nextInt();

            if(input > 0){
                SupplierSQL.deleteSupplier(input);
            } else if(input < 0){
                System.out.println("Invalid input");
            } else if (input == 0){
                System.out.println("Going back to Supplier Menu");
                return;
            }
        } while(input != 0);
    }
    
    /**
     * Method is used in all the methods above to print out the information of a supplier. This method takes in
     * a ResultSet and then prints out all the attributes each supplier of the ResultSet.
     * @param rs
     * @throws SQLException
     */
    public static void printSupplier(ResultSet rs) throws SQLException{
        if(!rs.next()){
            System.out.println("There is no supplier with this supplierID");
        } else{
            do{
                supplierID = rs.getInt("supplierID");
                name = rs.getString("name");
                phone = rs.getString("phone");
                email = rs.getString("email");
                location = rs.getString("location");
                amountOwed = rs.getDouble("amountOwed");
                System.out.println("Supplier ID: " + supplierID + ", name: " + name + ", phone: " + phone + ", email: "
                                    + email + ", location: " + location + ", amount owed: " + amountOwed);
            } while(rs.next());

        }
    }
    /**
    * This method is used to reset all the variables to "", 0, or false
    */
    public static void resetAttributes(){
        supplierID = 0;
        name = "";
        phone = "";
        email = "";
        location = "";
        amountOwed = 0.0;
    }
    /**
     * From option that the user selects, this switch statement will prompt the user for the new value for
     * specific attribute.
     * Case 7 will take all the updated attributes and update the supplier in the query. Afterwards, it will
     * reset all the variable fields to "" , 0, or false.
     * @param input
     * @param s
     */
    public static void updateAttributes(int input, String s) throws ParseException, ClassNotFoundException, SQLException{
        switch(input){
            case 0:
                System.out.println("Going back to Supplier Menu");
                System.out.println();
                resetAttributes();
                return;
            case 1:
                System.out.println("Enter Supplier ID:");
                supplierID = scan.nextInt();
                break;
            case 2:
                System.out.println("Enter Supplier Name:");
                name = scan.nextLine();
                break;
            case 3:
                System.out.println("Enter Phone Number:");
                phone = scan.nextLine();
                break;
            case 4:
                System.out.println("Enter Email:");
                email = scan.nextLine();
                break;
            case 5:
                System.out.println("Enter Location:");
                location = scan.nextLine();
                break;
            case 6:
                System.out.println("Enter Amount Owed:");
                amountOwed = scan.nextDouble();
                break;
            case 7:
                try{
                    if(s.equals("e")){
                        SupplierSQL.editSupplier(supplierID, name, phone, email, location, amountOwed);
                    }
                    else if(s.equals("a")){
                        SupplierSQL.addSupplier(supplierID, name, phone, email, location, amountOwed);
                    }
                } catch(SQLException e){
                    System.out.println("SQL Exception");
                    e.getStackTrace();
                    break;
                }
                rs = SupplierSQL.viewSupplier(supplierID);
                printSupplier(rs);
                resetAttributes();
                input = -1;
                return;
            default:
                System.out.println("Invalid input");
        }
    }
    /**
     * Method creates a bill for a single supplier
     */
    public static void createSupplierBill() throws ClassNotFoundException, SQLException, ParseException {
        do{
            System.out.println("In order to go back to Billing Menu, enter 0");
            System.out.println("Please enter a Supplier ID to generate a Supplier bill");
            System.out.println();
            System.out.println("Supplier ID: ");

            input = scan.nextInt();
            scan.nextLine();

            if(input > 0){
                rs = SupplierSQL.viewSupplier(input);
                if(!rs.next()){
                    System.out.println("There is no supplier with this supplierID");
                } else {
                  //rs.next();
                  supplierID = rs.getInt("supplierID");
                  name = rs.getString("name");
                  phone = rs.getString("phone");
                  email = rs.getString("email");
                  location = rs.getString("location");
                  amountOwed = rs.getDouble("amountOwed");
                  System.out.println("Supplier Bill For " + name);
                  System.out.println("Total Amount Owed $" + amountOwed);
                  System.out.println("Send Bill to " + location);
                  System.out.println("Supplier Contact Information ");
                  System.out.println("Phone " + phone);
                  System.out.println("Email " + email);
                  System.out.println();
                  rs.close();
                }
            } else if(input < 0){
                System.out.println("Invalid input");
            } else if(input == 0){
                System.out.println("Going back to Billing Menu");
                return;
            }
        } while(input != 0);
    }
}
