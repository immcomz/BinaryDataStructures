package com.imm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeightedGraph {
    private class Node {
        private String label;
        //add edge list in Node class instead of adjencyList
        //now edges automatially get initialised
        private List<Edge> edges = new ArrayList<>();

        public Node(String label) {
            this.label = label;
        }

        @Override
        public String toString() {
            return label;
        }
        //add edge to this/current object
        public void addEdge(Node to, int weight) {
            edges.add(new Edge(this, to, weight));
        }

        public List<Edge> getEdges() {
            return edges;
        }
    }

    private class Edge {
        private Node from;
        private Node to;
        private int weight;

        public Edge(Node from, Node to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        @Override
        public String toString() {
            return from + "->" + to; // A->B
        }
    }
    private Map<String,Node> nodes = new HashMap<>();
    //Remove adjacencyList to make the implementation in more OO
   // private Map<Node,List<Edge>> adjacencyList = new HashMap<>(); // Now EAch Node has multiple Edges is Weighted Graph

    public void addNode(String lable){
        //var node = new Node(lable);
        //avoid Duplication simpler using HashMap than iterate over an ArrayList find duplicates
        //put if any lable/node exist
        nodes.putIfAbsent(lable,new Node(lable));

        //remove adjacencyList
        //adjacencyList.putIfAbsent(node,new ArrayList<>());

    }
    public void addEdges(String from, String to,int weight){
        //get the from & to nodes from Nodes Map to make connections
        var fromNode = nodes.get(from);
        if(fromNode == null) throw new IllegalStateException();
        var toNode = nodes.get(to);
        if(toNode == null) throw new IllegalStateException();

        //otherwise add the connection/edge
        fromNode.addEdge(toNode,weight);

        //remove adjacencyList
        //adjacencyList.get(fromNode).add(new Edge(fromNode,toNode,weight));

        //Weighted undirected Graph has edge to To to From as well
        toNode.addEdge(fromNode,weight);

        //remove adjacencyList
        //adjacencyList.get(toNode).add(new Edge(toNode,fromNode,weight));
    }
    public void print(){

        //remove adjacencyList
//        for(var node: adjacencyList.keySet()) {
//            var edges= adjacencyList.get(node); //get according to key
//            if(!edges.isEmpty())
//                System.out.println(node + " is connected to " + edges);
//        }
        for(var node: nodes.values()) {
            var edges= node.getEdges(); //get according to key
            if(!edges.isEmpty())
                System.out.println(node + " is connected to " + edges);
        }

    }

    //NodeEntry class to determine the priority level for a Node
    //Now Node class hasn't polluted by priority field
    private class NodeEntry {
        private Node node;
        private int priority;

        public NodeEntry(Node node, int priority) {
            this.node = node;
            this.priority = priority;
        }
    }


}