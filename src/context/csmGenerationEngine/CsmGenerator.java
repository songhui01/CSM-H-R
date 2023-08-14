package context.csmGenerationEngine;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import context.convertion.UpperLevelInfo;
import context.core.ContextAttributeStateMachine;
import context.core.ContextCategory;
import context.core.Triple;

/**
 * This class is for building context attribute state machine
 * @author syue2
 *
 */
public class CsmGenerator {
	ContextAttributeStateMachine casm = new ContextAttributeStateMachine();
	ArrayList<Triple> al;
	
	CsmGenerator(){
		
	}
	
	CsmGenerator(ArrayList al){
		this.al=al;
	}
	
	public ContextAttributeStateMachine getCSM(){
		
		return casm;
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
