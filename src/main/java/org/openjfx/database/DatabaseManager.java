package org.openjfx.database;
import java.sql.*;


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
    public static void intialize(){
        String createLogin = """
               CREATE TABLE IF NOT EXISTS login (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                username TEXT unique,
                password TEXT,
                firstname TEXT ,
                surname TEXT ,
                email TEXT ,
                dob DATE,
                membership TEXT , 
                join_date DATE
               
               ); 
                """;
        //membership will be used to decided if it is a trainer or a member
        try(Connection conn = connect()){
            Statement stmt = connect().createStatement();
            stmt.execute(createLogin);
            System.out.println("Table created");
        }
        catch(SQLException e)
        {
            System.out.println(e);
        }
    }
//    public void insertUser(String username,String password,String firstname, String surname,String dob,String joinDate){
//
//        //add the users information to the database
//        String sql = "INSERT INTO login(username,password,firstname,surname,email,join_date) VALUES(?,?,?)";
//
//        try(Connection conn= connect();
//            PreparedStatement pstmt = conn.prepareStatement(sql))
//        {
//            pstmt.setString(2,username);
//            pstmt.setString(3,password);
//            pstmt.setString(4,firstname);
//            pstmt.setString(5,surname);
//            pstmt.setString(6,dob);
//            pstmt.setString(7,email);
//            pstmt.setString(9,joinDate);
//
//        }
//        catch(SQLException e){
//            System.out.println(e);
//        }
//    }
    public static void main(String[] args){
        intialize();
    }
}