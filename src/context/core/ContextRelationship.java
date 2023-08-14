package context.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import context.matrix.RMatrixes;
/**
 * @author syue2
 * 
 *the third dimension, the possible point of the relationship grade can be 100. 
 *There can be different contracts, we use 1-10 as one segment, and if the digit in ones is greater than 7 or 8, it
 *can be counted as significant enough to affect some decision related to that corresponding digit in tens.
 *
 *However, we only use one rule here, if the number is more that 80, then we count it as significant enough.
 */
public class ContextRelationship extends Ontology{
	private List<RelationshipPair> relationshipTypeList = new ArrayList<RelationshipPair>();
	private RMatrixes rMatrixes = new RMatrixes();
	
	public ContextRelationship(){
		
	}

	/**
	 * @return the rMatrixes
	 */
	public RMatrixes getrMatrixes() {
		return rMatrixes;
	}
	/**
	 * @param rMatrixes the rMatrixes to set
	 */
	public void setrMatrixes(RMatrixes rMatrixes) {
		this.rMatrixes = rMatrixes;
	}

	public void addEnity(int size){
		
	}

	/**
	 * @return the relationshipTypeList
	 */
	public List<RelationshipPair> getRelationshipTypeList() {
		return relationshipTypeList;
	}

	/**
	 * @param relationshipTypeList the relationshipTypeList to set
	 */
	public void setRelationshipTypeList(List<RelationshipPair> relationshipTypeList) {
		this.relationshipTypeList = relationshipTypeList;
	}

	



}
