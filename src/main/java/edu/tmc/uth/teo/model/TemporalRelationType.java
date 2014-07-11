package edu.tmc.uth.teo.model;

/**
 * An enumeration of Allen's Interval Algebra and Point Relations
 * 
 * @author yluo
 *
 */
public enum TemporalRelationType {
	BEFORE, AFTER, MEET, METBY, OVERLAP, OVERLAPPEDBY, DURING, CONTAIN, EQUAL, FINISH, FINISHEDBY, START, STARTEDBY, // Allen's interval relations
	START_BEFORE_START, START_BEFORE_END, END_BEFORE_START, END_BEFORE_END, START_AFTER_START, STAR_AFTER_END, END_AFTER_START, END_AFTER_END, // point relations (no equal for now)
	FULL // full relation
}