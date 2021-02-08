package com.imm;

public class AVLTree {
    private class AVLNode{
        private int value;
        private AVLNode leftChild;
        private AVLNode rightChild;

        public AVLNode(int value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "Node"+value;
        }
    }

    private AVLNode root;

    public void insert(int value){
        root = insert(root,value);
    }
    private AVLNode insert(AVLNode root,int value){
        //      10(rot)
        //    /         \
        //  Null(LC)    Null(RC)

        //      10(rot)
        //    /         \
        //  4(LC)    Null(RC)

        //      10(rot)
        //    /         \
        //  4(LC)    20(RC)

        //base condition
        if(root == null)
            return new AVLNode(value);

        if(value < root.value) //go to left side recursively
            root.leftChild = insert(root.leftChild,value);
        else //go to right side recursively
            root.rightChild = insert(root.rightChild,value);
        //System.out.println("inseerting root");
        return root;
    }
}
