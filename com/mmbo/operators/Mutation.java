package com.mmbo.operators;
import java.util.Arrays;

import com.mmbo.Go;
import com.mmbo.Solution;

/**
 * The `Mutation` class provides methods for applying mutation operations to a `Solution` object.
 */
public class Mutation {

    /**
     * Apply the specified mutation type with the given intensity to a solution.
     *
     * @param type      The type of mutation operation (SwapBest, SwapRandom, ScrambleSwap).
     * @param intensity The intensity of the mutation operation (a value between 0.0 and 1.0).
     * @param child     The child solution to be mutated.
     * @return A mutated child solution.
     */
    public static Solution applyMutation(Memeplex.Mutation type, double intensity, Solution child) {
        switch(type) {
            case SwapBest:
                if(Go.DEBUG_MODE){
                    System.out.println("SwapRandom case selected");
                }
                return swapRandom(intensity, child);
            case SwapRandom:
                if(Go.DEBUG_MODE){
                    System.out.println("SwapBest case selected");
                }
                return swapBest(intensity, child);
            case ScrambleSwap:
                if(Go.DEBUG_MODE){
                    System.out.println("ScrambleSwap case selected");
                }
                return scrambleSwap(intensity, child);
            default:
                if(Go.DEBUG_MODE){
                    System.out.println("No mutation applied");
                }
                return child;
        }
    }

    /**
     * Apply the Swap Random Elements Mutation to a child solution.
     *
     * @param intensity The intensity of the mutation operation (a value between 0.0 and 1.0).
     * @param child     The child solution to be mutated.
     * @return A mutated child solution using the Swap Random Elements Mutation.
     */
    private static Solution swapRandom(double intensity, Solution child) {
        //Calculate number of repetitions (5α)
        int repetitions = (int)(intensity*5);
        int[] permutation = child.getFeederConfiguration();
        int conf[] = new int[permutation.length]; 
        conf = permutation.clone();

        //Perform the mutation
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
        if(Go.DEBUG_MODE){
            System.out.println("Original solution ========>" + Arrays.toString(permutation));
            System.out.println("Mutated solution ========>" + Arrays.toString(conf) + "\n");
        }
        return new Solution(child, conf);
    }

    /**
     * Apply the Swap Best Elements Mutation to a child solution.
     *
     * @param intensity The intensity of the mutation operation (a value between 0.0 and 1.0).
     * @param child     The child solution to be mutated.
     * @return A mutated child solution using the Swap Best Elements Mutation.
     */
    private static Solution swapBest(double intensity, Solution child) {
        //Calculate number of repetitions (1000α^3)
        int repetitions = (int)(Math.pow(intensity,3)*1000);
        int[] permutation = child.getFeederConfiguration();
        int conf[] = new int[permutation.length];
        conf = permutation.clone();

        Solution mutatedSolution = child;

        //Perform the mutation
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

            //If the new solution is better than the mutated solution and the child, return the new solution
            if (mutatedSolution.compareTo(newSolution) < 0 && child.compareTo(newSolution) < 0) {
                if(Go.DEBUG_MODE){
                    System.out.println("Original solution ========>" + Arrays.toString(permutation));
                    System.out.println("New solution ========>" + Arrays.toString(conf));
                }
                return newSolution;
            } else {
                mutatedSolution = newSolution;
            }

        }
        if(Go.DEBUG_MODE){
            System.out.println("Original solution ========>" + Arrays.toString(permutation));
            System.out.println("New solution ========>" + Arrays.toString(conf) + "\n");
        }
        return mutatedSolution;
    }

    /**
     * Apply the Scramble Swap Mutation to a child solution.
     *
     * @param intensity The intensity of the mutation operation (a value between 0.0 and 1.0).
     * @param child     The child solution to be mutated.
     * @return A mutated child solution using the Scramble Swap Mutation.
     */
    private static Solution scrambleSwap(double intensity, Solution child) {
        //Calculate number of repetitions (5α)
        int repetitions = (int)(5*intensity);
        
        int[] permutation = child.getFeederConfiguration();
        int conf[] = new int[permutation.length];
        conf = permutation.clone();

        //Perform the mutation
        for (int i = 0; i < repetitions; i++) {
            int ex1, ex2;
            do {
                ex1 = 1 + (int) (Math.random() * Solution.getNumberOfTypes());
                ex2 = 1 + (int) (Math.random() * Solution.getNumberOfTypes());
            } while (ex1 < ex2);
            
            for (int j = 0; j < 2*repetitions; j++) {
                int node1 = ex1 + (int) (Math.random() * (ex2 - ex1));
                int node2 = ex1 + (int) (Math.random() * (ex2 - ex1));
                int temp = conf[node1];
                conf[node1] = conf[node2];
                conf[node2] = temp;
            }
        }
        if(Go.DEBUG_MODE){
            System.out.println("Original solution ========>" + Arrays.toString(permutation));
            System.out.println("Mutated solution ========>" + Arrays.toString(conf) + "\n");
        }
        return new Solution(child, conf);
    }

}