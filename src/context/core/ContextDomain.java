package context.core;

import context.privacy.ObjectURIMapping;
import java.util.ArrayList;
import java.util.List;

public class ContextDomain extends Ontology{
        
        /* 
         * Added in June 2023:
         * time_interval is like the clock in a processor to decide the saturation time for denoting how long an event happens before 
         * or after another event so that we can know the two events temporally correlated with each other. For smart elevator example, 
         * we can set it to 60 sec hierarchy is the total levels of the domain model, for sometimes the code need to refer to a specific
         * level and a specific attribute step stores the number of steps the domain allows the computing to consider. 
         *
         * The larger the step is, the more the computing may happen. For example, 2.13 means that two cycles and 3 steps can be considered.
         */
        private int time_interval = 60;
        private int h = 1; // hierarchy
        private float r = 0.1f; // rounds and steps, precision for float is enough
        private double steadyStateRate = 0.4; // Default is 0.4, which only uses highest state frequency: hsf = 45, hsf*ssr = 45*0.4 = 20 is to be used to determine steady state.
                                               // Different algorithms can be developed depends on the highest frequency and lowest frequency for different attribute
        
        //TODO, move col to com for specially processing privacy
        private ObjectURIMapping com = new ObjectURIMapping();  
	private List<String> col_domain = new ArrayList<String>(); //use object URI to identify, a list of all entities
        
	private ContextRelationship cr = new ContextRelationship(); //for computing
	private List<ContextCategory> ccl = new ArrayList<ContextCategory>(); //for modeling
	

	public ContextDomain(){
	}
	
	public ContextDomain(int domainId, String domainName, String domainURI){
		this.id=domainId;
		this.name=domainName;
		this.uri=domainURI;
	}
	
	public void addContextCategory(ContextCategory cc){
		this.ccl.add(cc);
	}
	
	public void addContextObjectURI(String uri){
		this.col_domain.add(uri);
		cr.addEnity(this.col_domain.size());
	}
	
	/**
	 * @return the col_domain
	 */
	public List<String> getCol_domain() {
		return col_domain;
	}

	/**
	 * @param col_domain the col_domain to set
	 */
	public void setCol_domain(List<String> col_domain) {
		this.col_domain = col_domain;
	}

	/**
	 * @return the cr
	 */
	public ContextRelationship getCr() {
		return cr;
	}

	/**
	 * @param cr the cr to set
	 */
	public void setCr(ContextRelationship cr) {
		this.cr = cr;
	}

	/**
	 * @return the ccl
	 */
	public List<ContextCategory> getCcl() {
		return ccl;
	}

	/**
	 * @param ccl the ccl to set
	 */
	public void setCcl(List<ContextCategory> ccl) {
		this.ccl = ccl;
	}

        public int getTime_interval() {
            return time_interval;
        }

        public void setTime_interval(int time_interval) {
            this.time_interval = time_interval;
        }

        public int getH() {
            return h;
        }

        public void setH(int h) {
            this.h = h;
        }

        public float getR() {
            return r;
        }

        public void setR(float r) {
            this.r = r;
        }
	
        public double getSteadyStateRate() {
            return steadyStateRate;
        }

        public void setSteadyStateRate(double steadyStateRate) {
            this.steadyStateRate = steadyStateRate;
        }

}
