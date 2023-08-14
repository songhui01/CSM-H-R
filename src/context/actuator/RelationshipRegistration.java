package context.actuator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import context.core.ContextDomain;
import context.core.RelationshipPair;

public class RelationshipRegistration {
	public static void main(String args[]) throws JSONException, IOException{
		ObjectMapper mapper = new ObjectMapper();
		//JSON from file to Object
		//ContextDomain cd1 = mapper.readValue(new File("config/relationshipRegistration.json"), ContextDomain.class);
		JSONObject obj = JasonReader.readJson("config/relationshipRegistration2.json");
		JSONArray a = obj.getJSONArray("infoList");
		
		//handle domain of the container
		//container contains all state machine for the input; context domain is the domain
		String domainId = obj.getString("domainId");
		
		ContextDomain cd1 = mapper.readValue(new File("dataFiles/casmfile_domain_"+5+".json"), ContextDomain.class);
		
		//the first step is to register the relationship type; then assign the both people with the type;
		String relationName, relationURI, subjectURI, objectURI;
		int closeness = 0;
		for (int i = 0; i < a.length(); i++) {
			//handle context object
			JSONObject o = a.getJSONObject(i);
			relationName=o.getJSONObject("predicate").getString("predicateName");
			relationURI=o.getJSONObject("predicate").getString("predicateURI");
			Iterator iter = null;
			if(cd1.getCr().getRelationshipTypeList()!=null&&cd1.getCr().getRelationshipTypeList().size()>0){
				iter= cd1.getCr().getRelationshipTypeList().iterator();
			}
			boolean tag = true;
			while(iter!=null&&iter.hasNext()){
				RelationshipPair rp= (RelationshipPair)iter.next(); 
				if (relationURI.equals(rp.getRelationURI())){
					tag =false;
					break;
				}
			}

			if(tag){//this type is a new type, not in the list yet
				cd1.getCr().getRelationshipTypeList().add(new RelationshipPair(relationName, relationURI));
			}
			
			//get the index of the new relationship type so that it would be the value of rmatrix
			Iterator it2 = cd1.getCr().getRelationshipTypeList().iterator();
			int index = -1;
			int tmp = -1;
			while(it2!=null&&it2.hasNext()){
				tmp++; 
				RelationshipPair rp= (RelationshipPair)it2.next(); 
				if (relationURI.equals(rp.getRelationURI())){
					index = tmp; //this index is important
				}
			}
			subjectURI = o.getJSONObject("subject").getString("subjectURI");
			objectURI = o.getJSONObject("object").getString("objectURI");
			int x = cd1.getCol_domain().indexOf(subjectURI);
			int y = cd1.getCol_domain().indexOf(objectURI);
			
			cd1.getCr().getrMatrixes().getRmatrix().get(x).set(y, index);
			cd1.getCr().getrMatrixes().getRmatrix().get(y).set(x, index);
			
			closeness = o.getInt("closeness");
			cd1.getCr().getrMatrixes().getrClosenessMatrix().get(x).set(y, closeness);
			cd1.getCr().getrMatrixes().getrClosenessMatrix().get(y).set(x, closeness);
		}
		
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(cd1);
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
