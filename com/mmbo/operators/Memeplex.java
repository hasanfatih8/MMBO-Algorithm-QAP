package com.mmbo.operators;
import java.util.Random;
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
        CX,
        None
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

    private Crossover crossover;
    private Mutation mutation;
    private double mutationIntensity;
    private LocalSearch localSearch;
    private int depthOfLocalSearch;
    
    public Memeplex(Crossover crossover, Mutation mutation, double mutationIntensity, LocalSearch localSearch, int depthOfLocalSearch) {
        this.crossover = crossover;
        this.mutation = mutation;
        this.mutationIntensity = mutationIntensity;
        this.localSearch = localSearch;
        this.depthOfLocalSearch = depthOfLocalSearch;         
    }

    public Memeplex() {
        this.crossover = getRandomCrossover();
        this.mutation = getRandomMutation();
        this.mutationIntensity = getRandomMutationIntensity();
        this.localSearch = getRandomLocalSearch();
        this.depthOfLocalSearch = getRandomDepthOfLocalSearch();;         
    }


    public Crossover getRandomCrossover() {
        int random = (int) (Math.random() * 4);
        switch(random) {
            case 0:
                return Memeplex.Crossover.PMX;
            case 1:
                return Memeplex.Crossover.OX;
            case 2:
                return Memeplex.Crossover.CX;
            default:
                return Memeplex.Crossover.None;
        }
    }

    public double getRandomMutationIntensity() {
        double[] values = {0.2, 0.4, 0.6, 0.8, 1};
        int randomIndex = new Random().nextInt(values.length);
        return values[randomIndex];
    }

    public Mutation getRandomMutation(){
        int random = (int) (Math.random() * 3);
        switch(random) {
            case 0:
                return Memeplex.Mutation.SwapRandom;
            case 1:
                return Memeplex.Mutation.SwapBest;
            case 2:
                return Memeplex.Mutation.ScrambleSwap;
            default:
                return Memeplex.Mutation.SwapRandom;
        }
    }

    public LocalSearch getRandomLocalSearch(){
        int random = (int) (Math.random() * 2);
        switch(random) {
            case 0:
                return Memeplex.LocalSearch.SwapFirstII;
            case 1:
                return Memeplex.LocalSearch.SwapBestII;
            default:
                return Memeplex.LocalSearch.SwapFirstII;
        }
    }

    public int getRandomDepthOfLocalSearch() {
        int[] values = {1, 2, 3, 4, 5};
        int randomIndex = new Random().nextInt(values.length);
        return values[randomIndex];
    }

    public Crossover getCrossover() {
        return crossover;
    }

    public Mutation getMutation() {
        return mutation;
    }

    public double getMutationIntensity() {
        return mutationIntensity;
    }

    public LocalSearch getLocalSearch() {
        return localSearch;
    }

    public int getDepthOfLocalSearch() {
        return depthOfLocalSearch;
    }
    

}
