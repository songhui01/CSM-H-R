package context.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import context.matrix.Matrixes;
import context.matrix.SMatrixes;

public class ContextSituationStateMachine{
	private SMatrixes smatrixes = new SMatrixes(); //use this structure to store the core data; matrix for state transitions, and matrix for prediction
	//private ContextAttribute ca = new ContextAttribute(); //contains context attribute name and a list of states
	private HashMap<String, Integer> hmstates = new HashMap<String, Integer>(); //a key value map  like (home, 1); ##the string here can be extended(replaced) using the state class
	private HashMap<String, Integer> hmdecisions = new HashMap<String, Integer>(); //a key value map  like (take, 1)
	private int currentState = -1; //initial value is -1
	private String lastTransition; //a string string map  like (coffee shop, home, "01")
	
	public ContextSituationStateMachine(){
		
	}
	
	//match input to a specific transition
	public Transition matchTransition(Object input, List inputList){
		return new Transition(new ContextCategory());
	}
	
	public void prettyPrint(){
		
	}

	/**
	 * @return the smatrixes
	 */
	public SMatrixes getSmatrixes() {
		return smatrixes;
	}

	/**
	 * @param smatrixes the smatrixes to set
	 */
	public void setSmatrixes(SMatrixes smatrixes) {
		this.smatrixes = smatrixes;
	}

	/**
	 * @return the transitionList
	 */
	public List<Transition> getTransitionList() {
		List<Transition> l = new ArrayList<Transition>();
		return l;
	}

	/**
	 * @return the hmstates
	 */
	public HashMap<String, Integer> getHmstates() {
		return hmstates;
	}

	/**
	 * @param hmstates the hmstates to set
	 */
	public void setHmstates(HashMap<String, Integer> hmstates) {
		this.hmstates = hmstates;
	}
	
	public void addtoHashMapStates(int place, String stateName){
		this.hmstates.put(stateName, place);
	}

	/**
	 * @return the currentState
	 */
	public int getCurrentState() {
		return currentState;
	}

	/**
	 * @param currentState the currentState to set
	 */
	public void setCurrentState(int currentState) {
		this.currentState = currentState;
	}

	/**
	 * @return the hmdecisions
	 */
	public HashMap<String, Integer> getHmdecisions() {
		return hmdecisions;
	}

	/**
	 * @param hmdecisions the hmdecisions to set
	 */
	public void setHmdecisions(HashMap<String, Integer> hmdecisions) {
		this.hmdecisions = hmdecisions;
	}

	/**
	 * @return the lastTransition
	 */
	public String getLastTransition() {
		return lastTransition;
	}

	/**
	 * @param lastTransition the lastTransition to set
	 */
	public void setLastTransition(String lastTransition) {
		this.lastTransition = lastTransition;
	}
}
