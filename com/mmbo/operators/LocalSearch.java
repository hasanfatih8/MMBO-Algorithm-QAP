package com.mmbo.operators;

import com.mmbo.Go;
import com.mmbo.Solution;

/**
 * This class provides local search algorithms for improving solutions.
 */
public class LocalSearch {
    
    /**
     * Apply a local search algorithm based on the specified type.
     *
     * @param type  The type of local search algorithm to apply.
     * @param depth The depth or number of iterations for the local search.
     * @param child The initial solution to be improved.
     * @return The improved solution after applying the local search.
     */
    public static Solution applyLocalSearch(Memeplex.LocalSearch type, int depth, Solution child) {
        switch(type) {
            case SwapFirstII:
                if(Go.DEBUG_MODE){
                    System.out.println("SwapFirstII case selected");
                }
                return swapFirstII(depth, child);
            case SwapBestII:
                if(Go.DEBUG_MODE){
                    System.out.println("SwapBestII case selected");
                }
                return swapBestII(depth, child);
            default:
                if(Go.DEBUG_MODE){
                    System.out.println("No local search applied");
                }
                return child;
        }
    }


    /**
     * Apply the Swap-First Local Search algorithm to improve a solution.
     *
     * @param depth The depth or number of iterations for the local search.
     * @param child The initial solution to be improved.
     * @return The improved solution after applying the Swap-First Local Search.
     */
    private static Solution swapFirstII(int depth, Solution child) {
        if(Go.DEBUG_MODE){
            System.out.println("Child solution: " + child.getFeederConfiguration() + "Fitness(cost): " + child.getFitness());
        }
        Solution better = child;

        for(int i=0; i < depth; i++) {
            Mutation.applyMutation(Memeplex.Mutation.SwapRandom, 0.2, better);
            if(Go.DEBUG_MODE){
                System.out.println("Mutated solution(better)" + better.getFeederConfiguration() + "Fitness(cost): " + better.getFitness());   
            }
            if(better.getFitness() < child.getFitness()) {
                if(Go.DEBUG_MODE){
                    System.out.println("Better solution found" + better.getFeederConfiguration() + "Fitness(cost): " + better.getFitness() + "\n");
                }
                return better;
            }
        }
        return child;
    }

    /**
     * Apply the Swap-Best Local Search algorithm to improve a solution.
     *
     * @param depth The depth or number of iterations for the local search.
     * @param child The initial solution to be improved.
     * @return The improved solution after applying the Swap-Best Local Search.
     */
    private static Solution swapBestII(int depth, Solution child) {
        Solution temp = child;
        Solution best = child;

        for(int i=0; i < depth; i++) {
            if(Go.DEBUG_MODE){
                System.out.println("Before Mutation - temp: " + temp);
            }
            Mutation.applyMutation(Memeplex.Mutation.SwapRandom, 0.2, temp);
            if(Go.DEBUG_MODE){
                System.out.println("After Mutation - temp: " + temp);
            }
            if(temp.getFitness() < best.getFitness()){
                best = temp;
                if(Go.DEBUG_MODE){
                    System.out.println("Found a better solution - best: " + best);
                }
            }   
        }
        if(Go.DEBUG_MODE){
            System.out.println("Final best solution: " + best + "\n");
        }
        return best;
    }

}
