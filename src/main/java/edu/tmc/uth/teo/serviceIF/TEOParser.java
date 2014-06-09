package edu.tmc.uth.teo.serviceIF;


public interface TEOParser {
	// return value tells you if parsing was 
	// error free or not. if true, it went through without errors
	// if false, there were some errors.
	public boolean parse();
	//public Collection<Event> getEvents();
	public int getEventCount();
	//public CNTROMap getEventMap();
	//public CNTROMap getRelationMap();
}
