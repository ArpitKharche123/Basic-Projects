package Interfaces;

import DTO.Courses;

public interface ICourseCRUD {
	void insertCourse(Courses course);
	void updateCourse(Courses course);
	void getAllCourses();
	void deleteCourseById(Courses course);
}
