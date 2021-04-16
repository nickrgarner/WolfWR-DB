import java.sql.*;
import java.util.*;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;

public class Merchandise {
    static Scanner scan = new Scanner(System.in);
    static int input = -1;
    static ResultSet rs = null;

    //Declaring and Instantiating all the attribute of a Merchandise
    static int productID = -1;
    static int storeID = -1;
    static String name = "";
    static int quantity = -1;
    static double buyPrice = -1.00;
    static double marketPrice = -1.00;
    static Date productionDate = Date.valueOf("0001-01-01");
    static Date expiration = Date.valueOf("0001-01-01");
    static int supplierID = -1;

    public static void merchandiseMenu() throws ClassNotFoundException, SQLException, ParseException{
        do{
            System.out.println("Merchandise Menu");
            System.out.println();
            System.out.println("0. Return to Main Menu");
            System.out.println("1. View All Merchandise");
            System.out.println("2. View Merchandise by ID");
            System.out.println("3. Add Merchandise");
            System.out.println("4. Edit Merchandise");
            System.out.println("5. Delete Merchandise");
            System.out.println();
            System.out.println("Please choose an option from the menu: ");

            input = scan.nextInt();

            switch(input){
                case 0:
                    System.out.println("Going back to Main Menu");
                    return;
                case 1:
                    viewAllMerchandise();
                    break;
                case 2:
                    viewMerchandise();
                    break;
                case 3:
                    addMerchandise(1);
                    break;
                case 4:
                    editMerchandise();
                    break;
                case 5:
                    deleteMerchandise();
                    break;
                default:
                    System.out.println("Invalid input");
                    break;
            }
            input = -1;
        } while(input != 0); 
    }

    /**
     * Method displays all merchandise in the database
     */
    public static void viewAllMerchandise() throws ClassNotFoundException, SQLException, ParseException{
        try{
            rs = MerchandiseSQL.viewAllMerchandise();
            printMerchandise(rs);
        }
        catch(SQLException e){
            System.out.println("SQL Exception");
            e.getStackTrace();
        }
    }
    /**
     * Method displays information of a single merchandise
     */
    public static void viewMerchandise() throws ClassNotFoundException, SQLException, ParseException {
        do{
            System.out.println("In order to go back to Merchandise Menu, enter 0");
            System.out.println("Please enter a product ID to view a merchandise");
            System.out.println();
            System.out.println("Product ID: ");

            input = scan.nextInt();
            scan.nextLine();

            if(input > 0){
                rs = MerchandiseSQL.viewMerchandise(input);
                printMerchandise(rs);
                rs.close();

            } else if(input < 0){
                System.out.println("Invalid input");
            } else if(input == 0){
                System.out.println("Going back to Merchandise Menu");
                return;
            }
        } while(input != 0);
    }
    /**
     * Method adds a merchandise's information to database by calling the addMerchandise method in the MerchandiseSQL file
     */
    public static void addMerchandise(int num) throws ParseException, ClassNotFoundException, SQLException{
        //All the current attributes on the first iteration will be empty, as the user begins to fill in attributes
        //of the merchandise the display will be updated. Before the user selects 10, all the attributes must be filled
        //so that the merchandise can be created in the database.
        do{
            if(num == 1){
                System.out.println("0. Go back to Merchandise Menu: ");
            } else {
                System.out.println("0. Go back to Maintain Inventory Menu: ");
            }
            
            System.out.println("1. Product ID: " + productID);
            System.out.println("2. Store ID: " + storeID);
            System.out.println("3. Name: " + name);
            System.out.println("4. Quantity: " + quantity);
            System.out.println("5. Buy Price: " + buyPrice);
            System.out.println("6. Market Price: " + marketPrice);
            System.out.println("7. Production Date: " + productionDate);
            System.out.println("8. Expiration: " + expiration);
            System.out.println("9. Supplier ID: " + supplierID);
            System.out.println("10. After all attributes have been entered, select 10 to create the new merchandise:");
            System.out.println();
            System.out.println("Select a number to add to merchandise information: ");

            input = scan.nextInt();
            scan.nextLine();

            updateAttributes(input, "a", num);
        } while(input != 0);
        rs.close();
    }
    /**
     * Method edits a merchandise's information to database by calling the editMerchandise in the MerchandiseSQL file
     */
    public static void editMerchandise() throws ParseException, ClassNotFoundException, SQLException{
        int input = 0;
        do{
            System.out.println("In order to go back to Merchandise Menu, enter 0");
            System.out.println("Please enter a product ID to edit a merchandise");

            input = scan.nextInt();
            scan.nextLine();
            System.out.println();
            //productID must be a number greater than 0 so that the application can search for that merchandise.
            if(input > 0){
                rs = MerchandiseSQL.viewMerchandise(productID);
                //Checks if viewMerchandise was able to find an existing Merchandise
                if(!rs.next()){
                    System.out.println("Merchandise does not exist");
                } else{
                    //The merchadise exists and all the attributes of that merchandise are being stored into variables so 
                    //that they can be displayed to the user. In order for them to choose which attribute to edit.
                    productID = rs.getInt("productID");
                    storeID = rs.getInt("storeID"); 
                    name = rs.getString("name");
                    quantity = rs.getInt("quantity");
                    buyPrice =rs.getDouble("buyPrice");
                    marketPrice = rs.getDouble("marketPrice");
                    productionDate = rs.getDate("productionDate");
                    expiration = rs.getDate("expiration");
                    supplierID = rs.getInt("supplierID");
                    do{
                        //Attributes are displayed to the user and they must choose which one to edit.
                        //User will be reprompted these options until they decide to go back to Merchandise Menu
                        System.out.println("0. Go back to Merchandise Menu: ");
                        System.out.println("1. Product ID: " + productID);
                        System.out.println("2. Store ID: " + storeID);
                        System.out.println("3. Name: " + name);
                        System.out.println("4. Quantity: " + quantity);
                        System.out.println("5. Buy Price: " + buyPrice);
                        System.out.println("6. Market Price: " + marketPrice);
                        System.out.println("7. Production Date: " + productionDate);
                        System.out.println("8. Expiration: " + expiration);
                        System.out.println("9. Supplier ID: " + supplierID);
                        System.out.println("10. After all attributes have been entered, select 10 to create the new merchandise:");
                        System.out.println();
                        System.out.println("Select a number to edit merchandise information: ");
                        
            
                        input = scan.nextInt();
                        scan.nextLine();
                        
                        updateAttributes(input, "e", 1);
                    } while(input != 0);
                }
            }
        }while(input != 0);
    }
    /**
     * Method deletes a merchandise's information in the database by calling the deleteMerchandise in the MerchandiseSQL file
     */
    public static void deleteMerchandise(){
        do {
            System.out.println("In order to go back to Merchandise Menu, enter 0");
            System.out.println("Please enter a product ID to delete a merchandise");
            System.out.println();
            System.out.println("Product ID: ");

            input = scan.nextInt();

            if(input > 0){
                MerchandiseSQL.deleteMerchandise(input);
            } else if(input < 0){
                System.out.println("Invalid input");
            } else if (input == 0){
                System.out.println("Going back to Merchandise Menu");
                return;
            }
        } while(input != 0);
    }

    public static void newInventory() throws ParseException, ClassNotFoundException, SQLException{
        do {
            System.out.println("In order to go back to Main Menu, enter 0");
            System.out.println("Input new inventory into database, enter 1");
            input = scan.nextInt();
            if(input == 1){
                addMerchandise(0);
        
                System.out.println();
                System.out.println("Update the amount owed to the supplier, please enter a Supplier ID: ");
                input = scan.nextInt();
                MerchandiseSQL.updateAmountOwed(input);
            } else{
                System.out.println("Invalid input");
            }
        } while(input != 0);
    }

    public static void returnInventory(){

    }

    public static void transferInventory(){

    }

    /**
     * Method is used in all the methods above to print out the information of a merchandise. This method takes in 
     * a ResultSet and then prints out all the attributes each merchandise of the ResultSet.
     * @param rs
     * @throws SQLException
     */
    public static void printMerchandise(ResultSet rs) throws SQLException{
        if(!rs.next()){
            System.out.println("There is no merchandise with this productID");
        } else{
            do{
                productID = rs.getInt("productID");
                storeID = rs.getInt("storeID");
                name = rs.getString("name");
                quantity = rs.getInt("quantity");
                buyPrice = rs.getDouble("buyPrice");
                marketPrice = rs.getDouble("marketPrice");
                productionDate = rs.getDate("productionDate");
                expiration = rs.getDate("expiration");
                supplierID = rs.getInt("supplierID");
                System.out.println("Product ID: " + productID + ", store ID: " + storeID + ", name: " + name + ", quantity: "
                                    + quantity + ", buy price: " + buyPrice + ", market price: " + marketPrice + ", production date: " + productionDate 
                                    + ", expiration: "+ expiration + ", supplier ID: " + supplierID);
            } while(rs.next());
            
        }
    }

     /**
    * This method is used to reset all the variables to "", 0, or false
    */
    public static void resetAttributes(){
        productID = 0;
        storeID = 0;
        name = "";
        quantity = 0;
        buyPrice = 0.0;
        marketPrice = 0.0;
        productionDate = Date.valueOf("0001-01-01");
        expiration = Date.valueOf("0001-01-01");
        supplierID = 0;
    }

    /**
     * From option that the user selects, this switch statement will prompt the user for the new value for 
     * specific attribute.
     * Case 10 will take all the updated attributes and update the merchandise in the query. Afterwards, it will
     * reset all the variable fields to "" , 0, or false.
     * @param input
     * @param s
     */
    public static void updateAttributes(int input, String s, int num) throws ParseException, ClassNotFoundException, SQLException{
        switch(input){
            case 0:
                System.out.println("Going back to Merchandise Menu");
                System.out.println();
                resetAttributes();
                return;
            case 1:
                System.out.println("Enter Product ID:");
                productID = scan.nextInt();
                break;
            case 2:
                System.out.println("Enter Store ID:");
                storeID = scan.nextInt();
                break;
            case 3:
                System.out.println("Enter Name:");
                name = scan.nextLine();
                break;
            case 4:
                System.out.println("Quantity:");
                quantity = scan.nextInt();
                break;
            case 5:
                System.out.println("Enter Buy Price:");
                buyPrice = scan.nextDouble();
                break;
            case 6:
                System.out.println("Enter Market Price:");
                marketPrice = scan.nextDouble();
                break;
            case 7:
                System.out.println("Enter Production Date:");
                productionDate = Date.valueOf(scan.nextLine());
                break;
            case 8:
                System.out.println("Enter Expiration Date:");
                expiration = Date.valueOf(scan.nextLine());
                break;
            case 9:
                System.out.println("Enter Supplier ID:");
                supplierID = scan.nextInt();
                break;
            case 10:
                try{
                    if(s.equals("e")){
                        MerchandiseSQL.editMerchandise(productID, storeID, name, quantity, buyPrice, marketPrice, productionDate, expiration, supplierID);
                    }
                    else if(s.equals("a")){
                        if(num == 1){
                            MerchandiseSQL.addMerchandise(productID, storeID, name, quantity, buyPrice, marketPrice, productionDate, expiration, supplierID);
                        }
                        else{
                            MerchandiseSQL.addNewMerchandise(productID, storeID, name, quantity, buyPrice, marketPrice, productionDate, expiration, supplierID);
                        }
                    }
                } catch(SQLException e){
                    System.out.println("SQL Exception");
                    e.getStackTrace();
                    break;
                }
                rs = MerchandiseSQL.viewMerchandise(productID);
                printMerchandise(rs);
                resetAttributes();
                break;
            default:
                System.out.println("Invalid input");
        }
    }
}
