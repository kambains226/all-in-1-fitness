package org.openjfx.database;
import javafx.scene.control.TextField;
import org.openjfx.models.Food;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;


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
    // make a function which can be used to with an array
    public static void insertFood(String [] foodAttributes){


        //going to need to crate a select funciton
        //add the users information to the database
        System.out.println(foodAttributes.length);
        String sql = "INSERT INTO food(name,calories,protein,carbs,fats,sugar,track_date) VALUES(?,?,?,?,?,?,?)";

        try(Connection conn= connect();
            PreparedStatement pstmt = conn.prepareStatement(sql))
        {
            System.out.println("Inserting food");
            pstmt.setString(1,foodAttributes[0]);
            pstmt.setInt(2, Integer.parseInt(foodAttributes[1]));
            pstmt.setInt(3,Integer.parseInt(foodAttributes[2]));
            pstmt.setInt(4,Integer.parseInt(foodAttributes[3]));
            pstmt.setInt(5,Integer.parseInt(foodAttributes[4]));
            pstmt.setInt(6,Integer.parseInt(foodAttributes[5]));
            pstmt.setString(7,foodAttributes[6]);


            pstmt.execute();
        }
        catch(SQLException e){
            System.out.println(e);
        }
    }
   //used so i can use this insert function for to insert any table i want
//    public static void insert(String name, int[] values){
//
//        //add the users information to the database
//        System.out.println("Inserting2 user");
//        String sql = "INSERT INTO login(username,password,dob,email,join_date) VALUES(?,?,?,?,?)";
//
//        try(Connection conn= connect();
//            PreparedStatement pstmt = conn.prepareStatement(sql))
//        {
//            System.out.println("Inserting user");
//            pstmt.setString(1,username);
//            pstmt.setString(2,password);
//            pstmt.setString(3,dob);
//            pstmt.setString(4,email);
//            pstmt.setString(5,joinDate);
//
//
//            pstmt.execute();
//        }
//        catch(SQLException e){
//            System.out.println(e);
//        }
//    }
    public static  ArrayList<Food> Select(String column,String column_value){
        String sql = "SELECT * FROM food WHERE "+ column+" = ?" ;
        ArrayList <Food>foodView = new ArrayList<>();//stores all the inseted food into an array of food
        try(Connection conn = connect();
            PreparedStatement pstm  =conn.prepareStatement(sql)
        ){
            pstm.setString(1,column_value);
            ResultSet rs = pstm.executeQuery();


            String[] test = new String[rs.getMetaData().getColumnCount()];


            while(rs.next()){
                //need to make the data get looped through

                    for (int i =0; i <rs.getMetaData().getColumnCount(); i++){
                        String data = rs.getString(i+1);
                        test[i] =data;
                    }
                    foodView.add(new Food(test[1],Integer.parseInt(test[2]),Integer.parseInt(test[3]),Integer.parseInt(test[4]),Integer.parseInt(test[5]),Integer.parseInt(test[6])));



            }
            System.out.println(foodView);
        }
        catch(SQLException e){
            System.out.println(e);
        }
        System.out.println(foodView.size()+" rows found");
        return foodView;
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