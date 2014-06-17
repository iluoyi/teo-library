package edu.tmc.uth.teo.main;

public class TEOConstants {
	
	/**
	 * Name Spaces
	 */
	public static final String TEO_NS = "http://informatics.mayo.edu/TEO.owl#";
	
	/**
	 * Class Names
	 */
	public static final String TEO_EVENT_CLS_NAME = "TEO_0000025"; // Event
	public static final String TEO_HOLIDAY_CLS_NAME = "TEO_0000011"; // Holiday
	public static final String TEO_TIMEGRANULARITY_CLS_NAME = "TEO_0000012"; // Time Granularity
	public static final String TEO_FREQUENCY_CLS_NAME = "TEO_0000023"; // Frequency
	public static final String TEO_TIMEPERIOD_CLS_NAME = "TEO_0000036"; // TimePeriod
	public static final String TEO_TIMEPHASE_CLS_NAME = "TEO_0000024"; // TimePhase
	public static final String TEO_DURATIONMEASUREMENT_CLS_NAME = "TEO_0000032"; // Duration Measurement
	public static final String TEO_PERIODICTIMEINTERVAL_CLS_NAME = "TEO_0000034"; // Periodic Time Interval
	public static final String TEO_TIMESEQUENCE_CLS_NAME = "TEO_0000044"; // TimeSequence
	public static final String TEO_DAY_CLS_NAME = "TEO_0000095"; // Day
	public static final String TEO_WEEK_CLS_NAME = "TEO_0000030"; // Week
	public static final String TEO_MONTH_CLS_NAME = "TEO_0000050"; // Month
	public static final String TEO_YEAR_CLS_NAME = "TEO_0000051"; // Year
	
	
	/**
	 * Object Property Names
	 */	
	// events
	public static final String TEO_HASVALIDTIME_PRP_NAME = "TEO_0000007"; // hasValidTime [domain: Event (Yi: + TimeInstant?)]
	// time interval
	public static final String TEO_HASSTARTTIME_PRP_NAME = "TEO_0000028"; // hasStartTime [domain: TimeInterval + Periodic TimeInterval]
	public static final String TEO_HASENDTIME_PRP_NAME = "TEO_0000006"; // hasEndTime [domain: TimeInterval + Periodic TimeInterval]
	public static final String TEO_HASDURATION_PRP_NAME = "TEO_0000022"; // hasDuration [domain: TimeInterval + Periodic TimeInterval, range: Duration Measurement]
	public static final String TEO_HASDURATIONUNIT_PRP_NAME = "TEO_0000040"; // domain: Duration Measurement
	public static final String TEO_HASPHASE_PRP_NAME = "TEO_0000077"; // domain: Periodic TimeInterval, range: TimePhase
	public static final String TEO_HASPERIOD_PRP_NAME = "TEO_0000078"; // domain: Periodic TimeInterval, range: TimePeriod
	// frequency
	public static final String TEO_DENOMINATOR_PRP_NAME = "TEO_0000076"; // domain: Frequency
	public static final String TEO_HASFREQUENCY_PRP_NAME = "TEO_0000079"; // range: Frequency
	// temporal relations
	public static final String TEO_TEMPORALRELATION_PRP_NAME = "TEO_0000039";
	public static final String TEO_TR_AFTER_PRP_NAME = "TEO_0000016";
	public static final String TEO_TR_BEFORE_PRP_NAME = "TEO_0000002";
	public static final String TEO_TR_MEET_PRP_NAME = "TEO_0000020";
	public static final String TEO_TR_OVERLAP_PRP_NAME = "TEO_0000038";
	public static final String TEO_TR_CONTAIN_PRP_NAME = "TEO_0000010";
	public static final String TEO_TR_DURING_PRP_NAME = "TEO_0000003";
	public static final String TEO_TR_EQUAL_PRP_NAME = "TEO_0000018";
	public static final String TEO_TR_FINISH_PRP_NAME = "TEO_0000037";
	public static final String TEO_TR_START_PRP_NAME = "TEO_0000014";
	// occur
	public static final String TEO_OCCUR_PRP_NAME = "TEO_0000085"; // domain: ConnectedTemporalRegion (Yi: + Event?)
	public static final String TEO_OCCURYEAR_PRP_NAME = "TEO_0000132";
	public static final String TEO_OCCURMONTH_PRP_NAME = "TEO_0000086";
	public static final String TEO_OCCURWEEK_PRP_NAME = "TEO_0000090";
	public static final String TEO_OCCURDAY_PRP_NAME = "TEO_0000087";
	// time sequence
	public static final String TEO_HASTIMESEQUENCE_PRP_NAME = "TEO_0000096";// domain: ConnectedTemporalRegion + ScatteredTemporalRegion

	
	/**
	 * Data Property Names
	 */
	public static final String TEO_HASMODALITY_PRP_NAME = "TEO_0000004";
	public static final String TEO_HASNORMALIZEDTIME_PRP_NAME = "TEO_0000005";
	public static final String TEO_HASORIGTIME_PRP_NAME = "TEO_0000015";
	public static final String TEO_HASDURATIONVALUE_PRP_NAME = "TEO_0000041"; // hasDurationValue
	public static final String TEO_HASCALENDARPATTERNFORM_PRP_NAME = "TEO_0000064"; // hasDurationValue
	public static final String TEO_NUMERATOR_PRP_NAME = "TEO_0000076"; // domain: Frequency

	
	/**
	 *  Classes
	 */
	public static final String TEMPORAL_INSTANT_CLS = "http://www.ifomis.org/bfo/1.1/span#TemporalInstant";
	public static final String TEMPORAL_INTERVAL_CLS = "http://www.ifomis.org/bfo/1.1/span#TemporalInterval";
	public static final String PERIODIC_TIME_INTERVAL_CLS = getWithNS(TEO_PERIODICTIMEINTERVAL_CLS_NAME);
	
	public static final String TEO_EVENT_CLS = getWithNS(TEO_EVENT_CLS_NAME);
	public static final String TEO_HOLIDAY_CLS = getWithNS(TEO_HOLIDAY_CLS_NAME);
	public static final String TEO_TIMEGRANULARITY_CLS = getWithNS(TEO_TIMEGRANULARITY_CLS_NAME);
	public static final String TEO_FREQUENCY_CLS = getWithNS(TEO_FREQUENCY_CLS_NAME);
	public static final String TEO_TIMEPERIOD_CLS = getWithNS(TEO_TIMEPERIOD_CLS_NAME);
	public static final String TEO_TIMEPHASE_CLS = getWithNS(TEO_TIMEPHASE_CLS_NAME);
	public static final String TEO_DURATIONMEASUREMENT_CLS = getWithNS(TEO_DURATIONMEASUREMENT_CLS_NAME);
	public static final String TEO_TIMESEQUENCE_CLS = getWithNS(TEO_TIMESEQUENCE_CLS_NAME);
	public static final String TEO_DAY_CLS = getWithNS(TEO_DAY_CLS_NAME);
	public static final String TEO_WEEK_CLS = getWithNS(TEO_WEEK_CLS_NAME);
	public static final String TEO_MONTH_CLS = getWithNS(TEO_MONTH_CLS_NAME);
	public static final String TEO_YEAR_CLS = getWithNS(TEO_YEAR_CLS_NAME);
	
	
	/**
	 *  Object Properties
	 */
	public static final String TEO_HASVALIDTIME_PRP = getWithNS(TEO_HASVALIDTIME_PRP_NAME);
	public static final String TEO_HASSTARTTIME_PRP = getWithNS(TEO_HASSTARTTIME_PRP_NAME);
	public static final String TEO_HASENDTIME_PRP = getWithNS(TEO_HASENDTIME_PRP_NAME);
	public static final String TEO_HASDURATION_PRP = getWithNS(TEO_HASDURATION_PRP_NAME);
	public static final String TEO_HASDURATIONUNIT_PRP = getWithNS(TEO_HASDURATIONUNIT_PRP_NAME);
	public static final String TEO_HASPHASE_PRP = getWithNS(TEO_HASPHASE_PRP_NAME);
	public static final String TEO_HASPERIOD_PRP = getWithNS(TEO_HASPERIOD_PRP_NAME);
	public static final String TEO_DENOMINATOR_PRP = getWithNS(TEO_DENOMINATOR_PRP_NAME);
	public static final String TEO_HASFREQUENCY_PRP = getWithNS(TEO_HASFREQUENCY_PRP_NAME);
	public static final String TEO_TEMPORALRELATION_PRP = getWithNS(TEO_TEMPORALRELATION_PRP_NAME);
	public static final String TEO_TR_AFTER_PRP = getWithNS(TEO_TR_AFTER_PRP_NAME);
	public static final String TEO_TR_BEFORE_PRP = getWithNS(TEO_TR_BEFORE_PRP_NAME);
	public static final String TEO_TR_MEET_PRP = getWithNS(TEO_TR_MEET_PRP_NAME);
	public static final String TEO_TR_OVERLAP_PRP = getWithNS(TEO_TR_OVERLAP_PRP_NAME);
	public static final String TEO_TR_CONTAIN_PRP = getWithNS(TEO_TR_CONTAIN_PRP_NAME);
	public static final String TEO_TR_DURING_PRP = getWithNS(TEO_TR_DURING_PRP_NAME);
	public static final String TEO_TR_EQUAL_PRP = getWithNS(TEO_TR_EQUAL_PRP_NAME);
	public static final String TEO_TR_FINISH_PRP = getWithNS(TEO_TR_FINISH_PRP_NAME);
	public static final String TEO_TR_START_PRP = getWithNS(TEO_TR_START_PRP_NAME);
	public static final String TEO_OCCUR_PRP = getWithNS(TEO_OCCUR_PRP_NAME);
	public static final String TEO_OCCURYEAR_PRP = getWithNS(TEO_OCCURYEAR_PRP_NAME);
	public static final String TEO_OCCURMONTH_PRP = getWithNS(TEO_OCCURMONTH_PRP_NAME);
	public static final String TEO_OCCURWEEK_PRP = getWithNS(TEO_OCCURWEEK_PRP_NAME);
	public static final String TEO_HASTIMESEQUENCE_PRP = getWithNS(TEO_HASTIMESEQUENCE_PRP_NAME);
	public static final String TEO_HASMODALITY_PRP = getWithNS(TEO_HASMODALITY_PRP_NAME);
	public static final String TEO_HASNORMALIZEDTIME_PRP = getWithNS(TEO_HASNORMALIZEDTIME_PRP_NAME);
	public static final String TEO_HASORIGTIME_PRP = getWithNS(TEO_HASORIGTIME_PRP_NAME);
	public static final String TEO_HASDURATIONVALUE_PRP = getWithNS(TEO_HASDURATIONVALUE_PRP_NAME);
	public static final String TEO_HASCALENDARPATTERNFORM_PRP = getWithNS(TEO_HASCALENDARPATTERNFORM_PRP_NAME);
	public static final String TEO_NUMERATOR_PRP = getWithNS(TEO_NUMERATOR_PRP_NAME);
	
	
	/**
	 *  Data Properties
	 */
	public static String getWithNS(String name) {
		return TEO_NS + name;
	}

}
