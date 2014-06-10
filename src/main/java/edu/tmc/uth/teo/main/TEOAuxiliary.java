package edu.tmc.uth.teo.main;


import java.util.Set;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import com.clarkparsia.pellet.owlapiv3.PelletReasoner;

import edu.tmc.uth.teo.impl.TEOLoaderImpl;

public class TEOAuxiliary {
	private OWLOntology ontology = null;
	private String uri = "src//test//resources//TEO//TEOAnnotation_1.owl"; // served as the default uri
	
//	public CNTROParser parser_ = null;
	
	public TEOAuxiliary(String ontUri) 	{
		this.uri = ontUri;
	}
	
	public void loadOntology() {
		TEOLoaderImpl loader = new TEOLoaderImpl();
		ontology = (OWLOntology) loader.load(uri);
	}
	
	public String getURI() {
		return this.uri;
	}
	
	public OWLOntology getOntology() {
		return this.ontology;
	}
	
//	public void parse()
//	{
//		boolean parseError = true;
//		parser_ = null;
//		switch(this.parsertype)
//		{
//			case OWLAPI:
//				if (ontology_ == null)
//					System.out.println("Ontology is Null! exiting...");
//				else
//				{
//					parser_ = new CNTROOWLAPIParser(ontology_);
//					parseError = parser_.parse();
//				}
//				break;
//			case JENA:
//				if (model_ == null)
//					System.out.println("Ont Model is Null! exiting...");
//				else
//				{
//					parser_ = new CNTROJenaAPIParser(model_);
//					parseError = parser_.parse();
//				}
//				break;
//			default: System.out.println("Incorrect Parser Type: Has to be either OWL API or Jena.");
//				return;
//		}
//		System.out.println("Parsing Error=" + parseError);
//		System.out.println("Events Loaded=" + parser_.getEventCount() + ", " + parser_.getEventMap().size());
//		System.out.println("Statements Loaded=" + parser_.getRelationMap().size());
//		//System.out.println(parser_.getRelationMap().toString());
//	}
	
	public static void main(String args[]) {
		TEOAuxiliary aux = new TEOAuxiliary("src//test//resources//TEO//TEOAnnotation_1.owl");
		aux.loadOntology();
		OWLOntology ontology = aux.getOntology();
			
		// load the ontology to the reasoner
		PelletReasoner lreasoner = com.clarkparsia.pellet.owlapiv3.PelletReasonerFactory.getInstance().createReasoner(ontology);
		OWLOntologyManager manager = ontology.getOWLOntologyManager();
		OWLDataFactory df = manager.getOWLDataFactory();
		
		// create property and resources to query the reasoner
		IRI targetClass = IRI.create(TEOConstants.TEO_EVENT_CLS);
		System.out.println(targetClass);
		
		OWLClass eventCls = df.getOWLClass(targetClass);
		System.out.println(eventCls.toString());
		
		// get all instances of Person class
		Set<OWLNamedIndividual> individuals = lreasoner.getInstances(eventCls, false).getFlattened();
		System.out.println(individuals.size());
		
		for (OWLNamedIndividual ind : individuals) {
			System.out.println(ind.getIRI());
		}
	}
}
