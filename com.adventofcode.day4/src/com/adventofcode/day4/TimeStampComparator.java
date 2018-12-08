package com.adventofcode.day4;

import java.util.Comparator;

public class TimeStampComparator implements Comparator<TimeStamp> {

	@Override
	public int compare(TimeStamp ts1, TimeStamp ts2) {		
		return ts1.getMoment().compareTo(ts2.getMoment());
	}

}
