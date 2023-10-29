package com.mmbo.operators;

public class Memeplex {
    enum Crossover {
        PMX,
        OX,
        CX
    }

    enum Mutation {
        SwapRandom,
        SwapBest,
        ScrambleSwap
    }

    enum LocalSearch {
        SwapFirstII,
        SwapBestII
    }
}
