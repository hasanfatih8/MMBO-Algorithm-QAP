import java.util.ArrayList;
import java.util.Iterator;


public class Population extends ArrayList<Solution> {
    /**
     * represents a group of solutions. We simply called Population inspired from GA
     */
    private static final long serialVersionUID = -7107869948639093967L;
    private double totalFitness;

    public Population() {
    }

    public boolean add(Solution ind) {
        totalFitness += ind.getFitness();
        return super.add(ind);
    }

    /**
     * returns total fitness of all solutions in this population
     *
     * @return
     */
    public double getTotalFitness() {
        return totalFitness;
    }

    public boolean remove(Solution ind) {
        totalFitness -= ind.getFitness();
        return super.remove(ind);
    }

    public void clear() {
        totalFitness = 0;
        super.clear();
    }

    /**
     * returns the solution with greatest fitness value
     *
     * @return
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
     * returns the solution with smallest fitness value
     *
     * @return
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
