package edu.tmc.uth.teo.queryIF;

public class Granularity implements Comparable<Granularity> {
	private Unit unit;
	
	public Granularity() {
		this.unit = Unit.UNKNOWN;
	}
	
	public Granularity(Unit unit) {
		this.unit = unit;
	}
	
	public void setUnit(Unit unit) {
		this.unit = unit;
	}
	
	public Unit getUnit() {
		return this.unit;
	}

	public int compareTo(Granularity o) {
		return getUnit().compareTo(o.getUnit());
	}
}