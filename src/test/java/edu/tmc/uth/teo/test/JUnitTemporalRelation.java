package edu.tmc.uth.teo.test;

import java.util.ArrayList;

import org.allen.temporalintervalrelationships.Node;
import org.junit.Before;
import org.junit.Test;

import edu.tmc.uth.teo.impl.TEOOWLAPILoader;
import edu.tmc.uth.teo.impl.TEOOWLAPIParser;
import edu.tmc.uth.teo.impl.TEOOWLAPIQuerier;
import edu.tmc.uth.teo.impl.TEOOWLAPIReasoner;
import edu.tmc.uth.teo.interfaces.TEOLoader;
import edu.tmc.uth.teo.interfaces.TEOParser;
import edu.tmc.uth.teo.interfaces.TEOQuerier;
import edu.tmc.uth.teo.interfaces.TEOReasoner;
import edu.tmc.uth.teo.model.Event;
import edu.tmc.uth.teo.model.Granularity;
import edu.tmc.uth.teo.model.Unit;

public class JUnitTemporalRelation {
	private TEOLoader loader = null;
	private TEOParser parser = null;
	private TEOQuerier querier = null;
	private TEOReasoner reasoner = null;
	
	@Before
	public void setUp() throws Exception {
		System.out.println("Loader: loading begins.");
		loader = new TEOOWLAPILoader("src//test//resources//TEO//TEOAnnotation_4.owl");
		System.out.println("Status: " + loader.load());
		System.out.println("Loader: loading completes.\n");
		
		System.out.println("Parser: parsing begins.");
		parser = new TEOOWLAPIParser(loader.getOntology());
		System.out.println("Status: " + parser.parse());
		System.out.println("Parser: parsing completes.\n");
		
//		System.out.println("Querier: preparing the querier.");
//		querier = new TEOOWLAPIQuerier(parser.getEventMap());
//		System.out.println("Querier: preparing the querier completes.\n");
		
		System.out.println("Reasoner: reasoning begins.");
		reasoner = new TEOOWLAPIReasoner(parser.getEventMap());
		System.out.println("Status: " + reasoner.reasonTemporalRelations());
		System.out.println("Reasoner: reasoning completes.\n");		
		
		System.out.println("Querier: preparing the querier.");
		querier = new TEOOWLAPIQuerier(reasoner.getEventMap());
		System.out.println("Querier: preparing the querier completes.\n");

	}
	
//	@Test
//	public void testGetEventByIRIStr() {
//		System.out.println("######################## Testing GetEventByIRIStr #####################################");
//		Event event0 = querier.getEventByIRIStr("http://www.cse.lehigh.edu/~yil712/TEO/annotation_4.owl#Event0");
//		Event event1 = querier.getEventByIRIStr("http://www.cse.lehigh.edu/~yil712/TEO/annotation_4.owl#Event1");
//		Event event2 = querier.getEventByIRIStr("http://www.cse.lehigh.edu/~yil712/TEO/annotation_4.owl#Event2");
//		Event event3 = querier.getEventByIRIStr("http://www.cse.lehigh.edu/~yil712/TEO/annotation_4.owl#Event3");
//		Event event4 = querier.getEventByIRIStr("http://www.cse.lehigh.edu/~yil712/TEO/annotation_4.owl#Event4");
//
//		System.out.println("Event0:\n" + event0);
//		System.out.println("\nEvent1:\n" + event1);
//		System.out.println("\nEvent2:\n" + event2);
//		System.out.println("\nEvent3:\n" + event3);
//		System.out.println("\nEvent4:\n" + event4);
//	}
	
	@Test
	public void testGetTemporalRelationsBetweenEvents() {
		System.out.println("######################## Testing GetEventByIRIStr #####################################");
		Event event0 = querier.getEventByIRIStr("http://www.cse.lehigh.edu/~yil712/TEO/annotation_4.owl#Event0");
		Event event1 = querier.getEventByIRIStr("http://www.cse.lehigh.edu/~yil712/TEO/annotation_4.owl#Event1");
		Event event2 = querier.getEventByIRIStr("http://www.cse.lehigh.edu/~yil712/TEO/annotation_4.owl#Event2");
		Event event3 = querier.getEventByIRIStr("http://www.cse.lehigh.edu/~yil712/TEO/annotation_4.owl#Event3");
		Event event4 = querier.getEventByIRIStr("http://www.cse.lehigh.edu/~yil712/TEO/annotation_4.owl#Event4");

		System.out.println("event0 - event3:\n" + querier.getTemporalRelationType(event0, event3, null));
	}
	
}
