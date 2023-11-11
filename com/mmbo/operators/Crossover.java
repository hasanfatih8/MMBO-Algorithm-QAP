package com.mmbo.operators;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

import com.mmbo.Go;
import com.mmbo.Solution;

/**
 * @author Emir Said Haliloglu
 * The `Crossover` class provides methods for performing crossover operations on `Solution` objects.
 */
public class Crossover {

    //Empty constructor
    private Crossover() {}

    /**
     * Apply the specified crossover type to two parent solutions and generate child solutions.
     *
     * @param type     The type of crossover operation (OX, PMX, or CX).
     * @param parent1  The first parent solution.
     * @param parent2  The second parent solution.
     * @return An array of child solutions resulting from the crossover operation.
     */
    public static Solution applyCrossover(Memeplex.Crossover type, Solution parent1, Solution parent2) {
        switch(type) {
            case OX:
                if(Go.DEBUG_MODE){
                    System.out.println("OX case selected");
                } 
                return orderCrossover(parent1, parent2);
            case PMX:
                if(Go.DEBUG_MODE){
                    System.out.println("PMX case selected");
                }
                return partiallyMatchedCrossover(parent1, parent2);
            case CX:
                if(Go.DEBUG_MODE){
                    System.out.println("CX case selected");
                }
                return cycleCrossover(parent1, parent2);
            default:
                if(Go.DEBUG_MODE){
                    System.out.println("No crossover applied");
                }
                return parent1;
        }
    }

    /**
     * Perform Order Crossover (OX) on two parent solutions.
     *
     * @param parent1 The first parent solution.
     * @param parent2 The second parent solution.
     * @return An array containing a single child solution generated using the OX crossover.
     */
    private static Solution orderCrossover(Solution parent1, Solution parent2) {
        int startPos, endPos;
        //Extract parent permutations
        int[] permutationParent1 = parent1.getFeederConfiguration();
        int[] permutationParent2 = parent2.getFeederConfiguration();
        
        //Select random start and end positions for crossover, ensuring start < end
        do {
            startPos = 1 + (int) (Math.random() * Solution.getNumberOfTypes());
            endPos = 1 + (int) (Math.random() * Solution.getNumberOfTypes());
        } while (startPos >= endPos);
        if(Go.DEBUG_MODE){
            System.out.println("Start position(index): " + startPos + " End position(index): " + endPos);
        }

        //Define a set to keep track of facilities already in the child permutation
        HashSet<Integer> facilitiesInRange = new HashSet<Integer>();

        //Create child permutation array
        int[] permutationChild1 = new int[Solution.getNumberOfTypes() + 1];

        //Copy the selected range from parent 1 to child, update the set
        for (int i = startPos; i < endPos + 1; i++) {
            permutationChild1[i] = permutationParent1[i];
            facilitiesInRange.add(permutationParent1[i]);
        }
        //Print the facilities in range
        if(Go.DEBUG_MODE){
            System.out.println("Facilities in range: " + facilitiesInRange);
        }

        int pointer = endPos + 1;
        int emptyCities = Solution.getNumberOfTypes() - facilitiesInRange.size();
        
        //Copy the remaining facilities from parent 2 to child
        for (int i = 0; i < emptyCities; i++) {
            if(pointer < Solution.getNumberOfTypes()){
                int pointerParent2 = pointer;
                while(facilitiesInRange.contains(permutationParent2[pointerParent2])) {
                    if(pointerParent2 == 32){
                        pointerParent2 = 0;
                    }
                    pointerParent2++;
                }
                permutationChild1[pointer] = permutationParent2[pointerParent2]; 
                pointer++;  
            } else {
                pointer = 1;
                i--;
            }
        }
        if(Go.DEBUG_MODE){
            System.out.println("Child permutation: " + Arrays.toString(permutationChild1));
        }
        return new Solution(parent1, permutationChild1);
    }

    /**
     * Perform Partially Matched Crossover (PMX) on two parent solutions.
     *
     * @param parent1 The first parent solution.
     * @param parent2 The second parent solution.
     * @return An array containing two child solutions generated using the PMX crossover.
     */
    private static Solution partiallyMatchedCrossover(Solution parent1, Solution parent2) {
        int startPos, endPos;
        //Extract parent permutations
        int[] permutationParent1 = parent1.getFeederConfiguration();
        int[] permutationParent2 = parent2.getFeederConfiguration();
        
        //Select random start and end positions for crossover, ensuring start < end
        do {
            startPos = 1 + (int) (Math.random() * Solution.getNumberOfTypes());
            endPos = 1 + (int) (Math.random() * Solution.getNumberOfTypes());
        } while (startPos > endPos);

        //Create child permutation arrays
        int[] permutationChild1 = permutationParent1.clone();
        int[] permutationChild2 = permutationParent2.clone();

        //Create maps to keep track of partially matched genes
        HashMap<Integer,Integer> partiallyMatchMap1to2 = new HashMap<Integer,Integer>();
        HashMap<Integer,Integer> partiallyMatchMap2to1 = new HashMap<Integer,Integer>();
        for (int i = startPos; i < endPos + 1; i++) {
            permutationChild1[i] = permutationParent2[i];
            permutationChild2[i] = permutationParent1[i];
            partiallyMatchMap1to2.put(permutationChild1[i], permutationChild2[i]);
            partiallyMatchMap2to1.put(permutationChild2[i], permutationChild1[i]);
        }
        if(Go.DEBUG_MODE){
            System.out.println("Partially matched map 1 to 2: " + partiallyMatchMap1to2);
            System.out.println("Partially matched map 2 to 1: " + partiallyMatchMap2to1);
        }

        //Update the rest of the child permutations
        for (int i = 1; i < Solution.getNumberOfTypes() + 1; i++) {
            if(i >= startPos && i <= endPos) continue;
            //For child 1
            if(partiallyMatchMap1to2.containsKey(permutationChild1[i])) {
                permutationChild1[i] = partiallyMatchMap1to2.get(permutationChild1[i]);
            }
            //For child 2
            if(partiallyMatchMap1to2.containsKey(permutationChild1[i])) {
                permutationChild1[i] = partiallyMatchMap1to2.get(permutationChild1[i]);
            }
        }
        if(Go.DEBUG_MODE){
            System.out.println("Child 1 permutation: " + java.util.Arrays.toString(permutationChild1));
            System.out.println("Child 2 permutation: " + java.util.Arrays.toString(permutationChild2));
        }

        return new Solution(parent1, permutationChild1);
    }

    /**
     * Perform Cycle Crossover (CX) on two parent solutions.
     *
     * @param parent1 The first parent solution.
     * @param parent2 The second parent solution.
     * @return An array containing a single child solution generated using the CX crossover.
     */
    private static Solution cycleCrossover(Solution parent1, Solution parent2) {
        //Extract parent permutations
        int[] permutationParent1 = parent1.getFeederConfiguration();
        int[] permutationParent2 = parent2.getFeederConfiguration();
        
        //Create a set to keep track of the cycle
        HashSet<Integer> cycle = new HashSet<>();

        //Create a map to keep track of the permutations
        HashMap<Integer,Integer> mappedPermutations = new HashMap<>();
        for (int i = 1; i < Solution.getNumberOfTypes() + 1; i++) {
            mappedPermutations.put(permutationParent1[i], permutationParent2[i]);
        }
        
        //Create the cycle
        int lastIndex = permutationParent1[1];
        while(!cycle.contains(lastIndex)) {
            if(mappedPermutations.containsKey(lastIndex)) {
                cycle.add(lastIndex);
                lastIndex = mappedPermutations.get(lastIndex);
            } else {
                return parent1;
            }
        }

        //Create child permutation array, copy the cycle from parent 1 and fill the rest from parent 2
        int[] protoChild1 = new int[Solution.getNumberOfTypes() + 1]; 
        for (int i = 1; i < Solution.getNumberOfTypes() + 1; i++) {
            if(cycle.contains(permutationParent1[i])) {
                protoChild1[i] = permutationParent1[i];
            } else {
                protoChild1[i] = permutationParent2[i];
            }
        }
        if(Go.DEBUG_MODE){
            System.out.println("Child permutation: " + java.util.Arrays.toString(protoChild1));
        }

        return new Solution(parent1, protoChild1);
    }
    
}








