package org.openjfx.database;
import javafx.scene.control.TextField;
import org.openjfx.models.Food;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.IntToDoubleFunction;


//sqllite documentation https://www.sqlite.org/docs.html
public class DatabaseManager implements  DatabaseFunctions {
    private Connection conn;
    private static final String DB_URL= "jdbc:sqlite:gym.db";


    public DatabaseManager() {
        connect();

    }

    @Override
    public void connect(){
        try{
            conn = DriverManager.getConnection(DB_URL);
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void close(){
        try{
            if(conn != null){
                conn.close();
            }
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    //creates the tables
    @Override
    public void create(){
        String createLogin = """
               CREATE TABLE IF NOT EXISTS login (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                username TEXT unique,
                password TEXT,
                email TEXT ,
                dob DATE
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
               track_date DATE,
               user_id INTEGER
               ); 
                """;
        ;
        String createWeight = """
                CREATE TABLE IF NOT EXISTS weight (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                weight INTEGER,
                goal   INTEGER,
                user_id INTEGER, 
                goal_date DATE
                );
                """;
        //membership will be used to decided if it is a trainer or a member
        try{
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(createLogin);

            stmt.execute(createFood);

            stmt.execute(createWeight);
        }
        catch(SQLException e)
        {
            System.out.println(e);
        }
    }
    @Override
    //inserts the data
    public void insert(String table ,String[] columns,String[] values) {
        String columnsNames = String.join(",", columns);
        String[] questionMarks = new String [values.length];
        //files the array with question marks
        Arrays.fill(questionMarks, "?");
        String placeholders = String.join(",", questionMarks);

        String insertSql = "INSERT INTO " + table + "(" + columnsNames + ") VALUES (" + placeholders + ")";
        try (PreparedStatement pstmt = conn.prepareStatement(insertSql)) {
            for (int i = 0; i < values.length; i++) {
                pstmt.setString(i+1  ,  (values[i]));
            }

            pstmt.execute();

        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }

    }

    //gets the object to determine the fields

    public void insertOb(String table,Object object){
        Class <?> clas = object.getClass();
        //gets the fields with annotations
        Field[] fields = clas.getDeclaredFields();
       //gets the columsn
        List<String> columnsNames = new ArrayList<>();
        //gets the values
        List<Object> values = new ArrayList<>();

        for(Field f : fields){
            //checks if there is annotation
           if(f.isAnnotationPresent(Column.class)){
              Column column = f.getAnnotation(Column.class);
              String columnName = column.name();
              columnsNames.add(columnName);
           }
           //makes sure fiedls are able to be accessed
            f.setAccessible(true);
            try{
                values.add(f.get(object));

            }
            catch(IllegalAccessException e){
                System.out.println(e.getMessage());
            }
        }
//        https://docs.oracle.com/javase/8/docs/api/java/util/Map.html
        //map documenation
        if(object instanceof  Food){
            Food food = (Food) object;
            Map<String, Float> macros= food.getMacros();
            columnsNames = new ArrayList<>();
           values = new ArrayList<>();
           values.add(food.NameProperty());
            columnsNames.add("name");
            for(Map.Entry<String, Float> entry : macros.entrySet()){
                columnsNames.add(entry.getKey());
                values.add(entry.getValue());
            }

            columnsNames.add("track_date");
            columnsNames.add("user_id");
            values.add(food.getDate());
            values.add(food.getUser_id());

        }
        System.out.println(columnsNames);
        System.out.println(values);
        String columns= String.join(",", columnsNames);
        String[] questionMarks = new String [values.size()];
        //files the array with question marks
        Arrays.fill(questionMarks, "?");
        String placeholders = String.join(",", questionMarks);

        String sql = "INSERT INTO " + table + "(" + columns+ ") VALUES (" + placeholders + ")";
        System.out.println(sql);
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            for (int i = 0; i < values.size(); i++) {
                pstmt.setObject(i+1  ,  values.get(i));
            }

            pstmt.execute();

        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }




    //edits the data for the food
    @Override
    public void editData(String [] data,int id,String user){


        //edits the data in the database
        System.out.println(Arrays.toString(data));
        System.out.println(id);
        String sql ="UPDATE food SET name=?,calories=?,protein=?,carbs=?,fats=?,sugar=? WHERE id=?";

        try(PreparedStatement pstmt = conn.prepareStatement(sql))
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
            System.out.println(e.getMessage());

        }

    }
    //deletes the data
    @Override
    public void deleteData(String table,int id){
        String sql = "DELETE FROM "+table +" WHERE id=?";

        try(PreparedStatement pstmt = conn.prepareStatement(sql))
        {
            pstmt.setInt(1,id);
            pstmt.execute();
        }
        catch(SQLException e){
            System.out.println(e);
        }

    }

    @Override
    //limiting the data  by 1
    public ArrayList<String> Select(String table,String column,String column_value){

        String sql = "SELECT * FROM "+table+" WHERE "+ column+" = ? Limit 1" ;
        ArrayList <String>result= new ArrayList<>();//stores all the inseted food into an array of food
        try(PreparedStatement pstm  =conn.prepareStatement(sql)
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

            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return result;
    }
    @Override
    //when you need multiple where clauses
    public ArrayList<Food>  selectFoodAnd(String identifer,String idValue,String andId ,String andValue)

    {

        String sql ="SELECT * FROM food WHERE " + identifer +" = ? AND " + andId + " = ? " ;
//       System.out.println(sql+idValue+andValue);
        ArrayList <Food>foodView = new ArrayList<>();//stores all the inseted food into an array of food
        try(PreparedStatement pstm  =conn.prepareStatement(sql)
        ){
            pstm.setString(1,idValue);
            pstm.setInt(2,(Integer.parseInt(andValue)));
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
            System.out.println(e.getMessage());
        }
        return foodView;
    }
    //selects all the data in  a specified table

    @Override
    public String[] selectAll(String[] columns, String table){

        //allows the function to be used in multipe situations
       String sql ="SELECT ";
       for (int i = 0; i < columns.length; i++) {
           sql += columns[i]+" ";
           if(i!=columns.length-1){
               sql += ",";
           }

       }
       sql+= "FROM "+table;
        ArrayList<String> data=new ArrayList<>() ;
        try(
        PreparedStatement pstmt =conn.prepareStatement(sql)){

            ResultSet rs  =pstmt.executeQuery();

            String [] temp = new String[rs.getMetaData().getColumnCount()];
            // used to store the values of the data to add to the arraylist
            while(rs.next()){
                for (int i =0; i <rs.getMetaData().getColumnCount(); i++){
                    String value= rs.getString(i+1);
                    temp[i] =value;
                }
                data.addAll(Arrays.asList(temp));//adds the temp contents to the data
            }

        }
        catch(SQLException e){
            e.printStackTrace();
        }

        return data.toArray(new String[data.size()]);



    }
    // overloads the selectAll to take a limit
    //selects the login id

    @Override
    public int getsId(String table,String identifer,String idValue)
    {
        String sql = "SELECT id FROM " + table+ " WHERE "+ identifer+ " =? " ;

        try(PreparedStatement pstmt = conn.prepareStatement(sql))

        {
            // gets the result of the query

            pstmt.setString(1,idValue);
            ResultSet rs = pstmt.executeQuery();
            int id = rs.getInt("id");
            return id;




        }
        catch(SQLException e)
        {
            System.out.println(e);
        }
        return 0;
    }
    //returns the most recently added data
    @Override
    public String[]  selectOrder(String table,String identifer,String idValue,String limit)
    {

        //by id as its alwasy uniquye and auto incremented
        String sql ="SELECT * "+"FROM "+table+" WHERE " + identifer +" = ?"+"ORDER BY id DESC ";
        //if limit is 0 ther will be no limit applied
        if(Integer.valueOf(limit) != 0){
            sql+=" LIMIT ? ";
        }
        ArrayList<String> data=new ArrayList<>() ;
        try(PreparedStatement pstmt =conn.prepareStatement(sql)){


            // used to store the values of the data to add to the arraylist
           pstmt.setString(1,idValue);
           if(Integer.valueOf(limit) != 0){

               pstmt.setInt(2,Integer.parseInt(limit));
           }

           ResultSet rs = pstmt.executeQuery();

            String [] temp = new String[rs.getMetaData().getColumnCount()-1];
           while(rs.next()){
               for (int i =0; i <rs.getMetaData().getColumnCount()-1; i++){
                   String value= rs.getString(i+2);
                   temp[i] =value;
               }
               data.addAll(Arrays.asList(temp));
           }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return data.toArray(new String[data.size()]);
    }
    @Override
    //selects the specific value
    public String[]  selectSpecific(String table,String column,String identifer,String idValue)
    {

        String sql ="SELECT "+column+" FROM "+table+" WHERE " + identifer +" = "+idValue;
        //if limit is 0 ther will be no limit applied
        ArrayList<String> data=new ArrayList<>() ;
        try(
            PreparedStatement pstmt =conn.prepareStatement(sql)){


            // used to store the values of the data to add to the arraylist


            ResultSet rs = pstmt.executeQuery();

            while(rs.next()){
                data.add(rs.getString(1));
                for (int i =0; i <rs.getMetaData().getColumnCount()-1; i++){
                    String value= rs.getString(i+2);
                    data.add(value);
                }
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }        //converts data to an array
        return data.toArray(new String[data.size()]);
    }

    //checks if the username is exists
    @Override
    public String check(String username){

        String sql = "SELECT * FROM login WHERE username=?";

        try(
            PreparedStatement pstmt = conn.prepareStatement(sql))
        {
            pstmt.setString(1,username);
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
    public static void main(String[] args){
        DatabaseManager dbm = new DatabaseManager();
    dbm.create();
}
}