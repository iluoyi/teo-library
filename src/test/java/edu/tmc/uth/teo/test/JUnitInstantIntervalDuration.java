package edu.tmc.uth.teo.test;

import org.junit.Before;
import org.junit.Test;

import edu.tmc.uth.teo.impl.TEOOWLAPILoader;
import edu.tmc.uth.teo.impl.TEOOWLAPIParser;
import edu.tmc.uth.teo.impl.TEOOWLAPIQuerier;
import edu.tmc.uth.teo.interfaces.TEOLoader;
import edu.tmc.uth.teo.interfaces.TEOParser;
import edu.tmc.uth.teo.interfaces.TEOQuerier;
import edu.tmc.uth.teo.model.Event;

public class JUnitInstantIntervalDuration {

	private TEOLoader loader = null;
	private TEOParser parser = null;
	private TEOQuerier querier = null;

	@Before
	public void setUp() throws Exception {
		System.out.println("Loader: loading begins.");
		loader = new TEOOWLAPILoader("src//test//resources//TEO//TEOAnnotation_3.owl");
		System.out.println("Status: " + loader.load());
		System.out.println("Loader: loading completes.\n");
		
		System.out.println("Parser: parsing begins.");
		parser = new TEOOWLAPIParser(loader.getOntology());
		parser.parse();
		System.out.println("Parser: parsing completes.\n");
		
		System.out.println("Querier: preparing the querier.");
		querier = new TEOOWLAPIQuerier(parser);
		System.out.println("Querier: preparing the querier completes.\n");

	}
	
	@Test
	public void testGetEventByIRIStr() {
		System.out.println("######################## Testing GetEventByIRIStr #####################################");
		Event event1 = querier.getEventByIRIStr("http://www.cse.lehigh.edu/~yil712/TEO/annotation_3.owl#Event1");
		Event event2 = querier.getEventByIRIStr("http://www.cse.lehigh.edu/~yil712/TEO/annotation_3.owl#Event2");
		Event event3 = querier.getEventByIRIStr("http://www.cse.lehigh.edu/~yil712/TEO/annotation_3.owl#Event3");
		Event event4 = querier.getEventByIRIStr("http://www.cse.lehigh.edu/~yil712/TEO/annotation_3.owl#Event4");
		Event event5 = querier.getEventByIRIStr("http://www.cse.lehigh.edu/~yil712/TEO/annotation_3.owl#Event5");
		
		System.out.println("Event1:\n" + event1);
		System.out.println("\nEvent2:\n" + event2);
		System.out.println("\nEvent3:\n" + event3);
		System.out.println("\nEvent4:\n" + event4);
		System.out.println("\nEvent4:\n" + event5);
	}
}
