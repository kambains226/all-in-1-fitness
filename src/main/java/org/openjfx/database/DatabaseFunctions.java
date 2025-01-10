package org.openjfx.database;

import org.openjfx.models.Food;

import java.util.ArrayList;

//database interface for all the functions
public interface DatabaseFunctions  {
    void connect();
    void close();
    void create();
    void insert(String table ,String [] columns , String [ ] values);

    /**
     *
     *
     * @param username the username of the user
     * @param password the hashed password ofhte user
     * @param dob     the date of birth of the user
     * @param email   the email of the user
     * @param joinDate the date the user joined
     */
    void insertUser(String username,String password,String dob,String email,String joinDate);
    void insertFood(String [] foodAttributes);
    void edit(String table,String[] columns,String[] values , String unique , String uniquevalue);
    default void editData(String [] data,int id,String user) {
        throw new UnsupportedOperationException("editing is not been implemeneted ");
    }
    void deleteData(String table,int id);
    ArrayList<Food> selectFoodAnd(String identifer, String idValue, String andId , String andValue);
    ArrayList<String> Select(String table,String column,String column_value);
    String[] selectAll(String[] columns, String table);
    int getsId(String table,String identifer,String idValue);
    String[]  selectOrder(String table,String identifer,String idValue,String limit);
    String[]  selectSpecific(String table,String column,String identifer,String idValue);
    String check(String username);


}
