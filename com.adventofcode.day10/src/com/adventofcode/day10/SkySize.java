package com.adventofcode.day10;

public class SkySize {
	int h;
	int w;
	int minX, minY;

	public SkySize(int i, int j, int minX, int minY) {
		w = i;
		h = j;
		this.minX = minX;
		this.minY = minY;
	}
	
	public String toString () {
		return "size="+w+","+h+ "min="+minX+","+ minY;
	}
}
