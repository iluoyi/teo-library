package edu.tmc.uth.teo.impl;

import edu.tmc.uth.teo.interfaces.TEOParser;
import edu.tmc.uth.teo.interfaces.TEOQuerier;
import edu.tmc.uth.teo.model.Duration;
import edu.tmc.uth.teo.model.Event;
import edu.tmc.uth.teo.model.Granularity;

public class TEOOWLAPIQuerier implements TEOQuerier {
	TEOParser parser = null;
	
	public TEOOWLAPIQuerier(TEOParser parser) {
		this.parser = parser;
	}
	
	public Event getEventByIRIStr(String IRIStr) {
		if (IRIStr != null && this.parser != null) {
			return this.parser.getEventMap().get(IRIStr);
		}
		return null;
	}

	public Duration getDuration(Event intervalEvent) {
		// TODO Auto-generated method stub
		return null;
	}

	public Duration getDurationBetweenEvents(Event startEvent, Event endEvent,
			Granularity granularity) {
		// TODO Auto-generated method stub
		return null;
	}

}
