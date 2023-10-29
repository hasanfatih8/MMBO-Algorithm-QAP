package com.mmbo.operators;

import java.util.HashMap;
import java.util.HashSet;

import com.mmbo.Solution;

public class Crossover {

    static Solution[] applyCrossover(Memeplex.Crossover type, Solution parent1, Solution parent2) {
        switch(type) {
            case OX:
                return orderCrossover(parent1, parent2);
            case PMX:
                return partiallyMatchedCrossover(parent1, parent2);
            case CX:
                return cycleCrossover(parent1, parent2);
            default:
                return new Solution[] {parent1, parent2};
        }
    }

    private static Solution[] orderCrossover(Solution parent1, Solution parent2) {
        
        int startPos, endPos;
        int[] permutationParent1 = parent1.getFeederConfiguration();
        int[] permutationParent2 = parent2.getFeederConfiguration();
        
        do {
            startPos = 1 + (int) (Math.random() * Solution.getNumberOfTypes());
            endPos = 1 + (int) (Math.random() * Solution.getNumberOfTypes());
        } while (startPos > endPos);

        HashSet<Integer> facilitiesInRange = new HashSet<Integer>();

        int[] permutationChild1 = new int[Solution.getNumberOfTypes() + 1];

        for (int i = startPos; i < endPos + 1; i++) {
            permutationChild1[i] = permutationParent1[i];
            facilitiesInRange.add(permutationParent1[i]);
        }

        int pointer = endPos + 1;
        int emptyCities = Solution.getNumberOfTypes() - facilitiesInRange.size();
        
        for (int i = 0; i < emptyCities; i++) {

            if(pointer < Solution.getNumberOfTypes()){

                int pointerParent2 = pointer;
                while(facilitiesInRange.contains(permutationParent2[pointer])) {
                    pointerParent2++;
                }
                
                permutationChild1[pointer] = permutationParent2[pointerParent2]; 
                pointer++; 
                  
            } else {
                pointer = 1;
                i--;
            }
        }
        return new Solution[] {
            new Solution(parent1, permutationChild1)
        };
    }

    private static Solution[] partiallyMatchedCrossover(Solution parent1, Solution parent2) {
        
        int startPos, endPos;
        int[] permutationParent1 = parent1.getFeederConfiguration();
        int[] permutationParent2 = parent2.getFeederConfiguration();
        
        do {
            startPos = 1 + (int) (Math.random() * Solution.getNumberOfTypes());
            endPos = 1 + (int) (Math.random() * Solution.getNumberOfTypes());
        } while (startPos > endPos);

        int[] permutationChild1 = permutationParent1.clone();
        int[] permutationChild2 = permutationParent2.clone();

        HashMap<Integer,Integer> partiallyMatchMap1to2 = new HashMap<Integer,Integer>();
        HashMap<Integer,Integer> partiallyMatchMap2to1 = new HashMap<Integer,Integer>();

        for (int i = startPos; i < endPos + 1; i++) {
            permutationChild1[i] = permutationParent2[i];
            permutationChild2[i] = permutationParent1[i];
            partiallyMatchMap1to2.put(permutationChild1[i], permutationChild2[i]);
            partiallyMatchMap2to1.put(permutationChild2[i], permutationChild1[i]);
        }

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

        return new Solution[] {
            new Solution(parent1, permutationChild1), 
            new Solution(parent2, permutationChild2)
        };
    }


    private static Solution[] cycleCrossover(Solution parent1, Solution parent2) {
        
        // 1 -> 5 -> 2 -> 4 -> 7 -> 3 -> 6

        int[] permutationParent1 = parent1.getFeederConfiguration();
        int[] permutationParent2 = parent2.getFeederConfiguration();
        
        HashSet<Integer> cycle = new HashSet<>();
        HashMap<Integer,Integer> mappedPermutations = new HashMap<>();

        for (int i = 1; i < Solution.getNumberOfTypes() + 1; i++) {
            mappedPermutations.put(permutationParent1[i], permutationParent2[i]);
        }
        
        int lastIndex = permutationParent1[1];
        while(!cycle.contains(lastIndex)) {
            if(mappedPermutations.containsKey(lastIndex)) {
                cycle.add(lastIndex);
                lastIndex = mappedPermutations.get(lastIndex);
            } else {
                throw new NullPointerException("Cycle Crossover: Creating cycle error!!");
            }
        }

        int[] protoChild1 = new int[Solution.getNumberOfTypes() + 1];
        // may be added inversion of it as other child

        for (int i = 1; i < Solution.getNumberOfTypes() + 1; i++) {
            if(cycle.contains(permutationParent1[i])) {
                protoChild1[i] = permutationParent1[i];
            } else {
                protoChild1[i] = permutationParent2[i];
            }
        }

        return new Solution[] {
            new Solution(parent1, protoChild1)
            //new Solution(parent2, null)
        };
        
    }





    
}








