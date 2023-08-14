package context.utility;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * This class is used by RandomGenerator to generate random date after a timestamp
 * @author syue2
 *
 */
public class TimeHelper {
	private String initalDate="17-Nov-18 02:10:15";
	
	TimeHelper(){	}
	
	TimeHelper(String date){
		this.initalDate = date;
	}
	
	public String getNextDate() throws ParseException{
        DateFormat formatter = new SimpleDateFormat("dd-MMM-yy HH:mm:ss");
        Calendar cal=Calendar.getInstance();
		cal.setTime(formatter.parse(initalDate));
        Long value1 = cal.getTimeInMillis();
        Long value2 = value1 + (long)(1800000*(1+Math.random()));
        cal.setTimeInMillis(value2);
        initalDate = formatter.format(cal.getTime());
        return initalDate;
	}

}
