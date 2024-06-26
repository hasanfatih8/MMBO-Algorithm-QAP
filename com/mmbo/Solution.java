package com.mmbo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import com.mmbo.operators.Crossover;
import com.mmbo.operators.LocalSearch;
import com.mmbo.operators.Memeplex;
import com.mmbo.operators.Mutation;

public class Solution implements Comparable, Cloneable {
    /**
     * n denotes number of people and offices.
     */
    private int permutation[]; // permutation has length n+1, index 0 is not used because component types in
                               // feeder will be assigned to slots 1 to n
    private int[][] affinity, distance; // affinity and distance has length n+1 x n+1, index 0 is used for numberOfTypes
    private double cost;
    private static int numberOfTypes;
    private ArrayList<Solution> neighbourSet;
    private static int numberOfNeighborsCreated = 0; // counter designed for counting # of neighbors created. Criterion
                                                     // used for stopping BirdsAlgorithm.
    static UtilityScore utilityScore; // utility score for memeplexes which creates better solution
    static UtilityScore achievementScore; // utility score for memeplexes which occured in the past
    static Memeplex bestMemeplex; // best memeplex for the second part
    public Memeplex memeplex;

    /**
     * creates a solution with the given affinity and distance matrices
     */
    public Solution(int[][] affinity, int[][] distance) {
        this.affinity = affinity;
        this.distance = distance;
        this.memeplex = new Memeplex();
        createRandomConf();
        calculateCost();
    }

    /**
     * creates a solution with the given permutation array will be used for cloning
     * and changing permutation
     */
    public Solution(Solution otherSolution, int permutation[]) {
        this.affinity = otherSolution.affinity;
        this.distance = otherSolution.distance;
        this.permutation = permutation;
        this.memeplex = otherSolution.memeplex;
        calculateCost();
    }

    /**
     * returns the cost, fitness value of this solution
     */
    public double getCost() {
        return cost;
    }

    /**
     * written for genetic algorithm and returns cost of this solution object
     */
    public double getFitness() {
        return getCost();
    }

    /**
     * creates a random configuration
     * called only when a Solution object with no configuration is created.
     */
    public void createRandomConf() {
        HashSet<Integer> set = new HashSet<Integer>();
        int k = 0, j = 0;
        for (int i = 1; i < affinity.length; i++) {
            set.add(i);
        }
        numberOfTypes = set.size();
        permutation = new int[numberOfTypes + 1];
        while (!set.isEmpty()) {
            k = 1 + (int) (Math.random() * (numberOfTypes));
            if (set.contains(k)) {
                permutation[++j] = k;
                set.remove(k);
            }
        }
    }

    /**
     * calculates cost of this Solution object
     * It is called once, when this Solution object is created,
     * and stores the cost value in global variable cost.
     * Whenever cost is inquired, it should be acquired via getCost() method
     */
    public void calculateCost() {
        cost = 0;
        int office1, office2;
        for (int i = 1; i < affinity.length; i++) {
            for (int j = 1; j < affinity[i].length; j++) {
                office1 = permutation[i];
                office2 = permutation[j];
                cost += affinity[i][j] * distance[office1][office2];
            }
        }
    }

    public void createNeighborSet(int nongbr, Solution mate, AlgorithmMode mode) {
        switch (mode) {
            case MBO:
                createNeighborSetMBO(nongbr);
                break;
            case MMMBOv1:
            case MMMBOv2:
                createNeighborSetMMMBO(nongbr, mate);
                break;
            default:
                break;
        }
    }

    /**
     * cretaes a neighbor Set of this solution which includes non elements
     */
    public void createNeighborSetMMMBO(int nongbr, Solution mate) {
        neighbourSet = new ArrayList<Solution>();
        Solution parameter1Solution, parameter2Solution;

        if (this.getCost() < mate.getCost()) {
            parameter1Solution = this;
            parameter2Solution = mate;
        } else {
            parameter1Solution = mate;
            parameter2Solution = this;
        }

        Memeplex applyingMemeplex = bestMemeplex != null ? bestMemeplex : parameter1Solution.memeplex;

        for (int i = 0; i < nongbr; i++) {
            // neighbourSet.add(randomSwapMutation());
            Solution child = Crossover.applyCrossover(applyingMemeplex.getCrossover(), parameter1Solution, parameter2Solution);
            if(child.getCost() >= parameter1Solution.getCost()) {
                child = parameter1Solution;
            }else {
                achievementScore.addCrossover(applyingMemeplex.getCrossover());
            }
            utilityScore.addCrossover(applyingMemeplex.getCrossover());
            
            child = Mutation.applyMutation(applyingMemeplex.getMutation(), applyingMemeplex.getMutationIntensity(), child);
            if(child.getCost() < parameter1Solution.getCost()) {
                achievementScore.addMutation(applyingMemeplex.getMutation());
                achievementScore.addMutationIntensity(applyingMemeplex.getMutationIntensity());
            }
            utilityScore.addMutation(applyingMemeplex.getMutation());
            utilityScore.addMutationIntensity(applyingMemeplex.getMutationIntensity());
            
            child = LocalSearch.applyLocalSearch(applyingMemeplex.getLocalSearch(), applyingMemeplex.getDepthOfLocalSearch(), child);
            if(child.getCost() < parameter1Solution.getCost()) {
                if(applyingMemeplex.getCrossover() == Memeplex.Crossover.None)
                    achievementScore.addCrossover(applyingMemeplex.getCrossover()); // if none crossover is applied, then increase the achievement score of none crossover 
                achievementScore.addLocalSearch(applyingMemeplex.getLocalSearch());
                achievementScore.addDepthOfLocalSearch(applyingMemeplex.getDepthOfLocalSearch());
            }
            utilityScore.addLocalSearch(applyingMemeplex.getLocalSearch());
            utilityScore.addDepthOfLocalSearch(applyingMemeplex.getDepthOfLocalSearch());
            
            if (bestMemeplex == null && Math.random() < 0.20)
                child.memeplex = new Memeplex();
                
            neighbourSet.add(child);
            numberOfNeighborsCreated++;
        }
        sortNeighbours();
    }

    public void createNeighborSetMBO(int nongbr) {
        neighbourSet = new ArrayList<Solution>();
        for (int i = 0; i < nongbr; i++) {
            neighbourSet.add(Mutation.applyMutation(Memeplex.Mutation.SwapRandom, 0.2, this));
            numberOfNeighborsCreated++;
        }
        sortNeighbours();
    }

    /**
     * returns best neighbour of this solution and removes it from the neighbor set
     */
    public Solution getBestNeighbour() {
        Solution bestNeighbour = neighbourSet.get(0);
        neighbourSet.remove(0);
        return bestNeighbour;
    }

    /**
     * returns best neighbor of this solution and DOES NOT remove it from the
     * neighbor set
     */
    public Solution checkBestNeighbour() {
        Solution bestNeighbour = neighbourSet.get(0);
        return bestNeighbour;
    }

    /**
     * adds the given solution (n) to the neighbor set of this solution
     * adding a new solution to the neighbor list requires resorting that list
     */
    public void addNeighbour(Solution n) {
        neighbourSet.add(n);
        sortNeighbours();
    }

    private void sortNeighbours() {
        Object list[] = neighbourSet.toArray();
        Arrays.sort(list);
        neighbourSet.clear();
        for (int i = 0; i < list.length; i++) {
            neighbourSet.add((Solution) list[i]);
        }
    }

    /**
     * returns the neighbor set of this solution
     */
    public ArrayList<Solution> getNeighbourSet() {
        return neighbourSet;
    }

    /**
     * overidden for cloning objects when the same copy is created while crossover
     * is applied
     */
    public Object clone() {
        int conf[] = new int[permutation.length];
        for (int i = 0; i < conf.length; i++) {
            conf[i] = permutation[i];
        }
        return new Solution(this, conf);
    }

    /**
     * a solution with a smaller fitness value is considered as a small solution.
     */
    public int compareTo(Object o) {
        if (o instanceof Solution) {
            return (int) (this.getFitness() - ((Solution) o).getFitness());
        } else
            return 0;
    }

    /**
     * returns the permutation (feeder configuration) of a problem
     */
    public int[] getFeederConfiguration() {
        return permutation;
    }

    /**
     * returns the string rep. of the solution in terms of permutation matrix
     */
    public String toString() {
        String conf = "";
        for (int i = 1; i < permutation.length; i++) {
            conf += permutation[i] + ", ";
        }
        conf += "\nMemeplex of the solution: " + memeplex.getCrossover() + " " + memeplex.getMutation() + " "
                + memeplex.getMutationIntensity() + " " + memeplex.getLocalSearch() + " "
                + memeplex.getDepthOfLocalSearch() + " ";
        return conf;
    }

    public boolean equals(Object obj) {
        return equals((Solution) obj);
    }

    public boolean equals(Solution s) {
        int[] sfc = s.getFeederConfiguration();
        boolean same = true;
        for (int i = 1; i < permutation.length; i++) {
            same = permutation[i] == sfc[i];
            if (!same)
                break;
        }
        return same;
    }

    /**
     * returns number of types (people, offices) for this solution
     */
    public static int getNumberOfTypes() {
        return numberOfTypes;
    }

    /**
     * returns number of neighbors created so far from the last call of
     * resetNumberOfNeighborsCreated()
     *
     * @return
     */
    public static int getNumberOfNeighborsCreated() {
        return numberOfNeighborsCreated;
    }

    /**
     * resets the number of neighbors to zero
     * should be called when an algorithm starts
     */
    public static void resetNumberOfNeighborsCreated() {
        numberOfNeighborsCreated = 0;
    }
}
