package edu.tmc.uth.teo.model;

import java.util.ArrayList;

import edu.tmc.uth.teo.utils.TemporalRelationUtils;


public class TemporalRelationInShortCode {

	private short relationCode; // this is a relation combination, we use its short code to represent e.g. "pmoFD"
	private Granularity granularity;
	private Duration timeOffset;
	private AssemblyMethod assemblyMethod;

	public TemporalRelationInShortCode(short relationCode) {
		super();
		this.relationCode = relationCode;
		this.timeOffset = null;
		this.granularity = new Granularity(Unit.UNKNOWN);
		this.assemblyMethod = AssemblyMethod.UNKNOWN;
	}

	public AssemblyMethod getAssemblyMethod() {
		return assemblyMethod;
	}

	public void setAssemblyMethod(AssemblyMethod assemblyMethod) {
		this.assemblyMethod = assemblyMethod;
	}

	public short getRelationCode() {
		return relationCode;
	}

	public void setRelationCode(short relationCode) {
		this.relationCode = relationCode;
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
		return "[" + printRelationsFrom(getRelationCode()) + 
				(timeOffset != null? ("(timeOffset: "+ timeOffset + ")"):"") + 
				(!assemblyMethod.equals(AssemblyMethod.UNKNOWN)? ("(assemblyMethod: "+ assemblyMethod + ")"):"") + 
				(!granularity.getUnit().equals(Unit.UNKNOWN)? ("(granularity: "+ granularity + ")"):"") + "]";
	}
	
	public String printRelationsFrom(short code) {
		String relations = "";
		ArrayList<TemporalRelationType> relationList = TemporalRelationUtils.getTemporalRelationTypeListFromConstraintShort(code);
		if (relationList != null && !relationList.isEmpty()) relations += relationList.get(0);
		for (int i = 1; i < relationList.size(); i ++) {
			relations += (", " + relationList.get(i));
		}
 		return relations;
	}
	
	/**
	 * Only check the TemporalRelationType, no assembly/granularity information
	 */
	@Override
	public boolean equals(Object o) { 
		if (o instanceof TemporalRelationInShortCode) {
			if (this.relationCode == ((TemporalRelationInShortCode) o).relationCode) {
				return true;
			}
		}
		return false;
	}
}
