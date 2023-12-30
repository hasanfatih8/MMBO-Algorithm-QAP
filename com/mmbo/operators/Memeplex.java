package com.mmbo.operators;
import java.util.Random;

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
    
    public static Crossover getBestCrossover(UtilityScore numeratorScore, UtilityScore denominator) {
        double max = (double)numeratorScore.getCX_Counter() / (double)denominator.getCX_Counter();
        Crossover bestCrossover = Crossover.CX;
        
        if (max < (double)numeratorScore.getOX_Counter() / (double)denominator.getOX_Counter()) {
            max = (double)numeratorScore.getOX_Counter() / (double)denominator.getOX_Counter();
            bestCrossover = Crossover.OX;
        }

        if (max < (double)numeratorScore.getPMX_Counter() / (double)denominator.getPMX_Counter()) {
            max = (double)numeratorScore.getPMX_Counter() / (double)denominator.getPMX_Counter();
            bestCrossover = Crossover.PMX;
        }

        if(max < (double)numeratorScore.getNoneCrossover_Counter() / (double)denominator.getNoneCrossover_Counter()) {
            max = (double)numeratorScore.getNoneCrossover_Counter() / (double)denominator.getNoneCrossover_Counter();
            bestCrossover = Crossover.None;
        }

        return bestCrossover;
    }

    public static Mutation getBestMutation(UtilityScore numeratorScore, UtilityScore denominator) {
        double max = (double)numeratorScore.getSwapRandom_Counter() / (double)denominator.getSwapRandom_Counter();
        Mutation bestMutation = Mutation.SwapRandom;
        
        if (max < (double)numeratorScore.getSwapBest_Counter() / (double)denominator.getSwapBest_Counter()) {
            max = (double)numeratorScore.getSwapBest_Counter() / (double)denominator.getSwapBest_Counter();
            bestMutation = Mutation.SwapBest;
        }

        if (max < (double)numeratorScore.getScrambleSwap_Counter() / (double)denominator.getScrambleSwap_Counter()) {
            max = (double)numeratorScore.getScrambleSwap_Counter() / (double)denominator.getScrambleSwap_Counter();
            bestMutation = Mutation.ScrambleSwap;
        }

        return bestMutation;
    }

    public static double getBestMutationIntensity(UtilityScore numeratorScore, UtilityScore denominator) {
        double max = (double)numeratorScore.getMutationIntensity_0_2_Counter() / (double)denominator.getMutationIntensity_0_2_Counter();
        double bestMutationIntensity = 0.2;
        
        if (max < (double)numeratorScore.getMutationIntensity_0_4_Counter() / (double)denominator.getMutationIntensity_0_4_Counter()) {
            max = (double)numeratorScore.getMutationIntensity_0_4_Counter() / (double)denominator.getMutationIntensity_0_4_Counter();
            bestMutationIntensity = 0.4;
        }

        if (max < (double)numeratorScore.getMutationIntensity_0_6_Counter() / (double)denominator.getMutationIntensity_0_6_Counter()) {
            max = (double)numeratorScore.getMutationIntensity_0_6_Counter() / (double)denominator.getMutationIntensity_0_6_Counter();
            bestMutationIntensity = 0.6;
        }

        if (max < (double)numeratorScore.getMutationIntensity_0_8_Counter() / (double)denominator.getMutationIntensity_0_8_Counter()) {
            max = (double)numeratorScore.getMutationIntensity_0_8_Counter() / (double)denominator.getMutationIntensity_0_8_Counter();
            bestMutationIntensity = 0.8;
        }

        if (max < (double)numeratorScore.getMutationIntensity_1_Counter() / (double)denominator.getMutationIntensity_1_Counter()) {
            max = (double)numeratorScore.getMutationIntensity_1_Counter() / (double)denominator.getMutationIntensity_1_Counter();
            bestMutationIntensity = 1.0;
        }

        return bestMutationIntensity;
    }

    public static LocalSearch getBestLocalSearch(UtilityScore numeratorScore, UtilityScore denominator) {
        double max = (double)numeratorScore.getSwapFirstII_Counter() / (double)denominator.getSwapFirstII_Counter();
        LocalSearch bestLocalSearch = LocalSearch.SwapFirstII;
        
        if (max < (double)numeratorScore.getSwapBestII_Counter() / (double)denominator.getSwapBestII_Counter()) {
            max = (double)numeratorScore.getSwapBestII_Counter() / (double)denominator.getSwapBestII_Counter();
            bestLocalSearch = LocalSearch.SwapBestII;
        }

        return bestLocalSearch;
    }

    public static int getBestDepthOfLocalSearch(UtilityScore numeratorScore, UtilityScore denominator) {
        double max = (double)numeratorScore.getDepthOfLocalSearch_1_Counter() / (double)denominator.getDepthOfLocalSearch_1_Counter();
        int bestDepthOfLocalSearch = 1;
        
        if (max < (double)numeratorScore.getDepthOfLocalSearch_2_Counter() / (double)denominator.getDepthOfLocalSearch_2_Counter()) {
            max = (double)numeratorScore.getDepthOfLocalSearch_2_Counter() / (double)denominator.getDepthOfLocalSearch_2_Counter();
            bestDepthOfLocalSearch = 2;
        }

        if (max < (double)numeratorScore.getDepthOfLocalSearch_3_Counter() / (double)denominator.getDepthOfLocalSearch_3_Counter()) {
            max = (double)numeratorScore.getDepthOfLocalSearch_3_Counter() / (double)denominator.getDepthOfLocalSearch_3_Counter();
            bestDepthOfLocalSearch = 3;
        }

        if (max < (double)numeratorScore.getDepthOfLocalSearch_4_Counter() / (double)denominator.getDepthOfLocalSearch_4_Counter()) {
            max = (double)numeratorScore.getDepthOfLocalSearch_4_Counter() / (double)denominator.getDepthOfLocalSearch_4_Counter();
            bestDepthOfLocalSearch = 4;
        }

        if (max < (double)numeratorScore.getDepthOfLocalSearch_5_Counter() / (double)denominator.getDepthOfLocalSearch_5_Counter()) {
            max = (double)numeratorScore.getDepthOfLocalSearch_5_Counter() / (double)denominator.getDepthOfLocalSearch_5_Counter();
            bestDepthOfLocalSearch = 5;
        }

        return bestDepthOfLocalSearch;
    }
}
