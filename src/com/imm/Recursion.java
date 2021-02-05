package com.imm;

public class Recursion {
    //Factorial Numbers
    //f(n) n x f(n-1)
    //f(6) 6 x f(5)
    //f(5)     5 x 4 x 3 x 2 x 1
    //f(4)         4 x 3 x 2 x 1
    //f(3)             3 x 2 x 1

    public int factorialUsingIteration(int n){
        var factorial=1;
        for(int i=n; i>0; i--){
            factorial *= i;
        }
        return factorial;
    }
    //in Recursion Calling the method itself. Java uses Stacks keep track of values
    public int factorialUsingRecursion(int n){
        //Base Condition / recursion termination condition otherwise this recursive forever
        //Base Cindition avod cicle
        if(n == 0) return 1;
        //else loop the recursion
        return n * factorialUsingRecursion(n-1);

    }
}
