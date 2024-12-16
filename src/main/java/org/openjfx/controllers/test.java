package org.openjfx.controllers;
import org.mindrot.jbcrypt.BCrypt;
public class test {


    public void testx(){
        String salt =BCrypt.gensalt(12);
        String password="adc";
        String hash = BCrypt.hashpw(password,salt);
        System.out.println(hash);

        System.out.println(BCrypt.checkpw(password,hash));

    }

    public class TrackerController {



        public void initialize(){
            System.out.println("test");
        }
        public void load(){
            System.out.println("load");
        }
    }

    public class TrackerController2 {
        public void initialize() {
            System.out.println("Initializing Tracker");
        }
    }
}
