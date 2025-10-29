package Servlet;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import Interfaces.IEmployeeService;
import Model.Employee;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import service.EmployeeService;

/**
 * Main Servlet Controller for the Employee Management System.
 *
 * Uses @WebServlet annotation for registration and URL mapping.
 * Handles all GET and POST requests for employee CRUD operations.
 */
@WebServlet(
    name = "EmployeeServlet",
    urlPatterns = {"/employee"},
    initParams = {
        @WebInitParam(name = "defaultDepartment", value = "General")
    },
    loadOnStartup = 1 // Load-on-startup requirement
)
public class EmployeeServlet extends HttpServlet {

	private IEmployeeService employeeService;
	private String defaultDepartment;

	/**
	 * init() method: Called once when the servlet is first loaded.
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		// 1. Initialize the global employee map(shared accross servlets) in ServletContext
		ServletContext context = config.getServletContext();
		Map<String, Employee> employeeMap = (Map<String, Employee>) context.getAttribute("employeeMap");

		//if map is not present
		if (employeeMap == null) {
			// ConcurrentHashMap for thread-safety in a web environment
			// multiple requests can safely modify it concurrently.
			employeeMap = new ConcurrentHashMap<>();
			context.setAttribute("employeeMap", employeeMap);
		}

		// 2. Initialize the service layer
		this.employeeService = new EmployeeService();
		this.employeeService.setMap(employeeMap); // Pass the shared map to the service

		// 3. Get default department from ServletConfig(Servlet Specific Object)
		this.defaultDepartment = config.getInitParameter("defaultDepartment");

		System.out.println("EmployeeServlet initialized. Default Department: " + this.defaultDepartment);
	}

	/**
	 * doGet(): Handles all GET requests. Routes traffic based on the 'action'
	 * parameter.
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getParameter("action");
		if (action == null) {
			action = "list"; // Default action
		}

		switch (action) {
		case "create":
			showNewForm(request, response);
			break;
		case "edit":
			showEditForm(request, response);
			break;
		case "delete":
			deleteEmployee(request, response);
			break;
		case "list":
		default:
			listEmployees(request, response);
			break;
		}
	}

	/**
	 * doPost(): Handles all POST requests. Used for creating new employees and
	 * updating existing ones.
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String id = request.getParameter("id");

		if (id == null || id.isEmpty()) {
			// Create new employee
			addEmployee(request, response);
		} else {
			// Update existing employee
			updateEmployee(request, response);
		}
	}

	// --- Private Controller Methods (Called from doGet/doPost) ---

	/**
	 * Lists all employees. Forwards to employeeList.jsp.
	 */
	private void listEmployees(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Collection<Employee> employees = employeeService.getAllEmployees();
		request.setAttribute("employeeList", employees);

		// --- Handle HttpSession and Cookies for display ---

		// Get last updated ID from Session
		HttpSession session = request.getSession(false); // Don't create if it doesn't exist
		if (session != null) {
			String lastUpdatedId = (String) session.getAttribute("lastUpdatedId");
			if (lastUpdatedId != null) {
				request.setAttribute("lastUpdatedId", lastUpdatedId);
			}
		}

		// Get last updated Name from Cookie
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("lastUpdatedName".equals(cookie.getName())) {
					request.setAttribute("lastUpdatedName", cookie.getValue());
					break;
				}
			}
		}

		RequestDispatcher dispatcher = request.getRequestDispatcher("employeeList.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * Shows the form for creating a new employee. Forwards to employeeForm.jsp.
	 */
	private void showNewForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Set default department from ServletConfig
		request.setAttribute("defaultDepartment", this.defaultDepartment);

		RequestDispatcher dispatcher = request.getRequestDispatcher("employeeForm.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * Shows the form for editing an existing employee. Forwards to
	 * employeeForm.jsp.
	 */
	private void showEditForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String id = request.getParameter("id");
		Employee existingEmployee = employeeService.getEmployee(id);

		RequestDispatcher dispatcher = request.getRequestDispatcher("employeeForm.jsp");
		request.setAttribute("employee", existingEmployee);
		dispatcher.forward(request, response);
	}

	/**
	 * Handles POST data to add a new employee. Redirects back to the list.
	 */
	private void addEmployee(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String department = request.getParameter("department");

		// Use default if empty
		if (department == null || department.trim().isEmpty()) {
			department = this.defaultDepartment;
		}

		employeeService.addEmployee(name, email, department);

		// Use sendRedirect (as required)
		response.sendRedirect("employee?action=list");
	}

	/**
	 * Handles POST data to update an existing employee. Redirects back to the list.
	 */
	private void updateEmployee(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String department = request.getParameter("department");

		Employee updatedEmployee = employeeService.updateEmployee(id, name, email, department);

		// --- Store in HttpSession and Cookie (as required) ---
		if (updatedEmployee != null) {
			// Store ID in HttpSession
			HttpSession session = request.getSession(); // Create one if it doesn't exist
			session.setAttribute("lastUpdatedId", updatedEmployee.getId());

			// Store Name in Cookie
			Cookie cookie = new Cookie("lastUpdatedName", updatedEmployee.getName().replace(" ", "_"));
			cookie.setMaxAge(60 * 60 * 24); // 1 day
			// cookie.setPath(request.getContextPath()); // Good practice
			response.addCookie(cookie);
		}

		response.sendRedirect("employee?action=list");
	}

	/**
	 * Deletes an employee. Redirects back to the list.
	 */
	private void deleteEmployee(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String id = request.getParameter("id");
		employeeService.deleteEmployee(id);

		// Use sendRedirect (as required)
		response.sendRedirect("employee?action=list");
	}
}
