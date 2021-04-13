import java.sql.*;
import java.util.*;
import java.io.*;
import java.sql.SQLException;
import java.text.ParseException;

public class Merchandise {
    static Scanner scan = new Scanner(System.in);
    static int input = 0;
    static ResultSet rs = null;

    //Declaring and Instantiating all the attribute of a Merchandise
    static int productID = 0;
    static int storeID = 0;
    static String name = "";
    static int quantity = 0;
    static double buyPrice = 0.0;
    static double marketPrice = 0.0;
    static Date productionDate = 0000-00-00;
    static Date expiration = 0000-00-00;
    static int supplierID = 0;

    public static void merchandiseMenu() throws ClassNotFoundException, SQLException, ParseException{
        do{
            System.out.println("Merchandise Menu");
            System.out.println();
            System.out.println("1. View All Merchandise");
            System.out.println("2. View Merchandise by ID");
            System.out.println("3. Add Merchandise");
            System.out.println("4. Edit Merchandise");
            System.out.println("5. Delete Merchandise");
            System.out.println("6. Return to Main Menu");
            System.out.println();
            System.out.println("Please choose an option from the menu: ");

            input = scan.nextInt();

            switch(input){
                case 1:
                    viewAllMerchandise();
                    break;
                case 2:
                    viewMerchandise();
                    break;
                case 3:
                    addMerchandise();
                    break;
                case 4:
                    editMerchandise();
                    break;
                case 5:
                    deleteMerchandise();
                    break;
                case 6:
                    System.out.println("Going back to Main Menu");
                    break;
                default:
                    System.out.println("Invalid input");
                    break;
            }
        } while(input != 6); 
    }

    /**
     * Method displays all members in the database
     */
    public static void viewAllMerchandise() throws ClassNotFoundException, SQLException, ParseException{
        
    }
    /**
     * Method displays information of a single member
     */
    public static void viewMerchandise() throws ClassNotFoundException, SQLException, ParseException {
        
    }
    /**
     * Method adds a member's information to database by calling the addMember in the MemberSQl file
     */
    public static void addMerchandise() throws ParseException, ClassNotFoundException, SQLException{
        
    }
    /**
     * Method edits a member's information to database by calling the editMember in the MemberSQl file
     */
    public static void editMerchandise() throws ParseException, ClassNotFoundException, SQLException{
        
    }
    /**
     * Method deletes a member's information in the database by calling the deleteMember in the MemberSQl file
     */
    public static void deleteMerchandise(){
        
    }

    /**
     * Method is used in all the methods above to print out the information of a member. This method takes in 
     * a ResultSet and then prints out all the attributes each member of the ResultSet.
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
        productionDate = 0000-00-00;
        expiration = 0000-00-00;
        supplierID = 0;
    }
}
