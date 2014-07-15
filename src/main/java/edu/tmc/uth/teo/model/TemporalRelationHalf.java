package edu.tmc.uth.teo.model;


public class TemporalRelationHalf {

	private TemporalRelationType relationType;
	private Granularity granularity;
	private Duration timeOffset;
	private AssemblyMethod assemblyMethod;

	public TemporalRelationHalf(TemporalRelationType relationType) {
		super();
		this.relationType = relationType;
		this.timeOffset = null;
		this.granularity = new Granularity(Unit.UNKNOWN);
		this.assemblyMethod = AssemblyMethod.ASSERTED;
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

	@Override
	public String toString() {
		return "[" + getRelationType() + 
				(timeOffset != null? ("(timeOffset: "+ timeOffset + ")"):"") + "]";
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof TemporalRelationHalf) {
			if (this.relationType.equals(((TemporalRelationHalf) o).relationType)) {
				return true;
			}
		}
		return false;
	}
}
