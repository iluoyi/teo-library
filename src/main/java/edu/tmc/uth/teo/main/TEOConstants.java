package edu.tmc.uth.teo.main;


public class TEOConstants 
{
	public static final String TEO_TR_NS = "http://informatics.mayo.edu/TEOTemporalRelation#";
	public static final String TEO_NS = "http://informatics.mayo.edu/TEO.owl#";
	//public static final String AELST_NS = "http://www.TEO.org/AE-lst.owl#";
	
	public static final String AELST_NS = TEO_NS;
	
	// Class Names
	public static final String TEO_EVENT_CLS_NAME = "TEO_0000025"; //http://informatics.mayo.edu/TEO.owl#TEO_0000025
	public static final String TEO_TIMEINSTANCE_CLS_NAME = "TimeInstant";
	public static final String TEO_TIMEINTERVAL_CLS_NAME = "TimeInterval";
	public static final String TEO_TIMEPERIOD_CLS_NAME = "TimePeriod";
	public static final String TEO_TIMEPHASE_CLS_NAME = "TimePhase";
	public static final String TEO_DURATION_CLS_NAME = "Duration";

	public static final String TEO_TR_TEMPORAL_RELATION_STMT_CLS_NAME = "TemporalRelationStatement";
	
	// Property Names
	public static final String TEO_HASVALIDTIME_PRP_NAME = "hasValidTime";
	public static final String TEO_HASNOTETIME_PRP_NAME = "hasNoteTime";
	public static final String TEO_HASPERIOD_PRP_NAME = "hasPeriod";
	public static final String TEO_TEMPORALRELATION_PRP_NAME = "temporalRelation";
	public static final String TEO_HASORIGINALTIME_PRP_NAME = "hasOrigTime";
	public static final String TEO_HASNORMALIZEDTIME_PRP_NAME = "hasNormalizedTime";
	public static final String TEO_HASMODALITY_PRP_NAME = "hasModality";
	public static final String TEO_HASSTARTTIME_PRP_NAME = "hasStartTime";
	public static final String TEO_HASENDTIME_PRP_NAME = "hasEndTime";
	public static final String TEO_HASDURATION_PRP_NAME = "hasDuration";
	public static final String TEO_HASDURATIONVALUE_PRP_NAME = "hasDurationValue";
	public static final String TEO_HASDURATIONUNIT_PRP_NAME = "hasDurationUnit";
	public static final String TEO_HASOFFSET_PRP_NAME = "hasOffset";
	public static final String TEO_HASTIMEOFFSET_PRP_NAME = "hasTimeOffset";
	public static final String TEO_HASGRANULARITY_PRP_NAME = "hasGranularity";
	
	public static final String TEO_TR_AFTER_PRP_NAME = "after";
	public static final String TEO_TR_BEFORE_PRP_NAME = "before";
	public static final String TEO_TR_MEET_PRP_NAME = "meet";
	public static final String TEO_TR_OVERLAP_PRP_NAME = "overlap";
	public static final String TEO_TR_CONTAIN_PRP_NAME = "contain";
	public static final String TEO_TR_DURING_PRP_NAME = "during";
	public static final String TEO_TR_EQUAL_PRP_NAME = "equal";
	public static final String TEO_TR_FINISH_PRP_NAME = "finish";
	public static final String TEO_TR_START_PRP_NAME = "start";
	
	public static final String TEO_TR_CONTINUES_THROUGH_PRP_NAME = "continues_through";
	public static final String TEO_TR_INCLUDE_PRP_NAME = "include";
	public static final String TEO_TR_INITIATE_PRP_NAME = "initiate";
	public static final String TEO_TR_IS_INCLUDED_PRP_NAME = "is_included";
	
	public static final String TEO_TR_OVERLAPPED_BY_PRP_NAME = "overlapped_by";
	public static final String TEO_TR_SIMULTANEOUS_PRP_NAME = "simultaneous";
	public static final String TEO_TR_TERMINATE_PRP_NAME = "terminate";
	public static final String TEO_TR_SAMEAS_PRP_NAME = "sameas";
	
	
	public static final String TEO_TR_TEMPORAL_SUBJECT_PRP_NAME = "temporalSubject";
	public static final String TEO_TR_TEMPORAL_OBJECT_PRP_NAME = "temporalObject";
	public static final String TEO_TR_TEMPORAL_PREDICATE_PRP_NAME = "temporalPredicate";

	
	// Classes
	public static final String TEO_EVENT_CLS = getWithNS(TEO_EVENT_CLS_NAME);
	public static final String TEO_TIMEINSTANCE_CLS = getWithNS(TEO_TIMEINSTANCE_CLS_NAME);
	public static final String TEO_TIMEINTERVAL_CLS = getWithNS(TEO_TIMEINTERVAL_CLS_NAME);
	public static final String TEO_TIMEPERIOD_CLS = getWithNS(TEO_TIMEPERIOD_CLS_NAME);
	public static final String TEO_TIMEPHASE_CLS = getWithNS(TEO_TIMEPHASE_CLS_NAME);
	public static final String TEO_DURATION_CLS = getWithNS(TEO_DURATION_CLS_NAME);
	
	public static final String TEO_TR_TEMPORAL_RELATION_STMT_CLS = getWithNS(TEO_TR_TEMPORAL_RELATION_STMT_CLS_NAME);
	
	// Properties
	public static final String TEO_HASVALIDTIME_PRP = getWithNS(TEO_HASVALIDTIME_PRP_NAME, AELST_NS);
	public static final String TEO_HASNOTETIME_PRP = getWithNS(TEO_HASNOTETIME_PRP_NAME, AELST_NS);
	public static final String TEO_HASPERIOD_PRP = getWithNS(TEO_HASPERIOD_PRP_NAME, AELST_NS);
	public static final String TEO_TEMPORALRELATION_PRP = getWithNS(TEO_TEMPORALRELATION_PRP_NAME, TEO_TR_NS);
	public static final String TEO_HASORIGINALTIME_PRP = getWithNS(TEO_HASORIGINALTIME_PRP_NAME, AELST_NS);
	public static final String TEO_HASNORMALIZEDTIME_PRP = getWithNS(TEO_HASNORMALIZEDTIME_PRP_NAME, AELST_NS);
	public static final String TEO_HASMODALITY_PRP = getWithNS(TEO_HASMODALITY_PRP_NAME, AELST_NS);
	public static final String TEO_HASSTARTTIME_PRP = getWithNS(TEO_HASSTARTTIME_PRP_NAME, AELST_NS);
	public static final String TEO_HASENDTIME_PRP = getWithNS(TEO_HASENDTIME_PRP_NAME, AELST_NS);
	public static final String TEO_HASDURATION_PRP = getWithNS(TEO_HASDURATION_PRP_NAME);
	public static final String TEO_HASDURATIONVALUE_PRP = getWithNS(TEO_HASDURATIONVALUE_PRP_NAME);
	public static final String TEO_HASDURATIONUNIT_PRP = getWithNS(TEO_HASDURATIONUNIT_PRP_NAME);
	public static final String TEO_HASOFFSET_PRP = getWithNS(TEO_HASOFFSET_PRP_NAME, AELST_NS);
	public static final String TEO_HASTIMEOFFSET_PRP = getWithNS(TEO_HASTIMEOFFSET_PRP_NAME, AELST_NS);
	
	public static final String TEO_TR_AFTER_PRP = getWithNS(TEO_TR_AFTER_PRP_NAME);
	public static final String TEO_TR_BEFORE_PRP = getWithNS(TEO_TR_BEFORE_PRP_NAME);
	public static final String TEO_TR_MEET_PRP = getWithNS(TEO_TR_MEET_PRP_NAME);
	public static final String TEO_TR_OVERLAP_PRP = getWithNS(TEO_TR_OVERLAP_PRP_NAME);
	public static final String TEO_TR_CONTAIN_PRP = getWithNS(TEO_TR_CONTAIN_PRP_NAME);
	public static final String TEO_TR_DURING_PRP = getWithNS(TEO_TR_DURING_PRP_NAME);
	public static final String TEO_TR_EQUAL_PRP = getWithNS(TEO_TR_EQUAL_PRP_NAME);
	public static final String TEO_TR_FINISH_PRP = getWithNS(TEO_TR_FINISH_PRP_NAME);
	public static final String TEO_TR_START_PRP = getWithNS(TEO_TR_START_PRP_NAME);
	
	public static final String TEO_TR_CONTINUES_THROUGH_PRP = getWithNS(TEO_TR_CONTINUES_THROUGH_PRP_NAME);
	public static final String TEO_TR_INCLUDE_PRP = getWithNS(TEO_TR_INCLUDE_PRP_NAME);
	public static final String TEO_TR_INITIATE_PRP = getWithNS(TEO_TR_INITIATE_PRP_NAME);
	public static final String TEO_TR_IS_INCLUDED_PRP = getWithNS(TEO_TR_IS_INCLUDED_PRP_NAME);
	public static final String TEO_TR_OVERLAPPED_BY_PRP = getWithNS(TEO_TR_OVERLAPPED_BY_PRP_NAME);
	public static final String TEO_TR_SIMULTANEOUS_PRP = getWithNS(TEO_TR_SIMULTANEOUS_PRP_NAME);
	public static final String TEO_TR_TERMINATE_PRP = getWithNS(TEO_TR_TERMINATE_PRP_NAME);
	public static final String TEO_TR_SAMEAS_PRP = getWithNS(TEO_TR_SAMEAS_PRP_NAME);

	public static final String TEO_TR_TEMPORAL_SUBJECT_PRP = getWithNS(TEO_TR_TEMPORAL_SUBJECT_PRP_NAME, AELST_NS);
	public static final String TEO_TR_TEMPORAL_OBJECT_PRP = getWithNS(TEO_TR_TEMPORAL_OBJECT_PRP_NAME, AELST_NS);
	public static final String TEO_TR_TEMPORAL_PREDICATE_PRP = getWithNS(TEO_TR_TEMPORAL_PREDICATE_PRP_NAME, AELST_NS);

	public static String getWithNS(String name)
	{
		return TEO_NS + name;
	}
	
	public static String getWithNS(String name, String namespace)
	{
		return namespace + name;
	}
}
