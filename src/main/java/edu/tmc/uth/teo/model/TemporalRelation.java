package edu.tmc.uth.teo.model;

import java.util.EnumSet;

public class TemporalRelation {

	public static EnumSet<TemporalRelationType> TemporalIntervalRelationSet = EnumSet.of(TemporalRelationType.BEFORE, TemporalRelationType.FINISH, TemporalRelationType.FINISHEDBY,
																					TemporalRelationType.AFTER, TemporalRelationType.START, TemporalRelationType.STARTEDBY,
																					TemporalRelationType.CONTAIN, TemporalRelationType.DURING, TemporalRelationType.MEET,
																					TemporalRelationType.METBY, TemporalRelationType.EQUAL, TemporalRelationType.OVERLAP,
																					TemporalRelationType.OVERLAPPEDBY);
	
	public static EnumSet<TemporalRelationType> TemporalPointRelationSet = EnumSet.of(TemporalRelationType.START_BEFORE_START, TemporalRelationType.START_AFTER_START,
																					TemporalRelationType.START_BEFORE_END, TemporalRelationType.STAR_AFTER_END,
																					TemporalRelationType.END_BEFORE_START, TemporalRelationType.END_AFTER_START,
																					TemporalRelationType.END_BEFORE_END, TemporalRelationType.END_AFTER_END);
	
	private String sourceIRI, targetIRI;
	private TemporalRelationType relationType;
	private Granularity granularity;
	private Duration timeOffset;
	private AssemblyMethod assemblyMethod;

	public TemporalRelation(String sourceIRI, String targetIRI,
			TemporalRelationType relationType) {
		super();
		this.sourceIRI = sourceIRI;
		this.targetIRI = targetIRI;
		this.relationType = relationType;
		this.timeOffset = null;
		this.granularity = new Granularity(Unit.UNKNOWN);
		this.assemblyMethod = AssemblyMethod.ASSERTED;
	}

	public String getSourceIRI() {
		return sourceIRI;
	}

	public void setSourceIRI(String sourceIRI) {
		this.sourceIRI = sourceIRI;
	}

	public String getTargetIRI() {
		return targetIRI;
	}

	public void setTargetIRI(String targetIRI) {
		this.targetIRI = targetIRI;
	}

	public AssemblyMethod getAssemblyMethod() {
		return assemblyMethod;
	}

	public void setAssemblyMethod(AssemblyMethod assemblyMethod) {
		this.assemblyMethod = assemblyMethod;
	}

	public TemporalRelationType getRelationType() {
		return relationType;
	}

	public void setRelationType(TemporalRelationType relationType) {
		this.relationType = relationType;
	}

	public Granularity getGranularity() {
		return granularity;
	}

	public void setGranularity(Granularity granularity) {
		this.granularity = granularity;
	}

	public Duration getTimeOffset() {
		return timeOffset;
	}

	public void setTimeOffset(Duration timeOffset) {
		this.timeOffset = timeOffset;
	}

	public String printTarget() {
		return "[" + getRelationType() + "->" + getTargetIRI() + 
				(timeOffset != null? ("(timeOffset: "+ timeOffset + ")"):"") + "]";
	}
	
	public String toString() {
		return "[" + getSourceIRI() + "->" + getRelationType() + "->" + getTargetIRI() + 
				(timeOffset != null? ("(timeOffset: "+ timeOffset + ")"):"") + "]";
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof TemporalRelation) {
			if (this.sourceIRI.equals(((TemporalRelation) o).sourceIRI) && this.targetIRI.equals(((TemporalRelation) o).targetIRI) 
					&& this.relationType.equals(((TemporalRelation) o).relationType)) {
				return true;
			}
		}
		return false;
	}
}
