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
import context.utility.measurement.ObjectSizeCalculator;

public class BoomWithTripleListCASMhr {
	public static void main(String args[]) throws JSONException, IOException{
                //init domain
                DomainInitializationHR.init(6);
                
		JSONObject obj = JasonReader.readJson("config/samplefile3.json");
		JSONArray a = obj.getJSONArray("infoListHR");
		
		//handle domain of the container
		//container contains all state machine for the input; context domain is the domain

		String domainId = obj.getString("domainId");
                domainId = "6"; // hr_023
		//String domainName = obj.getString("domainName");
		//String domainURI = obj.getString("domainURI");
		
		//System.out.println(domainId);
		ObjectMapper mapper = new ObjectMapper();
		//JSON from file to Object
		ContextDomain cd = mapper.readValue(new File("dataFiles2/casmfile_domain_"+domainId+".json"), ContextDomain.class);

		Instant start = Instant.now();

		cd=AssembleCSMs.assembleCASMhrwithCondition(a, cd);
		//cd=AssembleCSMs.assembleCASMhr(a, cd);
		//cd=AssembleCSMs.assembleCASMCSSM(a, cd);
                
                // test the memory size
                // ObjectSizeCalculator osi = new ObjectSizeCalculator();
                long size = ObjectSizeCalculator.getObjectSize(cd);
                System.out.println("Size of the object: " + size + " bytes");

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
		try (FileWriter fileWriter = new FileWriter("datafiles2/casmfile_domain_"+cd.getId()+".json", false)) {//cd.getDomainId()
			fileWriter.write(json);
			System.out.println("Successfully Copied JSON Object to File...");
		}
	}
}
