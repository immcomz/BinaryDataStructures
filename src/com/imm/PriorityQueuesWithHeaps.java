package com.imm;

public class PriorityQueuesWithHeaps {
    private Heaps heap = new Heaps();

    public void enqueue(int item){
        //O(log n)
        heap.insert(item);
    }
    public int dequeue(){
        //O(log n)
        return heap.remove();
    }
    public boolean isEmpty(){
        return heap.isEmpty();

    }
}
