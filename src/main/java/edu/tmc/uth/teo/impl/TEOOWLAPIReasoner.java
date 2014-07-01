package edu.tmc.uth.teo.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Vector;

import org.allen.temporalintervalrelationships.Constraint;
import org.allen.temporalintervalrelationships.ConstraintNetwork;
import org.allen.temporalintervalrelationships.Node;

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
			Iterator<Entry<String, Event>> it = eventMap.entrySet().iterator();
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

	/**
	 * Note: currently, we assume temporal relations only happen between Events.
	 */
	public boolean reasonTemporalRelations() {
		if (eventMap != null) {
			HashMap<String, Node<String>> nodeMap = new HashMap<String, Node<String>>();
			ConstraintNetwork<String> constraintNetwork = new ConstraintNetwork<String>();
			for (String nodeStr : eventMap.keySet()) {
				Node<String> node = new Node<String>(nodeStr);
				constraintNetwork.addNode(node);
				nodeMap.put(nodeStr, node);
			}
			
			Iterator<Event> it = eventMap.values().iterator();
			while (it != null && it.hasNext()) {
				Event event = it.next();
				String sourceStr = event.getIRIStr();
				if (event.getTemporalRelations() != null) {
					for (TemporalRelation relation : event.getTemporalRelations()) {
						String targetStr = relation.getTargetIRI();
						TemporalRelationType type = relation.getRelationType();
						short typeCode = getTemporalRelationCode(type);
						Constraint<String> constraint = new Constraint<String>(nodeMap.get(sourceStr), nodeMap.get(targetStr), typeCode);
						constraintNetwork.addConstraint(constraint);
					}
				}
			}
			
			if (constraintNetwork.pathConsistency()) { // this network is consistent
				// TODO: a quick-and-dirty implementation here for the consistency of querier's API
				ArrayList<Node<String>> matrixNodes = constraintNetwork.getModeledNodes();
				ArrayList<ArrayList<Short>> matrix = constraintNetwork.getConstraintNetwork();
				for (int i = 0; i < matrixNodes.size(); i ++) {
					String sourceStr = matrixNodes.get(i).getIdentifier();
					Event event = eventMap.get(sourceStr);

					for (int j = 0; j < matrixNodes.size(); j ++) {
						String targetStr = matrixNodes.get(j).getIdentifier();
						TemporalRelationType type = getTemporalRelationType(matrix.get(i).get(j));
						TemporalRelation relation = new TemporalRelation(sourceStr, targetStr, type);
						if (event.getTemporalRelations() == null || !event.getTemporalRelations().contains(relation)) {
							event.addTemporalRelation(relation);
						}
					}
				}
			} else {
				System.out.println("This network is inconsistent!");
			}
		}
		return false;
	}
	

	public static TemporalRelationType getTemporalRelationType(short relationCode) {
		switch (relationCode) {
			case 2: return TemporalRelationType.AFTER;
			case 1: return TemporalRelationType.BEFORE;
			case 64: return TemporalRelationType.MEET;
			case 128: return TemporalRelationType.METBY;
			case 1024: return TemporalRelationType.FINISH;
			case 2048: return TemporalRelationType.FINISHEDBY;
			case 256: return TemporalRelationType.START;
			case 512: return TemporalRelationType.STARTEDBY;
			case 16: return TemporalRelationType.OVERLAP;
			case 32: return TemporalRelationType.OVERLAPPEDBY;
			case 4096: return TemporalRelationType.EQUAL;
			case 8: return TemporalRelationType.CONTAIN;
			case 4: return TemporalRelationType.DURING;
			default: return TemporalRelationType.FULL;
		}
	}

	
	public static short getTemporalRelationCode(TemporalRelationType relationType) {
		switch (relationType) {
			case AFTER: return ConstraintNetwork.bin_after;
			case BEFORE: return ConstraintNetwork.bin_before;
			case MEET: return ConstraintNetwork.bin_meets;
			case METBY: return ConstraintNetwork.bin_metby;
			case FINISH: return ConstraintNetwork.bin_finishes;
			case FINISHEDBY: return ConstraintNetwork.bin_finishedby;
			case START: return ConstraintNetwork.bin_starts;
			case STARTEDBY: return ConstraintNetwork.bin_startedby;
			case OVERLAP: return ConstraintNetwork.bin_overlaps;
			case OVERLAPPEDBY: return ConstraintNetwork.bin_overlappedby;
			case EQUAL: return ConstraintNetwork.bin_equals;
			case CONTAIN: return ConstraintNetwork.bin_contains;
			case DURING: return ConstraintNetwork.bin_during;
			default: return ConstraintNetwork.bin_all;
		}
	}
}
