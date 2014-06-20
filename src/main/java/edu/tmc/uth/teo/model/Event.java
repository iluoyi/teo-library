package edu.tmc.uth.teo.model;

import java.util.Vector;


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
	private TemporalType eventType; 
	private TemporalRegion validTime; // might be TimeInsant, TimeInterval, PeriodicTimeInterval
	private Vector<TemporalRelation> relations;
	
	public Event() {
		this.eventType = TemporalType.UNKNOWN;
		this.validTime = null;
		this.relations = null;
	}
	
	public Event(TemporalType type) {
		this.eventType = type;
		this.validTime = null;
		this.relations = null;
	}
	
	public TemporalType getEventType() {
		return this.eventType;
	}
	
	public void setEventType(TemporalType eventType) {
		this.eventType = eventType;
	}
		
	public TemporalRegion getValidTime() {
		return this.validTime;
	}
	
	public void setValidTime(TemporalRegion validTime) {
		this.validTime = validTime;
	}
	
	public Vector<TemporalRelation> getTemporalRelations() {
		return this.relations;
	}
	
	public void addTemporalRelation(TemporalRelation oneRelation) {
		if (relations == null) {
			relations = new Vector<TemporalRelation>();
		}
		relations.add(oneRelation);
	}
	
	public String printRelations() {
		if (relations != null) {
			StringBuffer buf = null;
			buf = new StringBuffer("{\n");
			for (TemporalRelation oneRelation : relations) {
				buf.append(oneRelation.printTarget() + "\n");
			}
			buf.append("}");
			return buf.toString();
		} else {
			return null;
		}
	}
	
	public String toString() {
		return "EventIRI: " + getIRIStr() + "\nEventType: " + getEventType() + "\nhasValidTime: " + getValidTime() +
				"\nhasTemporalRelations:" + printRelations();
	}
}
