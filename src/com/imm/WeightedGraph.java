package com.imm;

import java.util.*;

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

    public Path getShortestPath(String from, String to) {
        var fromNode = nodes.get(from);//starting Node
        if (fromNode == null)
            throw new IllegalArgumentException();

        var toNode = nodes.get(to); //end Node
        if (toNode == null)
            throw new IllegalArgumentException();

        Map<Node, Integer> distances = new HashMap<>();
        for (var node : nodes.values()) //add weights of each nodes to distance map
            distances.put(node, Integer.MAX_VALUE);
        distances.replace(fromNode, 0);//make starting node weight as 0

        Map<Node, Node> previousNodes = new HashMap<>();

        Set<Node> visited = new HashSet<>(); //to keep track of visited nodes/ avoid Duplication

        //priority queue to add node with priority(NodeEntries)
        //Highest Priority node will move to front
        PriorityQueue<NodeEntry> queue = new PriorityQueue<>(
                Comparator.comparingInt(ne -> ne.priority)
        );
        queue.add(new NodeEntry(fromNode, 0));//fromNode is the staring node(make weight 0)

        // A--3--B
        // |\    | \1
        // | \   |  \
        // 4  2  6    D
        // |   \ |  /
        // |    \| /5
        // C--1--D
        //breadth first traversal or travel root/parent first (starting node first)
        while (!queue.isEmpty()) {
            var current = queue.remove().node;
            visited.add(current); //add current to visited

            //now vist all the unvisited neighbours
            for (var edge : current.getEdges()) {
                if (visited.contains(edge.to)) //if current neighbour/edge already visited
                    continue; //skip to next neighbour
                //              = A +B
                var newDistance = distances.get(current) + edge.weight;// distance from starting node to current node + distance to its/current edge/neighbour
                if (newDistance < distances.get(edge.to)) { //A+B(3) < A+D+B (2+6+8) or Visit B Via A < Visit B Via D followed by A
                    //update the distance table with shortest path from to current node
                    distances.replace(edge.to, newDistance);
                    previousNodes.put(edge.to, current);
                    queue.add(new NodeEntry(edge.to, newDistance));
                }
            }
        }

        return buildPath(previousNodes, toNode);
    }

    private Path buildPath(
            Map<Node, Node> previousNodes, Node toNode) {

        Stack<Node> stack = new Stack<>();
        stack.push(toNode);
        var previous = previousNodes.get(toNode);
        while (previous != null) {
            stack.push(previous);
            previous = previousNodes.get(previous);
        }

        var path = new Path();
        while (!stack.isEmpty())
            path.add(stack.pop().label);

        return path;
    }


}