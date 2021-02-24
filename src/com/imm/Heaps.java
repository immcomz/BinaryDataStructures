package com.imm;

public class Heaps {
    // Charadteristics childs < parent | values filled from lef to right
    //  Balanced  20 (max value)        20 (max value) Not Balanced
    //        5       15             5       15
    //      4   5    6            4     5   ?    6
    //if parent < child swap the indexes/ bubble up indexes
    //

    private int[] items = new int[10];
    private int size; //to keep track of number of items in an array/heap

    public void insert(int value) {

        if(isFull()) throw new IllegalStateException();
        items[size++] = value;
        buubleUp();

    }
    private boolean isFull(){
        return size == items.length;
    }
    private void buubleUp(){
        var index = size - 1; //index of last value
        var parentIndex = parent(index);
        // as long as newItem >its parent, BubbleUP
        while (index > 0 && items[index] > items[parentIndex]) {
            swap(index,parentIndex);
            //       50          50
            //    40    =>    60 i-1/2 //decrement index
            // 60 i         40
            //decrement index
            index = parent(index);

        }
    }

    private int parent(int index){
        return (index -1) / 2;
    }
    //swap if the parent < its childs
    private void swap(int first, int second){
        var temp = items[first];
        items[first] = items[second];
        items[second] = temp;
    }
}
