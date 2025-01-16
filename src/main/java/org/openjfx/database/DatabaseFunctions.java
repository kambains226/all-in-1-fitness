package org.openjfx.database;

import org.openjfx.models.Food;

import java.util.ArrayList;

//database interface for all the functions
public interface DatabaseFunctions  {
    void connect();
    void close();
    void create();
    //used for the weight and quick add
    void insert(String table ,String [] columns , String [ ] values);
    /**
     *
     *
     * @param table table name
     * @param object the object getting inserted
     */
    //used for the insert and login
    void insertOb(String table,Object object);

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
