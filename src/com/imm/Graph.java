package com.imm;

import java.util.*;

public class Graph {
    private class Node{
        String lable;

        public Node(String lable) {
            this.lable = lable;
        }

        @Override
        public String toString() {
            return lable;
        }
    }
   //       ---------------------
   //  node | ArrayList<Node> which holds Edges/connections
   //       ---------------------
   //node'A'| [B,X,Y}           |
   //       ---------------------
   //node'Z'| [t,u]             |
   //       ---------------------
    Map<String, Node> nodes = new HashMap<>();
    Map<Node, ArrayList<Node>> adjacencyList = new HashMap<>();

    public void addNode(String lable){
        var node = new Node(lable);
        //avoid Duplication simpler using HashMap than iterate over an ArrayList find duplicates
        //put if any lable/node exist
        nodes.putIfAbsent(lable,node);
        adjacencyList.putIfAbsent(node,new ArrayList<>());

    }
    public void addEdges(String from, String to){
        //get the from & to nodes from Nodes Map to make connections
        var fromNode = nodes.get(from);
        if(fromNode == null) throw new IllegalStateException();
        var toNode = nodes.get(to);
        if(toNode == null) throw new IllegalStateException();

        //otherwise add the connection/edge
        adjacencyList.get(fromNode).add(toNode);
    }
    public void print(){

        for(var node: adjacencyList.keySet()) {
            var edges= adjacencyList.get(node); //get according to key
            if(!edges.isEmpty())
                System.out.println(node + " is connected to " + edges);
        }
    }
    public void removeNode(String lable){
        //remove everything Nodes and Connections/edges
        //get the node if its available
        var node = nodes.get(lable);
        if(node == null) return;

        //node'A' ---------------------
        //node'B'| [B,A,Y}           |
        //       ---------------------
        //node'C'| [t,A]             |
        //       ---------------------
        //ex : remove('A')
        for(var n: adjacencyList.keySet())
            adjacencyList.get(n).remove(node);// remove all the connections/edges to all nodes/lables in Arraylist value in Map
        //node'A' ---------------------
        //node'B'| [B,Y}           |
        //       ---------------------
        //node'C'| [t]             |
        //       ---------------------
        adjacencyList.remove(node);
        //node'B'| [B,Y}           |
        //       ---------------------
        //node'C'| [t]             |
        //       ---------------------
        nodes.remove(lable);
    }
    public void removeEdges(String from, String to){
        var fromNode = nodes.get(from);
        if(fromNode == null) throw new IllegalStateException();
        var toNode = nodes.get(to);
        if(toNode == null) throw new IllegalStateException();

        adjacencyList.get(fromNode).remove(toNode);
    }
    //Depth-First Traversal Using Recursive
    public void traverseDepthFirstRecursive(String root){
        var node = nodes.get(root);
        if(node == null) return;
        //otherwise start travel
        traverseDepthFirstRecursive(node, new HashSet<>());
    }
    private void traverseDepthFirstRecursive(Node root, Set<Node> visited){
        //Set keep the track of visited nodes/ edges

        // visit root Node
        System.out.println(root);

        //now mark as Visited the root
        visited.add(root);

        //now recursively visit all the neighbours of this root Node

        //  A->B ->C   start from A (!visited.contains(node)) visit B & E Edges in adjacencyList recursively
        //  |      |   traverse C and its edges
        //  v      v   Finally doesn't travel E cause its already travelled (visited.contains(node)=true)
        //  E <----D
        //
        for(var node: adjacencyList.get(root)) { //return ArrayList Nodes
            if (!visited.contains(node)) // if haven't visited this node/
                traverseDepthFirstRecursive(node, visited); // visit recursively this node and its unvisited neighbours/connections

        }
    }
    public void traverseDepthFirstIterate(String root){
        var node = nodes.get(root);
        if(node == null) return;

        Set<Node> visited = new HashSet<>();

        Stack<Node> stack = new Stack<>();
        stack.push(node);

        while(!stack.isEmpty()){
            var current = stack.pop();

            if(visited.contains(current)) continue; // have visited this before get another node from stack
            //or jump to next node // avoid duplications to the set

            //otherwise
            System.out.println(current); //print current node
            visited.add(current);//add it to visited haven't visited

            //now visit unvisited neighbours of current
            for(var neighbour: adjacencyList.get(current)) {
                //System.out.println("adjacencyList.get(current)"+neighbour);
                if(!visited.contains(neighbour)) //haven't visited this node/neighbour before
                    stack.push(neighbour);
            }
        }
    }
    public void traverseBreadthFirst(String root) {
        var node = nodes.get(root);
        if (node == null)
            return;

        Set<Node> visited = new HashSet<>();

        Queue<Node> queue = new ArrayDeque<>();
        queue.add(node);

        while (!queue.isEmpty()) {
            var current = queue.remove();

            if (visited.contains(current))
                continue;

            System.out.println(current);
            visited.add(current);

            for (var neighbour : adjacencyList.get(current))
                if (!visited.contains(neighbour))
                    queue.add(neighbour);
        }
    }
    public List<String> topologicalSort() {
        Stack<Node> stack = new Stack<>();
        Set<Node> visited = new HashSet<>();

        for (var node : nodes.values())
            topologicalSort(node, visited, stack);

        List<String> sorted = new ArrayList<>();
        while (!stack.empty())
            sorted.add(stack.pop().lable);

        return sorted;
    }

    private void topologicalSort(
            Node node, Set<Node> visited, Stack<Node> stack) {
        if (visited.contains(node))
            return;

        visited.add(node);

        for (var neighbour : adjacencyList.get(node))
            topologicalSort(neighbour, visited, stack);

        stack.push(node);
    }
}
