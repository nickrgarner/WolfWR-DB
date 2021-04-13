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
        
        //This do while loop will reprompt the options that the application can do
        do{
            System.out.println("1. Store");
            System.out.println("2. Staff");
            System.out.println("3. Sign Up");
            System.out.println("4. Member");
            System.out.println("5. Supplier");
            System.out.println("6. Merchandise");
            System.out.println("7. Discount");
            System.out.println("8. Transaction");
            System.out.println("9. Exit");

            System.out.print("Please choose one of the options above: ");
            input = scan.nextInt();
            System.out.prinln();
            switch(input){
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    Member.memberMenu();
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    break;
                case 8:
                    break;
                case 9:
                    System.out.println("Exiting Application");
                    break;
                default:
                    System.out.println("Invalid input");
                    break;
            }
        } while(input != 9);

    }
}
