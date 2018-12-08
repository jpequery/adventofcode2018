package com.adventofcode.day6;

public class Coordonate {
	String id;
	int x;
	int y;
	private boolean _border;
	private int _surface = 0;
	
	public Coordonate (String data, int num) {
		int pos = data.indexOf(',');
		String xS = data.substring(0, pos);
		String yS = data.substring(pos +2);
		
		x = Integer.parseInt(xS);
		y = Integer.parseInt(yS);
		
		id = Integer.toString(num);
	}

	public int getDistance(int i, int j) {
		return Math.abs(i-x) + Math.abs(j-y);
	}

	public void setBorder(boolean b) {
		_border = true;
	}
	
	public boolean isBorder () {
		return _border;
	}

	public void addOne() {
		_surface ++;
		
	}

	public int getSurface() {
		return _surface;
	}
	
	
}
