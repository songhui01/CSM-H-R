package context.core;

public class RelationshipPair {
	private String relationName;
	private String relationURI;
	
	public RelationshipPair(){
		
	}
	
	public RelationshipPair(String relationName, String relationURI){
		this.relationName=relationName;
		this.relationURI=relationURI;
	}
	/**
	 * @return the relationName
	 */
	public String getRelationName() {
		return relationName;
	}
	/**
	 * @param relationName the relationName to set
	 */
	public void setRelationName(String relationName) {
		this.relationName = relationName;
	}
	/**
	 * @return the relationURI
	 */
	public String getRelationURI() {
		return relationURI;
	}
	/**
	 * @param relationURI the relationURI to set
	 */
	public void setRelationURI(String relationURI) {
		this.relationURI = relationURI;
	}
	

}
