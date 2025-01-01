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
        }
        catch(SQLException e)
        {
            System.out.println(e);
        }
    }
    public static void insertUser(String username,String password,String dob,String email,String joinDate){

        //add the users information to the database
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

//        if (foodAttributes == null || foodAttributes.length > foodAttributes.length) {}
        String sql = "INSERT INTO food(name,calories,protein,carbs,fats,sugar,track_date) VALUES(?,?,?,?,?,?,?)";

        try(Connection conn= connect();
            PreparedStatement pstmt = conn.prepareStatement(sql))
        {

            pstmt.setString(1,foodAttributes[0]);
            pstmt.setFloat(2, Float.parseFloat(foodAttributes[1]));
            pstmt.setFloat(3,Float.parseFloat(foodAttributes[2]));
            pstmt.setFloat(4,Float.parseFloat(foodAttributes[3]));
            pstmt.setFloat(5,Float.parseFloat(foodAttributes[4]));
            pstmt.setFloat(6,Float.parseFloat(foodAttributes[5]));
            pstmt.setString(7,foodAttributes[6]);


            pstmt.execute();
        }
        catch(SQLException e){
            System.out.println(e);
        }
        catch(NullPointerException e){

//           System.out.println("a");
            e.printStackTrace();
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
    //updates the data from the table
    public static void editData(String [] data,int id){


        //edits the data in the database
        String sql ="UPDATE food SET name=?,calories=?,protein=?,carbs=?,fats=?,sugar=? WHERE id=?";

        try(Connection conn = connect();
        PreparedStatement pstmt = conn.prepareStatement(sql))
        {

            pstmt.setString(1,data[0]);//name
            pstmt.setFloat(2,Float.parseFloat(data[1]));//calories
            pstmt.setFloat(3,Float.parseFloat(data[2]));//protein
            pstmt.setFloat(4,Float.parseFloat(data[3]));//carbs
            pstmt.setFloat(5,Float.parseFloat(data[4]));//fats
            pstmt.setFloat(6,Float.parseFloat(data[5]));//sugars
            pstmt.setInt(7,id);
            pstmt.executeUpdate();
        }
        catch (SQLException e){
            System.out.println(e);

        }

    }
    public static void deleteData(int id){
        String sql = "DELETE FROM food WHERE id=?";

        try(Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sql))
        {
            pstmt.setInt(1,id);
            pstmt.execute();
        }
        catch(SQLException e){
            System.out.println(e);
        }
    }
    public static  ArrayList<Food> Select(String column,String column_value){
        String sql = "SELECT * FROM food WHERE "+ column+" = ?" ;
        ArrayList <Food>foodView = new ArrayList<>();//stores all the inseted food into an array of food
        try(Connection conn = connect();
            PreparedStatement pstm  =conn.prepareStatement(sql)
        ){
            pstm.setString(1,column_value);
            ResultSet rs = pstm.executeQuery();

            //stores the data where the columns match
            String[] test = new String[rs.getMetaData().getColumnCount()];


            while(rs.next()){
                //need to make the data get looped through

                    for (int i =0; i <rs.getMetaData().getColumnCount(); i++){
                        String data = rs.getString(i+1);
                        test[i] =data;
                    }
                    foodView.add(new Food(Integer.parseInt(test[0]),test[1],Float.parseFloat(test[2]),Float.parseFloat(test[3]),Float.parseFloat(test[4]),Float.parseFloat(test[5]),Float.parseFloat(test[6])));



            }
        }
        catch(SQLException e){
            System.out.println(e);
        }
        return foodView;
    }
    //selects all the data in  a specified table
    public static String[] selectAll(String[] columns, String table){

        //allows the function to be used in multipe situations
       String sql ="SELECT ";
       for (int i = 0; i < columns.length; i++) {
           sql += columns[i]+" ";
           if(i!=columns.length-1){
               sql += ",";
           }

       }
       sql+= "FROM "+table;
       System.out.println(sql);
        ArrayList<String> data=new ArrayList<>() ;
        try(Connection conn = connect();
        PreparedStatement pstmt =conn.prepareStatement(sql)){

            ResultSet rs  =pstmt.executeQuery();

            String [] temp = new String[rs.getMetaData().getColumnCount()];
            // used to store the values of the data to add to the arraylist
            while(rs.next()){
                for (int i =0; i <rs.getMetaData().getColumnCount(); i++){
                    String value= rs.getString(i+1);
                    temp[i] =value;
                }
//                System.out.println(temp.toString());
                data.addAll(Arrays.asList(temp));//adds the temp contents to the data
            }
            for (int i =0; i <data.size(); i++){
                System.out.println(data.get(i));
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }

        return data.toArray(new String[data.size()]);



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