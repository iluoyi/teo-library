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
import edu.tmc.uth.teo.model.AssemblyMethod;
import edu.tmc.uth.teo.model.Event;
import edu.tmc.uth.teo.model.TemporalRelationTarget;
import edu.tmc.uth.teo.utils.TemporalRelationUtils;

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
//					Vector<TemporalRelationMeta> relations = startEvent.getTemporalRelations();
//					for (TemporalRelationMeta relation : relations) {// Assumption: the target IRI must be an Event
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
				HashMap<String, ArrayList<TemporalRelationTarget>> relationMap = event.getTemporalRelations();
				
				if (relationMap != null) {
					Iterator<Entry<String, ArrayList<TemporalRelationTarget>>> itor = relationMap.entrySet().iterator();
					while (itor.hasNext()) { // for each targetIRI
						Entry<String, ArrayList<TemporalRelationTarget>> pair = itor.next();
						String targetStr = pair.getKey();
						ArrayList<TemporalRelationTarget> relationList = pair.getValue();
						short typeCode = TemporalRelationUtils.getMergedTemporalRelationCode(relationList); // should merge them and get the minimum labeling set
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
						// Yi: the short code "matrix.get(i).get(j)" represents a single relation (relation combination), so we should not split them
						TemporalRelationTarget relation = new TemporalRelationTarget(matrix.get(i).get(j));
						relation.setAssemblyMethod(AssemblyMethod.INFERRED); // inferred relations
						event.addTemporalRelation(targetStr, relation);
					}
				}
				return true;
			} else {
				System.err.println("This network is inconsistent in reasonTemporalRelations().");
			}
		}
		return false;
	}
	
	
	
}
