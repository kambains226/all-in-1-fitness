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
    boolean insertOb(String table,Object object);

    default void editData(String [] data,int id,String user) {
        throw new UnsupportedOperationException("editing is not been implemeneted ");
    }
    void deleteData(String table,int id);

    //general select
    ArrayList <String[]> select(String table, String[] columns , String whereClause,Object [] whereParams,String orderBy,String limit);
    //selects the particular food
    ArrayList<Food> selectFood(String identifer, String idValue, String andId , String andValue);
    //for the quick add for making it so quick add can store all the food
    String[] selectAll(String[] columns, String table);
    //gets the id for the user making it so the user id can be gathered
    int getsId(String table,String identifer,String idValue);
    //orders the food
    String[]  selectOrder(String table,String identifer,String idValue,String limit);
    String check(String username);


}
