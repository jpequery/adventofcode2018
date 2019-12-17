package com.adventofcode.day11;

public class Day11 {

	private static final int SIZE = 300;
	private static final int SERIAL = 1723;
	int data[][] = new int[SIZE][SIZE];

	public static void main(String[] args) {
		new Day11().run();
	}

	private void run() {
		fillUpTab();
		lookupBest();
	}

	private void lookupBest() {
		int maxpower = 0;
		int bestx = 0, besty = 0;
		int bestGridSize = 0;
		for (int gridSize = 1; gridSize < 300; gridSize++) {
			for (int i = 0; i < SIZE - gridSize -1; i++) {
				for (int j = 0; j < SIZE - gridSize -1; j++) {
					int res = computeGrid(i, j, gridSize);
					if (res > maxpower) {
						System.out.println("newMax=" + res);
						maxpower = res;
						bestx = i;
						besty = j;
						bestGridSize = gridSize;
					}
				}
			}
		}
		// PART1 : best=30 coordinates=34,13
		System.out.println("best=" + maxpower + " coordinates=" + (bestx + 1) + "," + (besty + 1) + ","+bestGridSize);
	}

	private int computeGrid(int i, int j, int gridSize) {
		int power = 0;
		for (int x = i; x < i + gridSize; x++) {
			for (int y = j; y < j + gridSize; y++) {
				power += data[x][y];
			}
		}
		return power;
	}

	private void fillUpTab() {
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				data[i][j] = computeValue(i, j);
			}
		}
	}

	private int computeValue(int i, int j) {
		int rackId = i + 1 + 10;
		int power = rackId;
		power = power * (j + 1);
		power += SERIAL;
		power *= rackId;
		power = centaine(power);
		power -= 5;
		return power;
	}

	private int centaine(int power) {
		String num = Integer.toString(power);
		char c = num.charAt(num.length() - 3);
		return Character.getNumericValue(c);
	}

}
