package edu.tmc.uth.teo.utils;

import java.text.ParseException;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class can be replaced by the Duration class in Java 8.
 * 
 * Adapted from http://stackoverflow.com/questions/11021838/parsing-duration-string-into-milliseconds
 * 
 */
public class DurationParser {
	
	// Should be in the format of "0Y 0M 0W 0D 0H 2m 0s"
	private static Pattern p = Pattern.compile("(\\d+)Y\\s+(\\d+)M\\s+(\\d+)W\\s+(\\d+)D\\s+(\\d+)H\\s+(\\d+)m\\s+(\\d+)s");

	/**
	 *  
	 * Note: we assume 1Y = 365D, 1M = 30D, 1W = 7D, which should be refined in the future.
	 * 
	 * @throws ParseException
	 */
	public static long parseDuration(String duration) throws ParseException {
	    Matcher m = p.matcher(duration);

	    long milliseconds = 0;
	    if (m.find() && m.groupCount() == 7) {
	    	int years = Integer.parseInt(m.group(1));
	        int month = Integer.parseInt(m.group(2));
	        int week = Integer.parseInt(m.group(3));  
	        
	        int days = Integer.parseInt(m.group(4));
	        days += years * 365 + month * 30 + week * 7;
	        milliseconds += TimeUnit.MILLISECONDS.convert(days, TimeUnit.DAYS);
	        
	        int hours = Integer.parseInt(m.group(5));
	        milliseconds += TimeUnit.MILLISECONDS.convert(hours, TimeUnit.HOURS);
	        
	        int minutes = Integer.parseInt(m.group(6));
	        milliseconds += TimeUnit.MILLISECONDS.convert(minutes, TimeUnit.MINUTES);
	        
	        int seconds = Integer.parseInt(m.group(7));
	        milliseconds += TimeUnit.MILLISECONDS.convert(seconds, TimeUnit.SECONDS);
	    } else {
	        throw new ParseException("Cannot parse duration " + duration, 0);
	    }
	    return milliseconds;
	}
	
	
	public static void main(String args[]) {
		try {
			System.out.println(DurationParser.parseDuration("01Y 0M 0W 0D 0H 2m 0s"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
}