package com.mmbo.operators;
/**
 * The `Memeplex` class defines enums for different types of crossover, mutation, and local search operations.
 */
public class Memeplex {
    /**
     * Enum for different crossover types.
     */
    public enum Crossover {
        PMX,
        OX,
        CX
    }
    /**
     * Enum for different mutation types.
     */
    public enum Mutation {
        SwapRandom,
        SwapBest,
        ScrambleSwap
    }
    /**
     * Enum for different local search types.
     */
    public enum LocalSearch {
        SwapFirstII,
        SwapBestII
    }
}
