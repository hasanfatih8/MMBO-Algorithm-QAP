import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class Solution implements Comparable,Cloneable{
	/**
	 * n denotes number of people and offices.
	 */
	private int permutation[]; //permutation has length n+1, index 0 is not used
	//because component types in feeder will be assigned to slots 1 to n
	private int[][] affinity, distance;//aff and dist has length n+1xn+1, index 0 is not used
	private double cost;
	private static int not;
	private ArrayList<Solution> neighbourSet;
	private static int non=0;//counter designed for counting # of ngbrs created. Criterion used for stopping BirdsAlgorithm.
	/**
	 * creates a solution with the given affinity and distance matrices
	 * @param ps
	 */
	public Solution(int[][] affinity,int[][] distance){
		this.affinity=affinity;
		this.distance=distance;
		createRandomConf();
		calculateCost();
	}
	/**
	 * creates a solution with the given affinity and distance matrices
	 * will be used for creating a neighbor and cloning
	 * @param ps
	 * @param conf
	 */
	public Solution(int[][] affinity,int[][] distance, int fc[]){
		this.affinity=affinity;
		this.distance=distance;
		this.permutation=fc;
		calculateCost();
	}
	/**
	 * returns the cost, fitness value of this solution
	 * @return
	 */
	public double getCost(){
		return cost;
	}
	/**
	 * written for genetic algorithm and returns cost of this solution object
	 * @return
	 */
	public double getFitness(){
		return getCost();
	}

	/**
	 * creates a random configuration
	 * called only when a Solution object with no configuration is created.
	 */
	public void createRandomConf(){
			HashSet<Integer> set=new HashSet<Integer>();
			int k=0,j=0;
			for(int i=1;i<affinity.length;i++){
				set.add(i);
			}
			not=set.size();
			permutation=new int[not+1];
			while(!set.isEmpty()){
				k=1+(int)(Math.random()*(not));
				if(set.contains(k)){
					permutation[++j]=k;
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
	public void calculateCost(){
		cost=0;
		int office1,office2;
		for(int i=1;i<affinity.length;i++){
			for(int j=1;j<affinity[i].length;j++){
				office1=permutation[i];
				office2=permutation[j];
				cost+=affinity[i][j]*distance[office1][office2];
			}
		}
	}
	/**
	 * creates a new Solution object as a neighbor of this solution.
	 * This new Solution object is created by simply swapping contents of randomly chosen two slots. 
	 * @return Solution object
	 */
	public Solution neighbour(){
		int conf[]=new int[permutation.length];
		int ex1,ex2;
		ex1=1+(int) (Math.random()*not);
		do{
			ex2=1+(int) (Math.random()*not);
		}while(ex1==ex2);
		for(int i=0;i<conf.length;i++){
			conf[i]=permutation[i];
		}
		conf[ex1]=permutation[ex2];
		conf[ex2]=permutation[ex1];
		return new Solution(affinity,distance,conf);
	}

	/**
	 * cretaes a neighbor Set of this solution which includes non elements
	 * @param non
	 */
	public void createNeighborSet(int nongbr){
		neighbourSet=new ArrayList<Solution>();
		for(int i=0;i<nongbr;i++){
			neighbourSet.add(neighbour());
			non++;
		}
		sortNeighbours();
	}
	
	/**
	 * returns best neighbour of this solution and removes it from the neighbor set
	 * @return Solution
	 */
	public Solution getBestNeighbour(){
		Solution n=neighbourSet.get(0);
		neighbourSet.remove(0);
		return n;
	}
	/**
	 * returns best neighbor of this solution and DOES NOT remove it from the neighbor set
	 * @return Solution
	 */
	public Solution checkBestNeighbour(){
		Solution n=neighbourSet.get(0);
		return n;
	}
	
	/**
	 * adds the given solution (n) to the neighbor set of this solution 
	 * adding a new solution to the neighbor list requires resorting that list 
	 * @param n
	 */
	public void addNeighbour(Solution n){
		neighbourSet.add(n);
		sortNeighbours();
	}
	private void sortNeighbours(){
		Object list[]=neighbourSet.toArray();
		Arrays.sort(list);
		neighbourSet.clear();
		for(int i=0;i<list.length;i++){
			neighbourSet.add((Solution)list[i]);
		}
	}
	/**
	 * returns the neighbor set of this solution
	 * @return
	 */
	public ArrayList<Solution> getNeighbourSet(){
		return neighbourSet;
	}
	/**
	 * overidden for cloning objects when the same copy is created while crossover is applied 
	 */
	public Object clone() {
		int conf[]=new int[permutation.length];
		for(int i=0;i<conf.length;i++){
			conf[i]=permutation[i];
		}
		return new Solution(affinity,distance,conf);
	}
	/**
	 * a solution with a smaller fitness value is considered as a small solution.  
	 */
	public int compareTo(Object o){
		if(o instanceof Solution){
			return (int)(this.getFitness()-((Solution)o).getFitness());
		}
		else
			return 0;
	}
	/**
	 * returns the permutation (feeder configuration) of a problem
	 * @return
	 */
	public int[] getFC(){
		return permutation;
	}
	/**
	 * returns the string rep. of the solution in terms of permutation matrix
	 */
	public String toString(){
		String conf="";
		for(int i=1;i<permutation.length;i++){
			conf+=permutation[i]+", ";
		}
		return conf;
	}
	public boolean equals(Object obj){
		return equals((Solution) obj);
	}
	public boolean equals(Solution s){
		int[] sfc=s.getFC();
		boolean same=true;
		for(int i=1;i<permutation.length;i++){
			same=permutation[i]==sfc[i];
			if(!same) break;
		}
		return same;
	}
	/**
	 * returns number of types (people, offices) for this solution
	 * @return
	 */
	public static int getNOT(){
		return not;
	}
	/**
	 * returns number of neighbors created so far from the last call of resetNON()
	 * @return
	 */
	public static int getNON(){
		return non;
	}
	/**
	 * resets the number of neighbors to zero
	 * should be called when an algorithm starts
	 *
	 */
	public static void resetNON(){
		non=0;
	}
}
