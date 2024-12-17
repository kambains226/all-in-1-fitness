package org.openjfx.database;
import javafx.scene.control.TextField;

import java.sql.*;
import javafx.scene.control.TextField;


//sqllite documentation https://www.sqlite.org/docs.html
public class DatabaseManager {
    private static final String DB_URL= "jdbc:sqlite:gym.db";


    public static Connection connect(){
        Connection conn = null;

            try{
                conn = DriverManager.getConnection(DB_URL);
                System.out.println("Connected to database");
            }
            catch(SQLException e){
                System.out.println(e);
            }
            return conn;

    }
    //creates the table
    public static void intialize(){
        String createLogin = """
               CREATE TABLE IF NOT EXISTS login (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                username TEXT unique,
                password TEXT,
                email TEXT ,
                dob DATE,
                membership TEXT , 
                join_date DATE
               
               ); 
                """;
        String createFood = """
               CREATE TABLE IF NOT EXISTS food (
               id INTEGER PRIMARY KEY AUTOINCREMENT,
               name TEXT,
               calories INTEGER,
               protein INTEGER,
               carbs INTEGER,
               fats INTEGER,
               sugar INTEGER,
               track_date DATE
               ); 
                """;
        //membership will be used to decided if it is a trainer or a member
        try(Connection conn = connect()){
            Statement stmt = connect().createStatement();
            stmt.execute(createFood);
            System.out.println("Table created");
        }
        catch(SQLException e)
        {
            System.out.println(e);
        }
    }
    public static void insertUser(String username,String password,String dob,String email,String joinDate){

        //add the users information to the database
        System.out.println("Inserting2 user");
        String sql = "INSERT INTO login(username,password,dob,email,join_date) VALUES(?,?,?,?,?)";

        try(Connection conn= connect();
            PreparedStatement pstmt = conn.prepareStatement(sql))
        {
            System.out.println("Inserting user");
            pstmt.setString(1,username);
            pstmt.setString(2,password);
            pstmt.setString(3,dob);
            pstmt.setString(4,email);
            pstmt.setString(5,joinDate);


            pstmt.execute();
        }
        catch(SQLException e){
            System.out.println(e);
        }
    }

    public static String check(TextField username){

        String sql = "SELECT * FROM login WHERE username=?";

        try(Connection conn=connect();
            PreparedStatement pstmt = conn.prepareStatement(sql))
        {
            pstmt.setString(1,username.getText());
            // gets the result of the query
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                String user = rs.getString("username");
                String password = rs.getString("password");
                String dob = rs.getString("dob");
                return  password;
            }

        }
        catch(SQLException e)
        {
            System.out.println(e);
        }
        return null;
    }
//
    public static void main(String[] args){
        intialize();
    }
}