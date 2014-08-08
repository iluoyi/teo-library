package edu.tmc.uth.teo.calendaranalyzer;

import java.util.HashMap;
import java.util.Set;

import org.semanticweb.owlapi.model.ClassExpressionType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import aima.core.logic.propositional.parsing.PLParser;
import aima.core.logic.propositional.parsing.ast.Sentence;
import aima.core.logic.propositional.visitors.ConvertToDNF;
import edu.tmc.uth.teo.impl.TEOOWLAPILoader;

public class TestOWAAPI {

	public HashMap<String, String> restrictionMap = new HashMap<String, String>();
	
	public String getObjectPropSomeValueFrom(OWLClassExpression classExpression) {
		String key = "";
		if (classExpression.getClassExpressionType().equals(ClassExpressionType.OBJECT_SOME_VALUES_FROM)) {
			String some = "";
			Set<OWLObjectProperty> propSet = classExpression.getObjectPropertiesInSignature();
			Set<OWLClass> clsSet = classExpression.getClassesInSignature();
			
			for (OWLObjectProperty prop : propSet)
				some += prop;
			
			some += " some (";
			
			// Assumption: classes in clsSet should be disjunctive (Mon or Tue or Wed...)
			OWLClass[] clsArray = clsSet.toArray(new OWLClass[clsSet.size()]);
			some += clsArray[0];
			for (int i = 1; i < clsArray.length; i++) 
				some += (" or " + clsArray[i]);
			some += ")";
			
			int index = restrictionMap.size() + 1;
			key = "R" + index;
			restrictionMap.put(key, some);
		}
		return key;
	}
	
	public void getDataPropDatatype(OWLClassExpression classExpression) {
		String key = "";
		if (classExpression.getClassExpressionType().equals(ClassExpressionType.DATA_SOME_VALUES_FROM)) {
			String some = "";
			Set<OWLDataProperty> propSet = classExpression.getDataPropertiesInSignature();
			Set<OWLDatatype> typeSet = classExpression.getDatatypesInSignature();
			Set<OWLEntity> nested = classExpression.getSignature();
			
			//TODO: ????? OWLRestriction restrict = new OWLRestriction(classExpression);
			
			for (OWLDataProperty prop : propSet)
				System.out.println(prop);
			
			for (OWLDatatype type : typeSet)
				System.out.println(type);
			
			for (OWLEntity nest : nested)
				System.out.println(nest);
		}
	}
	
	public String getLogicAlgebra(OWLClassExpression classExpression) {
		if ((!classExpression.getClassExpressionType().equals(ClassExpressionType.OBJECT_INTERSECTION_OF)) &&
				(!classExpression.getClassExpressionType().equals(ClassExpressionType.OBJECT_UNION_OF))) {
			if (classExpression.getClassExpressionType().equals(ClassExpressionType.OBJECT_SOME_VALUES_FROM)) {
				return getObjectPropSomeValueFrom(classExpression);
			} else if (classExpression.getClassExpressionType().equals(ClassExpressionType.DATA_SOME_VALUES_FROM)) {
				getDataPropDatatype(classExpression);
				return "data_some";
			} else {
				return "unknown";
			}
		} else { 
			String result = " ( ";
			if (classExpression.getClassExpressionType().equals(ClassExpressionType.OBJECT_INTERSECTION_OF)) {
				OWLClassExpression[] classArray = classExpression.asConjunctSet().toArray(new OWLClassExpression[classExpression.asConjunctSet().size()]);
				if (classArray.length > 0) result += getLogicAlgebra(classArray[0]);
				for (int i = 1; i < classArray.length; i ++) {
					result += (" & " + getLogicAlgebra(classArray[i]));
				}
			} else if (classExpression.getClassExpressionType().equals(ClassExpressionType.OBJECT_UNION_OF)) {				
				OWLClassExpression[] classArray = classExpression.asDisjunctSet().toArray(new OWLClassExpression[classExpression.asConjunctSet().size()]);
				if (classArray.length > 0) result += getLogicAlgebra(classArray[0]);
				for (int i = 1; i < classArray.length; i ++) {
					result += (" | " + getLogicAlgebra(classArray[i]));
				}
			}
			result += " ) ";
			return result;
		}
	}
	
	public static void main(String args[]) {
		String fileName = "src/test/resources/TEO/TEO_1.1.0.owl";
		TEOOWLAPILoader loader = new TEOOWLAPILoader(fileName);
		loader.load();
		OWLOntology ontology = (OWLOntology) loader.getOntology();
		OWLOntologyManager manager = ontology.getOWLOntologyManager();
		OWLDataFactory df = manager.getOWLDataFactory();
		
		OWLClass dateClass = df.getOWLClass(IRI.create(CalendarConstants.getWithNS("TEO_0000066")));
		Set<OWLClassExpression> eqClasses = dateClass.getEquivalentClasses(ontology);
		
		TestOWAAPI test = new TestOWAAPI();
	
		String logic = null;
		for (OWLClassExpression eqClass : eqClasses) {
				logic = test.getLogicAlgebra(eqClass);
				System.out.println(logic);	
				
				PLParser parser = new PLParser();
				Sentence symbol = parser.parse(logic);
				Sentence transformed = ConvertToDNF.convert(symbol);
				System.out.println(transformed.toString());
		}
		
		
	}
}
