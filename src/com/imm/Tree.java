package com.imm;


import java.util.ArrayList;

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
        //use PostOrder approach cause go to leaves first/ Base Condition
        //1+max(height(L),height(R))
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
    //Olog(n) cause Travers Half of the Tree of Binary Search Tree(LeftSide)
    public int minInBinarySearchTree(){
        var current = root;
        var last = current;

        while(current != null){
            last = current;
            // trverse to left subtree leaf cause binary search tree
            current = current.leftChild;
        }
        return last.value; // last leaf of left binary Tree which has the lowest number
    }
    public int min(){
        return min(root);
    }
    //O(n) cause traverse all over the tree
    private int min(Node root){
        //use PostOrder traversal approach cause go to leaves first
        //      root
        //      /  \
        //   left  right
        // min= Math.min(Math.min(left,right),root)

        //Base Condition
        if(isLeaf(root)) return root.value;

        var left = min(root.leftChild);
        var right = min(root.rightChild);

        return Math.min(Math.min(left,right),root.value);
    }

    public boolean equals(Tree other){
        if(other == null) return false;//other tree is null
        return equals(root, other.root);

    }
    private boolean equals(Node first, Node second){
        //Use Pre Order Traversal Approach/ Base Condition/ visit root first
        //both trees are null
        if(first == null && second == null) return true;
        //when nodes has value
        if(first != null && second != null){
            return first.value == second.value
                    //at the same time check the left and right
                    // //subtrees(root.left , root.right) recursively
                    && equals(first.leftChild, second.leftChild)
                    && equals(first.rightChild, second.rightChild);
        }
        return false;// first.value != second.value
    }

    public boolean isBinarySearchTree(){
        //root can be any value so min and max can be any value of Integer
        return isBinarySearchTree(root,Integer.MIN_VALUE,Integer.MAX_VALUE);
    }
    private boolean isBinarySearchTree(Node root,int min,int max){
        //                 min   .    max
        //           -infinity <root> +infinity
        //                       20
         //                    /   \
         //   ( -infinity,root-1) (root+1, +infinity)
         //                  /       \
         //                10        40

        //check the root. if null is a binary search tree
         //pre order travels base condition
         if(root == null) return true;
            // validate the root in the correct range (min < root < max)
         if(root.value < min  || root.value >max) return false;

         return isBinarySearchTree(root.leftChild,min,root.value-1)
                 && isBinarySearchTree(root.rightChild,root.value+1,max);

     }

    public ArrayList<Integer> getNodesAtDistance(int distance){
        //get list of all nodes at distance
        var list = new ArrayList<Integer>();
        getNodesAtDistance(root,distance,list);
        return list;
    }
    private void getNodesAtDistance(Node root,int distance,ArrayList<Integer> list){
        // ex print distance level at 2
        //       20  distance from root= 0 = distance to target =2(2-0) where 2=distance example
        //      /   \
        //    10    21 distance from root =1 distance to target =1(2-1)
        //   /  \  /  \
        //  18  9  9  10 distance from root = 2 distance to target =0(2-1-1)
        if(root == null) return;

        //base condition
        if(distance == 0){
            list.add(root.value);
            //System.out.println(root.value);
            return;
        }
        //recursively go 1step(distance-1) down
        getNodesAtDistance(root.leftChild,distance -1,list);
        getNodesAtDistance(root.rightChild,distance -1,list);

    }

}
