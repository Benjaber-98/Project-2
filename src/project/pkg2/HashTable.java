/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.pkg2;

import java.util.LinkedList;

/**
 *
 * @author Mahmoud
 */
public class HashTable {
    
    private int size = 100;
    private LinkedList<TrackingDevice>[] devices = new LinkedList[size]; 
    public int searchingLoops = 0, maxLoops = 0;
    
    
    
    public boolean add(TrackingDevice device){
        int index = hashId(device.getId());
        if(devices[index] == null) {
            devices[index] = new LinkedList();
        }
        devices[index].add(device);
        return true;
    }
    
    public TrackingDevice get(int id) {
        searchingLoops = 0;
        int pos = hashId(id);
        
        if(devices[pos] == null) return null;
        
        for(TrackingDevice t : devices[pos]) {
            searchingLoops++;
            if(searchingLoops > maxLoops) maxLoops = searchingLoops;
            if(t.getId() == id) {
                return t;
            }
        }
        
        return null;
    }
    
    private int hashId(Integer num) {
        
        int code = num.hashCode();
//        long hash = 3L;
//        String id = String.valueOf(num);
//        for (int i = 0; i < id.length(); i++) {
//            hash = hash*id.charAt(i) + 3;
//        }
        
        return (int)(code % (int)size);
    }
    
    public Integer[] getKeys() {
        Integer[] keys = new Integer[100];
        int pos = 0;
        for(LinkedList<TrackingDevice> l : devices) {
            if(l == null) continue;
            for(TrackingDevice t : l ) {
                keys[pos++] = t.getId();
            }
        }
        
        return keys;
    }
    
}
