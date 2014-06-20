package edu.tmc.uth.teo.interfaces;

import java.util.HashMap;

import org.semanticweb.owlapi.model.OWLObjectProperty;

import edu.tmc.uth.teo.model.Event;
import edu.tmc.uth.teo.model.TemporalRelationType;


public interface TEOParser {

	public boolean parse();
	
	public int getEventCount();
	
	public HashMap<String, Event> getEventMap();

	public HashMap<TemporalRelationType, OWLObjectProperty> getTemporalRelationMap();
}
