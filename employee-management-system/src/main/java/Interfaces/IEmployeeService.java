package Interfaces;

import java.util.Collection;
import java.util.Map;

import Model.Employee;

public interface IEmployeeService {
	/**
     * gets the shared employee map from the ServletContext and sets it to the Service
     * if map is not present, creates a new ConcurrentHashMap<String,Employee> and sets it to Service
     * @param employeeMap The application-scoped map to store employees.
     */
    void setMap(Map<String, Employee> employeeMap);

    /**
     * Retrieves a single employee by their ID.
     * @param eid The ID of the employee to retrieve.
     * @return The Employee object, or null if not found.
     */
    Employee getEmployee(String eid);

    /**
     * Retrieves all employees.
     * @return A Collection of all Employee objects.
     */
    Collection<Employee> getAllEmployees();

    /**
     * Adds a new employee to the system.
     * Generates a unique ID for the employee.
     * @param name The employee's name.
     * @param email The employee's email.
     * @param department The employee's department.
     * @return The newly created Employee object with its generated ID.
     */
    Employee addEmployee(String name, String email, String department);

    /**
     * Updates an existing employee's details.
     * @param id The ID of the employee to update.
     * @param name The new name.
     * @param email The new email.
     * @param department The new department.
     * @return The updated Employee object.
     */
    Employee updateEmployee(String id, String name, String email, String department);

    /**
     * Deletes an employee from the system.
     * @param eid The ID of the employee to delete.
     * @return true if deletion was successful, false otherwise.
     */
    boolean deleteEmployee(String eid);
}
