package edu.tmc.uth.teo.interfaces;

import java.util.HashMap;

import edu.tmc.uth.teo.model.Event;

public interface TEOReasoner {

	public boolean reasonValidTime(); // to complete the valid time of all events
	
	public boolean reasonTemporalRelations();

	public HashMap<String, Event> getEventMap();
	
}
