/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package context.utility.hr;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class PersonGenerator extends Generator{
    private static final String[] FIRST_NAMES = {"John", "Emma", "Michael", "Olivia", "William", "Ava", "James", "Sophia", "Alexander", "Isabella", "Daniel", "Mia", "David", "Charlotte", "Andrew", "Amelia", "Joseph", "Ella", "Matthew", "Grace"};
    private static final String[] LAST_NAMES = {"Smith", "Johnson", "Brown", "Taylor", "Miller", "Wilson", "Davis", "Thomas", "Anderson", "Roberts", "Martin", "Walker", "Clark", "Hill", "Green", "Lee", "King", "Baker", "Carter", "Evans"};

    private static final int MIN_AGE_STUDENT = 16;
    private static final int MIN_AGE_PROFESSOR = 25;
    private static final int MAX_AGE = 65;

    public static String[] HEALTH_CONDITIONS = new String[]{"Very_Poor", "Poor", "Fair", "Below_Average", "Average", "Above_Average", "Good", "Very_Good", "Excellent"};
    public static final String[] BLOOD_SUGAR_CONDITIONS = {"Very_Low", "Low", "Slightly_Low", "Normal", "Slightly_High", "High", "Very_High"};
    public static final String[] EMOTION_CONDITIONS = {"Angry", "Sad", "Calm", "Content", "Happy"};
    public static final String[] AGE_GROUP_CONDITIONS = {"16-25", "26-35", "36-45", "46-55", "56-65"};
    public static final String[] SEX_CONDITIONS = {"Male", "Female"};
    public static final String[] PERSON_OCCUPATION_CONDITIONS = {"Student", "Professor"};

    private static final Random random = new Random();
    
    public static List<Person> generateRandomPersonsWithType(int n, String person_type, Map<String, Double> indexCombProbabilityMap) {
        List<Person> people = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            String firstName = FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
            String lastName = LAST_NAMES[random.nextInt(LAST_NAMES.length)];
            String sex = SEX_CONDITIONS[random.nextInt(SEX_CONDITIONS.length)];
            int age = random.nextInt(MAX_AGE - MIN_AGE_STUDENT + 1) + MIN_AGE_STUDENT;
            String healthCondition = HEALTH_CONDITIONS[random.nextInt(HEALTH_CONDITIONS.length)];
            String bloodSugarCondition = BLOOD_SUGAR_CONDITIONS[random.nextInt(BLOOD_SUGAR_CONDITIONS.length)];
            String emotionCondition = EMOTION_CONDITIONS[random.nextInt(EMOTION_CONDITIONS.length)];
            String uri = "csm/smart_restaurant/person/" + person_type + "/" + i;
            String type = person_type;
            int id = i;

            Person person = new Person(firstName, lastName, uri, id, type, sex, age, healthCondition, bloodSugarCondition, emotionCondition);
            
            /**
             * the types_number = m, m types of students can be decided by healthCondition, bloodSugarCondition, emotionCondition, age, sex, current location,
             * and the history visit from home to restaurant, or gym home to restaurant, or park home to restaurant, etc, we now only consider steps
             * of 1, 2 and 3. So we will make comparison between 0, 1,2,3. However, for this data generation, we only consider steps of 1. 
             * 
             * Target parameter to be predicted:
             * PROBABILITY_LOW_SUGAR_RESTAURANT_VISIT
             * 
             * (hierarchies, relations, and Steps, Circles)
             * Then next step is to consider the other app domains such that whether a person has an activity, and the status of that activity
             * 
             * 
             * array1 = {a1, a2, a3, a4}, array2={b1,b2,b3,b4,b5,b6}, 
             * suppose n =5, then it is 5 is less than 2*3, then I divide array1 into 2 sets according to the index{a1, a2} and {a3, a4}, 
             * and divide array2 into 3 sets{b1, b2} {b3, b4} {b5, b6}, 
             * then I would say {a1, b1} is in group 1, {a1, b2} is in group 1, and {a1, b3} is in group 2, and {a1, b5} is in another group. 
             * So using this method, now I have 6 groups, 
             * This method is based on the above methodology however, 5 groups is by combing two of the groups into 1 group.
             * 
             * n is greater than 0, and less or equal than arrayLength1 * arrayLength2
             * 
             * to simplify the generation logic, I would just pick combination one by one and assign them into a type, and then the rest will be the last type
             */
            
            
            String[] condition_sequences = new String[]{"HEALTH_CONDITIONS", "BLOOD_SUGAR_CONDITIONS", "EMOTION_CONDITIONS", "AGE_GROUP_CONDITIONS", 
                "SEX_CONDITIONS", "PERSON_OCCUPATION_CONDITIONS"};
            int[] indexes_array = new int[condition_sequences.length];
            indexes_array[0] = getStringIndex(HEALTH_CONDITIONS, healthCondition);
            indexes_array[1] = getStringIndex(BLOOD_SUGAR_CONDITIONS, bloodSugarCondition);
            indexes_array[2] = getStringIndex(EMOTION_CONDITIONS, emotionCondition);
            indexes_array[3] = getStringIndex(AGE_GROUP_CONDITIONS, person.getAgeGroupCondition());
            indexes_array[4] = getStringIndex(SEX_CONDITIONS, sex);
            indexes_array[5] = getStringIndex(PERSON_OCCUPATION_CONDITIONS, person.getOccupationCondition());
            
            int[] defaultArray = new int[1];
            defaultArray[0] = -1;
            
            double prob = 0.0;
            // System.out.println(Arrays.toString(indexes_array));
            if(indexCombProbabilityMap.containsKey(Arrays.toString(indexes_array))) {
                prob = indexCombProbabilityMap.get(Arrays.toString(indexes_array));
            } else {
                prob = indexCombProbabilityMap.get(Arrays.toString(defaultArray));
            }
            // System.out.println("Prob is : " + prob);
            person.setPROBABILITY_OUTSIDE_LOW_SUGAR_RESTAURANT_VISIT(prob);
//            person.setPROBABILITY_OUTSIDE_LOW_SUGAR_STAY_THE_SAME_PLACE(random.nextDouble() * (1 - prob));
            person.setPROBABILITY_OUTSIDE_LOW_SUGAR_HOME_RETURN(1 - prob);
            people.add(person);
        }

        return people;
    }
    
    public static int getStringIndex(String[] array, String target) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(target)) {
                return i;
            }
        }
        return -1; // Return -1 if the target string is not found in the array
    }

    public static void main(String[] args) {
        int n = 5; // Number of random people to generate
        List<Person> people = generateRandomPersonsWithType(n, "", null);

        System.out.println("Generated Random People:");
        for (Person person : people) {
            System.out.println(person);
        }
    }
}

