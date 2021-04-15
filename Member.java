import java.sql.*;
import java.util.*;
import java.sql.SQLException;
import java.text.ParseException;

public class Member{
    static Scanner scan = new Scanner(System.in);
    static int input = 0;
    static ResultSet rs = null;

    //Declaring and Instantiating all the attribute of a Member
    static int memberID = 0;
    static String firstName = "";
    static String lastName = "";
    static String level = "";
    static String email = "";
    static String phone = "";
    static String address = "";
    static boolean activeStatus = false;
    static double rewardAmount = 0.0;

    /**
     * This method is called in the MainMenu file when the user wants to add/edit/delete/view members in the database.
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws ParseException
     */
    public static void memberMenu() throws ClassNotFoundException, SQLException, ParseException{
        do{
            System.out.println("Member Menu");
            System.out.println();
            System.out.println("0. Return to Main Menu");
            System.out.println("1. View All Members");
            System.out.println("2. View Member by ID");
            System.out.println("3. Add Member");
            System.out.println("4. Edit Member");
            System.out.println("5. Delete Member");
            System.out.println();
            System.out.println("Please choose an option from the menu: ");

            input = scan.nextInt();

            switch(input){
                case 6:
                    System.out.println("Going back to Main Menu");
                    break;
                case 1:
                    viewAllMembers();
                    break;
                case 2:
                    viewMember();
                    break;
                case 3:
                    addMember();
                    break;
                case 4:
                    editMember();
                    break;
                case 5:
                    deleteMember();
                    break;
                default:
                    System.out.println("Invalid input");
                    break;
            }
        } while(input != 0); 
    }
    /**
     * Method displays all members in the database
     */
    public static void viewAllMembers() throws ClassNotFoundException, SQLException, ParseException{
        try{
            rs = MemberSQL.viewAllMembers();
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
                rs = MemberSQL.viewMember(input);
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
     * Method adds a member's information to database by calling the addMember in the MemberSQl file
     */
    public static void addMember() throws ParseException, ClassNotFoundException, SQLException{
        //All the current attributes on the first iteration will be empty, as the user begins to fill in attributes
        //of the member the display will be updated. Before the user selects 10, all the attributes must be filled
        //so that the member can be created in the database.
        do{
            System.out.println("0. Go back to Member Menu: ");
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
            System.out.println();
            System.out.println("Select a number to add to member information: ");

            input = scan.nextInt();
            scan.nextLine();

            updateAttributes(input, "a");
        } while(input != 0);
        rs.close();
    }
    /**
     * Method edits a member's information to database by calling the editMember in the MemberSQl file
     */
    public static void editMember() throws ParseException, ClassNotFoundException, SQLException{
        int input = 0;
        do{
            System.out.println("In order to go back to Member Menu, enter 0");
            System.out.println("Please enter a member ID to edit a member");

            input = scan.nextInt();
            scan.nextLine();
            System.out.println();
            //MemberID must be a number greater than 0 so that the application can search for that member.
            if(input > 0){
                rs = MemberSQL.viewMember(memberID);
                //Checks if viewMember was able to find an existing Member
                if(!rs.next()){
                    System.out.println("Member does not exist");
                } else{
                    //The member exist and all the attributes of that member are being stored into variables so 
                    //that they can be displayed to the user. In order for them to choose which attribute to edit.
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
                        //Attributes are displayed to the user and they must choose which one to edit.
                        //User will be reprompted these options until they decide to go back to Member Menu
                        System.out.println("0. Go back to Member Menu: ");
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
                        System.out.println();
                        System.out.println("Select a number to edit member information: ");
            
                        input = scan.nextInt();
                        scan.nextLine();
                        
                        updateAttributes(input, "e");
                    } while(input != 0);
                }
            }
        }while(input != 0);
    }
    /**
     * Method deletes a member's information in the database by calling the deleteMember in the MemberSQl file
     */
    public static void deleteMember(){
        do {
            System.out.println("In order to go back to Member Menu, enter 0");
            System.out.println("Please enter a member ID to delete a member");
            System.out.println();
            System.out.println("Member ID: ");

            input = scan.nextInt();

            if(input > 0){
                MemberSQL.deleteMember(input);
            } else if(input < 0){
                System.out.println("Invalid input");
            } else if (input ==0){
                System.out.println("Going back to Member Menu");
                break;
            }
        } while(input != 0);
    }
    /**
     * Method is used in all the methods above to print out the information of a member. This method takes in 
     * a ResultSet and then prints out all the attributes each member of the ResultSet.
     * @param rs
     * @throws SQLException
     */
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
    /**
    * This method is used to reset all the variables to "", 0, or false
    */
    public static void resetAttributes(){
        memberID = 0;
        firstName = "";
        lastName = "";
        level = "";
        email = "";
        phone = "";
        address = "";
        activeStatus = false;
        rewardAmount = 0.0;
    }
    /**
     * From option that the user selects, this switch statement will prompt the user for the new value for 
     * specific attribute.
     * Case 10 will take all the updated attributes and update the member in the query. Afterwards, it will
     * reset all the variable fields to "" , 0, or false.
     * @param input
     * @param s
     */
    public static void updateAttributes(int input, String s) throws ParseException, ClassNotFoundException, SQLException{
        switch(input){
            case 0:
                System.out.println("Going back to Member Menu");
                System.out.println();
                resetAttributes();
                break;
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
                try{
                    if(s.equals("e")){
                        MemberSQL.editMember(memberID, firstName, lastName, level, email, phone, address, activeStatus, rewardAmount);
                    }
                    else if(s.equals("a")){
                        MemberSQL.addMember(memberID, firstName, lastName, level, email, phone, address, activeStatus, rewardAmount);
                    }
                } catch(SQLException e){
                    System.out.println("SQL Exception");
                    e.getStackTrace();
                    break;
                }
                rs = MemberSQL.viewMember(memberID);
                printMember(rs);
                resetAttributes();
                break;
            default:
                System.out.println("Invalid input");
        }
    }
}