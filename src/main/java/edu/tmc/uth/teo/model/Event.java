package edu.tmc.uth.teo.model;


/**
 * 
 * Note:
 * 1. Future improvement, an Event can have multiple validTime (from different sentences, we have to check the validation in advance);
 * 
 * 
 * @author yluo
 *
 */
public class Event extends TEOClass {
	protected TemporalType eventType; 
	private TemporalRegion validTime; // might be TimeInsant, TimeInterval, PeriodicTimeInterval
	
	// hasTemporalRelations
	
	public Event() {
		eventType = TemporalType.UNKNOWN;
	}
	
	public Event(TemporalType type) {
		this.eventType = type;
	}
	
	public TemporalType getEventType() {
		return this.eventType;
	}
		
	public TemporalRegion getValidTime() {
		return this.validTime;
	}
	
	public void setValidTime(TemporalRegion validTime) {
		this.validTime = validTime;
	}
}
