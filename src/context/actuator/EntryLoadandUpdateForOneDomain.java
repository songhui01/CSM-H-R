package context.actuator;

import java.io.File;
import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import context.core.ContextCategory;
import context.csmGenerationEngine.CSMUpdater;

public class EntryLoadandUpdateForOneDomain {
	
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
		
		CSMUpdater csmupdater = new CSMUpdater();
		csmupdater.updateCD("config/fromWearableDeviceSystemToCSMCloudServer.json");
		csmupdater.writeToDisk();
	}

}
