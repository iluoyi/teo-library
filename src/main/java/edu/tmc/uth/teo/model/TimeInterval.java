package edu.tmc.uth.teo.model;

public class TimeInterval {
	private TimeInstant startTime;
	private TimeInstant endTime;
	private Duration duration;
	
	private boolean isValid; // 1. start <= end; 2. duration = end - start.
	
	public boolean isValidOrNot() {
		return this.isValid;
	}
	
	public TimeInstant getStartTime() {
		return this.startTime;
	}
	
	public TimeInstant getEndTime() {
		return this.endTime;
	}
	
	public Duration getDuration() {
		return this.duration;
	}
	
	public TimeInterval()  {
		this.startTime = null;
		this.endTime = null;
		this.duration = null;
		this.isValid = false;
	}
	
	public TimeInterval(TimeInstant startTime, TimeInstant endTime, Duration duration) {
		if (startTime != null && endTime != null && duration != null) {
			this.startTime = startTime;
			this.endTime = endTime;
			this.duration = duration;
			checkValidation();
		}
	}
		
	public TimeInterval(TimeInstant startTime, TimeInstant endTime) {
		if (startTime != null && endTime != null) {
			this.startTime = startTime;
			this.endTime = endTime;
			
			if (startTime.compareTo(endTime) > 0) {
				this.isValid = false;
				this.duration = null;
			} else {
				this.isValid = true;
				this.duration = Duration.getDurationFromStartEndTime(startTime, endTime);
			}
		}
	}
	
	public TimeInterval(TimeInstant startTime, Duration duration) {
		if (startTime != null && duration != null) {
			this.startTime = startTime;
			this.duration = duration;
			this.endTime = getEndTimeInstantFrom(this.startTime, this.duration);
			this.isValid = true;
		}
	}
	
	public TimeInterval(Duration duration, TimeInstant endTime) {
		if (startTime != null && duration != null) {
			this.endTime = startTime;
			this.duration = duration;
			this.startTime = getStartTimeInstantFrom(this.endTime, this.duration);
			this.isValid = true;
		}
	}
	
	// TODO
	public void checkValidation() {
		this.isValid = true;
		if (startTime.compareTo(endTime) > 0) {
			this.isValid = false;
		} else {
			Duration calDuration = Duration.getDurationFromStartEndTime(startTime, endTime);
			if (this.duration.compareTo(calDuration) != 0) {
				this.isValid = false;
			}
		}
	}
		
	// TODO
	public static TimeInstant getEndTimeInstantFrom(TimeInstant startTimeInstant, Duration duration) {
		return null;
	}
	
	// TODO
	public static TimeInstant getStartTimeInstantFrom(TimeInstant endTimeInstant, Duration duration) {
		return null;
	}
}
