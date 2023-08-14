package context.actuator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import context.core.ContextDomain;

public class DomainInitialization {
	public static void main(String args[]) throws JSONException, IOException{
		ObjectMapper mapper = new ObjectMapper();
		//JSON from file to Object
		ContextDomain cd = mapper.readValue(new File("config/domainInitialization.json"), ContextDomain.class);
		
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(cd);
		File file = new File("dataFiles2");
		if(!file.exists()){
			file.mkdirs();
		}
		try (FileWriter fileWriter = new FileWriter("dataFiles/casmfile_domain_"+5+".json")) {
			fileWriter.write(json);
			System.out.println("Successfully Copied JSON Object to File...");
		}
	}


}
