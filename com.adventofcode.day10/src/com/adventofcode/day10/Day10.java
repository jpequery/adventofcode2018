package com.adventofcode.day10;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.text.Position;

class Star{
	int x, y;
	int dx, dy;
	
	public Star(String data) {
		x = Integer.valueOf(data.substring(10, 16).trim());
		y = Integer.valueOf(data.substring(18, 24).trim());
		
		dx = Integer.valueOf(data.substring(36, 38).trim());
		dy = Integer.valueOf(data.substring(40, 42).trim());		
	}
	
	public void tick () {
		x += dx;
		y += dy;
	}
	
	public boolean isPlace (int x, int y) {
		return this.x==x && this.y==y;
	}

}

public class Day10 {

	public static void main(String[] args) {
		if (args.length != 1) {
			System.err.println("bad argument");
			return;
		}
		String file = args[0];
		File fic = new File (file);
		try {
			new Day10().run(fic);
		} catch (IOException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void run(File fic)  throws IOException, Exception{
		
		FileInputStream fis = new FileInputStream(fic);
		DataInputStream dis = new DataInputStream(fis);

		List<Star> stars = new ArrayList<Star>();
		while (dis.available() > 0) {
			String data = dis.readLine();
			Star star = new Star (data);
			stars.add(star);
		}
		SkySize previousSize = new SkySize(110000, 110000, 0, 0); //bidon ! 
		int seconds = 0;
		while (true) {
			seconds++;
			timeTick (stars);
			SkySize s = computeSkySize(stars);
			if (s.w < 81) {
				displaySky (stars, s);
			}
			previousSize = s;
			System.out.println("seconds = " + seconds);
		}
	}

	private void displaySky(List<Star> stars, SkySize s) {
		for (int i=s.minY; i <= s.minY+s.h; i++) {
			for (int j=s.minX; j <= s.minX+s.w; j++) {
				boolean found = false;
				for (Star star : stars) {
					if (star.isPlace(j, i)) found = true;
				}
				if (found) System.out.print('#');
				else System.out.print('.');
			}
			System.out.println();
		}
	}

	private SkySize computeSkySize(List<Star> stars) {
		int minX = Integer.MAX_VALUE;
		int maxX = Integer.MIN_VALUE;
		
		int minY = Integer.MAX_VALUE;
		int maxY = Integer.MIN_VALUE;

		for (Star star : stars) {
			if (star.x < minX) minX = star.x;
			if (star.y < minY) minY = star.y;
			if (star.x > maxX) maxX = star.x;
			if (star.y > maxY) maxY = star.y;
		}
		return new SkySize(maxX - minX, maxY - minY, minX, minY);
	}

	private void timeTick(List<Star> stars) {
		for (Star star : stars) {
			star.tick();
		}	
	}

}
