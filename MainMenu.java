import java.util.*;
import java.sql.SQLException;
import java.text.ParseException;

/**
 * This is the Main Menu that will be displayed when the application is ran. This menu will allow the user to
 * navigate through the different functionalities of application.
 */
public class MainMenu {
    static Scanner scan = new Scanner(System.in);
    static int input = -1;
    public static void initiate()
    {
        try {
            System.out.println("Welcome to our application for WolfWR, a WolfCity  wholesale store chain");
            System.out.println();

            //This do while loop will reprompt the Main Menu options that the application can do
            do{
                System.out.println("0. Exit");
                System.out.println("1. Information Processing (CRUD)");
                System.out.println("2. Maintain Inventory");
                System.out.println("3. Maintaining Billing and Transaction Records");
                System.out.println("4. Reports");

                System.out.print("Please choose one of the options above: ");
                input = scan.nextInt();
                System.out.println();
                switch(input){
                    case 0:
                        System.out.println("Exiting Application");
                        return;
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
                input = -1;
            } while(input != 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Information processing. Enter/update/delete basic
     * information about stores, customers, staff, and suppliers.
     * Manage promotion or sale information for products.???
     */
    public static void informationProcessMenu() throws ClassNotFoundException, ParseException, SQLException
    {
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
                    return;
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
                    break;
                default:
                    System.out.println("Invalid input");
                    break;
            }
            input = -1;
        } while(input != 0);
    }
    /**
     * Create inventory for newly arrived products.
     * Update inventory with returns. Manage product transfers
     * between stores in the chain.
     */
    public static void maintainInventoryMenu() throws ClassNotFoundException, ParseException, SQLException
    {
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
                    return;
                case 1:
                    Merchandise.newInventory();
                    break;
                case 2:
                    Merchandise.returnInventory();
                    break;
                case 3:
                    Merchandise.transferInventory();
                    break;

                default:
                    System.out.println("Invalid input");
                    break;
            }
            input = -1;
        } while(input != 0);
    }

    /**
     * Maintaining billing and transaction records. Create or generate bills
     * that are to be paid to a specific supplier.  Generate reward
     * checks for platinum customers that are due at the end of the year.
     * For each transaction, calculate the total price, check if any item is on sale or not and,
     * if it is, apply discounts according to the discount information.
     */
    public static void maintainBillingTransactions() throws ClassNotFoundException, ParseException, SQLException
    {
        do{
            System.out.println("Maintain Billing and Transactions Menu");
            System.out.println("0. Go back to Main Menu:");
            System.out.println("1. Generate Supplier Bill");
            System.out.println("2. Generate Member Reward Check");
            System.out.println("3. Calculate Transaction Totals");
            System.out.println("4. Check Transaction for Discounts");

            System.out.print("Please choose one of the options above: ");
            input = scan.nextInt();
            System.out.println();
            switch(input){
                case 0:
                    System.out.println("Going back to Main Menu");
                    return;
                case 1:
                    Supplier.createSupplierBill();
                    break;
                case 2:
                    Member.rewardCheck();
                    break;
                case 3:
                    Transaction.transactionTotal();
                    break;
                case 4:
                    Transaction.checkDiscounts();
                    break;
                default:
                    System.out.println("Invalid input");
                    break;
            }
            input = -1;
        } while(input != 0);
    }

    /**
     * Reports: Generate all of the following reports. General reports such
     * as total sales report by day, by month, or by year. Sales growth report
     * for a specific store for a given time period. Merchandise stock report for each
     * store or for a certain product. Customer growth report by month or by year.
     * Customer activity report such as total purchase amount for a given time period.
     */
    public static void reports() throws ClassNotFoundException, ParseException, SQLException
    {
        do{
            System.out.println("Reports Menu");
            System.out.println("0. Go back to Main Menu:");
            System.out.println("1. Total Sales Report by day, month, or year");
            System.out.println("2. Sales Growth Report for Specific Store");
            System.out.println("3. Merchandise Stock report");
            System.out.println("4. Customer Growth Report");
            System.out.println("5. Customer Purchase Report");

            System.out.print("Please choose one of the options above: ");
            input = scan.nextInt();
            System.out.println();
            switch(input){
                case 0:
                    System.out.println("Going back to Main Menu");
                    return;
                case 1:
                    Transaction.totalSales();
                    break;
                case 2:
                    Transaction.totalSales();
                    break;
                case 3:
                    Merchandise.viewStockReport();
                    break;
                case 4:
                    SignUp.memberGrowth();
                    break;
                case 5:
                    Transaction.totalSales();
                    break;

                default:
                    System.out.println("Invalid input");
                    break;
            }
            input = -1;
        } while(input != 0);
    }
}
