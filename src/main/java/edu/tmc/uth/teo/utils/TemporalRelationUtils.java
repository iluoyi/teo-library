package edu.tmc.uth.teo.utils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.allen.temporalintervalrelationships.ConstraintNetwork;

import edu.tmc.uth.teo.model.AssemblyMethod;
import edu.tmc.uth.teo.model.DirectedAcyclicGraph;
import edu.tmc.uth.teo.model.TemporalRelationHalf;
import edu.tmc.uth.teo.model.TemporalRelationType;

/**
 * We implemented the Kahn Algorithm to find the topology order
 * 
 * @author yluo
 *
 */
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
	
	/**
	 * The helper function to get TemporalRelationType from the relation string.
	 */
	public static TemporalRelationType getTemporalRelationType(String relationStr) {
		// interval relations
		if (relationStr.equals("before")) return TemporalRelationType.BEFORE;
		if (relationStr.equals("after")) return TemporalRelationType.AFTER;
		if (relationStr.equals("meet")) return TemporalRelationType.MEET;
		if (relationStr.equals("metBy")) return TemporalRelationType.METBY;
		if (relationStr.equals("during")) return TemporalRelationType.DURING;
		if (relationStr.equals("contain")) return TemporalRelationType.CONTAIN;
		if (relationStr.equals("overlap")) return TemporalRelationType.OVERLAP;
		if (relationStr.equals("overlappedBy")) return TemporalRelationType.OVERLAPPEDBY;
		if (relationStr.equals("start")) return TemporalRelationType.START;
		if (relationStr.equals("startedBy")) return TemporalRelationType.STARTEDBY;
		if (relationStr.equals("finish")) return TemporalRelationType.FINISH;
		if (relationStr.equals("finishedBy")) return TemporalRelationType.FINISHEDBY;
		if (relationStr.equals("equal")) return TemporalRelationType.EQUAL;
		if (relationStr.equals("before")) return TemporalRelationType.BEFORE;
		// point relations
		if (relationStr.equals("startBeforeStart")) return TemporalRelationType.START_BEFORE_START;
		if (relationStr.equals("startAfterStart")) return TemporalRelationType.START_AFTER_START;
		if (relationStr.equals("startEqualStart")) return TemporalRelationType.START_EQUAL_START;
		if (relationStr.equals("endBeforeEnd")) return TemporalRelationType.END_BEFORE_END;
		if (relationStr.equals("endAfterEnd")) return TemporalRelationType.END_AFTER_END;
		if (relationStr.equals("endEqualEnd")) return TemporalRelationType.END_EQUAL_END;
		if (relationStr.equals("startBeforeEnd")) return TemporalRelationType.START_BEFORE_END;
		if (relationStr.equals("startAfterEnd")) return TemporalRelationType.START_AFTER_END;
		if (relationStr.equals("startEqualEnd")) return TemporalRelationType.START_EQUAL_END;
		if (relationStr.equals("endBeforeStart")) return TemporalRelationType.END_BEFORE_START;
		if (relationStr.equals("endAfterStart")) return TemporalRelationType.END_AFTER_START;
		if (relationStr.equals("endEqualStart")) return TemporalRelationType.END_EQUAL_START;
		// full
		return TemporalRelationType.FULL;
	}
	
	public static ArrayList<TemporalRelationType> getTemporalRelationTypeList(ArrayList<String> relationStrList) {
		ArrayList<TemporalRelationType> resultList = null;
		if (relationStrList != null) {
			resultList = new ArrayList<TemporalRelationType>();
			for (String relationStr : relationStrList) {
				resultList.add(getTemporalRelationType(relationStr));
			}
		}
		return resultList;
	}
	
	/**
	 * The helper function to get TemporalRelationCode from the relation type.
	 */
	public static short getTemporalRelationCode(TemporalRelationType relationType) {
		switch (relationType) {
			// interval relations
			case AFTER: return ConstraintNetwork.bin_after;
			case BEFORE: return ConstraintNetwork.bin_before;
			case MEET: return ConstraintNetwork.bin_meets;
			case METBY: return ConstraintNetwork.bin_metby;
			case FINISH: return ConstraintNetwork.bin_finishes;
			case FINISHEDBY: return ConstraintNetwork.bin_finishedby;
			case START: return ConstraintNetwork.bin_starts;
			case STARTEDBY: return ConstraintNetwork.bin_startedby;
			case OVERLAP: return ConstraintNetwork.bin_overlaps;
			case OVERLAPPEDBY: return ConstraintNetwork.bin_overlappedby;
			case EQUAL: return ConstraintNetwork.bin_equals;
			case CONTAIN: return ConstraintNetwork.bin_contains;
			case DURING: return ConstraintNetwork.bin_during;
			// point relations
			case START_BEFORE_START: return ConstraintNetwork.bin_SBS;
			case START_AFTER_START: return ConstraintNetwork.bin_SAS;
			case START_EQUAL_START: return ConstraintNetwork.bin_SES;
			case START_BEFORE_END: return ConstraintNetwork.bin_SBE;
			case START_AFTER_END: return ConstraintNetwork.bin_SAE;
			case START_EQUAL_END: return ConstraintNetwork.bin_SEE;
			case END_BEFORE_START: return ConstraintNetwork.bin_EBS;
			case END_AFTER_START: return ConstraintNetwork.bin_EAS;
			case END_EQUAL_START: return ConstraintNetwork.bin_EES;
			case END_BEFORE_END: return ConstraintNetwork.bin_EBE;
			case END_AFTER_END: return ConstraintNetwork.bin_EAE;
			case END_EQUAL_END: return ConstraintNetwork.bin_EEE;
			// full
			default: return ConstraintNetwork.bin_all;
		}
	}
	
	/**
	 * To merge relations between a pair of nodes to minimize the label
	 * @param relationList
	 * @return
	 */
	public static short getMergedTemporalRelationCode(ArrayList<TemporalRelationHalf> relationList) {
		short result = ConstraintNetwork.bin_all;
		if (relationList != null && !relationList.isEmpty()) {
			TemporalRelationHalf relation = relationList.get(0); // the first one
			result = getTemporalRelationCode(relation.getRelationType());
			for (int i = 1; i < relationList.size(); i++) {
				relation = relationList.get(i);
				if (relation.getAssemblyMethod().equals(AssemblyMethod.ASSERTED)) {
					result |= getTemporalRelationCode(relation.getRelationType()); // union for "Asserted Relations"
				} else {
					result &= getTemporalRelationCode(relation.getRelationType()); // intersection for "Inferred Relations"
				}
			}
		}
		return result;
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
	
	/**
	 * For a list of relations, they should be consistent.
	 * @param relationList
	 * @return
	 */
	public static boolean isStartBeforeStart(ArrayList<TemporalRelationType> relationList) {
		if (relationList != null) {
			for (TemporalRelationType relation : relationList) {
				if (!isStartBeforeStart(relation)) {
					return false;
				}
			}
			if (!relationList.isEmpty()) {
				return true;
			}
		}
		return false;
	}
}
