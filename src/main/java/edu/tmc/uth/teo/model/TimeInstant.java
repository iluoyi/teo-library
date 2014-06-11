package edu.tmc.uth.teo.model;

import java.sql.Time;

import edu.tmc.uth.teo.queryIF.Granularity;

/**
 * 
 * 1. We put constraints on the Constructor so that we can only have valid instances of TimeInstant.
 * 
 * @author yluo
 *
 */
public class TimeInstant extends ConnectedTemporalRegion implements Comparable<TimeInstant>{
	private String origTime; // display value - a must field
	private long normalizedTime; // in milliseconds since January 1, 1970, 00:00:00 GMT, can only be computed.
	
	// hidden super class filed: Granularity timeGranularity;

	/**
	 * To initialize a Duration object, the original time string and its granularity must be provided.
	 * 
	 * @param val
	 * @param unit
	 */
	public TimeInstant(String origTime, Granularity gran) {
		this.origTime = origTime;
		this.setGranularity(gran);
		this.normalizedTime = getNormalizedTimeFromOrigTime(origTime, gran);
	}
	
	public void resetOriginalTime(String origTime, Granularity gran) {
		this.origTime = origTime;
		this.setGranularity(gran);
		this.normalizedTime = getNormalizedTimeFromOrigTime(origTime, gran);
	}
	
	public String getOriginalTime() {
		return origTime;
	}

	public Time getNormalizedTime() {
		return new Time(normalizedTime);
	}
	
	/**
	 * To get a TimeInstant in a different granularity and keep the normalizedTime the same.
	 * @param newGran
	 * @return
	 */
	public TimeInstant toGranularity(Granularity newGran) {
		String newTimeStr = getTransferedTime(this.getOriginalTime(), this.getGranularity(), newGran);
		
		TimeInstant newTimeInstant = new TimeInstant(newTimeStr, newGran);
		
		return newTimeInstant;
	}
	
	// TODO
	public static String getTransferedTime(String oldTimeStr, Granularity oldGran, Granularity newGran) {
		return null;
	}
	
	// TODO
	public static long getNormalizedTimeFromOrigTime(String origTime, Granularity gran) {

		return 0;
	}
	
	public String toString() {
		return "{" + super.toString() + 
				((this.origTime != null)? ("{Orig:" + this.origTime + "}"):"")  +
				((this.normalizedTime != -1)? ("{Norm:" + this.normalizedTime + "}"):"")  + "}";
	}
	
	
	public int compareTo(long otherTime) {
		if (this.normalizedTime > normalizedTime) {
			return 1;
		} else if (this.normalizedTime < normalizedTime) {
			return -1;
		} else {
			return 0;
		}
	}
	
	public int compareTo(TimeInstant other) {
		if (this.normalizedTime > other.normalizedTime) {
			return 1;
		} else if (this.normalizedTime < other.normalizedTime) {
			return -1;
		} else {
			return 0;
		}
	}
	
}
