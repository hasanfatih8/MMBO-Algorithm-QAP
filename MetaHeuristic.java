import java.io.*;
import java.util.*;


public class MetaHeuristic {
	protected Solution cs,bs;
	protected String res;
	protected int[][] affinity, distance;//aff and dist has length n+1xn+1, index 0 is not used
	protected String input;//input file
	protected double density;


    public MetaHeuristic(){
    	res="res_"+this.getClass().getName()+".txt";
    }
	public void checkInputFileFormatAndRead(){
		readFlowMatrixAndDistanceMatrixFile();
	}

	public void readFlowMatrixAndDistanceMatrixFile(){
     	Scanner s;
    	int nop;//# of people or offices
		try{
			s=new Scanner(new File(input));
			nop=s.nextInt();
			affinity=new int[nop+1][nop+1];
			distance=new int[nop+1][nop+1];
	
//			now reading the flow (affinity) values
			for(int i=1;i<nop+1;i++){
				try{
					for(int j=1;j<nop+1;j++){
						affinity[i][j]=s.nextInt();
						if(affinity[i][j]!=0){
							density+=1;
						}
					}
				}
				catch(InputMismatchException e){
					System.out.println("Input type is not integer!!!");
				}
				catch(NoSuchElementException e){
					System.out.println("Line exhausted, no more inputs!!!");
				}
			}
			density=density/Math.pow(nop,2);

//			now reading the distance values
			for(int i=1;i<nop+1;i++){
				try{
					for(int j=1;j<nop+1;j++){
						distance[i][j]=s.nextInt();
					}
				}
				catch(InputMismatchException e){
					System.out.println("Input type is not integer!!!");
				}
				catch(NoSuchElementException e){
					System.out.println("Line exhausted, no more inputs!!!");
				}
			}
		}
		catch (IOException exception){
			System.out.println (exception);
		}
    }
}
