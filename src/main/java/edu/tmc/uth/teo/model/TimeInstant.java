package edu.tmc.uth.teo.model;

import java.util.Date;

import edu.tmc.uth.teo.queryIF.Granularity;
import edu.tmc.uth.teo.utils.DateUtils;

/**
 * 
 * @author yluo
 *
 */
public class TimeInstant extends ConnectedTemporalRegion implements Comparable<TimeInstant>{
	private AssemblyMethod assemblyMethod;
	
	private String origTime; // display value
	private long normalizedTime; // in milliseconds since January 1, 1970, 00:00:00 GMT.
	
	// hidden super class filed: Granularity timeGranularity;

	/**
	 * Constructor 1
	 */
	public TimeInstant() {
		this.origTime = null;
		this.setGranularity(new Granularity());
		this.normalizedTime = -1;
		this.assemblyMethod = AssemblyMethod.UNKNOWN;
	}
	
	/**
	 * Constructor 2, to compute normalizedTime automatically
	 */
	public TimeInstant(String origTime, Granularity gran) {
		this.origTime = origTime;
		this.setGranularity(gran);
		this.normalizedTime = getNormalizedTimeFromOrigTime(origTime, gran);
		this.assemblyMethod = AssemblyMethod.ASSERTED;
	}
	
	/**
	 * Constructor 3
	 */
	public TimeInstant(long normalizedTime) {
		this.origTime = null;
		this.setGranularity(new Granularity());
		this.normalizedTime = normalizedTime;
		this.assemblyMethod = AssemblyMethod.INFERRED;
	}
	
	public void reset(String origTime, Granularity gran) {
		this.origTime = origTime;
		this.setGranularity(gran);
		this.normalizedTime = getNormalizedTimeFromOrigTime(origTime, gran);
	}
	
	public String getOriginalTime() {
		return origTime;
	}

	public Date getNormalizedDate() {
		return new Date(normalizedTime);
	}
	
	public long getNormalizedTime() {
		return normalizedTime;
	}
	
	public AssemblyMethod getAssemblyMethod() {
		return this.assemblyMethod;
	}
		
	public String toString() {
		return "{" + super.toString() + 
				((this.origTime != null)? ("{Orig:" + this.origTime + "}"):"")  +
				((this.normalizedTime != -1)? ("{Norm:" + this.normalizedTime + "}"):"")  + "}";
	}
	
		
	public int compareTo(TimeInstant otherTimeInstant) {
		if (this.normalizedTime > otherTimeInstant.normalizedTime) {
			return 1;
		} else if (this.normalizedTime < otherTimeInstant.normalizedTime) {
			return -1;
		} else {
			return 0;
		}
	}

	
	/**
	 * Get normalized time value from the string and granularity.
	 */
	public static long getNormalizedTimeFromOrigTime(String origTimeStr, Granularity gran) {
		Date parsedDate = DateUtils.parse(origTimeStr);
		
		if (parsedDate != null) {
			Date trasferedDate = DateUtils.setGranularity(parsedDate, gran.getUnit()); // might truncate finer granularities
			return trasferedDate.getTime();
		} else {
			return -1;
		}
	}
}
