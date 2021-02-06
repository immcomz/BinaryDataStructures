package com.imm;

public class AVLTree {
    private class Node{
        private int value;
        private int leftChild;
        private int rightChild;

        public Node(int value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "Node"+value;
        }
    }

    private Node root;


}
