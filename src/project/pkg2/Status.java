/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.pkg2;

import java.util.Random;

/**
 *
 * @author Mahmoud
 */
public enum Status {
    On, Off;
    
    static Random r = new Random();
    
    /*
    * Returns a random value from the status enum 
    */
    public static Status randomStatus() {
        // values() returns an array of the values the enum contains
        Status statusValues[] = values();
        
        return statusValues[r.nextInt(statusValues.length)];
        
    }
}
