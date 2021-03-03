package com.imm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeightedGraph {
    private class Node {
        private String label;
        private List<Edge> edges = new ArrayList<>();

        public Node(String label) {
            this.label = label;
        }

        @Override
        public String toString() {
            return label;
        }

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
    private Map<Node,List<Edge>> adjacencyList = new HashMap<>(); // Now EAch Node has multiple Edges is Weighted Graph

    public void addNode(String lable){
        var node = new Node(lable);
        //avoid Duplication simpler using HashMap than iterate over an ArrayList find duplicates
        //put if any lable/node exist
        nodes.putIfAbsent(lable,node);
        adjacencyList.putIfAbsent(node,new ArrayList<>());

    }
    public void addEdges(String from, String to,int weight){
        //get the from & to nodes from Nodes Map to make connections
        var fromNode = nodes.get(from);
        if(fromNode == null) throw new IllegalStateException();
        var toNode = nodes.get(to);
        if(toNode == null) throw new IllegalStateException();

        //otherwise add the connection/edge
        adjacencyList.get(fromNode).add(new Edge(fromNode,toNode,weight));
        //Weighted undirected Graph has edge to To to From as well
        adjacencyList.get(toNode).add(new Edge(toNode,fromNode,weight));
    }
    public void print(){

        for(var node: adjacencyList.keySet()) {
            var edges= adjacencyList.get(node); //get according to key
            if(!edges.isEmpty())
                System.out.println(node + " is connected to " + edges);
        }
    }
}