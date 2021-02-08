package com.imm;

public class AVLTree {
    private class AVLNode{
        private int height;
        private int value;
        private AVLNode leftChild;
        private AVLNode rightChild;

        public AVLNode(int value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "value"+this.value;
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

        //cakculating each node's height
        root.height = Math.max(
                height(root.leftChild),
                height(root.rightChild) +1);

        //finaly return the root value/left or right child
        return root;
    }
    private int height(AVLNode node){
        //this metod make sure the value of node's height (wheater empty or nor)
        return node == null ? -1 : node.height;
    }

}
