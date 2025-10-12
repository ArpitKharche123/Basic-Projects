package Interfaces;

import java.util.List;

import DTO.Students;

public interface IStudentCRUD {
	void insertStudent(Students student);
	void insertBulkStudents(List<Students> students);
	void updateStudentById(Students student);
	void getAllStudents();
	void deleteStudentById(int id);
}
