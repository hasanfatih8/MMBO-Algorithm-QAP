package com.mmbo.operators;

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
    static Solution applyLocalSearch(Memeplex.LocalSearch type, int depth, Solution child) {
        switch(type) {
            case SwapFirstII:
                return swapFirstII(depth, child);
            case SwapBestII:
                return swapBestII(depth, child);
            default:
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

        Solution better = child;

        for(int i=0; i < depth; i++) {
            Mutation.applyMutation(Memeplex.Mutation.SwapRandom, 0.2, better);   
            if(better.getFitness() < child.getFitness()) {
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
            Mutation.applyMutation(Memeplex.Mutation.SwapRandom, 0.2, temp);
            if(temp.getFitness() < best.getFitness()){
                best = temp;
            }   
        }
        return best;
    }

}
