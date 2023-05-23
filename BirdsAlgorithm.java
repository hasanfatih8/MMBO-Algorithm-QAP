import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

import javax.swing.JOptionPane;


public class BirdsAlgorithm extends MetaHeuristic {
	private int nob;//# of birds, n 
	private int non;//# of neighbors, k
	
	private int olf;//overlap factor, x
	private int noi;//# of iterations, K (in this implementation, creating a neighbour counts for an iteration)
	private Population flock;//flock of birds (solutions) 0 is the leader and the following elements 
//	of the ArrayList(population) are listed as follows.  See that odd ones are on the left and even one 
//	are on the right
//					0
//				1		2
//			3				4
//		5						6
	private int initialFlockSortedAccToPerf=1;//1 means sorting the initial flock randomly,
	//while 2 means sorting them according to their performance
	private int nof;//# of flapping (kanat cirpma), m
	private int leaderExchangeMode=1;//1 means exchange the leader with the successor
	//2 means exchanging the leader with the best
	//3 means exchanging if its performance is gets worse
	private int sortAccToPerf=1;//1 means the flock permutation stays the same
	//2 means sorting them according performance
	
	private boolean leftSide=true;
	private boolean leaderImproves=true;
	private long st;
	private double et;
	private static String bestRes="bestResults.txt";
	public static void main(String args[]){
	}
	public BirdsAlgorithm(int n, int k, int m, int x, int ifsatp, int lem, int satp, String input){
		this.nob=n;
		this.non=k;
		this.nof=m;
		this.olf=x;
		initialFlockSortedAccToPerf=ifsatp;
		leaderExchangeMode=lem;
		sortAccToPerf=satp;
		this.input=input;
		checkInputFileFormatAndRead();
//		System.out.println(flowMap);
		st=System.currentTimeMillis();
		flock=new Population();
		createInitialFlock();
		Solution.resetNON();
		noi=(int)Math.pow(Solution.getNOT(),3);
//		System.out.println(noi+" "+Solution.getNON());
		while(Solution.getNON()<noi){
			for(int i=0;i<nof;i++){
				flyFlock();
			}
//			System.out.println("Flock flied");
			replaceLeader();
//			System.out.println("Leader replaced");
			sortTheSuccessors();
//			System.out.println("Successors sorted");
			leaderImproves=true;
//			System.out.println(Solution.getNON());
		}
		writeResults();
	}
	/**
	 * This method creates nob solutions (birds), adds them to the flock and
	 * sorts them according to their performance (fitness) if the initialFlockSortedAccToPerf paramater 
	 * is given as 2 (see kuslar-150110.doc).
	 * @param sorted
	 */
	public void createInitialFlock(){
		for(int i=0;i<nob;i++){
			flock.add(new Solution(affinity,distance));
		}
		if(initialFlockSortedAccToPerf==2){
			Object list[]=flock.toArray();
			flock.clear();
			Arrays.sort(list);
			for(int k=0;k<list.length;k++)
				flock.add((Solution) list[k]);
		}
	}
	public void flyFlock(){
//kuþ önce kendi komþu kümesine bakacak, eðer iyileþmezse öndeki kuþtan komþu isticek.
		
		Solution leader,cs,besto;//best 1 used for repalcing the leader, best other used for transferring following birds
		//create neighbors of each solution
		Solution bests[]=new Solution[nob];
		
		
//		System.out.println(flock);
		leader=flock.get(0);
		leader.createNeighborSet(non);
//		System.out.println(leader.getNeighbourSet().toString());
//		System.out.println("Neighbors of leader are created, leader: "+leader);
		for(int i=1;i<nob;i++){
			flock.get(i).createNeighborSet(non-olf);
//			System.out.println(flock.get(i)+" Nhgbrs: "+flock.get(i).getNeighbourSet().toString());
		}
//		System.out.println("Neighbors of each solution are created");
		
		leader=flock.get(0);
		bests[0]=leader.getBestNeighbour();
		cs=flock.get(1);
		besto=cs.getBestNeighbour();
		if(besto.getFitness()<=cs.getFitness()){
			bests[1]=besto;
		}
		else if(leader.checkBestNeighbour().getFitness()<=cs.getFitness()){
			bests[1]=leader.getBestNeighbour();
		}
		
		cs=flock.get(2);
		besto=cs.getBestNeighbour();
		if(besto.getFitness()<=cs.getFitness()){
			bests[2]=besto;
		}
		else if(leader.checkBestNeighbour().getFitness()<=cs.getFitness()){
			bests[2]=leader.getBestNeighbour();
		}
		
		for(int i=3;i<nob;i++){
			cs=flock.get(i);
			besto=cs.getBestNeighbour();
			if(besto.getFitness()<=cs.getFitness()){
				bests[i]=besto;
			}
			else if(flock.get(i-2).checkBestNeighbour().getFitness()<=cs.getFitness()){
				bests[i]=flock.get(i-2).getBestNeighbour();
			}
		}

		if(flock.get(0).getFitness()<=bests[0].getFitness())
			leaderImproves=false;
		//replace the current birds with their best nghbrs, if they are better
		for(int i=0;i<nob;i++){
//			System.out.println(flock.get(i)+": "+flock.get(i).getNeighbourSet());
			if(flock.get(i).getFitness()>=bests[i].getFitness()){
				flock.remove(i);
				flock.add(i,bests[i]);
			}
		}

//	===============================			
			
//		Solution leader,cs,besto;//best 1 used for repalcing the leader, best other used for transferring following birds
//		//create neighbors of each solution
//		Solution bests[]=new Solution[nob];
		
//		System.out.println(flock);
		leader=flock.get(0);
		leader.createNeighborSet(non);
//		System.out.println(leader.getNeighbourSet().toString());
//		System.out.println("Neighbors of leader are created, leader: "+leader);
		for(int i=1;i<nob;i++){
			flock.get(i).createNeighborSet(non-olf);
//			System.out.println(flock.get(i)+" Nhgbrs: "+flock.get(i).getNeighbourSet().toString());
		}
//		System.out.println("Neighbors of each solution are created");
		//add neighbors of leader to its immediate followers
		leader=flock.get(0);
		bests[0]=leader.getBestNeighbour();
//		System.out.println("Leader and its best ngbr: "+leader+", "+bests[0]);
		for(int i=0;i<2*olf;i++){
			besto=leader.getBestNeighbour();
//			System.out.print(i+" "+besto+",");
			if(i%2==0){
				flock.get(1).addNeighbour(besto);
			}
			else{
				flock.get(2).addNeighbour(besto);
			}
		}
//		System.out.println();
		
//		the following birds of the leader are determining their best elements and next olf neighbours are 
//		trasferred to their followers.  Finally if a solution's best nghbr is better than it, it is replaced. 
		for(int i=1;i<nob-2;i++){
			cs=flock.get(i);
//			System.out.println(i+": "+cs+cs.getNeighbourSet().toString());
			bests[i]=cs.getBestNeighbour();
			for(int j=0;j<olf;j++){
				besto=cs.getBestNeighbour();
				flock.get(i+2).addNeighbour(besto);
			}
//			System.out.println("/t"+(i+2)+": "+flock.get(i+2)+flock.get(i+2).getNeighbourSet().toString());
		}
		bests[nob-2]=flock.get(nob-2).getBestNeighbour();
		bests[nob-1]=flock.get(nob-1).getBestNeighbour();

		if(flock.get(0).getFitness()<=bests[0].getFitness())
			leaderImproves=false;
		//replace the current birds with their best nghbrs, if they are better
		for(int i=0;i<nob;i++){
//			System.out.println(flock.get(i)+": "+flock.get(i).getNeighbourSet());
			if(flock.get(i).getFitness()>=bests[i].getFitness()){
				flock.remove(i);
				flock.add(i,bests[i]);
			}
		}
//		System.out.println("Flock after one flapping: "+flock);
	}
	public void replaceLeader(){
		switch(leaderExchangeMode){
		case 1://change with the immediate successor
			replaceLeaderWithSuccessor();
			break;
		case 2://change with the best
			replaceLeaderWithBest();
			break;
		case 3://change with the best if leader does not improve
			if(!leaderImproves){
				replaceLeaderWithBest();
			}
			break;
		}
	}
	public void replaceLeaderWithSuccessor(){
		Population nf=new Population();//new flock
		if(leftSide){
			nf.add(flock.get(1));
			for(int i=1;i<nob-2;i++){
				if(i%2==1)
					nf.add(flock.get(i+2));
				else
					nf.add(flock.get(i));
			}
			nf.add(flock.get(0));
			nf.add(flock.get(nob-1));
		}
		else{
			nf.add(flock.get(2));
			for(int i=1;i<nob-1;i++){
				if(i%2==1)
					nf.add(flock.get(i));
				else
					nf.add(flock.get(i+2));
			}
			nf.add(flock.get(0));
		}
		flock=nf;
		leftSide=!leftSide;
	}
	public void replaceLeaderWithBest(){
		Population nf=new Population();//new flock
		int best=0;
		for(int i=1;i<flock.size();i++){
			if(flock.get(i).getFitness()<=flock.get(best).getFitness()){
				best=i;
			}
		}
		if(best==0) return;//if best of the flock is the leader itself, then simply return
		nf.add(flock.get(best));
		for(int i=1;i<best;i++)
			nf.add(flock.get(i));
		if(best%2==1){//if best is located on left
			for(int i=best;i<nob-2;i++){
				if(i%2==1)
					nf.add(flock.get(i+2));
				else
					nf.add(flock.get(i));
			}
			nf.add(flock.get(0));
			nf.add(flock.get(nob-1));
		}
		else{//if best is located on right
			for(int i=best;i<nob-1;i++){
				if(i%2==1)
					nf.add(flock.get(i));
				else
					nf.add(flock.get(i+2));
			}
			nf.add(flock.get(0));
		}
		flock=nf;
	}
	public void sortTheSuccessors(){
		Population p;
		Solution leader;
		leader=flock.get(0);
		if(sortAccToPerf==2){
			p=new Population();
			for(int i=1;i<nob;i++){
				p.add(flock.get(i));
			}
			Object list[]=p.toArray();
			Arrays.sort(list);
			flock=new Population();
			flock.add(leader);
			for(int k=0;k<list.length;k++){
				flock.add((Solution) list[k]);
			}
		}
	}
	public void writeResults(){
//comment out the following pirces of code for writing the results to a file
//	    try {
//	        BufferedWriter out = new BufferedWriter(new FileWriter(res,true));
//	        et=(System.currentTimeMillis()-st)/(double) 1000;
//	        out.write(input+"\t"+flock.getMin().getFitness()+"\t"+nob+"\t"+non+"\t"+nof+"\t"+olf+"\t"+Solution.getNON()+"\t"+et+"\t"+(density)+"\t"+Solution.getNOT()+"\t\n");
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
		et=(System.currentTimeMillis()-st)/(double) 1000;
        JOptionPane.showMessageDialog(null,"Input file: "+input+"\nSolution Permutation: "+flock.getMin()+"\nCost of the solution: "+flock.getMin().getFitness()+"\nRun time: "+et+" seconds.");
	}
	
}
