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

public class BTree {
    //Degree of BTree
    int t;
    
    private int ids[];
    private int pos = 0;
    
    BTreeNode root;

    BTree(int t) {
        this.t = t;
        root = null;
        ids = new int[100];
    }

    public void add(TrackingDevice device) {
        // Tree is Empty, then add a new elemet to root
        if (root == null) {
            root = new BTreeNode(t, true);
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
                BTreeNode ch = new BTreeNode(t, false);
                ch.child[0] = root;
                root = ch;
                root.splitChild(0, root, t);
                root.insert(device, t);
            } else {
                root.insert(device, t);
            }
        }
    }

    public TrackingDevice get(int id) {
        return root.search(id);
    }
    
    public int[] getKeys() {
        root.travers();
        pos = 0;
        return ids;
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
        BTreeNode(int t, boolean leaf) {
            keys = new TrackingDevice[2 * t - 1];
            child = new BTreeNode[2 * t];
            isLeaf = leaf;
            n = 0;
        }

        private TrackingDevice search(int id) {
            int i = 0;
            for (i = 0; i < n; i++) {
                if (keys[i].getId() >= id)
                        break;
            }
            if (i < n && keys[i].getId() == id)
                return this.keys[i];
            if (isLeaf == true)
                return null;
            return child[i].search(id);
        }

        /*
        * the insert method won't be accessed until the node
        * that called it is not full of elemets
        */
        private void insert(TrackingDevice device, int t) {
            if (isLeaf) {
                int x = n;
                // move elemtns by one position until the device 
                // is not smaller than keys
                while (x > 0 && keys[x - 1].getId() > device.getId()) {
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
                while (x < n && this.keys[x].getId() < device.getId())
                    x++;
                
                // Child is full
                if (this.child[x].n == (2 * t - 1))
                {
                    //split child in poition x and add middle value to parent [this]
                    // parent won't be full !!!!
                    splitChild(x, this, t);
                    
                    //try to insert device to parent again to check the right position for device
                    this.insert(device, t);
                    
                } else {
                    child[x].insert(device, t);
                }
            }
        }

        private void splitChild(int ce, BTreeNode parent, int t) {
            // middle position in the full child
            int mid = parent.child[ce].n / 2;
            
            int x = parent.n;
            //move all keys after the position of new elemtn from child by one position
            while (x > ce) {
                    parent.keys[x] = parent.keys[x - 1];
                    //the bug was here 
                    parent.child[x+1] = parent.child[x];
                    x--;
            }
            
            //put middle elemtn of child in parent
            parent.keys[x] = parent.child[ce].keys[mid];
            
            //free the middle element
            parent.child[ce].keys[mid] = null;
            
            int tempChildN = parent.child[ce].n;
            parent.child[ce].n--;
            parent.n++;
            
            //new node contains keys and children after middle in child
            BTreeNode nbtn = new BTreeNode(t, true);
            
            int i = 0;
            while (mid < tempChildN - 1) { 
                    nbtn.child[i] = child[ce].child[++mid];
                    nbtn.keys[i++] = child[ce].keys[mid];
                    parent.child[ce].n--;
                    parent.child[ce].keys[mid] = null;
            }
            
            //add last child to new node
            nbtn.child[i] = child[ce].child[++mid];
            nbtn.n = i;
            parent.child[ce + 1] = nbtn;
            if (child[ce].isLeaf != false) {
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
                ids[pos++] = this.keys[i].getId();
            }
            
            //if this node has children traverse through the last child
            if(this.isLeaf == false) {
                this.child[i].travers();
            }
            
            
        }
    }
}


