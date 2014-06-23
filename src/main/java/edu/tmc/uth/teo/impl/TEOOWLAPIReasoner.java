package edu.tmc.uth.teo.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Vector;

import edu.tmc.uth.teo.interfaces.TEOReasoner;
import edu.tmc.uth.teo.model.Event;
import edu.tmc.uth.teo.model.TemporalRegion;
import edu.tmc.uth.teo.model.TemporalRelation;
import edu.tmc.uth.teo.model.TemporalRelationType;
import edu.tmc.uth.teo.model.TemporalType;
import edu.tmc.uth.teo.model.TimeInstant;
import edu.tmc.uth.teo.model.TimeInterval;
import edu.tmc.uth.teo.utils.TimeUtils;

public class TEOOWLAPIReasoner implements TEOReasoner {
	private HashMap<String, Event> eventMap = null;
	
	private Vector<Event> validTimeEvent = null; // events that contain validTime and can be used for inference
	private Vector<Event> visitedEvent = null; // events that contain validTime and have been visited/used for inference
	
	public TEOOWLAPIReasoner(HashMap<String, Event> eventMap) {
		this.eventMap = eventMap;
	}
	
	public HashMap<String, Event> getEventMap() {
		return this.eventMap;
	}
	
	/**
	 * Note: currently, we assume temporal relations only happen between Events.
	 */
	public boolean reasonValidTime() {
		if (eventMap != null) {
			// 1. pin all events which have valid time onto the time line (put them in a Set "validTimeEvent")
			Iterator<Entry<String, Event>> it =eventMap.entrySet().iterator();
			if (it != null && it.hasNext()) validTimeEvent = new Vector<Event>();
			while (it.hasNext()) {
				Event crtEvent = it.next().getValue();
				if (crtEvent.getValidTime() != null)
					validTimeEvent.add(crtEvent);
			}
			if (validTimeEvent != null) visitedEvent = new Vector<Event>();
			
			// 2. iteration: reason validTimes for other events
			while (validTimeEvent != null && !validTimeEvent.isEmpty()) {
				Event startEvent = validTimeEvent.firstElement();
				TimeInstant startTime = null;
				if (startEvent.getEventType().equals(TemporalType.TIMEINSTANT)) {
					startTime = (TimeInstant) startEvent.getValidTime();
				} else if (startEvent.getEventType().equals(TemporalType.TIMEINTERVAL)) {
					startTime = ((TimeInterval) startEvent.getValidTime()).getStartTime();
				}
				if (startTime != null) {
					Vector<TemporalRelation> relations = startEvent.getTemporalRelations();
					for (TemporalRelation relation : relations) {// Assumption: the target IRI must be an Event
						if (relation.getTimeOffset() != null) { // before or after
							Event targetEvent = eventMap.get(relation.getTargetIRI());
							if (!visitedEvent.contains(targetEvent)) { // then we can add new validTime info for this targetEvent
								TemporalRegion validTime = null;
								if (targetEvent.getEventType().equals(TemporalType.TIMEINTERVAL)) {
									//TODO: for time interval 
									
								} else { // Unknown or timeInstant -> timeInstant
									if (targetEvent.getEventType().equals(TemporalType.UNKNOWN)) {
										targetEvent.setEventType(TemporalType.TIMEINSTANT);
									}
									if (targetEvent.getValidTime() != null) {
										System.err.println("Error: deteced duplicate infered results for a sigle Event - " + targetEvent);
									} else {
										if (relation.getRelationType().equals(TemporalRelationType.BEFORE)) {
											validTime = TimeUtils.getEndTimeInstantFrom(startTime, relation.getTimeOffset(), null);
										} else if (relation.getRelationType().equals(TemporalRelationType.AFTER)) {
											validTime = TimeUtils.getStartTimeInstantFrom(relation.getTimeOffset(), startTime, null);
										}
										targetEvent.setValidTime(validTime);
										validTimeEvent.add(targetEvent);
									}
								}
							}
						}
					}
				}
				visitedEvent.add(startEvent);
				validTimeEvent.remove(startEvent);
			}
			
			return true;
		}
		return false;
	}

}
