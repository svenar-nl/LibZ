package me.winspeednl.libz.tasks;

import java.util.ArrayList;

import me.winspeednl.libz.entities.LibZ_Entity;

public class TaskContainer {
	private ArrayList<Task> tasks = new ArrayList<Task>();
	private LibZ_Entity entity;
	
	public TaskContainer(LibZ_Entity entity) {
		this.entity = entity;
	}

	public void addTask(Task task) {
		tasks.add(task);
	}
	
	public void update() {
		ArrayList<Task> deadTasks = null;
		
		for (Task task : tasks) {
			if (task.isAlive()) {
				if (task.update())
					entity.taskTriggered(task.getName());
			}
			else {
				if (deadTasks == null) deadTasks = new ArrayList<Task>();
				deadTasks.add(task);
			}
		}
		
		if (deadTasks != null)
			for (Task deadTask : deadTasks)
				tasks.remove(deadTask);
	}
	
	public int activeTasks() {
		int activeTasks = 0;
		for (Task task : tasks) if (task.isAlive() && task.isRunning()) activeTasks++;
		return activeTasks;
	}
	
	public ArrayList<Task> getTasks() {
		return tasks;
	}
}