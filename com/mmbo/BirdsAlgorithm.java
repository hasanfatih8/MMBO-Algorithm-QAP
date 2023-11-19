package com.mmbo;
import java.util.Arrays;
import javax.swing.JOptionPane;

public class BirdsAlgorithm extends MetaHeuristic{
    private int numberOfBirds; //# of birds, n
    private int numberOfNeighbors; //# of neighbors, k
    private int overLapFactor; //overlap factor, x
    private int numberOfIterations; //# of iterations, K (in this implementation, creating a neighbour counts for an iteration)
    
    private Population flock; 
/*
    flock of birds (solutions) 0 is the leader and the following elements of the ArrayList(population) are listed as follows.  
    See that odd ones are on the left and even ones are on the right
					0
				1		2
			3				4
		5						6
*/        

    //1 means sorting the initial flock randomly, while 2 means sorting them according to their performance
    private int initialFlockSortedAccToPerf = 1; 
    
    /*
    1 means exchange the leader with the successor
    2 means exchanging the leader with the best
    3 means exchanging if its performance is gets worse
    */
    private int leaderExchangeMode = 1; 

    /*
    1 means the flock permutation stays the same
    2 means sorting them according performance
    */
    private int sortAccordingToPerformance = 1;
    
    //leftSide is used for determining the side of the leader
    private boolean leftSide = true;
    //leaderImproves is used for determining whether the leader improves or not
    private boolean leaderImproves = true;

    //Keep start time and end time for calculating the run time
    private long startTime;
    private double endTime;
    
    public BirdsAlgorithm(
            int numberOfBirds,
            int numberOfNeighbors,
            int numberOfFlapping,
            int overLapFactor,
            int initialFlockSortedAccToPerf,
            int leaderExchangeMode,
            int sortAccordingToPerformance,
            String input) 
            {
                this.numberOfBirds = numberOfBirds;
                this.numberOfNeighbors = numberOfNeighbors;
                this.overLapFactor = overLapFactor;
                this.initialFlockSortedAccToPerf = initialFlockSortedAccToPerf;
                this.leaderExchangeMode = leaderExchangeMode;
                this.sortAccordingToPerformance = sortAccordingToPerformance;
                this.input = input;
                checkInputFileFormatAndRead();
                startTime = System.currentTimeMillis();
                flock = new Population();
                createInitialFlock();
                Solution.resetNumberOfNeighborsCreated();
                numberOfIterations = (int) Math.pow(Solution.getNumberOfTypes(), 3);
                while (Solution.getNumberOfNeighborsCreated() < numberOfIterations) {
                    for (int i = 0; i < numberOfFlapping; i++) {
                        flyFlock();
                    }
                    replaceLeader();
                    sortTheSuccessors();
                    leaderImproves = true;
                }
                writeResults();
            }

    /**
    * Initializes the flock of birds with a specified number of solutions (birds).
    * Each bird is created and added to the flock. Optionally, the flock can be sorted
    * based on their performance (fitness) if the 'initialFlockSortedAccToPerf' parameter
    * is set to 2 (otherwise, the flock is sorted randomly).
    */
    public void createInitialFlock() {
        for (int i = 0; i < numberOfBirds; i++) {
            flock.add(new Solution(affinity, distance));
        }
        // If the 'initialFlockSortedAccToPerf' parameter is set to 2, sort the flock
        // based on their performance (fitness) using a sorting algorithm.
        if (initialFlockSortedAccToPerf == 2) {
            Object list[] = flock.toArray();
            flock.clear();
            Arrays.sort(list);
            for (int k = 0; k < list.length; k++)
                flock.add((Solution) list[k]);
        }
    }

    /**
    * Simulates the flight behavior of a flock of birds (solutions).
    * 
    * During each iteration, birds assess their neighbors' performance and consider 
    * making changes based on their neighbors' fitness. The best neighboring solutions 
    * are determined and may be transferred to other birds in the flock.
    */
    public void flyFlock() {
        // Bird-related variables
        Solution leader, currentSolutionTODO, bestOther;
    
        // An array to store the best neighboring solutions for each bird
        Solution bests[] = new Solution[numberOfBirds];

        // Initialization phase
        leader = flock.get(0);
        leader.createNeighborSet(numberOfNeighbors, flock.chooseRandomMate(0));

        for (int i = 1; i < numberOfBirds; i++) {
            flock.get(i).createNeighborSet(numberOfNeighbors - overLapFactor, flock.chooseRandomMate(i));
        }
        
        // Determine the best neighbors for the leader and the second bird
        leader = flock.get(0);
        bests[0] = leader.getBestNeighbour();


        currentSolutionTODO = flock.get(1);
        bestOther = currentSolutionTODO.getBestNeighbour();
        
        if (bestOther.getFitness() <= currentSolutionTODO.getFitness()) {
            bests[1] = bestOther;
        } else if (leader.checkBestNeighbour().getFitness() <= currentSolutionTODO.getFitness()) {
            bests[1] = leader.getBestNeighbour();
        } else {
            bests[1] = flock.get(1);
        }

        //Why there is a spesiific case for the third bird?
        currentSolutionTODO = flock.get(2);
        bestOther = currentSolutionTODO.getBestNeighbour();
        if (bestOther.getFitness() <= currentSolutionTODO.getFitness()) {
            bests[2] = bestOther;
        } else if (leader.checkBestNeighbour().getFitness() <= currentSolutionTODO.getFitness()) {
            bests[2] = leader.getBestNeighbour();
        } else {
            bests[2] = flock.get(2);
        }

        // Determine the best neighbors for other birds
        for (int i = 3; i < numberOfBirds; i++) {
            currentSolutionTODO = flock.get(i);
            bestOther = currentSolutionTODO.getBestNeighbour();
            
            if (bestOther.getFitness() <= currentSolutionTODO.getFitness()) {
                bests[i] = bestOther;
            } else if (flock.get(i - 2).checkBestNeighbour().getFitness() <= currentSolutionTODO.getFitness()) {
                bests[i] = flock.get(i - 2).getBestNeighbour();
            } else {
                bests[i] = flock.get(i);
            }
        }

        // Check if the leader bird's fitness improves
        if (flock.get(0).getFitness() <= bests[0].getFitness()) {
            leaderImproves = false;
        }

        // Replace birds with their best neighbors if they have better fitness
        for (int i = 0; i < numberOfBirds; i++) {
            if (flock.get(i).getFitness() >= bests[i].getFitness()) {
                flock.remove(i);
                flock.add(i, bests[i]);
            }
        }
	

        //Solution leader,cs,besto;//best 1 used for repalcing the leader, best other used for transferring following birds
        leader = flock.get(0);
        leader.createNeighborSet(numberOfNeighbors, flock.chooseRandomMate(0));
        for (int i = 1; i < numberOfBirds; i++) {
            flock.get(i).createNeighborSet(numberOfNeighbors - overLapFactor, flock.chooseRandomMate(i));
        }

        leader = flock.get(0);
        bests[0] = leader.getBestNeighbour();
        for (int i = 0; i < 2 * overLapFactor; i++) {
            bestOther = leader.getBestNeighbour();
            if (i % 2 == 0) {
                flock.get(1).addNeighbour(bestOther);
            } else {
                flock.get(2).addNeighbour(bestOther);
            }
        }

        //the following birds of the leader are determining their best elements and next olf neighbours are 
        //trasferred to their followers.  Finally if a solution's best nghbr is better than it, it is replaced. 
        for (int i = 1; i < numberOfBirds - 2; i++) {
            currentSolutionTODO = flock.get(i);
            bests[i] = currentSolutionTODO.getBestNeighbour();
            for (int j = 0; j < overLapFactor; j++) {
                bestOther = currentSolutionTODO.getBestNeighbour();
                flock.get(i + 2).addNeighbour(bestOther);
            }
        }
        bests[numberOfBirds - 2] = flock.get(numberOfBirds - 2).getBestNeighbour();
        bests[numberOfBirds - 1] = flock.get(numberOfBirds - 1).getBestNeighbour();

        if (flock.get(0).getFitness() <= bests[0].getFitness())
            leaderImproves = false;
        //replace the current birds with their best nghbrs, if they are better
        for (int i = 0; i < numberOfBirds; i++) {
            if (flock.get(i).getFitness() >= bests[i].getFitness()) {
                flock.remove(i);
                flock.add(i, bests[i]);
            }
        } 
    }

    public void replaceLeader() {
        switch (leaderExchangeMode) {
            case 1://change with the immediate successor
                replaceLeaderWithSuccessor();
                break;
            case 2://change with the best
                replaceLeaderWithBest();
                break;
            case 3://change with the best if leader does not improve
                if (!leaderImproves) {
                    replaceLeaderWithBest();
                }
                break;
        }
    }

    public void replaceLeaderWithSuccessor() {
        Population nf = new Population();//new flock
        if (leftSide) {
            nf.add(flock.get(1));
            for (int i = 1; i < numberOfBirds - 2; i++) {
                if (i % 2 == 1)
                    nf.add(flock.get(i + 2));
                else
                    nf.add(flock.get(i));
            }
            nf.add(flock.get(0));
            nf.add(flock.get(numberOfBirds - 1));
        } else {
            nf.add(flock.get(2));
            for (int i = 1; i < numberOfBirds - 1; i++) {
                if (i % 2 == 1)
                    nf.add(flock.get(i));
                else
                    nf.add(flock.get(i + 2));
            }
            nf.add(flock.get(0));
        }
        flock = nf;
        leftSide = !leftSide;
    }

    public void replaceLeaderWithBest() {
        Population newFlock = new Population();//new flock
        int best = 0;
        for (int i = 1; i < flock.size(); i++) {
            if (flock.get(i).getFitness() <= flock.get(best).getFitness()) {
                best = i;
            }
        }
        if (best == 0) return;//if best of the flock is the leader itself, then simply return
        newFlock.add(flock.get(best));
        for (int i = 1; i < best; i++)
            newFlock.add(flock.get(i));
        if (best % 2 == 1) {//if best is located on left
            for (int i = best; i < numberOfBirds - 2; i++) {
                if (i % 2 == 1)
                    newFlock.add(flock.get(i + 2));
                else
                    newFlock.add(flock.get(i));
            }
            newFlock.add(flock.get(0));
            newFlock.add(flock.get(numberOfBirds - 1));
        } else {//if best is located on right
            for (int i = best; i < numberOfBirds - 1; i++) {
                if (i % 2 == 1)
                    newFlock.add(flock.get(i));
                else
                    newFlock.add(flock.get(i + 2));
            }
            newFlock.add(flock.get(0));
        }
        flock = newFlock;
    }

    public void sortTheSuccessors() {
        Population population;
        Solution leader;
        leader = flock.get(0);
        if (sortAccordingToPerformance == 2) {
            population = new Population();
            for (int i = 1; i < numberOfBirds; i++) {
                population.add(flock.get(i));
            }
            Object list[] = population.toArray();
            Arrays.sort(list);
            flock = new Population();
            flock.add(leader);
            for (int k = 0; k < list.length; k++) {
                flock.add((Solution) list[k]);
            }
        }
    }

    public void writeResults() {
//comment out the following pirces of code for writing the results to a file
//	    try {
//	        BufferedWriter out = new BufferedWriter(new FileWriter(res,true));
//	        endTime=(System.currentTimeMillis()-startTime)/(double) 1000;
//	        out.write(input+"\t"+flock.getMin().getFitness()+"\t"+nob+"\t"+non+"\t"+nof+"\t"+olf+"\t"+Solution.getNumberOfNeighborsCreated()+"\t"+endTime+"\t"+(density)+"\t"+Solution.getNumberOfTypes()+"\t\n");
//	        out.close();
//	    } catch (IOException e) {
//	    	System.out.println(e);
//	    }
//	    try {
//	        BufferedWriter out = new BufferedWriter(new FileWriter(bestRes,true));
//	        out.write(input+": "+flock.getMin()+" "+flock.getMin().getFitness()+"\t\n");
//	        out.close();
//	    } catch (IOException e) {
//	    	System.out.println(e);
//	    }

        // Example data to be written to the Excel file
        String[][] newData = {
            {"PMX", String.valueOf(flock.getMin().getFitness())},
            {"Local Search", "780"}
        };

        // Specify the file path where you want to save the Excel file
        String filePath = "results.xlsx";

        // Create an instance of ExcelWriter and call the appendResultsToExcel method
        ExcelWriter.appendResultsToExcel(newData, filePath);
        endTime = (System.currentTimeMillis() - startTime) / (double) 1000;
        JOptionPane.showMessageDialog(null, "Input file: " + input + "\nSolution Permutation: " + flock.getMin() + "\nCost of the solution: " + flock.getMin().getFitness() + "\nRun time: " + endTime + " seconds.");
    }

}
