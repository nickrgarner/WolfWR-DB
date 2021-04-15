import java.sql.*;
import java.util.*;
import java.io.*;
import java.sql.SQLException;
import java.text.ParseException;

public class SignUp {
    static Scanner scan = new Scanner(System.in);
    static int input = 0;
    static ResultSet rs = null;

    //Declaring and Instantiating all the attribute of a SignUp
    static int storeID = 0;
    static Date signUpDate = 0001-01-01;
    static int staffID = 0;
    static int memberID = 0;

    /**
     * This method is called in the MainMenu file when the user wants to add/edit/delete/view members in the database.
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws ParseException
     */
    public static void signUpMenu() throws ClassNotFoundException, SQLException, ParseException{
        do{
            System.out.println("SignUp Menu");
            System.out.println();
            System.out.println("0. Return to Main Menu");
            System.out.println("1. View All SignUps");
            System.out.println("2. View SignUp by ID");
            System.out.println("3. Add SignUp");
            System.out.println("4. Edit SignUp");
            System.out.println("5. Delete SignUp");
            System.out.println();
            System.out.println("Please choose an option from the menu: ");

            input = scan.nextInt();

            switch(input){
                case 6:
                    System.out.println("Going back to Main Menu");
                    break;
                case 1:
                    viewAllSignUps();
                    break;
                case 2:
                    viewSignUp();
                    break;
                case 3:
                    addSignUp();
                    break;
                case 4:
                    editSignUp();
                    break;
                case 5:
                    deleteSignUp();
                    break;
                default:
                    System.out.println("Invalid input");
                    break;
            }
        } while(input != 0); 
    }

    /**
     * Method displays all SignUps in the database
     */
    public static void viewAllSignUps() throws ClassNotFoundException, SQLException, ParseException{
        try{
            rs = SignUpSQL.viewAllSignUps();
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
    public static void viewSignUp() throws ClassNotFoundException, SQLException, ParseException {
        do{
            System.out.println("In order to go back to SignUp Menu, enter 0");
            System.out.println("Please enter a member ID to view a SignUp");
            System.out.println();
            System.out.println("Member ID: ");

            input = scan.nextInt();
            scan.nextLine();

            if(input > 0){
                rs = SignUpSQL.viewSignUp(input);
                printMember(rs);
                rs.close();

            } else if(input < 0){
                System.out.println("Invalid input");
            } else if(input == 0){
                System.out.println("Going back to SignUp Menu");
                break;
            }
        } while(input != 0);
    }

    /**
     * Method adds a SignUp information to database by calling the addSignUp in the SignUpSQl file
     */
    public static void addSignUp() throws ParseException, ClassNotFoundException, SQLException{
        //All the current attributes on the first iteration will be empty, as the user begins to fill in attributes
        //of the SignUp the display will be updated. Before the user selects 5, all the attributes must be filled
        //so that the SignUp can be created in the database.
        do{
            System.out.println("0. Go back to Member Menu: ");
            System.out.println("1. Store ID: " + memberID);
            System.out.println("2. Sign Up Date: " + signUpDate);
            System.out.println("3. Staff ID: " + staffID);
            System.out.println("4. Member ID: " + memberID);
            System.out.println("5. After all attributes have been entered, select 5 to add SignUp:");
            System.out.println();
            System.out.println("Select a number to add SignUp information: ");

            input = scan.nextInt();
            scan.nextLine();

            updateAttributes(input, "a");
        } while(input != 0);
        rs.close();
    }

    /**
     * Method edits a member's information to database by calling the editMember in the MemberSQl file
     */
    public static void editSignUp() throws ParseException, ClassNotFoundException, SQLException{
        int input = 0;
        do{
            System.out.println("In order to go back to SignUp Menu, enter 0");
            System.out.println("Please enter a member ID to edit a SignUp");

            input = scan.nextInt();
            scan.nextLine();
            System.out.println();
            //MemberID must be a number greater than 0 so that the application can search for that member.
            if(input > 0){
                rs = SignUpSQL.viewSignUp(memberID);
                //Checks if viewSignUp was able to find an existing SignUp
                if(!rs.next()){
                    System.out.println("SignUp does not exist");
                } else{
                    //The SignUp exist and all the attributes of that SignUp are being stored into variables so 
                    //that they can be displayed to the user. In order for them to choose which attribute to edit.
                    storeID = rs.getInt("storeID");
                    signUpDate = rs.getDate("signUpDate"); 
                    staffID = rs.getInt("staffID");
                    memberID = rs.getInt("memberID");

                    do{
                        //Attributes are displayed to the user and they must choose which one to edit.
                        //User will be reprompted these options until they decide to go back to SignUp Menu
                        System.out.println("0. Go back to Member Menu: ");
                        System.out.println("1. Store ID: " + memberID);
                        System.out.println("2. Sign Up Date: " + signUpDate);
                        System.out.println("3. Staff ID: " + staffID);
                        System.out.println("4. Member ID: " + memberID);
                        System.out.println("5. After all attributes have been entered, select 5 to edit SignUp:");
                        System.out.println();
                        System.out.println("Select a number to edit SignUp information: ");
            
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
    public static void deleteSignUp(){
        do {
            System.out.println("In order to go back to SignUp Menu, enter 0");
            System.out.println("Please enter a member ID to delete a SignUp");
            System.out.println();
            System.out.println("Member ID: ");

            input = scan.nextInt();

            if(input > 0){
                SignUpSQL.deleteSignUp(input);
            } else if(input < 0){
                System.out.println("Invalid input");
            } else if (input ==0){
                System.out.println("Going back to SignUp Menu");
                break;
            }
        } while(input != 0);
    }

    /**
     * Method is used in all the methods above to print out the information of a SignUp. This method takes in 
     * a ResultSet and then prints out all the attributes each SignUp of the ResultSet.
     * @param rs
     * @throws SQLException
     */
    public static void printSignUp(ResultSet rs) throws SQLException{
        if(!rs.next()){
            System.out.println("There is no SignUp with this memberID");
        } else{
            do{
                storeID = rs.getInt("storeID");
                signUpDate = rs.getDate("signUpDate");
                staffID = rs.getInt("staffID");
                memberID = rs.getInt("memberID");
                
                System.out.println("Store ID: " + storeID + ", Sign Up Date: " + signUpDate + ", Staff ID: " + staffID + ", Member ID: "
                                    + memberID );
            } while(rs.next());
            
        }
    }

    /**
    * This method is used to reset all the variables to "", 0, or false
    */
    public static void resetAttributes(){
        int storeID = 0;
        Date signUpDate = 0001-01-01;
        int staffID = 0;
        int memberID = 0;
    }

    /**
     * From option that the user selects, this switch statement will prompt the user for the new value for 
     * specific attribute.
     * Case 5 will take all the updated attributes and update the SignUp in the query. Afterwards, it will
     * reset all the variable fields to "" , 0, or false.
     * @param input
     * @param s
     */
    public static void updateAttributes(int input, String s) throws ParseException, ClassNotFoundException, SQLException{
        switch(input){
            case 0:
                System.out.println("Going back to SignUp Menu");
                System.out.println();
                resetAttributes();
                break;
            case 1:
                System.out.println("Enter Store ID:");
                memberID = scan.nextInt();
                break;
            case 2:
                System.out.println("Enter Sign Up Date:");
                firstName = scan.nextLine();
                break;
            case 3:
                System.out.println("Enter Staff ID:");
                lastName = scan.nextLine();
                break;
            case 4:
                System.out.println("Enter Member ID:");
                level = scan.nextLine();
                break;
            case 5:
                try{
                    if(s.equals("e")){
                        SignUpSQL.editSignUp(storeID, signUpDate, staffID, memberID);
                    }
                    else if(s.equals("a")){
                        SignUpSQL.addSignUp(storeID, signUpDate, staffID, memberID);
                    }
                } catch(SQLException e){
                    System.out.println("SQL Exception");
                    e.getStackTrace();
                    break;
                }
                rs = SignUpSQL.viewSignUp(memberID);
                printSignUp(rs);
                resetAttributes();
                break;
            default:
                System.out.println("Invalid input");
        }
    }
}
