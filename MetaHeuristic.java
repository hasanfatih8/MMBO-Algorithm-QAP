import java.io.*;
import java.util.*;


public class MetaHeuristic {
    protected Solution cs, bs; // TODO Unused... Kafa yorma şu anlık
    protected String res; // TODO
    protected int[][] affinity, distance; //aff and dist has length n+1xn+1, index 0 is not used
    protected String input; //input file
    protected double density;


    public MetaHeuristic() {
        res = "res_" + this.getClass().getName() + ".txt";
    }

    public void checkInputFileFormatAndRead() {
        readFlowMatrixAndDistanceMatrixFile();
    }

    public void readFlowMatrixAndDistanceMatrixFile() {
        Scanner s;
        int numberOfPeople;//# of people or offices
        try {
            s = new Scanner(new File(input));
            numberOfPeople = s.nextInt();
            affinity = new int[numberOfPeople + 1][numberOfPeople + 1];
            distance = new int[numberOfPeople + 1][numberOfPeople + 1];

//			now reading the flow (affinity) values
            for (int i = 1; i < numberOfPeople + 1; i++) {
                try {
                    for (int j = 1; j < numberOfPeople + 1; j++) {
                        affinity[i][j] = s.nextInt();
                        if (affinity[i][j] != 0) {
                            density += 1;
                        }
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Input type is not integer!!!");
                } catch (NoSuchElementException e) {
                    System.out.println("Line exhausted, no more inputs!!!");
                }
            }
            density = density / Math.pow(numberOfPeople, 2);

//			now reading the distance values
            for (int i = 1; i < numberOfPeople + 1; i++) {
                try {
                    for (int j = 1; j < numberOfPeople + 1; j++) {
                        distance[i][j] = s.nextInt();
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Input type is not integer!!!");
                } catch (NoSuchElementException e) {
                    System.out.println("Line exhausted, no more inputs!!!");
                }
            }
        } catch (IOException exception) {
            System.out.println(exception);
        }
    }
}
