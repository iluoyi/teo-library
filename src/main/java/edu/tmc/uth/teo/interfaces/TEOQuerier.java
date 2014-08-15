package edu.tmc.uth.teo.interfaces;

import java.util.ArrayList;
import java.util.List;

import edu.tmc.uth.teo.model.Duration;
import edu.tmc.uth.teo.model.Event;
import edu.tmc.uth.teo.model.Granularity;

public interface TEOQuerier {
	 // this API can be further optimized with Lucene upon fields: localname, comments, and label
//	public List<Event> findEvents(String searchText);
	
//	public Date getEventFeature(Event event, EventFeature feature) throws CNTROException;

	public Event getEventByIRIStr(String IRIStr);
	
	public Duration getDuration(Event intervalEvent); // for instantEvent we print out error messages

	public Duration getDurationBetweenEvents(Event event1, Event event2, Granularity granularity);

	public ArrayList<Short> getTemporalRelationType(Event event1, Event event2, Granularity granularity);

//	public TemporalRelationType getTemporalRelationType(Event oneEvent,
//			Time time) throws CNTROException;
//
	public List<Event> getEventsTimeline();
	
}
