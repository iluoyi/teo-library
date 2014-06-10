package edu.tmc.uth.teo.model;

import edu.tmc.uth.teo.queryIF.Granularity;

public class Duration extends TEOClass implements Comparable<Duration> {
	private Granularity durationUnit;
	private String durationValue; // should be unified for comparison?
	
	public Duration() {
		this.durationValue = null;
		this.durationUnit = Granularity.UNKNOWN;
	}
	
	public Duration(String val, Granularity gran) {
		this.durationValue = val;
		this.durationUnit = gran;
	}
	
	public String toString() {
		return ""  + ((this.durationUnit != null)? ("{Dur. unit:" + this.durationUnit + "}"):"") +
				"{Dur. value:" + this.durationValue + "}" ;
	}
	
	public boolean equals() {
		return false;
	}
	
	// TODO
	public static Duration getDurationFromStartEndTime(TimeInstant startTime, TimeInstant endTime) {
		Duration duration = new Duration();
		
		return duration;
	}

	// TODO
	public int compareTo(Duration other) {
		// 1. unify the granularity to the finer one
		
		// 2. transfer the duration value and compare
		
		return 0;
	}
}
