package edu.tmc.uth.teo.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import edu.tmc.uth.teo.interfaces.TEOQuerier;
import edu.tmc.uth.teo.model.DirectedAcyclicGraph;
import edu.tmc.uth.teo.model.Duration;
import edu.tmc.uth.teo.model.Edge;
import edu.tmc.uth.teo.model.Event;
import edu.tmc.uth.teo.model.Granularity;
import edu.tmc.uth.teo.model.TemporalRelation;
import edu.tmc.uth.teo.model.TemporalRelationType;
import edu.tmc.uth.teo.model.TemporalType;
import edu.tmc.uth.teo.model.TimeInstant;
import edu.tmc.uth.teo.model.TimeInterval;
import edu.tmc.uth.teo.utils.TemporalRelationUtils;
import edu.tmc.uth.teo.utils.TimeUtils;

/**
 * 
 * @author yluo
 *
 */
public class TEOOWLAPIQuerier implements TEOQuerier {
	public HashMap<String, Event> eventMap = null;
	
	public TEOOWLAPIQuerier(HashMap<String, Event> eventMap) {
		this.eventMap = eventMap;
	}
	
	public Event getEventByIRIStr(String IRIStr) {
		if (IRIStr != null && this.eventMap != null) {
			return this.eventMap.get(IRIStr);
		}
		return null;
	}

	public Duration getDuration(Event intervalEvent) {
		if (intervalEvent != null) {
			if (intervalEvent.getEventType().compareTo(TemporalType.TIMEINTERVAL) == 0) {
				TimeInterval timeInterval = (TimeInterval) intervalEvent.getValidTime();
				if (timeInterval != null) {
					return timeInterval.getDuration();
				}
			} else {
				System.out.println("Error: Only TimeInterval Event has duration.");		
			}
		}
		return null;
	}

	public Duration getDurationBetweenEvents(Event event1, Event event2, Granularity gran) {
		if (event1 != null && event2 != null) {
			TimeInstant begin = null;
			TimeInstant end = null;
			
			if (event1.getEventType().compareTo(TemporalType.TIMEINSTANT) == 0) {
				begin = (TimeInstant) event1.getValidTime();
			} else if (event1.getEventType().compareTo(TemporalType.TIMEINTERVAL) == 0) {
				begin = ((TimeInterval) event1.getValidTime()).getStartTime();
			}
			
			if (event2.getEventType().compareTo(TemporalType.TIMEINSTANT) == 0) {
				end = (TimeInstant) event2.getValidTime();
			} else if (event2.getEventType().compareTo(TemporalType.TIMEINTERVAL) == 0) {
				end = ((TimeInterval) event2.getValidTime()).getStartTime();
			}
			
			if (begin != null && end != null && begin.compareTo(end) > 0) {
				TimeInstant temp = begin;
				begin = end;
				end = temp;
			}
			
			return TimeUtils.getDurationFrom(begin, end, gran);
		}
		return null;
	}
	
	public Vector<TemporalRelationType> getTemporalRelationType(Event event1,
			Event event2, Granularity granularity) {
		Vector<TemporalRelationType> relationList = new Vector<TemporalRelationType>();
		if (event1 != null && event2 != null) {
			String targetIRIStr = event2.getIRIStr();
			Vector<TemporalRelation> relations = event1.getTemporalRelations();
			for (TemporalRelation relation : relations) {
				if (relation.getTargetIRI().equals(targetIRIStr)) {
					relationList.add(relation.getRelationType());
				}
			}
		}
		return relationList;
	}

	public List<Event> getEventsTimeline() {
		Set<String> strSet = eventMap.keySet();
		String[] vertexStr = new String[strSet.size()];
		int count = 0;
		for (String str : strSet) {
			vertexStr[count++] = str;
		}
		
		ArrayList<Edge> edges = new ArrayList<Edge>();
		HashMap<String, Integer> viMap = new HashMap<String, Integer>();
		
		for (int i = 0; i < vertexStr.length; i ++) {
			viMap.put(vertexStr[i], i);
		}
		
		for (String oneString : vertexStr) {
			Event oneEvent = eventMap.get(oneString);
			Vector<TemporalRelation> relations = oneEvent.getTemporalRelations();
			// add an edge if the relation can infer "startBeforeStart"
			for (TemporalRelation relation : relations) {
				if (TemporalRelationUtils.isStartBeforeStart(relation.getRelationType())) {
					Edge newEdge = new Edge(viMap.get(relation.getSourceIRI()), viMap.get(relation.getTargetIRI()));
					edges.add(newEdge);
				}
			}
		}
		
		DirectedAcyclicGraph<String> graph = new DirectedAcyclicGraph<String>(edges, vertexStr);
		List<String> vList = TemporalRelationUtils.<String>topologySort(graph);
		List<Event> eventList = new ArrayList<Event>();
		
		for (String vStr : vList) {
			eventList.add(eventMap.get(vStr));
		}
		
		return eventList;
	}
}
