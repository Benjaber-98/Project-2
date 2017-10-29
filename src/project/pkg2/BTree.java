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
    //Degree of BTree
    int t;
    
    BTreeNode root;

    BTree(int t) {
        this.t = t;
        root = null;
    }

    public void add(TrackingDevice device) {
        // Tree is Empty
        if (root == null) {
            root = new BTreeNode(t, true);
            root.keys[0] = device;
            root.n++;
        }
        // Tree is Not Empty
        else {
            // The root Node is full
            if (root.n == 2 * t - 1) {
                BTreeNode ch = new BTreeNode(t, false);
                ch.child[0] = root;
                root = ch;
                ch.splitChild(0, root, t);
                root.insert(device, t);
            } else {
                root.insert(device, t);
            }
        }
    }

    public TrackingDevice get(int id) {
        return root.search(id);
    }

    class BTreeNode {
        TrackingDevice[] keys;
        BTreeNode[] child;
        int n;
        boolean leaf;

        // Constructor
        BTreeNode(int t, boolean l) {
            keys = new TrackingDevice[2 * t - 1];
            child = new BTreeNode[2 * t];
            leaf = l;
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
            if (leaf == true)
                return null;
            //System.out.println(i);
            return child[i].search(id);
        }

        private void insert(TrackingDevice device, int t) {
            if (leaf) {
                int x = n;
                while (x > 0 && keys[x - 1].getId() > device.getId()) {
                        keys[x] = keys[--x];
                }
                this.keys[x] = device;
                n++;
            } else {
                int x = 0;
                while (x < n && keys[x].getId() < device.getId())
                    x++;
                if (child[x].n == (2 * t - 1))// Child is full
                {
                    splitChild(x, this, t);
                    int i = x;//i=1
                    int m = this.child[x].n - 1;//m=1
                    if (this.child[x].keys[m].getId() < device.getId())
                            i++;
                    this.insert(device, t);
                    // if(child[x].leaf==true)
                    // child[x+1].leaf=true;
                } else {
                    child[x].insert(device, t);
                }
            }
        }

        private void splitChild(int ce, BTreeNode parent, int t) {
            int mid = (parent.child[ce].n / 2);
            int x = parent.n;
            while (x > ce) {
                    parent.keys[x] = parent.keys[x - 1];
                    x--;
            }
            parent.keys[x] = parent.child[ce].keys[mid];
            parent.child[ce].keys[mid] = null;
            int tempChildN = parent.child[ce].n;
            parent.child[ce].n--;//n=4
            parent.n++;//n=2
            BTreeNode nbtn = new BTreeNode(t, true);
            int i = 0;
            // int j = mid;
            while (mid < tempChildN - 1) { // mid = 2
                    nbtn.child[i] = child[ce].child[++mid];
                    nbtn.keys[i++] = child[ce].keys[mid];
                    parent.child[ce].n--;//n=2
                    parent.child[ce].keys[mid] = null;
            }
            nbtn.child[i] = child[ce].child[++mid];
            nbtn.n = i;//i=3
            parent.child[ce + 1] = nbtn;
            if (child[ce].leaf != false) {
                    nbtn.leaf = true;
            } else {
                    nbtn.leaf = false;
            }
        }
    }
}


