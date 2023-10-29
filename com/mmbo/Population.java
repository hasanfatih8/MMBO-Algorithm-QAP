package com.mmbo;

import java.util.ArrayList;
import java.util.Iterator;


public class Population extends ArrayList<Solution> {
    /**
     * represents a group of solutions. We simply called Population inspired from GA
     */
    private double totalFitness;

    public Population() {
    }

    //add a bird(solution) to flock(population)
    public boolean add(Solution ind) {
        totalFitness += ind.getFitness();
        return super.add(ind);
    }
    //remove a bird(solution) from flock(population)
    public boolean remove(Solution ind) {
        totalFitness -= ind.getFitness();
        return super.remove(ind);
    }
    //returns total fitness of all solutions in this population
    public double getTotalFitness() {
        return totalFitness;
    }
    //clears the population
    public void clear() {
        totalFitness = 0;
        super.clear();
    }

    //returns the solution with greatest fitness value
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

    //returns the solution with smallest fitness value
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
