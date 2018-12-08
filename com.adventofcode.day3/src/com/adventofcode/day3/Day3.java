package com.adventofcode.day3;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day3 {
	
	public int nbOverlap = 0;

	class Rectangle{
		private static final int OVERLAP_DELTA = 10000;
		int num;

		public int getNum() {
			return num;
		}

		public int getLeft() {
			return left;
		}


		public int getTop() {
			return top;
		}


		public int getWidth() {
			return width;
		}


		public int getTall() {
			return tall;
		}


		int left;
		int top;
		int width;
		int tall;
		

		Rectangle (String str){
			int a = str.indexOf("@");
			int b = str.indexOf(",");
			int c = str.indexOf(':');
			int d = str.indexOf('x');

			num = Integer.parseInt(str.substring(1, a-1));
			left = Integer.parseInt(str.substring(a+2, b));
			top = Integer.parseInt(str.substring(b+1, c));
			
			width = Integer.parseInt(str.substring(c+2, d));
			tall = Integer.parseInt(str.substring(d+1));			
		}
		
		boolean haveOverlap (int[][] fabric) throws Exception {
			boolean result = false;
			for (int i=0; i<width; i++) {
				for (int j=0; j<tall; j++) {
					int actual = fabric[left+i][top+j];
					if (actual == 0) throw new Exception("pb 0");
					if (actual > OVERLAP_DELTA)
						result = true;
					if (actual < OVERLAP_DELTA && actual != num)
						throw new Exception("pb X");
				}
			}
			return result;
		}

		public void apply(int[][] fabric) {
			for (int i=0; i<width; i++) {
				for (int j=0; j<tall; j++) {
					int actual = fabric[left+i][top+j];
					if (actual != 0) {
						fabric[left+i][top+j] = OVERLAP_DELTA + num;
						if (actual < OVERLAP_DELTA) nbOverlap++;
					}
					else fabric[left+i][top+j] = num;
				}
			}			
		}
	}
	
	List<Rectangle> allPieces = new ArrayList<Day3.Rectangle>();

	public static void main (String [] args) {
		if (args.length != 1) {
			System.err.println("bad argument");
			return;
		}
		String file = args[0];
		File fic = new File (file);
		try {
			new Day3().run(fic);
		} catch (IOException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void run(File fic) throws Exception {
		FileInputStream fis = new FileInputStream(fic);
		DataInputStream dis = new DataInputStream(fis);
		while (dis.available() > 0) {
			String str = dis.readLine();
			Rectangle rect = new Rectangle(str);
			allPieces.add(rect);
		}
		int maxWidth = 0;
		int maxHeigh = 0;
		
		for (Rectangle r : allPieces) {
			int x = r.getLeft() + r.getWidth();
			if (x>maxWidth) maxWidth = x;
			int y = r.getTop() + r.getTall();
			if (y>maxHeigh) maxHeigh= y;
		}
		
		int fabric[][] = new int [maxWidth][maxHeigh];


		for (Rectangle r : allPieces) {
			r.apply (fabric);
		}
		System.out.println("nbOverlap = " + nbOverlap);
		
		for (Rectangle p : allPieces) {
			if (! p.haveOverlap(fabric)) {
				System.out.println(" non overlap fabric : " + p.getNum());
			}
		}
		
	}
}
