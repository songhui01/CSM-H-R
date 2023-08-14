/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package context.utility.hr;

/**
 *
 * @author songhuiyue
 */
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PersonActivityGenerator002 {
    private static final int NUM_PEOPLE = 20;
    private static final int NUM_YEARS = 10;
    private static final double ACTIVITY_PROBABILITY = 0.5;

    private static final String[] LOCATIONS = {"Home", "Work", "School", "Restaurant", "Park", "Gym", "Shopping Mall"};
    private static final String[] WORK_ACTIVITIES = {"Meeting", "Coding", "Planning", "Presenting", "Researching"};
    private static final String[] STUDY_ACTIVITIES = {"Lecture", "Homework", "Reading", "Research", "Group Study"};
    private static final String[] EXERCISE_ACTIVITIES = {"Running", "Weightlifting", "Yoga", "Cycling", "Swimming"};
    private static final String[] SOCIAL_ACTIVITIES = {"Meeting Friends", "Attending Party", "Dinner Out", "Movie Night", "Concert"};
    private static final String[] RELAXATION_ACTIVITIES = {"Reading", "Watching TV", "Listening to Music", "Meditation", "Spa"};
    private static final String[] HOME_ACTIVITIES = {"Cooking", "Cleaning", "Watching TV", "Reading", "Playing Games"};

    private static final Random random = new Random();
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
//    public static List<PersonActivity> generatePersonActivities(int numPeople, int numYears) {
//        List<PersonActivity> personActivities = new ArrayList<>();
//        List<PersonGenerator.Person> people = PersonGenerator.generateRandomPeople(numPeople);
//
//        LocalDate startDate = LocalDate.now().minusYears(numYears);
//        LocalDate endDate = LocalDate.now();
//
//        for (PersonGenerator.Person person : people) {
//            LocalDateTime currentTimestamp = LocalDateTime.of(startDate, LocalTime.MIN);
//
//            while (currentTimestamp.isBefore(LocalDateTime.of(endDate, LocalTime.MAX))) {
//                if (random.nextDouble() < ACTIVITY_PROBABILITY) {
//                    String location = getRandomLocation();
//                    String activity = getRandomActivity(person.getAge());
//                    PersonActivity personActivity = new PersonActivity(person.getFullName(), currentTimestamp, location, activity);
//                    personActivities.add(personActivity);
//                }
//
//                currentTimestamp = currentTimestamp.plusHours(2); // Go to another place every 2 hours
//                updatePersonConditions(person);
//            }
//        }
//
//        return personActivities;
//    }
//
//    private static String getRandomLocation() {
//        return LOCATIONS[random.nextInt(LOCATIONS.length)];
//    }
//
//    private static String getRandomActivity(int age) {
//        int ageGroup = determineAgeGroup(age);
//
//        switch (ageGroup) {
//            case 0: // Child
//                return STUDY_ACTIVITIES[random.nextInt(STUDY_ACTIVITIES.length)];
//            case 1: // Teenager
//                return SOCIAL_ACTIVITIES[random.nextInt(SOCIAL_ACTIVITIES.length)];
//            case 2: // Adult
//                return getRandomAdultActivity();
//            case 3: // Senior
//                return RELAXATION_ACTIVITIES[random.nextInt(RELAXATION_ACTIVITIES.length)];
//            default:
//                return "";
//        }
//    }
//
//    private static int determineAgeGroup(int age) {
//        if (age <= 12) {
//            return 0; // Child
//        } else if (age <= 19) {
//            return 1; // Teenager
//        } else if (age <= 64) {
//            return 2; // Adult
//        } else {
//            return 3; // Senior
//        }
//    }
//
//    private static String getRandomAdultActivity() {
//        int randomValue = random.nextInt(100);
//
//        if (randomValue < 20) {
//            return WORK_ACTIVITIES[random.nextInt(WORK_ACTIVITIES.length)];
//        } else if (randomValue < 40) {
//            return SOCIAL_ACTIVITIES[random.nextInt(SOCIAL_ACTIVITIES.length)];
//        } else if (randomValue < 60) {
//            return EXERCISE_ACTIVITIES[random.nextInt(EXERCISE_ACTIVITIES.length)];
//        } else {
//            return RELAXATION_ACTIVITIES[random.nextInt(RELAXATION_ACTIVITIES.length)];
//        }
//    }
//
//    private static void updatePersonConditions(PersonGenerator.Person person) {
//        updateBloodSugarCondition(person);
//        updateEmotionCondition(person);
//    }
//
//    private static void updateBloodSugarCondition(PersonGenerator.Person person) {
//        double currentBloodSugar = person.getBloodSugarLevel();
//
//        if (currentBloodSugar < 70) {
//            if (!person.isInRestaurant()) {
//                person.setInRestaurant(random.nextDouble() < 0.6); // 60% possibility of going to a restaurant
//                person.setBloodSugarCondition("High");
//            }
//        } else {
//            person.setInRestaurant(false);
//            person.setBloodSugarCondition("Normal");
//        }
//    }
//
//    private static void updateEmotionCondition(PersonGenerator.Person person) {
//        double currentEmotionLevel = person.getEmotionLevel();
//
//        if (random.nextDouble() < 0.3) { // 30% possibility of emotion change
//            double emotionChange = random.nextDouble() * 20 - 10; // Random change between -10 to +10
//            double newEmotionLevel = Math.max(0, Math.min(100, currentEmotionLevel + emotionChange));
//            person.setEmotionLevel(newEmotionLevel);
//        }
//    }
//
//    public static void main(String[] args) {
//        int numPeople = NUM_PEOPLE;
//        int numYears = NUM_YEARS;
//
//        List<PersonActivity> personActivities = generatePersonActivities(numPeople, numYears);
//
//        System.out.println("Generated Person Activities:");
//        for (PersonActivity personActivity : personActivities) {
//            System.out.println(personActivity);
//        }
//    }
}

