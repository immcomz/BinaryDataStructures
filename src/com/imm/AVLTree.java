package com.imm;

public class AVLTree {
    private class AVLNode{
        private int height;
        private int value;
        private AVLNode leftChild;
        private AVLNode rightChild;

        public AVLNode(int value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "value"+this.value;
        }
    }

    private AVLNode root;

    public void insert(int value){
        root = insert(root,value);
    }
    private AVLNode insert(AVLNode root,int value){
        //      10(rot)
        //    /         \
        //  Null(LC)    Null(RC)

        //      10(rot)
        //    /         \
        //  4(LC)    Null(RC)

        //      10(rot)
        //    /         \
        //  4(LC)    20(RC)

        //base condition
        if(root == null)
            return new AVLNode(value);
        //if(root.value == value) return root;

        if(value < root.value) //go to left side recursively
            root.leftChild = insert(root.leftChild,value);
        else //go to right side recursively
            root.rightChild = insert(root.rightChild,value);

        //calculating each node's height
        setHeight(root);

        //Calculating the Balance Factor &-
        //-finaly return the balance root /left or right child
        return balance(root);
    }

    private AVLNode balance(AVLNode root){

        if(isLeftHeave(root)){ //when Left Heavy

            //Detect two Rotation 1st leftRotate when balanceFactor < 0
            if(balanceFactor(root.leftChild)<0)
                root.leftChild = rotateLeft(root.leftChild); //Left Rotate
            //case of skew/ second rotation
            return rotateRight(root);
        }
        else if(isRightHeavy(root)) { //when right Heavy
            //           1st Rotation   2nd Rotation
            //   10        10
            //      30 =>    20            20
            //   20            30       10    30

            //Detect two Rotation 1st rightRotate when balanceFactor > 0
            if(balanceFactor(root.rightChild)>0)
                root.rightChild = rotateRight(root.rightChild);//Right Rotate 30->20
            //case of skew/ second rotation
            return rotateLeft(root);
        }
        //if balanced
        return root;

    }
    private AVLNode rotateLeft(AVLNode root){
      // root                          newRoot = root.right
       // 10                                   20
       //   newRoot             newRoot.left=root
       //     20                            10     30
       // 15      30                            15 =newRoot.left = root.right

        //step 1
        var newRoot = root.rightChild; //10->20

        //step 2
        //   10 -(root)
        //  /  \
        //null  15 - (newRoot.leftChild)
        root.rightChild = newRoot.leftChild;

        //step 3
        //      20 = newRoot
        //    /    \
        //   10  }
        //  /  \  } = root/newRoot.leftChild
        //     15 }
        newRoot.leftChild = root;

        //step 4
        setHeight(root);
        setHeight(newRoot);
        return newRoot;
    }
    private AVLNode rotateRight(AVLNode root){
        var newRoot = root.leftChild;

        root.leftChild = newRoot.rightChild;
        newRoot.rightChild = root;

        setHeight(root);
        setHeight(newRoot);

        return newRoot;
    }
    private void setHeight(AVLNode node){
        node.height = Math.max(
                height(node.leftChild),
                height(node.rightChild) +1);

    }

    //BF >1 => Left Heavy
    //BF <-1 => Right Heavy
    private boolean isLeftHeave(AVLNode node){
        return balanceFactor(node) >1;
    }
    private boolean isRightHeavy(AVLNode node){
        return balanceFactor(node) < -1;
    }
    private int balanceFactor(AVLNode node){
        return (node==null) ? 0 : height(node.leftChild) - height(node.rightChild);
    }
    private int height(AVLNode node){
        //this metod make sure the value of node's height (wheater empty or nor)
        return node == null ? -1 : node.height;
    }


}
