package Drivers;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import CRUD.CourseCRUD;
import CRUD.EnrollmentCRUD;
import CRUD.StudentCRUD;
import DTO.Courses;
import DTO.Enrollments;
import DTO.Students;
import Interfaces.ICourseCRUD;
import Interfaces.IEnrollmentCRUD;
import Interfaces.IStudentCRUD;

public class SCMSMenu {
	static Scanner scanner = new Scanner(System.in);
	static int choice;

	static IStudentCRUD studentCRUD = new StudentCRUD();
	static Students students;

	static ICourseCRUD courseCRUD = new CourseCRUD();
	static Courses courses = new Courses();

	static IEnrollmentCRUD enrollmentCRUD = new EnrollmentCRUD();
	static Enrollments enrollments = new Enrollments();

	static List<Students> studList;

	static Students studentSetter() {
		Students students = new Students();
		System.out.println("Enter Student id: ");
		students.setId(scanner.nextInt());
		scanner.nextLine();
		System.out.println("Enter Student Name: ");
		students.setName(scanner.nextLine());
		System.out.println("Enter Stundent email: ");
		students.setEmail(scanner.nextLine());
		System.out.println("Enter Student DOB");
		students.setDob(scanner.nextLine());

		return students;
	}

	static void courseSetter() {
		System.out.println("Enter Course id: ");
		courses.setId(scanner.nextInt());
		scanner.nextLine();
		System.out.println("Enter Course Name: ");
		courses.setName(scanner.nextLine());
		System.out.println("Enter Course Credits");
		courses.setCredits(scanner.nextInt());
	}

	static void adminMenu() {
		System.out.println("Admin Menu\nSelect one of the following:");
		System.out.println(
				"1.Add course\n2.Update Course\n3.Delete Course\n4.View all students\n5.View all courses\n6.View all enrollments\n7.Assign grades\n8.Add multiple students\n9.Delete Student\n10.Exit");
		choice = scanner.nextInt();
		switch (choice) {
		case 1: {
			courseSetter();
			courseCRUD.insertCourse(courses);
			break;
		}
		case 2: {
			courseSetter();
			courseCRUD.updateCourse(courses);
			break;
		}
		case 3: {
			System.out.println("Enter the course id to be deleted: ");
			courses.setId(scanner.nextInt());
			scanner.nextLine();
			courseCRUD.deleteCourseById(courses);
			break;
		}
		case 4: {
			studentCRUD.getAllStudents();
			break;
		}
		case 5: {
			courseCRUD.getAllCourses();
			break;
		}
		case 6:
			enrollmentCRUD.getAllEnrollments();
			break;
		case 7:
			System.out.println("Enter student id: ");
			enrollments.setS_id(scanner.nextInt());
			System.out.println("Enter course id: ");
			enrollments.setC_id(scanner.nextInt());
			scanner.nextLine();
			System.out.println("Enter the grade: ");
			enrollments.setGrade(scanner.nextLine());

			enrollmentCRUD.updateGrade(enrollments);
			break;
		case 8: {
			studList = new ArrayList<Students>();
			char choice;
			do {
				Students studs = studentSetter();
				studList.add(studs);
				System.out.println("Do you want to add more students?(y/n)");
				choice = scanner.nextLine().charAt(0);
			} while (choice == 'y');
			studentCRUD.insertBulkStudents(studList);
		}
		case 9: {
			System.out.println("Enter the student id to be deleted:");
			studentCRUD.deleteStudentById(scanner.nextInt());
			break;
		}
		case 10: {
			chooseMenu();
			break;
		}

		default:
			System.out.println("Invalid choice!!");
			adminMenu();
		}
	}

	static void studentMenu() {
		System.out.println("Student Menu\nSelect one of the following:");
		System.out.println(
				"1.Register New Student\n2.Update Student Profile\n3.Enroll in a course\n4.View enrolled courses\n5.View grades\n6.View GPA\n7.Exit");
		choice = scanner.nextInt();
		switch (choice) {
		case 1: {
			Students students = studentSetter();
			studentCRUD.insertStudent(students);
			break;
		}
		case 2: {
			Students students = studentSetter();
			studentCRUD.updateStudentById(students);
			break;
		}
		case 3:
			System.out.println("Enter enrollment id: ");
			enrollments.setId(scanner.nextInt());
			System.out.println("Enter Student Id: ");
			enrollments.setS_id(scanner.nextInt());
			System.out.println("Enter Course Id: ");
			enrollments.setC_id(scanner.nextInt());

			enrollmentCRUD.insertEnrollment(enrollments);
			break;
		case 4: {
			System.out.println("Enter the student id: ");
			enrollmentCRUD.getAllEnrollementsBysID(scanner.nextInt());
			break;
		}
		case 5:
			System.out.println("Enter the student id: ");
			enrollmentCRUD.getAllGrades(scanner.nextInt());
			break;
		case 6:
			// scanner.nextLine(); //to handle input buffer
			System.out.println("Enter the grade to check gpa: ");
			String grade = scanner.next();
			enrollmentCRUD.getGPA(grade);
			break;
		case 7:
			chooseMenu();
			break;
		default:
			System.out.println("Invalid choice!!");
			studentMenu();
		}
	}

	public static void main(String[] args) {
		chooseMenu();

		// testing

	}

	public static void chooseMenu() {
		while (true) {
			System.out.println("\n***** WELCOME TO STUDENT COURSE MANAGEMENT SYSTEM*****\n");
			System.out.println("Select from below: ");
			System.out.println("1.Admin Menu\n2.Student Menu\n0.Exit\n");
			choice = scanner.nextInt();
			switch (choice) {
			case 1:
				adminMenu();
				break;
			case 2:
				studentMenu();
				break;
			case 0:
				System.out.println("Thank you, visit again 😸");
				scanner.close();
				System.exit(0);
			default:
				System.out.println("Invalid choice");
				chooseMenu();
			}
		}
	}
}
