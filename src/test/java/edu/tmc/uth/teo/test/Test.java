package edu.tmc.uth.teo.test;

import java.util.HashMap;

import org.semanticweb.owlapi.model.OWLOntology;

import edu.tmc.uth.teo.impl.TEOParserImpl;
import edu.tmc.uth.teo.main.TEOAuxiliary;
import edu.tmc.uth.teo.model.Duration;
import edu.tmc.uth.teo.model.Event;
import edu.tmc.uth.teo.model.TimeInstant;
import edu.tmc.uth.teo.queryIF.Granularity;
import edu.tmc.uth.teo.queryIF.Unit;
import edu.tmc.uth.teo.utils.TimeUtils;

public class Test {
	public static void main(String[] args) {
		TEOAuxiliary aux = new TEOAuxiliary(
				"src//test//resources//TEO//TEOAnnotation_2.owl");
		aux.loadOntology();
		OWLOntology ontology = aux.getOntology();

		TEOParserImpl parser = new TEOParserImpl(ontology);
		parser.parse();
		
		HashMap<String, Event> eventMap = parser.getEventMap();
		
		Event event1 = eventMap.get("http://www.cse.lehigh.edu/~yil712/TEO/annotation_2.owl#InitialDrugElustingStentImplantation1");
		Event event2 = eventMap.get("http://www.cse.lehigh.edu/~yil712/TEO/annotation_2.owl#ER_Admission1");
		
		TimeInstant ti1 = (TimeInstant) event1.getTimeInstant();
		TimeInstant ti2 = (TimeInstant) event2.getTimeInstant();
		
		System.out.println();
		System.out.println(ti1.getNormalizedDate());
		System.out.println(ti2.getNormalizedDate());
		
		Duration dur = TimeUtils.getDurationFrom(ti2, ti1, new Granularity(Unit.YEAR));
		System.out.println();
		System.out.println(dur.getDurationValue());
	}
}
