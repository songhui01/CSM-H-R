package context.core;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Songhui Yue
 * ?Will this class extends the object in convertion
 */
public class ContextAttribute extends Ontology{
	private List<ContextAttributeState> contextAttributeStatesList = new ArrayList<ContextAttributeState>();
	
	public ContextAttribute(){
	}
	
	public ContextAttribute(String name){
		this.setName(name);
	}

	/**
	 * @return the contextAttributeStatesList
	 */
	public List<ContextAttributeState> getContextAttributeStatesList() {
		return contextAttributeStatesList;
	}

	/**
	 * @param contextAttributeStatesList the contextAttributeStatesList to set
	 */
	public void setContextAttributeStatesList(
			List<ContextAttributeState> contextAttributeStatesList) {
		this.contextAttributeStatesList = contextAttributeStatesList;
	}
	
	public void addContextAttributeStateToList(ContextAttributeState cas){
		this.contextAttributeStatesList.add(cas);
	}
}
