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


}
