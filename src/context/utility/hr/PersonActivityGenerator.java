/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package context.utility.hr;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import context.convertion.ConditionHR;
import context.convertion.ObjectHR;
import context.convertion.AttributeHR;
import context.convertion.StateHR;
import context.convertion.TripleHR;
import context.convertion.UpperLevelInfo;
import context.core.granularity.TimeGranularity;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileWriter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import java.util.HashMap;
import java.util.Map;

// smart campus domain: suppose we have 20000 students, 500 faculties, 100 types of students which have 100 different behavior habit styles, based on all kinds of information given.
// students they each may have 5 friends on campus, 3 friends in contact outside of campus, in average 3 family members in contact.
// instructors they may have collegues relationships, 1 to 30, and they can teach students 1 to 50, advise students 1 to 2.
// locations will have 20 buildings for classes and various offices, labs, 4 libraries, 3 dinning halls, 2 rec center, and dormitories 10.
// student will need to go to two buildings for classes, 1 library for after class learning or projects meetings, and then a random dinning hall for lunch 
// or dinner, and rec center for exercising, 

// for testing, we will need to generate a dataset for one style of students, then check the behavior of the student, like the next actions, and decisions
// particularly, we will check the affection of the transition history

public class PersonActivityGenerator extends Generator{
    private static final int WORKING_HOURS_PER_DAY = 8; // hours
    private static final int ACTIVITY_CHANGE_INTERVAL = 2; // hours

    private static final int NUM_PEOPLE = 20;
    private static final int NUM_YEARS = 10;
    private static final double ACTIVITY_PROBABILITY = 0.5;

    private static final Random random = new Random();
    
    private static int home_times = 0;
    private static int res_times = 0;
    private static int staying_outside_times = 0;
    private static int record_id = 0;
    
    private static UpperLevelInfo uli = new UpperLevelInfo();
    private static List<TripleHR> personActivities = new ArrayList<TripleHR>();
    
    private static ArrayList<String> sugarLevels = new ArrayList<>(Arrays.asList(PersonGenerator.BLOOD_SUGAR_CONDITIONS));
    
    public static class PersonActivity {
        private final String personName;
        private final LocalDateTime timestamp;
        private final String location;
        private final String activity;

        public PersonActivity(String personName, LocalDateTime timestamp, String location, String activity) {
            this.personName = personName;
            this.timestamp = timestamp;
            this.location = location;
            this.activity = activity;
        }

        public String toString() {
            return "Person: " + personName + "\n"
                    + "Timestamp: " + timestamp + "\n"
                    + "Location: " + location + "\n"
                    + "Activity: " + activity + "\n";
        }
    }
    
    // "Home", "Gym", "Park" are special locations compared to places, and restaurants
    // In this file, we only consider these general names without considering their precise locations, distances, traffics and other factors among each other
    // suppose we are considering every 1 hour or 2 hour interval by probability, then the interval should be considered as the same level to the matrixes as a parameter.
    public static void generatePersonActivities(Person person, List<String> places, List<String> restaurantNames, BufferedWriter writer) throws IOException, NoSuchFieldException, IllegalAccessException {

        // Initialize person's initial state
        String currentPlace = places.get(random.nextInt(places.size()));
        String currentRestaurant = null;
        
        // Update person's health condition, blood sugar, and emotion condition
        person.setHealthCondition(PersonGenerator.HEALTH_CONDITIONS[random.nextInt(PersonGenerator.HEALTH_CONDITIONS.length)]);
        person.setBloodSugarCondition(person.getPersonalNextBloodSugarCondition(person.getBloodSugarCondition()));
        person.setEmotionCondition(PersonGenerator.EMOTION_CONDITIONS[random.nextInt(PersonGenerator.EMOTION_CONDITIONS.length)]);

        String currentActivity = person.getActivityByPlaceAndProbability(currentPlace);

        ObjectHR object = new ObjectHR();
        object.setName(person.getFullName());
        object.setType(person.getType()); // now we only have student and professor
        object.setUri(person.getUri());
        ArrayList<String> attributeList = new ArrayList<>();
        attributeList.add("healthCondition");
        attributeList.add("bloodSugarCondition");
        attributeList.add("emotionCondition");
        attributeList.add("ageGroupCondition");
        attributeList.add("sex");
        attributeList.add("occupationCondition");
        attributeList.add("activity");
        attributeList.add("location");
        // Generate person's activities for each hour of the day, from 10 am to 8 pm
        for (int hour = 10; hour < 200; hour += 1 ) {
            // Check if it's time to change the activity
            if (hour > 10 ) { //&& hour % ACTIVITY_CHANGE_INTERVAL == 0) {
                String originalPlace = currentPlace;
                String originalRestaurant = currentRestaurant;
                
                // Randomly choose a new place to visit
                currentPlace = places.get(random.nextInt(places.size()));
                person.setLocation(currentPlace);
                
                // Update person's health condition, and emotion condition
                person.setHealthCondition(PersonGenerator.HEALTH_CONDITIONS[random.nextInt(PersonGenerator.HEALTH_CONDITIONS.length)]);
                person.setEmotionCondition(PersonGenerator.EMOTION_CONDITIONS[random.nextInt(PersonGenerator.EMOTION_CONDITIONS.length)]);
                
                currentRestaurant = null;
                /*
                when their sugar level is low, and if they are currently not in a restaurant, 
                they will 60% possibility go to a restaurant for the next destination, and then their blood sugar level will become normal(70%),
                or high(30%), they will 30% possibility go back to home, and their blood sugar level will become normal(90%) or high(10%).
                */
                int index = sugarLevels.indexOf(person.getBloodSugarCondition());
                if(person.getBloodSugarCondition().contains("Low") && originalRestaurant == null && originalPlace != "Home") {
                    if(random.nextDouble() < person.getPROBABILITY_OUTSIDE_LOW_SUGAR_RESTAURANT_VISIT()) {
                        writer.write(person.getHealthCondition() + ", " + 
                                person.getBloodSugarCondition() + ", " + 
                                person.getEmotionCondition() + ", " + 
                                person.getAgeGroupCondition() + ", " + 
                                person.getSex() + ", " + 
//                                person.getOccupationCondition() + ", go_restaurant"
                                person.getOccupationCondition() + ", " + 
                                "go_restaurant"
                                + "\n");
                        res_times++;
                        currentPlace = restaurantNames.get(random.nextInt(restaurantNames.size()));
                        currentRestaurant = currentPlace;
                        if(random.nextDouble() < person.getPROBABILITY_SUGAR_RESTAURANT_HIGHER_1()) {
                            person.setBloodSugarCondition(PersonGenerator.BLOOD_SUGAR_CONDITIONS[index + 1]);
                        } else {
                            person.setBloodSugarCondition(PersonGenerator.BLOOD_SUGAR_CONDITIONS[index + 2]);
                        }
                    } else {
                        home_times ++;
                        currentPlace = "Home";
                        writer.write(person.getHealthCondition()+ ", " + 
                                person.getBloodSugarCondition()+ ", " + 
                                person.getEmotionCondition()+ ", " + 
                                person.getAgeGroupCondition()+ ", " + 
                                person.getSex()+ ", " + 
                                person.getOccupationCondition()+ ", " + 
                                "other"
                                + "\n");
                        if(random.nextDouble() < person.getPROBABILITY_SUGAR_HOME_HIGHER_1()) {
                            person.setBloodSugarCondition(PersonGenerator.BLOOD_SUGAR_CONDITIONS[index + 1]);
                        } else {
                            person.setBloodSugarCondition(PersonGenerator.BLOOD_SUGAR_CONDITIONS[index + 2]);
                        }
                    }
                    
//                    else if(random.nextDouble() < person.getPROBABILITY_OUTSIDE_LOW_SUGAR_HOME_RETURN()){ // && originalRestaurant == null && originalPlace != "Home"
//                        home_times ++;
//                        currentPlace = "Home";
//                        writer.write(person.getHealthCondition()+ ", " + 
//                                person.getBloodSugarCondition()+ ", " + 
//                                person.getEmotionCondition()+ ", " + 
//                                person.getAgeGroupCondition()+ ", " + 
//                                person.getSex()+ ", " + 
//                                person.getOccupationCondition()+ ", " + 
//                                "other"
//                                + "\n");
//                        if(random.nextDouble() < person.getPROBABILITY_SUGAR_HOME_HIGHER_1()) {
//                            person.setBloodSugarCondition(PersonGenerator.BLOOD_SUGAR_CONDITIONS[index + 1]);
//                        } else {
//                            person.setBloodSugarCondition(PersonGenerator.BLOOD_SUGAR_CONDITIONS[index + 2]);
//                        }
//                    } else if(random.nextDouble() < person.getPROBABILITY_OUTSIDE_LOW_SUGAR_STAY_THE_SAME_PLACE()){
//                        staying_outside_times ++;
////                        currentPlace = "outside"; current place will be still outside of home or restaurant 
//                        writer.write(person.getHealthCondition()+ ", " + 
//                                person.getBloodSugarCondition()+ ", " + 
//                                person.getEmotionCondition()+ ", " + 
//                                person.getAgeGroupCondition()+ ", " + 
//                                person.getSex()+ ", " + 
//                                person.getOccupationCondition()+ ", " + 
//                                "other"
//                                + "\n");
//                        if(random.nextDouble() < person.getPROBABILITY_SUGAR_STAY_OUTSIDE_HIGHER_1()) {
//                            person.setBloodSugarCondition(PersonGenerator.BLOOD_SUGAR_CONDITIONS[index + 1]);
//                        } else {
//                            person.setBloodSugarCondition(PersonGenerator.BLOOD_SUGAR_CONDITIONS[index + 2]);
//                        }
//                    }
                } else if(person.getBloodSugarCondition().contains("Low") && originalRestaurant != null) { 
                    // visited restaurant, then go out to stores, since the place has been updated, then no need to update the place here
                    // just update the sugar
                    if(random.nextDouble() < person.getPROBABILITY_SUGAR_RESTAURANT_HIGHER_1()) {
                        person.setBloodSugarCondition(PersonGenerator.BLOOD_SUGAR_CONDITIONS[index + 1]);
                    } else {
                        person.setBloodSugarCondition(PersonGenerator.BLOOD_SUGAR_CONDITIONS[index + 2]);
                    }
                } else if(person.getBloodSugarCondition().contains("Low") && originalPlace == "Home") { 
                    // visited home, then go out to stores, since the place has been updated, then no need to update the place here
                    // just update the sugar for historically at home
                    if(random.nextDouble() < person.getPROBABILITY_SUGAR_HOME_HIGHER_1()) {
                        person.setBloodSugarCondition(PersonGenerator.BLOOD_SUGAR_CONDITIONS[index + 1]);
                    } else {
                        person.setBloodSugarCondition(PersonGenerator.BLOOD_SUGAR_CONDITIONS[index + 2]);
                    }
                } else {
                    // person.setBloodSugarCondition(PersonGenerator.BLOOD_SUGAR_CONDITIONS[random.nextInt(PersonGenerator.BLOOD_SUGAR_CONDITIONS.length)]);
                    person.setBloodSugarCondition(person.getPersonalNextBloodSugarCondition(person.getBloodSugarCondition()));
                }
                
                if(!person.getBloodSugarCondition().contains("Low")) {
                    originalRestaurant = null;
                }
                
                // the function call is like, if the place is home, then it will select an activity of home by probability and return it here
                currentActivity = person.getActivityByPlaceAndProbability(currentPlace);
            
            
            /**
             * we now know the probability of going home and going restaurant if given blood sugar is low, however, if not only going home or restaurant, then
             * this is the next step, to consider the activity, which will be decided by the transition history.
             * 
             * Now we will create the generated data format, so that the CSMEngine can assemble the data into CASM and CSSM.
             * 
             * for CSSM, we use "1-2-3-4" as the hash map key to denote the unique key of a situation state. if a new attribute is added, like 5, we can have
             * two options, one is to create another branch, "1-2-3-4-5", and maintain multiple CSSM at the same time, and see how this will affect the decision matrix in a relatively long run, if not,
             * we will denote 5 is not relevant in this app domain, and return to use "1-2-3-4". Another option is to use CASM to decide whether 5 is relevant
             * with the decision.
             * 
             * Normally, many recommendation systems are decision-based[][]. Many decisions can be achieved by learning from data[][]. And some data connections 
             * are a lot of if and else and sometimes it is very dynamic to build such steady if-else relationships, due to dynamism. So we just provide a way to 
             * build it dynamically, and also give a hints that which will affect the context. Our main contribution is to develop the framework and two implementations
             * exploration based on different data structure styles, to provide a way of starting the data processing and add hands-on intelligence
             * to their systems. It can also serves as a start basis to explore related methodologies and algorithms.
             */
            
                for(int i = 0; i < attributeList.size(); i++) {
                    AttributeHR attribute = new AttributeHR();
                    StateHR state = new StateHR();
                    ConditionHR condition = new ConditionHR();
                    attribute.setName(attributeList.get(i));
                    attribute.setType("attribute");
                    attribute.setUri("csm/smart_restaurant/"+attributeList.get(i));

                    // when "decision" is set as the type, then the processing of casm will update the location matrix as well as decision matrix
                    if(person.getBloodSugarCondition().contains("Low") && originalRestaurant == null && originalPlace != "Home" 
                            && currentRestaurant != null && "location".equals(attribute.getName())) {
                        attribute.setType("decision");
                    } else if(person.getBloodSugarCondition().contains("Low") && originalRestaurant == null && originalPlace != "Home" 
                            && currentPlace == "Home" && "location".equals(attribute.getName())) {
                        attribute.setType("decision");
                    } else if(person.getBloodSugarCondition().contains("Low") && originalRestaurant == null && originalPlace != "Home" 
                            && "location".equals(attribute.getName())) {
                        attribute.setType("decision");
                    } else if ("occupationCondition".equals(attribute.getName()) || "sex".equals(attribute.getName())) {
                        attribute.setType("Nominal");
                    } else {
                        attribute.setType("Ordinal");
                    }

                    state.setName(person.getStateNameByAttributeName(attributeList.get(i)));
                    state.setType(person.getStateTypeByAttributeName(attributeList.get(i)));
                    state.setUri("csm/smart_restaurant/" + attribute.getName() + "/" + state.getName()); //  we may update this part for hierarchies

                    condition.setName("time");
                    condition.setTime(String.valueOf(hour));
                    condition.setGranularity(TimeGranularity.HOUR);
                    condition.setType("discrete"); // TODO: for timing, it can be discrete, or period, or continous, those values can be used to determine 
                                                        // the properties of the value which help choose corresponding learning methods or transition methods

                    // only if old state and new state for an attribute are different, we add this record
                    if(person.getOldStates().containsKey(attribute.getName()) 
                            && state.getName().equals(person.getOldStates().get(attribute.getName()))) {
                        // old state equals new current state, then we just continue for this attribute, else, we update the old states to latest
                        continue;
                    } else {
                        person.getOldStates().put(attribute.getName(), state.getName());
                    }

                    TripleHR personActivity = new TripleHR();
                    personActivity.setObject(object);
                    personActivity.setAttribute(attribute);
                    personActivity.setState(state);
                    personActivity.setCondition(condition);
                    personActivity.setRID(String.valueOf(record_id));
                    record_id++;
                    personActivities.add(personActivity);
                }
            }
            
            // Print the person's current state for the hour
//            System.out.println("Hour: " + hour);
//            System.out.println(person);
//            System.out.println("Current Place: " + currentPlace);
//            System.out.println("Current Restaurant: " + (currentRestaurant != null ? currentRestaurant : "None"));
//            System.out.println("Current Activity: " + currentActivity);
//            System.out.println("----------------------------------");
        }
    }

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, IOException, NoSuchFieldException {
        int number_students = 2000; // Number of random students to generate
        int number_professors = 500;
        
        int number_of_types_of_person = 6300;
        
        String[] condition_sequences = new String[]{"HEALTH_CONDITIONS", "BLOOD_SUGAR_CONDITIONS", "EMOTION_CONDITIONS", "AGE_GROUP_CONDITIONS", 
            "SEX_CONDITIONS", "PERSON_OCCUPATION_CONDITIONS"};
        generateIndexCombProbabilityMap(number_of_types_of_person, "PersonGenerator", condition_sequences);
        
         printIndexCombProbabilityMap();
        // System.exit(0);
        
        List<Person> students = PersonGenerator.generateRandomPersonsWithType(number_students, "Student", indexCombProbabilityMap);
        List<Person> professors = PersonGenerator.generateRandomPersonsWithType(number_professors, "Professor", indexCombProbabilityMap);
        
        List<Person> people = new ArrayList<>(students);
        people.addAll(professors);
        
        int numStores = 20; // Number of unique store names to generate
        List<String> storeNames = StoreNameGenerator.generateUniqueStoreNames(numStores);
        storeNames.add("Gym");
        storeNames.add("Park");
        storeNames.add("Home");
        
        uli.setDeviceId("android");
        uli.setDeviceName("any");
        uli.setDeviceType("smartphone");
        uli.setInfoType("any");
        uli.setDomainName("Smart Restaurant");
        uli.setDomainId("6");
        uli.setDomainURI("test006");
        
        int numRestaurants = 10; // Number of unique restaurant names to generate
        List<String> restaurantNames = RestaurantNameGenerator.generateUniqueRestaurantNames(numRestaurants);

        System.out.println("Generated Random People and Their Activities:");
        
        BufferedWriter writer = getWriter("config/data_2000_times_20500_regression.arff");
        
        int countPersons = 0;
        for (Person person : people) {
            //System.out.println(person);
            
            if(person.getPROBABILITY_SUGAR_NORMAL_TURN_DOWN() < 0.50) continue;
            countPersons++;
            
            generatePersonActivities(person, storeNames, restaurantNames, writer);
            System.out.println("----------------------------------");

            // the printing below shows that the probability methods used are correct
           
            System.out.println(person.getPROBABILITY_SUGAR_NORMAL_TURN_DOWN());
            System.out.println("Home times is : " + home_times);
            System.out.println("Res times is : " + res_times);
            System.out.println("Staying times is : " + staying_outside_times);
            System.out.println("getPROBABILITY_LOW_SUGAR_RESTAURANT_VISIT is : " + person.getPROBABILITY_OUTSIDE_LOW_SUGAR_RESTAURANT_VISIT());
            System.out.println("getPROBABILITY_OUTSIDE_LOW_SUGAR_HOME_RETURN is : " + person.getPROBABILITY_OUTSIDE_LOW_SUGAR_HOME_RETURN());
            System.out.println("getPROBABILITY_OUTSIDE_LOW_SUGAR_STAY_THE_SAME_PLACE is : " + person.getPROBABILITY_OUTSIDE_LOW_SUGAR_STAY_THE_SAME_PLACE());

            System.out.println(home_times + res_times + staying_outside_times);
            System.out.println((double)res_times / (home_times + res_times + staying_outside_times));
            System.out.println("In total, the number of generated qualified persons is: " + countPersons);
            home_times = 0;
            res_times = 0;
            staying_outside_times = 0;
        }
        uli.setInfoListHR(personActivities);
        //https://www.journaldev.com/2324/jackson-json-java-parser-api-example-tutorial
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(uli);
        //System.out.print(json);

        try (PrintWriter out = new PrintWriter(new FileWriter("config/samplefile3.json", false))) {
            out.println(json);
        }

        closeWriter(writer);
        
    }
    
    public static void printIndexCombProbabilityMap() {
        // Print the contents of the map
        
        System.out.println("Hashmap size: " + indexCombProbabilityMap.size());
        for (Map.Entry<String, Double> entry : indexCombProbabilityMap.entrySet()) {
            String key = entry.getKey();
            Double value = entry.getValue();
            System.out.print("Key: ");
            System.out.print(key);
            System.out.println(" Value: " + value);
        }
    }
}
