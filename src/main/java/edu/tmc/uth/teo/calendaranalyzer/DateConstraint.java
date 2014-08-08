package edu.tmc.uth.teo.calendaranalyzer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class DateConstraint {
	public static final int MAX_YEAR = 2020;
	public static final int MIN_YEAR = 1970;
	private int minYear = MIN_YEAR, maxYear = MAX_YEAR; // assumption: minYear = 1970, maxYear = 2020
	private Set<Integer> monthDay;
	private Set<Integer> weekDay;
	private Set<Integer> monthWeek;
	private Set<Integer> month;
	
	public int getMinYear() {
		return minYear;
	}
	public void setMinYear(int minYear) {
		if (minYear >= MIN_YEAR && minYear <= maxYear)
			this.minYear = minYear;
	}
	
	public int getMaxYear() {
		return maxYear;
	}
	public void setMaxYear(int maxYear) {
		if (maxYear <= MAX_YEAR && maxYear >= minYear)
			this.maxYear = maxYear;
	}
	
	public Set<Integer> getMonthDay() {
		return monthDay;
	}
	public void setMonthDay(Set<Integer> monthDay) {
		this.monthDay = monthDay;
	}
	public void addMonthDay(int i) {
		assert(i >= 1 && i <= 31);
		if (monthDay == null)
			monthDay = new HashSet<Integer>();
		monthDay.add(i);
	}
	

	public Set<Integer> getWeekDay() {
		return weekDay;
	}
	public void setWeekDay(Set<Integer> weekDay) {
		this.weekDay = weekDay;
	}
	public void addWeekDay(int i) {
		assert(i >= Calendar.SUNDAY && i <= Calendar.SATURDAY);
		if (weekDay == null)
			weekDay = new HashSet<Integer>();
		weekDay.add(i);
	}
	

	public Set<Integer> getMonthWeek() {
		return monthWeek;
	}
	public void setMonthWeek(Set<Integer> monthWeek) {
		this.monthWeek = monthWeek;
	}
	public void addMonthWeek(int i) {
		assert(i >= 1 && i <= 5);
		if (monthWeek == null)
			monthWeek = new HashSet<Integer>();
		monthWeek.add(i);
	}
	
	public Set<Integer> getMonth() {
		return month;
	}
	public void setMonth(Set<Integer> month) {
		this.month = month;
	}
	public void addMonth(int i) {
		assert(i >= Calendar.JANUARY && i <= Calendar.DECEMBER);
		if (month == null)
			month = new HashSet<Integer>();
		month.add(i);
	}
	
	public String diplaySetElements(Set<Integer> set) {
		String result = "";
		if (set != null && !set.isEmpty()) {
			Integer[] setArray = set.toArray(new Integer[set.size()]);
			result += setArray[0];
			for (int i = 1; i < setArray.length; i ++) {
				result += (", " + setArray[i]);
			}
		}
		return result;
	}
	
	public String toString() {
		String result = "";
		result += "{[Year: (" + minYear + ", " + maxYear + ")]\n";
		result += "[Month: (" + diplaySetElements(month) + ")]\n";
		result += "[MonthDay: (" + diplaySetElements(monthDay) + ")]\n";
		result += "[Week: (" + diplaySetElements(monthWeek) + ")]\n";
		result += "[WeekDay: (" + diplaySetElements(weekDay) + ")]}";
		return result;
	}
	
	
	
	public static ArrayList<String> enumerateDates(DateConstraint dateConstraint) {
		// TODO: all kinds of constraints/boundaries!
		SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyy");
		ArrayList<String> dates = new ArrayList<String>(); 
		
		Calendar newDate = Calendar.getInstance();
		for (int year = dateConstraint.getMinYear(); year <= dateConstraint.getMaxYear(); year ++) {
			System.out.println("year - " + year);
			newDate.set(Calendar.YEAR, year);
			for (Integer month : dateConstraint.getMonth()) {
				System.out.println("month - " + month);
				newDate.set(Calendar.MONTH, month);
				// TODO: fix the bug, should check!
				for (Integer monthDay : dateConstraint.getMonthDay()) {
					System.out.println("day - " + monthDay);
					newDate.set(Calendar.DAY_OF_MONTH, monthDay);
					// eject the date
					dates.add(df.format(newDate.getTime()));
				}
			}
		}
		
		return dates;
	}
}
