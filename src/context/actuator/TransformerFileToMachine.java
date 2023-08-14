package context.actuator;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import context.convertion.UpperLevelInfo;
import context.core.ContextAttributeStateMachine;
import context.core.ContextCategory;

public final class TransformerFileToMachine {
	public static void transform(){
		
	}
	

	public static ContextAttributeStateMachine load(String name){
		return new ContextAttributeStateMachine();
	}
	
	public static void main(String args[]) throws JsonParseException, JsonMappingException, IOException{
		ObjectMapper mapper = new ObjectMapper();
		//JSON from file to Object
		UpperLevelInfo upperLevelInfo = mapper.readValue(new File("config/samplefile2.json"), UpperLevelInfo.class);
		
		ObjectMapper om = new ObjectMapper();
		om.enable(SerializationFeature.INDENT_OUTPUT); //pretty print
		String s = om.writeValueAsString(upperLevelInfo);
		System.out.println(s);
		
		

	}
	
}
