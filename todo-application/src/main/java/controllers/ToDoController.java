package controllers;

import java.util.Collection;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import model.Task;
import service.ITodoService;

@Controller
public class ToDoController {
	@Autowired
	private ITodoService todoService;

	@GetMapping("/")
	public String showIndexPage() {
		return "index";
	}

	@GetMapping("/tasks")
	public String viewTasks(Model model) {
		Collection<Task> tasksMap = todoService.getAllTasks();
		model.addAttribute("tasksMap", tasksMap);
		return "home";
	}

	@GetMapping("/addTask")
	public ModelAndView showAddTaskForm() {
		Task emptyTask = new Task();
		return new ModelAndView("addTask", "taskModel", emptyTask);
	}

	@PostMapping("/saveTask")
	public String saveTask(@ModelAttribute("taskModel") Task task, Model model) {
		todoService.addTask(task);
		model.addAttribute("title", task.getTitle());
		model.addAttribute("action", "Added");
		return "success";
	}

	@GetMapping("/editTask")
	public ModelAndView showEditTaskForm(@RequestParam("id") UUID id) {
		Task taskToEdit = todoService.getTaskById(id);

		if (taskToEdit != null) {
			return new ModelAndView("editTask", "taskModel", taskToEdit);
		}
		return new ModelAndView("redirect:/");
	}

	@PostMapping("/updateTask")
	public String updateTask(@ModelAttribute("taskModel") Task task, Model model) {
		todoService.updateTask(task.getId(), task);
		model.addAttribute("title", task.getTitle());
		model.addAttribute("action", "Updated");
		return "success";
	}

	@GetMapping("/deleteTask")
	public String deleteTask(@RequestParam("id") UUID id, Model model) {

		Task taskk = todoService.getTaskById(id);
		
		model.addAttribute("title", taskk.getTitle());

		boolean deleted = todoService.deleteTask(id);

		if (deleted) {
			System.out.println("Task with UUID " + id + " successfully deleted.");
		}

		return "success";
	}
}