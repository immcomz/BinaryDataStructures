package com.imm;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
//	    AVLTree tree = new AVLTree();
//	    tree.insert(10);
//        tree.insert(20);
//        tree.insert(39);


//        tree.insert(10);
//        tree.insert(9);
//        tree.insert(9);
//        tree.insert(1);
//        tree.insert(6);
//        tree.insert(8);
//        tree.insert(10);
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

        //tree.traverseLevelOrder();
// heaps///////////////
       // var heap = new Heaps();
//        heap.insert(10);
//        heap.insert(5);
//        heap.insert(17);
//        heap.insert(4);
//       heap.insert(30);
//       heap.remove();
//        System.out.println("break point");

///////////////Sorting Heaps/////////////////
//        int [] numbers = {5,3,10,1,4,2,8};
//        for (var number : numbers )
//            heap.insert(number);
//        System.out.println(Arrays.toString(numbers));
//
//        for(var i =0 ; i<numbers.length; i++)
//            //Descending Order
//            numbers[i]= heap.remove(); //heap always remove max value
////
////        for(var i =numbers.length-1 ; i>=0; i--)
////            //Ascending Order
////            numbers[i]= heap.remove(); //heap always remove max value
//        System.out.println(Arrays.toString(numbers));

        ////////Heapify/////////////////
//        int [] numbers = {5,3,8,4,10,2};
//        Heaps.heapify(numbers);
//
//        //NOW ALWAYS ROOT HAS THE MAX VALUE COMPARE TO ITS BOTH CHILDREN
//        //             8
//        //         4       5
//        //       3   1    2
//        System.out.println(Arrays.toString(numbers));
//
//        System.out.println(Heaps.getKthLatrgest(numbers,7));

        //////Tries//////////
        var trie = new TriesWithHashMaps();
        trie.insert("car");

       trie.insert("care");
        trie.insert("can");
       trie.removeWord("car");
        trie.removeWord("care");
        //trie.insert("ass");
        //System.out.println("Debug");
       System.out.println(trie.contains("car"));
       System.out.println(trie.contains("care"));
        System.out.println(trie.contains("can"));
//        System.out.println("pre");
//        trie.preOrdertraverse();
//        System.out.println("post");
//        trie.postOrdertraverse();

    }
}
