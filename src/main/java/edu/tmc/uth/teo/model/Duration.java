package edu.tmc.uth.teo.model;

import edu.tmc.uth.teo.queryIF.Granularity;
import edu.tmc.uth.teo.queryIF.Unit;

/**
 * 1. We put constraints on the Constructor so that we can only have valid instances of Duration.
 * 
 * @author yluo
 *
 */
public class Duration extends TEOClass implements Comparable<Duration> {
	private Unit durUnit; // display unit - a must field
	private String durStr; // display string - a must field
	
	private long durValue; // in milliseconds, can only be computed.
	
	/**
	 * To initialize a Duration object, the display duration string and its unit must be provided.
	 * 
	 * @param val
	 * @param unit
	 */
	public Duration(String durStr, Unit durUnit) {
		this.durStr = durStr;
		this.durUnit = durUnit;
		this.durValue = getDurValueFromStr(durStr, durUnit);
	}
	
	public void resetDurStr(String durStr, Unit durUnit) {
		this.durStr = durStr;
		this.durUnit = durUnit;
		this.durValue = getDurValueFromStr(durStr, durUnit);
	}
	
	public Unit getUnit() {
		return this.durUnit;
	}
	
	public String getDurString() {
		return this.durStr;
	}
	
	/**
	 * To get a Duration in a different Unit and keep the durValue the same.
	 * @param newGran
	 * @return
	 */
	public Duration toUnit(Unit newUnit) {
		String newDurStr = getTransferedDurStr(this.getDurString(), this.durUnit, newUnit);
		
		Duration newDuration = new Duration(newDurStr, newUnit);
		
		return newDuration;
	}
	
	// TODO
	public static String getTransferedDurStr(String oldDurStr, Unit oldUnit, Unit newUnit) {
		return null;
	}

	// TODO
	public static long getDurValueFromStr(String durStr, Unit durUnit) {
		
		return 0;
	}
	
	public String toString() {
		return ""  + ((this.durUnit != null)? ("{Dur. unit:" + this.durUnit + "}"):"") +
				"{Dur. value:" + this.durStr + "}" ;
	}
	
	public int compareTo(long otherDurValue) {
		if (this.durValue > otherDurValue) {
			return 1;
		} else if (this.durValue < otherDurValue) {
			return -1;
		} else {
			return 0;
		}
	}

	public int compareTo(Duration other) {
		if (this.durValue > other.durValue) {
			return 1;
		} else if (this.durValue < other.durValue) {
			return -1;
		} else {
			return 0;
		}
	}
}
