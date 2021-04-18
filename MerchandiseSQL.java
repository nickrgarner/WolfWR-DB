import java.sql.*;
import java.sql.Date;
import java.text.ParseException;
import java.sql.SQLException;

public class MerchandiseSQL {

    //This connects to the database by calling the login file
    static Connection connection = Login.connection;
    static Statement statement = Login.statement;
    static ResultSet result = Login.result;

    /**
     * Method is called in Merchandise file where user wants to see all the Merchandise in the database. This method
     * will perform the sql statement and generate a result set of all the Merchandise in the database.
     * @return ResultSet
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws ParseException
     */
    public static ResultSet viewAllMerchandise() throws ClassNotFoundException, SQLException, ParseException{
        //A table of data representing a database result set, which is usually generated by executing a
        //statement that queries the database.
        ResultSet returnSet = null;
        //Object that represents a precompiled SQL statement
        PreparedStatement ps = null;

        try {
            ps = connection.prepareStatement("SELECT * FROM Merchandise;");
            returnSet = ps.executeQuery();
            ps.close();
        } catch (SQLException e) {
            System.out.println("SQL Exception");
            System.out.println(e.getStackTrace());
            return null;
        }
        return returnSet;
    }

    /**
     * Method is called in the Merchandise file and is used to find a specific merchandise.
     * @param productID
     * @return ResultSet
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws ParseException
     */
    public static ResultSet storeMerchandiseStockReport(int storeID) throws ClassNotFoundException, SQLException, ParseException{
        //A table of data representing a database result set, which is usually generated by executing a
        //statement that queries the database.
        ResultSet returnSet = null;
        //Object that represents a precompiled SQL statement
        PreparedStatement ps = null;

        try {
            ps = connection.prepareStatement("SELECT * FROM Merchandise WHERE storeID = ?;");
            ps.setInt(1, storeID);
            returnSet = ps.executeQuery();
            ps.close();
        } catch (SQLException e) {
            System.out.println("SQL Exception");
            System.out.println(e.getStackTrace());
            return null;
        }
        return returnSet;
    }
    /**
     * Method is called in the Merchandise file and is used to find a specific merchandise.
     * @param productID
     * @return ResultSet
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws ParseException
     */
    public static ResultSet viewMerchandise(int productID) throws ClassNotFoundException, SQLException, ParseException{
        //A table of data representing a database result set, which is usually generated by executing a
        //statement that queries the database.
        ResultSet returnSet = null;
        //Object that represents a precompiled SQL statement
        PreparedStatement ps = null;

        try {
            ps = connection.prepareStatement("SELECT * FROM Merchandise WHERE productID = ?;");
            ps.setInt(1, productID);
            returnSet = ps.executeQuery();
            ps.close();
        } catch (SQLException e) {
            System.out.println("SQL Exception");
            System.out.println(e.getStackTrace());
            return null;
        }
        return returnSet;
    }

    /**
     * Method is called from Merchandise file and is called after the user has finished filling all the attributes for a Merchandise
     * This method also adds the merchandise to the suppliers total amount Owed.
     * @param productID
     * @param storeID
     * @param name
     * @param quantity
     * @param buyPrice
     * @param marketPrice
     * @param productionDate
     * @param expiration
     * @param supplierID
     * @throws SQLException
     * @throws ParseException
     */
    static void addMerchandise(int productID, int storeID, String name, int quantity, double buyPrice, double marketPrice, Date productionDate, Date expiration, int supplierID) throws SQLException, ParseException{
        //Object that represents a precompiled SQL statement
        PreparedStatement ps = null;
        int id = 0;
        try{
            ps = connection.prepareStatement("INSERT INTO Merchandise (productID, storeID, name, quantity, buyPrice, marketPrice, productionDate, expiration, supplierID) VALUES (?,?,?,?,?,?,?,?,?);");
            ps.setInt(1,productID);
            ps.setInt(2,storeID);
            ps.setString(3,name);
            ps.setInt(4,quantity);
            ps.setDouble(5, buyPrice);
            ps.setDouble(6,marketPrice);
            ps.setDate(7,productionDate);
            ps.setDate(8, expiration);
            ps.setInt(9, supplierID);

            id = ps.executeUpdate();
            ps.close();

            double newAmountOwed = quantity * buyPrice;
            ps = connection.prepareStatement("UPDATE Supplier SET amountOwed = amountOwed + ? WHERE supplierID = ?;");
            ps.setDouble(1, newAmountOwed);
            ps.setInt(2, supplierID);

            id = ps.executeUpdate();
            connection.commit();
            ps.close();

            System.out.println(id);

            if(id > 0){
                System.out.println("Merchandise added successfully");
            } else{
                System.out.println("Merchandise not added");
            }
        }
        catch (SQLException e) {
            System.out.println("SQL Exception");
            connection.rollback();
            System.out.println(e.getStackTrace());
        }
    }
    /**
     * Method is called from Merchandise file and is called after the user has finished filling all the attributes that they want to edit
     * for a Merchandise
     * @param productID
     * @param storeID
     * @param name
     * @param quantity
     * @param buyPrice
     * @param marketPrice
     * @param productionDate
     * @param expiration
     * @param supplierID
     * @throws SQLException
     * @throws ParseException
     */
    static void editMerchandise(int productID, int storeID, String name, int quantity, double buyPrice, double marketPrice, Date productionDate, Date expiration, int supplierID) throws SQLException, ParseException{
        //Object that represents a precompiled SQL statement
        PreparedStatement ps = null;
        int id = 0;
        try{
            ps = connection.prepareStatement("UPDATE Merchandise SET storeID = ?, name = ?, quantity = ?, buyPrice = ?, marketPrice = ?, productionDate = ?, expiration = ?, supplierID = ? WHERE productID = ?;");

            ps.setInt(1,storeID);
            ps.setString(2,name);
            ps.setInt(3,quantity);
            ps.setDouble(4, buyPrice);
            ps.setDouble(5,marketPrice);
            ps.setDate(6,productionDate);
            ps.setDate(7, expiration);
            ps.setInt(8, supplierID);
            ps.setInt(9,productID);

            id = ps.executeUpdate();
            connection.commit();
            ps.close();
            System.out.println(id);


            if(id > 0){
                System.out.println("Merchandise updated successfully");
            } else{
                System.out.println("Merchandise not updated");
            }
        }
        catch (SQLException e) {
            System.out.println("SQL Exception");
            connection.rollback();
            System.out.println(e.getStackTrace());
        }
    }
    /**
     * Method is called from Merchandise file and is called to delete a merchandise from the database
     * @param productID
     */
    static void deleteMerchandise(int productID){
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM Merchandise WHERE productID = ?;");
            ps.setInt(1, productID);
            int id = ps.executeUpdate();

            System.out.println(id);

            if (id > 0) {
                System.out.println("Merchandise deleted");
            } else {
                System.out.println("Merchandise not deleted");
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception");
            System.out.println(e.getStackTrace());
        }
    }

    /**
     * Method adds a merchandise to the database and handles duplicates. Method is called in Merchandise file to execute the sql query.
     * @param productID
     * @param storeID
     * @param name
     * @param quantity
     * @param buyPrice
     * @param marketPrice
     * @param productionDate
     * @param expiration
     * @param supplierID
     * @throws SQLException
     * @throws ParseException
     */
    static void addNewMerchandise(int productID, int storeID, String name, int quantity, double buyPrice, double marketPrice, Date productionDate, Date expiration, int supplierID) throws SQLException, ParseException{
        //Object that represents a precompiled SQL statement
        PreparedStatement ps = null;
        PreparedStatement ps2 = null;
        int id = 0;
        try{
            ps = connection.prepareStatement("INSERT INTO Merchandise (productID, storeID, name, quantity, buyPrice, marketPrice, productionDate, expiration, supplierID) VALUES (?,?,?,?,?,?,?,?,?) ON DUPLICATE KEY UPDATE quantity = quantity + 10000;");
            ps.setInt(1,productID);
            ps.setInt(2,storeID);
            ps.setString(3,name);
            ps.setInt(4,quantity);
            ps.setDouble(5, buyPrice);
            ps.setDouble(6,marketPrice);
            ps.setDate(7,productionDate);
            ps.setDate(8, expiration);
            ps.setInt(9, supplierID);
            
            id = ps.executeUpdate();
            connection.commit();
            ps.close();
            System.out.println(id);
            
            if(id > 0){
                System.out.println("New Merchandise added successfully");
            } else{
                System.out.println("New Merchandise not added");
            }

            id = 0;
            ps2 = connection.prepareStatement("UPDATE Supplier SET amountOwed = amountOwed + (?*?) where supplierID = ?;");
            ps2.setDouble(1,buyPrice);
            ps2.setInt(2,quantity);
            ps2.setInt(3,supplierID);
            
            id = ps2.executeUpdate();
            connection.commit();
            ps2.close();
            System.out.println(id);
            
            if(id > 0){
                System.out.println("Amount owed to supplier updated");
            } else{
                System.out.println("Amount owed to supplier could not be updated");
            }
        }
        catch (SQLException e) {
            System.out.println("SQL Exception");
            connection.rollback();
            System.out.println(e.getStackTrace());
        }
    }
    
    /**
     * Method is called in Merchandise file under returnInventory and is used to execute queries when a member returns a product. The first query will be updating the 
     * Merchandise database and increasing the quantity of the product. The second query will be reducing the reward amount that the member has.
     * The last query will be removing the transaction from the database.
     * @param productID
     * @param supplierID
     * @param storeID
     * @param memberID
     * @param transactionID
     * @throws SQLException
     * @throws ParseException
     */
    public static void returnInventory(int productID, int supplierID, int storeID, int memberID, int transactionID, int transactionQuantity) throws SQLException, ParseException{
        //Object that represents a precompiled SQL statement
        PreparedStatement ps = null;
        PreparedStatement ps2 = null;
        PreparedStatement ps3 = null;

        int id = 0;
        try{
            ps = connection.prepareStatement("Update Merchandise SET quantity = quantity + ? WHERE productID = ? AND supplierID = ? AND storeID = ?;");
            ps.setInt(1, transactionQuantity);
            ps.setInt(2,productID);
            ps.setInt(3, supplierID);
            ps.setInt(4,storeID);
            
            
            id = ps.executeUpdate();
            connection.commit();
            ps.close();
            System.out.println(id);
            
            if(id > 0){
                System.out.println("Merchandise quantity updated");
            } else{
                System.out.println("Merchandise quantity not updated");
            }

            id = 0;
            ps2 = connection.prepareStatement("UPDATE Member SET rewardAmount = rewardAMOUNT - (SELECT(total*0.02) FROM Transaction WHERE transactionID = ? AND productID = ?) WHERE memberID = ?;");
            ps2.setInt(1,transactionID);
            ps2.setInt(2,productID);
            ps2.setInt(3,memberID);
            
            id = ps2.executeUpdate();
            connection.commit();
            ps2.close();
            System.out.println(id);
            
            if(id > 0){
                System.out.println("Member reward amount updated");
            } else{
                System.out.println("Member reward amount not updated");
            }

            id = 0;
            ps3 = connection.prepareStatement("DELETE FROM Transaction WHERE transactionID = ?;");
            ps3.setInt(1,transactionID);
            
            id = ps3.executeUpdate();
            connection.commit();
            ps3.close();
            System.out.println(id);
            
            if(id > 0){
                System.out.println("Transaction deleted");
            } else{
                System.out.println("Transaction not deleted");
            }
        }
        catch (SQLException e) {
            System.out.println("SQL Exception");
            connection.rollback();
            System.out.println(e.getStackTrace());
        }
    }
    /**
     * This method is called in the Merchandise file under transferInventory. The purpose of this method is to take in two storeIDs and transfer the product of one
     * store to another store. This method will execute two queries with the first query reducing the quantity of the original store. The second query will be adding the 
     * product to the second store, while handling duplicates.
     * @param productID
     * @param storeID
     * @param name
     * @param quantity
     * @param buyPrice
     * @param marketPrice
     * @param productionDate
     * @param expiration
     * @param supplierID
     * @param storeID2
     * @throws SQLException
     * @throws ParseException
     */
    public static void transferInventory(int productID, int storeID, String name, int quantity, double buyPrice, double marketPrice, Date productionDate, Date expiration, int supplierID,int storeID2) throws SQLException, ParseException{
        //Object that represents a precompiled SQL statement
        PreparedStatement ps = null;
        PreparedStatement ps2 = null;
        int id = 0;
        try{
            id = 0;
            ps = connection.prepareStatement("UPDATE Merchandise SET quantity = quantity - 10 WHERE productID = ? AND supplierID = ? AND storeID = ?;");
            ps.setInt(1,productID);
            ps.setInt(2,supplierID);
            ps.setInt(3,storeID);
            
            id = ps.executeUpdate();
            connection.commit();
            ps.close();
            System.out.println(id);
            
            if(id > 0){
                System.out.println("Merchandise removed from original store");
            } else{
                System.out.println("Merchandise not removed from original store");
            }

            id = 0;
            ps2 = connection.prepareStatement("INSERT INTO Merchandise (productID, storeID, name, quantity, buyPrice, marketPrice, productionDate, expiration, supplierID) VALUES (?,?,?,?,?,?,?,?,?) ON DUPLICATE KEY UPDATE quantity = quantity + 10;");
            ps2.setInt(1,productID);
            ps2.setInt(2,storeID2);
            ps2.setString(3,name);
            ps2.setInt(4,quantity);
            ps2.setDouble(5, buyPrice);
            ps2.setDouble(6,marketPrice);
            ps2.setDate(7,productionDate);
            ps2.setDate(8, expiration);
            ps2.setInt(9, supplierID);
            
            id = ps2.executeUpdate();
            connection.commit();
            ps2.close();
            System.out.println(id);
            
            if(id > 0){
                System.out.println("Merchandise transfered successfully to store");
            } else{
                System.out.println("Merchandise not transfered successfully to store");
            }
        }
        catch (SQLException e) {
            System.out.println("SQL Exception");
            connection.rollback();
            System.out.println(e.getStackTrace());
        }
    }
}
