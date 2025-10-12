package Interfaces;

import DTO.Enrollments;
import DTO.Students;

public interface IEnrollmentCRUD {
	void insertEnrollment(Enrollments enrollment);
	void updateGrade(Enrollments enrollment);
	void deleteEnrollmentByID(Enrollments enrollment);
	void getAllEnrollementsBysID(int student_id);
	void getAllEnrollments();
	void getAllGrades(int student_id);
	void getGPA(String grade);
}
