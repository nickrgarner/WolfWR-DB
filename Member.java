import java.sql.*;
import java.util.*;
import java.io.*;
import java.sql.SQLException;
import java.text.ParseException;

public class Member{
    static Scanner scan = new Scanner(System.in);
    static int input = 0;

    static int memberID = 0;
    static String firstName = "";
    static String lastName = "";
    static String level = "";
    static String email = "";
    static String phone = "";
    static String address = "";
    static boolean activeStatus = false;
    static double rewardAmount = 0.0;


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
    public static void viewAllMembers() throws ClassNotFoundException, SQLException, ParseException{
        try{
            ResultSet rs = MemberSQL.viewAllMembers();
            printMember(rs);
        }
        catch(SQLException e){
            System.out.println("SQL Exception");
            e.getStackTrace();
        }
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
                ResultSet rs = MemberSQL.viewMember(input);
                printMember(rs);
                rs.close();

            } else if(input < 0){
                System.out.println("Invalid input");
            } else if(input == 0){
                System.out.println("Going back to Member Menu");
                break;
            }
        } while(input != 0);
    }
    /**
     * Method adds a member's information to database
     */
    public static void add() throws ParseException, ClassNotFoundException, SQLException{
        
        do{
            System.out.println("1. Member ID: " + memberID);
            System.out.println("2. First Name: " + firstName);
            System.out.println("3. Last Name: " + lastName);
            System.out.println("4. Level: " + level);
            System.out.println("5. Email: " + email);
            System.out.println("6. Phone: " + phone);
            System.out.println("7. Address: " + address);
            System.out.println("8. Active Status: " + activeStatus);
            System.out.println("9. Reward Amount: " + rewardAmount);
            System.out.println("10. After all attributes have been entered, select 10 to create the new member:");
            System.out.println("11. Go back to menu: ");
            System.out.println();
            System.out.println("Select a number to add to member information: ");

            input = scan.nextInt();
            scan.nextLine();

            switch(input){
                case 1:
                    System.out.println("Enter Member ID:");
                    memberID = scan.nextInt();
                    break;
                case 2:
                    System.out.println("Enter First Name:");
                    firstName = scan.nextLine();
                    break;
                case 3:
                    System.out.println("Enter Last Name:");
                    lastName = scan.nextLine();
                    break;
                case 4:
                    System.out.println("Enter Level:");
                    level = scan.nextLine();
                    break;
                case 5:
                    System.out.println("Enter Email:");
                    email = scan.nextLine();
                    break;
                case 6:
                    System.out.println("Enter Phone:");
                    phone = scan.nextLine();
                    break;
                case 7:
                    System.out.println("Enter Address:");
                    address = scan.nextLine();
                    break;
                case 8:
                    System.out.println("Enter True or False for Active Status:");
                    activeStatus = Boolean.parseBoolean(scan.nextLine());
                    break;
                case 9:
                    System.out.println("Enter Reward Amount:");
                    rewardAmount = scan.nextDouble();
                    break;
                case 10:
                    MemberSQL.addMember(memberID, firstName, lastName, level, email, phone, address, activeStatus, rewardAmount);
                    ResultSet rs = MemberSQL.viewMember(memberID);
                    printMember(rs);
                    memberID = 0;
                    firstName = "";
                    lastName = "";
                    level = "";
                    email = "";
                    phone = "";
                    address = "";
                    activeStatus = false;
                    rewardAmount = 0.0;
                    break;
                case 11:
                    System.out.println("Going back to menu");
                    System.out.println();
                    memberID = 0;
                    firstName = "";
                    lastName = "";
                    level = "";
                    email = "";
                    phone = "";
                    address = "";
                    activeStatus = false;
                    rewardAmount = 0.0;
                    break;
                default:
                    System.out.println("Invalid input");
            }
        } while(input != 11);
    }
    /**
     * Method edits a member's information
     */
    public static void edit() throws ParseException, ClassNotFoundException, SQLException{
        System.out.println("In order to go back to Member Menu, enter 0");
        System.out.println("Please enter a member ID to edit a member");

        input = scan.nextInt();
        scan.nextLine();
        System.out.println();
        if(input > 0){
            ResultSet rs = MemberSQL.viewMember(memberID);
            if(!rs.next()){
                System.out.println("Member does not exist");
            } else{
                memberID = rs.getInt("memberID");
                firstName = rs.getString("firstName"); 
                lastName = rs.getString("lastName");
                level = rs.getString("level");
                email =rs.getString("email");
                phone = rs.getString("phone");
                address = rs.getString("address");
                activeStatus = rs.getBoolean("activeStatus");
                rewardAmount = rs.getDouble("rewardAmount");
                do{
                    System.out.println("1. Member ID: " + memberID);
                    System.out.println("2. First Name: " + firstName);
                    System.out.println("3. Last Name: " + lastName);
                    System.out.println("4. Level: " + level);
                    System.out.println("5. Email: " + email);
                    System.out.println("6. Phone: " + phone);
                    System.out.println("7. Address: " + address);
                    System.out.println("8. Active Status: " + activeStatus);
                    System.out.println("9. Reward Amount: " + rewardAmount);
                    System.out.println("10. After all attributes have been entered, select 10 to edit member:");
                    System.out.println("11. Go back to menu: ");
                    System.out.println();
                    System.out.println("Select a number to edit to member information: ");
        
                    input = scan.nextInt();
                    scan.nextLine();
        
                    switch(input){
                        case 1:
                            System.out.println("Enter Member ID:");
                            memberID = scan.nextInt();
                            break;
                        case 2:
                            System.out.println("Enter First Name:");
                            firstName = scan.nextLine();
                            break;
                        case 3:
                            System.out.println("Enter Last Name:");
                            lastName = scan.nextLine();
                            break;
                        case 4:
                            System.out.println("Enter Level:");
                            level = scan.nextLine();
                            break;
                        case 5:
                            System.out.println("Enter Email:");
                            email = scan.nextLine();
                            break;
                        case 6:
                            System.out.println("Enter Phone:");
                            phone = scan.nextLine();
                            break;
                        case 7:
                            System.out.println("Enter Address:");
                            address = scan.nextLine();
                            break;
                        case 8:
                            System.out.println("Enter True or False for Active Status:");
                            activeStatus = Boolean.parseBoolean(scan.nextLine());
                            break;
                        case 9:
                            System.out.println("Enter Reward Amount:");
                            rewardAmount = scan.nextDouble();
                            break;
                        case 10:
                            MemberSQL.editMember(memberID, firstName, lastName, level, email, phone, address, activeStatus, rewardAmount);
                            rs = MemberSQL.viewMember(memberID);
                            printMember(rs);
                            memberID = 0;
                            firstName = "";
                            lastName = "";
                            level = "";
                            email = "";
                            phone = "";
                            address = "";
                            activeStatus = false;
                            rewardAmount = 0.0;
                            break;
                        case 11:
                            System.out.println("Going back to menu");
                            System.out.println();
                            memberID = 0;
                            firstName = "";
                            lastName = "";
                            level = "";
                            email = "";
                            phone = "";
                            address = "";
                            activeStatus = false;
                            rewardAmount = 0.0;
                            break;
                        default:
                            System.out.println("Invalid input");
                    }
                } while(input != 11);
            }
        }
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
        } while(input != 0);
    }

    public static void printMember(ResultSet rs) throws SQLException{
        if(!rs.next()){
            System.out.println("There is no member with this memberID");
        } else{
            do{
                memberID = rs.getInt("memberID");
                firstName = rs.getString("firstName");
                lastName = rs.getString("lastName");
                level = rs.getString("level");
                email = rs.getString("email");
                phone = rs.getString("phone");
                address = rs.getString("address");
                activeStatus = rs.getBoolean("activeStatus");
                rewardAmount = rs.getDouble("rewardAmount");
                System.out.println("Member ID: " + memberID + ", first name: " + firstName + ", last name: " + lastName + ", level: "
                                    + level + ", email: " + email + ", phone: " + phone + ", address: " + address 
                                    + ", active status: "+ activeStatus + ", reward amount: " + rewardAmount);
            } while(rs.next());
            
        }
    }

}