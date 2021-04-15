import java.util.*;
import java.io.*;
import java.sql.SQLException;
import java.text.ParseException;

/**
 * This is the Main Menu that will be displayed when the application is ran. This menu will allow the user to 
 * navigate through the different functionalities of application.
 */
public class MainMenu {
    static Scanner scan = new Scanner(System.in);
    static int input = 0;
    public static void main(String[] args) throws ClassNotFoundException, SQLException, ParseException {
        System.out.println("Welcome to our application for WolfWR, a WolfCity  wholesale store chain");
        System.out.println();
        
        //This do while loop will reprompt the Main Menu options that the application can do
        do{
            System.out.println("0. Exit");
            System.out.println("1. Information Processing");
            System.out.println("2. Maintain Inventory");
            System.out.println("3. Maintaining Billing and Transaction Records");
            System.out.println("4. Reports");

            System.out.print("Please choose one of the options above: ");
            input = scan.nextInt();
            System.out.println();
            switch(input){
                case 0:
                    System.out.println("Exiting Application");
                    break;
                case 1:
                    informationProcessMenu();
                    break;
                case 2:
                    maintainInventoryMenu();
                    break;
                case 3:
                    maintainBillingTransactions();
                    break;
                case 4:
                    reports();
                    break;
                default:
                    System.out.println("Invalid input");
                    break;
            }
        } while(input != 0);
    }

    /**
     * Information processing. Enter/update/delete basic 
     * information about stores, customers, staff, and suppliers. 
     * Manage promotion or sale information for products.”
     */
    public static void informationProcessMenu(){
        do{
            System.out.println("Information Processing Menu");
            System.out.println("0. Go back to Main Menu:");
            System.out.println("1. Store");
            System.out.println("2. Staff");
            System.out.println("3. Sign Up");
            System.out.println("4. Member");
            System.out.println("5. Supplier");
            System.out.println("6. Discount");
            System.out.println("7. Merchandise");
            System.out.println("8. Transaction");

            System.out.print("Please choose one of the options above: ");
            input = scan.nextInt();
            System.out.println();
            switch(input){
                case 0:
                    System.out.println("Going back to Main Menu");
                    break;
                case 1:
                    Store.storeMenu();
                    break;
                case 2:
                    Staff.staffMenu();
                    break;
                case 3:
                    SignUp.signUpMenu();
                    break;
                case 4:
                    Member.memberMenu();
                    break;
                case 5:
                    Supplier.supplierMenu();
                    break;
                case 6:
                    Discount.discountMenu();
                    break;
                case 7:
                    Merchandise.merchandiseMenu();
                    break;
                case 8:
                    Transaction.transactionMenu();
                default:
                    System.out.println("Invalid input");
                    break;
            }
        } while(input != 0);
    }
    /**
     * Create inventory for newly arrived products. 
     * Update inventory with returns. Manage product transfers 
     * between stores in the chain.
     */
    public static void maintainInventoryMenu(){
        do{
            System.out.println("Maintain Inventory Menu");
            System.out.println("0. Go back to Main Menu:");
            System.out.println("1. Newly Arrived Products");
            System.out.println("2. Returns");
            System.out.println("3. Product Transfer Between Stores");

            System.out.print("Please choose one of the options above: ");
            input = scan.nextInt();
            System.out.println();
            switch(input){
                case 0:
                    System.out.println("Going back to Main Menu");
                    break;
                case 1:
                    //Make a method for this operation       
                        //INSERT INTO Merchandise
                        Merchandise.merchandiseAdd();
                        //UPDATE Supplier with SET
                                 
                    break;
                case 2:
                    //Make method for this operation
                    
                        //UPDATE Merchandise with SET
                        //UPDATE Member with SET
                        //DELETE FROM Transaction

                    break;
                case 3:
                    //Make method for this operation

                        //UPDATE Merchandise with SET
                        //INSERT INTO Merchandise
                    Merchandise.addMerchandise();
                    break;
                
                default:
                    System.out.println("Invalid input");
                    break;
            }
        } while(input != 0);
    }
    /**
     * Maintaining billing and transaction records. Create or generate bills 
     * that are to be paid to a specific supplier.  Generate reward 
     * checks for platinum customers that are due at the end of the year. 
     * For each transaction, calculate the total price, check if any item is on sale or not and, 
     * if it is, apply discounts according to the discount information.
     */
    public static void maintainBillingTransactions(){
        do{
            System.out.println("Maintain Billing and Transactions Menu");
            System.out.println("0. Go back to Main Menu:");
            System.out.println("1. Create or generate bills that are to be paid to a specific supplier");
            System.out.println("2. Generate reward checks for platinum customers that are due at the end of the year");
            System.out.println("3. For each transaction, calculate the total price");
            System.out.println("4. Check if any item is on sale or not");
            System.out.println("5. If item is on sale, apply discounts according to the discount information.");
            
            System.out.print("Please choose one of the options above: ");
            input = scan.nextInt();
            System.out.println();
            switch(input){
                case 0:
                    System.out.println("Going back to Main Menu");
                    break;
                case 1:
                    //Make a method for this operation       
                        //SELECT amountOweed FROM Supplier WHERE Supplier = ?
                                 
                    break;
                case 2:
                    //Make method for this operation
                        //SELECT rewardAmount FROM Member Where MemberID = ?

                    break;
                case 3:
                    //Make method for this operation
                        //SELECT SUM(total) FROM Transaction WHERE transactionID = ?

                    break;
                case 4:
                    //Make method for this operation
                        //SELECT Transaction.transactinID, Transaction.productID, Discount.priceReduction
                        //FROM Transaction
                        //INNER JOIN Discount
                        //ON Transaction.productID = Discount.productID
                        //WHERE Transaction.transactionID = ?
                        //AND Discount.startDate <= Transaction.date
                        //AND Discount.endDate >= Transaction.date;
                    
                    break;
                case 5:
                    //Make method for this operation
                        //UPDATE Transaction
                        //SET total = total - quantity * 10.00
                        //WHERE transactionID = ?
                        //AND productID = ?
                    
                    break;
                
                default:
                    System.out.println("Invalid input");
                    break;
            }
        } while(input != 0);
    }

    /**
     * Reports: Generate all of the following reports. General reports such 
     * as total sales report by day, by month, or by year. Sales growth report 
     * for a specific store for a given time period. Merchandise stock report for each 
     * store or for a certain product. Customer growth report by month or by year. 
     * Customer activity report such as total purchase amount for a given time period.
     */
    public static void reports(){
        do{
            System.out.println("Reports Menu");
            System.out.println("0. Go back to Main Menu:");
            System.out.println("1. General reports such as total sales report by day, by month, or by year");
            System.out.println("2. Sales growth report for a specific store for a given time period");
            System.out.println("3. Merchandise stock report for each store or for a certain product");
            System.out.println("4. Customer growth report by month or by year");
            System.out.println("5. Customer activity report such as total purchase amount for a given time period");
            
            System.out.print("Please choose one of the options above: ");
            input = scan.nextInt();
            System.out.println();
            switch(input){
                case 0:
                    System.out.println("Going back to Main Menu");
                    break;
                case 1:
                    //Make a method for this operation       
                        // SELECT SUM(total)
                        // FROM Transaction
                        // WHERE date=(SELECT CURDATE());

                        // SELECT SUM(total)
                        // FROM Transaction
                        // WHERE MONTH(date)=MONTH((SELECT CURDATE()))
                        // AND YEAR(date)=YEAR((SELECT CURDATE()));

                        // SELECT SUM(total)
                        // FROM Transaction
                        // WHERE YEAR(date)=YEAR((SELECT CURDATE()));
                                 
                    break;
                case 2:
                    //Make method for this operation
                        // SELECT SUM(total)
                        // FROM Transaction
                        // WHERE date=(SELECT CURDATE())
                        // AND storeID=?;

                        // SELECT SUM(total)
                        // FROM Transaction
                        // WHERE MONTH(date)=MONTH((SELECT CURDATE()))
                        // AND YEAR(date)=YEAR((SELECT CURDATE()))
                        // AND storeID=?;

                        // SELECT SUM(total)
                        // FROM Transaction
                        // WHERE YEAR(date)=YEAR((SELECT CURDATE()))
                        // AND storeID=?;

                    break;
                case 3:
                    //Make method for this operation
                        // SELECT *
                        // FROM Merchandise
                        // WHERE storeID=1;

                        // SELECT *
                        // FROM Merchandise
                        // WHERE productID=3;

                    break;
                case 4:
                    //Make method for this operation
                        // SELECT COUNT(memberID)
                        // FROM SignUp
                        // WHERE MONTH(signUpDate)=MONTH((SELECT CURDATE()))
                        // AND YEAR(signUpDate)=YEAR((SELECT CURDATE()));

                        // SELECT COUNT(memberID)
                        // FROM SignUp
                        // WHERE YEAR(signUpDate)=YEAR((SELECT CURDATE()));
                    
                    break;
                case 5:
                    //Make method for this operation
                        // SELECT SUM(total)
                        // FROM Transaction
                        // WHERE memberID=3
                        // AND YEAR(date)=YEAR((SELECT CURDATE));
                    
                    break;
                
                default:
                    System.out.println("Invalid input");
                    break;
            }
        } while(input != 0);
    }
}
