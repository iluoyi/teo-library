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
	private TemporalRegion validTime; // can be TimeInstant, TimeInterval or PeriodicTimeInterval
	private int eventType; // TimeInstant = 0, TimeInterval = 1 or PeriodicTimeInterval = 2
	
	// hasTemporalRelations
	
	public Event() {
		validTime = null;
		eventType = -1;
	}
	
	public String getEventType() {
		switch (this.eventType) {
		case 0: return "TIMEINSTANT";
		case 1: return "TIMEINTERVAL";
		case 2: return "PERIODICTIMEINTERVAL";
		default: return "UNKNOWN";
		}
	}
	
	
}
