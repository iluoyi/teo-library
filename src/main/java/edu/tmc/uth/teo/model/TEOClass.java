package edu.tmc.uth.teo.model;

public abstract class TEOClass {
	private String uri = null;
	private String label = null; // as unique id
	
	public String getClassURI() {
		return this.uri;
	}

	public void setClassURI(String uri) {
		this.uri = uri;
	}
	
	public String getClassLabel() {
		return this.label;
	}

	public void setClassLabel(String label) {
		this.label = label;
	}
	
	public String toString() {
		return getClassLabel();
	}
}
