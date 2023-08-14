package context.matrix;

import java.util.Vector;

public class MatrixesForDecision {
	//Using vector for 2D array. It is threadsafe.
	private Vector<Vector<Integer>>  casmMatrix= new Vector<Vector<Integer>>();
	private Vector<Vector<Double>>  predicationMatrix= new Vector<Vector<Double>>();
	private Vector<Vector<String>>  functionMatrix= new Vector<Vector<String>>(); //for decisions
	private Vector<Vector<Integer>>  decisionMatrix= new Vector<Vector<Integer>>(); //decision can be injected from the outside and dynamically build the decision matrix
	
	//the spot in the new column which is related to the preState will be set to 1
	public Vector addState(int row){
		//handle initial
		if(casmMatrix.size()==0){
			Vector<Integer> r=new Vector<>();
			r.add(0);
			casmMatrix.add(r);
			return casmMatrix;
		}
		
		//add a row of matrix.size +1
		Vector<Integer> r=new Vector<>();
		for(int i=0;i<casmMatrix.size()+1;i++){
            r.add(0);
        }
		casmMatrix.add(r);
		
		//add column to the original rows
		for(int j=0;j<casmMatrix.size()-1;j++){
			r=casmMatrix.get(j);
			if(j==row){
				casmMatrix.get(j).add(1);
			} else{
				casmMatrix.get(j).add(0);
			}
	    }
		return casmMatrix;
	}
	
	//todo
	public Vector updateHmStates(int row, int column){
		casmMatrix.get(row).set(column, casmMatrix.get(row).get(column).intValue()+1);
		return casmMatrix;
	}

	public Vector<Vector<Integer>> getCasmMatrix() {
		return casmMatrix;
	}

	public void setCasmMatrix(Vector<Vector<Integer>> casmMatrix) {
		this.casmMatrix = casmMatrix;
	}

	public Vector<Vector<Double>> getPredicationMatrix() {
		return predicationMatrix;
	}

	public void setPredicationMatrix(Vector<Vector<Double>> predicationMatrix) {
		this.predicationMatrix = predicationMatrix;
	}

	/**
	 * @return the functionMatrix
	 */
	public Vector<Vector<String>> getFunctionMatrix() {
		return functionMatrix;
	}

	/**
	 * @param functionMatrix the functionMatrix to set
	 */
	public void setFunctionMatrix(Vector<Vector<String>> functionMatrix) {
		this.functionMatrix = functionMatrix;
	}
}
