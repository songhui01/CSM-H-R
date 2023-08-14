package context.matrix;

import java.util.Vector;

public class Matrixes {
	//Using vector for 2D array. It is threadsafe.
	//The important idea about matrix is that we can use deep matrix to represent the multiple level conditions such as state(transition)/decision
	private Vector<Vector<Integer>>  casmMatrix= new Vector<Vector<Integer>>();
	private Vector<Vector<Double>>  predicationMatrix= new Vector<Vector<Double>>();
	private Vector<Vector<String>>  functionMatrix= new Vector<Vector<String>>(); //for decisions
	private Vector<Vector<Vector<Integer>>>  decisionMatrix= new Vector<Vector<Vector<Integer>>>(); //decision can be injected from the outside and dynamically build the decision matrix
	
	//the spot in the new column which is related to the preState will be set to 1
	public Vector addState(int preState){ 
		//handle initial
		if(casmMatrix.size()==0||preState==-1){
			Vector<Integer> r=new Vector<>();
			r.add(0);
			casmMatrix.add(r);
			
			return casmMatrix;
		}
		
		//add a row of matrix.size
		Vector<Integer> r=new Vector<>();
		for(int i=0;i<casmMatrix.size();i++){
            r.add(0);
        }
		casmMatrix.add(r);
		
		//add column to all rows
		for(int j=0;j<casmMatrix.size();j++){
			r=casmMatrix.get(j);
			if(j==preState){
				r.add(1);
			} else{
				r.add(0);
			}
	    }
		return casmMatrix;
	}
	
        /**
         * 
         * @param preState
         * @param decision
         * @return 
         * 
         * TODO
         * we are now only handling binary decisions, more types of decisons and be handled in the future
         */
	public Vector addStateForDeicision(int preState, int decision){ //if decision is 1, then it is take, if decision is 0, then it is pass, this can be extensible later by refactoring
		//handle initial
		if(decisionMatrix.size()==0||preState==-1){
			//handle decisionMatrix 2019-01-15
			Vector<Vector<Integer>> rd=new Vector<Vector<Integer>>();
			Vector<Integer> rd2=new Vector<>();
			if(decision == 1){
				rd2.add(0); //the first one is for "take" in the case of elevator "take/pass"
				rd2.add(0); //the second number is for counting the pass in the case of elevator "take/pass"
			}else{
				rd2.add(0); //the first one is for "take" in the case of elevator "take/pass"
				rd2.add(0); //the second number is for counting the pass in the case of elevator "take/pass"
			}
			rd.add(rd2);
			decisionMatrix.add(rd);
			
			return decisionMatrix;
		}
		
		
		//handle decisionMatrix 2019-01-15
		//add a row of matrix.size
		Vector<Vector<Integer>> rd=new Vector<Vector<Integer>>();
		for(int i=0;i<decisionMatrix.size();i++){
			Vector<Integer> rd2=new Vector<>();
			rd2.add(0); //the first one is for "take" in the case of elevator "take/pass"
			rd2.add(0); //the second number is for counting the pass in the case of elevator "take/pass"
            rd.add(rd2);
        }
		decisionMatrix.add(rd);
		
		//add column to the all rows
		//handle decisionMatrix 2019-01-15
		for(int j=0;j<decisionMatrix.size();j++){
			if(j==preState){
				Vector<Integer> rd2=new Vector<>();
				if(decision == 1){
					rd2.add(1); //the first one is for "take" in the case of elevator "take/pass"
					rd2.add(0); //the second number is for counting the pass in the case of elevator "take/pass"
				}else{
					rd2.add(0); //the first one is for "take" in the case of elevator "take/pass"
					rd2.add(1); //the second number is for counting the pass in the case of elevator "take/pass"
				}
				decisionMatrix.get(j).add(rd2);
			} else{
				Vector<Integer> rd2=new Vector<>();
				rd2.add(0); 
				rd2.add(0);
				decisionMatrix.get(j).add(rd2);
			}
	    }
		
		return decisionMatrix;
	}
	
	//todo
	public Vector updateHmStates(int row, int column){
		casmMatrix.get(row).set(column, casmMatrix.get(row).get(column).intValue()+1);
		return casmMatrix;
	}
	
	//handle update decisionMatrix 2019-01-15
	public Vector updateDecisionMatrixStates(int row, int column, int decision){
		if(decision == 1){
			decisionMatrix.get(row).get(column).set(0, decisionMatrix.get(row).get(column).get(0).intValue()+1);
		}else{
			decisionMatrix.get(row).get(column).set(1, decisionMatrix.get(row).get(column).get(1).intValue()+1);
		}
		return decisionMatrix;
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

	/**
	 * @return the decisionMatrix
	 */
	public Vector<Vector<Vector<Integer>>> getDecisionMatrix() {
		return decisionMatrix;
	}

	/**
	 * @param decisionMatrix the decisionMatrix to set
	 */
	public void setDecisionMatrix(Vector<Vector<Vector<Integer>>> decisionMatrix) {
		this.decisionMatrix = decisionMatrix;
	}
}
