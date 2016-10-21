package me.winspeednl.libz.actions;

import java.util.ArrayList;

public class ActionContainer {
	
	private ArrayList<Action> actions = new ArrayList<Action>();
	private boolean isRunning;
	
	public void update() {
		if (isRunning) {
			ArrayList<Action> completedActions = null;
			
			for (Action action : actions) {
				if (action.isStarted) action.update();
				else {
					action.onStart();
					action.isStarted = true;
				}
				if (action.isBlocking) break;
				if (action.isFinished) {
					action.onEnd();
					if (completedActions == null) completedActions = new ArrayList<Action>();
					completedActions.add(action);
				}
			}
			if (completedActions != null) for (Action action : completedActions) actions.remove(action);
		}
	}
	
	public void start() {
		isRunning = true;
	}
	
	public void stop() {
		isRunning = false;
	}
	
	public boolean isEmpty() {
		return actions.isEmpty();
	}
	
	public void pushFront(Action action) {
		if (actions.contains(action)) {
			int id = actions.indexOf(action);
			if (id + 1 < actions.size()) {
				actions.remove(action);
				actions.add(id + 1, action);
			}
		}
	}
	
	public void pushBack(Action action) {
		if (actions.contains(action)) {
			int id = actions.indexOf(action);
			if (id - 1 > 0) {
				actions.remove(action);
				actions.add(id - 1, action);
			}
		}
	}
	
	public void insertAfter(Action action, Action newAction) {
		int id = actions.indexOf(action);
		actions.add(id + 1, newAction);
	}
	
	public void insertBehind(Action action, Action newAction) {
		int id = actions.indexOf(action);
		actions.add(id - 1, newAction);
	}
	
	public void add(Action action) {
		actions.add(action);
	}
}
