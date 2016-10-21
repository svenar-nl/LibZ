package me.winspeednl.libz.actions;

public abstract class Action {
	public boolean isStarted, isFinished, isBlocking;
	public long startTime, elapsed, duration;
	public ActionContainer owner;
	
	public abstract void onStart();
	public abstract void onEnd();
	public abstract void update();
	
	public void finish() {
		isFinished = true;
		isBlocking = false;
	}
}