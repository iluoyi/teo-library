package edu.tmc.uth.teo.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Vector;

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
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLOntology;

import com.clarkparsia.pellet.owlapiv3.PelletReasoner;

import edu.tmc.uth.teo.interfaces.TEOParser;
import edu.tmc.uth.teo.model.Duration;
import edu.tmc.uth.teo.model.Event;
import edu.tmc.uth.teo.model.Granularity;
import edu.tmc.uth.teo.model.TemporalRelation;
import edu.tmc.uth.teo.model.TemporalRelationType;
import edu.tmc.uth.teo.model.TemporalType;
import edu.tmc.uth.teo.model.TimeInstant;
import edu.tmc.uth.teo.model.TimeInterval;
import edu.tmc.uth.teo.model.Unit;
import edu.tmc.uth.teo.utils.StringUtils;
import edu.tmc.uth.teo.utils.TEOConstants;

public class TEOOWLAPIParser implements TEOParser {

	private OWLOntology ontology = null;
	private OWLDataFactory df = null;
	private PelletReasoner reasoner = null;
	
	// inner helper data structures
	private HashMap<String, Event> eventMap = null;
	private HashMap<String, TemporalRelation> relationMap = null;
	private HashMap<OWLObjectProperty, TemporalRelationType> relationRoaster = null;
	
	private Vector<String> iriList = null;
	private HashMap<String, OWLNamedIndividual> timeOffsetMap = null;
	
	// Properties
	private OWLAnnotationProperty rdfLabel = null;
	private OWLAnnotationProperty hasTimeOffset = null;

	private OWLObjectProperty hasValidTime = null;
	private OWLObjectProperty hasStartTime = null;
	private OWLObjectProperty hasEndTime = null;
	private OWLObjectProperty hasDuration = null;
	
	private OWLObjectProperty before = null;
	private OWLObjectProperty after = null;
	private OWLObjectProperty start = null;
	private OWLObjectProperty startedBy = null;
	private OWLObjectProperty finish = null;
	private OWLObjectProperty finishedBy = null;
	private OWLObjectProperty meet = null;
	private OWLObjectProperty metBy = null;
	private OWLObjectProperty overlap = null;
	private OWLObjectProperty overlappedBy = null;
	private OWLObjectProperty contain = null;
	private OWLObjectProperty during = null;
	private OWLObjectProperty equal = null;
	
	private OWLDataProperty hasDurationPattern= null;

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
		
		this.eventMap = new HashMap<String, Event>();
		this.relationMap = new HashMap<String, TemporalRelation>();
		this.relationRoaster = new HashMap<OWLObjectProperty, TemporalRelationType>();
		this.ontology = (OWLOntology) ont;
		this.df = this.ontology.getOWLOntologyManager().getOWLDataFactory();
		this.reasoner = com.clarkparsia.pellet.owlapiv3.PelletReasonerFactory.getInstance().createReasoner(this.ontology);

		if ((df == null) || (reasoner == null)) {
			System.out.println("!!!!!!!!!! Initialization Error!! df/reasoner is null. Nothing will work !!!!!!!!!!!!!");
			return;
		}
		
		// Properties
		rdfLabel = df.getRDFSLabel();
		hasTimeOffset = df.getOWLAnnotationProperty(IRI.create(TEOConstants.TEO_HASTIMEOFFSET_PRP));
		
		hasValidTime = df.getOWLObjectProperty(IRI.create(TEOConstants.TEO_HASVALIDTIME_PRP));
		hasStartTime = df.getOWLObjectProperty(IRI.create(TEOConstants.TEO_HASSTARTTIME_PRP));
		hasEndTime = df.getOWLObjectProperty(IRI.create(TEOConstants.TEO_HASENDTIME_PRP));
		hasDuration = df.getOWLObjectProperty(IRI.create(TEOConstants.TEO_HASDURATION_PRP));
		
		before = df.getOWLObjectProperty(IRI.create(TEOConstants.TEO_TR_BEFORE_PRP));
		after = df.getOWLObjectProperty(IRI.create(TEOConstants.TEO_TR_AFTER_PRP));
		start = df.getOWLObjectProperty(IRI.create(TEOConstants.TEO_TR_START_PRP));
		startedBy = null;
		finish = df.getOWLObjectProperty(IRI.create(TEOConstants.TEO_TR_FINISH_PRP));
		finishedBy = null;
		meet = df.getOWLObjectProperty(IRI.create(TEOConstants.TEO_TR_MEET_PRP));
		metBy = null;
		overlap = df.getOWLObjectProperty(IRI.create(TEOConstants.TEO_TR_OVERLAP_PRP));
		overlappedBy = null;
		contain = df.getOWLObjectProperty(IRI.create(TEOConstants.TEO_TR_CONTAIN_PRP));
		during = df.getOWLObjectProperty(IRI.create(TEOConstants.TEO_TR_DURING_PRP));
		equal = df.getOWLObjectProperty(IRI.create(TEOConstants.TEO_TR_EQUAL_PRP));
		
		if (before != null) relationRoaster.put(before, TemporalRelationType.BEFORE);
		if (after != null) relationRoaster.put(after, TemporalRelationType.AFTER);
		if (start != null) relationRoaster.put(start, TemporalRelationType.START);
		if (startedBy != null) relationRoaster.put(startedBy, TemporalRelationType.STARTEDBY);
		if (finish != null) relationRoaster.put(finish, TemporalRelationType.FINISH);
		if (finishedBy != null) relationRoaster.put(finishedBy, TemporalRelationType.FINISHEDBY);
		if (meet != null) relationRoaster.put(meet, TemporalRelationType.MEET);
		if (metBy != null) relationRoaster.put(metBy, TemporalRelationType.METBY);
		if (overlap != null) relationRoaster.put(overlap, TemporalRelationType.OVERLAP);
		if (overlappedBy != null) relationRoaster.put(overlappedBy, TemporalRelationType.OVERLAPPEDBY);
		if (contain != null) relationRoaster.put(contain, TemporalRelationType.CONTAIN);
		if (during != null) relationRoaster.put(during, TemporalRelationType.DURING);
		if (equal != null) relationRoaster.put(equal, TemporalRelationType.EQUAL);
		
		hasDurationPattern = df.getOWLDataProperty(IRI.create(TEOConstants.TEO_HASDURATIONPATTERN_PRP));
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
		
		if (individuals != null && !individuals.isEmpty()) this.timeOffsetMap = new HashMap<String, OWLNamedIndividual>();
		
		for (OWLNamedIndividual eventIndividual : individuals) {
			if (eventIndividual != null) {
				System.out.println("[####################################]Processing Events....--> " + eventIndividual.getIRI().toString());
				
				Event event = parseEvent(eventIndividual);
				eventMap.put(eventIndividual.getIRI().toString(), event);
			}
		}
		
		// handle timeOffset for before or after
		if (timeOffsetMap != null && !timeOffsetMap.isEmpty()) {
			Iterator<Entry<String, OWLNamedIndividual>> it = timeOffsetMap.entrySet().iterator();
			while (it.hasNext()) {
				Entry<String, OWLNamedIndividual> pair = it.next();
				String[] parts = pair.getKey().split("-");
				int sourceIRIIndex = Integer.parseInt(parts[0]);
				int targetIRIIndex = Integer.parseInt(parts[1]);
				
				TemporalRelation relation1 = relationMap.get(sourceIRIIndex + "-" + TemporalRelationType.BEFORE + "-" + targetIRIIndex);
				relation1.setTimeOffset(this.parseDuration(pair.getValue()));
				TemporalRelation relation2 = relationMap.get(targetIRIIndex + "-" + TemporalRelationType.AFTER + "-" + sourceIRIIndex);
				relation2.setTimeOffset(this.parseDuration(pair.getValue()));
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
		Event event = new Event();
		String sourceIRIStr = eventIndividual.getIRI().toString();
		event.setIRIStr(sourceIRIStr);
		int sourceIRIIndex = this.getEventIRIIndex(sourceIRIStr);
		
		Set<OWLNamedIndividual> valueList = null;		
		// parse the valid time
		valueList = getObjectPropertyValue(eventIndividual, hasValidTime); // hasValidTime
		for (OWLNamedIndividual validTime : valueList) {
			TemporalType parsedType = getTemporalType(validTime);
			if (parsedType.equals(TemporalType.TIMEINSTANT)) {
				event.setEventType(TemporalType.TIMEINSTANT);
				TimeInstant parsedInstant = parseTimeInstant(validTime);
				event.setValidTime(parsedInstant);
			}
			else if (parsedType.equals(TemporalType.TIMEINTERVAL)) {
				event.setEventType(TemporalType.TIMEINTERVAL);
				TimeInterval parsedInterval = parseTimeInterval(validTime);
				event.setValidTime(parsedInterval);
			}
		}
		
		// We prepare the timeOffsetMap for "before/after", this can only recored axioms before Pellet's reasoning!
		// Note: we use "getIRIIndex(sourceIRIStr)-getIRIIndex(targetIRIStr)" as the key, "timeOffset Duration's OWLNamedIndividual" as the value. 
		Set<OWLObjectPropertyAssertionAxiom> axiomSet = ontology.getObjectPropertyAssertionAxioms(eventIndividual);
		for (OWLObjectPropertyAssertionAxiom axiom : axiomSet) {
			TemporalRelationType relationType = relationRoaster.get(axiom.getProperty());
			if (relationType != null && (relationType.equals(TemporalRelationType.BEFORE) || relationType.equals(TemporalRelationType.AFTER))) {
				String keyStr = null;
				if (relationType.equals(TemporalRelationType.BEFORE)) { // before
					keyStr = sourceIRIIndex + "-" + this.getEventIRIIndex(axiom.getObject().asOWLNamedIndividual().getIRI().toString());
				} else { // after, normalized to before
					keyStr = this.getEventIRIIndex(axiom.getObject().asOWLNamedIndividual().getIRI().toString()) + "-" + sourceIRIIndex;
				}
				if (timeOffsetMap.containsKey(keyStr)) {
					System.err.println("Error: detected duplicate timeOffset for [" + sourceIRIStr + "->" + relationType + "->" + axiom.getObject().asOWLNamedIndividual().getIRI().toString() + "]");
				} else {
					Set<OWLAnnotation> annotSet = axiom.getAnnotations(hasTimeOffset);
					for (OWLAnnotation annot : annotSet) {
						OWLNamedIndividual valueStr = df.getOWLNamedIndividual(IRI.create(annot.getValue().toString()));
						timeOffsetMap.put(keyStr, valueStr);
					}
				}
			}
		}
		
		// parse each temporal relation
		// because we don't have reification triples, every relation must be attached to an event
		Iterator<Entry<OWLObjectProperty, TemporalRelationType>> it = relationRoaster.entrySet().iterator();
		OWLObjectProperty relationPro = null;
		TemporalRelationType relationType = null;
		String targetIRIStr = null;
		TemporalRelation relation = null;
		String relationKey = null;
		//TODO: assemblyMethod, granularity?		
		while (it.hasNext()) {
			Entry<OWLObjectProperty, TemporalRelationType> pair = it.next();
			relationType = pair.getValue();
			relationPro = pair.getKey();
			if (relationPro != null) {
				valueList = getObjectPropertyValue(eventIndividual, relationPro); // Inferred results of: before, after, and so on...
				
				for (OWLNamedIndividual target : valueList) {
					targetIRIStr = target.getIRI().toString();
					relationKey = sourceIRIIndex + "-" + relationType + "-" + this.getEventIRIIndex(targetIRIStr);
					relation = new TemporalRelation(sourceIRIStr, targetIRIStr, relationType);
					relationMap.put(relationKey, relation);
					event.addTemporalRelation(relation); // ignore timeOffset here, will be handled outside		
				}
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
	 * Duration parser from durationPattern "0Y0M0W0D0H0m0s"
	 */
	public Duration parseDuration(OWLNamedIndividual durIndividual) {
		Duration duration = null;
		
		Set<OWLLiteral> valueList = getDataPropertyValue(durIndividual, hasDurationPattern);
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

	public HashMap<OWLObjectProperty, TemporalRelationType> getTemporalRelationMap() {
		return this.relationRoaster;
	}

	private void addEventIRI(String iriStr) {
		if (this.iriList == null) {
			iriList = new Vector<String>();
		}
		if (!iriList.contains(iriStr)) {
			iriList.add(iriStr);
		}
	}
	
	private int getEventIRIIndex(String iriStr) {
		if (iriList == null) {
			addEventIRI(iriStr);
		}
		int index = iriList.indexOf(iriStr);
		if (index < 0) {
			addEventIRI(iriStr);
			return iriList.size() - 1;
		} else {
			return index;
		}
	}
	
	private String getEventIRIString(int index) {
		return this.iriList.get(index);
	}
}
