package context.actuator;

import java.time.Instant;
import java.util.Iterator;

//https://central.sonatype.com/artifact/org.json/json/20180813?smo=true
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import context.core.ContextAttribute;
import context.core.ContextAttributeSnapshot;
import context.core.ContextAttributeState;
import context.core.ContextAttributeStateMachine;
import context.core.ContextCategory;
import context.core.ContextDomain;
import context.core.ContextObject;
import context.core.ContextSituationState;
import context.core.ContextSituationStateMachine;
import context.utility.ListHelper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author songhuiyue
 * 
 * This class contains methods for:
 * assembleCASM
 * assembleCSSM
 * assembleCASMCSSM
 * 
 * assembleCASMhr
 * assembleCSSMhr
 * assembleCASMCSSMhr
 * 
 */

public class AssembleCSMs {
	
	public static ContextDomain assembleCASM(JSONArray a, ContextDomain cd) throws JSONException{
		
		//handle contextObject, ContextAttributeStateMachine List and ContextAttribute List
		ContextAttributeStateMachine casm;
		ContextCategory cc = new ContextCategory();
		ContextObject co;
		ContextAttribute ca;
		ContextAttributeState cas;
		
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
				cd.getCol_domain().add(co.getUri());//this is to add new object in context domain
				cd.getCr().getrMatrixes().addState(0);
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
						o2.getJSONObject("object").getString("objectURI"),
                                                                o2.getJSONObject("object").getString("objectType"));
				
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
				if(casm.getHmstates().containsKey(cas.getUri())){ //then edit matrix
					col = casm.getHmstates().get(cas.getUri());
					casm.getMatrixes().updateHmStates(casm.getCurrentState(), col);
					casm.getMatrixes().updateDecisionMatrixStates(casm.getCurrentState(), col, decision); //row, column, decision
					casm.setCurrentState(col);
				} else{ //then add a state; here since the object is new, then the state should be the first one, and the size should be zero
					casm.addtoHashMapStates(0, cas.getUri());
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
								o2.getJSONObject("object").getString("objectURI"),
                                                                o2.getJSONObject("object").getString("objectType"));
						
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
						if(casm.getHmstates().containsKey(cas.getUri())){ //if the state has already been recorded, then edit matrix
							col = casm.getHmstates().get(cas.getUri());
							//System.out.println("row:" + casm.getCurrentState());
							//System.out.println("col:" + col);
							casm.getMatrixes().updateHmStates(casm.getCurrentState(), col);
							casm.getMatrixes().updateDecisionMatrixStates(casm.getCurrentState(), col, decision); //row, column, decision
							//TODO: important: from current state to a new state, actions can be triggered here
							// and the actions can be stored in a decision form in decision matrix
							casm.setCurrentState(col);
						} else{ 
							int size = casm.getHmstates().size();
							casm.addtoHashMapStates(size, cas.getUri());
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
		
		return cd;
	}
        
        public static String append(String compressed, String s) {
            return compressed.concat(".").concat(s);
        }
        
        public static void printListToFile(List<String> lines, String filePath) {
            try {
                // Create a FileWriter with the file path
                FileWriter fileWriter = new FileWriter(filePath);

                // Write each line to the file
                for (String line : lines) {
                    fileWriter.write(line + "\n");
                }

                // Close the FileWriter
                fileWriter.close();

                System.out.println("Lines written to file successfully.");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

	public static ContextDomain assembleCASMhr(JSONArray a, ContextDomain cd) throws JSONException{
		
		//handle contextObject, ContextAttributeStateMachine List and ContextAttribute List
		ContextAttributeStateMachine casm;
		ContextCategory cc = new ContextCategory();
		ContextObject co;
		ContextAttribute ca;
		ContextAttributeState cas;
                String compressed = "";
                List<String> compressedList = new ArrayList<String>();
		
		for (int i = 0; i < a.length(); i++) {
			//handle context object
			JSONObject o = a.getJSONObject(i);
			
			//here to handle context category
			//1: check the existence of the context category, if not, give an error in terminal
			//2: if exist, then modify that category, add or update a context object of that category
			// subjectType/objectType/PredicateType are mapped to CategoryName , subject in triple is mapped to an object
			Iterator itcc = cd.getCcl().iterator();
			boolean bcc = true; //suppose its new category at first, if false later, then okay, otherwise, break and give error
			ContextCategory cotmpcc = new ContextCategory();
			String subjectTypeCategoryName = o.getJSONObject("object").getString("type");
			Iterator it1;
                        int category_index = 0;
			while(itcc.hasNext()){
				cotmpcc = (ContextCategory)itcc.next();
				if(subjectTypeCategoryName.equals(cotmpcc.getName())){
					//then find the object in this category and update it
					cc = cotmpcc;
					bcc = false;
                                        compressed = append(compressed, String.valueOf(category_index));
                                        break;
				}
                                category_index++;
			}
			
			if(bcc==true){
				System.out.println("the category of '"+subjectTypeCategoryName+"' does not exist, please double check and configure to add that context categroy first");
				break;
			}
					
			//System.out.println(o.getJSONObject("subject").getString("subjectName"));
			//TODO: add check to the json file that the two attributes can not be null
			co = new ContextObject(o.getJSONObject("object").getString("name"), 
                                        o.getJSONObject("object").getString("type"), 
					o.getJSONObject("object").getString("uri"));
			int decision = "decision".equals(o.getJSONObject("state").getString("type"))?1:0;
			
			it1 = cc.getCol().iterator();
			boolean b = false; // new object
			ContextObject cotmp = new ContextObject();
			while(it1.hasNext()){
				cotmp = (ContextObject)it1.next();
				if(cotmp.getUri().equals(co.getUri())){
					b=true; // already has the object
                                        break;
				}
			}
			
			if(!b){//this situation is that the context object is a new one.
				cd.getCol_domain().add(co.getUri());//this is to add new object in context domain
				cd.getCr().getrMatrixes().addState(0);
				cc.addContextObject(co);
                                
                                // object index is 0
				compressed = append(compressed, String.valueOf(0));
                                
				//then the context attribute will be very new and its the type in the object
				//???so context object is a real object but context attribute is a concept
				//???how to draw in uml class diagram need to be considered
				ca = new ContextAttribute(o.getJSONObject("attribute").getString("name"));
                                ca.setUri(o.getJSONObject("attribute").getString("uri"));
                                ca.setType(o.getJSONObject("attribute").getString("type"));
				co.addContextAttribute(ca);//append the attribute to the list cal in the co.
				
				//handle the context attribute state
				JSONObject o2 = a.getJSONObject(i);
				//System.out.println(o.getJSONObject("subject").getString("subjectName"));
				//TODO: add check to the json file that the two attributes can not be null
				cas = new ContextAttributeState(o2.getJSONObject("state").getString("name"), 
						o2.getJSONObject("state").getString("uri"),
                                                                o2.getJSONObject("state").getString("type"));
				
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
				if(casm.getHmstates().containsKey(cas.getUri())){ //then edit matrix
					col = casm.getHmstates().get(cas.getUri());
					casm.getMatrixes().updateHmStates(casm.getCurrentState(), col);
					casm.getMatrixes().updateDecisionMatrixStates(casm.getCurrentState(), col, decision); //row, column, decision
					casm.setCurrentState(col);
				} else{ //then add a state; here since the object is new, then the state should be the first one, and the size should be zero
					casm.addtoHashMapStates(0, cas.getUri());
					casm.getMatrixes().addState(casm.getCurrentState()); // use current state to find the original state
					casm.getMatrixes().addStateForDeicision(casm.getCurrentState(),decision); // if currentState is -1, then it is initilizing
					casm.setCurrentState(0); //the first state of the first object
				}
				co.addAttributeStateMachine(casm);
				
			}else {//here the context object is not new. and we just need to search and update
				Iterator it3 = cc.getCol().iterator();
				boolean b3 = false; // new object
				ContextObject cotmp3 = new ContextObject();
                                int object_index = 0;
				while(it3.hasNext()){
					cotmp3 = (ContextObject)it3.next();
					if(cotmp3.getUri().equals(co.getUri())){
						//found the object and update it
						//TODO: Did not handle context attribute here
						//ca = new ContextAttribute(o.getJSONObject("object").getString("objectType"));
                                                
                                                compressed = append(compressed, String.valueOf(object_index));
                                                
						Iterator it4=cotmp3.getCal()!=null?cotmp3.getCal().iterator():null;
						ContextAttribute catmp;
						int it4int=0, tag=0;
                                                boolean found_attribute = false;
						while(it4.hasNext()){
							catmp = (ContextAttribute)it4.next();
							if(catmp.getName().equals(o.getJSONObject("attribute").getString("name"))){
								it4int=tag;
                                                                found_attribute = true;
							}
							tag++;
						}
                                                
                                                if(found_attribute == true) {
                                                    // if the context attribute is found to be at an index
                                                    ca=cotmp3.getCal().get(it4int);
                                                } else {
                                                    //if the context attribute is not found anywhere, we will add a context attribute to the cal
                                                    ca = new ContextAttribute(o.getJSONObject("attribute").getString("name"));
                                                    ca.setUri(o.getJSONObject("attribute").getString("uri"));
                                                    ca.setType(o.getJSONObject("attribute").getString("type"));
                                                    cotmp3.getCal().add(ca);
                                                    
                                                    casm = new ContextAttributeStateMachine();
                                                    cotmp3.addAttributeStateMachine(casm);
                                                    it4int = cotmp3.getCal().size() - 1;
                                                }
                                                
                                                // attribute index
                                                compressed = append(compressed, String.valueOf(it4int));

						//handle the context attribute state
						JSONObject o2 = a.getJSONObject(i);
						//System.out.println(o.getJSONObject("subject").getString("subjectName"));
						//TODO: add check to the json file that the two attributes can not be null
                                                //System.out.println(o2.getJSONObject("state").getString("name"));
						cas = new ContextAttributeState(o2.getJSONObject("state").getString("name"), 
								o2.getJSONObject("state").getString("uri"), 
                                                                o2.getJSONObject("state").getString("type"));
						
						Iterator it2 = ca.getContextAttributeStatesList()!=null?
								ca.getContextAttributeStatesList().iterator():null;
						boolean b2 = false; // new object state
						ContextAttributeState castmp = new ContextAttributeState();
                                                
                                                int state_index = 0;
						while(it2!=null&&it2.hasNext()){
							castmp = (ContextAttributeState)it2.next();
							if(castmp.getUri().equals(cas.getUri())){
								b2=true; // already has the object state
                                                                break;
							}
                                                        state_index++;
						}
						
						if(!b2){
							//System.out.println("this is the place 1");
							ca.addContextAttributeStateToList(cas);
                                                        state_index = ca.getContextAttributeStatesList().size() - 1;
						}
                                                
                                                // state index
                                                compressed = append(compressed, String.valueOf(state_index));
                                                
						//cotmp3.addContextAttribute(ca);
						//the context attribute state machine will be very new too
						casm = cotmp3.getCasml().get(it4int);
						//casm.setCa(ca);
						
						int col = 0;//used to note the incoming state and for the parameter of matrix
						
						//handle hmstates (to store the map of the name and the number of states)
						//handle matrixes (to store the value of the casm)
						if(casm.getHmstates().containsKey(cas.getUri())){ //if the state has already been recorded, then edit matrix
							col = casm.getHmstates().get(cas.getUri());
							//System.out.println("row:" + casm.getCurrentState());
							//System.out.println("col:" + col);
							casm.getMatrixes().updateHmStates(casm.getCurrentState(), col);
							casm.getMatrixes().updateDecisionMatrixStates(casm.getCurrentState(), col, decision); //row, column, decision
							//TODO: important: from current state to a new state, actions can be triggered here
							// and the actions can be stored in a decision form in decision matrix
							casm.setCurrentState(col);
						} else{ 
							int size = casm.getHmstates().size();
							casm.addtoHashMapStates(size, cas.getUri());
							casm.getMatrixes().addState(casm.getCurrentState()); // use current state to find the original state
							casm.getMatrixes().addStateForDeicision(casm.getCurrentState(),decision);
							casm.setCurrentState(size); 
						}
						//cotmp3.addAttributeStateMachine(casm);
                                                
					}
                                        object_index++;
				}
			}
			
			//handle context attribute list
                        
                        //handle compression
                        compressedList.add(compressed);
                        compressed = "";
		}
                
		printListToFile(compressedList, "dataFiles/compressed.txt");
                
		return cd;
	}
        
        
	public static ContextDomain assembleCASMhrwithCondition(JSONArray a, ContextDomain cd) throws JSONException{
		
		//handle contextObject, ContextAttributeStateMachine List and ContextAttribute List
		ContextAttributeStateMachine casm;
		ContextCategory cc = new ContextCategory();
		ContextObject co;
		ContextAttribute ca;
		ContextAttributeState cas;
		ContextAttribute ca_condition;
		ContextAttributeState cas_condition;
                String compressed = "";
                List<String> compressedList = new ArrayList<String>();
		
		for (int i = 0; i < a.length(); i++) {
			//handle context object
			JSONObject o = a.getJSONObject(i);
			
			//here to handle context category
			//1: check the existence of the context category, if not, give an error in terminal
			//2: if exist, then modify that category, add or update a context object of that category
			// subjectType/objectType/PredicateType are mapped to CategoryName , subject in triple is mapped to an object
			Iterator itcc = cd.getCcl().iterator();
			boolean bcc = true; //suppose its new category at first, if false later, then okay, otherwise, break and give error
			ContextCategory cotmpcc = new ContextCategory();
			String subjectTypeCategoryName = o.getJSONObject("object").getString("type");
			Iterator it1;
                        int category_index = 0;
			while(itcc.hasNext()){
				cotmpcc = (ContextCategory)itcc.next();
				if(subjectTypeCategoryName.equals(cotmpcc.getName())){
					//then find the object in this category and update it
					cc = cotmpcc;
					bcc = false;
                                        compressed = append(compressed, String.valueOf(category_index));
                                        break;
				}
                                category_index++;
			}
			
			if(bcc==true){
				System.out.println("the category of '"+subjectTypeCategoryName+"' does not exist, please double check and configure to add that context categroy first");
				break;
			}
					
			//System.out.println(o.getJSONObject("subject").getString("subjectName"));
			//TODO: add check to the json file that the two attributes can not be null
			co = new ContextObject(o.getJSONObject("object").getString("name"), 
                                        o.getJSONObject("object").getString("type"), 
					o.getJSONObject("object").getString("uri"));
			int decision = "decision".equals(o.getJSONObject("state").getString("type"))?1:0;
			
			it1 = cc.getCol().iterator();
			boolean b = false; // new object
			ContextObject cotmp = new ContextObject();
			while(it1.hasNext()){
				cotmp = (ContextObject)it1.next();
				if(cotmp.getUri().equals(co.getUri())){
					b=true; // already has the object
                                        break;
				}
			}
			
			if(!b){//this situation is that the context object is a new one.
				cd.getCol_domain().add(co.getUri());//this is to add new object in context domain
				cd.getCr().getrMatrixes().addState(0);
				cc.addContextObject(co);
                                
                                // object index is not 0, it is the next index
				compressed = append(compressed, String.valueOf(cc.getCol().size() - 1));
                                // attribute index is 0
                                compressed = append(compressed, String.valueOf(0));
                                // new state index is 0
                                compressed = append(compressed, String.valueOf(0));
                                compressed = compressed + ":::"; 
                                // condition attribute index is 1
                                compressed = append(compressed, String.valueOf(1));
                                // first condition attribute state index is 0
                                compressed = append(compressed, String.valueOf(0));
                                
				//then the context attribute will be very new and its the type in the object
				//???so context object is a real object but context attribute is a concept
				//???how to draw in uml class diagram need to be considered
				ca = new ContextAttribute(o.getJSONObject("attribute").getString("name"));
                                ca.setUri(o.getJSONObject("attribute").getString("uri"));
                                ca.setType(o.getJSONObject("attribute").getString("type"));
				co.addContextAttribute(ca);//append the attribute to the list cal in the co.
                                
				//handle the context attribute state
				JSONObject o2 = a.getJSONObject(i);
				//System.out.println(o.getJSONObject("subject").getString("subjectName"));
				//TODO: add check to the json file that the two attributes can not be null
				cas = new ContextAttributeState(o2.getJSONObject("state").getString("name"), 
						o2.getJSONObject("state").getString("uri"),
                                                                o2.getJSONObject("state").getString("type"));
				
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
				if(casm.getHmstates().containsKey(cas.getUri())){ //then edit matrix
					col = casm.getHmstates().get(cas.getUri());
					casm.getMatrixes().updateHmStates(casm.getCurrentState(), col);
					casm.getMatrixes().updateDecisionMatrixStates(casm.getCurrentState(), col, decision); //row, column, decision
					casm.setCurrentState(col);
				} else{ //then add a state; here since the object is new, then the state should be the first one, and the size should be zero
					casm.addtoHashMapStates(0, cas.getUri());
					casm.getMatrixes().addState(casm.getCurrentState()); // use current state to find the original state
					casm.getMatrixes().addStateForDeicision(casm.getCurrentState(),decision); // if currentState is -1, then it is initilizing
					casm.setCurrentState(0); //the first state of the first object
				}
				co.addAttributeStateMachine(casm);
                                
                                // handle conditions
                                ca_condition = new ContextAttribute(o.getJSONObject("condition").getString("name"));
                                int index = co.getCal().size() - 1;
                                ca_condition.setId(index);
                                ca_condition.setType(o.getJSONObject("condition").getString("type"));
				co.addContextAttribute(ca_condition);//append the attribute to the list cal in the co.
                                
                                // TODO: we are defaulting the conditoin type to be HOUR-granularity; use the condition value to be URI as default
                                String condition_uri = "csm/"+cd.getName()+"/"+o.getJSONObject("condition").getString("name")
                                        +"/"+o.getJSONObject("condition").getString("granularity")
                                        +"/"+o.getJSONObject("condition").getString(o.getJSONObject("condition").getString("name"));
				cas_condition = new ContextAttributeState(o.getJSONObject("condition").getString(o.getJSONObject("condition").getString("name")), 
						condition_uri,
                                                                o.getJSONObject("condition").getString("granularity"));
				
				Iterator it3 = ca_condition.getContextAttributeStatesList()!=null?
						ca_condition.getContextAttributeStatesList().iterator():null;
				boolean b3 = false; // new object state
				ContextAttributeState castmp3 = new ContextAttributeState();
				while(it3!=null&&it3.hasNext()){
					castmp3 = (ContextAttributeState)it3.next();
                                        // now the uri actually is the value of the condition
					if(castmp3.getId() == cas_condition.getId()){
						b3=true; // already has the object state
					}
				}
				
				if(!b3){
					ca_condition.addContextAttributeStateToList(cas_condition);
				}
                                //casm space keeper 
				casm = new ContextAttributeStateMachine();
				co.addAttributeStateMachine(casm);
			}else {//here the context object is not new. and we just need to search and update
				Iterator it3 = cc.getCol().iterator();
				boolean b3 = false; // new object
				ContextObject cotmp3 = new ContextObject();
                                int object_index = 0;
				while(it3.hasNext()){
					cotmp3 = (ContextObject)it3.next();
					if(cotmp3.getUri().equals(co.getUri())){
						//found the object and update it
						//TODO: Did not handle context attribute here
						//ca = new ContextAttribute(o.getJSONObject("object").getString("objectType"));
                                                
                                                compressed = append(compressed, String.valueOf(object_index));
                                                
						Iterator it4=cotmp3.getCal()!=null?cotmp3.getCal().iterator():null;
						ContextAttribute catmp;
						int it4int=0, tag=0;
                                                boolean found_attribute = false;
						while(it4.hasNext()){
							catmp = (ContextAttribute)it4.next();
							if(catmp.getName().equals(o.getJSONObject("attribute").getString("name"))){
								it4int=tag;
                                                                found_attribute = true;
							}
							tag++;
						}
                                                
                                                if(found_attribute == true) {
                                                    // if the context attribute is found to be at an index
                                                    ca=cotmp3.getCal().get(it4int);
                                                } else {
                                                    //if the context attribute is not found anywhere, we will add a context attribute to the cal
                                                    ca = new ContextAttribute(o.getJSONObject("attribute").getString("name"));
                                                    ca.setUri(o.getJSONObject("attribute").getString("uri"));
                                                    ca.setType(o.getJSONObject("attribute").getString("type"));
                                                    cotmp3.getCal().add(ca);
                                                    
                                                    casm = new ContextAttributeStateMachine();
                                                    cotmp3.addAttributeStateMachine(casm);
                                                    it4int = cotmp3.getCal().size() - 1;
                                                }
                                                
                                                // attribute index
                                                compressed = append(compressed, String.valueOf(it4int));

						//handle the context attribute state
						JSONObject o2 = a.getJSONObject(i);
						//System.out.println(o.getJSONObject("subject").getString("subjectName"));
						//TODO: add check to the json file that the two attributes can not be null
                                                //System.out.println(o2.getJSONObject("state").getString("name"));
						cas = new ContextAttributeState(o2.getJSONObject("state").getString("name"), 
								o2.getJSONObject("state").getString("uri"), 
                                                                o2.getJSONObject("state").getString("type"));
						
						Iterator it2 = ca.getContextAttributeStatesList()!=null?
								ca.getContextAttributeStatesList().iterator():null;
						boolean b2 = false; // new object state
						ContextAttributeState castmp = new ContextAttributeState();
                                                
                                                int state_index = 0;
						while(it2!=null&&it2.hasNext()){
							castmp = (ContextAttributeState)it2.next();
							if(castmp.getUri().equals(cas.getUri())){
								b2=true; // already has the object state
                                                                break;
							}
                                                        state_index++;
						}
						
						if(!b2){
							//System.out.println("this is the place 1");
							ca.addContextAttributeStateToList(cas);
                                                        state_index = ca.getContextAttributeStatesList().size() - 1;
						}
                                                
                                                // state index
                                                compressed = append(compressed, String.valueOf(state_index));
                                                
						//cotmp3.addContextAttribute(ca);
						//the context attribute state machine will be very new too
						casm = cotmp3.getCasml().get(it4int);
						//casm.setCa(ca);
						
						int col = 0;//used to note the incoming state and for the parameter of matrix
						
						//handle hmstates (to store the map of the name and the number of states)
						//handle matrixes (to store the value of the casm)
						if(casm.getHmstates().containsKey(cas.getUri())){ //if the state has already been recorded, then edit matrix
							col = casm.getHmstates().get(cas.getUri());
							//System.out.println("row:" + casm.getCurrentState());
							//System.out.println("col:" + col);
							casm.getMatrixes().updateHmStates(casm.getCurrentState(), col);
							casm.getMatrixes().updateDecisionMatrixStates(casm.getCurrentState(), col, decision); //row, column, decision
							//TODO: important: from current state to a new state, actions can be triggered here
							// and the actions can be stored in a decision form in decision matrix
							casm.setCurrentState(col);
						} else{ 
							int size = casm.getHmstates().size();
							casm.addtoHashMapStates(size, cas.getUri());
							casm.getMatrixes().addState(casm.getCurrentState()); // use current state to find the original state
							casm.getMatrixes().addStateForDeicision(casm.getCurrentState(),decision);
							casm.setCurrentState(size); 
						}
						//cotmp3.addAttributeStateMachine(casm);
                                                
                                                
                                                /**************************************************************/
                                                
                                                // here after handles the condition....
                                                
                                                compressed = compressed + ":::"; 
                                                
//                                                compressed = append(compressed, String.valueOf(category_index));
//                                                compressed = append(compressed, String.valueOf(object_index));
                                                
						Iterator it5=cotmp3.getCal()!=null?cotmp3.getCal().iterator():null;
						ContextAttribute catmp5;
						int it5int=0, tag5=0;
                                                boolean found_attribute5 = false;
						while(it5.hasNext()){
							catmp5 = (ContextAttribute)it5.next();
//                                                        System.out.println("**********");
//                                                        System.out.println("catemp5 time is: " + catmp5.getName());
//                                                        System.out.println("json time is: " + o.getJSONObject("condition").getString("name"));
//                                                        System.out.println(tag5);
							if(catmp5.getName().equals(o.getJSONObject("condition").getString("name"))){
//                                                            System.out.println("find a match");
								it5int=tag5;
                                                                found_attribute5 = true;
							}
							tag5++;
						}
                                                
                                                if(found_attribute5 == true) {
                                                    // if the context attribute is found to be at an index
                                                    ca_condition=cotmp3.getCal().get(it5int);
                                                } else {
                                                    //if the context attribute is not found anywhere, we will add a context attribute to the cal
                                                    // handle conditions
                                                    ca_condition = new ContextAttribute(o.getJSONObject("condition").getString("name"));
                                                    int index = co.getCal().size() - 1;
                                                    ca_condition.setId(index);
                                                    ca_condition.setType(o.getJSONObject("condition").getString("type"));
                                                    cotmp3.addContextAttribute(ca_condition);//append the attribute to the list cal in the co.
                                                    
                                                    ContextAttributeStateMachine casm2 = new ContextAttributeStateMachine();
                                                    cotmp3.addAttributeStateMachine(casm2);
                                                    it5int = cotmp3.getCal().size() - 1;
                                                }
                                                                              
                                                // attribute index
                                                compressed = append(compressed, String.valueOf(it5int));

						//handle the context attribute state
						//System.out.println(o.getJSONObject("subject").getString("subjectName"));
						//TODO: add check to the json file that the two attributes can not be null
                                                //System.out.println(o2.getJSONObject("state").getString("name"));
                                                // TODO: we are defaulting the conditoin type to be HOUR-granularity; use the condition value to be URI as default
                                                
                                                String condition_uri = "csm/"+cd.getName()+"/"+o.getJSONObject("condition").getString("name")
                                                        +"/"+o.getJSONObject("condition").getString("granularity")
                                                        +"/"+o.getJSONObject("condition").getString(o.getJSONObject("condition").getString("name"));
                                                cas_condition = new ContextAttributeState(o.getJSONObject("condition").getString(o.getJSONObject("condition").getString("name")), 
                                                                condition_uri,
                                                                                o.getJSONObject("condition").getString("granularity"));
				
						
						Iterator it6 = ca_condition.getContextAttributeStatesList()!=null?
								ca_condition.getContextAttributeStatesList().iterator():null;
						boolean b6 = false; // new object state
						ContextAttributeState castmp6 = new ContextAttributeState();
                                                
                                                int state_index6 = 0;
						while(it6!=null&&it6.hasNext()){
							castmp6 = (ContextAttributeState)it6.next();
							if(castmp6.getName().equals(cas_condition.getName())){
								b6=true; // already has the object state
                                                                break;
							}
                                                        state_index6++;
						}
						
						if(!b6){
							//System.out.println("this is the place 1");
							ca_condition.addContextAttributeStateToList(cas_condition);
                                                        state_index6 = ca_condition.getContextAttributeStatesList().size() - 1;
						}
                                                
                                                // state index
                                                compressed = append(compressed, String.valueOf(state_index6));
                                                
                                                
					}
                                        object_index++;
				}
			}
			
			//handle context attribute list
                        
                        //handle compression
                        compressedList.add(compressed);
                        compressed = "";
		}
                
		printListToFile(compressedList, "dataFiles/compressed.txt");
                
		return cd;
	}
	
	//this method is used to build the situation from "one time information", which is the information passed 
	//to this engine at one time; So during the period of casms, we identify the situational objects by referring to the 
	//relation registration module. In this method, we rerun the process of building CASMs, and to find the situations
	//of the identified objects.
	//This is the current method. How to add a state, or how to do the transition shift will be in future work.
	//CSSM is under a context object, not under context category or domain
	public static ContextDomain assembleCSSMhr(JSONArray a, ContextDomain cd) throws JSONException{
		
		//handle contextObject, ContextAttributeStateMachine List and ContextAttribute List
		ContextAttributeStateMachine casm;
		ContextSituationStateMachine cssm;
		ContextCategory cc = new ContextCategory();
		ContextObject co;
		ContextAttribute ca;
		ContextAttributeState cas;
		
		for (int i = 0; i < a.length(); i++) {
			//handle context object
			JSONObject o = a.getJSONObject(i);
			
			//if the person leaves a place, then we do not consider this as a new object and a new place state, because arrive is the first
			// and in our prototype of the research, we only consider to build CASM for arrive(behavior) predicate.
			// To extend the framework, we can have behavior class in the framework, and the behavior can be a lot of types
			//This is one of the limitation of the research; another one is that we do not have the object adding and transition shift
//			if("leave".equals(o.getJSONObject("predicate").getString("predicateName"))){
//				continue;
//			}
//			
			//here to handle context category
			//1: check the existence of the context category, if not, give an error in terminal
			//2: if exist, then modify that category, add or update a context object of that category
			// subjectType/objectType/PredicateType are mapped to CategoryName , subject in triple is mapped to an object
			Iterator itcc = cd.getCcl().iterator();
			boolean bcc = true; //suppose its new category at first, if false later, then okay, otherwise, break and give error
			ContextCategory cotmpcc = new ContextCategory();
			String subjectTypeCategoryName = o.getJSONObject("object").getString("type");
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
			co = new ContextObject(o.getJSONObject("object").getString("name"), 
                                o.getJSONObject("object").getString("type"), 
					o.getJSONObject("object").getString("uri"));
			int decision = "decision".equals(o.getJSONObject("state").getString("type"))?1:0;

			Iterator it1;
			it1 = cc.getCol().iterator();
			boolean b = false; // new object, not existing
			ContextObject cotmp = new ContextObject();
			while(it1.hasNext()){
				cotmp = (ContextObject)it1.next();
				if(cotmp.getUri().equals(co.getUri())){
					b=true; // already has the object
				}
			}
			
			//From here on, We need to find the context situation state machine and search and update or initialize
			//here the context object is not new. and we just need to search and update
			//This situation state machine is only for the decision for using elevator, so we can have a list of situation state machine for different purpose,
			//which could be in the future work
			
			//we need to get the related object's current state and put them into the context situation state
			
			ContextSituationState css1= new ContextSituationState();
			String objectURI = o.getJSONObject("object").getString("uri");
			String attributeURI = o.getJSONObject("attribute").getString("name"); //here we use attribute name
			String stateURI = o.getJSONObject("state").getString("uri");
			ContextAttributeSnapshot casnap = new ContextAttributeSnapshot(objectURI, attributeURI, stateURI);
			css1.addContextAttributeSnapshotToList(casnap);
			
			//find a list of related objects by using relation matrix
			Iterator itobjects = cd.getCol_domain().iterator();
			int index = 0;
			int tag2 = 0;
			int length = cd.getCol_domain().size();
			while(itobjects.hasNext()){
				ContextObject co2= (ContextObject)itobjects.next();
				if(objectURI.equals(co2.getUri())){
					index = tag2; //the index of the object found here, which is the matrix row
				}
				tag2++;
			}
			for (int ii = 0; ii<length; ii++){
				if(cd.getCr().getrMatrixes().getrClosenessMatrix().get(index).get(ii)>=80){
					//then the relation is good enough, we add the current state of this object
				}
			}
			
			Iterator it3 = cc.getCol().iterator();
			boolean b3 = false; // new object
			ContextObject cotmp3 = new ContextObject();
			while(it3.hasNext()){
				cotmp3 = (ContextObject)it3.next();
				if(cotmp3.getUri().equals(co.getUri())){
					//found the object and use it
					//we get the current attribute URI, and the current state URI, and the object URI
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
                                                                o2.getJSONObject("object").getString("objectType"));
					
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
					if(casm.getHmstates().containsKey(cas.getUri())){ //if the state has already been recorded, then edit matrix
						col = casm.getHmstates().get(cas.getUri());
						//System.out.println("row:" + casm.getCurrentState());
						//System.out.println("col:" + col);
						casm.getMatrixes().updateHmStates(casm.getCurrentState(), col);
						casm.getMatrixes().updateDecisionMatrixStates(casm.getCurrentState(), col, decision); //row, column, decision
						//TODO: important: from current state to a new state, actions can be triggered here
						// and the actions can be stored in a decision form in decision matrix
						casm.setCurrentState(col);
					} else{ 
						int size = casm.getHmstates().size();
						casm.addtoHashMapStates(size, cas.getUri());
						casm.getMatrixes().addState(casm.getCurrentState()); // use current state to find the original state
						casm.getMatrixes().addStateForDeicision(casm.getCurrentState(),decision);
						casm.setCurrentState(size); 
					}
					//cotmp3.addAttributeStateMachine(casm);
				}
			}
			//handle context attribute list
		}
		return cd;
	}
        
	
	//this method is used to build the CASM and CSSM together
	public static ContextDomain assembleCASMCSSM(JSONArray a, ContextDomain cd) throws JSONException{
		
		//handle contextObject, ContextAttributeStateMachine List and ContextAttribute List
		ContextAttributeStateMachine casm;
		ContextSituationStateMachine cssm;
		ContextCategory cc = new ContextCategory();
		ContextObject co;
		ContextAttribute ca;
		ContextAttributeState cas;
		//System.out.println("position 1");
		for (int i = 0; i < a.length(); i++) {
			//handle context object
			JSONObject o = a.getJSONObject(i);
			
			//if the person leaves a place, then we do not consider this as a new object and a new place state, because arrive is the first
			// and in our prototype of the research, we only consider to build CASM for arrive(behavior) predicate.
			// To extend the framework, we can have behavior class in the framework, and the behavior can be a lot of types
			//This is one of the limitation of the research; another one is that we do not have the object adding and transition shift
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

			Iterator it1;
			it1 = cc.getCol().iterator();
			boolean b = false; // new object, not existing
			ContextObject cotmp = new ContextObject();
			while(it1.hasNext()){
				cotmp = (ContextObject)it1.next();
				if(cotmp.getUri().equals(co.getUri())){
					b=true; // already has the object
				}
			}
			
			//From here on, We need to find the context situation state machine and search and update or initialize
			//here the context object is not new. and we just need to search and update
			//This situation state machine is only for the decision for using elevator, so we can have a list of situation state machine for different purpose,
			//which could be in the future work

			//System.out.println("position 1.5");
			//we need to get the related object's current state and put them into the context situation state
			if(!b){//this situation is that the context object is a new one.
				cd.getCol_domain().add(co.getUri());//this is to add new object in context domain
				cd.getCr().getrMatrixes().addState(0);
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
						o2.getJSONObject("object").getString("objectURI"),
                                                                o2.getJSONObject("object").getString("objectType"));
				
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
				if(casm.getHmstates().containsKey(cas.getUri())){ //then edit matrix
					col = casm.getHmstates().get(cas.getUri());
					casm.getMatrixes().updateHmStates(casm.getCurrentState(), col);
					casm.getMatrixes().updateDecisionMatrixStates(casm.getCurrentState(), col, decision); //row, column, decision
					casm.setCurrentState(col);
				} else{ //then add a state; here since the object is new, then the state should be the first one, and the size should be zero
					casm.addtoHashMapStates(0, cas.getUri());
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
						
						//handle CSSM
						ContextSituationState css1= new ContextSituationState();
						String objectURI = o.getJSONObject("subject").getString("subjectURI");
						String attributeURI = o.getJSONObject("object").getString("objectType"); //here we use objectType
						String stateURI = o.getJSONObject("object").getString("objectURI");
						ContextAttributeSnapshot casnap = new ContextAttributeSnapshot(objectURI, attributeURI, stateURI);
						css1.addContextAttributeSnapshotToList(casnap);
						
						//find a list of related objects by using relation matrix
						Iterator itobjects = cd.getCol_domain().iterator();
						int index = 0;
						int tag2 = 0;
						int length = cd.getCol_domain().size();
						while(itobjects.hasNext()){
							String co2= (String)itobjects.next();
							if(objectURI.equals(co2)){
								index = tag2; //the index of the object found here, which is the matrix row
							}
							tag2++;
						}
						for (int ii = 0; ii<length; ii++){
							if(cd.getCr().getrMatrixes().getrClosenessMatrix().get(index).get(ii)>=80){
								//then the relation is good enough, we add the current state of this object
								String thisObject = cd.getCol_domain().get(ii);
								Iterator itSS1 = cd.getCcl().iterator();
								while(itSS1.hasNext()){
									ContextCategory ccSS1 = (ContextCategory)itSS1.next();
									Iterator itSS2 = ccSS1.getCol().iterator();
									while(itSS2.hasNext()){
										ContextObject coSS2 = (ContextObject)itSS2.next();
										if(thisObject.equals(coSS2.getUri())){
											//then the object here in this category is our target now
											//we will add the state and the last transition to this situation
											//then we iterate its attributes
											Iterator itSS3 = coSS2.getCasml().iterator();
											Iterator itSS4 = coSS2.getCal().iterator();
											while(itSS3.hasNext()){
												ContextAttributeStateMachine casm4 = (ContextAttributeStateMachine)itSS3.next();
												ContextAttribute ca4 = (ContextAttribute)itSS4.next();
												ContextAttributeSnapshot casnap4 = new ContextAttributeSnapshot(coSS2.getUri(), ca4.getName(),casm4.extractCurrentStateName() );
												//here the state should be the URI not the name
												if(!ListHelper.inTheSnapshotList(casnap4, css1.getContextAttributeSnapshotList())){
													css1.addContextAttributeSnapshotToList(casnap4);
												}
											}
										}
									}
								}
							}
						}
						int existAndIndex = cotmp3.existAndIndexInCSSL(css1);
						if(existAndIndex>-1){ //existing, so we just have the index, and use the index to set current
							//casm.getMatrixes().updateHmStates(casm.getCurrentState(), col);
							//casm.getMatrixes().updateDecisionMatrixStates(casm.getCurrentState(), col, decision); //row, column, decision
							cotmp3.getCssm().getSmatrixes().updateHmStates(cotmp3.getCssm().getCurrentState(), existAndIndex);
							cotmp3.getCssm().getSmatrixes().updateDecisionMatrixStates(cotmp3.getCssm().getCurrentState(), existAndIndex, decision);
							cotmp3.getCssm().setCurrentState(existAndIndex);
						}else{
							cotmp3.addContextSituationStateToList(css1);
							cotmp3.getCssm().getSmatrixes().addState(cotmp3.getCssm().getCurrentState());
							cotmp3.getCssm().getSmatrixes().addStateForDeicision(cotmp3.getCssm().getCurrentState(), decision);
							int currentIndex = cotmp3.getCssl().size()-1;
							css1.setId(currentIndex);
							cotmp3.getCssm().setCurrentState(currentIndex);
						}
						

						//after mainly handly CSSM
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
                                                                o2.getJSONObject("object").getString("objectType"));
						
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
						if(casm.getHmstates().containsKey(cas.getUri())){ //if the state has already been recorded, then edit matrix
							col = casm.getHmstates().get(cas.getUri());
							//System.out.println("row:" + casm.getCurrentState());
							//System.out.println("col:" + col);
							casm.getMatrixes().updateHmStates(casm.getCurrentState(), col);
							casm.getMatrixes().updateDecisionMatrixStates(casm.getCurrentState(), col, decision); //row, column, decision
							//TODO: important: from current state to a new state, actions can be triggered here
							// and the actions can be stored in a decision form in decision matrix
							casm.setCurrentState(col);
						} else{ 
							int size = casm.getHmstates().size();
							casm.addtoHashMapStates(size, cas.getUri());
							casm.getMatrixes().addState(casm.getCurrentState()); // use current state to find the original state
							casm.getMatrixes().addStateForDeicision(casm.getCurrentState(),decision);
							casm.setCurrentState(size); 
						}
						//cotmp3.addAttributeStateMachine(casm);
					}
				}
			}
			
			
			Iterator it3 = cc.getCol().iterator();
			boolean b3 = false; // new object
			ContextObject cotmp3 = new ContextObject();


			while(it3.hasNext()){
				cotmp3 = (ContextObject)it3.next();
				if(cotmp3.getUri().equals(co.getUri())){
					//found the object and use it
					//we get the current attribute URI, and the current state URI, and the object URI
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
                                                                o2.getJSONObject("object").getString("objectType"));
					
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
					if(casm.getHmstates().containsKey(cas.getUri())){ //if the state has already been recorded, then edit matrix
						col = casm.getHmstates().get(cas.getUri());
						//System.out.println("row:" + casm.getCurrentState());
						//System.out.println("col:" + col);
						casm.getMatrixes().updateHmStates(casm.getCurrentState(), col);
						casm.getMatrixes().updateDecisionMatrixStates(casm.getCurrentState(), col, decision); //row, column, decision
						//TODO: important: from current state to a new state, actions can be triggered here
						// and the actions can be stored in a decision form in decision matrix
						casm.setCurrentState(col);
					} else{ 
						int size = casm.getHmstates().size();
						casm.addtoHashMapStates(size, cas.getUri());
						casm.getMatrixes().addState(casm.getCurrentState()); // use current state to find the original state
						casm.getMatrixes().addStateForDeicision(casm.getCurrentState(),decision);
						casm.setCurrentState(size); 
					}
					//cotmp3.addAttributeStateMachine(casm);
				}
			}
			//handle context attribute list

		}

		return cd;
	}
        
        
	//this method is used to build the CASM and CSSM together
	public static ContextDomain assembleCASMCSSMhr(JSONArray a, ContextDomain cd) throws JSONException{
		
		//handle contextObject, ContextAttributeStateMachine List and ContextAttribute List
		ContextAttributeStateMachine casm;
		ContextSituationStateMachine cssm;
		ContextCategory cc = new ContextCategory();
		ContextObject co;
		ContextAttribute ca;
		ContextAttributeState cas;
		//System.out.println("position 1");
		for (int i = 0; i < a.length(); i++) {
			//handle context object
			JSONObject o = a.getJSONObject(i);
			
			//if the person leaves a place, then we do not consider this as a new object and a new place state, because arrive is the first
			// and in our prototype of the research, we only consider to build CASM for arrive(behavior) predicate.
			// To extend the framework, we can have behavior class in the framework, and the behavior can be a lot of types
			//This is one of the limitation of the research; another one is that we do not have the object adding and transition shift
//			if("leave".equals(o.getJSONObject("predicate").getString("predicateName"))){
//				continue;
//			}
			
			//here to handle context category
			//1: check the existence of the context category, if not, give an error in terminal
			//2: if exist, then modify that category, add or update a context object of that category
			// subjectType/objectType/PredicateType are mapped to CategoryName , subject in triple is mapped to an object
			Iterator itcc = cd.getCcl().iterator();
			boolean bcc = true; //suppose its new category at first, if false later, then okay, otherwise, break and give error
			ContextCategory cotmpcc = new ContextCategory();
			String subjectTypeCategoryName = o.getJSONObject("object").getString("type");
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
			co = new ContextObject(o.getJSONObject("object").getString("name"), 
                                o.getJSONObject("object").getString("type"), 
					o.getJSONObject("object").getString("uri"));
			int decision = "decision".equals(o.getJSONObject("state").getString("type"))?1:0;
                        
			Iterator it1;
			it1 = cc.getCol().iterator();
			boolean b = false; // new object, not existing
			ContextObject cotmp = new ContextObject();
			while(it1.hasNext()){
				cotmp = (ContextObject)it1.next();
				if(cotmp.getUri().equals(co.getUri())){
					b=true; // already has the object
				}
			}
			
			//From here on, We need to find the context situation state machine and search and update or initialize
			//here the context object is not new. and we just need to search and update
			//This situation state machine is only for the decision for using elevator, so we can have a list of situation state machine for different purpose,
			//which could be in the future work

			//System.out.println("position 1.5");
			//we need to get the related object's current state and put them into the context situation state
			if(!b){//this situation is that the context object is a new one.
				cd.getCol_domain().add(co.getUri());//this is to add new object in context domain
				cd.getCr().getrMatrixes().addState(0);
				cc.addContextObject(co);
				
				//then the context attribute will be very new and its the type in the object
				//???so context object is a real object but context attribute is a concept
				//???how to draw in uml class diagram need to be considered
				ca = new ContextAttribute(o.getJSONObject("attribute").getString("name"));
				co.addContextAttribute(ca);//append the attribute to the list cal in the co.
				
				//handle the context attribute state
				JSONObject o2 = a.getJSONObject(i);
				//System.out.println(o.getJSONObject("subject").getString("subjectName"));
				//TODO: add check to the json file that the two attributes can not be null
				cas = new ContextAttributeState(o2.getJSONObject("state").getString("name"), 
						o2.getJSONObject("state").getString("uri"),
                                                                o2.getJSONObject("state").getString("type"));
				
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
				if(casm.getHmstates().containsKey(cas.getUri())){ //then edit matrix
					col = casm.getHmstates().get(cas.getUri());
					casm.getMatrixes().updateHmStates(casm.getCurrentState(), col);
					casm.getMatrixes().updateDecisionMatrixStates(casm.getCurrentState(), col, decision); //row, column, decision
					casm.setCurrentState(col);
				} else{ //then add a state; here since the object is new, then the state should be the first one, and the size should be zero
					casm.addtoHashMapStates(0, cas.getUri());
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
						
						//handle CSSM
						ContextSituationState css1= new ContextSituationState();
						String objectURI = o.getJSONObject("object").getString("uri");
						String attributeURI = o.getJSONObject("attribute").getString("type"); //here we use objectType
						String stateURI = o.getJSONObject("state").getString("uri");
						ContextAttributeSnapshot casnap = new ContextAttributeSnapshot(objectURI, attributeURI, stateURI);
						css1.addContextAttributeSnapshotToList(casnap);
						
						//find a list of related objects by using relation matrix
						Iterator itobjects = cd.getCol_domain().iterator();
						int index = 0;
						int tag2 = 0;
						int length = cd.getCol_domain().size();
						while(itobjects.hasNext()){
							String co2= (String)itobjects.next();
							if(objectURI.equals(co2)){
								index = tag2; //the index of the object found here, which is the matrix row
							}
							tag2++;
						}
						for (int ii = 0; ii<length; ii++){
							if(cd.getCr().getrMatrixes().getrClosenessMatrix().get(index).get(ii)>=80){
								//then the relation is good enough, we add the current state of this object
								String thisObject = cd.getCol_domain().get(ii);
								Iterator itSS1 = cd.getCcl().iterator();
								while(itSS1.hasNext()){
									ContextCategory ccSS1 = (ContextCategory)itSS1.next();
									Iterator itSS2 = ccSS1.getCol().iterator();
									while(itSS2.hasNext()){
										ContextObject coSS2 = (ContextObject)itSS2.next();
										if(thisObject.equals(coSS2.getUri())){
											//then the object here in this category is our target now
											//we will add the state and the last transition to this situation
											//then we iterate its attributes
											Iterator itSS3 = coSS2.getCasml().iterator();
											Iterator itSS4 = coSS2.getCal().iterator();
											while(itSS3.hasNext()){
												ContextAttributeStateMachine casm4 = (ContextAttributeStateMachine)itSS3.next();
												ContextAttribute ca4 = (ContextAttribute)itSS4.next();
												ContextAttributeSnapshot casnap4 = new ContextAttributeSnapshot(coSS2.getUri(), ca4.getName(),casm4.extractCurrentStateName() );
												//here the state should be the URI not the name
												if(!ListHelper.inTheSnapshotList(casnap4, css1.getContextAttributeSnapshotList())){
													css1.addContextAttributeSnapshotToList(casnap4);
												}
											}
										}
									}
								}
							}
						}
						int existAndIndex = cotmp3.existAndIndexInCSSL(css1);
						if(existAndIndex>-1){ //existing, so we just have the index, and use the index to set current
							//casm.getMatrixes().updateHmStates(casm.getCurrentState(), col);
							//casm.getMatrixes().updateDecisionMatrixStates(casm.getCurrentState(), col, decision); //row, column, decision
							cotmp3.getCssm().getSmatrixes().updateHmStates(cotmp3.getCssm().getCurrentState(), existAndIndex);
							cotmp3.getCssm().getSmatrixes().updateDecisionMatrixStates(cotmp3.getCssm().getCurrentState(), existAndIndex, decision);
							cotmp3.getCssm().setCurrentState(existAndIndex);
						}else{
							cotmp3.addContextSituationStateToList(css1);
							cotmp3.getCssm().getSmatrixes().addState(cotmp3.getCssm().getCurrentState());
							cotmp3.getCssm().getSmatrixes().addStateForDeicision(cotmp3.getCssm().getCurrentState(), decision);
							int currentIndex = cotmp3.getCssl().size()-1;
							css1.setId(currentIndex);
							cotmp3.getCssm().setCurrentState(currentIndex);
						}
						

						//after mainly handly CSSM
						Iterator it4=cotmp3.getCal()!=null?cotmp3.getCal().iterator():null;
						ContextAttribute catmp;
						int it4int=0, tag=0;
						while(it4.hasNext()){
							catmp = (ContextAttribute)it4.next();
							if(catmp.getName().equals(o.getJSONObject("attribute").getString("name"))){
								it4int=tag;
							}
							tag++;
						}
						ca=cotmp3.getCal().get(it4int);
						
						//handle the context attribute state
						JSONObject o2 = a.getJSONObject(i);
						//System.out.println(o.getJSONObject("subject").getString("subjectName"));
						//TODO: add check to the json file that the two attributes can not be null
						cas = new ContextAttributeState(o2.getJSONObject("state").getString("name"), 
								o2.getJSONObject("state").getString("uri"),
                                                                o2.getJSONObject("state").getString("type"));
						
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
						if(casm.getHmstates().containsKey(cas.getUri())){ //if the state has already been recorded, then edit matrix
							col = casm.getHmstates().get(cas.getUri());
							//System.out.println("row:" + casm.getCurrentState());
							//System.out.println("col:" + col);
							casm.getMatrixes().updateHmStates(casm.getCurrentState(), col);
							casm.getMatrixes().updateDecisionMatrixStates(casm.getCurrentState(), col, decision); //row, column, decision
							//TODO: important: from current state to a new state, actions can be triggered here
							// and the actions can be stored in a decision form in decision matrix
							casm.setCurrentState(col);
						} else{ 
							int size = casm.getHmstates().size();
							casm.addtoHashMapStates(size, cas.getUri());
							casm.getMatrixes().addState(casm.getCurrentState()); // use current state to find the original state
							casm.getMatrixes().addStateForDeicision(casm.getCurrentState(),decision);
							casm.setCurrentState(size); 
						}
						//cotmp3.addAttributeStateMachine(casm);
					}
				}
			}
			
			
			Iterator it3 = cc.getCol().iterator();
			boolean b3 = false; // new object
			ContextObject cotmp3 = new ContextObject();


			while(it3.hasNext()){
				cotmp3 = (ContextObject)it3.next();
				if(cotmp3.getUri().equals(co.getUri())){
					//found the object and use it
					//we get the current attribute URI, and the current state URI, and the object URI
					Iterator it4=cotmp3.getCal()!=null?cotmp3.getCal().iterator():null;
					ContextAttribute catmp;
					int it4int=0, tag=0;
					while(it4.hasNext()){
						catmp = (ContextAttribute)it4.next();
						if(catmp.getName().equals(o.getJSONObject("attribute").getString("name"))){
							it4int=tag;
						}
						tag++;
					}
					ca=cotmp3.getCal().get(it4int);
					
					//handle the context attribute state
					JSONObject o2 = a.getJSONObject(i);
					//System.out.println(o.getJSONObject("subject").getString("subjectName"));
					//TODO: add check to the json file that the two attributes can not be null
					cas = new ContextAttributeState(o2.getJSONObject("state").getString("name"), 
							o2.getJSONObject("state").getString("uri"),
                                                                o2.getJSONObject("state").getString("type"));
					
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
					if(casm.getHmstates().containsKey(cas.getUri())){ //if the state has already been recorded, then edit matrix
						col = casm.getHmstates().get(cas.getUri());
						//System.out.println("row:" + casm.getCurrentState());
						//System.out.println("col:" + col);
						casm.getMatrixes().updateHmStates(casm.getCurrentState(), col);
						casm.getMatrixes().updateDecisionMatrixStates(casm.getCurrentState(), col, decision); //row, column, decision
						//TODO: important: from current state to a new state, actions can be triggered here
						// and the actions can be stored in a decision form in decision matrix
						casm.setCurrentState(col);
					} else{ 
						int size = casm.getHmstates().size();
						casm.addtoHashMapStates(size, cas.getUri());
						casm.getMatrixes().addState(casm.getCurrentState()); // use current state to find the original state
						casm.getMatrixes().addStateForDeicision(casm.getCurrentState(),decision);
						casm.setCurrentState(size); 
					}
					//cotmp3.addAttributeStateMachine(casm);
				}
			}
			//handle context attribute list

		}

		return cd;
	}

}
