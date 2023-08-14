package context.actuator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import context.core.ContextAttribute;
import context.core.ContextAttributeState;
import context.core.ContextAttributeStateMachine;
import context.core.ContextCategory;
import context.core.ContextDomain;
import context.core.ContextObject;

public class BoomWithTripleList_backup {
	public static void main(String args[]) throws JSONException, IOException{
		
		JSONObject obj = JasonReader.readJson("config/samplefile2.json");
		JSONArray a = obj.getJSONArray("infoList");
		
		//handle domain of the container
		//container contains all state machine for the input; context domain is the domain

		String domainId = obj.getString("domainId");
		//String domainName = obj.getString("domainName");
		//String domainURI = obj.getString("domainURI");
		

		ObjectMapper mapper = new ObjectMapper();
		//JSON from file to Object
		ContextDomain cd = mapper.readValue(new File("dataFiles2/casmfile_domain_"+domainId+".json"), ContextDomain.class);
		
		//ContextCategory cc = new ContextCategory(domainName,domainURI);
		
		//handle contextObject, ContextAttributeStateMachine List and ContextAttribute List
		ContextAttributeStateMachine casm;
		ContextCategory cc = new ContextCategory();
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
			
			//here to handle context category
			//1: check the existence of the context category, if not, give an error in terminal
			//2: if exist, then modify that category, add or update a context object of that category
			// subjectType/objectType/PredicateType are mapped to CategoryName , subject in triple is mapped to an object
			Iterator itcc = cd.getCcl().iterator();
			boolean bcc = true; //suppose its new category at first, if false later, then okay, otherwise, break and give error
			ContextCategory cotmpcc = new ContextCategory();
			String subjectTypeCategoryName = o.getJSONObject("subject").getString("subjectType");
			Iterator it1;
			while(itcc.hasNext()){
				cotmpcc = (ContextCategory)itcc.next();
				if(subjectTypeCategoryName.equals(cotmpcc.getName())){
					//then find the object in this category and update it
					cc = cotmpcc;
					bcc = false;
				}
			}
			
			if(bcc==true){
				System.out.println("the category of '"+subjectTypeCategoryName+"' does not exist, please double check and configure to add that context categroy first");
				break;
			}
					
			//System.out.println(o.getJSONObject("subject").getString("subjectName"));
			//TODO: add check to the json file that the two attributes can not be null
			co = new ContextObject(o.getJSONObject("subject").getString("subjectName"), 
                                o.getJSONObject("subject").getString("subjectType"),
					o.getJSONObject("subject").getString("subjectURI"));
			int decision = "TAKE".equals(o.getJSONObject("decision").getString("decisionName"))?1:0;
			
			it1 = cc.getCol().iterator();
			boolean b = false; // new object
			ContextObject cotmp = new ContextObject();
			while(it1.hasNext()){
				cotmp = (ContextObject)it1.next();
				if(cotmp.getUri().equals(co.getUri())){
					b=true; // already has the object
				}
			}
			
			if(!b){//this situation is that the context object is a new one.
				cc.addContextObject(co);
				
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
						o2.getJSONObject("object").getString("objectURI"));
				
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
				Iterator it3 = cc.getCol().iterator();
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
								o2.getJSONObject("object").getString("objectURI"));
						
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
							//System.out.println("row:" + casm.getCurrentState());
							//System.out.println("col:" + col);
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
		
		prettyPrint(cd);
		
		Instant end = Instant.now();

		Duration timeElapsed = Duration.between(start, end);
		System.out.println("Time taken: "+ timeElapsed.toMillis() +" milliseconds");
		
	}
	
	public static void prettyPrint(ContextDomain cd) throws IOException{
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(cd);
		System.out.print(json);
		new File("dataFiles").mkdir();
		try (FileWriter fileWriter = new FileWriter("datafiles/casmfile_domain_"+5+".json")) {//cd.getDomainId()
			fileWriter.write(json);
			System.out.println("Successfully Copied JSON Object to File...");
		}
	}
}
