package com.adventofcode.day6;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day6 {

	private static final int WORLD_SIZE = 1000;

	public static void main(String[] args) {
		if (args.length != 1) {
			System.err.println("bad argument");
			return;
		}
		String file = args[0];
		File fic = new File (file);
		try {
			new Day6().run(fic);
		} catch (IOException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void run(File fic)  throws IOException, Exception{
		List<Coordonate> allCoordonates = new ArrayList<Coordonate>();
		FileInputStream fis = new FileInputStream(fic);
		DataInputStream dis = new DataInputStream(fis);
		int num = 0;
		while (dis.available() > 0) {
			String data = dis.readLine();

			allCoordonates.add (new Coordonate (data, num++));
		}
		
		Coordonate [][] world = new Coordonate[WORLD_SIZE][WORLD_SIZE];
		for (int i = 0; i<WORLD_SIZE; i++) {
			for (int j = 0; j<WORLD_SIZE; j++) {
				Coordonate closest = findClosestCoordonate (allCoordonates, i, j);
				
				// note : if multiple, closest is null;
				world [i][j] = closest;
			}			
		}
		
		// excluding borders
		for (int i = 0; i<WORLD_SIZE; i++) {
			Coordonate elem = world[i][0];
			if (elem!= null) elem.setBorder (true);
			elem = world[i][WORLD_SIZE-1];
			if (elem!= null) elem.setBorder (true);
		}
		for (int i = 0; i<WORLD_SIZE; i++) {
			Coordonate elem = world[0][i];
			if (elem!= null) elem.setBorder (true);
			elem = world[WORLD_SIZE-1][i];
			if (elem!= null) elem.setBorder (true);
		}
		
		/* calcul de surface */
		for (int i = 0; i<WORLD_SIZE; i++) {
			for (int j = 0; j<WORLD_SIZE; j++) {
				Coordonate elem = world[i][j];
				if (elem!=null) elem.addOne ();
			}
		}
		
		Coordonate maxSurfaceCoordonate = null;
		for (Coordonate coordonate : allCoordonates) {
			if (!coordonate.isBorder()) {
				if (maxSurfaceCoordonate == null || coordonate.getSurface() > maxSurfaceCoordonate.getSurface())
					maxSurfaceCoordonate = coordonate;
			}
		} 
		System.out.println ("max Surface = " + maxSurfaceCoordonate.getSurface());
		
		
		// cazlcul de la region
		int regionSize = 0;
		for (int i = 0; i<WORLD_SIZE; i++) {
			for (int j = 0; j<WORLD_SIZE; j++) {
				int distance = 0;
				for (Coordonate coordonate : allCoordonates) {
					distance += coordonate.getDistance(i, j);
				}
				if (distance < 10000) {
					regionSize ++;
				}
			}
		}
		
		System.out.println("regionSize=" + regionSize);
	}

	private Coordonate findClosestCoordonate(List<Coordonate> allCoordonates, int i, int j) {
		int minDist = WORLD_SIZE;
		Coordonate best = null;
		for (Coordonate coordonate : allCoordonates) {
			int dist = coordonate.getDistance (i, j);
			if (dist < minDist) {
				best = coordonate;
				minDist = dist;
			} else if (dist == minDist) {
				// found a equal better, nullify
				best = null;
			}
		}
		return best;
	}

}
