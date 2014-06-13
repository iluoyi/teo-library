package edu.tmc.uth.teo.test;

import java.util.Date;
import java.util.List;

import org.ocpsoft.prettytime.nlp.PrettyTimeParser;

public class Test {
	 public static void main(String[] args) {
	      List<Date> dates = new PrettyTimeParser().parse("2013");// bug1: 2008
	      
	      Date parsedDate = dates.get(0);
	      System.out.println(parsedDate);
	   }
}
