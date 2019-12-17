package com.adventofcode.day12;

import java.util.ArrayList;
import java.util.List;

class Pattern  {
	boolean source[] = new boolean [5];
	boolean result;
	String definition;
	
	public String toString () {
		return definition;
	}
	
	public Pattern (String def) {
		for (int i=0; i<5; i++) {
			Character c = def.charAt(i);
			source[i] = c=='#';
			result = def.charAt(9) == '#';
			definition = def;
		}
	}
	boolean isApplicablePattern (String str, int pos) {
		boolean applicable = true;
		String sub = extractSub (str, pos);
		for (int i = 0; i < 5; i++) {
			if ((sub.charAt(i)=='#') != source[i]) applicable = false;
		}
		return applicable;
	}
	
	private String extractSub(String str, int pos) {
		String result = "";
		for (int i = pos - 2; i< pos +3; i++) {
			if (i < 0) result += ".";
			else if (i >= str.length()) result += ".";
			else result += str.charAt(i);
		}
		return result;
	}

	public boolean getResult() {
		return result;	
	}
}

public class Day12 {
	private static final long  NB_GENERATION = 50000;
	String input = "##..##..#.##.###....###.###.#.#.######.#.#.#.#.##.###.####..#.###...#######.####.##...#######.##..#";
	List<Pattern> patterns = new ArrayList<Pattern>();
	
	public static void main(String[] args) {
		new Day12().run();
	}

	private void run() {
		buildPatterns ();
		input = ".........." + input + "..........";
		int addedPadding = 10;
		for (long day = 1; day <= NB_GENERATION; day ++) {
			char[] chars = input.toCharArray();
			for (int i=0; i < input.length(); i++) {
				List<Pattern> applicablePatterns = getApplicablePatterns (input, i);
				if (applicablePatterns.size() == 0)
					System.out.print("no applicable pattern");
				if (applicablePatterns.size() > 1)
					System.out.print("too much applicable pattenr");
				boolean res = applicablePatterns.get(0).getResult ();

				chars[i] = res?'#':'.';
			}
			input = String.valueOf(chars);
			if (! input.startsWith("..")) {
				input = ".." + input;
				addedPadding += 2;
			}
			if (! input.endsWith("..")) {
				input += "..";
			}
//			System.out.print(".");
//			if (day % 100 == 0) System.out.println();
			System.out.println("day " +day + " " + input);
			System.out.println("somme des pots=" + nbPot (input, addedPadding));
			
		}	
		
	}

	private int nbPot(String str, int addedPadding) {
		int result = 0;
		for (int i=0; i < str.length(); i++) {
			if (str.charAt(i) == '#') {
				result += i - addedPadding;
			}
		}
		return result;
	}



	private List<Pattern> getApplicablePatterns(String input2, int pos) {
		List<Pattern> result = new ArrayList<Pattern>();
		for (Pattern pattern : patterns) {
			if (pattern.isApplicablePattern(input2, pos))
				result.add(pattern);
		}
		return result;
	}

	private void buildPatterns() {
		patterns.add(new Pattern ("##... => ."));
		patterns.add(new Pattern ("....# => ."));
		patterns.add(new Pattern ("#.##. => ."));
		patterns.add(new Pattern ("..... => ."));
		patterns.add(new Pattern ("..### => ."));
		patterns.add(new Pattern ("###.. => ."));
		patterns.add(new Pattern ("#..#. => #"));
		patterns.add(new Pattern ("##.## => ."));
		patterns.add(new Pattern ("...## => #"));
		patterns.add(new Pattern ("#..## => ."));
		patterns.add(new Pattern ("#.### => ."));
		patterns.add(new Pattern ("#.#.# => #"));
		patterns.add(new Pattern ("####. => ."));
		patterns.add(new Pattern (".###. => #"));
		patterns.add(new Pattern (".##.# => ."));
		patterns.add(new Pattern ("##.#. => #"));
		patterns.add(new Pattern ("...#. => ."));
		patterns.add(new Pattern (".#.#. => ."));
		patterns.add(new Pattern ("#...# => #"));
		patterns.add(new Pattern ("##### => #"));
		patterns.add(new Pattern ("..#.. => ."));
		patterns.add(new Pattern ("..#.# => #"));
		patterns.add(new Pattern ("..##. => ."));
		patterns.add(new Pattern ("###.# => #"));
		patterns.add(new Pattern (".#### => #"));
		patterns.add(new Pattern ("#.... => ."));
		patterns.add(new Pattern (".#..# => #"));
		patterns.add(new Pattern (".##.. => #"));
		patterns.add(new Pattern ("#.#.. => #"));
		patterns.add(new Pattern ("##..# => ."));
		patterns.add(new Pattern (".#... => #"));
		patterns.add(new Pattern (".#.## => #"));
	}
}
