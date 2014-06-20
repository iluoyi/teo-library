package edu.tmc.uth.teo.impl;

import java.util.HashMap;

import org.semanticweb.owlapi.model.OWLObjectProperty;

import edu.tmc.uth.teo.interfaces.TEOReasoner;
import edu.tmc.uth.teo.model.Event;
import edu.tmc.uth.teo.model.TemporalRelationType;

public class TEOOWLAPIReasoner implements TEOReasoner {
	private HashMap<String, Event> eventMap = null;
	private HashMap<TemporalRelationType, OWLObjectProperty> relationMap = null; 
	
	public TEOOWLAPIReasoner(HashMap<String, Event> eventMap, HashMap<TemporalRelationType, OWLObjectProperty> relationMap) {
		this.eventMap = eventMap;
		this.relationMap = relationMap;
	}
	
	public boolean reason() {
		
		// 1. pin all events which have valid time onto the time line
		
		// 2. iteration: reason validTimes of other events from existing events on the time line (E) until E.isEmpty()
		
		
		
		return false;
	}

}
