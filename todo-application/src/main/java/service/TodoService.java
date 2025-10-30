package service;

import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import model.Task;
	
@Service
public class TodoService implements ITodoService{

	private static final ConcurrentHashMap<UUID, Task> tasks = new ConcurrentHashMap<>();
	@Override
	public void addTask(Task task) {
		task.setId(UUID.randomUUID());
		tasks.put(task.getId(), task);
	}

	@Override
	public void updateTask(UUID id, Task task) {
		task.setId(id);
		tasks.put(id, task);
	}

	@Override
	public boolean deleteTask(UUID id) {
		return tasks.remove(id) != null;
	}

	@Override
	public Task getTaskById(UUID id) {
		return tasks.get(id);
	}

	@Override
	public Collection<Task> getAllTasks() {
		return tasks.values();
	}
}
