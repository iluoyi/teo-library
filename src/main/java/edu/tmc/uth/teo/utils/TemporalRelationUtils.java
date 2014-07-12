package edu.tmc.uth.teo.utils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import edu.tmc.uth.teo.model.DirectedAcyclicGraph;
import edu.tmc.uth.teo.model.TemporalRelationType;

public class TemporalRelationUtils {
	
	/**
	 * Kahn Algorithm, O(N+E)
	 * 
	 * @param graph
	 * @return
	 */
	public static <T> List<T> topologySort(DirectedAcyclicGraph<T> graph) {
		List<T> vList = new ArrayList<T>();  // result
		Queue<T> queue = new LinkedList<T>(); // temp queue
		
		List<T> vertices = graph.getVertices();
		
		for (T v : vertices) {
			if (graph.getInDegree(graph.getIndex(v)) == 0) {
				queue.add(v);
			}
		}
		
		while (!queue.isEmpty()) {
			T crtV = queue.poll();
			int u = graph.getIndex(crtV);
			vList.add(crtV);
			List<Integer> neighbors = graph.getNeighbors(u);
			
			for (Integer v : neighbors) {
				graph.removeEdge(u, v);
				if (graph.getInDegree(v) == 0) {
					queue.add(graph.getVertix(v));
				}
			}
		}
		
		if (vList.size() != graph.getSize()) {
			System.err.println("The DAG contains cycles!");
		}
		
		return vList;
	}
	
	
	public static boolean isStartBeforeStart(TemporalRelationType relationType) {
		if (relationType.equals(TemporalRelationType.START_BEFORE_START) || 
				relationType.equals(TemporalRelationType.BEFORE) || 
				relationType.equals(TemporalRelationType.MEET) || 
				relationType.equals(TemporalRelationType.OVERLAP) ||
				relationType.equals(TemporalRelationType.FINISHEDBY) ||
				relationType.equals(TemporalRelationType.CONTAIN)) {
			return true;
		}
		return false;
	}
}
