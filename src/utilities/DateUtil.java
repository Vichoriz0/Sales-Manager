package utilities;

import java.sql.Date;
import java.util.Calendar;

public class DateUtil {

	//Incrementa (o decrementa) los dias de una fecha de entrada. Extraido de https://stackoverflow.com/questions/428918/how-can-i-increment-a-date-by-one-day-in-java
	public static Date addDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); 	//Valores negativos decrementan los dias.
        return new Date(cal.getTimeInMillis());
    }
}
