package edu.tmc.uth.teo.test;

import java.util.HashMap;

import org.semanticweb.owlapi.model.OWLOntology;

import edu.tmc.uth.teo.impl.TEOParserImpl;
import edu.tmc.uth.teo.main.TEOAuxiliary;
import edu.tmc.uth.teo.model.Event;
import edu.tmc.uth.teo.model.TimeInstant;
import edu.tmc.uth.teo.model.TimeInterval;
import edu.tmc.uth.teo.queryIF.Unit;
import edu.tmc.uth.teo.utils.DurationUtils;

public class Test {
	public static void main(String[] args) {
		TEOAuxiliary aux = new TEOAuxiliary(
				"src//test//resources//TEO//TEOAnnotation_3.owl");
		aux.loadOntology();
		OWLOntology ontology = aux.getOntology();

		TEOParserImpl parser = new TEOParserImpl(ontology);
		parser.parse();
		
		HashMap<String, Event> eventMap = parser.getEventMap();
		
		Event event1 = eventMap.get("http://www.cse.lehigh.edu/~yil712/TEO/annotation_3.owl#Event1");
		Event event2 = eventMap.get("http://www.cse.lehigh.edu/~yil712/TEO/annotation_3.owl#Event2");
		Event event3 = eventMap.get("http://www.cse.lehigh.edu/~yil712/TEO/annotation_3.owl#Event3");
		Event event4 = eventMap.get("http://www.cse.lehigh.edu/~yil712/TEO/annotation_3.owl#Event4");
		Event event5 = eventMap.get("http://www.cse.lehigh.edu/~yil712/TEO/annotation_3.owl#Event5");
		
		TimeInstant ti1 = (TimeInstant) event1.getValidTime();
		TimeInstant ti2 = (TimeInstant) event2.getValidTime();
		TimeInterval ti3 = (TimeInterval) event3.getValidTime();
		TimeInterval ti4 = (TimeInterval) event4.getValidTime();
		TimeInterval ti5 = (TimeInterval) event5.getValidTime();
		
		System.out.println();
		System.out.println("Event 1: " + ti1.getNormalizedDate());
		System.out.println("Event 2: " + ti2.getNormalizedDate());
		System.out.println("Event 3: " + ti3.getStartTime().getNormalizedDate() + ", " + ti3.getEndTime().getNormalizedDate() + ", " + ti3.getDuration().getDurationValue());
		System.out.println("Event 4: " + ti4.getStartTime().getNormalizedDate() + ", " + ti4.getEndTime().getNormalizedDate() + ", " + DurationUtils.changeToUnit(ti4.getDuration().getDurationValue(), Unit.MONTH));
		System.out.println("Event 5: " + ti5.getStartTime().getNormalizedDate() + ", " + ti5.getEndTime().getNormalizedDate() + ", " + DurationUtils.changeToUnit(ti5.getDuration().getDurationValue(), Unit.YEAR));
		
	}
}
