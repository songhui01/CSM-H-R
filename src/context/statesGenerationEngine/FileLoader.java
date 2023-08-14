package context.statesGenerationEngine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

//Each state, which may include an action, a location etc, has a unique identification, and we call it uid.
//Each entity also has an uid.
public class FileLoader {

	public static String fileName;
	public static String info;
	public static String currentWord;
	public static BufferedReader br;
	
	public FileLoader(String fileName){
		FileLoader.fileName=fileName;
		@SuppressWarnings("unused")
		File file = new File(fileName); 
		try{
			br = new BufferedReader(new FileReader(fileName));
			StringBuilder sb = new StringBuilder();
		    String line = br.readLine();

		    while (line != null) {
		        sb.append(line);
		        sb.append(System.lineSeparator());
		        line = br.readLine();
		    }
		    String everything = sb.toString();
		    info = everything;
		   //System.out.println(sb);
		   //while(info != null && !info.isEmpty()){
		   //	   proceedToNext();
		   //}
		 }catch(IOException e){
			 e.printStackTrace();
		 }
	}
	
	public static void main(String args[]) throws IOException{
		br = new BufferedReader(new FileReader("/Users/songhuiyue/Documents/workspace/ContextStateMachine/config/contextFile_SmartCampus_2.txt"));
		try {
		    StringBuilder sb = new StringBuilder();
		    String line = br.readLine();

		    while (line != null) {
		        sb.append(line);
		        sb.append(System.lineSeparator());
		        line = br.readLine();
		    }
		    String everything = sb.toString();
		    info = everything;
		    System.out.println(sb);
		  /* while(info != null && !info.isEmpty()){
			    proceedToNext();
		   }*/
		    
		} finally {
		    br.close();
		}
	}
	
	public static void proceedToNext(){
		int nextBlank = findNextBlank()>=0?findNextBlank():(info.length()-1);
		//System.out.println(nextBlank);
		if(!info.isEmpty()){
			String temp= info.substring(0, nextBlank);
			currentWord=temp.replaceAll("\\s+",""); 
			currentWord=currentWord.replaceAll(":",""); 
			if(currentWord!=" "&&currentWord!="\n"&&currentWord!="\t"&&currentWord!=""&&!currentWord.isEmpty()){
		        System.out.println(currentWord);
			}
			/*if(currentWord==""||currentWord.isEmpty()){
				nextBlank = findNextBlank()>=0?findNextBlank():(info.length()-1);
		        info = info.substring(nextBlank+1);
				proceedToNext();
			}*/
	        info = info.substring(nextBlank+1);
	        //System.out.println(info);
		}
	}
	
	public static int findNextBlank(){
		return (info.indexOf(" ")<=info.indexOf("\t")?info.indexOf(" "):info.indexOf("\t"))<info.indexOf("\n")?
				(info.indexOf(" ")<=info.indexOf("\t")?info.indexOf(" "):info.indexOf("\t")):info.indexOf("\n");
	}
}
