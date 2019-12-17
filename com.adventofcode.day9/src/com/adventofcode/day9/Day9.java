package com.adventofcode.day9;

import java.util.ArrayList;
import java.util.List;



class Plateau{
	class Item {
		int marble;
		Item next;
		Item previous;
		
		Item (int marble, Item insertAfter) {
			this.marble = marble;
			if (insertAfter == null) {
				previous = this;
				next = this;
			} else {
				Item newAfter = insertAfter.next;
				previous = insertAfter;
				next = newAfter;
				insertAfter.next = this;
				newAfter.previous = this;			
			}
		}
		int remove () {
			this.next.previous = this.previous;
			this.previous.next = this.next;
			return this.marble;
		}
	}
	Item root;
	Item currentItem;
	
	public int insertMarble (int marble) {
		if (marble == 0) {
			root = new Item (marble, null);
			currentItem = root;
		} else if ( marble % 23 == 0) {
			Item toRemove = currentItem.previous.previous.previous.previous.previous.previous.previous;
			int score = toRemove.remove();
			currentItem = toRemove.next;
			return marble + score;
		}else {
			Item insertAfter = currentItem.next;
			Item item = new Item(marble, insertAfter);
			currentItem = item;
		}
		return 0;
	}
	
	public String toString () {
		StringBuilder sb = new StringBuilder();
		Item element = root;
		boolean init = true;
		while (init || element != root) {
			init = false;
			sb.append(displayElement (element));
			element = element.next;
		}
		return sb.toString();
	}

	private String displayElement(Item element) {
		String result = "";
		if (currentItem == element) result+='(';
		result += element.marble;
		if (currentItem == element) result+=')';
		result += " ";
		return result;
	}
	
}

class Player {
	int num;
	long score = 0;
	
	static List<Player> players = new ArrayList<Player>();
	
	public Player (int n) {
		num = n;
		players.add(this);
	}
	
	static Player createPlayers (int nb) {
		for (int i=0; i < nb; i++)
			new Player(i);
		return players.get(0);
	}
	
	public Player nextPlayer() {
		int index = players.indexOf(this);
		if (index == players.size() -1) index = 0;
		else index ++;
		return players.get(index);
	}
}

public class Day9 {
	public static void main(String[] args) {
		new Day9().run(465, 71498);
	}

	private void run(int nbPlayers, int lastMarbleScore) {
		Player currentPlayer = Player.createPlayers(nbPlayers);
		int currentMarble = 0;
		Plateau plateau = new Plateau();
		
		while (currentMarble <= lastMarbleScore * 100) {
			int score = plateau.insertMarble(currentMarble);
			currentPlayer.score += score;
//			System.out.println(plateau);
			currentMarble++;
			currentPlayer = currentPlayer.nextPlayer();
		}
		long max = 0;
		for (Player p : Player.players) {
			if (p.score>max)
				max = p.score;
		}
		System.out.println("max score = " + max);
	}
}
