package edu.tmc.uth.teo.model;

public class TemporalRelation {

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
}
