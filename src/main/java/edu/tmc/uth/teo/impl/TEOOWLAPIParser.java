package edu.tmc.uth.teo.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;

import com.clarkparsia.pellet.owlapiv3.PelletReasoner;

import edu.tmc.uth.teo.interfaces.TEOParser;
import edu.tmc.uth.teo.utils.TEOConstants;
import edu.tmc.uth.teo.model.Duration;
import edu.tmc.uth.teo.model.Event;
import edu.tmc.uth.teo.model.Granularity;
import edu.tmc.uth.teo.model.TemporalType;
import edu.tmc.uth.teo.model.TimeInstant;
import edu.tmc.uth.teo.model.TimeInterval;
import edu.tmc.uth.teo.model.Unit;
import edu.tmc.uth.teo.utils.StringUtils;

public class TEOOWLAPIParser implements TEOParser {

	public OWLOntology ontology = null;
	public OWLDataFactory df = null;
	public PelletReasoner reasoner = null;
	
	public HashMap<String, Event> eventMap = new HashMap<String, Event>();
	
	// Properties
	public OWLAnnotationProperty rdfLabel = null;

	public OWLObjectProperty hasValidTime = null;
	public OWLObjectProperty hasStartTime = null;
	public OWLObjectProperty hasEndTime = null;
	public OWLObjectProperty hasDuration = null;
	
	public OWLDataProperty hasDurationValue = null;

	public int getEventCount() {
		return eventMap.size();
	}
	
	public HashMap<String, Event> getEventMap() {
		return this.eventMap;
	}
	
	/**
	 * Constructor
	 * @param ont
	 */
	public TEOOWLAPIParser(Object ont) {
		if (ont == null) {
			System.out.println("!!!!!!!!!! Initialization Error!! ontology is null. Nothing will work !!!!!!!!!!!!!");
			return;
		}
		
		this.ontology = (OWLOntology) ont;
		this.df = this.ontology.getOWLOntologyManager().getOWLDataFactory();
		this.reasoner = com.clarkparsia.pellet.owlapiv3.PelletReasonerFactory.getInstance().createReasoner(this.ontology);

		if ((df == null) || (reasoner == null)) {
			System.out.println("!!!!!!!!!! Initialization Error!! df/reasoner is null. Nothing will work !!!!!!!!!!!!!");
			return;
		}
		
		// Properties
		rdfLabel = df.getRDFSLabel();
		
		hasValidTime = df.getOWLObjectProperty(IRI.create(TEOConstants.TEO_HASVALIDTIME_PRP));
		hasStartTime = df.getOWLObjectProperty(IRI.create(TEOConstants.TEO_HASSTARTTIME_PRP));
		hasEndTime = df.getOWLObjectProperty(IRI.create(TEOConstants.TEO_HASENDTIME_PRP));
		hasDuration = df.getOWLObjectProperty(IRI.create(TEOConstants.TEO_HASDURATION_PRP));
		
		hasDurationValue = df.getOWLDataProperty(IRI.create(TEOConstants.TEO_HASDURATIONVALUE_PRP));
	}
	
	/**
	 * The main parser to parse the given file
	 */
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
				System.out.println("[####################################]Processing Events....--> " + eventIndividual.getIRI().toString());
				
				Event event = parseEvent(eventIndividual);
				eventMap.put(eventIndividual.getIRI().toString(), event);
			}
		}
		return noError;
	}
	
	/**
	 * Event parser
	 * @param eventIndividual
	 * @return
	 */
	public Event parseEvent(OWLNamedIndividual eventIndividual) {
		Event event = null;
		
		// parse the valid time
		Set<OWLNamedIndividual> valueList = getObjectPropertyValue(eventIndividual, hasValidTime); // hasValidTime
		for (OWLNamedIndividual validTime : valueList) {
			TemporalType parsedType = getTemporalType(validTime);
			if (parsedType.equals(TemporalType.TIMEINSTANT)) {
				event = new Event(TemporalType.TIMEINSTANT);
				TimeInstant parsedInstant = parseTimeInstant(validTime);
				event.setValidTime(parsedInstant);
			}
			else if (parsedType.equals(TemporalType.TIMEINTERVAL)) {
				event = new Event(TemporalType.TIMEINTERVAL);
				TimeInterval parsedInterval = parseTimeInterval(validTime);
				event.setValidTime(parsedInterval);
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
	
	/**
	 * TimeInstant parser
	 * @param timeIndividual
	 * @return
	 */
	public TimeInstant parseTimeInstant(OWLNamedIndividual timeIndividual) {
		TimeInstant timeInstant = null;
		
		String labelAsTime = getAnnotationPropertyValue(timeIndividual, rdfLabel);
		if (labelAsTime != null) {
			Granularity gran = new Granularity(Unit.SECOND); // TODO: granularity should be parsed from the string
			
			timeInstant = new TimeInstant(labelAsTime, gran);
		}
		
		return timeInstant;
	}
	
	/**
	 * TimeInterval parser
	 * @param timeIndividual
	 * @return
	 */
	public TimeInterval parseTimeInterval(OWLNamedIndividual timeIndividual) {
		TimeInterval timeInterval = null;
		
		TimeInstant startTimeInstant = null;
		TimeInstant endTimeInstant = null;
		Duration duration = null;
		
		// parse the start time
		Set<OWLNamedIndividual> valueList = getObjectPropertyValue(timeIndividual, hasStartTime); // hasStartTime
		for (OWLNamedIndividual startTime : valueList) {
			if (startTime != null) {
				startTimeInstant = parseTimeInstant(startTime);
			}
		}
		// parse the end time
		valueList = getObjectPropertyValue(timeIndividual, hasEndTime); // hasStartTime
		for (OWLNamedIndividual endTime : valueList) {
			if (endTime != null) {
				endTimeInstant = parseTimeInstant(endTime);
			}
		}
		// parse the duration
		valueList = getObjectPropertyValue(timeIndividual, hasDuration); // hasDuration
		for (OWLNamedIndividual dur : valueList) {
			if (dur != null) {
				duration = parseDuration(dur);
			}
		}
		if (startTimeInstant != null && endTimeInstant != null && duration != null) { // we have to check the validation first
			if (TimeInterval.isValidTimeInterval(startTimeInstant, endTimeInstant, duration)) {
				// Assumption: if start, end and duration are valid and given, we use start and end to populate the time interval.
				timeInterval = new TimeInterval(startTimeInstant, endTimeInstant); 
			} else {
				System.out.println("Inconsistent startTime, endTime and duration for TimeInterval: " + timeIndividual.getIRI());
			}
		} else if (startTimeInstant != null && endTimeInstant != null) {
			timeInterval = new TimeInterval(startTimeInstant, endTimeInstant);
		} else if (startTimeInstant != null && duration != null) {
			timeInterval = new TimeInterval(startTimeInstant, duration);
		} else if (endTimeInstant != null && duration != null) {
			timeInterval = new TimeInterval(duration, endTimeInstant);
		} else {
			System.out.println("Given information is not sufficient to parse the TimeInterval: " + timeIndividual.getIRI());
		}
		
		return timeInterval;
	}
	
	/**
	 * Duration parser
	 */
	public Duration parseDuration(OWLNamedIndividual durIndividual) {
		Duration duration = null;
		
		Set<OWLLiteral> valueList = getDataPropertyValue(durIndividual, hasDurationValue);
		for (OWLLiteral durValue : valueList) {
			if (durValue != null) {
				String durValueStr = durValue.getLiteral();
				Unit unit = Unit.SECOND; // TODO: granularity should be parsed from the string
				
				duration = new Duration(durValueStr, unit);
			}
		}
		
		return duration;
	}
	
	/**
	 * To get AnnotationPropertyValue
	 * @param pI
	 * @param annProperty
	 * @return
	 */
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
	
	/**
	 * To get ObjectPropertyValue
	 * @param pI
	 * @param objProperty
	 * @return
	 */
	public Set<OWLNamedIndividual> getObjectPropertyValue(OWLNamedIndividual pI, OWLObjectProperty objProperty)
	{
		if ((pI == null)||(objProperty == null))
			return null;

		Set<OWLNamedIndividual> propList = reasoner.getObjectPropertyValues(pI, objProperty).getFlattened();
		
		return propList;
	}
	
	/**
	 * To get DataPropertyValue
	 * @param pI
	 * @param dataProperty
	 * @return
	 */
	public Set<OWLLiteral> getDataPropertyValue(OWLNamedIndividual pI,
			OWLDataProperty dataProperty) {
		if ((pI == null) || (dataProperty == null))
			return null;

		Map<OWLDataPropertyExpression, Set<OWLLiteral>> dataProperties = pI
				.getDataPropertyValues(ontology);

		if (dataProperties.containsKey(dataProperty)) {
			return dataProperties.get(dataProperty);
		}

		return null;
	}

}
