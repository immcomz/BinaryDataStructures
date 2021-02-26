package com.imm;


import java.util.HashMap;
//With HashMaps No wasting memories Like arrays (initialising )
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
    }

    //Every Trie has Empty Root/Empty Character
    private Node root = new Node(' ');

    public void insert(String word){

        var current = root;//make current as root which is empty character
        for(var ch : word.toCharArray()){

            //if current children at index = null, create a new node with character
            if(current.children.get(ch) == null)
                current.children.put(ch,new Node(ch));
            current = current.children.get(ch); // GO to the next children if its not null
        }
        current.isEndOfWord = true;
    }
}

