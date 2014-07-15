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
import edu.tmc.uth.teo.model.TemporalRelationHalf;
import edu.tmc.uth.teo.model.TemporalRelationType;

/**
 * 
 * @author yluo
 *
 */
public class TEOOWLAPIReasoner implements TEOReasoner {
	private HashMap<String, Event> eventMap = null;
	
	public TEOOWLAPIReasoner(HashMap<String, Event> eventMap) {
		this.eventMap = eventMap;
	}
	
	public HashMap<String, Event> getEventMap() {
		return this.eventMap;
	}
	
	/**
	 * Note: currently, we assume temporal relations only happen between Events.
	 */
	private Vector<Event> validTimeEvent = null; // events that contain validTime and can be used for inference
	private Vector<Event> visitedEvent = null; // events that contain validTime and have been visited/used for inference
	
	public boolean reasonValidTime() {
//		if (eventMap != null) {
//			// 1. pin all events which have valid time onto the time line (put them in a Set "validTimeEvent")
//			Iterator<Entry<String, Event>> it = eventMap.entrySet().iterator();
//			if (it != null && it.hasNext()) validTimeEvent = new Vector<Event>();
//			while (it.hasNext()) {
//				Event crtEvent = it.next().getValue();
//				if (crtEvent.getValidTime() != null)
//					validTimeEvent.add(crtEvent);
//			}
//			if (validTimeEvent != null) visitedEvent = new Vector<Event>();
//			
//			// 2. iteration: reason validTimes for other events
//			while (validTimeEvent != null && !validTimeEvent.isEmpty()) {
//				Event startEvent = validTimeEvent.firstElement();
//				TimeInstant startTime = null;
//				if (startEvent.getEventType().equals(TemporalType.TIMEINSTANT)) {
//					startTime = (TimeInstant) startEvent.getValidTime();
//				} else if (startEvent.getEventType().equals(TemporalType.TIMEINTERVAL)) {
//					startTime = ((TimeInterval) startEvent.getValidTime()).getStartTime();
//				}
//				if (startTime != null) {
//					Vector<TemporalRelationFull> relations = startEvent.getTemporalRelations();
//					for (TemporalRelationFull relation : relations) {// Assumption: the target IRI must be an Event
//						if (relation.getTimeOffset() != null) { // before or after
//							Event targetEvent = eventMap.get(relation.getTargetIRI());
//							if (!visitedEvent.contains(targetEvent)) { // then we can add new validTime info for this targetEvent
//								TemporalRegion validTime = null;
//								if (targetEvent.getEventType().equals(TemporalType.TIMEINTERVAL)) {
//									//TODO: for time interval 
//									
//								} else { // Unknown or timeInstant -> timeInstant
//									if (targetEvent.getEventType().equals(TemporalType.UNKNOWN)) {
//										targetEvent.setEventType(TemporalType.TIMEINSTANT);
//									}
//									if (targetEvent.getValidTime() != null) {
//										System.err.println("Error: deteced duplicate infered results for a sigle Event - " + targetEvent);
//									} else {
//										if (relation.getRelationType().equals(TemporalRelationType.BEFORE)) {
//											validTime = TimeUtils.getEndTimeInstantFrom(startTime, relation.getTimeOffset(), null);
//										} else if (relation.getRelationType().equals(TemporalRelationType.AFTER)) {
//											validTime = TimeUtils.getStartTimeInstantFrom(relation.getTimeOffset(), startTime, null);
//										}
//										targetEvent.setValidTime(validTime);
//										validTimeEvent.add(targetEvent);
//									}
//								}
//							}
//						}
//					}
//				}
//				visitedEvent.add(startEvent);
//				validTimeEvent.remove(startEvent);
//			}
//			return true;
//		}
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
				HashMap<String, ArrayList<TemporalRelationHalf>> relationMap = event.getTemporalRelations();
				
				if (relationMap != null) {
					Iterator<Entry<String, ArrayList<TemporalRelationHalf>>> itor = relationMap.entrySet().iterator();
					while (itor.hasNext()) { // for each targetIRI
						Entry<String, ArrayList<TemporalRelationHalf>> pair = itor.next();
						String targetStr = pair.getKey();
						ArrayList<TemporalRelationHalf> relationList = pair.getValue();
						short typeCode = getTemporalRelationCodeIntersection(relationList); //TODO : Intersection???
						Constraint<String> constraint = new Constraint<String>(nodeMap.get(sourceStr), nodeMap.get(targetStr), typeCode);
						constraintNetwork.addConstraint(constraint);
					}
				}
			}
			
			if (constraintNetwork.pathConsistency()) { // this network is consistent, then populate the Matrix (eventMap)
				// a quick-and-dirty implementation here for the consistency of querier's API
				ArrayList<Node<String>> matrixNodes = constraintNetwork.getModeledNodes();
				ArrayList<ArrayList<Short>> matrix = constraintNetwork.getConstraintNetwork();
				for (int i = 0; i < matrixNodes.size(); i ++) {
					String sourceStr = matrixNodes.get(i).getIdentifier();
					Event event = eventMap.get(sourceStr);

					for (int j = 0; j < matrixNodes.size(); j ++) {
						String targetStr = matrixNodes.get(j).getIdentifier();
						ArrayList<String> relationStrs = constraintNetwork.getConstraintStringFromConstraintShort(matrix.get(i).get(j));
						for (String relationStr : relationStrs) {
							TemporalRelationHalf relation = new TemporalRelationHalf(getTemporalRelationType(relationStr));
							event.addTemporalRelation(targetStr, relation);
						}
					}
				}
				return true;
			} else {
				System.err.println("This network is inconsistent in reasonTemporalRelations().");
			}
		}
		return false;
	}
	
	/*
	 * Helper functions
	 */
	public static TemporalRelationType getTemporalRelationType(String relationStr) {
		// interval relations
		if (relationStr.equals("before")) return TemporalRelationType.BEFORE;
		if (relationStr.equals("after")) return TemporalRelationType.AFTER;
		if (relationStr.equals("meet")) return TemporalRelationType.MEET;
		if (relationStr.equals("metBy")) return TemporalRelationType.METBY;
		if (relationStr.equals("during")) return TemporalRelationType.DURING;
		if (relationStr.equals("contain")) return TemporalRelationType.CONTAIN;
		if (relationStr.equals("overlap")) return TemporalRelationType.OVERLAP;
		if (relationStr.equals("overlappedBy")) return TemporalRelationType.OVERLAPPEDBY;
		if (relationStr.equals("start")) return TemporalRelationType.START;
		if (relationStr.equals("startedBy")) return TemporalRelationType.STARTEDBY;
		if (relationStr.equals("finish")) return TemporalRelationType.FINISH;
		if (relationStr.equals("finishedBy")) return TemporalRelationType.FINISHEDBY;
		if (relationStr.equals("equal")) return TemporalRelationType.EQUAL;
		if (relationStr.equals("before")) return TemporalRelationType.BEFORE;
		// point relations
		if (relationStr.equals("startBeforeStart")) return TemporalRelationType.START_BEFORE_START;
		if (relationStr.equals("startAfterStart")) return TemporalRelationType.START_AFTER_START;
		if (relationStr.equals("startEqualStart")) return TemporalRelationType.START_EQUAL_START;
		if (relationStr.equals("endBeforeEnd")) return TemporalRelationType.END_BEFORE_END;
		if (relationStr.equals("endAfterEnd")) return TemporalRelationType.END_AFTER_END;
		if (relationStr.equals("endEqualEnd")) return TemporalRelationType.END_EQUAL_END;
		if (relationStr.equals("startBeforeEnd")) return TemporalRelationType.START_BEFORE_END;
		if (relationStr.equals("startAfterEnd")) return TemporalRelationType.START_AFTER_END;
		if (relationStr.equals("startEqualEnd")) return TemporalRelationType.START_EQUAL_END;
		if (relationStr.equals("endBeforeStart")) return TemporalRelationType.END_BEFORE_START;
		if (relationStr.equals("endAfterStart")) return TemporalRelationType.END_AFTER_START;
		if (relationStr.equals("endEqualStart")) return TemporalRelationType.END_EQUAL_START;
		// full
		return TemporalRelationType.FULL;
	}
	
	public static short getTemporalRelationCode(TemporalRelationType relationType) {
		switch (relationType) {
			// interval relations
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
			// point relations
			case START_BEFORE_START: return ConstraintNetwork.bin_SBS;
			case START_AFTER_START: return ConstraintNetwork.bin_SAS;
			case START_EQUAL_START: return ConstraintNetwork.bin_SES;
			case START_BEFORE_END: return ConstraintNetwork.bin_SBE;
			case START_AFTER_END: return ConstraintNetwork.bin_SAE;
			case START_EQUAL_END: return ConstraintNetwork.bin_SEE;
			case END_BEFORE_START: return ConstraintNetwork.bin_EBS;
			case END_AFTER_START: return ConstraintNetwork.bin_EAS;
			case END_EQUAL_START: return ConstraintNetwork.bin_EES;
			case END_BEFORE_END: return ConstraintNetwork.bin_EBE;
			case END_AFTER_END: return ConstraintNetwork.bin_EAE;
			case END_EQUAL_END: return ConstraintNetwork.bin_EEE;
			// full
			default: return ConstraintNetwork.bin_all;
		}
	}
	
	//TODO ????????????
	public static short getTemporalRelationCodeIntersection(ArrayList<TemporalRelationHalf> relationList) {
		short result = ConstraintNetwork.bin_all;
		if (relationList != null && !relationList.isEmpty()) {
			for (TemporalRelationHalf relaion : relationList) {
				result &= getTemporalRelationCode(relaion.getRelationType()); // intersection
			}
		}
		return result;
	}
	//TODO ????????????
}
