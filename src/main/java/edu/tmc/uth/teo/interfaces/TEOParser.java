package edu.tmc.uth.teo.interfaces;

import java.util.HashMap;

import edu.tmc.uth.teo.model.Event;


public interface TEOParser {

	public boolean parse();
	
	public int getEventCount();
	
	public HashMap<String, Event> getEventMap();
	
}
