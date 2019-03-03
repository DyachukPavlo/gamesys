package gamesys.services.utils;

import gamesys.exceptions.NullValueException;
import gamesys.exceptions.TaskAlreadyExistsException;
import gamesys.exceptions.TaskNotFoundException;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduleTasks {
    private static final Map<String, ExecutorService> tasks = new HashMap<>();

    public static void runTask(String taskName, Runnable runnable, long period, TimeUnit unit) throws TaskAlreadyExistsException, NullValueException {
        if (tasks.containsKey(taskName)) {
            throw new TaskAlreadyExistsException("Task with such name already exists");
        } else if (taskName == null) {
            throw new NullValueException("Name of a task should not be null");
        } else if (runnable == null) {
            throw new NullValueException("Runnable shoul not be null");
        }
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(runnable, 0, period, unit);
        tasks.put(taskName, executorService);
    }

    public static void stopTask(String taskName) throws TaskNotFoundException {
        ExecutorService executorService = tasks.get(taskName);
        if (executorService == null) {
            throw new TaskNotFoundException("Task with such name not found");
        }
        executorService.shutdown();
        tasks.remove(taskName);
    }

    public static Map<String, ExecutorService> showActiveTasks() {
        return tasks;
    }
}