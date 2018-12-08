package com.adventofcode.day4;

import java.awt.Desktop.Action;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeStamp {
	Date timestamp;

	GuardAction action;
	String guardId;
	
	public Date getMoment () {
		return timestamp;
	}
	
	public String getGuard () {
		return guardId;
	}
	
	public GuardAction getAction () {
		return action;
	}
	
	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
	@SuppressWarnings("deprecation")
	public TimeStamp(String str) throws ParseException {
		
		timestamp = sdf.parse(str.substring(1, 17));
		String strAction = str.substring(18);
		if (strAction.contains("wakes"))
			action = GuardAction.WAKE_UP;
		else if (strAction.contains("sleep"))
			action = GuardAction.SLEEP;
		else if (strAction.contains("begins")) {
			action = GuardAction.BEGIN_SHIFT;
			int index = strAction.indexOf("#");
			guardId = strAction.substring(index, strAction.indexOf (" ", index));
		}
	}

}
