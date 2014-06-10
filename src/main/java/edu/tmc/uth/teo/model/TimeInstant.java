package edu.tmc.uth.teo.model;

import edu.tmc.uth.teo.queryIF.Granularity;


public class TimeInstant extends ConnectedTemporalRegion implements Comparable<TimeInstant>{
	private String originalTime;
	private String normalizedTime;
	
	private Granularity timeUnit;

	public TimeInstant() {
		this.originalTime = null;
		this.normalizedTime = null;
	}
	
	public void setOriginalTime(String originalTime) {
		this.originalTime = originalTime;
	}
	
	public String getOriginalTime() {
		return originalTime;
	}

	public String getNormalizedTime() {
		return normalizedTime;
	}
	
	public String toString() {
		return "{" + super.toString() + 
				((this.originalTime != null)? ("{Orig:" + this.originalTime + "}"):"")  +
				((this.normalizedTime != null)? ("{Norm:" + this.normalizedTime + "}"):"")  + "}";
	}
	
	// TODO
	public int compareTo(TimeInstant other) {
		
		return 0;
	}
	
}
