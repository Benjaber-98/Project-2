/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.pkg2;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Mahmoud
 */
public class Project2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        TrackingDevice[] device = new TrackingDevice[100];
        Array array = new Array();
        Random r = new Random();
        Scanner in = new Scanner(System.in);
        Integer start = 400000, end = 600000;
        
        for(int i = 0; i < 100; i++) {
            device[i] = new TrackingDevice();
        }
        
        /*
            generating 50 random id from TrackingDevice Array 
            and then generate 50 random id not exists in TrackingDevice Array
        */
        
        Integer ids[] = new Integer[100];
        Integer x[] = new Integer[100];
        for(int i = 0; i < 100; i++) {
            x[i] = device[i].getId();
        }

        for(int i = 0; i < 50; i++) {
            ids[i] = device[r.nextInt(100)].getId();
        }
        
        for(int i = 50; i < 100; i++) {
            ids[i] = TrackingDevice.generateRandomId();
        }
        //Shuffle the array that generated 
        Collections.shuffle(Arrays.asList(ids));
        
        //Work With Array Class
        
        System.out.println("------------ Start The Array ------------");
        
        long startTime = System.currentTimeMillis();
        for(int i = 0; i < 100; i++) {
            array.add(device[i]);
        }
        
        long endTime = System.currentTimeMillis();
        long arrayAddingTime = endTime - startTime;
        
        startTime = System.currentTimeMillis();
        for(int i = 0; i < 100; i++) {
            if(array.get(ids[i]) != null) {
                System.out.println("Device with ID " + ids[i] + " is Found !");
            } else {
                System.out.println("Device with ID " + ids[i] + " isn't Found !");
            }
        }
        
        
        endTime = System.currentTimeMillis();
        long arrayGetTime = endTime - startTime;

        startTime = System.currentTimeMillis();
        System.out.println("Devices in range from " + start + " to " + end);
        for(Integer i = start; i <= end; i++) {
            TrackingDevice d = array.get(i);
            if(d != null) {
                System.out.println("Device with ID " + d.getId() + " is Found!");
            }
        }
        
        endTime = System.currentTimeMillis();
        long arrayRangeSearchTime = endTime - startTime;
        
        System.out.println("Ids in ascending order : ");
        
        startTime = System.currentTimeMillis();
        {
            Integer sorted[] = new Integer[100];
            System.arraycopy(array.getIds(), 0, sorted, 0, 100);
            Arrays.sort(sorted);
            for(int i = 0; i < 100; i++) {
                System.out.println(sorted[i]);
            }
        }
        endTime = System.currentTimeMillis();
        long arraySortingTime = endTime - startTime;

        
        System.out.println("------------ End The Array ------------");
        
        System.out.println("------------ Start The HashTable ------------");
        
        
        HashTable hashTable = new HashTable();
        
        startTime = System.currentTimeMillis();
        for(int i = 0; i < 100; i++) {
            hashTable.add(device[i]);
        }
        endTime = System.currentTimeMillis();
        long hashTableAddingTime = endTime - startTime;
        
        startTime = System.currentTimeMillis();
        for(int i = 0; i < 100; i++) {
            if(hashTable.get(ids[i]) != null) {
                System.out.println("Device with ID " + ids[i] + " is Found !");
            } else {
                System.out.println("Device with ID " + ids[i] + " isn't Found !");
            }
        }
        endTime = System.currentTimeMillis();
        long hashTableGetTime = endTime - startTime;
        
        System.out.println("Devices in range from " + start + " to " + end);
        startTime = System.currentTimeMillis();
        for(Integer i = start; i <= end; i++) {
            TrackingDevice d = hashTable.get(i);
            if(d != null) {
                System.out.println("Device with ID " + d.getId() + " is Found!");
            }
        }
        endTime = System.currentTimeMillis();
        long hashTableRangeSearchTime = endTime - startTime;
        
        System.out.println("Ids in ascending order : ");
        startTime = System.currentTimeMillis();
        {
            Integer sorted[] = new Integer[100];
            System.arraycopy(hashTable.getKeys(), 0, sorted, 0, 100);
            Arrays.sort(sorted);
            for(int i = 0; i < 100; i++) {
                System.out.println(sorted[i]);
            }
        }
        endTime = System.currentTimeMillis();
        long hashTableSortingTime = endTime - startTime;

        
        System.out.println("------------ End The HashTable ------------");
        
        System.out.println("------------ Start The BTree ------------");
        
        
        BTree btree = new BTree(3);
        
        startTime = System.currentTimeMillis();
        for(int i = 0; i < 100; i++) {
            btree.add(device[i]);
        }
        endTime = System.currentTimeMillis();
        long btreeAddingTime = endTime - startTime;
        
        startTime = System.currentTimeMillis();
        for(int i = 0; i < 100; i++) {
            if(btree.get(ids[i]) != null) {
                System.out.println("Device with ID " + ids[i] + " is Found !");
            } else {
                System.out.println("Device with ID " + ids[i] + " isn't Found !");
            }
        }
        endTime = System.currentTimeMillis();
        long btreeGetTime = endTime - startTime;
        
        System.out.println("Devices in range from " + start + " to " + end);
        startTime = System.currentTimeMillis();
        for(Integer i = start; i <= end; i++) {
            TrackingDevice d = hashTable.get(i);
            if(d != null) {
                System.out.println("Device with ID " + d.getId() + " is Found!");
            }
        }
        endTime = System.currentTimeMillis();
        long btreeRangeSearchTime = endTime - startTime;
        
        System.out.println("Ids in ascending order : ");
        startTime = System.currentTimeMillis();
        {
            Integer sorted[] = btree.getKeys();
            for(int i = 0; i < sorted.length; i++) {
                System.out.println(sorted[i]);
            }
        }
        endTime = System.currentTimeMillis();
        long btreeSortingTime = endTime - startTime;

        
        System.out.println("------------ End The BTree ------------");
        
        System.out.println("------- Array ------");
        
        System.out.println("Adding time : " + arrayAddingTime + "ms");
        System.out.println("Searching time : " + arrayGetTime + "ms");
        System.out.println("Searching in range time : " + arrayRangeSearchTime + "ms");
        System.out.println("Sorting time : " + arraySortingTime + "ms");
        System.out.println("Average time for all operations : " + ((arrayAddingTime + arrayGetTime + arrayRangeSearchTime + arraySortingTime) / 4) + "ms");
        System.out.println("Max Number of Loops in searching : " + array.maxLoops);

        
        System.out.println("------- HashTable ------");
        
        System.out.println("Adding time : " + hashTableAddingTime + "ms");
        System.out.println("Searching time : " + hashTableGetTime + "ms");
        System.out.println("Searching in range time : " + hashTableRangeSearchTime + "ms");
        System.out.println("Sorting time : " + hashTableSortingTime + "ms");
        System.out.println("Average time for all operations : " + ((hashTableAddingTime + hashTableGetTime + hashTableRangeSearchTime + hashTableSortingTime) / 4) + "ms");
        System.out.println("Max Number of Loops in searching : " + hashTable.maxLoops);
        
        System.out.println("------- BTree ------");
        
        System.out.println("Adding time : " + btreeAddingTime + "ms");
        System.out.println("Searching time : " + btreeGetTime + "ms");
        System.out.println("Searching in range time : " + btreeRangeSearchTime + "ms");
        System.out.println("Sorting time : " + btreeSortingTime + "ms");
        System.out.println("Average time for all operations : " + ((btreeAddingTime + btreeGetTime + btreeRangeSearchTime + btreeSortingTime ) / 4) + "ms");
//        System.out.println("Number of Loops while Adding : " + btree.addDevicesLoops);
        System.out.println("Number of Loops while Searching : " + btree.searchingLoops);
//        System.out.println("Number of Loops while getting all keys : " + btree.getKeysLoops);

        
        TrackingDevice d[] = new TrackingDevice[10000];
        for(int i = 0; i < d.length; i++) {
            d[i] = new TrackingDevice();
        }
        BTree b = new BTree(3);
        int w[] = new int[d.length];
        for(int i = 0; i < d.length; i++) {
            b.add(d[i]);
            w[i] = d[i].getId();
        }
        
        System.out.println("---------------");
        
        for(int i = 0; i < d.length; i++) {
            b.get(w[i]);
        }
        
        Integer[] y = b.getKeys();
        System.out.println(y.length);
        
        System.out.println("Max Loops : " + b.maxLoops);
        
        
    }
    
}
