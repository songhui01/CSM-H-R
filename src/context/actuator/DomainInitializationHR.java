package context.actuator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import context.core.ContextDomain;

public class DomainInitializationHR {
	public static void main(String args[]) throws JSONException, IOException{
		ObjectMapper mapper = new ObjectMapper();
		//JSON from file to Object
		ContextDomain cd = mapper.readValue(new File("config/domainInitialization_hr.json"), ContextDomain.class);
		
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(cd);
		File file = new File("dataFiles2");
		if(!file.exists()){
			file.mkdirs();
		}
		try (FileWriter fileWriter = new FileWriter("dataFiles2/casmfile_domain_"+"6"+".json", false)) {
			fileWriter.write(json);
			System.out.println("Successfully Copied JSON Object to File...");
		}
	}
        
        public static void init() throws JSONException, IOException{
                init(6);
        }

        public static void init(int domain_id) throws JSONException, IOException{
                ObjectMapper mapper = new ObjectMapper();
		//JSON from file to Object
		ContextDomain cd = mapper.readValue(new File("config/domainInitialization_hr.json"), ContextDomain.class);
		
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(cd);
		File file = new File("dataFiles2");
		if(!file.exists()){
			file.mkdirs();
		}
		try (FileWriter fileWriter = new FileWriter("dataFiles2/casmfile_domain_"+domain_id+".json", false)) {
			fileWriter.write(json);
			System.out.println("Successfully Copied JSON Object to File...");
		}
        }


}
