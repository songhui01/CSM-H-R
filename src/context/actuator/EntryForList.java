package context.actuator;

import java.io.File;
import java.io.FileWriter;

/*
 * this project is for evaluation of CSM middleware and arichitecture. 
 * The input of the middleware is ontology based context data, the output is state-based context data(a CSM).
 * The middleware is for maintaining the CSMs for systems.
 * The architecture is to illustrate a scenario where the middleware can be adapted. So that the middleware
 * can support for context sharing and interoperability.
 * The key part is to show how to reason using states. How to make prediction more easily.
 */

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.json.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import context.core.*;
import context.matrix.Matrixes;

public class EntryForList {
	public static void main(String args[]) throws JSONException, IOException{
		JSONObject obj = JasonReader.readJson("config/samplefile2.json");
		JSONArray a = obj.getJSONArray("infoList");
		
		//handle domain of the container
		//container contains all state machine for the input; context domain is the domain

		String domainId = obj.getString("domainId");
		String domainName = obj.getString("domainName");
		String domainURI = obj.getString("domainURI");
		
		ContextCategory cd = new ContextCategory(domainName,domainURI);//domainId
		
		//handle contextObject, ContextAttributeStateMachine List and ContextAttribute List
		ContextAttributeStateMachine casm;
		ContextObject co;
		ContextAttribute ca;
		ContextAttributeState cas;
		Instant start = Instant.now();
		for (int i = 0; i < a.length(); i++) {
			//handle context object
			JSONObject o = a.getJSONObject(i);
			
			//if the person leaves a place, then we do not consider this as a new object and a new place state, because arrive is the first
			if("leave".equals(o.getJSONObject("predicate").getString("predicateName"))){
				continue;
			}
			
			//System.out.println(o.getJSONObject("subject").getString("subjectName"));
			//TODO: add check to the json file that the two attributes can not be null
			co = new ContextObject(o.getJSONObject("subject").getString("subjectName"), 
                                o.getJSONObject("subject").getString("subjectType"),
					o.getJSONObject("subject").getString("subjectURI"));
			int decision = "TAKE".equals(o.getJSONObject("decision").getString("decisionName"))?1:0;
			
			Iterator it1 = cd.getCol().iterator();
			boolean b = false; // new object
			ContextObject cotmp = new ContextObject();
			while(it1.hasNext()){
				cotmp = (ContextObject)it1.next();
				if(cotmp.getUri().equals(co.getUri())){
					b=true; // already has the object
				}
			}
			
			if(!b){//this situation is that the context object is a new one.
				cd.addContextObject(co);
				
				//then the context attribute will be very new and its the type in the object
				//???so context object is a real object but context attribute is a concept
				//???how to draw in uml class diagram need to be considered
				ca = new ContextAttribute(o.getJSONObject("object").getString("objectType"));
				co.addContextAttribute(ca);//append the attribute to the list cal in the co.
				
				//handle the context attribute state
				JSONObject o2 = a.getJSONObject(i);
				//System.out.println(o.getJSONObject("subject").getString("subjectName"));
				//TODO: add check to the json file that the two attributes can not be null
				cas = new ContextAttributeState(o2.getJSONObject("object").getString("objectName"), 
						o2.getJSONObject("object").getString("objectURI"),
                                                                o2.getJSONObject("state").getString("value_type"));
				
				Iterator it2 = ca.getContextAttributeStatesList()!=null?
						ca.getContextAttributeStatesList().iterator():null;
				boolean b2 = false; // new object state
				ContextAttributeState castmp = new ContextAttributeState();
				while(it2!=null&&it2.hasNext()){
					castmp = (ContextAttributeState)it2.next();
					if(castmp.getUri().equals(cas.getUri())){
						b2=true; // already has the object state
					}
				}
				
				if(!b2){
					ca.addContextAttributeStateToList(cas);
				}
				//the context attribute state machine will be very new too
				casm = new ContextAttributeStateMachine();
				//casm.setCa(ca);
				
				int col = 0;//used to note the incoming state and for the parameter of matrix
				
				//handle hmstates (to store the map of the name and the number of states)
				//handle matrixes (to store the value of the casm)
				if(casm.getHmstates().containsKey(cas.getName())){ //then edit matrix
					col = casm.getHmstates().get(cas.getName());
					casm.getMatrixes().updateHmStates(casm.getCurrentState(), col);
					casm.getMatrixes().updateDecisionMatrixStates(casm.getCurrentState(), col, decision); //row, column, decision
					casm.setCurrentState(col);
				} else{ //then add a state; here since the object is new, then the state should be the first one, and the size should be zero
					casm.addtoHashMapStates(0, cas.getName());
					casm.getMatrixes().addState(casm.getCurrentState()); // use current state to find the original state
					casm.getMatrixes().addStateForDeicision(casm.getCurrentState(),decision);
					casm.setCurrentState(0); //the first state of the first object
				}
				co.addAttributeStateMachine(casm);
				
			}else {//here the context object is not new. and we just need to search and update
				Iterator it3 = cd.getCol().iterator();
				boolean b3 = false; // new object
				ContextObject cotmp3 = new ContextObject();
				while(it3.hasNext()){
					cotmp3 = (ContextObject)it3.next();
					if(cotmp3.getUri().equals(co.getUri())){
						//found the object and update it
						//TODO: Did not handle context attribute here
						//ca = new ContextAttribute(o.getJSONObject("object").getString("objectType"));
						Iterator it4=cotmp3.getCal()!=null?cotmp3.getCal().iterator():null;
						ContextAttribute catmp;
						int it4int=0, tag=0;
						while(it4.hasNext()){
							catmp = (ContextAttribute)it4.next();
							if(catmp.getName().equals(o.getJSONObject("object").getString("objectType"))){
								it4int=tag;
							}
							tag++;
						}
						ca=cotmp3.getCal().get(it4int);
						
						//handle the context attribute state
						JSONObject o2 = a.getJSONObject(i);
						//System.out.println(o.getJSONObject("subject").getString("subjectName"));
						//TODO: add check to the json file that the two attributes can not be null
						cas = new ContextAttributeState(o2.getJSONObject("object").getString("objectName"), 
								o2.getJSONObject("object").getString("objectURI"),
                                                                o2.getJSONObject("state").getString("value_type"));
						
						Iterator it2 = ca.getContextAttributeStatesList()!=null?
								ca.getContextAttributeStatesList().iterator():null;
						boolean b2 = false; // new object state
						ContextAttributeState castmp = new ContextAttributeState();
						while(it2!=null&&it2.hasNext()){
							castmp = (ContextAttributeState)it2.next();
							if(castmp.getUri().equals(cas.getUri())){
								b2=true; // already has the object state
							}
						}
						
						if(!b2){
							//System.out.println("this is the place 1");
							ca.addContextAttributeStateToList(cas);
						}
						//cotmp3.addContextAttribute(ca);
						//the context attribute state machine will be very new too
						casm = cotmp3.getCasml().get(it4int);
						//casm.setCa(ca);
						
						int col = 0;//used to note the incoming state and for the parameter of matrix
						
						//handle hmstates (to store the map of the name and the number of states)
						//handle matrixes (to store the value of the casm)
						if(casm.getHmstates().containsKey(cas.getName())){ //if the state has already been recorded, then edit matrix
							col = casm.getHmstates().get(cas.getName());
							System.out.println("row:" + casm.getCurrentState());
							System.out.println("col:" + col);
							casm.getMatrixes().updateHmStates(casm.getCurrentState(), col);
							casm.getMatrixes().updateDecisionMatrixStates(casm.getCurrentState(), col, decision); //row, column, decision
							//TODO: important: from current state to a new state, actions can be triggered here
							// and the actions can be stored in a decision form in decision matrix
							casm.setCurrentState(col);
						} else{ 
							int size = casm.getHmstates().size();
							casm.addtoHashMapStates(size, cas.getName());
							casm.getMatrixes().addState(casm.getCurrentState()); // use current state to find the original state
							casm.getMatrixes().addStateForDeicision(casm.getCurrentState(),decision);
							casm.setCurrentState(size); 
						}
						//cotmp3.addAttributeStateMachine(casm);
					}
				}
			}
			
			//handle context attribute list
			
		}
		
		//Testing
		Iterator it2 = cd.getCol().iterator();
		ContextObject cotmp2 = new ContextObject();
		while(it2.hasNext()){
			cotmp2 = (ContextObject)it2.next();
			System.out.println(cotmp2.getName());
		}
		//prettyPrint(cd);
		
		Instant end = Instant.now();

		Duration timeElapsed = Duration.between(start, end);
		System.out.println("Time taken: "+ timeElapsed.toMillis() +" milliseconds");
		
	}
	
	public static ArrayList assembleCASML(){
		
		return new ArrayList();
	}
	
	public static void prettyPrint(ContextDomain cd) throws IOException{
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(cd);
		System.out.print(json);
		new File("dataFiles").mkdir();
		try (FileWriter fileWriter = new FileWriter("datafiles/casmfile_domain_"+cd.getId()+".json")) {//cd.getDomainId()
			fileWriter.write(json);
			System.out.println("Successfully Copied JSON Object to File...");
		}
	}
	
	//suppose the elevator need to know people's location csm, to calculate the possibility.
	//however, the p can be counted by using data only from the elevator side. 
	//The elevator can collect some information and make prediction using binary tree, svm, 
	//random forest, naive bayes, markov etc. 
	//Could this be improved by applying the csms.
}
