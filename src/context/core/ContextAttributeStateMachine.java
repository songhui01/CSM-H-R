package context.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import context.matrix.Matrixes;

/**
 * @author Songhui Yue
 * Core Class
 */
public class ContextAttributeStateMachine{
	private Matrixes matrixes = new Matrixes(); //use this structure to store the core data; matrix for state transitions, and matrix for prediction
	//private ContextAttribute ca = new ContextAttribute(); //contains context attribute name and a list of states
	private HashMap<String, Integer> hmstates = new HashMap<String, Integer>(); //a key value map  like (home, 1); ##the string here can be extended(replaced) using the state class
                                                                                    // suppose the home index is 1, then (home, 1);
	private HashMap<String, Integer> hmdecisions = new HashMap<String, Integer>(); //a key value map  like (take, 1)
	private int currentState = -1; //initial value is -1
	private String lastTransition; //a string string map  like (coffee shop, home, "01")
	
	public ContextAttributeStateMachine(){
		
	}
	
	//match input to a specific transition
	public Transition matchTransition(Object input, List inputList){
		return new Transition(new ContextCategory());
	}
	
	public void prettyPrint(){
		
	}

	/**
	 * @return the transitionList
	 */
	public List<Transition> getTransitionList() {
		List<Transition> l = new ArrayList<Transition>();
		return l;
	}
	
	public static <T, E> T getKeyByValue(Map<T, E> map, E value) {
	    for (Entry<T, E> entry : map.entrySet()) {
	        if (Objects.equals(value, entry.getValue())) {
	            return entry.getKey();
	        }
	    }
	    return null;
	}
	
	public String extractCurrentStateName(){
		return getKeyByValue(hmstates, currentState);
	}

	/**
	 * @return the matrixes
	 */
	public Matrixes getMatrixes() {
		return matrixes;
	}

	/**
	 * @param matrixes the matrixes to set
	 */
	public void setMatrixes(Matrixes matrixes) {
		this.matrixes = matrixes;
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
	
	public void addtoHashMapStates(int place, String stateURI){
		this.hmstates.put(stateURI, place);
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
