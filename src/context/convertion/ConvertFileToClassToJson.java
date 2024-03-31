package context.convertion;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class ConvertFileToClassToJson {
	public static void main (String args[]) throws FileNotFoundException, IOException, InterruptedException{
		// Read data from file
		FileReader fr;
	    // List to collect PersonLocation objects
		List<Triple> personLocations = new ArrayList<Triple>();
		Instant start = Instant.now();
		try (BufferedReader br = new BufferedReader(new FileReader("config/samplefile1.txt"))) {
		    // Read file line by line
		    String line = "";
		    int tmp = 0;
		    while ((line = br.readLine()) != null) {
		    	//Thread.sleep(1);
		       // Parse line to extract individual fields
		       String[] data = parseLine(line);

		       Triple personLocation = new Triple();
		       personLocation.setRID(data[0].trim());
		       Subject subject = new Subject();
		       subject.setSubjectURI(data[1].trim());
		       subject.setSubjectName(data[2].trim());
		       subject.setSubjectType(data[3].trim());
		       personLocation.setSubject(subject);
		       Predicate predicate = new Predicate();
		       predicate.setPredicateName(data[6].trim());
		       predicate.setPredicateURI(data[7].trim());
		       predicate.setPredicateType(data[8].trim());
		       personLocation.setPredicate(predicate);
		       Object object = new Object();
		       object.setObjectURI(data[9].trim());
		       object.setObjectName(data[10].trim());
		       object.setObjectType(data[11].trim());
		       personLocation.setObject(object);
		       Condition condition = new Condition();
		       condition.setTime(data[4].trim());
		       personLocation.setCondition(condition);
		       Decision decision = new Decision();
		       decision.setDecisionName(data[5].trim());
		       personLocation.setDecision(decision);

		       // Add object to list
		       personLocations.add(personLocation);
		       //System.out.println(personLocation.getDecision());
		       //System.gc();
		       if(tmp == 200000){
		    	   break;
		       }
		       tmp++;
		    }
		    System.out.println("this is 1");
		    System.gc();
		    while ((line = br.readLine()) != null) {
		    	//Thread.sleep(1);
		       // Parse line to extract individual fields
		       String[] data = parseLine(line);

		       Triple personLocation = new Triple();
		       personLocation.setRID(data[0].trim());
		       Subject subject = new Subject();
		       subject.setSubjectURI(data[1].trim());
		       subject.setSubjectName(data[2].trim());
		       subject.setSubjectType(data[3].trim());
		       personLocation.setSubject(subject);
		       Predicate predicate = new Predicate();
		       predicate.setPredicateName(data[6].trim());
		       predicate.setPredicateURI(data[7].trim());
		       predicate.setPredicateType(data[8].trim());
		       personLocation.setPredicate(predicate);
		       Object object = new Object();
		       object.setObjectURI(data[9].trim());
		       object.setObjectName(data[10].trim());
		       object.setObjectType(data[11].trim());
		       personLocation.setObject(object);
		       Condition condition = new Condition();
		       condition.setTime(data[4].trim());
		       personLocation.setCondition(condition);
		       Decision decision = new Decision();
		       decision.setDecisionName(data[5].trim());
		       personLocation.setDecision(decision);

		       // Add object to list
		       personLocations.add(personLocation);
		       //System.out.println(personLocation.getDecision());
		       //System.gc();
		       if(tmp == 400000){
		    	   break;
		       }
		    }
		    System.out.println("this is 2");
		    System.gc();
		    while ((line = br.readLine()) != null) {
		    	//Thread.sleep(1);
		       // Parse line to extract individual fields
		       String[] data = parseLine(line);

		       Triple personLocation = new Triple();
		       personLocation.setRID(data[0].trim());
		       Subject subject = new Subject();
		       subject.setSubjectURI(data[1].trim());
		       subject.setSubjectName(data[2].trim());
		       subject.setSubjectType(data[3].trim());
		       personLocation.setSubject(subject);
		       Predicate predicate = new Predicate();
		       predicate.setPredicateName(data[6].trim());
		       predicate.setPredicateURI(data[7].trim());
		       predicate.setPredicateType(data[8].trim());
		       personLocation.setPredicate(predicate);
		       Object object = new Object();
		       object.setObjectURI(data[9].trim());
		       object.setObjectName(data[10].trim());
		       object.setObjectType(data[11].trim());
		       personLocation.setObject(object);
		       Condition condition = new Condition();
		       condition.setTime(data[4].trim());
		       personLocation.setCondition(condition);
		       Decision decision = new Decision();
		       decision.setDecisionName(data[5].trim());
		       personLocation.setDecision(decision);

		       // Add object to list
		       personLocations.add(personLocation);
		       //System.out.println(personLocation.getDecision());
		       //System.gc();
		       if(tmp == 600000){
		    	   break;
		       }
		    }
		    System.out.println("this is 3");
		    System.gc();
		    while ((line = br.readLine()) != null) {
		    	//Thread.sleep(1);
		       // Parse line to extract individual fields
		       String[] data = parseLine(line);

		       Triple personLocation = new Triple();
		       personLocation.setRID(data[0].trim());
		       Subject subject = new Subject();
		       subject.setSubjectURI(data[1].trim());
		       subject.setSubjectName(data[2].trim());
		       subject.setSubjectType(data[3].trim());
		       personLocation.setSubject(subject);
		       Predicate predicate = new Predicate();
		       predicate.setPredicateName(data[6].trim());
		       predicate.setPredicateURI(data[7].trim());
		       predicate.setPredicateType(data[8].trim());
		       personLocation.setPredicate(predicate);
		       Object object = new Object();
		       object.setObjectURI(data[9].trim());
		       object.setObjectName(data[10].trim());
		       object.setObjectType(data[11].trim());
		       personLocation.setObject(object);
		       Condition condition = new Condition();
		       condition.setTime(data[4].trim());
		       personLocation.setCondition(condition);
		       Decision decision = new Decision();
		       decision.setDecisionName(data[5].trim());
		       personLocation.setDecision(decision);

		       // Add object to list
		       personLocations.add(personLocation);
		       //System.out.println(personLocation.getDecision());
		       //System.gc();
		       if(tmp == 800000){
		    	   break;
		       }
		    }
		    System.out.println("this is 4");
		    System.gc();
		    while ((line = br.readLine()) != null) {
		    	//Thread.sleep(1);
		       // Parse line to extract individual fields
		       String[] data = parseLine(line);

		       Triple personLocation = new Triple();
		       personLocation.setRID(data[0].trim());
		       Subject subject = new Subject();
		       subject.setSubjectURI(data[1].trim());
		       subject.setSubjectName(data[2].trim());
		       subject.setSubjectType(data[3].trim());
		       personLocation.setSubject(subject);
		       Predicate predicate = new Predicate();
		       predicate.setPredicateName(data[6].trim());
		       predicate.setPredicateURI(data[7].trim());
		       predicate.setPredicateType(data[8].trim());
		       personLocation.setPredicate(predicate);
		       Object object = new Object();
		       object.setObjectURI(data[9].trim());
		       object.setObjectName(data[10].trim());
		       object.setObjectType(data[11].trim());
		       personLocation.setObject(object);
		       Condition condition = new Condition();
		       condition.setTime(data[4].trim());
		       personLocation.setCondition(condition);
		       Decision decision = new Decision();
		       decision.setDecisionName(data[5].trim());
		       personLocation.setDecision(decision);

		       // Add object to list
		       personLocations.add(personLocation);
		       //System.out.println(personLocation.getDecision());
		       //System.gc();
		       if(tmp == 1200000){
		    	   break;
		       }
		    }
		    System.out.println("this is 5");
		}
		
		UpperLevelInfo uli = new UpperLevelInfo();
		uli.setDeviceId("android");
		uli.setDeviceName("any");
		uli.setDeviceType("smartphone");
		uli.setInfoList(personLocations);
		uli.setInfoType("any");
		uli.setDomainName("Smart Campus");
		uli.setDomainId("5");
		uli.setDomainURI("test");
		
		//https://www.journaldev.com/2324/jackson-json-java-parser-api-example-tutorial
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(uli);
		//System.out.print(json);
		
		try (PrintWriter out = new PrintWriter(new FileWriter("config/samplefile2.json", false))) {
		    out.println(json);
		}
		Instant end = Instant.now();
		Duration timeElapsed = Duration.between(start, end);
		System.out.println("Time taken: "+ timeElapsed.toMillis() +" milliseconds");
	}
	
	public static String[] parseLine(String line){
		String[] split = line.split(",");
		return  split;
	}

}
