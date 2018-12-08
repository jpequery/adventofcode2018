package com.adventofcode.day5;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Day5 {

	public static void main(String[] args) {
		if (args.length != 1) {
			System.err.println("bad argument");
			return;
		}
		String file = args[0];
		File fic = new File (file);
		try {
			new Day5().run(fic);
		} catch (IOException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void run(File fic) throws IOException, Exception {
		FileInputStream fis = new FileInputStream(fic);
		DataInputStream dis = new DataInputStream(fis);
		String data = "";
		while (dis.available() > 0) {
			data = dis.readLine();
		}
		String str = doCompleteReduce(data);
		System.out.println("remaining items : " + str.length());
		
		int best = str.length();
		for (char c = 'A'; c <= 'Z'; c++) {
			String data2 = removeCharFromString (data, c);
			String res=doCompleteReduce(data2);
			if (res.length() < best)
				best = res.length();
		}
		System.out.println("best reduction " + best);
	}

	private String removeCharFromString(String data, char c) {
		String inter = data.replaceAll(String.valueOf(c), "");
		return inter.replaceAll(String.valueOf(c).toLowerCase(), "");
	}

	private String doCompleteReduce(String str) {
		int length;
		do {
			length = str.length(); // ruse de chacal puant
			str = reduce (str);
		} while (str.length() != length);
		return str;
	}

	private String reduce(String str) {
		for (int i= 1; i < str.length(); i++) {
			char a = str.charAt(i);
			char b = str.charAt(i-1);
			
			if (canReduce(a,b)) {
				String end = str.substring(i+1);
				String begin = str.substring(0, i-1);
				return begin + end;
			}
		}
		return str;
	}

	private boolean canReduce(char a, char b) {
		if (a+0 == b+32) return true;
		if (a+32 == b+0) return true;
		return false;
	}

}
