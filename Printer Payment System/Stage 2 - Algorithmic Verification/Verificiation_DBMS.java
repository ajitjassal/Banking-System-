package banking;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;


public class JDBC {
    private String pathToDb = "jdbc:sqlite:.\\";// path to DB FILE


    public JDBC(String dbName) {//expected dbName = bank.db
        pathToDb = pathToDb + dbName;
        createNewDatabase();
        createNewTable();
    }

    private Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(pathToDb);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
    public void createNewTable() {
        String newTable = "CREATE TABLE IF NOT EXISTS card (\n"
                + "	id INTEGER,\n"
                + "	number TEXT NOT NULL,\n"
                + "	pin TEXT NOT NULL,\n"
                + " balance INTEGER DEFAULT 0\n"
                + ");";

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(newTable); // create a new table

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void createNewDatabase() {

        //String url = "jdbc:sqlite:C:\\" + fileName;

        try (Connection conn = DriverManager.getConnection(pathToDb)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
//                System.out.println("The driver name is " + meta.getDriverName());
//                System.out.println("A new database has been created.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void insertCard(int count, CreditCard tempCard) {

        int id = count;
        String number = Long.toString(tempCard.getCardNumber());
        String pin = Integer.toString(tempCard.getPin());
        int balance = tempCard.getBalance();

        String sql = "INSERT INTO card (id,number,pin,balance) VALUES(?,?,?,?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, number);
            pstmt.setString(3, pin);
            pstmt.setInt(4, balance);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void deleteCard(long deleteCard) {
        String sql = "DELETE FROM card WHERE number = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setString(1, Long.toString(deleteCard));
            // execute the delete statement
            pstmt.executeUpdate();
            System.out.println("The account has been closed!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public CreditCard checkCard(String cardNumber){
        CreditCard blankCard = new CreditCard();

        String sql = "SELECT number,pin,balance " + "FROM card WHERE number = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt  = conn.prepareStatement(sql)) {

            // set the value
            pstmt.setString(1, cardNumber);
            //
            ResultSet rs  = pstmt.executeQuery();

            // loop through the result set
            while (rs.next()) {
                blankCard.setCardNumber(Long.parseLong(rs.getString("number")));
                blankCard.setPin(Integer.parseInt(rs.getString("pin")));
                blankCard.setBalance(rs.getInt("balance"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return blankCard;
    }

}
