package com.mmbo;


public class SuccessRate {
    private double PMX_Ratio = 0;
    private double OX_Ratio = 0;
    private double CX_Ratio = 0;
    private double NoneCrossover_Ratio = 0;
    private double SwapRandom_Ratio = 0;
    private double SwapBest_Ratio = 0;
    private double ScrambleSwap_Ratio = 0;
    private double SwapFirstII_Ratio = 0;
    private double SwapBestII_Ratio = 0;
    private double DepthOfLocalSearch_1_Ratio = 0;
    private double DepthOfLocalSearch_2_Ratio = 0;
    private double DepthOfLocalSearch_3_Ratio = 0;
    private double DepthOfLocalSearch_4_Ratio = 0;
    private double DepthOfLocalSearch_5_Ratio = 0;
    private double MutationIntensity_0_2_Ratio = 0;
    private double MutationIntensity_0_4_Ratio = 0;
    private double MutationIntensity_0_6_Ratio = 0;
    private double MutationIntensity_0_8_Ratio = 0;
    private double MutationIntensity_1_Ratio = 0;
    private int scoreCounter = 0; // How many times were birds algorithm performed

    public void addSuccessRate(UtilityScore numerator, UtilityScore denominator) {
        PMX_Ratio += (double)numerator.getPMX_Counter() / (double)denominator.getPMX_Counter();
        OX_Ratio += (double)numerator.getOX_Counter() / (double)denominator.getOX_Counter();
        CX_Ratio += (double)numerator.getCX_Counter() / (double)denominator.getCX_Counter();
        NoneCrossover_Ratio += (double)numerator.getNoneCrossover_Counter() / (double)denominator.getNoneCrossover_Counter();
        SwapRandom_Ratio += (double)numerator.getSwapRandom_Counter() / (double)denominator.getSwapRandom_Counter();
        SwapBest_Ratio += (double)numerator.getSwapBest_Counter() / (double)denominator.getSwapBest_Counter();
        ScrambleSwap_Ratio += (double)numerator.getScrambleSwap_Counter() / (double)denominator.getScrambleSwap_Counter();
        SwapFirstII_Ratio += (double)numerator.getSwapFirstII_Counter() / (double)denominator.getSwapFirstII_Counter();
        SwapBestII_Ratio += (double)numerator.getSwapBestII_Counter() / (double)denominator.getSwapBestII_Counter();
        DepthOfLocalSearch_1_Ratio += (double)numerator.getDepthOfLocalSearch_1_Counter() / (double)denominator.getDepthOfLocalSearch_1_Counter();
        DepthOfLocalSearch_2_Ratio += (double)numerator.getDepthOfLocalSearch_2_Counter() / (double)denominator.getDepthOfLocalSearch_2_Counter();
        DepthOfLocalSearch_3_Ratio += (double)numerator.getDepthOfLocalSearch_3_Counter() / (double)denominator.getDepthOfLocalSearch_3_Counter();
        DepthOfLocalSearch_4_Ratio += (double)numerator.getDepthOfLocalSearch_4_Counter() / (double)denominator.getDepthOfLocalSearch_4_Counter();
        DepthOfLocalSearch_5_Ratio += (double)numerator.getDepthOfLocalSearch_5_Counter() / (double)denominator.getDepthOfLocalSearch_5_Counter();
        MutationIntensity_0_2_Ratio += (double)numerator.getMutationIntensity_0_2_Counter() / (double)denominator.getMutationIntensity_0_2_Counter();
        MutationIntensity_0_4_Ratio += (double)numerator.getMutationIntensity_0_4_Counter() / (double)denominator.getMutationIntensity_0_4_Counter();
        MutationIntensity_0_6_Ratio += (double)numerator.getMutationIntensity_0_6_Counter() / (double)denominator.getMutationIntensity_0_6_Counter();
        MutationIntensity_0_8_Ratio += (double)numerator.getMutationIntensity_0_8_Counter() / (double)denominator.getMutationIntensity_0_8_Counter();
        MutationIntensity_1_Ratio += (double)numerator.getMutationIntensity_1_Counter() / (double)denominator.getMutationIntensity_1_Counter();
        scoreCounter++;
    }

    public double getAveragePMXRatio(){
        return PMX_Ratio / (double)scoreCounter;
    }

    public double getAverageOXRatio(){
        return OX_Ratio / (double)scoreCounter;
    }

    public double getAverageCXRatio(){
        return CX_Ratio / (double)scoreCounter;
    }

    public double getAverageNoneCrossoverRatio(){
        return NoneCrossover_Ratio / (double)scoreCounter;
    }

    public double getAverageSwapRandomRatio(){
        return SwapRandom_Ratio / (double)scoreCounter;
    }

    public double getAverageSwapBestRatio(){
        return SwapBest_Ratio / (double)scoreCounter;
    }

    public double getAverageScrambleSwapRatio(){
        return ScrambleSwap_Ratio / (double)scoreCounter;
    }

    public double getAverageSwapFirstIIRatio(){
        return SwapFirstII_Ratio / (double)scoreCounter;
    }

    public double getAverageSwapBestIIRatio(){
        return SwapBestII_Ratio / (double)scoreCounter;
    }

    public double getAverageDepthOfLocalSearch_1_Ratio(){
        return DepthOfLocalSearch_1_Ratio / (double)scoreCounter;
    }

    public double getAverageDepthOfLocalSearch_2_Ratio(){
        return DepthOfLocalSearch_2_Ratio / (double)scoreCounter;
    }

    public double getAverageDepthOfLocalSearch_3_Ratio(){
        return DepthOfLocalSearch_3_Ratio / (double)scoreCounter;
    }

    public double getAverageDepthOfLocalSearch_4_Ratio(){
        return DepthOfLocalSearch_4_Ratio / (double)scoreCounter;
    }

    public double getAverageDepthOfLocalSearch_5_Ratio(){
        return DepthOfLocalSearch_5_Ratio / (double)scoreCounter;
    }

    public double getAverageMutationIntensity_0_2_Ratio(){
        return MutationIntensity_0_2_Ratio / (double)scoreCounter;
    }

    public double getAverageMutationIntensity_0_4_Ratio(){
        return MutationIntensity_0_4_Ratio / (double)scoreCounter;
    }

    public double getAverageMutationIntensity_0_6_Ratio(){
        return MutationIntensity_0_6_Ratio / (double)scoreCounter;
    }

    public double getAverageMutationIntensity_0_8_Ratio(){
        return MutationIntensity_0_8_Ratio / (double)scoreCounter;
    }

    public double getAverageMutationIntensity_1_Ratio(){
        return MutationIntensity_1_Ratio / (double)scoreCounter;
    }
    

    
}
