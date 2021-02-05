package com.imm;



public class Tree {
    private class Node {
        private int value;
        private Node leftChild;
        private Node rightChild;

        public Node(int value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "Node=" + value;
        }
    }

    private Node root;

    public void insert(int value) {
        //new node to insert
        var node = new Node(value);
        //if tree is empty create a root
        if (root == null) {
            root = node;
            return;
        }

        var current = root; //root = current node b4 travese
        while (true) {
            //value < current value, travels to left tree
            if (value < current.value) {
                //if there is no left node *
                if (current.leftChild == null) {
                    // create a new node at lrft  with value **
                    current.leftChild = node;
                    break;
                }
                // go down to left child tree/ trvelse
                current = current.leftChild;
            }
            // value > current value, travels to right tree opposite to left branch
            else {
                //if there is no right node *
                if (current.rightChild == null) {
                    // create a new node at right with value **
                    current.rightChild = node;
                    break;
                }
                // go down to left child tree/ trvelse
                current = current.rightChild;
            }
        }
    }

    public boolean find(int value){
        var current = root;

        while(current != null){ // travels end of to the tree
            if(value < current.value)
                current = current.leftChild; // go to  left side tree
            else if(value > current.value)  // go to  right side tree
                current = current.rightChild;
            else
                //System.out.println("current: "+current.value);
                return true; // value = current.value
        }
        return false; //some wehere at current=null
    }

    public void traversePreOrder(){
        //System.out.println("root "+root);
        traversePreOrder(root);
    }
    public void traverseInOrder(){
        traverseInOrder(root);
    }
    public void traversePostOrder(){
        traversePostOrder(root);
    }
    private void traversePreOrder(Node root){
        //first traverse the root and then leaves of it

        //1.root (print)
        //2.visit left recursively call itself
        //3.visit right recursively call itself

        //Base Condition
        if(root == null) return;
        System.out.println(root.value); //1
        traversePreOrder(root.leftChild); //2
        traversePreOrder(root.rightChild); //3

    }
    private void traverseInOrder(Node root){
        //1.visit left recursively call itself
        //2.root print recursively call itself
        //3.visit right

        //Base Condition
        if(root == null) return;

        traversePreOrder(root.leftChild); //1
        System.out.println(root.value); //2
        traversePreOrder(root.rightChild); //3
    }
    private void traversePostOrder(Node root){
        //first traverse the leaves and then root of them

        //1.visit left recursively call itself
        //2.visit right recursively call itself
        //3.root print

        //Base Condition
        if(root == null) return;

        traversePreOrder(root.leftChild); //1
        traversePreOrder(root.rightChild); //2
        System.out.println(root.value); //2

    }

    public int height(){
        return height(root);
    }
    private int height(Node root){
        //when tree is empty
        if(root==null) return -1;
        //Base Condition
        //when reaches a leaf node return height of its root as 0
        if(isLeaf(root)) return 0;
        return 1+ Math.max(height(root.rightChild),height(root.rightChild));
    }

    private boolean isLeaf(Node root){
        return  (root.leftChild == null && root.rightChild==null) ;
    }


}
