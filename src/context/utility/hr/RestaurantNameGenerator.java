/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package context.utility.hr;

/**
 *
 * @author songhuiyue
 */
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class RestaurantNameGenerator {
    private static final String[] PREFIXES = {"Delicious", "Tasty", "Yummy", "Gourmet", "Savory", "Flavorful", "Exquisite", "Delectable", "Scrumptious", "Mouthwatering"};
    private static final String[] SUFFIXES = {"Cuisine", "Bistro", "Kitchen", "Grill", "Diner", "Cafe", "Eatery", "Restaurant", "Barbecue", "Tavern"};

    public static List<String> generateUniqueRestaurantNames(int n) {
        List<String> restaurantNames = new ArrayList<>();
        Set<String> generatedNames = new HashSet<>();
        Random random = new Random();

        while (restaurantNames.size() < n) {
            String prefix = PREFIXES[random.nextInt(PREFIXES.length)];
            String suffix = SUFFIXES[random.nextInt(SUFFIXES.length)];
            String restaurantName = prefix + " " + suffix;

            if (!generatedNames.contains(restaurantName)) {
                restaurantNames.add(restaurantName);
                generatedNames.add(restaurantName);
            }
        }

        return restaurantNames;
    }

    public static void main(String[] args) {
        int n = 10; // Number of unique restaurant names to generate
        List<String> restaurantNames = generateUniqueRestaurantNames(n);

        System.out.println("Generated Unique Restaurant Names:");
        for (String restaurantName : restaurantNames) {
            System.out.println(restaurantName);
        }
    }
}

