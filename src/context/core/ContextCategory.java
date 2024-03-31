package context.core;

import context.privacy.ObjectURIMapping;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/** 
 * @author Songhui Yue
 * a context category is like a class in OO design;
 * context objects are the instances of this class in a specific context domain
 * when a context object is moved to a different domain, the context object can be 
 * transformed into that domain by using the states mapping
 */
public class ContextCategory extends Ontology{
    
	//TODO, move col to com for specially processing privacy
        private ObjectURIMapping com = new ObjectURIMapping(); 
	private List<ContextObject> col = new ArrayList<ContextObject>();
        
	//context attribute list for context category, to be refactored to put into an embeddling related list object
	private List<ContextAttribute> cal_category = new ArrayList<ContextAttribute>();
        
	//context situation state list for context category, to be refactored to put into an embeddling related list object
	private List<ContextSituationState> cssl_category = new ArrayList<ContextSituationState>();
	
	public ContextCategory(){
	}
	
	public ContextCategory(String categoryName, String categoryURI){
		this.name=categoryName;
		this.uri=categoryURI;
	}
	
	public void addContextObject(ContextObject co){
		this.col.add(co);
	}
	
	/**
	 * @return the col
	 */
	public List<ContextObject> getCol() {
		return col;
	}

	/**
	 * @param col the col to set
	 */
	public void setCol(List<ContextObject> col) {
		this.col = col;
	}
}
