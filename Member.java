import java.sql.*;
import java.util.*;
import java.io.*;
import java.sql.SQLException;
import java.text.ParseException;

public class Member{
    static Scanner scan = new Scanner(System.in);
    static int input = 0;

    static String firstName = "";
    static String lastName = "";
    static String level = "";
    static String email = "";
    static String phone = "";
    static String address = "";
    static int memberID = 0;
    static double rewardAmount = 0.0;
    static boolean activeStatus = false;

    public static void memberMenu() throws ClassNotFoundException, SQLException, ParseException{
        do{
            System.out.println("Member Menu");
            System.out.println();
            System.out.println("1. View All Members");
            System.out.println("2. View Member by ID");
            System.out.println("3. Add Member");
            System.out.println("4. Edit Member");
            System.out.println("5. Delete Member");
            System.out.println("6. Return to Main Menu");
            System.out.println();
            System.out.println("Please choose an option from the menu: ");

            input = scan.nextInt();

            switch(input){
                case 1:
                    viewAllMembers();
                    break;
                case 2:
                    viewMember();
                    break;
                case 3:
                    add();
                    break;
                case 4:
                    edit();
                    break;
                case 5:
                    delete();
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
    public static void viewAllMembers(){

    }
    /**
     * Method displays information of a single member
     */
    public static void viewMember() throws ClassNotFoundException, SQLException, ParseException {
        do{
            System.out.println("In order to go back to Member Menu, enter 0");
            System.out.println("Please enter a member ID to view a member");
            System.out.println();
            System.out.println("Member ID: ");

            input = scan.nextInt();
            scan.nextLine();

            if(input > 0){

            } else if(input < 0){
                System.out.println("Invalid input");
            } else if(input == 0){
                System.out.println("Going back to Member Menu");
                break;
            }
        } while(selection != 0);
    }
    /**
     * Method adds a member's information to database
     */
    public static void add() throws ParseException, ClassNotFoundException, SQLException{
        
        do{
            System.out.println("1. First Name: " + firstName);
            System.out.println("2. Last Name: " + lastName);
            System.out.println("3. Level: " + level);
            System.out.println("4. Email: " + email);
            System.out.println("5. Phone: " + phone);
            System.out.println("6. Address: " + address);
            System.out.println("7. Member ID: " + memberID);
            System.out.println("8. Reward Amount: " + rewardAmount);
            System.out.println("9. Active Status: " + activeStatus);
            System.out.println("10. Go back to menu: ");
            System.out.println();
            System.out.println("Select a number to add to member information: ");

            input = scan.nextInt();
            scan.nextLine();

            switch(input){
                case 1:
                    System.out.println("Enter First Name:");
                    firstName = scan.nextLine();
                    break;
                case 2:
                    System.out.println("Enter Last Name:");
                    lastName = scan.nextLine();
                    break;
                case 3:
                    System.out.println("Enter Level:");
                    level = scan.nextLine();
                    break;
                case 4:
                    System.out.println("Enter Email:");
                    email = scan.nextLine();
                    break;
                case 5:
                    System.out.println("Enter Phone:");
                    phone = scan.nextLine();
                    break;
                case 6:
                    System.out.println("Enter Address:");
                    address = scan.nextLine();
                    break;
                case 7:
                    System.out.println("Enter Member ID:");
                    memberID = scan.nextLine();
                    break;
                case 8:
                    System.out.println("Enter Reward Amount:");
                    rewardAmount = scan.nextLine();
                    break;
                case 9:
                    System.out.println("Enter True or False for Active Status:");
                    activeStatus = scan.nextLine();
                    break;
                case 10:
                    System.out.println("Going back to menu");
                    System.out.println();
                    firstName = "";
                    lastName = "";
                    level = "";
                    email = "";
                    phone = "";
                    address = "";
                    memberID = 0;
                    rewardAmount = 0.0;
                    activeStatus = false;
                    break;
                default:
                    System.out.println("Invalid input");
            }
        } while(input != 10);
    }
    /**
     * Method edits a member's information
     */
    public static void edit() throws ParseException, ClassNotFoundException, SQLException{

    }
    /**
     * Method deletes a member by id
     */
    public static void delete(){
        do {
            System.out.println("In order to go back to Member Menu, enter 0");
            System.out.println("Please enter a member ID to delete a member");
            System.out.println();
            System.out.println("Member ID: ");

            input = scan.nextInt();
            if(input > 0){

            } else if(input < 0){
                System.out.println("Invalid input");
            } else if (input ==0){
                System.out.println("Going back to Member Menu");
                break;
            }
        } while(selection != 0);
    }


}