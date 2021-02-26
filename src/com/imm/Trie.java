package com.imm;

public class Trie {
    private class Node{

        private static final int ALPHEBET_SIZE = 26;
        private char value;
        private Node[] children = new Node[ALPHEBET_SIZE];
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
        //       root(Empty)
        //        |
        //  children[1,     2,        3,4,5.......26]
        //           |      |         |
        //    children[26]  |         |
        //             children[26]   |
        //                         children[1.......26]
        //                                  |
        //                              children[26]
        //example of inserting BOY
        //              root
        //           children[1,B,3....26]
        //                      |
        //        children[1,..,O,16]
        //                      |
        //       children[1,...,Y,26]
        var current = root;//make current as root which is empty character
        for(var ch : word.toCharArray()){
            var index = ch - 'a'; // index to current character in inserting word
            //if current children at index = null, create a new node with character
            if(current.children[index] == null)
                current.children[index] = new Node(ch);
            current = current.children[index]; // GO to the next children if its not null
        }
        current.isEndOfWord = true;
    }
}

