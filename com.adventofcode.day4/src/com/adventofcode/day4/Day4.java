package com.adventofcode.day4;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Day4 {
	
	class HourlyGuard {
		String guardId;
		int [] minutes = new int [60];
		
		public HourlyGuard (String id) {
			guardId = id;
		}
		
		public void sleep (int minute) {
			minutes[minute]++;
		}

		public void sleep(Date sleepTime, Date wakeupTime) {
			for (int m = sleepTime.getMinutes(); m <wakeupTime.getMinutes(); m++)
				sleep (m);			
		}
		
		public int getWorstMinuteVal () {
			int result = 0;
			for (int m : minutes) {
				if (m>result) result = m;
			}
			return result;
		}

		public int getWorstMinute () {
			int result = 0;
			int index = 0;
			for (int i=0; i<minutes.length;i++ ) {
				if (minutes[i]>result) {
					result = minutes[i];
					index = i;
				}
			}
			return index;
		}
		
		public int getTotalSleep() {
			int result = 0;
			for (int m :minutes) {
				result += m;
			}
			return result;
		}
		
	}

	public static void main(String[] args) {
		if (args.length != 1) {
			System.err.println("bad argument");
			return;
		}
		String file = args[0];
		File fic = new File (file);
		try {
			new Day4().run(fic);
		} catch (IOException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	private List<TimeStamp> allTimeStamp = new ArrayList<TimeStamp>();

	private void run(File fic) throws IOException, ParseException {
		FileInputStream fis = new FileInputStream(fic);
		DataInputStream dis = new DataInputStream(fis);
		while (dis.available() > 0) {
			String str = dis.readLine();
			TimeStamp ts = new TimeStamp(str);
			allTimeStamp .add(ts);
		}
		allTimeStamp.sort(new TimeStampComparator ());
		
		// now all TS are sorted
		String currentGuard = "";
		Date sleepTime = new Date();
		
		HashMap<String, HourlyGuard> compting = new HashMap<String, HourlyGuard>();
		
		for (TimeStamp timeStamp : allTimeStamp) {
			if (timeStamp.getAction() == GuardAction.BEGIN_SHIFT) {
				currentGuard = timeStamp.getGuard();
			} if (timeStamp.getAction() == GuardAction.SLEEP) {
				sleepTime = timeStamp.getMoment();
			} if (timeStamp.getAction() == GuardAction.WAKE_UP) {
				HourlyGuard current = compting.get(currentGuard);
				if (current == null) {
					current = new HourlyGuard(currentGuard);
				    compting.put(currentGuard, current);
				}
				current.sleep(sleepTime, timeStamp.getMoment());
			}			
		}
		// strategy 1
		HourlyGuard candidat = null;
		for (String guard : compting.keySet()) {
			HourlyGuard s = compting.get(guard);
			if (candidat == null || candidat.getTotalSleep() < s.getTotalSleep())
				candidat = s;
		}
		System.out.println("worst guard = "+candidat.guardId+ " time="+candidat.getWorstMinute());
		// strategy 2
		HourlyGuard candidat2 = null;
		for (String guard : compting.keySet()) {
			HourlyGuard s = compting.get(guard);
			if (candidat2 == null || candidat2.getWorstMinuteVal() < s.getWorstMinuteVal())
				candidat2 = s;
		}
		System.out.println("worst guard = "+candidat2.guardId+ " time="+candidat2.getWorstMinute());
		
	}

}
