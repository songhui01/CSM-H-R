package context.csmGenerationEngine;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import context.actuator.JasonReader;
import context.core.ContextAttribute;
import context.core.ContextAttributeState;
import context.core.ContextAttributeStateMachine;
import context.core.ContextCategory;
import context.core.ContextDomain;
import context.core.ContextObject;
import context.matrix.Matrixes;

public class CSMUpdater {
	private ContextDomain cd = new ContextDomain();
	
	public CSMUpdater(){
		
	}
	
	public void updateCD(String filePath) throws JSONException, IOException{

		JSONObject obj = JasonReader.readJson(filePath);
		JSONArray a = obj.getJSONArray("infoList");
		
		//handle domain of the container
		//container contains all state machine for the input; context domain is the domain

		String domainId = obj.getString("domainId");
		String domainName = obj.getString("domainName");
		String domainURI = obj.getString("domainURI");
		
		ObjectMapper mapper = new ObjectMapper();
		//JSON from file to Object
		ContextCategory cd = mapper.readValue(new File("dataFiles/casmfile_domain_"+domainId+".json"), ContextCategory.class);
		
		//handle contextObject, ContextAttributeStateMachine List and ContextAttribute List
		ContextAttributeStateMachine casm;
		ContextObject co;
		ContextAttribute ca;
		ContextAttributeState cas;
		Matrixes matrixes;
		for (int i = 0; i < a.length(); i++) {
			//handle context object
			JSONObject o = a.getJSONObject(i);
			//System.out.println(o.getJSONObject("subject").getString("subjectName"));
			//TODO: add check to the json file that the two attributes can not be null
			co = new ContextObject(o.getJSONObject("subject").getString("subjectName"), 
                                o.getJSONObject("subject").getString("subjectType"),
					o.getJSONObject("subject").getString("subjectURI"));
			
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
				
				int row = 0;//used to note the incoming state and for the parameter of matrix
				
				//handle hmstates (to store the map of the name and the number of states)
				//handle matrixes (to store the value of the casm)
				if(casm.getHmstates().containsKey(cas.getName())){ //then edit matrix
					row = casm.getHmstates().get(cas.getName());
					casm.getMatrixes().updateHmStates(casm.getCurrentState(), row);
					casm.setCurrentState(row);
				} else{ //then add a state
					casm.addtoHashMapStates(casm.getHmstates().size(), cas.getName());
					casm.getMatrixes().addState(casm.getHmstates().size());
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
						
						int row = 0;//used to note the incoming state and for the parameter of matrix
						
						//handle hmstates (to store the map of the name and the number of states)
						//handle matrixes (to store the value of the casm)
						if(casm.getHmstates().containsKey(cas.getName())){ //then edit matrix
							row = casm.getHmstates().get(cas.getName());
							casm.getMatrixes().updateHmStates(casm.getCurrentState(), row);
							//TODO: important: from current state to a new state, actions can be triggered here
							// and the actions can be stored in a decision form in decision matrix
							casm.setCurrentState(row);
						} else{ //then add a state
							casm.addtoHashMapStates(casm.getHmstates().size(), cas.getName());
							casm.getMatrixes().addState(casm.getHmstates().size());
						}
						//cotmp3.addAttributeStateMachine(casm);
					}
				}
			}
			
			//handle context attribute list
			
		}
		//this.cd=cd;
		
	}
	
	public void writeToDisk() throws IOException{
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(cd);
		//System.out.print(json);
		//new File("dataFiles").mkdir();
		File file = new File("dataFiles1");
		if(!file.exists()){
			file.mkdirs();
		}
		try (FileWriter fileWriter = new FileWriter("dataFiles1/casmfile_domain_"+cd.getId()+".json")) {
			fileWriter.write(json);
			//System.out.println("Successfully Copied JSON Object to File...");
		}
	}

	/**
	 * @return the cd
	 */
	public ContextDomain getCd() {
		return cd;
	}
	

}
