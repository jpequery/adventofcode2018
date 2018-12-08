package com.adventofcode.day7;

public class Worker {
	Step currentTask;
	
	int remainingTime;

	private String name;
	
	public Worker(int i) {
		name = Integer.toString(i);
	}

	public void assignTask (Step task, int tick) {
		currentTask = task;
		String name = task.getName();
		int x = name.charAt(0) - 'A' + 1;
		remainingTime = 60 + x;
		
		System.out.println ("worker " + this.name + " starting task " + task.getName() + " @" +tick);
	}
	
	public Step getFinishedTask(){
		Step result = currentTask;
		currentTask = null;
		return result;
	}
	
	public int getRemainingTime() {
		return remainingTime;
	}
	
	public void tick () {
		if (remainingTime > 0)
		remainingTime --;
	}
	
}
