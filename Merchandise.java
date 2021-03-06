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
            System.out.println("0. Return to Information Processing Menu");
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
                    System.out.println("Going back to Information Processing Menu");
                    return;
                case 1:
                    viewAllMerchandise();
                    break;
                case 2:
                    viewMerchandise();
                    break;
                case 3:
                    addMerchandise(false);
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
            System.out.println("0. Return to Merchandise Menu");
            System.out.println("1. View a merchandise record");

            input = scan.nextInt();
            scan.nextLine();

            if(input > 0){
                System.out.println("Please enter the following to view a merchandise");
                System.out.println();
                System.out.println("Product ID: ");
                int productID = scan.nextInt();
                scan.nextLine();

                System.out.println("Supplier ID: ");
                int supplierID = scan.nextInt();
                scan.nextLine();

                System.out.println("Store ID: ");
                int storeID = scan.nextInt();
                scan.nextLine();

                rs = MerchandiseSQL.viewMerchandise(productID, supplierID, storeID);
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
     * Merchandise stock report menu, users choose to view products by product ID or by Store ID
     */
    public static void viewStockReport() throws ClassNotFoundException, SQLException, ParseException {
      do{
          System.out.println("Merchandise Stock Report Menu");
          System.out.println("0. Go back to Reports Menu:");
          System.out.println("1. Merchandise Stock Report For A Store");
          System.out.println("2. Merchandise Stock Report For A Product");

          System.out.print("Please choose one of the options above: ");
          input = scan.nextInt();
          System.out.println();
          switch(input){
              case 0:
                  System.out.println("Going back to Reports Menu");
                  return;
              case 1:
                  storeStockReport();
                  break;
              case 2:
                  productStockReport();
                  break;
              default:
                  System.out.println("Invalid input");
                  break;
          }
          input = -1;
      } while(input != 0);
    }
    /**
     * Method displays all the merchandise for a single store
     */
    public static void storeStockReport() throws ClassNotFoundException, SQLException, ParseException {
        do{
            System.out.println();
            System.out.println("In order to go back to the stock report menu, enter 0");
            System.out.println("Please enter a store ID to view its merchandise stock report");
            System.out.println();
            System.out.println("Store ID: ");

            input = scan.nextInt();
            scan.nextLine();

            if(input > 0){
                rs = MerchandiseSQL.storeMerchandiseStockReport(input);
                System.out.println("** Stock Report **");
                if(!rs.next()){
                    System.out.println("This Store Has No Merchandise");
                } else{
                  printMerchandise(rs);
                  rs.close();
                }

            } else if(input < 0){
                System.out.println("Invalid input");
            } else if(input == 0){
                System.out.println("Going back to Reports Menu");
                return;
            }
        } while(input != 0);
    }
    /**
     * Method displays information for a single product
     */
    public static void productStockReport() throws ClassNotFoundException, SQLException, ParseException {
        do{
            System.out.println();
            System.out.println("In order to go back to the stock report menu, enter 0");
            System.out.println("Please enter a product ID to view its report");
            System.out.println();
            System.out.println("Product ID: ");

            input = scan.nextInt();
            scan.nextLine();

            if(input > 0){
                rs = MerchandiseSQL.viewMerchandiseAllStores(input);
                System.out.println("** Stock Report for Product **");
                printMerchandise(rs);
                rs.close();

            } else if(input < 0){
                System.out.println("Invalid input");
            } else if(input == 0){
                System.out.println("Going back to Reports Menu");
                return;
            }
        } while(input != 0);
    }


    /**
     * Method adds a merchandise's information to database by calling the addMerchandise method in the MerchandiseSQL file
     */
    public static void addMerchandise(boolean maintain) throws ParseException, ClassNotFoundException, SQLException{
        //All the current attributes on the first iteration will be empty, as the user begins to fill in attributes
        //of the merchandise the display will be updated. Before the user selects 10, all the attributes must be filled
        //so that the merchandise can be created in the database.
        do{
            if(maintain == true){
                System.out.println("0. Go back to Maintain Inventory Menu: ");
            } else {
                System.out.println("0. Go back to Merchandise Menu: ");
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

            if(input == 0){
                System.out.println("Going back to previous menu");
                resetAttributes();
                return;
            }

            updateAttributes(input, "a", maintain);
            input = -1;
        } while(input != 0);
        rs.close();
    }
    /**
     * Method edits a merchandise's information to database by calling the editMerchandise in the MerchandiseSQL file
     */
    public static void editMerchandise() throws ParseException, ClassNotFoundException, SQLException{
        int input = 0;
        do{
            System.out.println("0. Go back to Merchandise Menu");
            System.out.println("1. Edit a Merchandise record");

            input = scan.nextInt();
            scan.nextLine();
            System.out.println();
            //productID must be a number greater than 0 so that the application can search for that merchandise.
            if(input > 0){
                System.out.println("Please enter Product ID: ");
                productID = scan.nextInt();

                System.out.println("Please enter Supplier ID: ");
                supplierID = scan.nextInt();

                System.out.println("Please enter Store ID: ");
                storeID = scan.nextInt();

                rs = MerchandiseSQL.viewMerchandise(productID, supplierID, storeID);
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
                        updateAttributes(input, "e", false);
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

    /**
     * This method is similar to the addMerchandise in the Merchandise Menu as it adds a new merchandise to the menu. The only
     * difference with this method is the prompt for the user and the sql query handling duplicates.
     * @throws ParseException
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static void newInventory() throws ParseException, ClassNotFoundException, SQLException{
        do {
            System.out.println("In order to go back to Maintain Inventory Menu, enter 0");
            System.out.println("Input new inventory into database, enter 1");
            input = scan.nextInt();
            if(input == 0){
                System.out.println("Going back to Maintain Inventory Menu");
                return;
            }
            else if(input == 1){
                addMerchandise(true);
            } else{
                System.out.println("Invalid input");
            }
        } while(input != 0);
    }

    /**
     * This method is called in the MainMenu file under Maintain Inventory. In order to return inventory into data base, the user will be prompted for 
     * the productID, supplierID, and storeID. When all information is obtained, it will go to returnInventory in MerchandiseSQL 
     * to complete the query
     */
    public static void returnInventory() throws ParseException, ClassNotFoundException, SQLException{
        do {
            int memberID = -1;
            int transactionID = -1;
            int transactionQuantity = -1;
            System.out.println("In order to go back to Maintain Inventory Menu, enter 0");
            System.out.println("Return inventory into database, enter 1");
            input = scan.nextInt();
            if(input == 0){
                System.out.println("Going back to Maintain Inventory Menu");
                return;
            } else if (input == 1){
                System.out.println("In order to begin the return, please enter a productID");
                productID = scan.nextInt();

                System.out.println("Enter Supplier ID:");
                supplierID = scan.nextInt();

                System.out.println("Enter Store ID:");
                storeID = scan.nextInt();

                System.out.println("Enter Member ID:");
                memberID = scan.nextInt();

                System.out.println("Enter Transaction ID:");
                transactionID = scan.nextInt();

                ResultSet rs = TransactionSQL.viewTransaction(transactionID);
                rs.next();
                transactionQuantity = rs.getInt("quantity");
                MerchandiseSQL.returnInventory(productID, supplierID, storeID, memberID, transactionID, transactionQuantity);

                resetAttributes();
            } else{
                System.out.println("Invalid input");
            }
        } while(input != 0);
    }

    /**
     * This method is called in the MainMenu file under Maintain Inventory. In order for products to be transfered from store to store,
     * the user will be prompted for a productID, supplierID, and storeID that will be transfered to. After wards it will retrieve all the information 
     * of the original store by using the productID. To execute the sql query it will go to MerchandiseSQL file under the method transferInventory.
     */
    public static void transferInventory() throws ParseException, ClassNotFoundException, SQLException{
        do {
            System.out.println("In order to go back to Maintain Inventory Menu, enter 0");
            System.out.println("Transfer inventory between stores, enter 1");
            input = scan.nextInt();

            int storeID2 = -1;
            int xferQuantity = -1;

            if(input == 0){
                System.out.println("Going back to Maintain Inventory Menu");
                return;
            } else if (input == 1){
                System.out.println("In order to begin the transfer, please enter a productID");
                productID = scan.nextInt();

                System.out.println("Enter Supplier ID:");
                supplierID = scan.nextInt();

                System.out.println("Enter Store ID you want to transfer the product FROM:");
                storeID = scan.nextInt();

                System.out.println("Enter Store ID you want to transfer the product TO:");
                storeID2 = scan.nextInt();
                
                System.out.println("Enter the quantity you want to transfer:");
                xferQuantity = scan.nextInt();

                rs = MerchandiseSQL.viewMerchandise(productID, supplierID, storeID);
                //Checks if viewMerchandise was able to find an existing Merchandise
                if(!rs.next()){
                    System.out.println("Merchandise does not exist");
                } else{
                    //Grabs the variables needed to pass to the sql query
                    name = rs.getString("name");
                    quantity = rs.getInt("quantity");
                    buyPrice =rs.getDouble("buyPrice");
                    marketPrice = rs.getDouble("marketPrice");
                    productionDate = rs.getDate("productionDate");
                    expiration = rs.getDate("expiration");
                }

                MerchandiseSQL.transferInventory(productID, storeID, name, quantity, buyPrice, marketPrice, productionDate, expiration, supplierID, storeID2, xferQuantity);

                resetAttributes();
            } else{
                System.out.println("Invalid input");
            }
        } while(input != 0);
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
     * specific attribute. If the maintain boolean is true, the addNewInventory query will be used to add merchandise to the 
     * database. If the maintain boolean is false, the addMerchandise query will be used to add merchandise.
     * Case 10 will take all the updated attributes and update the merchandise in the query. Afterwards, it will
     * reset all the variable fields to "" , 0, or false.
     * @param input
     * @param s
     */
    public static void updateAttributes(int input, String s, boolean maintain) throws ParseException, ClassNotFoundException, SQLException{
        switch(input){
            case 0:
                if(maintain == true){
                    System.out.println("0. Go back to Maintain Inventory Menu: ");
                } else {
                    System.out.println("0. Go back to Merchandise Menu: ");
                }
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
                System.out.println("Enter Production Date in format YYYY-MM-DD:");
                productionDate = Date.valueOf(scan.nextLine());
                break;
            case 8:
                System.out.println("Enter Expiration Date in format YYYY-MM-DD:");
                try{
                    expiration = Date.valueOf(scan.nextLine());
                } catch (IllegalArgumentException e) {
                    expiration = null;
                }
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
                        if(maintain == true){
                            MerchandiseSQL.addNewMerchandise(productID, storeID, name, quantity, buyPrice, marketPrice, productionDate, expiration, supplierID);
                        }
                        else{
                            MerchandiseSQL.addMerchandise(productID, storeID, name, quantity, buyPrice, marketPrice, productionDate, expiration, supplierID);

                        }
                    }
                } catch(SQLException e){
                    System.out.println("SQL Exception");
                    e.getStackTrace();
                    break;
                }
                rs = MerchandiseSQL.viewMerchandise(productID, supplierID, storeID);
                printMerchandise(rs);
                resetAttributes();
                break;
            default:
                System.out.println("Invalid input");
        }
    }
}
