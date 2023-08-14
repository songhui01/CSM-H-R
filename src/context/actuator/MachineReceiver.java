package context.actuator;

import context.core.ContextAttribute;
import context.core.ContextAttributeState;
import context.core.ContextAttributeStateMachine;
import context.core.ContextCategory;
import context.statesGenerationEngine.FileLoader;

public class MachineReceiver {
	public static FileLoader fl;
	private static ContextCategory csm;
	private static ContextAttributeStateMachine casm;
	private static ContextAttribute ca;
	
	public static void main(String args[]){
		fl = new FileLoader("/Users/songhuiyue/Documents/workspace/ContextStateMachineForIVERS/config/contextFile_SmartCampus_2.txt");
		extractContextStateMachine();
		casm.prettyPrint();
		
	}
	
	public static void extractContextStateMachine(){
		String subjectType;
		String subjectName;
		while(FileLoader.info != null && !FileLoader.info.isEmpty()){
			FileLoader.proceedToNext();
			//System.out.println(checkType(fl.currentWord));
			if("SUBJECT".equals(checkType(FileLoader.currentWord))){
				//System.out.println("This is a subject: "+fl.currentWord);
				FileLoader.proceedToNext();
				while(FileLoader.currentWord.isEmpty()) FileLoader.proceedToNext();
				subjectType = FileLoader.currentWord;
				//System.out.println("This is a subject type: "+subjectType);
				FileLoader.proceedToNext();
				while(FileLoader.currentWord.isEmpty()) FileLoader.proceedToNext();
				subjectName = FileLoader.currentWord;
				//System.out.println("This is a subject type: "+subjectType+" name is: "+subjectName);
				//ContextCategory co = new ContextCategory(subjectType, subjectName, "abc");
				csm=new ContextCategory();
			}
			if("PREDICATE".equals(checkType(FileLoader.currentWord))){
				FileLoader.proceedToNext();
				while(FileLoader.currentWord.isEmpty()) FileLoader.proceedToNext();
				String attributeName = FileLoader.currentWord;
				ca= new ContextAttribute(attributeName);
				//csm.getCo().getCal().add(ca); //add a new attribute for cal in csm of co, here it is action
				
				FileLoader.proceedToNext();
				while(FileLoader.currentWord.isEmpty()) FileLoader.proceedToNext();
				String stateName = FileLoader.currentWord;
				ContextAttributeState cas = new ContextAttributeState(attributeName, stateName);
				//if casm exists, add a state and transition, if not, create one
				if(queryCASM(attributeName)){
					casm = loadCASM(attributeName);
					//casm.casl.add(cas);
					TransformerMachineToFile.transform(casm);
				}else{
					casm = new ContextAttributeStateMachine();
					//casm.casl.add(cas); //add a new state for casl of casm of co, here the state of the action is driving
					TransformerMachineToFile.transform(casm);
				}
			}
		}
	}
	
	public static boolean queryCASM(String name){
		return false;
	}
	
	public static ContextAttributeStateMachine loadCASM(String name){
		ContextAttributeStateMachine casm = (ContextAttributeStateMachine)TransformerFileToMachine.load(name);
		return casm;
	}
	
	public boolean checkSubjectExist(){
		return false;
	}
	
	public boolean checkAttributeExist(){
		return false;
	}
	public static String checkType(String word){
		return word.replaceAll(":",""); 
	}

}
