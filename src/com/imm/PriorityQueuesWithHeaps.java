package com.imm;

public class PriorityQueuesWithHeaps {
    private Heaps heap = new Heaps();

    public void enqueue(int item){
        heap.insert(item);
    }
    public int dequeue(){
        return heap.remove();
    }
    public boolean isEmpty(){
        return heap.isEmpty();

    }
}
