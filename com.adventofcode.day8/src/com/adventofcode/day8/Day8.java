package com.adventofcode.day8;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class Node{
	List<Node> subNodes = new ArrayList<Node>();
	List<String> metadatas = new ArrayList<String>();
	int endIndex;
	public int getEndIndex() {
		return endIndex ;
	}
	public int sumUp() {
		int result = 0;
		for (String string : metadatas) {
			result+= Integer.valueOf(string);
		}
		for (Node n : subNodes) {
			result+= n.sumUp();
		}
				
		return result;
	}
	
	public int sumUp2() {
		int result = 0;
		if (subNodes.size() == 0) {
			for (String string : metadatas) {
				result+= Integer.valueOf(string);
			}			
		} else {
			for (String string : metadatas) {
				int num = Integer.valueOf(string);
				if (num != 0 && num <= subNodes.size()) {
					result += subNodes.get(num -1).sumUp2();
				}
			}
		}
		return result;
	}
}

public class Day8 {

	public static void main(String[] args) {
		if (args.length != 1) {
			System.err.println("bad argument");
			return;
		}
		String file = args[0];
		File fic = new File (file);
		try {
			new Day8().run(fic);
		} catch (IOException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void run(File fic)  throws IOException, Exception{
		
		FileInputStream fis = new FileInputStream(fic);
		DataInputStream dis = new DataInputStream(fis);
		int num = 0;
		while (dis.available() > 0) {
			String data = dis.readLine();
			String[] splited = data.split(" ");
			
			Node root = parseNode (0, splited);
			System.out.println("somme des metadata = " + root.sumUp());
			System.out.println("somme par 2 = " + root.sumUp2());
		}
	}
	private Node parseNode(int i, String[] splited) {
		int nbSubNode = Integer.valueOf(splited[i]);
		int nbMetaData = Integer.valueOf(splited[i+1]);
		Node node = new Node ();
		i+=2;
		for (int nbNode = 0; nbNode < nbSubNode; nbNode++) {
			Node sub = parseNode(i, splited);
			node.subNodes.add(sub);
			i = sub.getEndIndex();
		}
		for (int numMD = 0; numMD < nbMetaData; numMD++) {
			node.metadatas.add(splited[i++]);
		}
		node.endIndex = i;
		return node;
	}
	

}
