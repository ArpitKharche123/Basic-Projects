package service;

import java.util.Collection;
import java.util.UUID;

import model.Task;


public interface ITodoService {

	/**
	 * Adds a new task .
	 *  @param task The Task object to be added.
	 */
	void addTask(Task task);
	
	/**
	 * Updates the details of an existing task identified by its ID.
	 *  @param id The UUID of the existing task to update.
	 * @param task The new Task object containing the updated fields.
	 */
	void updateTask(UUID id, Task task);

	/**
	 * Deletes an existing task based on the provided ID.
	 *  @param id The UUID of the task to be deleted.
	 * @return {@code true} if the task was successfully deleted; {@code false} otherwise.
	 */
	boolean deleteTask(UUID id);
	
	/**
	 * Fetches an existing task based on the provided ID.
	 *  @param id The UUID of the task to retrieve.
	 * @return The requested {@link Task} object, or {@code null} if no task with the given ID exists.
	 */
	Task getTaskById(UUID id);
	
	/**
	 * Fetches all tasks currently stored in the system.
	 *  @return A {@link Collection} of all available {@link Task} objects.
	 */
	Collection<Task> getAllTasks();
}