package context.core;

import java.util.ArrayList;
import java.util.List;

import context.convertion.Object;

//?Will this class extends the object in conversion
public class ContextSituationState extends Ontology{	
	private int id; // since the situation vary a lot. So we don't use URI but only use id to distinguish them.
	private boolean steadyStatus; // true or false
	private List<ContextAttributeSnapshot> contextAttributeSnapshotList = new ArrayList<ContextAttributeSnapshot>();

	public ContextSituationState(){
	}
	
	public ContextSituationState(boolean steadyStatus){
		this.setSteadyStatus(steadyStatus);
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

	/**
	 * @return the contextAttributeSnapshotList
	 */
	public List<ContextAttributeSnapshot> getContextAttributeSnapshotList() {
		return contextAttributeSnapshotList;
	}

	/**
	 * @param contextAttributeSnapshotList the contextAttributeSnapshotList to set
	 */
	public void setContextAttributeSnapshotList(
			List<ContextAttributeSnapshot> contextAttributeSnapshotList) {
		this.contextAttributeSnapshotList = contextAttributeSnapshotList;
	}
	
	public void addContextAttributeSnapshotToList(ContextAttributeSnapshot casnap){
		this.contextAttributeSnapshotList.add(casnap);
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
}
