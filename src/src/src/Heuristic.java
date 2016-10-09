/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

/**
 *
 * @author reboss
 */
public class Heuristic {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        int[] board = {1, 2, 3, 3, 4, 2, 2, 1, 3, 1}; 
        
        // n = 3, N = 10
        int[] testCase1 = {1, 1, 1, 2, 2, 2, 3, 3, 3, 0}; // h = 0
        int[] testCase2 = {2, 2, 3, 3, 1, 1, 2, 1, 3, 0}; // h = 5
        int[] testCase3 = {1, 1, 3, 3, 3, 2, 2, 0, 2, 1}; // h = 1
        
        // zero is only a wild card in the first set that it completes
        int[] testCase4 = {1, 3, 1, 3, 2, 3, 1, 0, 2, 2}; // h = 8
        
        // weird situation where the heuristic has the potential to become inconsistent
        // h = 3 but really we are 2 moves away if the uncovered cell is a 2
        int[] testCase5 = {1, 1, 2, 2, 2, 3, 3, 1, 3, 0}; 
        
        // doesn't get the correct heuristic but will still be admissible
        int[] testCase6 = {1, 2, 3, 2, 1, 2, 3, 2, 1, 0}; // h = 9
        
        // n = 2, N = 5
        int[] testCase7 = {1, 1, 2, 2, 0}; // h = 0
        int[] testCase8 = {1, 1, 2, 0, 2}; // h = 1
        
        // n = 4, N = 17
        int[] testCase9 = {1, 1, 1, 1, 2, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 4, 0}; // h= 0
        int[] testCase10 = {1, 1, 1, 1, 2, 2, 2, 2, 3, 4, 3, 3, 4, 4, 4, 3, 0}; // h= 5
        
        int[] testCase11 = {2, 2, 3, 3, 3, 1, 1, 1, 0, 2}; // h = 1 
        int[] testCase12 = {2, 2, 3, 3, 3, 0, 1, 1, 1, 2}; // h = 0
        
        System.out.println(evaluate(testCase1, 3, 10) == 0);
        System.out.println(evaluate(testCase2, 3, 10) == 5); // 5
        System.out.println(evaluate(testCase3, 3, 10) == 1);
        System.out.println(evaluate(testCase4, 3, 10) == 8);
        System.out.println(evaluate(testCase5, 3, 10)); // 3
        System.out.println("funny case = " +evaluate(testCase6, 3, 10));
        System.out.println(evaluate(testCase7, 2, 5) == 0 );
        System.out.println(evaluate(testCase8, 2, 5) == 1);
        System.out.println(evaluate(testCase9, 4, 17) == 0);
        System.out.println(evaluate(testCase10, 4, 17) == 5);
        
        System.out.println(evaluate(testCase11, 3, 10) == 1);
        System.out.println(evaluate(testCase12, 3, 10) == 0);

    }
    
    /**
     *
     * @param tiles
     * @param n = number of tiles with max value n
     * @param N = size of board
     * @return heuristic value; 0 if goal has been reached
     */
    public static int evaluate(int [] tiles, int n, int N){
        
        int i = 0;
        int nCount;
        int outOfPlace = 0;
        
        while (i < N){
            nCount = 1;
            int currentValue = tiles[i];
            
            while   ( tiles[(i + 1) % N] == currentValue ||
                    ( tiles[(i + 1) % N] == 0 ) ||
                    ( tiles[i % N] == 0 && tiles[(i+1) % N] == currentValue)){ 
                i++;
                nCount++;
            }
            if (nCount != n){
                //System.out.println(tiles[i%N]);
                // If current position is a zero AND current position - 1 is
                // equal to n, then 
                // the nth tiles are sorted followed by the open cell
                if (tiles[i%N] == 0 && tiles[(i+N-1) % N] == n){}
                else
                    outOfPlace++;
            }
            // account for incorrectly counting a wrapped yet sorted tile
            // when starting at index zero.  
            // The first time the array,
            // {1, 2, 2, 3, 3, 0, 1}, 
            // is iterated through, the 1 at index 0 will be counted 
            // as out of place because the algorithm doesn't wrap backwards to
            // check sorting.  
            // Once the iterator makes it to the end of the array, it will
            // wrap back around and see that the 1 is in fact sorted and 
            // will decrement the outOfPlace heuristic below. 
            if (i >= N && (nCount == n || nCount == n + 1))
                outOfPlace--; 
            i++;
        }
        return outOfPlace;
    }
    
    public static int[] getMoves(int[] board, int index, int n){
        int[] ret = new int[4];
        ret[0] = (index + 1) % n;
        ret[1] = (index - 1 + n) % n;
        ret[2] = (index + board[index]) % n;
        ret[3] = (index - board[index] + n) % n;
        return ret;
    }
    
}
