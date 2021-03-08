package com.imm;

import java.util.*;
//Undirected Weighted Graphs
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

        //distance HashTable to al Nodes except the starting node
        Map<Node, Integer> distances = new HashMap<>();
        for (var node : nodes.values()) //add weights of each nodes to distance map
            distances.put(node, Integer.MAX_VALUE);
        distances.replace(fromNode, 0);//make starting node weight as 0

        Map<Node, Node> previousNodes = new HashMap<>();// shortest distance from previous node

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
        // 4  2  6    E
        // |   \ |  /
        // |    \| /5
        // C--1--D
        //breadth first traversal or travel root/parent first (starting node first)
        while (!queue.isEmpty()) {
            var current = queue.remove().node;
            visited.add(current); //add current to visited
            System.out.println("current "+current);
            //now visit all the unvisited neighbours
            for (var edge : current.getEdges()) {
                if (visited.contains(edge.to)) //if current neighbour/edge already visited
                    continue; //skip to next neighbour
                //   current distance = A +B
                var newDistance = distances.get(current) + edge.weight;// distance from starting node to current node + distance to its/current edge/neighbour
                System.out.println("newDistance " + "from " +current + " to "+edge.to+ " is "+newDistance);
                //compare with new distance with currently recorded of distance of it neighbour(edge.to is considered as a neighbour)
                //currently at D and try to visit B(D.B) via D followed by A (A->D->B) > A.B
                if (newDistance < distances.get(edge.to)) { //ex: A+B(3)(previously recorded) < A+D+B (2+6+8) or Visit B Via A < Visit B Via D followed by A =false
                    //update the distance table with shortest path from to current node
                    distances.replace(edge.to, newDistance);// update edge's neighbour distance to new Distance
                    previousNodes.put(edge.to, current);// set previous node to current.edge
                    queue.add(new NodeEntry(edge.to, newDistance));//lowest distance node will come to the Front
                }
            }
        }

       return buildPath(previousNodes, toNode);
        //return distances.get(nodes.get(to));
    }

    private Path buildPath(
            Map<Node, Node> previousNodes, Node toNode) {
        //node  distance  previous
        //A        0
        //B        3        A
        //C        3        D
        //D        2        A
        //E        4        B

        Stack<Node> stack = new Stack<>();
        stack.push(toNode);//push E(toNode)
        //ex: path A-E
        var previous = previousNodes.get(toNode); //(toNode)E->B(previous)
        while (previous != null) {
            stack.push(previous);//push B then push A
            previous = previousNodes.get(previous); //B->A (previous of previous)
        }

        var path = new Path();
        while (!stack.isEmpty())
            path.add(stack.pop().label);//get the paths in to correct order by poping stack

        return path;
    }

    public boolean hasCycle(){
        Set<Node> visited = new HashSet<>();
        for(var node:nodes.values()){
            if(!visited.contains(node))// haven't visited this before
                if(hasCycle(node,null,visited)) return true;
        }
        return false;// cant find a cycle
    }

    private boolean hasCycle(Node node,Node parent, Set<Node> visited){

        //    A---B (Parent/previous A)   Visited - A A.B
        //     \  |                                 B.C C.A(visited) return true/has a cycle
        //      \ |
        //        C (Parent/previous B)

        //add to visited
        visited.add(node);

        for(var edge : node.getEdges()){
            if(edge.to == parent)// if neighbour trying to visiting where it came from(ex: C Visiting B (C.B) but B is parent/previous of C)
               continue; // ignore it

            if(visited.contains(edge.to)) return true; //already visited
            //recursively visit neighbours of edge(Depth First Traversal) , node beacome previous/parent now
            if(hasCycle(edge.to,node,visited)) return true;
        }
        return false; //no cycles
    }

    public WeightedGraph getMinimumSpanningTree() {
        // Weighted graph    Minimum Spanning Tree   starting node A ->  Tree.add(A)  -> add all edges of A to PQ [A->C,A->B]
        //    A--3--B           A     B              while(tree.nodes.size < graph.nodes.size) // or Tree.edges == n-1 n=total edges in graph //
        //    |   / |           |   / |                minEdge(A->C) = PQ.remove
        //    1  3  4           1  2  4                 nextNode-> minEdge.to.label
        //    | /   |           | /   |                     foreach()
        //    C--5--D           C     D                         if(!tree.contains(V))
        //                                                          add all edges of nextNode(C)

        var tree = new WeightedGraph();// new Minimum Spanning tree which will form from the graph

        if (nodes.isEmpty())
            return tree; //tree doesn't have any node

        PriorityQueue<Edge> edges = new PriorityQueue<>( //PQ which holds the all edges with wight
                Comparator.comparingInt(e -> e.weight) //lowest weight will come to front of Q
        );

        var startNode = nodes.values().iterator().next(); //ex: A
        edges.addAll(startNode.getEdges()); // add all edges of starting Node //ex: PQ[C,B]
        tree.addNode(startNode.label);// add starting node to tree T.add("A")

        if (edges.isEmpty())
            return tree; //tree doesn't have any edges
//Completion of initial Setup


        //Repeat, As long as tree doesn't have all the nodes in the graph
        while (tree.nodes.size() < nodes.size()) { // or  Tree.edges == n-1 n=total edges in graph

            var minEdge = edges.remove();//front has the lowest edge with node ex:A->C(weight=1)
            var nextNode = minEdge.to; // edgeNode of node //EX: C (A->C where C is the edge of A)

            if (tree.containsNode(nextNode.label))//already tree contains nextNode/ visited target node
                continue;

            //otherwise add this node to tree
            tree.addNode(nextNode.label);
            //add the edge ex: A->C from=A To=C weight=A->C
            tree.addEdges(minEdge.from.label,
                    nextNode.label, minEdge.weight);

            //add all the edges connected to this next node to Priority Q
            for (var edge : nextNode.getEdges())
                if (!tree.containsNode(edge.to.label)) //unless tree doesn't exist this node(edge.to.label) or already visited this node
                    edges.add(edge);
        }

        return tree;
    }

    public boolean containsNode(String label) {
        return nodes.containsKey(label);
    }
}