package context.core;

import java.util.Map;

public class ContextAttributeState extends Ontology{
	private boolean steadyStatus; // true or false
        
        // added July 2023 for hierachies
        private ContextObject stateObject = null;
	
	public ContextAttributeState(){
		
	}
	
	public ContextAttributeState(String stateName, String stateURI, String stateType){
		this.uri=stateURI;
		this.name=stateName;
		this.type=stateType;
	}
	
	public float getPossibility(ContextAttributeState current, ContextAttributeState target){
		return 1.00f;
	}

	/**
	 * @return the steadyStatus
	 */
	public boolean isSteadyStatus() {
		return steadyStatus;
	}

	/**
	 * @param steadyStatus the steadyStatus to set
	 */
	public void setSteadyStatus(boolean steadyStatus) {
		this.steadyStatus = steadyStatus;
	}
	
}
