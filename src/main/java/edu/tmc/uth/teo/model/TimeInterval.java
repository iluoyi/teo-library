package edu.tmc.uth.teo.model;

import edu.tmc.uth.teo.queryIF.Granularity;


/**
 * Note: startTime, endTime and duration should be unified to the same granularity when initializing the TimeInterval.
 * 
 * 1. We put constraints on Constructors so that we can only have valid instances of TimeInterval.
 * 2. Any 2 conditions can lead to the 3rd condition, so we have 3 constructors.
 * 
 * @author yluo
 *
 */
public class TimeInterval extends ConnectedTemporalRegion {
	private TimeInstant startTime; // Yi: startTime has its own granularity
	private TimeInstant endTime; // Yi: endTime has its own granularity
	private Duration duration; // Yi: duration has its own duration unit (similar to granularity)
	
	// hidden super class filed: Granularity timeGranularity;
	
	public TimeInstant getStartTime() {
		return this.startTime;
	}
	
	public TimeInstant getEndTime() {
		return this.endTime;
	}
	
	public Duration getDuration() {
		return this.duration;
	}
	
	/**
	 * Constructor 1
	 * @param startTime
	 * @param endTime
	 */
	public TimeInterval(TimeInstant startTime, TimeInstant endTime) {
		if (startTime != null && endTime != null) {
			this.startTime = startTime; // transfer startTime to the current granularity
			this.endTime = endTime; // transfer endTime to the current granularity
			this.duration = getDurationFrom(startTime, endTime, this.getGranularity());
		}
	}
	
	/**
	 * Constructor 2
	 * @param startTime
	 * @param duration
	 */
	public TimeInterval(TimeInstant startTime, Duration duration) {
		if (startTime != null && duration != null) {
			this.startTime = startTime;
			this.duration = duration;
			this.endTime = getEndTimeInstantFrom(startTime, duration, this.getGranularity());
		}
	}
	
	/**
	 * Constructor 3
	 * @param duration
	 * @param endTime
	 */
	public TimeInterval(Duration duration, TimeInstant endTime) {
		if (startTime != null && duration != null) {
			this.endTime = endTime;
			this.duration = duration;
			this.startTime = getStartTimeInstantFrom(duration, endTime, this.getGranularity());
		}
	}
	
	
	/**
	 * This method checks if three given conditions (startTime, endTime, and duration) could lead to
	 * valid time interval or not.
	 * 
	 * 1. start <= end; 2. duration = end - start.
	 * 
	 * @param startTime
	 * @param endTime
	 * @param duration
	 * @return
	 */
	public static boolean isValidTimeInterval(TimeInstant startTime, TimeInstant endTime, Duration duration) {
		return false;
	}
	
	// TODO
	public static Duration getDurationFrom(TimeInstant startTimeInstant, TimeInstant endTimeInstant, Granularity gran) {
		return null;
	}
		
	// TODO
	public static TimeInstant getEndTimeInstantFrom(TimeInstant startTimeInstant, Duration duration, Granularity gran) {
		return null;
	}
	
	// TODO
	public static TimeInstant getStartTimeInstantFrom(Duration duration, TimeInstant endTimeInstant, Granularity gran) {
		return null;
	}
}
