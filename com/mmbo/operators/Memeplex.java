package com.mmbo.operators;
/**
 * The `Memeplex` class defines enums for different types of crossover, mutation, and local search operations.
 */
public class Memeplex {
    /**
     * Enum for different crossover types.
     */
    enum Crossover {
        PMX,
        OX,
        CX
    }
    /**
     * Enum for different mutation types.
     */
    enum Mutation {
        SwapRandom,
        SwapBest,
        ScrambleSwap
    }
    /**
     * Enum for different local search types.
     */
    enum LocalSearch {
        SwapFirstII,
        SwapBestII
    }
}
