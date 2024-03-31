/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package context.core.granularity;

/**
 *
 * @author songhuiyue
 * 
 * Basic unit is 1 centimeter.
 * Conversion is needed for special calculations.
 */
public class LocationGranularity {
    
    public static final int CENTIMETER = 1;
    public static final int METER_1 = 1000;
    public static final int METER_10 = 10000;
    
    //
    public static final String BUILDING = "building";
    public static final String DISTRICT = "disrict";
    public static final String TOWN = "town";
    public static final String CITY = "city";
    public static final String COUNTY = "county";
    public static final String STATE = "state";
    public static final String COUNTRY = "country";
    public static final String CONTINENT = "Continent";

    private LocationGranularity() {
    }

    public static void doSomething() {
        // Utility method
    }
}
