package com.mmbo;

import com.mmbo.operators.Memeplex;

public class UtilityScore {

    private int PMX_Counter = 0;
    private int OX_Counter = 0;
    private int CX_Counter = 0;
    private int NoneCrossover_Counter = 0;
    private int SwapRandom_Counter = 0;
    private int SwapBest_Counter = 0;
    private int ScrambleSwap_Counter = 0;
    private int SwapFirstII_Counter = 0;
    private int SwapBestII_Counter = 0;
    private int DepthOfLocalSearch_1_Counter = 0;
    private int DepthOfLocalSearch_2_Counter = 0;
    private int DepthOfLocalSearch_3_Counter = 0;
    private int DepthOfLocalSearch_4_Counter = 0;
    private int DepthOfLocalSearch_5_Counter = 0;
    private int MutationIntensity_0_2_Counter = 0;
    private int MutationIntensity_0_4_Counter = 0;
    private int MutationIntensity_0_6_Counter = 0;
    private int MutationIntensity_0_8_Counter = 0;
    private int MutationIntensity_1_Counter = 0;

    public void addCrossover(Memeplex.Crossover crossover) {
        switch (crossover) {
            case PMX:
                PMX_Counter++;
                break;
            case OX:
                OX_Counter++;
                break;
            case CX:
                CX_Counter++;
                break;
            default:
                NoneCrossover_Counter++;
                break;
        }
    }

    public void addMutation(Memeplex.Mutation mutation) {
        switch (mutation) {
            case SwapRandom:
                SwapRandom_Counter++;
                break;
            case SwapBest:
                SwapBest_Counter++;
                break;
            case ScrambleSwap:
                ScrambleSwap_Counter++;
                break;
            default:
                break;
        }
    }

    public void addLocalSearch(Memeplex.LocalSearch localSearch) {
        switch (localSearch) {
            case SwapFirstII:
                SwapFirstII_Counter++;
                break;
            case SwapBestII:
                SwapBestII_Counter++;
                break;
            default:
                break;
        }
    }

    public void addDepthOfLocalSearch(int depthOfLocalSearch) {
        switch (depthOfLocalSearch) {
            case 1:
                DepthOfLocalSearch_1_Counter++;
                break;
            case 2:
                DepthOfLocalSearch_2_Counter++;
                break;
            case 3:
                DepthOfLocalSearch_3_Counter++;
                break;
            case 4:
                DepthOfLocalSearch_4_Counter++;
                break;
            case 5:
                DepthOfLocalSearch_5_Counter++;
                break;
            default:
                break;
        }
    }

    public void addMutationIntensity(double mutationIntensity) {
        switch ((int)(mutationIntensity*10)) { // Switch cannot get double values as input so we multiply by 10 and cast to int
            case 2:
                MutationIntensity_0_2_Counter++;
                break;
            case 4:
                MutationIntensity_0_4_Counter++;
                break;
            case 6:
                MutationIntensity_0_6_Counter++;
                break;
            case 8:
                MutationIntensity_0_8_Counter++;
                break;
            case 10:
                MutationIntensity_1_Counter++;
                break;
            default:
                break;
        }
    }

    public int getPMX_Counter() {
        return PMX_Counter;
    }

    public int getOX_Counter() {
        return OX_Counter;
    }

    public int getCX_Counter() {
        return CX_Counter;
    }

    public int getNoneCrossover_Counter() {
        return NoneCrossover_Counter;
    }

    public int getSwapRandom_Counter() {
        return SwapRandom_Counter;
    }

    public int getSwapBest_Counter() {
        return SwapBest_Counter;
    }

    public int getScrambleSwap_Counter() {
        return ScrambleSwap_Counter;
    }

    public int getSwapFirstII_Counter() {
        return SwapFirstII_Counter;
    }

    public int getSwapBestII_Counter() {
        return SwapBestII_Counter;
    }

    public int getDepthOfLocalSearch_1_Counter() {
        return DepthOfLocalSearch_1_Counter;
    }

    public int getDepthOfLocalSearch_2_Counter() {
        return DepthOfLocalSearch_2_Counter;
    }

    public int getDepthOfLocalSearch_3_Counter() {
        return DepthOfLocalSearch_3_Counter;
    }

    public int getDepthOfLocalSearch_4_Counter() {
        return DepthOfLocalSearch_4_Counter;
    }

    public int getDepthOfLocalSearch_5_Counter() {
        return DepthOfLocalSearch_5_Counter;
    }

    public int getMutationIntensity_0_2_Counter() {
        return MutationIntensity_0_2_Counter;
    }

    public int getMutationIntensity_0_4_Counter() {
        return MutationIntensity_0_4_Counter;
    }

    public int getMutationIntensity_0_6_Counter() {
        return MutationIntensity_0_6_Counter;
    }

    public int getMutationIntensity_0_8_Counter() {
        return MutationIntensity_0_8_Counter;
    }

    public int getMutationIntensity_1_Counter() {
        return MutationIntensity_1_Counter;
    }

    public void addUtilityScore(UtilityScore utilityScore) {
        this.PMX_Counter += utilityScore.getPMX_Counter();
        this.OX_Counter += utilityScore.getOX_Counter();
        this.CX_Counter += utilityScore.getCX_Counter();
        this.NoneCrossover_Counter += utilityScore.getNoneCrossover_Counter();
        this.SwapRandom_Counter += utilityScore.getSwapRandom_Counter();
        this.SwapBest_Counter += utilityScore.getSwapBest_Counter();
        this.ScrambleSwap_Counter += utilityScore.getScrambleSwap_Counter();
        this.SwapFirstII_Counter += utilityScore.getSwapFirstII_Counter();
        this.SwapBestII_Counter += utilityScore.getSwapBestII_Counter();
        this.DepthOfLocalSearch_1_Counter += utilityScore.getDepthOfLocalSearch_1_Counter();
        this.DepthOfLocalSearch_2_Counter += utilityScore.getDepthOfLocalSearch_2_Counter();
        this.DepthOfLocalSearch_3_Counter += utilityScore.getDepthOfLocalSearch_3_Counter();
        this.DepthOfLocalSearch_4_Counter += utilityScore.getDepthOfLocalSearch_4_Counter();
        this.DepthOfLocalSearch_5_Counter += utilityScore.getDepthOfLocalSearch_5_Counter();
        this.MutationIntensity_0_2_Counter += utilityScore.getMutationIntensity_0_2_Counter();
        this.MutationIntensity_0_4_Counter += utilityScore.getMutationIntensity_0_4_Counter();
        this.MutationIntensity_0_6_Counter += utilityScore.getMutationIntensity_0_6_Counter();
        this.MutationIntensity_0_8_Counter += utilityScore.getMutationIntensity_0_8_Counter();
        this.MutationIntensity_1_Counter += utilityScore.getMutationIntensity_1_Counter();
    }
}
