package edu.tmc.uth.teo.utils;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.ocpsoft.prettytime.nlp.PrettyTimeParser;

import edu.tmc.uth.teo.queryIF.Unit;

/**
 * This is a parser which combines the Rule-based method and NLP-based method.
 * 
 * @author yluo
 *
 */
public class DateParserUtil {
	
	public static Date parse(String text) {
		Date retDate = null;
			
		// Because the NLP-based method cannot handle the case when text = "2006" which represents a year, we deal with it specially
		String tempText = text;
		if (tempText.length() == 2) {
			tempText = "20" + tempText;
		}
		if (tempText.length() == 4) {
			try {
				Integer year = Integer.parseInt(tempText);
				Calendar cal = Calendar.getInstance();
				cal.set(Calendar.YEAR, year);	
				retDate = cal.getTime();
			} catch (NumberFormatException ex) {
				// do nothing
			}
		}
		
		// call the NLP-based method
		if (retDate == null) {
			List<Date> dates = new PrettyTimeParser().parse(text);
			if (dates !=null && !dates.isEmpty())
				retDate = dates.get(0);
		}
		
		// call the rule-based parser
		if (retDate == null) {
					
			if (!StringUtils.isNull(text)) 
				retDate = new DateParser().parse(text, new ParsePosition(0));
		}
		
		return retDate;
	}
	
	/**
	 * When specifying the granularity, we have to truncate the Date.
	 * 
	 * UNKNOWN, SECOND, MINUTE, HOUR, DAY, WEEK, MONTH, YEAR
	 * 
	 * @param unit
	 * @return
	 */
	public static Date setGranularity(Date givenDate, Unit unit) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(givenDate);
		
		if (unit.compareTo(Unit.MINUTE) >= 0)
			cal.set(Calendar.SECOND, 0);
		
		if (unit.compareTo(Unit.HOUR) >= 0)
			cal.set(Calendar.MINUTE, 0);
		
		if (unit.compareTo(Unit.DAY) >= 0)
			cal.set(Calendar.HOUR_OF_DAY, 0);
		
		if (unit.compareTo(Unit.WEEK) >= 0)
			cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		
		if (unit.compareTo(Unit.MONTH) >= 0)
			cal.set(Calendar.DAY_OF_MONTH, 1);
		
		if (unit.compareTo(Unit.YEAR) >= 0)
			cal.set(Calendar.DAY_OF_YEAR, 1);		
		
		Date date = cal.getTime();
		
		return date;
	}
	
	public static void main(String args[]) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		//Date date = DateParserUtil.parse("21:25:53 05/02/2014");
		Date date = DateParserUtil.parse("14");
		
		
		System.out.println(sf.format(date));
		System.out.println();
		System.out.println("SECOND = " + sf.format(DateParserUtil.setGranularity(date, Unit.SECOND)) + " value = " + DateParserUtil.setGranularity(date, Unit.SECOND).getTime());
		System.out.println("MINUTE = " + sf.format(DateParserUtil.setGranularity(date, Unit.MINUTE)) + " value = " + DateParserUtil.setGranularity(date, Unit.MINUTE).getTime());
		System.out.println("HOUR = " + sf.format(DateParserUtil.setGranularity(date, Unit.HOUR)) + " value = " + DateParserUtil.setGranularity(date, Unit.HOUR).getTime());
		System.out.println("DAY = " + sf.format(DateParserUtil.setGranularity(date, Unit.DAY)) + " value = " + DateParserUtil.setGranularity(date, Unit.DAY).getTime());
		System.out.println("WEEK = " + sf.format(DateParserUtil.setGranularity(date, Unit.WEEK)) + " value = " + DateParserUtil.setGranularity(date, Unit.WEEK).getTime());
		System.out.println("MONTH = " + sf.format(DateParserUtil.setGranularity(date, Unit.MONTH)) + " value = " + DateParserUtil.setGranularity(date, Unit.MONTH).getTime());
		System.out.println("YEAR = " + sf.format(DateParserUtil.setGranularity(date, Unit.YEAR)) + " value = " + DateParserUtil.setGranularity(date, Unit.YEAR).getTime());
	
	}
	
}
