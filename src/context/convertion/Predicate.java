package context.convertion;

public class Predicate {

	private String predicateType;
	private String predicateURI;
	private String predicateName;
	/**
	 * @return the predicateType
	 */
	public String getPredicateType() {
		return predicateType;
	}
	/**
	 * @param predicateType the predicateType to set
	 */
	public void setPredicateType(String predicateType) {
		this.predicateType = predicateType;
	}
	/**
	 * @return the predicateName
	 */
	public String getPredicateName() {
		return predicateName;
	}
	/**
	 * @param predicateName the predicateName to set
	 */
	public void setPredicateName(String predicateName) {
		this.predicateName = predicateName;
	}
	/**
	 * @return the predicateURI
	 */
	public String getPredicateURI() {
		return predicateURI;
	}
	/**
	 * @param predicateURI the predicateURI to set
	 */
	public void setPredicateURI(String predicateURI) {
		this.predicateURI = predicateURI;
	}

}
