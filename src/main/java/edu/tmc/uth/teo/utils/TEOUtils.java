package edu.tmc.uth.teo.utils;

public class TEOUtils {
	public static boolean isNull(String str) {
		if ((str == null)||("null".equalsIgnoreCase(str.trim()))||("".equalsIgnoreCase(str.trim())))
			return true;
		return false;
	}
	
}
