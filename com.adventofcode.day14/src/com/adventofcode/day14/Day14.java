package com.adventofcode.day14;

import java.util.ArrayList;
import java.util.List;

public class Day14 {

	private static final int INPUT = 5;
	List<Integer> score = new ArrayList<Integer>();
	int elf1current = 0;
	int elf2current = 1;

	public static void main(String[] args) {
		new Day14().run();
	}

	private void run() {
		// initialize
		score.add(3);
		score.add(7);

		while (score.size() < INPUT + 10) {
			computeRecipes ();
			elf1current = shiftElf (elf1current);
			elf2current = shiftElf (elf2current);
			//display(0, true);
		}
		String res = display(INPUT, false);
		// part 2, looking for res pattern in score
		String flattenScore = flatten();
		int index = flattenScore.indexOf(res);
		System.out.println("part2=" + flattenScore.substring(index, index + 5));
	}

	private String flatten() {
		StringBuilder sb = new StringBuilder();
		for (Integer integer : score) {
			sb.append(integer);
		}
		return sb.toString ();
	}

	private String display(int start, boolean spaces) {
		StringBuilder sb = new StringBuilder();
		for (int i =start; i < score.size(); i++) {
			if (spaces && i == elf1current) sb.append("("); 
			if (spaces && i == elf2current) sb.append("["); 
			sb.append(score.get(i));
			if (spaces && i == elf2current) sb.append("]"); 
			if (spaces && i == elf1current) sb.append(")"); 

			if (spaces )sb.append (" ");
		}
		System.out.println(sb);
		return sb.toString();
	}

	private int shiftElf(int current) {
		int shift = 1 + score.get(current);
		if (current + shift < score.size()) return current+shift;
		else {
			return (current + shift) % score.size();
		}
		
	}

	private void computeRecipes() {
		int res = score.get(elf1current) + score.get(elf2current);
		if (res < 10) score.add(res);
		else {
			score.add (1);
			score.add (res % 10);
		}
		
	}

}
