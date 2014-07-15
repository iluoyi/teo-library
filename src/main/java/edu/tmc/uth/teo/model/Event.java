package edu.tmc.uth.teo.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;


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
	private HashMap<String, ArrayList<TemporalRelationHalf>> relationMap; // HashMap<targetIRI, relation list>: 1. better for retrieval; 2. compress the used memory
	
	public Event() {
		this.eventType = TemporalType.UNKNOWN;
		this.validTime = null;
		this.relationMap = null;
	}
	
	public Event(TemporalType type) {
		this.eventType = type;
		this.validTime = null;
		this.relationMap = null;
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
	
	public HashMap<String, ArrayList<TemporalRelationHalf>> getTemporalRelations() {
		return this.relationMap;
	}
	
	public void addTemporalRelation(String targetIRI, TemporalRelationHalf relation) {
		if (relationMap == null) {
			relationMap = new HashMap<String, ArrayList<TemporalRelationHalf>>();
		} 
		if (relationMap.get(targetIRI) == null) {
			relationMap.put(targetIRI, new ArrayList<TemporalRelationHalf>());
		}
		if (!relationMap.get(targetIRI).contains(relation)) {
			relationMap.get(targetIRI).add(relation);
		}
	}
	
	public String printRelations() {
		if (relationMap != null) {
			StringBuffer buf = null;
			buf = new StringBuffer("{\n");
			Iterator<Entry<String, ArrayList<TemporalRelationHalf>>> it = relationMap.entrySet().iterator();
			
			while (it.hasNext()) {
				Entry<String, ArrayList<TemporalRelationHalf>> entry = it.next();
				ArrayList<TemporalRelationHalf> relationList = entry.getValue();
				for (TemporalRelationHalf oneRelation : relationList) {
					buf.append(oneRelation + "->" + entry.getKey() + "\n");
				}
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
