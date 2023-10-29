package com.mmbo.operators;
import com.mmbo.Solution;

public class Mutation {

    /*
     * Intensity is a value between 0.0-1.0, it determines how many times the operator will be repeated ( generally 0.2, 0.4 and 0.6 )
     */

    static Solution applyMutation(Memeplex.Mutation type, double intensity, Solution child) {
        switch(type) {
            case SwapBest:
                return swapRandom(intensity, child);
            case SwapRandom:
                return swapBest(intensity, child);
            case ScrambleSwap:
                return scrambleSwap(intensity, child);
            default:
                return child;
        }
    }

    // Repeats 5(intensity)  --> 5(0.2): 1 times
    private static Solution swapRandom(double intensity, Solution child) {
        int repetitions = (int)(intensity*5);
        
        int[] permutation = child.getFeederConfiguration();
        int conf[] = new int[permutation.length]; 
        conf = permutation.clone();

        for (int i = 0; i < repetitions; i++) {
            int ex1, ex2;
            ex1 = 1 + (int) (Math.random() * Solution.getNumberOfTypes());
            do {
                ex2 = 1 + (int) (Math.random() * Solution.getNumberOfTypes());
            } while (ex1 == ex2);

            int temp = conf[ex1];
            conf[ex1] = conf[ex2];
            conf[ex2] = temp;
        }
        return new Solution(child, conf);
    }

    // Repeats 1000(intensity^3)  --> 1000(0.2^3): 8 times
    private static Solution swapBest(double intensity, Solution child) {
        int repetitions = (int)(Math.pow(intensity,3)*1000);
        
        int[] permutation = child.getFeederConfiguration();
        int conf[] = new int[permutation.length];
        conf = permutation.clone();

        Solution mutatedSolution = child;

        for (int i = 0; i < repetitions; i++) {
            int ex1, ex2;
            ex1 = 1 + (int) (Math.random() * Solution.getNumberOfTypes());
            do {
                ex2 = 1 + (int) (Math.random() * Solution.getNumberOfTypes());
            } while (ex1 == ex2);
            

            int temp = conf[ex1];
            conf[ex1] = conf[ex2];
            conf[ex2] = temp;
 
            Solution newSolution = new Solution(mutatedSolution, conf); 

            if (mutatedSolution.compareTo(newSolution) < 0 && child.compareTo(newSolution) < 0) {
                return newSolution;
            } else {
                mutatedSolution = newSolution;
            }

        }
        return mutatedSolution;
    }

    /*
     *      3 9 2 1 5 6 2
     *        |     |
     *      3 9 2 1 5 6 2
     * 
     * Repeats 5(intensity) * 10(intensity)  --> 5(0.2) * 10(0.2): 2 times
     */
    private static Solution scrambleSwap(double intensity, Solution child) {
        int repetitions = (int)(5*intensity);
        
        int[] permutation = child.getFeederConfiguration();
        int conf[] = new int[permutation.length];
        conf = permutation.clone();

        for (int i = 0; i < repetitions; i++) {
            int ex1, ex2;
            do {
                ex1 = 1 + (int) (Math.random() * Solution.getNumberOfTypes());
                ex2 = 1 + (int) (Math.random() * Solution.getNumberOfTypes());
            } while (ex1 < ex2);
            
            for (int j = 0; j < 2*repetitions; j++) {

                int node1 = ex1 + 1 + (int) (Math.random() * (ex2 - ex1));
                int node2 = ex1 + 1 + (int) (Math.random() * (ex2 - ex1));
                // may be added check equality, find another random pair if it is
                
                int temp = conf[node1];
                conf[node1] = conf[node2];
                conf[node2] = temp;
            }

        }
        return new Solution(child, conf);
    }

}