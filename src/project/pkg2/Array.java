/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.pkg2;

/**
 *
 * @author Mahmoud
 */
public class Array {
    
    private TrackingDevice[] devices;
    int conditions = 0, loops = 0;
    
    private int pos = 0;
    
    public Array() {
        devices = new TrackingDevice[100];
    }
    
    public boolean add(TrackingDevice device){
        if(pos >= 100 || pos < 0) {
            conditions++;
            return false;
        }
        devices[pos++] = device;
        return true;
    }
    
    public TrackingDevice get(Integer id) {
        
        for(TrackingDevice t: devices) {
            loops++;
            if(id.equals(t.getId())) {
                conditions++;
                return t;
            }
        }
        
        return null;
    }
    
    public Integer[] getIds(){
        Integer[] ids = new Integer[100];
        for(int i = 0; i< 100; i++) {
            ids[i] = devices[i].getId(); 
            loops++;
        }
        return ids;
    }
    
    
}
