/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package context.core.granularity;

/**
 *
 * @author songhuiyue
 * 
 * Currently, basic unit is set to 1 second.
 */
public class TimeGranularity {
    
    public static final String HOUR = "hour";
    
    public static final int SECOND_V = 1;
    public static final int SECOND_10_V = 10;
    public static final int MINUTE_1_V = 60;
    public static final int MINUTE_5_V = 300;
    public static final int MINUTE_10_V = 600;
    public static final int MINUTE_30_V = 1800;
    public static final int HOUR_1_V = 3600;
    public static final int HOUR_12_V = 43200;
    public static final int DAY_1_V = 86400;
    public static final int DAY_2_V = 172800;
    public static final int DAY_10_V = 864000;
    public static final int DAY_15_V = 1296000;
    public static final int MONTH_1_V = 2630000; //approximation
    public static final int MONTH_3_V = 7890000; //approximation
    public static final int MONTH_6_V = 15780000; //approximation
    public static final int YEAR_1_V = 31560000; //approximation
    public static final int YEAR_2_V = 63120000; //approximation
    public static final int YEAR_10_V = 315600000; //approximation
    
    public int calculated_priority_1 = 0; // this is for recording a special granularity that matters significantly in a problem domain after learning.
    
    public TimeGranularity() {
    }

    public static void doSomething() {
        // Utility method
    }
}
