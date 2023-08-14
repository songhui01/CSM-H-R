package context.actuator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.*;

import context.core.*;

public class Entry {
	public static void main(String args[]) throws Exception{
		JSONObject obj = JasonReader.readJson("C:\\Users\\syue2\\workspace\\ContextStateMachineForIVERS\\config\\fromWearableDeviceSystemToCSMCloudServer.json");
		if("single".equals(obj.getString("infoType"))){
			String subjectId = obj.getJSONObject("subject").getString("subjectId");
			String subjectType = obj.getJSONObject("subject").getString("subjectType");
			String subjectName = obj.getJSONObject("subject").getString("subjectName");
			CSMRepository storage = new CSMRepository();
			try {
				//storage.readDataBase();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(subjectId);
			//if the subject and predicate(object) can be found, then just update the CASM
			//otherwise, just create a new CASM
			int objectMachineId, predicateMachineId = 0;
			objectMachineId = storage.find("subjectId", "objectId");
			predicateMachineId = storage.find("subjectId", "predicateId");
			if(objectMachineId>0){
				storage.updateCASM(obj);
			}else if (predicateMachineId>0){
				storage.updateCASM(obj);
			}else {
				storage.createCASM(obj);
			};

			//context of one entity, can include other entities, they are attributes
			//e.g. a house can have attribute of frank, number of person, overall temperature, etc
			/*
			storage.getContext("contextId/objectId");
			storage.getSituation("objectId");
			storage.getAttributeCurrentState("attributeId");
			storage.getAttributePreState("attributeId");
			storage.getAttributPredicatedNextState("attributeId");
			
			storage.createCASM(obj);
			storage.updateCASM(obj);*/
			
			List<ContextAttributeStateMachine> casml = assembleCASML(obj);
			//ContextCategory co = new ContextCategory(subjectType,subjectName,subjectId);
			ContextCategory csm = new ContextCategory();
			//csm.setCo(co);
			//csm.setCasml(casml);
		}else {
			
		}
		
	}
	
	public static ArrayList assembleCASML(JSONObject obj) throws JSONException{
		ArrayList al = new ArrayList();
		//find the machine in storage, if exists, just update it, if not, create one.
		//storage.find(obj.getJSONObject("predicate").getString("predicateId"));
		ContextAttributeStateMachine casm = new ContextAttributeStateMachine();
		//casm.setAttri(obj.getJSONObject("object").getString("objectType"));
		al.add(casm);
		return new ArrayList();
	}
}
