package com.example.it3197_casestudy.util;

import java.util.Calendar;
import java.util.Comparator;

/**
 * This class is to compare calendar and ignores the time that goes along the calendar
 * @author Lee Zhuo Xun
 *
 */
public class TimeIgnoringComparator implements Comparator<Calendar> {
	public int compare(Calendar c1, Calendar c2) {
		if (c1.get(Calendar.YEAR) != c2.get(Calendar.YEAR)) 
			return c1.get(Calendar.YEAR) - c2.get(Calendar.YEAR);
		if (c1.get(Calendar.MONTH) != c2.get(Calendar.MONTH)) 
			return c1.get(Calendar.MONTH) - c2.get(Calendar.MONTH);
		return c1.get(Calendar.DAY_OF_MONTH) - c2.get(Calendar.DAY_OF_MONTH);
	}
}
