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
    public int remove(){
        //heap empty
        if(isEmpty()) throw new IllegalStateException();

        //remove root node(index 0) and move last node to root node/ index 0
        var root = items[0];
        items[0] =items[size-1];
        size--;
        //or  items[0] =items[--size];  decrement 1st and --size = similar to size-1

        bubbleDown();

        return root;
    }
    private void bubbleDown(){
        // bubbleDown items[root] < children
        var index = 0;
        while(index < size && !(isValidParent(index))){ //items[root] < children
            var largerChildIndex = largerChildIndex(index);
            swap(index,largerChildIndex);
            //reset index to larger to continusly bubbleDown if items[root] < children
            index = largerChildIndex;

        }
    }
    public boolean isEmpty(){
        return size == 0;
    }
    private int largerChildIndex(int index){
        // if there is no left child that means there is no right child,
        if(!hasLeftChild(index)) return index; // - only parent avaulable

        //if there is no right child return left child index
        if(!hasrightChild(index)) return leftChildIndex(index);

        //otherwise compare left and right child finally return larger index
        return  (leftChild(index) > rightChild(index)) ?
                leftChildIndex(index): rightChildIndex(index);
    }
    private boolean hasLeftChild(int index){
        //valid left child if index <= size of heap
       return leftChildIndex(index) <= size;
    }
    private boolean hasrightChild(int index){
        return rightChildIndex(index) <= size;
    }
    private boolean isValidParent(int index){
        if(!hasLeftChild(index)) return true; //no childs

        var isValid = items[index] >= leftChild(index); // hasleftchild
        if(hasrightChild(index))
            //isValid = isValid & items[index] >= rightChild(index); // both child
            isValid &= items[index] >= rightChild(index);

        return isValid;
    }
    private int rightChild(int index){
        return items[rightChildIndex(index)];
    }
    private int leftChild(int index){
        return items[leftChildIndex(index)];
    }
    private int leftChildIndex(int index){
        return index * 2 + 1;
    }
    private int rightChildIndex(int index){
        return index * 2 + 2;
    }
    private boolean isFull(){
        return size == items.length;
    }
    private void buubleUp(){
        var index = size - 1; //index of last value

        // as long as current index item >its parent, BubbleUP
        while (index > 0 && items[index] > items[parent(index)]) {
            swap(index,parent(index));
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
    public int max(){
        if(isEmpty()) throw new IllegalStateException();
        return items[0];
    }

    //Heapify////
    public static void heapify(int[] array) {
        //index of last parent
        //       0
        //   1       2 -> last Parent
        // 3   4   5   6
        var lastParentIndex = array.length / 2 - 1;
        //use last parent to optimise the loop instead of iterate entrie array
        for (var i = lastParentIndex; i >= 0; i--)
            heapify(array, i);
    }
    private static void heapify(int[] array, int index) {
        var largerIndex = index; //Assumption

        var leftIndex = index * 2 + 1;
        if (leftIndex < array.length &&
                array[leftIndex] > array[largerIndex])
            largerIndex = leftIndex; //set larger Index to left if left > current index

        var rightIndex = index * 2 + 2;
        if (rightIndex < array.length &&
                array[rightIndex] > array[largerIndex])
            largerIndex = rightIndex;

        //base Condition
        // if it's a heap
        if (index == largerIndex) // root > its both children or opposite to above conditions
            return;

        swap(array, index, largerIndex);// otherwise swap two inexes
        heapify(array, largerIndex); //Recursively hepify other elements in
        // Array an go down the sub tree (index = largerIndex | heapify(array, largerIndex))
    }

    private static void swap(int[] array, int first, int second) {
        //swap to indexes in an array
        var temp = array[first];
        array[first] = array[second];
        array[second] = temp;
    }

    ///get Kth Largerst Item
    //           10         k=1->remove(10)      8   K=2 -> remove(8)    k=3 -> remove(5)
    //       5        8                       5    2                     remove() k times
    //      4  3     2                     4    3

    public static int getKthLatrgest(int []array,int k){
        if(k<=0 || k>array.length) throw new IllegalStateException();
        var heap = new Heaps();
        for(var number: array)
            heap.insert(number);

        for(var i=0 ; i<k-1 ;i++)
            heap.remove();
        return heap.max();
    }
}
