package com.adventofcode.day2;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day2 {
	int hasTwoCount;
	int hasThreeCount;
	
	public static void main(String[] args) {
		if (args.length != 1) {
			System.err.println("bad argument");
			return;
		}
		String file = args[0];
		File fic = new File (file);
		try {
			new Day2().run(fic);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void run(File fic) throws IOException {
		List<String> allreadyLoaded = new ArrayList<String>();
		FileInputStream fis = new FileInputStream(fic);
		DataInputStream dis = new DataInputStream(fis);
		while (dis.available() > 0) {
			String str = dis.readLine();
			Map<Character, Integer> count = counting (str);
			if (hasTwo (count)) hasTwoCount++;
			if (hasThree (count)) hasThreeCount++;
			for (String string : allreadyLoaded) {
				int diff = nbLetterDiff(string, str);
				if (diff == 1) 
					System.out.println("found one : " + str +" and " + string);
			}
			
			allreadyLoaded.add(str);
		}
		fis.close();
		System.out.println ("two="+hasTwoCount + " three="+hasThreeCount + " checksum=" + hasThreeCount*hasTwoCount);
	}
	
	int nbLetterDiff (String str1, String str2) {
		int nbDiff = 0;
		if (str1.length() != str2.length()) throw new RuntimeException("NONSENSE§§§§");
		for (int i=0; i<str1.length(); i++) {
			char c1 = str1.charAt(i);
			char c2 = str2.charAt(i);
			if (c1!= c2) nbDiff++;
		}
		return nbDiff;
	}

	private Map<Character, Integer> counting(String str) {
		Map<Character, Integer> map = new HashMap<Character, Integer>();
		for (int i=0; i<str.length(); i++) {
			char c = str.charAt(i);
			Integer nb = map.get(c);
			if (nb == null) map.put(c, 1); 
			else map.put(c, ++nb);			
		}
		return map;
	}

	private boolean hasThree(Map<Character, Integer> count) {
		for (Character key : count.keySet()) {
			Integer res = count.get(key);
			if (res == 3) return true;
		}
		return false;
	}

	private boolean hasTwo(Map<Character, Integer> count) {
		for (Character key : count.keySet()) {
			Integer res = count.get(key);
			if (res == 2) return true;
		}
		return false;
	}
}
