package context.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.HashSet;
import java.util.HashMap;

import context.utility.ListHelper;

public class ContextObject extends Ontology{
	private ContextSituationState currentSituationState;
	private int currentSituationStateID;
	private List<ContextAttributeStateMachine> casml = new ArrayList<ContextAttributeStateMachine>();
	private ContextSituationStateMachine cssm = new ContextSituationStateMachine(); //a situation is a combination of attributes of multiple objects
	//as for now, we only consider the related persons' location information in situation
	//TODO: to extend the context attribute, we can add context attribute type URI, so there can be different type
	//now we only consider one type
	
	//context attribute list
	private List<ContextAttribute> cal = new ArrayList<ContextAttribute>();
	
	//context situation state list
	private List<ContextSituationState> cssl = new ArrayList<ContextSituationState>();
        
        // labels are the ones that are not commonly changing. sex: male or female, 
        private HashMap<String, String> labels = null;
        private HashMap<String, String> conditions = null; // complement & conditions such as time, location, 
	
	public ContextObject(){
		
	}
	
	public ContextObject(String objectName, String objectType, String objectURI){
		this.name = objectName;
		this.type = objectType;
		this.uri = objectURI;
	}
	
	/**
	 * this method is important
	 * It get the current object state, which will be used by combining the related objects' states,
	 * to form a situation for this object
	 * 
	 * The object state include all the current states of the attributes
	 */
	public void getCurrentObjectStateForSituation(){
		
	}
	
	/**
	 * @return the casml
	 */
	public List<ContextAttributeStateMachine> getCasml() {
		return casml;
	}
	/**
	 * @param casml the casml to set
	 */
	public void setCasml(List<ContextAttributeStateMachine> casml) {
		this.casml = casml;
	}


	/**
	 * @return the cssm
	 */
	public ContextSituationStateMachine getCssm() {
		return cssm;
	}

	/**
	 * @param cssm the cssm to set
	 */
	public void setCssm(ContextSituationStateMachine cssm) {
		this.cssm = cssm;
	}

	/**
	 * @return the cal
	 */
	public List<ContextAttribute> getCal() {
		return cal;
	}
	/**
	 * @param cal the cal to set
	 */
	public void setCal(List<ContextAttribute> cal) {
		this.cal = cal;
	}
	

	/**
	 * @return the cssl
	 */
	public List<ContextSituationState> getCssl() {
		return cssl;
	}

	/**
	 * @param cssl the cssl to set
	 */
	public void setCssl(List<ContextSituationState> cssl) {
		this.cssl = cssl;
	}

	/**
	 * @return the currentSituationState
	 */
	public ContextSituationState getCurrentSituationState() {
		return currentSituationState;
	}

	/**
	 * @param currentSituationState the currentSituationState to set
	 */
	public void setCurrentSituationState(ContextSituationState currentSituationState) {
		this.currentSituationState = currentSituationState;
	}

	/**
	 * @return the currentSituationStateID
	 */
	public int getCurrentSituationStateID() {
		return currentSituationStateID;
	}

	/**
	 * @param currentSituationStateID the currentSituationStateID to set
	 */
	public void setCurrentSituationStateID(int currentSituationStateID) {
		this.currentSituationStateID = currentSituationStateID;
	}

	public void addAttributeStateMachine(ContextAttributeStateMachine casm){
		casml.add(casm);
	}
	
	public void addContextAttribute(ContextAttribute ca){
		cal.add(ca);
	}
	
	public void addContextSituationStateToList(ContextSituationState css){
		cssl.add(css);
	}
	
	/**
	 * if return -1, then not exist, if the return value if more than -1, then it is the index of the css
	 * @param css
	 * @return
	 */
	public int existAndIndexInCSSL(ContextSituationState css){
		int exist = -1;
		Iterator it = this.cssl.iterator();
		int tag =0;
		label1:
			while(it.hasNext()){
				ContextSituationState csstmp = (ContextSituationState)it.next();
				int size1= css.getContextAttributeSnapshotList().size();
				int size2= csstmp.getContextAttributeSnapshotList().size();
				if(size1 == size2){
					Iterator it2 = css.getContextAttributeSnapshotList().iterator();
					int tag2=0;
					while(it2.hasNext()){
						ContextAttributeSnapshot casnap = (ContextAttributeSnapshot)it2.next();
						if(!ListHelper.inTheSnapshotList(casnap, csstmp.getContextAttributeSnapshotList())){
							break;
						}else{
							if(tag2==size1-1){
								exist=tag;
								break label1;
							}
						}
						tag2++;
					}
				}
				tag++;
			}
		return exist;
	}
	
	public void prettyPrint(){
		System.out.println("The state machine is of : "+this.name+".");
		int size=0;
		if(casml!=null){
			size=casml.size();
		}
		System.out.println("It has "+size+" ContextAttributeStateMachines.");
		if(size>0){
			System.out.println("They are:");
			Iterator<ContextAttributeStateMachine> it=casml.iterator();
			while(it.hasNext()){
				ContextAttributeStateMachine temp = (ContextAttributeStateMachine)it.next();
				temp.prettyPrint();
			}
		}
		
	}

}
