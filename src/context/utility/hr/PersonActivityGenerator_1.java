package context.utility.hr;

///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package context.utility.parts2023;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Random;
//
//// smart campus domain: suppose we have 20000 students, 500 faculties, 100 types of students which have 100 different behavior habit styles, based on all kinds of information given.
//// students they each may have 5 friends on campus, 3 friends in contact outside of campus, in average 3 family members in contact.
//// instructors they may have collegues relationships, 1 to 30, and they can teach students 1 to 50, advise students 1 to 2.
//// locations will have 20 buildings for classes and various offices, labs, 4 libraries, 3 dinning halls, 2 rec center, and dormitories 10.
//// student will need to go to two buildings for classes, 1 library for after class learning or projects meetings, and then a random dinning hall for lunch 
//// or dinner, and rec center for exercising, 
//
//// for testing, we will need to generate a dataset for one style of students, then check the behavior of the student, like the next actions, and decisions
//// particularly, we will check the affection of the transition history
//
//// this printing message is to show the data scale to converge the probability of doing something, normally to hundreds level
///*
//313
//263
//Blood go down prob is : 0.5712073687399543
//0.5434027777777778
//292
//286
//Blood go down prob is : 0.2504274260791639
//0.5051903114186851
//284
//281
//Blood go down prob is : 0.9289750857893712
//0.5026548672566372
//267
//295
//Blood go down prob is : 0.4267396143219968
//0.4750889679715303
//*/
//
//public class PersonActivityGenerator_1 {
//    private static final int WORKING_HOURS_PER_DAY = 8;
//    private static final int ACTIVITY_CHANGE_INTERVAL = 2;
//
//    private static final double PROBABILITY_RESTAURANT_VISIT = 0.5;
//    private static final double PROBABILITY_HOME_RETURN = 0.5;
//    
//    private static final double PROBABILITY_SUGAR_RESTAURANT_HIGHER_1 = 0.7;
//    private static final double PROBABILITY_SUGAR_RESTAURANT_HIGHER_2 = 0.3;
//    private static final double PROBABILITY_SUGAR_HOME_HIGHER_1 = 0.9;
//    private static final double PROBABILITY_SUGAR_HOME_HIGHER_2 = 0.1;
//    
//    private static final int NUM_PEOPLE = 20;
//    private static final int NUM_YEARS = 10;
//    private static final double ACTIVITY_PROBABILITY = 0.5;
//
//    private static final Random random = new Random();
//    
//    private static int home_times = 0;
//    private static int res_times = 0;
//    
//    
//    private static ArrayList<String> sugarLevels = new ArrayList<>(Arrays.asList(PersonGenerator.BLOOD_SUGAR_CONDITIONS));
//    
//    public static class PersonActivity {
//        private final String personName;
//        private final LocalDateTime timestamp;
//        private final String location;
//        private final String activity;
//
//        public PersonActivity(String personName, LocalDateTime timestamp, String location, String activity) {
//            this.personName = personName;
//            this.timestamp = timestamp;
//            this.location = location;
//            this.activity = activity;
//        }
//
//        public String toString() {
//            return "Person: " + personName + "\n"
//                    + "Timestamp: " + timestamp + "\n"
//                    + "Location: " + location + "\n"
//                    + "Activity: " + activity + "\n";
//        }
//    }
//    
//    // "Home", "Gym", "Park" are special locations compared to places, and restaurants
//    // In this file, we only consider these general names without considering their precise locations, distances, traffics and other factors among each other
//    // suppose we are considering every 1 hour or 2 hour interval by probability, then the interval should be considered as the same level to the matrixes as a parameter.
//    public static void generatePersonActivities(Person person, List<String> places, List<String> restaurantNames) {
//
//        // Initialize person's initial state
//        String currentPlace = places.get(random.nextInt(places.size()));
//        String currentRestaurant = null;
//        
//        // Update person's health condition, blood sugar, and emotion condition
//        person.setHealthCondition(PersonGenerator.HEALTH_CONDITIONS[random.nextInt(PersonGenerator.HEALTH_CONDITIONS.length)]);
//        person.setBloodSugarCondition(person.getPersonalNextBloodSugarCondition(person.getBloodSugarCondition()));
//        person.setEmotionCondition(PersonGenerator.EMOTION_CONDITIONS[random.nextInt(PersonGenerator.EMOTION_CONDITIONS.length)]);
//
//        String currentActivity = person.getActivityByPlaceAndProbability(currentPlace);
//
//        // Generate person's activities for each hour of the day, from 10 am to 8 pm
//        for (int hour = 10; hour < 2000; hour += 1 ) {
//            // Check if it's time to change the activity
//            if (hour > 10 ) { //&& hour % ACTIVITY_CHANGE_INTERVAL == 0) {
//                String originalPlace = currentPlace;
//                String originalRestaurant = currentRestaurant;
//                
//                // Randomly choose a new place to visit
//                currentPlace = places.get(random.nextInt(places.size()));
//                
//                // Update person's health condition, and emotion condition
//                person.setHealthCondition(PersonGenerator.HEALTH_CONDITIONS[random.nextInt(PersonGenerator.HEALTH_CONDITIONS.length)]);
//                person.setEmotionCondition(PersonGenerator.EMOTION_CONDITIONS[random.nextInt(PersonGenerator.EMOTION_CONDITIONS.length)]);
//                
//                currentRestaurant = null;
//                /*
//                when their sugar level is low, and if they are currently not in a restaurant, 
//                they will 60% possibility go to a restaurant for the next destination, and then their blood sugar level will become normal(70%),
//                or high(30%), they will 30% possibility go back to home, and their blood sugar level will become normal(90%) or high(10%).
//                */
//                int index = sugarLevels.indexOf(person.getBloodSugarCondition());
//                if(person.getBloodSugarCondition().contains("Low") && originalRestaurant == null && originalPlace != "Home") {
//                    if(random.nextDouble() < PROBABILITY_RESTAURANT_VISIT ) {
//                        res_times++;
//                        currentPlace = restaurantNames.get(random.nextInt(restaurantNames.size()));
//                        currentRestaurant = currentPlace;
//                        if(random.nextDouble() < PROBABILITY_SUGAR_RESTAURANT_HIGHER_1) {
//                            person.setBloodSugarCondition(PersonGenerator.BLOOD_SUGAR_CONDITIONS[index + 1]);
//                        } else {
//                            person.setBloodSugarCondition(PersonGenerator.BLOOD_SUGAR_CONDITIONS[index + 2]);
//                        }
//                    } else {
//                        home_times ++;
//                        currentPlace = "Home";
//                        if(random.nextDouble() < PROBABILITY_SUGAR_HOME_HIGHER_1) {
//                            person.setBloodSugarCondition(PersonGenerator.BLOOD_SUGAR_CONDITIONS[index + 1]);
//                        } else {
//                            person.setBloodSugarCondition(PersonGenerator.BLOOD_SUGAR_CONDITIONS[index + 2]);
//                        }
//                    }
//                } else if(person.getBloodSugarCondition().contains("Low") && originalRestaurant != null) { 
//                    // visited restaurant, then go out to stores, since the place has been updated, then no need to update the place here
//                    // just update the sugar
//                    if(random.nextDouble() < PROBABILITY_SUGAR_RESTAURANT_HIGHER_1) {
//                        person.setBloodSugarCondition(PersonGenerator.BLOOD_SUGAR_CONDITIONS[index + 1]);
//                    } else {
//                        person.setBloodSugarCondition(PersonGenerator.BLOOD_SUGAR_CONDITIONS[index + 2]);
//                    }
//                } else if(person.getBloodSugarCondition().contains("Low") && originalPlace == "Home") { 
//                    // visited home, then go out to stores, since the place has been updated, then no need to update the place here
//                    // just update the sugar for historically at home
//                    if(random.nextDouble() < PROBABILITY_SUGAR_HOME_HIGHER_1) {
//                        person.setBloodSugarCondition(PersonGenerator.BLOOD_SUGAR_CONDITIONS[index + 1]);
//                    } else {
//                        person.setBloodSugarCondition(PersonGenerator.BLOOD_SUGAR_CONDITIONS[index + 2]);
//                    }
//                } else {
//                    person.setBloodSugarCondition(PersonGenerator.BLOOD_SUGAR_CONDITIONS[random.nextInt(PersonGenerator.BLOOD_SUGAR_CONDITIONS.length)]);
//                }
//                
//                if(!person.getBloodSugarCondition().contains("Low")) {
//                    originalRestaurant = null;
//                }
//                
//                currentActivity = person.getActivityByPlaceAndProbability(currentPlace);
//            }
//            
//            // Print the person's current state for the hour
////            System.out.println("Hour: " + hour);
////            System.out.println(person);
////            System.out.println("Current Place: " + currentPlace);
////            System.out.println("Current Restaurant: " + (currentRestaurant != null ? currentRestaurant : "None"));
////            System.out.println("Current Activity: " + currentActivity);
////            System.out.println("----------------------------------");
//        }
//    }
//
//    public static void main(String[] args) {
//        int n = 50; // Number of random people to generate
//        List<Person> people = PersonGenerator.generateRandomStudents(n);
//
//        int numStores = 20; // Number of unique store names to generate
//        List<String> storeNames = StoreNameGenerator.generateUniqueStoreNames(numStores);
//        storeNames.add("Home");
//        storeNames.add("Gym");
//        storeNames.add("Park");
//        
//        int numRestaurants = 10; // Number of unique restaurant names to generate
//        List<String> restaurantNames = RestaurantNameGenerator.generateUniqueRestaurantNames(numRestaurants);
//
//        System.out.println("Generated Random People and Their Activities:");
//        for (Person person : people) {
//            //System.out.println(person);
//            generatePersonActivities(person, storeNames, restaurantNames);
////            System.out.println("----------------------------------");
//
//            // the printing below shows that the probability methods used are correct
//            System.out.println(home_times);
//            System.out.println(res_times);
//            System.out.println("Blood go down prob is : " + person.getPROBABILITY_SUGAR_NORMAL_TURN_DOWN());
//
//            System.out.println((double)home_times / (home_times + res_times));
//            home_times = 0;
//            res_times = 0;
//        }
//        
//    }
//}
