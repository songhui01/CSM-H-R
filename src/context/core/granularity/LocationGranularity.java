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
    public static final String Building = "building";
    public static final String District = "disrict";
    public static final String Town = "town";
    public static final String City = "city";
    public static final String County = "county";
    public static final String State = "state";
    public static final String Country = "country";
    public static final String Continent = "Continent";

    private LocationGranularity() {
    }

    public static void doSomething() {
        // Utility method
    }
}
