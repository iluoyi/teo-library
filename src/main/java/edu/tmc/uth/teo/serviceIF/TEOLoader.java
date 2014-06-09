package edu.tmc.uth.teo.serviceIF;

import java.net.URL;

import org.semanticweb.owlapi.model.IRI;

public interface TEOLoader {

	/**
	 * To load the ontology from the IRI
	 * @param resource
	 * @return
	 */
	public Object load(IRI resource);
	
	/**
	 * To load the ontology from the file name
	 * @param filename
	 * @return
	 */
	public Object load(String filename);
	
	/**
	 * To load the ontology from the URL
	 * @param url
	 * @return
	 */
	public Object load (URL url);
	
	/**
	 * To retrieve the IRI of the loaded ontology
	 * @return
	 */
	public IRI getResourceIRI();
}
