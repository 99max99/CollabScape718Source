package com.rs.game.task;

public abstract class Task {

	private final int delay;
	private final boolean immediate;
	private int countdown;
	private boolean running = true;
	private TickType tickType;

	public Task() {
		this(1);
	}

	public Task(int delay) {
		this(delay, false, TickType.MAIN);
	}

	public Task(TickType tick) {
		this(1, false, tick);
	}

	public Task(boolean immediate) {
		this(1, immediate, TickType.MAIN);
	}

	public Task(int delay, boolean immediate) {
		this(delay, immediate, TickType.MAIN);
	}

	public Task(int delay, boolean immediate, TickType type) {
		checkDelay(delay);
		this.delay = delay;
		this.countdown = delay;
		this.immediate = immediate;
		this.tickType = type;
	}

	public boolean immediate() {
		return immediate;
	}

	public boolean running() {
		return running;
	}

	public boolean stopped() {
		return !running;
	}

	public TickType tickType() {
		return tickType;
	}

	public boolean run() {
		if (running && --countdown == 0) {
			execute();
			countdown = delay;
		}
		return running;
	}

	protected abstract void execute();

	public void delay(int delay) {
		checkDelay(delay);
		delay = 0;
	}

	public void stop() {
		checkStopped();
		running = false;
	}

	private void checkDelay(int delay) {
		if (delay <= 0)
			throw new IllegalArgumentException("Delay must be positive.");
	}

	private void checkStopped() {
		if (!running)
			throw new IllegalStateException();
	}

	public enum TickType {
		MAIN, SECONDARY;
	}

}