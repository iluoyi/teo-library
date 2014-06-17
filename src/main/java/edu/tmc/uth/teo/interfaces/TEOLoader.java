package edu.tmc.uth.teo.interfaces;

import org.semanticweb.owlapi.model.IRI;


public interface TEOLoader {

	public boolean load();

	public IRI getOntoIRI();
	public Object getOntology();
}
