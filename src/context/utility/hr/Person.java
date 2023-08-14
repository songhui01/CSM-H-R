/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package context.utility.hr;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import context.core.Ontology;
import java.lang.reflect.Field;
import java.util.HashMap;

/**
 *
 * @author songhuiyue
 * 
 * from a persons, a person's activities, locations, and other labels, to build CSSM for each person.
 * 
 * finally, we can synthesize all the person's activity and situation state transitions, and analyze the different groups of people to get a prediction
 * 
 * ablation study can be performed to retrieve the most relevant factors
 * 
 * 50+students, 50+professors, and each of them have different characteristics, we can predict for each person, or predict based on the groups
 * 
 * then generate each of the matrices based on some historic data of a person, using similarity to make prediction.
 * 
 * simulate two-step transitions to see the implicit impact and reasoning
 */
public class Person extends Ontology{
    private String firstName;
    private String lastName;
    private String sex;
    private int age;
    private String healthCondition;
    private String bloodSugarCondition;
    private String emotionCondition;
    private String ageGroupCondition;
    private String occupationCondition;
    private String activity;
    private String location;

    private String type; // person::student or person::professor
    private String uri; // person002
    
    private HashMap<String, Object> oldStates = new HashMap<>();
    
    private static final Random random = new Random();
    
    /*--------restaurant domain-----------*/
    
    // This pair is designed to have dynamic probability depends on the other parameters, and the circles relationships
    // the target is to build a model to predict the probability based on the other parameters, and also, considering circles or not, 
    // and ablation study to identify the key parameters
    // This is to assume that there is a factor about visiting tendency. Asumption. 
    // A method is designed to obtain the tendencies based on different other parameters, which also contains assumptions
    private double PROBABILITY_OUTSIDE_LOW_SUGAR_RESTAURANT_VISIT = 0.6; // random.nextDouble(); between a range depends on different other factors
    private double PROBABILITY_OUTSIDE_LOW_SUGAR_HOME_RETURN = 0.2; // These attributes are only considering for the person's current location is not at Home or any restaurants
    private double PROBABILITY_OUTSIDE_LOW_SUGAR_STAY_THE_SAME_PLACE = 0.2; // Stay at the same place probably eating some food, TODO this type of parameter can be set as a global parameter

    private double PROBABILITY_SUGAR_RESTAURANT_HIGHER_1 = random.nextDouble();
    private double PROBABILITY_SUGAR_RESTAURANT_HIGHER_2 = 1 - PROBABILITY_SUGAR_RESTAURANT_HIGHER_1;
    private double PROBABILITY_SUGAR_HOME_HIGHER_1 = random.nextDouble();
    private double PROBABILITY_SUGAR_HOME_HIGHER_2 = 1 - PROBABILITY_SUGAR_HOME_HIGHER_1;
    private double PROBABILITY_SUGAR_STAY_OUTSIDE_HIGHER_1 = random.nextDouble();
    private double PROBABILITY_SUGAR_STAY_OUTSIDE_HIGHER_2 = 1 - PROBABILITY_SUGAR_HOME_HIGHER_1;
    
    private static ArrayList<String> sugarLevels = new ArrayList<>(Arrays.asList(PersonGenerator.BLOOD_SUGAR_CONDITIONS));

    private double PROBABILITY_SUGAR_NORMAL_TURN_UP = random.nextDouble(); // this parameter is for normal case, no eating, 
    // sugar level may be up or down because of a person's implicit behaviors or habits
    // This parameter is something that can affect person's long term restaurant visit, 
    // because if the person will have lower sugar, then the person will go eating something
    // the task is to learn that the affect caused by this parameter, implicit affection
    private double PROBABILITY_SUGAR_NORMAL_STAY_SAME = (1 - PROBABILITY_SUGAR_NORMAL_TURN_UP) * random.nextDouble();
    private double PROBABILITY_SUGAR_NORMAL_TURN_DOWN = 1 - PROBABILITY_SUGAR_NORMAL_TURN_UP - PROBABILITY_SUGAR_NORMAL_STAY_SAME;
    
    /*--------elevator domain-----------*/
    private double PROBABILITY_TAKE_ELEVATOR = random.nextDouble();
    private double PROBABILITY_TAKE_STAIR = (1 - PROBABILITY_TAKE_ELEVATOR) * random.nextDouble();
    private double PROBABILITY_PASS = 1 - PROBABILITY_TAKE_ELEVATOR - PROBABILITY_TAKE_STAIR;
    
    public double getPROBABILITY_SUGAR_NORMAL_TURN_UP() {
        return PROBABILITY_SUGAR_NORMAL_TURN_UP;
    }

    public void setPROBABILITY_SUGAR_NORMAL_TURN_UP(double PROBABILITY_SUGAR_NORMAL_TURN_UP) {
        this.PROBABILITY_SUGAR_NORMAL_TURN_UP = PROBABILITY_SUGAR_NORMAL_TURN_UP;
    }

    public double getPROBABILITY_SUGAR_NORMAL_TURN_DOWN() {
        return PROBABILITY_SUGAR_NORMAL_TURN_DOWN;
    }

    public void setPROBABILITY_SUGAR_NORMAL_TURN_DOWN(double PROBABILITY_SUGAR_NORMAL_TURN_DOWN) {
        this.PROBABILITY_SUGAR_NORMAL_TURN_DOWN = PROBABILITY_SUGAR_NORMAL_TURN_DOWN;
    }
    
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getOccupationCondition() {
        return occupationCondition;
    }

    public void setOccupationCondition(String occupationCondition) {
        this.occupationCondition = occupationCondition;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
    
    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }
    
    public double getPROBABILITY_OUTSIDE_LOW_SUGAR_RESTAURANT_VISIT() {
        return PROBABILITY_OUTSIDE_LOW_SUGAR_RESTAURANT_VISIT;
    }

    public void setPROBABILITY_OUTSIDE_LOW_SUGAR_RESTAURANT_VISIT(double PROBABILITY_OUTSIDE_LOW_SUGAR_RESTAURANT_VISIT) {
        this.PROBABILITY_OUTSIDE_LOW_SUGAR_RESTAURANT_VISIT = PROBABILITY_OUTSIDE_LOW_SUGAR_RESTAURANT_VISIT;
    }

    public double getPROBABILITY_OUTSIDE_LOW_SUGAR_HOME_RETURN() {
        return PROBABILITY_OUTSIDE_LOW_SUGAR_HOME_RETURN;
    }

    public void setPROBABILITY_OUTSIDE_LOW_SUGAR_HOME_RETURN(double PROBABILITY_OUTSIDE_LOW_SUGAR_HOME_RETURN) {
        this.PROBABILITY_OUTSIDE_LOW_SUGAR_HOME_RETURN = PROBABILITY_OUTSIDE_LOW_SUGAR_HOME_RETURN;
    }

    public double getPROBABILITY_OUTSIDE_LOW_SUGAR_STAY_THE_SAME_PLACE() {
        return PROBABILITY_OUTSIDE_LOW_SUGAR_STAY_THE_SAME_PLACE;
    }

    public void setPROBABILITY_OUTSIDE_LOW_SUGAR_STAY_THE_SAME_PLACE(double PROBABILITY_OUTSIDE_LOW_SUGAR_STAY_THE_SAME_PLACE) {
        this.PROBABILITY_OUTSIDE_LOW_SUGAR_STAY_THE_SAME_PLACE = PROBABILITY_OUTSIDE_LOW_SUGAR_STAY_THE_SAME_PLACE;
    }
    
    public double getPROBABILITY_SUGAR_RESTAURANT_HIGHER_1() {
        return PROBABILITY_SUGAR_RESTAURANT_HIGHER_1;
    }

    public void setPROBABILITY_SUGAR_RESTAURANT_HIGHER_1(double PROBABILITY_SUGAR_RESTAURANT_HIGHER_1) {
        this.PROBABILITY_SUGAR_RESTAURANT_HIGHER_1 = PROBABILITY_SUGAR_RESTAURANT_HIGHER_1;
    }

    public double getPROBABILITY_SUGAR_RESTAURANT_HIGHER_2() {
        return PROBABILITY_SUGAR_RESTAURANT_HIGHER_2;
    }

    public void setPROBABILITY_SUGAR_RESTAURANT_HIGHER_2(double PROBABILITY_SUGAR_RESTAURANT_HIGHER_2) {
        this.PROBABILITY_SUGAR_RESTAURANT_HIGHER_2 = PROBABILITY_SUGAR_RESTAURANT_HIGHER_2;
    }

    public double getPROBABILITY_SUGAR_HOME_HIGHER_1() {
        return PROBABILITY_SUGAR_HOME_HIGHER_1;
    }

    public void setPROBABILITY_SUGAR_HOME_HIGHER_1(double PROBABILITY_SUGAR_HOME_HIGHER_1) {
        this.PROBABILITY_SUGAR_HOME_HIGHER_1 = PROBABILITY_SUGAR_HOME_HIGHER_1;
    }

    public double getPROBABILITY_SUGAR_HOME_HIGHER_2() {
        return PROBABILITY_SUGAR_HOME_HIGHER_2;
    }

    public void setPROBABILITY_SUGAR_HOME_HIGHER_2(double PROBABILITY_SUGAR_HOME_HIGHER_2) {
        this.PROBABILITY_SUGAR_HOME_HIGHER_2 = PROBABILITY_SUGAR_HOME_HIGHER_2;
    }
    
    public double getPROBABILITY_SUGAR_STAY_OUTSIDE_HIGHER_1() {
        return PROBABILITY_SUGAR_STAY_OUTSIDE_HIGHER_1;
    }

    public void setPROBABILITY_SUGAR_STAY_OUTSIDE_HIGHER_1(double PROBABILITY_SUGAR_STAY_OUTSIDE_HIGHER_1) {
        this.PROBABILITY_SUGAR_STAY_OUTSIDE_HIGHER_1 = PROBABILITY_SUGAR_STAY_OUTSIDE_HIGHER_1;
    }

    public double getPROBABILITY_SUGAR_STAY_OUTSIDE_HIGHER_2() {
        return PROBABILITY_SUGAR_STAY_OUTSIDE_HIGHER_2;
    }

    public void setPROBABILITY_SUGAR_STAY_OUTSIDE_HIGHER_2(double PROBABILITY_SUGAR_STAY_OUTSIDE_HIGHER_2) {
        this.PROBABILITY_SUGAR_STAY_OUTSIDE_HIGHER_2 = PROBABILITY_SUGAR_STAY_OUTSIDE_HIGHER_2;
    }
    
    public String getAgeGroupCondition() {
        return ageGroupCondition;
    }

    public void setAgeGroupCondition(String ageGroupCondition) {
        this.ageGroupCondition = ageGroupCondition;
    }
    
    public HashMap<String, Object> getOldStates() {
        return oldStates;
    }

    public void setOldStates(HashMap<String, Object> oldStates) {
        this.oldStates = oldStates;
    }
    
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    
    public Person(String firstName, String lastName, String uri, int id, String type, String sex, int age, String healthCondition, String bloodSugarCondition, String emotionCondition) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.name = firstName + " " + lastName;
        this.uri = uri;
        this.id = id;
        this.type = type;
        this.sex = sex;
        this.age = age;
        this.healthCondition = healthCondition;
        this.bloodSugarCondition = bloodSugarCondition;
        this.emotionCondition = emotionCondition;
        this.occupationCondition = type;
        this.location = "start_point";
        
        this.PROBABILITY_OUTSIDE_LOW_SUGAR_HOME_RETURN = random.nextDouble();
        this.PROBABILITY_OUTSIDE_LOW_SUGAR_STAY_THE_SAME_PLACE = random.nextDouble() * 0.2;
        this.PROBABILITY_OUTSIDE_LOW_SUGAR_RESTAURANT_VISIT = 1 - this.PROBABILITY_OUTSIDE_LOW_SUGAR_HOME_RETURN - this.PROBABILITY_OUTSIDE_LOW_SUGAR_STAY_THE_SAME_PLACE;
        
        this.PROBABILITY_SUGAR_HOME_HIGHER_1 = random.nextDouble();
        this.PROBABILITY_SUGAR_HOME_HIGHER_2 = 1 - this.PROBABILITY_SUGAR_HOME_HIGHER_1;
        
        this.PROBABILITY_SUGAR_RESTAURANT_HIGHER_1 = random.nextDouble();
        this.PROBABILITY_SUGAR_RESTAURANT_HIGHER_2 = 1 - this.PROBABILITY_SUGAR_RESTAURANT_HIGHER_1;
        
        this.PROBABILITY_SUGAR_STAY_OUTSIDE_HIGHER_1 = random.nextDouble();
        this.PROBABILITY_SUGAR_STAY_OUTSIDE_HIGHER_2 = 1 - this.PROBABILITY_SUGAR_STAY_OUTSIDE_HIGHER_1;
        
        if (age <= 25) {
            this.ageGroupCondition = "16-25";
        } else if (age >= 26 && age <= 35) {
            this.ageGroupCondition = "26-35";
        } else if (age >= 36 && age <= 45) {
            this.ageGroupCondition = "36-45";
        } else if (age >= 46 && age <= 55) {
            this.ageGroupCondition = "46-55";
        } else if (age >= 56 && age <= 65) {
            this.ageGroupCondition = "56-65";
        } else {
            System.out.println("Value is outside the specified ranges");
        }
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String toString() {
        return "Name: " + getFullName() + "\n"
                + "Sex: " + sex + "\n"
                + "Age: " + age + "\n"
                + "Health Condition: " + healthCondition + "\n"
                + "Blood Sugar Condition: " + bloodSugarCondition + "\n"
                + "Emotion Condition: " + emotionCondition + "\n";
    }

    public String getBloodSugarCondition() {
        return bloodSugarCondition;
    }

    public void setBloodSugarCondition(String bloodSugarCondition) {
        this.bloodSugarCondition = bloodSugarCondition;
    }     

    public String getHealthCondition() {
        return healthCondition;
    }

    public void setHealthCondition(String healthCondition) {
        this.healthCondition = healthCondition;
    }

    public String getEmotionCondition() {
        return emotionCondition;
    }

    public void setEmotionCondition(String emotionCondition) {
        this.emotionCondition = emotionCondition;
    }

    public String getActivityByPlaceAndProbability(String currentPlace) {
        String current_activity;
        switch (currentPlace) {
            case "Home":
                current_activity = ActivityCollection.HOME_ACTIVITIES[random.nextInt(ActivityCollection.HOME_ACTIVITIES.length)];
            case "PARK":
                current_activity = ActivityCollection.PARK_ACTIVITIES[random.nextInt(ActivityCollection.PARK_ACTIVITIES.length)];
            case "GYM":
                current_activity = ActivityCollection.EXERCISE_ACTIVITIES[random.nextInt(ActivityCollection.EXERCISE_ACTIVITIES.length)]; 
            case "School":
                current_activity = ActivityCollection.SCHOOL_ACTIVITIES[random.nextInt(ActivityCollection.SCHOOL_ACTIVITIES.length)]; 
            default:
                break;
        }

        current_activity = ActivityCollection.SOCIAL_ACTIVITIES[random.nextInt(ActivityCollection.SOCIAL_ACTIVITIES.length)];
        activity = current_activity;
        return current_activity;
    }
    
    
    public String getPersonalNextBloodSugarCondition(String currentCondition) {
        double randomNumber = random.nextDouble();
        int index = sugarLevels.indexOf(currentCondition);

        if (randomNumber < PROBABILITY_SUGAR_NORMAL_TURN_DOWN) {
            //lower
            if(index == 0) {
                return currentCondition;
            }
            
            return PersonGenerator.BLOOD_SUGAR_CONDITIONS[index - 1];
        } else {
            //higher
            if(index == sugarLevels.size() - 1) {
                return currentCondition;
            }
            
            return PersonGenerator.BLOOD_SUGAR_CONDITIONS[index + 1];
        }

    }
    
    /**
     * 
     * @param attributeName
     * @return 
     * 
     * state name now is the attribute value
     */
    public String getStateNameByAttributeName(String attributeName) throws NoSuchFieldException, IllegalAccessException {
        Class<?> clazz = this.getClass();
        Field field = clazz.getDeclaredField(attributeName);
        field.setAccessible(true);
        return (String)field.get(this);
    }

    /**
     * 
     * @param attributeName
     * @return 
     * now the state type is string, int or other variable types
     */
    public String getStateTypeByAttributeName(String attributeName) throws NoSuchFieldException {
        Class<?> clazz = this.getClass();
        Field field = clazz.getDeclaredField(attributeName);
        return (String)field.getType().getSimpleName();
    }
}

