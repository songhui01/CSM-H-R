package context.actuator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import context.core.ContextDomain;

public class BoomWithTripleListCSSM {
	public static void main(String args[]) throws JSONException, IOException{
		//init domain
                DomainInitialization.init(4);
                RelationshipRegistration.register();
                
		JSONObject obj = JasonReader.readJson("config/samplefile2.json");
		JSONArray a = obj.getJSONArray("infoList");
		
		//handle domain of the container
		//container contains all state machine for the input; context domain is the domain

		String domainId = obj.getString("domainId");
		//String domainName = obj.getString("domainName");
		//String domainURI = obj.getString("domainURI");
		

		ObjectMapper mapper = new ObjectMapper();
		//JSON from file to Object
		ContextDomain cd = mapper.readValue(new File("dataFiles/casmfile_domain_"+domainId+".json"), ContextDomain.class);

		Instant start = Instant.now();

		//cd=AssembleCSMs.assembleCASM(a, cd);
		cd=AssembleCSMs.assembleCASMCSSM(a, cd);

		Instant end = Instant.now();
		
		//During assembling casm the registration of a lot of things has been finished, then the situation state machine can be started
		//cd=EnsembleCSMs.ensembleCSSM(a, cd);
		
		prettyPrint(cd);
		

		Duration timeElapsed = Duration.between(start, end);
		System.out.println("Time taken: "+ timeElapsed.toMillis() +" milliseconds");
		
	}
	
	public static void prettyPrint(ContextDomain cd) throws IOException{
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(cd);
		//System.out.print(json);
		new File("dataFiles").mkdir();
		try (FileWriter fileWriter = new FileWriter("datafiles/casmfile_domain_"+4+".json")) {//cd.getDomainId()
			fileWriter.write(json);
			System.out.println("In CSSM, Successfully Copied JSON Object to File...");
		}
	}
}
