package com.imm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
            var edges= adjacencyList.get(node); //get accoding to key
            if(!edges.isEmpty())
                System.out.println(node + " is connected to " + edges);
        }
    }

}
