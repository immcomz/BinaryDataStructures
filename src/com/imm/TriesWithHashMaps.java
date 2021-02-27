package com.imm;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//With HashMaps No wasting memories Like arrays (initialising 26 size array )
public class TriesWithHashMaps {
    private class Node{

        private char value;
        //children holds a character(K) and NODE(V)
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
        public boolean hasChildren(){
            return !children.isEmpty();// return opposit (!return size == 0) inorder to code make more readable
        }
        public void removeChild(Character ch){
            children.remove(ch);
        }

        //        root - children<'b', Node> - children<'a',Node('a')> - children<g,Node('g')>
        //         |
        //       children<'a',Node('a')>
        //         |
        //      children<'n',Node('n')>
        //         |
        //      children<'t',Node('t')>
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
        if(word == null) return false;

        var current = root;
        for(var ch: word.toCharArray()){ //iterate over ever ch in word
            //if current node doesn't have a 'ch'
            if(!current.hasAChild(ch))
                return false;
            //traversal
            current = current.getChild(ch);
        }
        //otherwise found the word / not return true
        // cause when remove some word its isEndOfWord=false not physically remove it
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
    public void removeWord(String word){
        if(word == null) return;

        removeWord(root,word,0);
    }
    private void removeWord(Node root,String word,int index){
        //base condition that terminate the recursion
        if(index == word.length()){ //not word.length()-1 because starts from empty root
            //visiting the last Node to Remove
            //but need to remove later when com back to its paren  root.removeChild(ch);
            System.out.println(root.value);
            root.isEndOfWord = false;
            return;
        }

        //1st get the 'ch' at the index of th word
        var ch = word.charAt(index);

        //get the child Node of  ch in root/children
        var child = root.getChild(ch);

        //if word doesn't exist/ no any Characters
        if(child == null) return;

        //post order traversal visiting child 1st
        removeWord(child,word,index+1); //recursively remove/ go to the next indexes of the word

        //then visit root to remove last index
        //System.out.println(root.value);

        // if the child doesn't have any children
        // or no any characters related to end of the removing word
        //ex: cant remove word 'CAT' cause 'CATEGORY' exist.
        System.out.println(root.value);
        //if doesnt have any children and it's not the end of another word, physically remove it
        if(!child.hasChildren() && !child.isEndOfWord)
           root.removeChild(ch);

    }

   // Auto Completion

   //                                       c
  //                                        a
   //                                       r - last node of prefix
   //                              start recursion
   //                                      / \
  //  (prefix+node(d).value) isEndOfWord  d   e (prefix+node(e).value)
  //                      f(prefix+node(e).value+node(f).value)
  //                             u
   //                             l - isEndOfWord

    public List<String> findWords(String prefix){
        List<String> words = new ArrayList<>();
        var lastNode = findLastNodeOf(prefix);
        //start recursion
        findWords(lastNode,prefix,words);
        return words;
    }
    private void findWords(Node root, String prefix,List<String> words ){
        //recursion method //pre-order Traversals

        //if this current node represent an endOfWord (or word)
        if(root.isEndOfWord)
            words.add(prefix); // ex 'car' as prefix

        //visit the children of this current/root node
        for(var child: root.getChildren())
            findWords(child,prefix + child.value,words); // 'card' and 'careful'

    }
    private Node findLastNodeOf(String prefix){
        var current = root;
        // loop over all the ch in this preix String
        for(var ch: prefix.toCharArray()){
            //get the current node for this character
            var child = current.getChild(ch);
            if(child == null) return null; // no any prefix word i  Trie
            current = child; // otherwise set current to this child
        }
        return current; // last node of prefix
    }
}

