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
			iri = IRI.create(file.toURI().toURL());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return load(iri);
	}
	
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

	public Object load(URL url) 
	{
		IRI iri = IRI.create(url.getPath());
		return load(iri);
	}

	public IRI getResourceIRI() {
		return this.iri;
	}
}
