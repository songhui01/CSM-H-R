/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package context.utility.hr;

import java.util.Arrays;
import java.util.stream.Stream;
/**
 *
 * @author songhuiyue
 * 
 * The locations, can be in another class
 * Both a location and an activity can be a context object, which has different attributes,
 * e.g. Home, Temperature, 78 F
 * e.g. Meeting, Length, 30 min, participants, a list of people, Host, a person
 * 
 * In this simulation/experiment, we treat most of them as normal general activities, instead of using ontology URI to independently identify an entity of activity
 * For normal activities, we tag them as 'label', for ontology activity, we give it an ontology URI, and this activity can be used in a lot of app domains
 * 
 * We have a app domain called CampusMeeting to schedule an activity of group study or sports. The scheduling will be made by a random student, and assign 
 * a random classroom at a random building of a random floor. We know or we can learn that if random floor is greater than 1, then the attendees will more likely
 * to take an elevator, suppose there is a nearby elevator from the assigned classroom, otherwise, the attendees will more likely to not take the elevators.
 * 
 * So for the CampusMeeting app, w.r.t the location info of an activity, it is better to know as specific as possible to determine the impacts of different factors.
 * e.g. building_name, has_elevator, room, room_floor
 * 
 * Suppose the professor is somewhere, then it will be different, half half chance to take the elevator.
 * 
 * We are generating data for different domains, and then we need to have a way to connect them, some algorithms to connect them to make calculating together
 */
public class ActivityCollection {
    
    protected static final String[] WORK_ACTIVITIES = {"Meeting", "Coding", "Planning", "Presenting", "Researching"};
    protected static final String[] STUDY_ACTIVITIES = {"Lecture", "Homework", "Reading", "Researching", "Group Study"};
    protected static final String[] EXERCISE_ACTIVITIES = {"Running", "Weightlifting", "Yoga", "Cycling", "Swimming"};
    protected static final String[] SOCIAL_ACTIVITIES = {"Meeting Friends", "Attending Party", "Dinner Out", "Movie Night", "Concert"};
    protected static final String[] PARK_ACTIVITIES = {"Reading", "Watching TV", "Listening to Music", "Meditation", "Spa", "Running", "Bicycling"};
    protected static final String[] HOME_ACTIVITIES = {"Cooking", "Cleaning", "Watching TV", "Reading", "Playing Games", "Sleeping"};
    
    protected static final String[] SCHOOL_ACTIVITIES = Stream.concat(Stream.concat(Arrays.stream(STUDY_ACTIVITIES), Arrays.stream(EXERCISE_ACTIVITIES)), Arrays.stream(WORK_ACTIVITIES))
                .distinct().toArray(String[]::new);

}
