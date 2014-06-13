package edu.tmc.uth.teo.model;

import edu.tmc.uth.teo.queryIF.Unit;
import edu.tmc.uth.teo.utils.DurationParser;

/**
 * 1. We put constraints on the Constructor so that we can only have valid instances of Duration.
 * 
 * @author yluo
 *
 */
public class Duration extends TEOClass implements Comparable<Duration> {
	private AssemblyMethod assemblyMethod;
	
	private Unit durUnit; // display unit
	private String durStr; // display string
	
	private DurationValue durValue; // in milliseconds, should be computed.
	
	
	/**
	 * Constructor 1
	 */
	public Duration() {
		this.durStr = null;
		this.durUnit = Unit.UNKNOWN;
		this.durValue = null;
		this.assemblyMethod = AssemblyMethod.UNKNOWN;
	}
	
	/**
	 * Constructor 2, durValue is computed from durStr and durUnit
	 */
	public Duration(String durStr, Unit durUnit) {
		this.durStr = durStr;
		this.durUnit = durUnit;
		this.durValue = getDurValueFromStr(durStr, durUnit);
		this.assemblyMethod = AssemblyMethod.ASSERTED;
	}
	
	/**
	 * Constructor 3
	 */
	public Duration(DurationValue durValue) {
		this.durStr = null;
		this.durUnit = Unit.UNKNOWN;
		this.durValue = durValue;
		this.assemblyMethod = AssemblyMethod.INFERRED;
	}
	
	public void reset(String durStr, Unit durUnit) {
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
	
	public DurationValue getDurationValue() {
		return this.durValue;
	}
	
	public AssemblyMethod getAssemblyMethod() {
		return this.assemblyMethod;
	}
	
	public String toString() {
		return ""  + ((this.durUnit != null)? ("{Dur. unit:" + this.durUnit + "}"):"") +
				"{Dur. value:" + this.durStr + "}" ;
	}
	
	public int compareTo(Duration other) {
		return this.durValue.compareTo(other.durValue);
	}

	/**
	 * 
	 * @param durStr
	 * @param durUnit
	 * @return
	 */
	public static DurationValue getDurValueFromStr(String durStr, Unit durUnit) {
		DurationValue durValue = DurationParser.parseDuration(durStr);
		if (durValue != null) {
			durValue.supressUnitsLowerthan(durUnit);
		}
		return durValue; // might be null
	}
}
