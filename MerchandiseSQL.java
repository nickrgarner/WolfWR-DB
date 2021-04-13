import java.sql.*;
import java.util.*;
import java.io.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.Period;

public class MerchandiseSQL {
    private static final String jdbcURL = "";
    private static final String user = "";
    private static final String password = "";

    //This connects to the database by calling the login file 
    static Connection connection = login.connection;
    static Statement statement = login.statement;
    static ResultSet result = login.result;
}
