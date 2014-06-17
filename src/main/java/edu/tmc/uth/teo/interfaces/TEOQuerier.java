package edu.tmc.uth.teo.interfaces;

import edu.tmc.uth.teo.model.Duration;
import edu.tmc.uth.teo.model.Event;
import edu.tmc.uth.teo.model.Granularity;

public interface TEOQuerier {
	 // this API can be further optimized with Lucene upon fields: localname, comments, and label
//	public List<Event> findEvents(String searchText);
	
//	public Date getEventFeature(Event event, EventFeature feature) throws CNTROException;

	public Event getEventByIRIStr(String IRIStr);
	
	public Duration getDuration(Event intervalEvent); // for instantEvent we print out error messages

	public Duration getDurationBetweenEvents(Event startEvent, Event endEvent, Granularity granularity);

//	public Vector<TemporalRelationType> getTemporalRelationType(Event oneEvent,
//			Event twoEvent) throws CNTROException;
//
//	public TemporalRelationType getTemporalRelationType(Event oneEvent,
//			Time time) throws CNTROException;
//
//	public Hashtable<String, List<Event>> getEventsTimeline(
//			boolean reverseChronological, boolean filterUnclassified,
//			boolean groupSameEvents, boolean useNormalizedEventsIfAvailable,
//			Date assignThisTSIfNoneFound);
}
