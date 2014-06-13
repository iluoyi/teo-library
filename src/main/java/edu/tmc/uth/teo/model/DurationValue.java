package edu.tmc.uth.teo.model;

import edu.tmc.uth.teo.queryIF.Unit;

/**
 * JavaBean for DurationValue
 * @author yluo
 *
 */
public class DurationValue implements Comparable<DurationValue> {

	private int year, month, week, day, hour, minute, second;
	
	public DurationValue() {
		year = 0;
		month = 0;
		week = 0;
		day = 0;
		hour = 0;
		minute = 0;
		second = 0;
	}
	
	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getWeek() {
		return week;
	}

	public void setWeek(int week) {
		this.week = week;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getMinute() {
		return minute;
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}

	public int getSecond() {
		return second;
	}

	public void setSecond(int second) {
		this.second = second;
	}
	
	public String toString() {
		return year + "Y" + month + "M" + week + "W" + day + "D" + hour + "H" + minute + "m" + second + "s";
	}
	
	/**
	 * @param unit
	 */
	public void changeGranularity(Unit unit) {
		if (unit.compareTo(Unit.MINUTE) > 0) {
			this.second = 0;
		}
		if (unit.compareTo(Unit.HOUR) > 0) {
			this.minute = 0;
		}
		if (unit.compareTo(Unit.DAY) > 0) {
			this.hour = 0;
		}
		if (unit.compareTo(Unit.WEEK) > 0) {
			this.day = 0;
		}
		if (unit.compareTo(Unit.MONTH) > 0) {
			this.week = 0;
		}
		if (unit.compareTo(Unit.YEAR) > 0) {
			this.month = 0;
		}
	}

	/**
	 * Note: we have simple assumptions here.
	 *       1 year = 365 days, 1 month = 30 days, 1 week = 7 days.
	 */
	public int compareTo(DurationValue otherDur) {
		long second1 = this.year * 365 * 24 * 60 * 60 + this.month * 30 * 24 * 60 * 60 + this.week * 7 * 24 * 60 * 60 +
						this.day * 24 * 60 * 60 + this.hour * 60 * 60 + this.minute * 60 + this.second;
		long second2 = this.year * 365 * 24 * 60 * 60 + this.month * 30 * 24 * 60 * 60 + this.week * 7 * 24 * 60 * 60 +
						this.day * 24 * 60 * 60 + this.hour * 60 * 60 + this.minute * 60 + this.second;
		if (second1 > second2) {
			return 1;
		} else if (second1 < second2) {
			return -1;
		} else {
			return 0;
		}	
	}
}
