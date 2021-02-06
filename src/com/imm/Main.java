package com.imm;

public class Main {

    public static void main(String[] args) {
	    Tree tree = new Tree();
	    tree.insert(7);
        tree.insert(4);
        tree.insert(9);
        tree.insert(1);
        tree.insert(6);
        tree.insert(8);
        tree.insert(10);
//
//        Tree tree2 = new Tree();
//        tree2.insert(7);
//        tree2.insert(4);
//        tree2.insert(9);
//        tree2.insert(1);
//        tree2.insert(6);
//        tree2.insert(8);
//        tree2.insert(10);


      //  System.out.println(tree.find(4));
//        Recursion rec = new Recursion();
//        System.out.println(rec.factorialUsingRecursion(4));

        //tree.traversePostOrder();
        //System.out.println("height "+tree.height());
        //System.out.println("min "+tree.min());
       // System.out.println("equals "+tree.equals(tree2));
        //System.out.println("is a binary search tree "+tree.isBinarySearchTree());
//        System.out.println("node at kth distance");
//        var list = tree.getNodesAtDistance(1);
//        for(var item: list){
//            System.out.println(item);
//        }
//        System.out.println("stop");

        tree.traverseLevelOrder();
    }
}
