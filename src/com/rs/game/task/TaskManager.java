package com.rs.game.task;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.rs.Launcher;
import com.rs.cores.FastThreadFactory;
import com.rs.game.task.Task.TickType;
import com.rs.utils.Logger;

public final class TaskManager {

	private static final int MAIN_TICK = 600;
	private static final int SECONDARY_TICK = 50;

	private final ScheduledExecutorService main_service = Executors.newSingleThreadScheduledExecutor(new FastThreadFactory());
	private final ScheduledExecutorService secondary_service = Executors.newSingleThreadScheduledExecutor(new FastThreadFactory());

	private final List<Task> main_tasks = new ArrayList<Task>();
	private final List<Task> secondary_tasks = new ArrayList<Task>();

	private final Queue<Task> main_newTasks = new ArrayDeque<Task>();
	private final Queue<Task> secondary_newTasks = new ArrayDeque<Task>();

	public TaskManager() {

		main_service.scheduleAtFixedRate(new Runnable() {

			@Override
			public void run() {
				tick(TickType.MAIN);
			}

		}, 0, MAIN_TICK, TimeUnit.MILLISECONDS);

		secondary_service.scheduleAtFixedRate(new Runnable() {

			@Override
			public void run() {
				tick(TickType.SECONDARY);
			}

		}, 0, SECONDARY_TICK, TimeUnit.MILLISECONDS);

	}

	public void shutdown() {
		main_service.shutdown();
		secondary_service.shutdown();
	}

	public TaskManager schedule(final Task task) {

		if (task.immediate()) {

			final ScheduledExecutorService service = task.tickType() == TickType.MAIN ? main_service : secondary_service;

			service.execute(new Runnable() {
				@Override
				public void run() {
					task.execute();
				}
			});

		}

		final Queue<Task> newTasks = task.tickType() == TickType.MAIN ? main_newTasks : secondary_newTasks;

		synchronized (newTasks) {
			newTasks.add(task);
		}

		return this;

	}

	public void tick(TickType type) {

		final Queue<Task> newTasks = type == TickType.MAIN ? main_newTasks : secondary_newTasks;
		final List<Task> tasks = type == TickType.MAIN ? main_tasks : secondary_tasks;

		synchronized (newTasks) {
			Task task;
			while ((task = newTasks.poll()) != null)
				tasks.add(task);
		}

		for (Iterator<Task> it = tasks.iterator(); it.hasNext();) {
			final Task task = it.next();
			try {
				if (!task.run())
					it.remove();
			} catch (Throwable t) {
				Logger.log("Error", "Exception during task execution.");
				Logger.handle(t);
			}
		}

	}

}