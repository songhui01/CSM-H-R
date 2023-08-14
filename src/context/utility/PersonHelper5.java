package context.utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import context.utility.RandomGenerator.Location;
import context.utility.RandomGenerator.RESULT;

public class PersonHelper5 {
	Set <List<String>> personLocation=new HashSet<List<String>>();
	final static Map<String, String> location = new HashMap<String, String>();
	
	enum NAME 
    { 
        Mike, Frank, Joshua, Ted, Alice; 
    } 
	 enum Location
    { 
    	HOME, GYM, DINNINGHALL, COFFEESHOP, SEC;
    } 
    
    private static final List<Location> VALUES =
    		Collections.unmodifiableList(Arrays.asList(Location.values()));
    private static final int enumSize = VALUES.size();
    private static final int size = location.size();
    private static final Random RANDOM = new Random();
	    
	PersonHelper5(){
		location.put("HOME", "location03");
		location.put("GYM", "location04");
		location.put("DINNINGHALL", "location05");
		location.put("COFFEESHOP", "location06");
		location.put("SEC", "location07");
		List<String> a = new ArrayList<String>();
		String[] s = randomLocation();
		a.add("p001");
		a.add("Mike");
		a.add("Professor");
		a.add("leave");
		a.add("action01");
		a.add(s[0]); //uri of location
		a.add(s[1]); //name of location
		personLocation.add(a);
		a = new ArrayList<String>();
		s = randomLocation();
		a.add("p002");
		a.add("Frank");
		a.add("Student");
		a.add("leave");
		a.add("action01");
		a.add(s[0]); //uri of location
		a.add(s[1]); //name of location
		personLocation.add(a);
		a = new ArrayList<String>();
		s = randomLocation();
		a.add("p003");
		a.add("Joshua");
		a.add("Professor");
		a.add("leave");
		a.add("action01");
		a.add(s[0]); //uri of location
		a.add(s[1]); //name of location
		personLocation.add(a);
		a = new ArrayList<String>();
		s = randomLocation();
		a.add("p004");
		a.add("Ted");
		a.add("Student");
		a.add("leave");
		a.add("action01");
		a.add(s[0]); //uri of location
		a.add(s[1]); //name of location
		personLocation.add(a);
		a = new ArrayList<String>();
		s = randomLocation();
		a.add("p005");
		a.add("Alice");
		a.add("Student");
		a.add("leave");
		a.add("action01");
		a.add(s[0]); //uri of location
		a.add(s[1]); //name of location
		personLocation.add(a);
	}
	
	
	public List getNextPerson(){

		
		int size = personLocation.size();
		int item = new Random().nextInt(size); // In real life, the Random object should be rather more shared than this
		int i = 0;
		List<String> temp1 = new ArrayList<String>();
		List<String> temp2 = new ArrayList<String>();
		for(List obj : personLocation) //this step is assign temp1 and temp2 with obj
		{
		    if (i == item){
		        temp1 = obj;
	        	temp2 = obj;
		    }
		    i++;
		}
		if(temp1.contains("leave")){
			String[] s;
			if("GYM".equals(temp1.get(3))){
				System.out.println("test");
				s = randomLocationWithout_GYM();
			}else if("DINNINGHALL".equals(temp1.get(3))){
				s = randomLocationWithout_DINNINGHALL();
			}else if("COFFEESHOP".equals(temp1.get(3))){
				s = randomLocationWithout_COFFEESHOP();
			}else if("SEC".equals(temp1.get(3))){
				s = randomLocationWithout_SEC();
			}else if("HOME".equals(temp1.get(3))){
				s = randomLocationWithout_HOME();
			}else{
				s = randomLocation();
			}
			temp1.remove("leave");
			temp1.remove("action01");
			temp1.remove(3); //location URI
			temp1.remove(3); //location name
			temp1.add("arrive");
			temp1.add("action02");
			temp1.add(s[0]);
			temp1.add(s[1]);
		}else{
			String s2=temp1.get(4);
			String s3 =temp1.get(5);
			temp1.remove("arrive");
			temp1.remove("action02");
			temp1.remove(3); //location URI
			temp1.remove(3); //location name
			temp1.add("leave");
			temp1.add("action01");
			temp1.add(s2);
			temp1.add(s3);
		}
		personLocation.remove(temp2);
		personLocation.add(temp1);
		return temp2;
	}
	
    //We only consider three types of location here.
   
    public static String[] randomLocation(){

    	String tmp = VALUES.get(RANDOM.nextInt(enumSize)).toString();
    	Object randomName = location.get(tmp);
    	//System.out.print(randomName);
        String[] s = {randomName.toString(),tmp};
    	return s;
    	
    }
    
    enum LocationWithout_HOME
    { 
    	GYM, DINNINGHALL, COFFEESHOP, SEC;
    } 
    private static final List<LocationWithout_HOME> VALUESWithout_HOME =
    		Collections.unmodifiableList(Arrays.asList(LocationWithout_HOME.values()));
    private static final int enumSizeWithout_HOME = VALUESWithout_HOME.size();
    public static String[] randomLocationWithout_HOME(){
    	String tmp = VALUESWithout_HOME.get(RANDOM.nextInt(enumSizeWithout_HOME)).toString();
    	Object randomName = location.get(tmp);
    	//System.out.print(randomName);
        String[] s = {randomName.toString(),tmp};
    	return s;
    }
    
    enum LocationWithout_GYM
    { 
    	HOME, DINNINGHALL, COFFEESHOP, SEC;
    } 
    private static final List<LocationWithout_GYM> VALUESWithout_GYM =
    		Collections.unmodifiableList(Arrays.asList(LocationWithout_GYM.values()));
    private static final int enumSizeWithout_GYM = VALUESWithout_GYM.size();
    public static String[] randomLocationWithout_GYM(){
    	String tmp = VALUESWithout_GYM.get(RANDOM.nextInt(enumSizeWithout_GYM)).toString();
    	Object randomName = location.get(tmp);
    	//System.out.print(randomName);
        String[] s = {randomName.toString(),tmp};
    	return s;
    }
    
    enum LocationWithout_COFFEESHOP
    { 
    	HOME, DINNINGHALL, GYM, SEC;
    } 
    private static final List<LocationWithout_COFFEESHOP> VALUESWithout_COFFEESHOP =
    		Collections.unmodifiableList(Arrays.asList(LocationWithout_COFFEESHOP.values()));
    private static final int enumSizeWithout_COFFEESHOP = VALUESWithout_COFFEESHOP.size();
    public static String[] randomLocationWithout_COFFEESHOP(){
    	String tmp = VALUESWithout_COFFEESHOP.get(RANDOM.nextInt(enumSizeWithout_COFFEESHOP)).toString();
    	Object randomName = location.get(tmp);
    	//System.out.print(randomName);
        String[] s = {randomName.toString(),tmp};
    	return s;
    }
    
    enum LocationWithout_SEC
    { 
    	HOME, DINNINGHALL, GYM, COFFEESHOP;
    } 
    private static final List<LocationWithout_SEC> VALUESWithout_SEC =
    		Collections.unmodifiableList(Arrays.asList(LocationWithout_SEC.values()));
    private static final int enumSizeWithout_SEC = VALUESWithout_SEC.size();
    public static String[] randomLocationWithout_SEC(){
    	String tmp = VALUESWithout_SEC.get(RANDOM.nextInt(enumSizeWithout_SEC)).toString();
    	Object randomName = location.get(tmp);
    	//System.out.print(randomName);
        String[] s = {randomName.toString(),tmp};
    	return s;
    }
    
    enum LocationWithout_DINNINGHALL
    { 
    	HOME, SEC, GYM, COFFEESHOP;
    } 
    private static final List<LocationWithout_DINNINGHALL> VALUESWithout_DINNINGHALL =
    		Collections.unmodifiableList(Arrays.asList(LocationWithout_DINNINGHALL.values()));
    private static final int enumSizeWithout_DINNINGHALL = VALUESWithout_DINNINGHALL.size();
    public static String[] randomLocationWithout_DINNINGHALL(){
    	String tmp = VALUESWithout_DINNINGHALL.get(RANDOM.nextInt(enumSizeWithout_DINNINGHALL)).toString();
    	Object randomName = location.get(tmp);
    	//System.out.print(randomName);
        String[] s = {randomName.toString(),tmp};
    	return s;
    }

}
