package context.actuator;

import java.io.IOException;

import org.json.JSONException;

import context.csmGenerationEngine.CSMUpdater;
import context.predication.DecisionProbability;

public class VariousProbabilitiesUtl {
	public static void main(String args[]) throws JSONException, IOException{
		/*JSONObject obj = JasonReader.readJson("config/fromWearableDeviceSystemToCSMCloudServer.json");
		String domainId = obj.getString("domainId");
		
		ObjectMapper mapper = new ObjectMapper();
		//JSON from file to Object
		ContextDomain cd = mapper.readValue(new File("dataFiles/casmfile_domain_"+domainId+".json"), ContextDomain.class);
		
		ObjectMapper om = new ObjectMapper();
		om.enable(SerializationFeature.INDENT_OUTPUT); //pretty print
		String s = om.writeValueAsString(cd);
		System.out.println(s);
		*/
		int start = 1; //start state of the transition
		int end = 2; //end state of the transition
		int domainId = 1;
		DecisionProbability decisionProbability = new DecisionProbability();
		double d = decisionProbability.getProbabilityByTransition(start, end, domainId);
		System.out.println(d);
	}


}
