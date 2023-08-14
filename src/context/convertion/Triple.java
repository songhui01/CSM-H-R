package context.convertion;

public class Triple {
	private String RID;
	private Subject subject;
	private Object object;
	private Predicate predicate;
	private Condition condition;
	private Decision decision;
	/**
	 * @return the rID
	 */
	public String getRID() {
		return RID;
	}
	/**
	 * @param rID the rID to set
	 */
	public void setRID(String rID) {
		RID = rID;
	}
	/**
	 * @return the subject
	 */
	public Subject getSubject() {
		return subject;
	}
	/**
	 * @param subject the subject to set
	 */
	public void setSubject(Subject subject) {
		this.subject = subject;
	}
	/**
	 * @return the object
	 */
	public Object getObject() {
		return object;
	}
	/**
	 * @param object the object to set
	 */
	public void setObject(Object object) {
		this.object = object;
	}
	/**
	 * @return the predcate
	 */
	public Predicate getPredicate() {
		return predicate;
	}
	/**
	 * @param predcate the predcate to set
	 */
	public void setPredicate(Predicate predcate) {
		this.predicate = predcate;
	}
	/**
	 * @return the conditiohn
	 */
	public Condition getCondition() {
		return condition;
	}
	/**
	 * @param conditiohn the conditiohn to set
	 */
	public void setCondition(Condition condition) {
		this.condition = condition;
	}
	/**
	 * @return the decision
	 */
	public Decision getDecision() {
		return decision;
	}
	/**
	 * @param decision the decision to set
	 */
	public void setDecision(Decision decision) {
		this.decision = decision;
	}
	

}
