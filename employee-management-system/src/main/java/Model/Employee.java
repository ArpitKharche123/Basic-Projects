package Model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Employee {
	private String id;
	private String name;
	private String email;
	private String department;
}
