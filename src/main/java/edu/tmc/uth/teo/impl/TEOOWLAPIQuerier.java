package edu.tmc.uth.teo.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import edu.tmc.uth.teo.interfaces.TEOQuerier;
import edu.tmc.uth.teo.model.DirectedAcyclicGraph;
import edu.tmc.uth.teo.model.Duration;
import edu.tmc.uth.teo.model.Edge;
import edu.tmc.uth.teo.model.Event;
import edu.tmc.uth.teo.model.Granularity;
import edu.tmc.uth.teo.model.TemporalRelationTarget;
import edu.tmc.uth.teo.model.TemporalRelationType;
import edu.tmc.uth.teo.model.TemporalType;
import edu.tmc.uth.teo.model.TimeInstant;
import edu.tmc.uth.teo.model.TimeInterval;
import edu.tmc.uth.teo.utils.TEOConstants;
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
	
	/**
	 * Here we return the temporalRelationCode list due to possible relation combinations (e.g. "[before, contain]" as a relation), need to be interpreted further.
	 */
	public ArrayList<Short> getTemporalRelationType(Event event1, Event event2, Granularity granularity) {
		ArrayList<Short> relations= new ArrayList<Short>();
		if (event1 != null && event2 != null) {
			String targetIRIStr = event2.getIRIStr();
			HashMap<String, ArrayList<TemporalRelationTarget>> relationMap = event1.getTemporalRelations();
			ArrayList<TemporalRelationTarget> relationList = relationMap.get(targetIRIStr);
			for (TemporalRelationTarget relation : relationList) {	
				// TODO: need to consider the granularity
				relations.add(relation.getRelationCode());
			}
		} else {
			relations.add(TEOConstants.bin_full); // as the unknown relation
		}
		return relations;
	}

	/**
	 * The default configuration is to compare the startTime between a pair of events
	 */
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
		
		for (String sourceIRI : vertexStr) {
			Event sourceEvent = eventMap.get(sourceIRI);
			HashMap<String, ArrayList<TemporalRelationTarget>> relationMap = sourceEvent.getTemporalRelations();
			// TODO: to handle "startEqualStart"
			Iterator<Entry<String, ArrayList<TemporalRelationTarget>>> itor = relationMap.entrySet().iterator();
			while (itor.hasNext()) {
				Entry<String, ArrayList<TemporalRelationTarget>> pair = itor.next();
				String targetIRI = pair.getKey();
				ArrayList<TemporalRelationTarget> relationList = pair.getValue(); // relations should be merged first (intersection)
				short minRelations = TemporalRelationUtils.getMergedTemporalRelationCode(relationList);
				ArrayList<TemporalRelationType> relations = TemporalRelationUtils.getTemporalRelationTypeListFromConstraintShort(minRelations);
				if (TemporalRelationUtils.isStartBeforeStart(relations)) {
					System.out.println("Edge: " + sourceIRI + ", " + targetIRI + " <-" + relations);
					Edge newEdge = new Edge(viMap.get(sourceIRI), viMap.get(targetIRI));
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
