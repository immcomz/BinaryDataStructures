package com.imm;


import java.util.HashMap;
//With HashMaps No wasting memories Like arrays (initialising 26 size array )
public class TriesWithHashMaps {
    private class Node{

        private char value;
        private HashMap<Character,Node> children = new HashMap<>();
        private boolean isEndOfWord;

        public Node(char value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "value=" + value;
        }
        //Abstraction to access to children (hiding internal implementation of Node class to Trie class)
        //Robust code
        public boolean hasAChild(Character ch){
            return children.containsKey(ch);
        }

        //Add children to HashMap with given Character
        public void addChild(Character ch){
            children.put(ch,new Node(ch));
        }

        //get children to given Character
        public Node getChild(Character ch){
            return children.get(ch);
        }
        public Node[] getChildren(){
            //return values with Node Array
            return children.values().toArray(new Node[0]);
        }
    }

    //Every Trie has Empty Root/Empty Character
    private Node root = new Node(' ');

    public void insert(String word){

        var current = root;//make current as root which is empty character
        for(var ch : word.toCharArray()){

            //if current doesn't have a child with key of current ch(character)
            if(!current.hasAChild(ch))
                current.addChild(ch); //then add child
            current = current.getChild(ch); // otherwise to the next children if current has value of ch
        }
        current.isEndOfWord = true;
    }
    public boolean contains(String word){
        if(word ==null) return false;

        var current = root;
        for(var ch: word.toCharArray()){ //iterate over ever ch in word
            //if current node doesn't have a 'ch'
            if(!current.hasAChild(ch))
                return false;
            //traversal
            current = current.getChild(ch);
        }
        //otherwise found the word / return true
        return current.isEndOfWord;
    }
    public void preOrdertraverse(){
        preOrdertraverse(root);
    }
    private void preOrdertraverse(Node root){
        //Pre-Order: Visit the root first then children
        System.out.println(root.value);
        for(var child : root.getChildren())
            preOrdertraverse(child); //recursively travels other childrens in current child
    }

    public void postOrdertraverse(){
        postOrdertraverse(root);
    }
    private void postOrdertraverse(Node root){
        //Post-Order: Visit the children first then root
        for(var child : root.getChildren())
            postOrdertraverse(child); //recursively travels other childrens in current child

        //finally visit root
        System.out.println(root.value);

    }
}

