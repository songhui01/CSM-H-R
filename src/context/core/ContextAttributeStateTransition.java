package context.core;

import java.util.Map;

/**
 * @author Songhui Yue
 * The transition now has no URI. But it is represented using the last state and the next state such as "01" or "10".
 */
public class ContextAttributeStateTransition extends Ontology{
	
	
	public ContextAttributeStateTransition(){
		
	}
	
        /**
         * 
         * @param stateName
         * @param stateURI
         * @param id
         * @param type 
         * 
         * The type can be 1-step, 2-steps, 3-steps, or paths (more than one step) and circles
         */
	public ContextAttributeStateTransition(String stateName, String stateURI, int id, String type){
		this.setUri(stateURI);
		this.setName(stateName);
                this.setId(id);
                this.setType(type);
	}
	
	public float getPossibility(ContextAttributeStateTransition current, ContextAttributeStateTransition target){
		return 1.00f;
	}

}
