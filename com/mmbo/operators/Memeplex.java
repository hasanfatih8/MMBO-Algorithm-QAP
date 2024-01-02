package com.mmbo.operators;
import java.util.Random;

import com.mmbo.SuccessRate;
import com.mmbo.UtilityScore;
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
    
    public static Crossover getBestCrossover(SuccessRate successRate) {
        double max = successRate.getAverageCXRatio();
        Crossover bestCrossover = Crossover.CX;
        
        if (max < successRate.getAverageOXRatio()) {
            max = successRate.getAverageOXRatio();
            bestCrossover = Crossover.OX;
        }

        if (max < successRate.getAveragePMXRatio()) {
            max = successRate.getAveragePMXRatio();
            bestCrossover = Crossover.PMX;
        }

        if(max < successRate.getAverageNoneCrossoverRatio()) {
            max = successRate.getAverageNoneCrossoverRatio();
            bestCrossover = Crossover.None;
        }

        return bestCrossover;
    }

    public static Mutation getBestMutation(SuccessRate successRate) {
        double max = successRate.getAverageSwapRandomRatio();
        Mutation bestMutation = Mutation.SwapRandom;
        
        if (max < successRate.getAverageSwapBestRatio()) {
            max = successRate.getAverageSwapBestRatio();
            bestMutation = Mutation.SwapBest;
        }
        
        if (max < successRate.getAverageScrambleSwapRatio()) {
            max = successRate.getAverageScrambleSwapRatio();
            bestMutation = Mutation.ScrambleSwap;
        }

        return bestMutation;
    }

    public static double getBestMutationIntensity(SuccessRate successRate) {
        double max = successRate.getAverageMutationIntensity_0_2_Ratio();
        double bestMutationIntensity = 0.2;

        if (max < successRate.getAverageMutationIntensity_0_4_Ratio()) {
            max = successRate.getAverageMutationIntensity_0_4_Ratio();
            bestMutationIntensity = 0.4;
        }

        if (max < successRate.getAverageMutationIntensity_0_6_Ratio()) {
            max = successRate.getAverageMutationIntensity_0_6_Ratio();
            bestMutationIntensity = 0.6;
        }
        
        if (max < successRate.getAverageMutationIntensity_0_8_Ratio()) {
            max = successRate.getAverageMutationIntensity_0_8_Ratio();
            bestMutationIntensity = 0.8;
        }

        if (max < successRate.getAverageMutationIntensity_1_Ratio()) {
            max = successRate.getAverageMutationIntensity_1_Ratio();
            bestMutationIntensity = 1;
        }

        return bestMutationIntensity;
    }

    public static LocalSearch getBestLocalSearch(SuccessRate successRate) {
        double max = successRate.getAverageSwapFirstIIRatio();
        LocalSearch bestLocalSearch = LocalSearch.SwapFirstII;
        
        if (max < successRate.getAverageSwapBestIIRatio()) {
            max = successRate.getAverageSwapBestIIRatio();
            bestLocalSearch = LocalSearch.SwapBestII;
        }

        return bestLocalSearch;
    }

    public static int getBestDepthOfLocalSearch(SuccessRate successRate) {
        double max = successRate.getAverageDepthOfLocalSearch_1_Ratio();
        int bestDepthOfLocalSearch = 1;
        
        if (max < successRate.getAverageDepthOfLocalSearch_2_Ratio()) {
            max = successRate.getAverageDepthOfLocalSearch_2_Ratio();
            bestDepthOfLocalSearch = 2;
        }

        if (max < successRate.getAverageDepthOfLocalSearch_3_Ratio()) {
            max = successRate.getAverageDepthOfLocalSearch_3_Ratio();
            bestDepthOfLocalSearch = 3;
        }

        if (max < successRate.getAverageDepthOfLocalSearch_4_Ratio()) {
            max = successRate.getAverageDepthOfLocalSearch_4_Ratio();
            bestDepthOfLocalSearch = 4;
        }

        if (max < successRate.getAverageDepthOfLocalSearch_5_Ratio()) {
            max = successRate.getAverageDepthOfLocalSearch_5_Ratio();
            bestDepthOfLocalSearch = 5;
        }

        return bestDepthOfLocalSearch;
    }
}
