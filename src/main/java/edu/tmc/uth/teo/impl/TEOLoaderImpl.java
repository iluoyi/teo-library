package edu.tmc.uth.teo.impl;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;

public class TEOLoaderImpl {
	public IRI iri = null;
	
	public Object load(String filename) {
		File file = new File(filename);
		try {
			iri = IRI.create(file.toURI().toURL()); // to get the IRI of the file
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return load(iri);
	}
	
	/**
	 * We use OWL API to load the ontology
	 * @param resource
	 * @return
	 */
	public Object load(IRI resource) {
		try {
			OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
			OWLOntology ontology = manager.loadOntology(resource);
			return ontology;
		} catch (OWLOntologyCreationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * To load the ontology from the URL
	 * @param url
	 * @return
	 */
	public Object load(URL url) 
	{
		try {
			iri = IRI.create(url.toURI());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return load(iri);
	}

	/**
	 * To retrieve the IRI of the loaded ontology
	 */
	public IRI getResourceIRI() {
		return this.iri;
	}
}
