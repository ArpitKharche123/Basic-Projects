package service;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import Interfaces.IEmployeeService;
import Model.Employee;

public class EmployeeService implements IEmployeeService {
	// This map is shared from the ServletContext (application scope)
	private Map<String, Employee> employeeMap;

	@Override
	public void setMap(Map<String, Employee> employeeMap) {
		this.employeeMap = employeeMap;
	}

	@Override
	public Employee getEmployee(String eid) {
		if (employeeMap == null) 
			return null;
		return employeeMap.get(eid);
	}

	@Override
	public Collection<Employee> getAllEmployees() {
		if (employeeMap == null)
			return null;
		return employeeMap.values();
	}

	@Override
	public Employee addEmployee(String name, String email, String department) {
		if (employeeMap == null) return null;

        // Generate a unique ID using UUID
        String id = "EMP-" + UUID.randomUUID().toString();

        Employee employee = new Employee(id, name, email, department);
        employeeMap.put(id, employee);
        return employee;
	}

	@Override
	public Employee updateEmployee(String id, String name, String email, String department) {
		if (employeeMap == null || !employeeMap.containsKey(id)) {
            return null;
        }
        
        Employee employee = employeeMap.get(id);
        employee.setName(name);
        employee.setEmail(email);
        employee.setDepartment(department);
        
        // Put it back in the map (though it's modified by reference, this is good practice)
        employeeMap.put(id, employee);
        return employee;
	}

	@Override
	public boolean deleteEmployee(String eid) {
		if (employeeMap == null) {
            return false;
        }
        
        Employee removed = employeeMap.remove(eid);
        return removed != null; // Returns true if an employee was successfully removed
	}

}
