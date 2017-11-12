/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.pkg2;

import java.util.ArrayList;

/**
 *
 * @author Mahmoud
 */

public class BTree {
    
    public int searchingLoops = 0, maxLoops = 0;
    
    private int t;
    
    //ids of all devices
    private ArrayList<Integer> ids;
    private int pos = 0;
    
    BTreeNode root;

    public BTree(int t) {
        this.t = t;
        root = null;
        ids = new ArrayList<>();
    }

    public void add(TrackingDevice device) {
        // Tree is Empty, then add a new elemet to root
        if (root == null) {
            root = new BTreeNode(true);
            root.keys[0] = device;
            root.n++;
        }
        // Tree is Not Empty
        else {
            /*
            * The root is Full
            * create a new node and add the root as it's first child
            * make the new node as a root
            * split the first child which was the root !
            * Try to insert the device to the root
            */
            if (root.n == 2 * t - 1) {
                BTreeNode ch = new BTreeNode(false);
                ch.child[0] = root;
                root = ch;
                root.splitChild(0, root);
                root.insert(device);
            } else {
                root.insert(device);
            }
        }
    }

    public TrackingDevice get(int id) {
        searchingLoops = 0;
        return root.search(id);
    }
    
    public Integer[] getKeys() {
        root.travers();
        pos = 0;
        return ids.toArray(new Integer[0]);
    }
    

    /*
    * This class is responsible for creating and splitting nodes 
    * and also for traversing in btree elements
    */
    class BTreeNode {
        //the Array of Tracking Device Elements
        TrackingDevice[] keys;
        
        //Children Nodes of each node in the BTree
        BTreeNode[] child;
        
        //Number of keys in the Node
        int n;
        
        boolean isLeaf;

        // Constructor
        BTreeNode(boolean leaf) {
            keys = new TrackingDevice[2 * t - 1];
            child = new BTreeNode[2 * t];
            isLeaf = leaf;
            n = 0;
        }

        private TrackingDevice search(int id) {
            int i = 0;
//            for (i = 0; i < n; i++) {
//                searchingLoops++;
//                if(searchingLoops > maxLoops) {
//                    maxLoops = searchingLoops;
//                }
//                if (keys[i].getId() >= id)
//                        break;
//            }
/*
            int start = 0, end = this.n - 1, mid;
            
            //Apply binary search on node
            while(start <= end) {
                searchingLoops++;
                
                if(searchingLoops > maxLoops) maxLoops = searchingLoops;
                
                /*
                * First key in iteration is greater than id 
                * means we have to move to to next child of that key
                
                if(this.keys[start].getId() > id && !this.isLeaf) {
                    return this.child[start].search(id);
                }
                // last elemtnt less than id, move to node after end
                else if( this.keys[end].getId() < id && !this.isLeaf) {
                    return this.child[end+1].search(id);
                }
                
                mid = (start + end) / 2;
                if(keys[mid].getId() == id) {
                    return this.keys[mid];
                }
                
                if(id > this.keys[mid].getId()) { start = mid + 1; }
                else { end = mid - 1; }
            }
            return null;
            */
            return null;
        }

        /*
        * the insert method won't be accessed until the node
        * that called it is not full of elemets
        */
        private void insert(TrackingDevice device) {
            if (isLeaf) {
                int x = n;
                // move elemtns by one position until the device 
                // is not smaller than keys
                while (x > 0 && keys[x - 1].getId() >= device.getId()) {
                    
                    //update the element if we found it by id
                    if(keys[x - 1].getId() == device.getId()) {
                        this.keys[x - 1] = device;
                        return;
                    }
                    this.keys[x] = this.keys[x - 1];
                    x--;
                }
                // Add device in its right position
                this.keys[x] = device;
                n++;
                
            } else {
                int x = 0;
                /*
                * Keep adding 1 to x until we found the index of child
                * which is larger than the device
                */
                while (x < n && this.keys[x].getId() < device.getId()) {
                    x++;
                }
                
                // Child is full
                if (this.child[x].n == (2 * t - 1))
                {
                    //split child in poition x and add middle value to parent [this]
                    // parent won't be full !!!!
                    splitChild(x, this);
                    
                    //try to insert device to parent again to check the right position for device
                    this.insert(device);
                    
                } else {
                    child[x].insert(device);
                }
            }
        }

        private void splitChild(int childPos, BTreeNode parent) {
            // middle position in the full child
            int mid = parent.child[childPos].n / 2;
            
            int x = parent.n;
            //move all keys after the position of new elemtn from child by one position
            while (x > childPos) {
                parent.keys[x] = parent.keys[x - 1];
                //the bug was here 
                parent.child[x+1] = parent.child[x];
                x--;
            }
            
            //put middle elemtn of child in parent
            parent.keys[x] = parent.child[childPos].keys[mid];
            
            //free the middle element
            parent.child[childPos].keys[mid] = null;
            
            int tempChildN = parent.child[childPos].n;
            parent.child[childPos].n--;
            parent.n++;
            
            //new node contains keys and children after middle in child
            BTreeNode nbtn = new BTreeNode(true);
            
            int i = 0;
            while (mid < tempChildN - 1) { 
                nbtn.child[i] = child[childPos].child[++mid];
                nbtn.keys[i++] = child[childPos].keys[mid];
                parent.child[childPos].n--;
                parent.child[childPos].keys[mid] = null;
            }
            
            //add last child to new node
            nbtn.child[i] = child[childPos].child[++mid];
            nbtn.n = i;
            parent.child[childPos + 1] = nbtn;
            if (child[childPos].isLeaf) {
                    nbtn.isLeaf = true;
            } else {
                    nbtn.isLeaf = false;
            }
        }
        
        private void travers() {
            
            int i;
            //loop through every child in this node
            for(i = 0; i < this.n; i++) {
                //if this node has children, loop through each one
                if(! this.isLeaf) {
                    this.child[i].travers();
                }
                //add 
                ids.add(this.keys[i].getId());
            }
            
            //if this node has children traverse through the last child
            if(this.isLeaf == false) {
                this.child[i].travers();
            }
            
            
        }
    }
}


