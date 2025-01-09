package org.openjfx.database;
//database interface for which action and for which table
public interface DatabaseFunctions <T> {
    void insert(T entity);
    void update(T entity , int id);
    void delete (int id);
    T[] select (String id, String value , int limit);

}
