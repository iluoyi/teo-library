package edu.tmc.uth.teo.impl;

import java.util.HashMap;
import java.util.Set;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import com.clarkparsia.pellet.owlapiv3.PelletReasoner;

import edu.tmc.uth.teo.main.TEOConstants;
import edu.tmc.uth.teo.model.Event;
import edu.tmc.uth.teo.model.TemporalType;
import edu.tmc.uth.teo.model.TimeInstant;
import edu.tmc.uth.teo.queryIF.Granularity;
import edu.tmc.uth.teo.queryIF.Unit;
import edu.tmc.uth.teo.serviceIF.TEOParser;
import edu.tmc.uth.teo.utils.StringUtils;

public class TEOParserImpl implements TEOParser {

	public OWLOntology ontology = null;
	public OWLOntologyManager manager = null;
	public OWLDataFactory df = null;
	public PelletReasoner reasoner = null;
	
	public HashMap<String, Event> eventMap = new HashMap<String, Event>();
	
	// Properties
	public OWLAnnotationProperty rdfLabel = null;

	public OWLObjectProperty hasValidTime = null;
	public OWLObjectProperty hasStartTime = null;
	public OWLObjectProperty hasEndTime = null;
				
	public int getEventCount() {
		return eventMap.size();
	}
	
	public HashMap<String, Event> getEventMap() {
		return this.eventMap;
	}
	
	public TEOParserImpl(OWLOntology ont) {
		if (ont == null) {
			System.out.println("!!!!!!!!!! Initialization Error!! ontology is null. Nothing will work !!!!!!!!!!!!!");
			return;
		}
		
		ontology = ont;
		df = ontology.getOWLOntologyManager().getOWLDataFactory();
		manager = ontology.getOWLOntologyManager();
		reasoner = com.clarkparsia.pellet.owlapiv3.PelletReasonerFactory.getInstance().createReasoner(ontology);

		if ((df == null) || (reasoner == null)) {
			System.out.println("!!!!!!!!!! Initialization Error!! df/reasoner is null. Nothing will work !!!!!!!!!!!!!");
			return;
		}
		
		// Properties
		rdfLabel = df.getRDFSLabel();
		
		hasValidTime = df.getOWLObjectProperty(IRI.create(TEOConstants.TEO_HASVALIDTIME_PRP));
		hasStartTime = df.getOWLObjectProperty(IRI.create(TEOConstants.TEO_HASSTARTTIME_PRP));
		hasEndTime = df.getOWLObjectProperty(IRI.create(TEOConstants.TEO_HASENDTIME_PRP));
	}
	
	public boolean parse() {
		boolean noError = true;
		OWLClass c = null;
		Set<OWLNamedIndividual> individuals = null;
		
		/*
		 * Yi: To find out all individuals of "Event" class after reasoning.
		 */
		c = df.getOWLClass(IRI.create(TEOConstants.TEO_EVENT_CLS));
		individuals = reasoner.getInstances(c, false).getFlattened(); // from the reasoner
		
		for (OWLNamedIndividual eventIndividual : individuals) {
			if (eventIndividual != null) {
				System.out.println("\n[####################################]\nProcessing Events....--> " + eventIndividual.getIRI().toString());
				
				Event event = parseEvent(eventIndividual);
				eventMap.put(eventIndividual.getIRI().toString(), event);
			}
		}
		
		
		return noError;
	}
	
	public Event parseEvent(OWLNamedIndividual eventIndividual) {
		Event event = null;
		
		// parse the valid time
		Set<OWLNamedIndividual> valueList = reasoner.getObjectPropertyValues(eventIndividual, hasValidTime).getFlattened(); // hasValidTime
		for (OWLNamedIndividual validTime : valueList) {
			if (getTemporalType(validTime).equals(TemporalType.TIMEINSTANT)) {
				event = new Event(TemporalType.TIMEINSTANT);
				TimeInstant parsedInstant = parseTimeInstant(validTime);
				event.setValidTime(parsedInstant);
			}
		}
		
		return event;
	}
	
	/**
	 * Check the type of a given time individual
	 * @param timeIndividual
	 * @return
	 */
	public TemporalType getTemporalType(OWLNamedIndividual timeIndividual) {
		Set<OWLClass> typeList = reasoner.getTypes(timeIndividual, true).getFlattened(); // direct rdf:type
		
		for (OWLClass type : typeList) {
			if (type.getIRI().toString().equals(TEOConstants.TEMPORAL_INSTANT_CLS)) {
				return TemporalType.TIMEINSTANT;
			} else if (type.getIRI().toString().equals(TEOConstants.TEMPORAL_INTERVAL_CLS)) {
				return TemporalType.TIMEINTERVAL;
			} else if (type.getIRI().toString().equals(TEOConstants.PERIODIC_TIME_INTERVAL_CLS)) {
				return TemporalType.PERIODICTIMEINTERVAL;
			}
		}
		
		return null;
	}
	
	
	public TimeInstant parseTimeInstant(OWLNamedIndividual timeIndividual) {
		String labelAsTime = getAnnotationPropertyValue(timeIndividual, rdfLabel);
		
		TimeInstant timeInstant = null;
		if (labelAsTime != null) {
			Granularity gran = new Granularity(Unit.SECOND); // granularity should be parsed from the string
			timeInstant = new TimeInstant(labelAsTime, gran);
		}
		
		return timeInstant;
	}
	
	
	public String getAnnotationPropertyValue(OWLNamedIndividual pI,
			OWLAnnotationProperty annProperty) {
		if ((pI == null) || (ontology == null) || (annProperty == null))
			return null;

		Set<OWLAnnotation> annotations = pI.getAnnotations(ontology, annProperty);

		// return the first label annotation
		for (OWLAnnotation ann : annotations) {
			if ((ann.getValue() != null) && (!StringUtils.isNull(ann.getValue().toString()))) {
				String value = ann.getValue().toString();
				return StringUtils.getStringValueWithinQuotes(value);
			}
		}
		return null;
	}

}
