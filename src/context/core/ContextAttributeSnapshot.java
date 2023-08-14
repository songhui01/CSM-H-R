package context.core;

/**
 * @author Songhui Yue
 * This class is used for Context situation state machine.
 * In a context object which contains CSSM, there is a list of the object of this class
 */
public class ContextAttributeSnapshot {
	private String objectURI;
	private String attributeURI; // it is actually mapped to object type: objectid.attributename
	//. attribute and its value
	private String stateURI;
	public ContextAttributeSnapshot(){
		
	}

	public ContextAttributeSnapshot(String objectURI,  String attributeURI, String stateURI){
		this.objectURI=objectURI;
		this.attributeURI=attributeURI;
		this.stateURI=stateURI;
	}

	/**
	 * @return the objectURI
	 */
	public String getObjectURI() {
		return objectURI;
	}

	/**
	 * @param objectURI the objectURI to set
	 */
	public void setObjectURI(String objectURI) {
		this.objectURI = objectURI;
	}

	/**
	 * @return the attributeURI
	 */
	public String getAttributeURI() {
		return attributeURI;
	}

	/**
	 * @param attributeURI the attributeURI to set
	 */
	public void setAttributeURI(String attributeURI) {
		this.attributeURI = attributeURI;
	}

	/**
	 * @return the stateURI
	 */
	public String getStateURI() {
		return stateURI;
	}

	/**
	 * @param stateURI the stateURI to set
	 */
	public void setStateURI(String stateURI) {
		this.stateURI = stateURI;
	}


}
