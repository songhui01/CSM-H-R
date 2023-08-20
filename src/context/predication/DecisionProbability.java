package context.predication;

import java.io.File;
import java.io.IOException;
import java.util.Vector;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import context.core.ContextDomain;

public class DecisionProbability {
	
	public DecisionProbability(){
		
	}
	
	public double getProbabilityByTransition(int start, int end, String domainId) throws JsonParseException, JsonMappingException, IOException{
		ObjectMapper mapper = new ObjectMapper();
		Vector<Vector<Vector<Integer>>> decisionMatrix= new Vector<Vector<Vector<Integer>>>();
		//JSON from file to Object
 		ContextDomain cd = mapper.readValue(new File("dataFiles/casmfile_domain_"+domainId+".json"), ContextDomain.class);
		decisionMatrix = cd.getCcl().get(3).getCol().get(0).getCasml().get(0).getMatrixes().getDecisionMatrix();
		double take = decisionMatrix.get(start).get(end).get(0)*1.0;
		double pass = decisionMatrix.get(start).get(end).get(1)*1.0;
		double prob = (double)take/(take+pass);
		return prob;
	}

}
