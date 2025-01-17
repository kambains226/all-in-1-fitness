package org.openjfx.database;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
//creates the interface to map to the database field name
//using annotations
//IN code reference https://docs.oracle.com/javase/tutorial/java/annotations/basics.html
@Retention (RetentionPolicy.RUNTIME) //makes it used at runtime
public @interface Column {
    String name();//for the column name in the database
}
