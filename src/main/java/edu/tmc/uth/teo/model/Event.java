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
	private TemporalRegion validTime; // can be TimeInstant, TimeInterval or Periodic Time Interval
	
	// hasTemporalRelations
}
