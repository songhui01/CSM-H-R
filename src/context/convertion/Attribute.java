package context.convertion;

import context.core.Ontology;

public class Attribute extends Ontology{
	private String attributeType;
	private String attributeURI;
	private String attributeName;
	/**
	 * @return the attributeType
	 */
	public String getAttributeType() {
		return attributeType;
	}
	/**
	 * @param attributeType the attributeType to set
	 */
	public void setAttributeType(String attributeType) {
		this.attributeType = attributeType;
	}
	
	/**
	 * @return the attributeName
	 */
	public String getAttributeName() {
		return attributeName;
	}
	/**
	 * @param attributeName the attributeName to set
	 */
	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
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

}
