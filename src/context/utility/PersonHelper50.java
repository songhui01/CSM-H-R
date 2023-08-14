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

public class PersonHelper50 {
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
	    
	PersonHelper50(){
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
//		int i = 11;
//		while(i<=35){
//			s = randomLocation();
//			a.add("p0"+"i");
//			a.add("Alice"+"i");
//			a.add("Student");
//			a.add("leave");
//			a.add("action01"+"i");
//			a.add(s[0]); //uri of location
//			a.add(s[1]); //name of location
//			personLocation.add(a);
//		}
		a = new ArrayList<String>();
		s = randomLocation();
		a.add("p006");
		a.add("Alice6");
		a.add("Student");
		a.add("leave");
		a.add("action01");
		a.add(s[0]); //uri of location
		a.add(s[1]); //name of location
		personLocation.add(a); //6
		a = new ArrayList<String>();
		s = randomLocation();
		a.add("p007");
		a.add("Alice7");
		a.add("Student");
		a.add("leave");
		a.add("action01");
		a.add(s[0]); //uri of location
		a.add(s[1]); //name of location
		personLocation.add(a); //7
		a = new ArrayList<String>();
		s = randomLocation();
		a.add("p008");
		a.add("Alice8");
		a.add("Student");
		a.add("leave");
		a.add("action01");
		a.add(s[0]); //uri of location
		a.add(s[1]); //name of location
		personLocation.add(a); //8
		a = new ArrayList<String>();
		s = randomLocation();
		a.add("p009");
		a.add("Alice9");
		a.add("Student");
		a.add("leave");
		a.add("action01");
		a.add(s[0]); //uri of location
		a.add(s[1]); //name of location
		personLocation.add(a); //9
		a = new ArrayList<String>();
		s = randomLocation();
		a.add("p010");
		a.add("Alice10");
		a.add("Student");
		a.add("leave");
		a.add("action01");
		a.add(s[0]); //uri of location
		a.add(s[1]); //name of location
		personLocation.add(a); //10
		a = new ArrayList<String>();
		s = randomLocation();
		a.add("p011");
		a.add("Alice11");
		a.add("Student");
		a.add("leave");
		a.add("action01");
		a.add(s[0]); //uri of location
		a.add(s[1]); //name of location
		personLocation.add(a); //11
		a = new ArrayList<String>();
		s = randomLocation();
		a.add("p012");
		a.add("Alice12");
		a.add("Student");
		a.add("leave");
		a.add("action01");
		a.add(s[0]); //uri of location
		a.add(s[1]); //name of location
		personLocation.add(a); //12
		a = new ArrayList<String>();
		s = randomLocation();
		a.add("p013");
		a.add("Alice13");
		a.add("Student");
		a.add("leave");
		a.add("action01");
		a.add(s[0]); //uri of location
		a.add(s[1]); //name of location
		personLocation.add(a); //13
		a = new ArrayList<String>();
		s = randomLocation();
		a.add("p014");
		a.add("Alice14");
		a.add("Student");
		a.add("leave");
		a.add("action01");
		a.add(s[0]); //uri of location
		a.add(s[1]); //name of location
		personLocation.add(a); //14
		a = new ArrayList<String>();
		s = randomLocation();
		a.add("p015");
		a.add("Alice15");
		a.add("Student");
		a.add("leave");
		a.add("action01");
		a.add(s[0]); //uri of location
		a.add(s[1]); //name of location
		personLocation.add(a); //15
		a = new ArrayList<String>();
		s = randomLocation();
		a.add("p016");
		a.add("Alice16");
		a.add("Student");
		a.add("leave");
		a.add("action01");
		a.add(s[0]); //uri of location
		a.add(s[1]); //name of location
		personLocation.add(a); //16
		a = new ArrayList<String>();
		s = randomLocation();
		a.add("p017");
		a.add("Alice17");
		a.add("Student");
		a.add("leave");
		a.add("action01");
		a.add(s[0]); //uri of location
		a.add(s[1]); //name of location
		personLocation.add(a); //17
		a = new ArrayList<String>();
		s = randomLocation();
		a.add("p018");
		a.add("Alice18");
		a.add("Student");
		a.add("leave");
		a.add("action01");
		a.add(s[0]); //uri of location
		a.add(s[1]); //name of location
		personLocation.add(a); //18
		a = new ArrayList<String>();
		s = randomLocation();
		a.add("p019");
		a.add("Alice19");
		a.add("Student");
		a.add("leave");
		a.add("action01");
		a.add(s[0]); //uri of location
		a.add(s[1]); //name of location
		personLocation.add(a); //19
		a = new ArrayList<String>();
		s = randomLocation();
		a.add("p020");
		a.add("Alice20");
		a.add("Student");
		a.add("leave");
		a.add("action01");
		a.add(s[0]); //uri of location
		a.add(s[1]); //name of location
		personLocation.add(a); //20
		a = new ArrayList<String>();
		s = randomLocation();
		a.add("p021");
		a.add("Alice21");
		a.add("Student");
		a.add("leave");
		a.add("action01");
		a.add(s[0]); //uri of location
		a.add(s[1]); //name of location
		personLocation.add(a); //21
		a = new ArrayList<String>();
		s = randomLocation();
		a.add("p022");
		a.add("Alice22");
		a.add("Student");
		a.add("leave");
		a.add("action01");
		a.add(s[0]); //uri of location
		a.add(s[1]); //name of location
		personLocation.add(a); //22
		a = new ArrayList<String>();
		s = randomLocation();
		a.add("p023");
		a.add("Alice23");
		a.add("Student");
		a.add("leave");
		a.add("action01");
		a.add(s[0]); //uri of location
		a.add(s[1]); //name of location
		personLocation.add(a); //23
		a = new ArrayList<String>();
		s = randomLocation();
		a.add("p024");
		a.add("Alice24");
		a.add("Student");
		a.add("leave");
		a.add("action01");
		a.add(s[0]); //uri of location
		a.add(s[1]); //name of location
		personLocation.add(a); //24
		a = new ArrayList<String>();
		s = randomLocation();
		a.add("p025");
		a.add("Alice25");
		a.add("Student");
		a.add("leave");
		a.add("action01");
		a.add(s[0]); //uri of location
		a.add(s[1]); //name of location
		personLocation.add(a); //25
		a = new ArrayList<String>();
		s = randomLocation();
		a.add("p026");
		a.add("Alice26");
		a.add("Student");
		a.add("leave");
		a.add("action01");
		a.add(s[0]); //uri of location
		a.add(s[1]); //name of location
		personLocation.add(a); //26
		a = new ArrayList<String>();
		s = randomLocation();
		a.add("p027");
		a.add("Alice27");
		a.add("Student");
		a.add("leave");
		a.add("action01");
		a.add(s[0]); //uri of location
		a.add(s[1]); //name of location
		personLocation.add(a); //27
		a = new ArrayList<String>();
		s = randomLocation();
		a.add("p028");
		a.add("Alice28");
		a.add("Student");
		a.add("leave");
		a.add("action01");
		a.add(s[0]); //uri of location
		a.add(s[1]); //name of location
		personLocation.add(a); //28
		a = new ArrayList<String>();
		s = randomLocation();
		a.add("p029");
		a.add("Alice29");
		a.add("Student");
		a.add("leave");
		a.add("action01");
		a.add(s[0]); //uri of location
		a.add(s[1]); //name of location
		personLocation.add(a); //29
		a = new ArrayList<String>();
		s = randomLocation();
		a.add("p030");
		a.add("Alice30");
		a.add("Student");
		a.add("leave");
		a.add("action01");
		a.add(s[0]); //uri of location
		a.add(s[1]); //name of location
		personLocation.add(a); //30
		a = new ArrayList<String>();
		s = randomLocation();
		a.add("p031");
		a.add("Alice31");
		a.add("Student");
		a.add("leave");
		a.add("action01");
		a.add(s[0]); //uri of location
		a.add(s[1]); //name of location
		personLocation.add(a); //31
		a = new ArrayList<String>();
		s = randomLocation();
		a.add("p032");
		a.add("Alice32");
		a.add("Student");
		a.add("leave");
		a.add("action01");
		a.add(s[0]); //uri of location
		a.add(s[1]); //name of location
		personLocation.add(a); //32
		a = new ArrayList<String>();
		s = randomLocation();
		a.add("p033");
		a.add("Alice33");
		a.add("Student");
		a.add("leave");
		a.add("action01");
		a.add(s[0]); //uri of location
		a.add(s[1]); //name of location
		personLocation.add(a); //33
		a = new ArrayList<String>();
		s = randomLocation();
		a.add("p034");
		a.add("Alice34");
		a.add("Student");
		a.add("leave");
		a.add("action01");
		a.add(s[0]); //uri of location
		a.add(s[1]); //name of location
		personLocation.add(a); //34
		a = new ArrayList<String>();
		s = randomLocation();
		a.add("p035");
		a.add("Alice35");
		a.add("Student");
		a.add("leave");
		a.add("action01");
		a.add(s[0]); //uri of location
		a.add(s[1]); //name of location
		personLocation.add(a); //35
		a = new ArrayList<String>();
		s = randomLocation();
		a.add("p036");
		a.add("Alice36");
		a.add("Student");
		a.add("leave");
		a.add("action01");
		a.add(s[0]); //uri of location
		a.add(s[1]); //name of location
		personLocation.add(a); //36
		a = new ArrayList<String>();
		s = randomLocation();
		a.add("p037");
		a.add("Alice37");
		a.add("Student");
		a.add("leave");
		a.add("action01");
		a.add(s[0]); //uri of location
		a.add(s[1]); //name of location
		personLocation.add(a); //37
		a = new ArrayList<String>();
		s = randomLocation();
		a.add("p038");
		a.add("Alice38");
		a.add("Student");
		a.add("leave");
		a.add("action01");
		a.add(s[0]); //uri of location
		a.add(s[1]); //name of location
		personLocation.add(a); //38
		a = new ArrayList<String>();
		s = randomLocation();
		a.add("p039");
		a.add("Alice39");
		a.add("Student");
		a.add("leave");
		a.add("action01");
		a.add(s[0]); //uri of location
		a.add(s[1]); //name of location
		personLocation.add(a); //39
		a = new ArrayList<String>();
		s = randomLocation();
		a.add("p040");
		a.add("Alice40");
		a.add("Student");
		a.add("leave");
		a.add("action01");
		a.add(s[0]); //uri of location
		a.add(s[1]); //name of location
		personLocation.add(a); //40
		a = new ArrayList<String>();
		s = randomLocation();
		a.add("p041");
		a.add("Alice41");
		a.add("Student");
		a.add("leave");
		a.add("action01");
		a.add(s[0]); //uri of location
		a.add(s[1]); //name of location
		personLocation.add(a); //41
		a = new ArrayList<String>();
		s = randomLocation();
		a.add("p042");
		a.add("Alice42");
		a.add("Student");
		a.add("leave");
		a.add("action01");
		a.add(s[0]); //uri of location
		a.add(s[1]); //name of location
		personLocation.add(a); //42
		a = new ArrayList<String>();
		s = randomLocation();
		a.add("p043");
		a.add("Alice43");
		a.add("Student");
		a.add("leave");
		a.add("action01");
		a.add(s[0]); //uri of location
		a.add(s[1]); //name of location
		personLocation.add(a); //43
		a = new ArrayList<String>();
		s = randomLocation();
		a.add("p044");
		a.add("Alice44");
		a.add("Student");
		a.add("leave");
		a.add("action01");
		a.add(s[0]); //uri of location
		a.add(s[1]); //name of location
		personLocation.add(a); //44
		a = new ArrayList<String>();
		s = randomLocation();
		a.add("p045");
		a.add("Alice45");
		a.add("Student");
		a.add("leave");
		a.add("action01");
		a.add(s[0]); //uri of location
		a.add(s[1]); //name of location
		personLocation.add(a); //45
		a = new ArrayList<String>();
		s = randomLocation();
		a.add("p046");
		a.add("Alice46");
		a.add("Student");
		a.add("leave");
		a.add("action01");
		a.add(s[0]); //uri of location
		a.add(s[1]); //name of location
		personLocation.add(a); //46
		a = new ArrayList<String>();
		s = randomLocation();
		a.add("p047");
		a.add("Alice47");
		a.add("Student");
		a.add("leave");
		a.add("action01");
		a.add(s[0]); //uri of location
		a.add(s[1]); //name of location
		personLocation.add(a); //47
		a = new ArrayList<String>();
		s = randomLocation();
		a.add("p048");
		a.add("Alice48");
		a.add("Student");
		a.add("leave");
		a.add("action01");
		a.add(s[0]); //uri of location
		a.add(s[1]); //name of location
		personLocation.add(a); //48
		a = new ArrayList<String>();
		s = randomLocation();
		a.add("p049");
		a.add("Alice49");
		a.add("Student");
		a.add("leave");
		a.add("action01");
		a.add(s[0]); //uri of location
		a.add(s[1]); //name of location
		personLocation.add(a); //49
		a = new ArrayList<String>();
		s = randomLocation();
		a.add("p050");
		a.add("Alice50");
		a.add("Student");
		a.add("leave");
		a.add("action01");
		a.add(s[0]); //uri of location
		a.add(s[1]); //name of location
		personLocation.add(a); //50
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
