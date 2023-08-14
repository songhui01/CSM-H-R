package context.convertion;

public class TripleHR {
	private String RID; // record id
	private ObjectHR object;
	private AttributeHR attribute;
	private ConditionHR condition;
	private StateHR state;

        public ObjectHR getObject() {
            return object;
        }

        public void setObject(ObjectHR object) {
            this.object = object;
        }

        public AttributeHR getAttribute() {
            return attribute;
        }

        public void setAttribute(AttributeHR attribute) {
            this.attribute = attribute;
        }

        public ConditionHR getCondition() {
            return condition;
        }

        public void setCondition(ConditionHR condition) {
            this.condition = condition;
        }

        public StateHR getState() {
            return state;
        }

        public void setState(StateHR state) {
            this.state = state;
        }
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
	
}
