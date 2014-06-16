package edu.tmc.uth.teo.model;

import edu.tmc.uth.teo.queryIF.Granularity;
import edu.tmc.uth.teo.queryIF.Unit;
import edu.tmc.uth.teo.utils.DurationUtils;
import edu.tmc.uth.teo.utils.TimeUtils;


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
	private TimeInstant startTime; // startTime has its own granularity
	private TimeInstant endTime; // endTime has its own granularity
	private Duration duration; // duration has its own duration unit (similar to granularity)
	
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
			this.startTime = startTime;
			this.endTime = endTime;
			this.setGranularity(TimeUtils.getCoarserGranularity(startTime, endTime));
			this.duration = TimeUtils.getDurationFrom(startTime, endTime, this.getGranularity()); // get the duration according to the current granularity
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
			this.setUnit(TimeUtils.getFinerUnit(startTime, duration));
			this.endTime = TimeUtils.getEndTimeInstantFrom(startTime, duration, this.getGranularity());
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
			this.setUnit(TimeUtils.getFinerUnit(startTime, duration));
			this.startTime = TimeUtils.getStartTimeInstantFrom(duration, endTime, this.getGranularity());
		}
	}
	
	/**
	 * This method checks if three given conditions (startTime, endTime, and duration) could lead to
	 * a valid time interval or not.
	 * 
	 * 1. start <= end; 2. duration = end - start.
	 * 
	 * @param startTime
	 * @param endTime
	 * @param duration
	 * @return
	 */
	public static boolean isValidTimeInterval(TimeInstant startTime, TimeInstant endTime, Duration duration) {
		if (startTime != null && endTime != null && duration != null) {
			if (startTime.compareTo(endTime) <= 0) { // 1. start <= end
				Granularity corserGran = TimeUtils.getCoarserGranularity(startTime, endTime);
				Unit maxUnit = corserGran.getUnit().compareTo(duration.getUnit()) > 0 ? corserGran.getUnit() : duration.getUnit();
				
				Duration computedDur = TimeUtils.getDurationFrom(startTime, endTime, new Granularity(maxUnit));
				DurationValue givenDurValue = DurationUtils.changeToUnit(duration.getDurationValue(), maxUnit);
				
				if (computedDur.getDurationValue().compareTo(givenDurValue) == 0) { // 2. duration = end - start.
					return true;
				}
			}
		}
		return false;
	}
}
