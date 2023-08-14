/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package context.utility.hr;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 *
 * @author songhuiyue
 */

public class StoreNameGenerator {
    private static final String[] PREFIXES = {"ABC", "XYZ", "Best", "Super", "Happy", "Green", "Quick", "Sun", "Moon", "Star", "Golden", "Silver", "Rainbow", "Fantastic", "Magic", "Royal", "Crystal", "Deluxe", "Elegant", "Mega"};
    private static final String[] SUFFIXES = {"Mart", "Store", "Shop", "Outlet", "Center", "Market", "Place", "Square", "Emporium", "Bazaar", "Gallery", "Depot", "Corner", "Junction", "Plaza", "Palace", "Tower", "Hub", "Zone", "Complex"};

    public static List<String> generateUniqueStoreNames(int n) {
        List<String> storeNames = new ArrayList<>();
        Set<String> generatedNames = new HashSet<>();
        Random random = new Random();

        while (storeNames.size() < n) {
            String prefix = PREFIXES[random.nextInt(PREFIXES.length)];
            String suffix = SUFFIXES[random.nextInt(SUFFIXES.length)];
            String storeName = prefix + " " + suffix;

            if (!generatedNames.contains(storeName)) {
                storeNames.add(storeName);
                generatedNames.add(storeName);
            }
        }

        return storeNames;
    }

    public static void main(String[] args) {
        int n = 10; // Number of stores to generate
        List<String> storeNames = generateUniqueStoreNames(n);

        System.out.println("Generated Store Names:");
        for (String storeName : storeNames) {
            System.out.println(storeName);
        }
    }
}
