package com.adventofcode.day7;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

class Step {
	static List<Step> allSteps = new ArrayList<Step>();
	List<Step> premices = new ArrayList<Step>();
	String name;
	
	public String toString () {
		String result = name;
		result+= "<-";
		for (Step step : premices) {
			result += step.name + " ";
		}
		return result;
	}
	
	public Step(String str) {
		name = str;
	}

	public void addPremice (Step s) {
		premices.add(s);
	}
	
	public List<Step> getPremices(){
		return premices;
	}
	
	public static Step getOrCreateStep (String str) {
		for (Step step : allSteps) {
			if (step.name.equals(str))return step;
		}
		Step step = new Step (str);
		allSteps.add(step);
		return step;
	}
	
	public static Step createStep (String step, String premice) {
		Step a = getOrCreateStep(step);
		Step b = getOrCreateStep(premice);
		a.addPremice(b);
		return a;
	}

	public static List<Step> findExecutableStep(List<Step> done) {
		List<Step> result = new ArrayList<Step>();
		
		for (Step step : allSteps) {
			boolean executable = true;
			for (Step premice : step.premices) {
				if (! done.contains(premice)) executable = false;
			}
			if (executable) result.add(step);
		}
		
		result.sort(new StepComparator());
		return result;
	}

	public String getName() {
		return name;
	}
}
class StepComparator implements Comparator<Step> {

	@Override
	public int compare(Step arg0, Step arg1) {
		return arg0.getName().compareTo(arg1.getName());
	}

}

public class Day7 {
	

	private static final int NB_WORKERS = 5;

	public static void main(String[] args) {
		if (args.length != 1) {
			System.err.println("bad argument");
			return;
		}
		String file = args[0];
		File fic = new File (file);
		try {
			new Day7().run(fic);
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
			int index1 = data.indexOf(" must");
			int index2 = data.indexOf(" can");
			
			String a = data.substring(index1 -1, index1);
			String b = data.substring(index2 -1, index2);
			Step.createStep(b, a);
		}
		System.out.println("steps created : " + Step.allSteps.size());
		
		List<Step> done = new ArrayList<Step>();
		int tick = 0;
		List<Worker> workers = createWorkers ();
		while (done.size() < 26) {
			for (Worker worker : workers) {
				if (worker.getRemainingTime() == 0) {
					Step finishedTask = worker.getFinishedTask();					
					if (finishedTask != null) done.add(finishedTask);
				}
			}
			for (Worker worker : workers) {
				List<Step> availableTasks = Step.findExecutableStep(done);
				if (worker.getRemainingTime() == 0 && availableTasks.size() > 0) {
					Step startingTask = availableTasks.get(0);
					worker.assignTask(startingTask, tick);
					Step.allSteps.remove(startingTask);
				}				
			}
			
			for (Worker worker : workers) {
				worker.tick();
			}
			tick++;
		}
		System.out.println("ticker=" + (tick - 1));
		System.out.print("tasks order = ");
		for (Step s : done) {
			System.out.print(s.getName());
		}
		System.out.println();
	}

	private List<Worker> createWorkers() {
		ArrayList<Worker> result = new ArrayList<Worker>();
		for (int i=0 ; i<NB_WORKERS; i++)
			result.add(new Worker(i));
		return result;
	}

}
