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
        //if(root.value == value) return root;

        if(value < root.value) //go to left side recursively
            root.leftChild = insert(root.leftChild,value);
        else //go to right side recursively
            root.rightChild = insert(root.rightChild,value);

        //calculating each node's height
        root.height = Math.max(
                height(root.leftChild),
                height(root.rightChild) +1);

        //Calculating the Balance Factor
        //BF >1 => Left Heavy
        //BF <-1 => Right Heavy
        int BalanceFactor = balanceFactor(root);
        if(isLeftHeave(root)) System.out.println(root.value+" is Left heavy");
            else if(isRightHeavy(root)) System.out.println(root.value+" is Right heavy");

        //finaly return the root value/left or right child
        return root;
    }
    private boolean isLeftHeave(AVLNode node){
        return balanceFactor(node) >1;
    }
    private boolean isRightHeavy(AVLNode node){
        return balanceFactor(node) < -1;
    }
    private int balanceFactor(AVLNode node){
        return (node==null) ? 0 : height(node.leftChild) - height(node.rightChild);
    }
    private int height(AVLNode node){
        //this metod make sure the value of node's height (wheater empty or nor)
        return node == null ? -1 : node.height;
    }

}
