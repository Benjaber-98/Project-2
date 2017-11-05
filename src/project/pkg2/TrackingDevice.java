/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.pkg2;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Mahmoud
 */
public class TrackingDevice {
    
    private int id;
    private String timestamp;
    private String status;
    private double longitude;
    private double latitude;
    private int speed;
    
    private final Random r = new Random();
    
    
    public TrackingDevice(){
        // Generating Device Details
        setRandomId();
        setRandomTimestamp();
        setRandomStatus();
        setRandomLatitude();
        setRandomLongitude();
        setRandomSpeed();
        
    }
    
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Generating a random 13 digits id for each object 
     */
    private void setRandomId() {
        this.id = generateRandomId();
    }
    
    public static int generateRandomId(){
        int randomId = (int)(1000000*Math.random());
        Pattern pattern = Pattern.compile("^[0-9]{6}$");
        Matcher matcher = pattern.matcher(String.valueOf(randomId));
        while(! matcher.find()) {
            randomId *= 10;
            pattern = Pattern.compile("^[0-9]{6}$");
            matcher = pattern.matcher(String.valueOf(randomId));
        }
        
        return randomId;
    }
        
        
        

    /**
     * @return the timestamp
     */
    public String getTimestamp() {
        return timestamp;
    }

    /**
     * Generating a random Timestamp which is the current time
     */
    private void setRandomTimestamp() {
        
        //Current Milliseconds
        long currentMilliSeconds = System.currentTimeMillis();
        
        //The Format Of Timestamp
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); 
        
        //Creating a new Date from milliseconds
        Date currentDate = new Date(currentMilliSeconds);
        
        //Getting the timestamp in variable variable
        timestamp = dateFormat.format( currentDate );
        
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Generating a random Status from the Status Enum
     */
    private void setRandomStatus() {
        
        status = Status.randomStatus().toString();
        
    }

    /**
     * @return the longitude
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Generating a  random Longitude 
     */
    private void setRandomLongitude() {
        // generating a boolean value and change it to 1 or -1
        int longitudeNumber = r.nextBoolean() ? 1 : -1 ;
        this.longitude = r.nextDouble() * 90 * longitudeNumber;
        
    }

    /**
     * @return the latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Generating a  random Latitude 
     */
    private void setRandomLatitude() {
        
        // generating a boolean value and change it to 1 or -1
        int latitudeNumber = r.nextBoolean() ? 1 : -1 ;
        this.latitude = r.nextDouble() * 90 * latitudeNumber;
        
    }

    /**
     * @return the speed
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * Generating a  random speed 
     */
    private void setRandomSpeed() {
        this.speed = r.nextInt(361);
    }
    
    public String toString(){
        return "" + this.id;
    }
    

}
