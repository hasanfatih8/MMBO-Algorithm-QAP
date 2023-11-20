package com.mmbo;

import java.util.ArrayList;
import java.util.Iterator;


/**
 * Represents a group of solutions, often referred to as a population, inspired by Genetic Algorithms (GA).
 */
public class Population extends ArrayList<Solution> {

    private double totalFitness;

    /**
     * Constructs an empty population.
     */
    public Population() {
    }

    /**
     * Add a solution (bird) to the population (flock) and update the total fitness.
     *
     * @param ind The solution to add to the population.
     * @return true if the solution is added successfully.
     */
    public boolean add(Solution ind) {
        totalFitness += ind.getFitness();
        return super.add(ind);
    }

    /**
     * Remove a solution (bird) from the population and update the total fitness.
     *
     * @param ind The solution to remove from the population.
     * @return true if the solution is removed successfully.
     */
    public boolean remove(Solution ind) {
        totalFitness -= ind.getFitness();
        return super.remove(ind);
    }

    /**
     * Get the total fitness of all solutions in the population.
     *
     * @return The total fitness of the population.
     */
    public double getTotalFitness() {
        return totalFitness;
    }
    
    /**
     * Clears the population, resetting the total fitness to zero.
     */
    public void clear() {
        totalFitness = 0;
        super.clear();
    }

    /**
     * Choses a random solution from the population as mate.
     */
    public Solution chooseRandomMate(int currentIndex) {
        int mateIndex;
        do {
            mateIndex = (int)(Math.random()*size());
        } while(mateIndex == currentIndex);
        return get(mateIndex);
    }

    /**
     * Get the solution with the highest fitness value in the population.
     *
     * @return The solution with the highest fitness.
     */
    public Solution getMax() {
        Solution maxS = null, currentSolution;
        double max = 0;
        Iterator it = this.iterator();
        while (it.hasNext()) {
            currentSolution = (Solution) it.next();
            if (currentSolution.getFitness() > max) {
                max = currentSolution.getFitness();
                maxS = currentSolution;
            }
        }
        return maxS;
    }

    /**
     * Get the solution with the lowest fitness value in the population.
     *
     * @return The solution with the lowest fitness.
     */
    public Solution getMin() {
        Solution minS = null, currentSolution;
        double min = Double.MAX_VALUE;
        Iterator it = this.iterator();
        while (it.hasNext()) {
            currentSolution = (Solution) it.next();
            if (currentSolution.getFitness() < min) {
                min = currentSolution.getFitness();
                minS = currentSolution;
            }
        }
        return minS;
    }

}
