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
    public static final int SECOND = 1;
    public static final int SECOND_10 = 10;
    public static final int MINUTE_1 = 60;
    public static final int MINUTE_5 = 300;
    public static final int MINUTE_10 = 600;
    public static final int MINUTE_30 = 1800;
    public static final int DAY_1 = 86400;
    public static final int DAY_2 = 172800;
    public static final int DAY_10 = 864000;
    public static final int DAY_15 = 1296000;
    public static final int MONTH_1 = 2630000; //approximation
    public static final int MONTH_3 = 7890000; //approximation
    public static final int MONTH_6 = 15780000; //approximation
    public static final int YEAR_1 = 31560000; //approximation
    public static final int YEAR_2 = 63120000; //approximation
    public static final int YEAR_10 = 315600000; //approximation
    
    public int calculated_priority_1 = 0; // this is for recording a special granularity that matters significantly in a problem domain after learning.
    
    private TimeGranularity() {
    }

    public static void doSomething() {
        // Utility method
    }
}
