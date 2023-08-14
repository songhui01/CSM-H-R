package context.matrix;

import java.util.Vector;

public class RMatrixes {
	//Using vector for 3D array. It is threadsafe.
	//The important idea about rmatrix is that we can use deep rmatrix to represent the multiple level conditions such as state(transition)/decision
	private Vector<Vector<Integer>>  rmatrix= new Vector<Vector<Integer>>();
	private Vector<Vector<Integer>>  rClosenessMatrix= new Vector<Vector<Integer>>();
	//the spot in the new column which is related to the preState will be set to 1
	public Vector addState(int preState){ 
		//handle initial   if prestate is 0, then it means for rmatrix
		Vector<Integer> r1=new Vector<>();
		Vector<Integer> r2=new Vector<>();
		if(rmatrix.size()==0||preState==-1){
			r1.add(-1);
			r2.add(-1);
			rmatrix.add(r1);
			rClosenessMatrix.add(r2);
			return rmatrix;
		}
		
		//add a row of rmatrix.size
		Vector<Integer> r3=new Vector<>();
		Vector<Integer> r4=new Vector<>();
		for(int i=0;i<rmatrix.size();i++){
            r3.add(-1);
            r4.add(-1);
        }
		
		rmatrix.add(r3);
		rClosenessMatrix.addElement(r4);
		
		//add column to all rows
		for(int j=0;j<rmatrix.size();j++){
			rmatrix.get(j).add(-1);
			rClosenessMatrix.get(j).add(-1);
	    }
		
		return rmatrix;
	}
	
	//todo
	public Vector updateHmStates(int row, int column){
		rmatrix.get(row).set(column, rmatrix.get(row).get(column).intValue()+1);
		return rmatrix;
	}

	/**
	 * @return the rmatrix
	 */
	public Vector<Vector<Integer>> getRmatrix() {
		return rmatrix;
	}

	/**
	 * @param rmatrix the rmatrix to set
	 */
	public void setRmatrix(Vector<Vector<Integer>> rmatrix) {
		this.rmatrix = rmatrix;
	}

	/**
	 * @return the rClosenessMatrix
	 */
	public Vector<Vector<Integer>> getrClosenessMatrix() {
		return rClosenessMatrix;
	}

	/**
	 * @param rClosenessMatrix the rClosenessMatrix to set
	 */
	public void setrClosenessMatrix(Vector<Vector<Integer>> rClosenessMatrix) {
		this.rClosenessMatrix = rClosenessMatrix;
	}



}
