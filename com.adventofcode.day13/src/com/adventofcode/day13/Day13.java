package com.adventofcode.day13;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

enum Turn{
	LEFT, STRAIGHT, RIGHT;
	
	public Turn next () {
		if (this == LEFT) return STRAIGHT;
		if (this == STRAIGHT) return RIGHT;
		if (this == RIGHT) return LEFT;
		return null;// unreachable
	}
}

enum Direction {
	NORTH, SOUTH, EAST, WEST;
	
	public Direction left () {
		if (this==NORTH) return WEST;
		if (this==SOUTH) return EAST;
		if (this==EAST) return NORTH;
		if (this==WEST) return SOUTH;
		return null; // unreachable;
	}

	public Direction right () {
		if (this==NORTH) return EAST;
		if (this==SOUTH) return WEST;
		if (this==EAST) return SOUTH;
		if (this==WEST) return NORTH;
		return null; // unreachable;
	}

}

class Wagon {
	int x, y;
	int px, py;
	Direction direction;
	Turn nextTurn = Turn.LEFT;

	public Wagon(char c, int x, int y) {
		this.x = x;
		this.y = y;

		if (c == '<')
			direction = Direction.WEST;
		if (c == '>')
			direction = Direction.EAST;
		if (c == '^')
			direction = Direction.NORTH;
		if (c == 'v')
			direction = Direction.SOUTH;
	}

	boolean isCollide(Wagon w) {
		return w != this && x == w.x && y == w.y;
	}

	public String toString() {
		return "W(" + x + "," + y + ") " + direction;
	}

	public void tick(char[][] piste) {
		px = x; py=y;
		char c = piste[x][y];
		if (c == '/' && direction == Direction.NORTH)
			direction = Direction.EAST;
		else if (c == '/' && direction == Direction.SOUTH)
			direction = Direction.WEST;
		else if (c == '/' && direction == Direction.EAST)
			direction = Direction.NORTH;
		else if (c == '/' && direction == Direction.WEST)
			direction = Direction.SOUTH;
		else if (c == '\\' && direction == Direction.NORTH)
			direction = Direction.WEST;
		else if (c == '\\' && direction == Direction.SOUTH)
			direction = Direction.EAST;
		else if (c == '\\' && direction == Direction.EAST)
			direction = Direction.SOUTH;
		else if (c == '\\' && direction == Direction.WEST)
			direction = Direction.NORTH;

		// intersection 
		if (c == '+'
				) {
			if (nextTurn == Turn.LEFT)
				direction = direction.left();
			else if (nextTurn == Turn.RIGHT)
				direction = direction.right();
			nextTurn = nextTurn.next();
		}
		
		if (direction == Direction.NORTH) y--;
		if (direction == Direction.SOUTH) y++;
		if (direction == Direction.EAST) x++;
		if (direction == Direction.WEST) x--;

		if (c==' ') throw new RuntimeException("DERAILLEMENT §§§§");
	}

	public boolean here(int i, int j) {
		return x==i && y==j;
	}
}

public class Day13 {
	public static void main(String[] args) {
		if (args.length != 1) {
			System.err.println("bad argument");
			return;
		}
		String file = args[0];
		File fic = new File(file);
		try {
			new Day13().run(fic);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void run(File fic) throws IOException {
		FileInputStream fis = new FileInputStream(fic);
		DataInputStream dis = new DataInputStream(fis);
		int nbLines = 0;
		int nbCols = 0;
		while (dis.available() > 0) {
			String data = dis.readLine();
			nbLines++;
			if (data.length() > nbCols)
				nbCols = data.length();
		}
		char[][] piste = new char[nbCols][nbLines];
		List<Wagon> wagons = new ArrayList<Wagon>();
		FileInputStream fis2 = new FileInputStream(fic);
		DataInputStream dis2 = new DataInputStream(fis2);
		int currentLine = 0;
		while (dis2.available() > 0) {
			int currentCol = 0;
			String data = dis2.readLine();
			for (char c : data.toCharArray()) {
				piste[currentCol][currentLine] = c;
				if (isWagon(c)) {
					Wagon wagon = new Wagon(c, currentCol, currentLine);
					wagons.add(wagon);
				}
				currentCol++;
			}
			currentLine++;
		}
		System.out.println("nbWagons = " + wagons.size());
		int numTour = 1;
		while (wagons.size() != 1) {
			System.out.println("tour numero : " + numTour++);
			display (piste, wagons);
			List<Wagon> wagonsToRemove = new ArrayList<Wagon>();
			for (Wagon wagon : wagons) {
				wagon.tick(piste);
				for (Wagon wagon2 : wagons) {
					if (wagon.isCollide(wagon2)) {
						wagonsToRemove.add(wagon);
						wagonsToRemove.add(wagon2);
					}
				}
			}
			wagons.removeAll(wagonsToRemove);
		}

		System.out.println (wagons.get(0));
		// test 49,37
		// test 50,36
		// test 50,37
		// test 50,35
		
	}

	private void display(char[][] piste, List<Wagon> wagons) {
		for (int i=0; i<piste[0].length; i++) {
			for (int j=0; j<piste.length; j++) {
				char c = piste[j][i];
				for (Wagon wagon : wagons) {
					if  (wagon.here (j, i)) c='W';
				}
				System.out.print(c);
			}
			System.out.println();
		}
		
	}

	private boolean isWagon(char c) {
		return c == '<' || c == '>' || c == 'v' || c == '^';
	}
}