package com.adventofcode.day1;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Day1 {
	static int sum = 0;
	static Set<Integer> passed = new HashSet<Integer> ();
	static boolean foundFirstDouble = false;
	static int firstDouble;
	static int numberOfLine = 0;

	public static void main(String[] args) throws IOException {
		if (args.length != 1) {
			System.err.println("bad argument");
			return;
		}
		String file = args[0];
		File fic = new File (file);

		passed.add(sum);
	
		while (! foundFirstDouble) {
			FileInputStream fis = new FileInputStream(fic);
			DataInputStream dis = new DataInputStream(fis);
			while (dis.available() > 0) {
				String str = dis.readLine();
				numberOfLine ++;
				int num = Integer.parseInt(str);
				sum += num;
				
				if (passed.contains(sum)&& ! foundFirstDouble ) {
					foundFirstDouble = true;
					firstDouble = sum;
				}
				passed.add(sum);
			}
			System.out.println ("LOOP sum = " + sum + " lines "+numberOfLine+ " firstDOuble = "+ firstDouble);
		}
		System.out.println ("END sum = " + sum + " lines "+numberOfLine+ " firstDOuble = "+ firstDouble);
	}

}
