package context.core;

//to be used to make the framework scalable, extensible
public class Decision extends Ontology{
    
    private String decisionName;

    /**
     * @return the decisionName
     */
    public String getDecisionName() {
            return decisionName;
    }

    /**
     * @param decisionName the decisionName to set
     */
    public void setDecisionName(String decisionName) {
            this.decisionName = decisionName;
    }
}
