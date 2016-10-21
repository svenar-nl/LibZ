package me.winspeednl.libz.tasks;

public class Task {
	
	private String name = "Default";
	private int ticks = 0, interval = 0, executedCount = 0, maxExecutes = 0;
	private boolean repeat = false, isAlive = true, isRunning = false;
	
	public Task(String name, int interval, boolean repeat) {
		this(name, interval, repeat, false);
	}
	
	public Task(String name, int interval, boolean repeat, boolean forceStart) {
		this.name = name;
		this.interval = interval;
		this.repeat = repeat;
		this.isRunning = forceStart;
	}

	public boolean update() {
		if (isRunning) {
			if (!repeat && executedCount > 0) isAlive = false;
			if (maxExecutes != 0 && executedCount > maxExecutes) isAlive = false;
			if (interval <= ticks) {
				ticks = 0;
				executedCount++;
				return true;
			} else ticks++;
			return false;
		} else return false;
	}
	
	public void start() {
		isRunning = true;
	}
	
	public void stop() {
		isRunning = false;
	}
	
	public boolean isAlive() {
		return isAlive;
	}
	
	public boolean isRunning() {
		return isRunning;
	}

	public String getName() {
		return name;
	}
	
	public int executedCount() {
		return executedCount;
	}
	
	public void dieAfterNumExecutes(int count) {
		maxExecutes = count;
	}
	
	public void setInterval(int interval) {
		this.interval = interval;
	}
	
	public void reset() {
		ticks = 0;
		executedCount = 0;
		isAlive = true;
		isRunning = false;
	}
}