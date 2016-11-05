package com.rs.game;

import java.util.Timer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import com.rs.cores.DecoderThreadFactory;
import com.rs.cores.SlowThreadFactory;
import com.rs.game.task.Task;
import com.rs.game.task.TaskHandler;
import com.rs.game.task.TaskManager;
import com.rs.utils.Logger;

public final class GameEngine {

	private static GameEngine instance;

	private ExecutorService BOSS_EXECUTER;
	private ExecutorService WORKER_EXECUTER;

	private Timer fastExecutor;
	private ScheduledExecutorService slowExecutor;

	private TaskManager tasker;

	public GameEngine() {
		bossExecutor(Executors.newSingleThreadExecutor(new DecoderThreadFactory()));
		workerExecutor(Executors.newSingleThreadExecutor(new DecoderThreadFactory()));
		fastExecutor(new Timer());
		slowExecuter(Executors.newSingleThreadScheduledExecutor(new SlowThreadFactory()));
		tasker(new TaskManager());
		Logger.log("Game Engine", "Started Engine and Executors.");
	}

	public GameEngine init() {
		try {
			addTasks(TaskHandler.get().tasks());
			Logger.log("Game Engine", "Started " + TaskHandler.get().tasks().length + " Tasks.");
		} catch (Exception e) {
			Logger.log("Game Engine", "Error.");
		}
		return this;
	}

	public GameEngine addTask(final Task task) {

		if (task == null)
			return this;

		tasker().schedule(task);
		return this;
	}

	public GameEngine addTasks(Task[] tasks) {
		for (int i = 0; i < tasks.length; i++)
			addTask(tasks[i]);
		return this;
	}

	public GameEngine shutdown() {
		bossExecutor().shutdown();
		workerExecutor().shutdown();
		fastExecutor().cancel();
		slowExecutor().shutdown();
		tasker().shutdown();
		Logger.log("Game Engine", "Game Engine has been shutdown.");
		return this;
	}

	public ExecutorService bossExecutor() {
		return BOSS_EXECUTER;
	}

	public GameEngine bossExecutor(ExecutorService executor) {
		this.BOSS_EXECUTER = executor;
		return this;
	}

	public ExecutorService workerExecutor() {
		return WORKER_EXECUTER;
	}

	public GameEngine workerExecutor(ExecutorService executor) {
		this.WORKER_EXECUTER = executor;
		return this;
	}

	public Timer fastExecutor() {
		return fastExecutor;
	}

	public GameEngine fastExecutor(Timer fastExecutor) {
		this.fastExecutor = fastExecutor;
		return this;
	}

	public ScheduledExecutorService slowExecutor() {
		return slowExecutor;
	}

	public GameEngine slowExecuter(ScheduledExecutorService slowExecuter) {
		this.slowExecutor = slowExecuter;
		return this;
	}

	public TaskManager tasker() {
		return tasker;
	}

	public GameEngine tasker(TaskManager tasker) {
		this.tasker = tasker;
		return this;
	}

	public static GameEngine get() {
		if (instance == null)
			instance = new GameEngine();
		return instance;
	}

}
