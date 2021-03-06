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
    //         Adjacency List
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
       // Breadth-first Traversal (Iterative) Using Queue (F[]R)
       // C<-A->B->D-       Start From A
       // ^         |       print A and visit its unvisited neighbours B and C F[B,C]R
       // |_________|       print B and visit its unvisited neighbours D   F[C,D]R
       //                   print C and visit its unvisited neighbours Null F[D]R
       //                   print D and visit its unvisited neighbours C, but already visited C finally stop.
        var node = nodes.get(root);
        if (node == null)
            return;

        Set<Node> visited = new HashSet<>(); //holds visited nodes

        Queue<Node> queue = new ArrayDeque<>(); //
        queue.add(node);

        while (!queue.isEmpty()) {
            var current = queue.remove();

            if (visited.contains(current)) //already have visited to this parent/root
                continue; ///go/skip to the next root/parent

            //otherwise
            System.out.println(current);
            visited.add(current);

            //now visit current's unvisited neighbours
            for (var neighbour : adjacencyList.get(current))
                if (!visited.contains(neighbour)) //haven't visited to this neighbour before
                    queue.add(neighbour); //add to queue
        }
    }
    public List<String> topologicalSort() {
    //  >A
    // /  \
    //X    > P
    // \  /
    //  >B

        Stack<Node> stack = new Stack<>();
        Set<Node> visited = new HashSet<>();

        // depth first travels every node in graph
        for (var node : nodes.values())
            topologicalSort(node, visited, stack);
        //now stack contains the all the topological Nodes in a Reverse Order
        List<String> sorted = new ArrayList<>();
        while (!stack.empty())
            sorted.add(stack.pop().lable); // pop them into srted and make them in correct order

        return sorted;//return sorted
    }

    private void topologicalSort(
            Node node, Set<Node> visited, Stack<Node> stack) {
        //reffer to graph.txt

        //return from recursive method if already visited
        if (visited.contains(node))
            return;

        //otherwise add node to visited
        visited.add(node);

        //visit all neighbours recursively
        for (var neighbour : adjacencyList.get(node))
            topologicalSort(neighbour, visited, stack);
        // once finish traverse to all child/neighbours push them in to stack
        //last children in the current node
        stack.push(node); //P,A,B,X where p-deepest node while X is start
    }
    public boolean hasCycle() {

        //  >B
        // /  \
        //A <- > C
        // \  /
        //  D
        Set<Node> all = new HashSet<>();
        all.addAll(nodes.values());//copy all the nodes to all set

        Set<Node> visiting = new HashSet<>(); // currently trying to visiting
        Set<Node> visited = new HashSet<>();

        while (!all.isEmpty()) {//check each and every nodes in set weather has a cycle or not
            var current = all.iterator().next();
            if (hasCycle(current, all, visiting, visited))
                return true;//if its a cycle
        }

        return false;//other wise no cycle
    }

    private boolean hasCycle(Node node, Set<Node> all,
                             Set<Node> visiting, Set<Node> visited) {
        all.remove(node);
        visiting.add(node); //move all.remove(node) to currently visiting set

        //visit all of the neighbours of this node
        for (var neighbour : adjacencyList.get(node)) {
            //if already visited a node
            if (visited.contains(neighbour))
                continue;
            //trying to visiting twice which already trying to visiting  //ex c->A
            if (visiting.contains(neighbour))
                return true;
            //recursively visit neighbours of this neighbour
            if (hasCycle(neighbour, all, visiting, visited))// similar to hasCycle(neighbour, all, visiting, visited)==true
                return true;
        }
        //move node from visiting to visited
        visiting.remove(node);
        visited.add(node);

        return false;//no cycle found
    }
}
