package CRUD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import DTO.Courses;
import Interfaces.ICourseCRUD;

public class CourseCRUD implements ICourseCRUD {
	final String PATH = "jdbc:postgresql://localhost:5432/studentdb?user=postgres&password=root1234";
	final String INSERT = "insert into courses values(?,?,?)";
	final String UPDATE = "update courses set name=?,credits=? where id=?";
	final String FETCH_ALL = "Select * from courses";
	final String DELETE = "Delete from courses where id=?";

	@Override
	public void insertCourse(Courses course) {
		try (Connection connection = DriverManager.getConnection(PATH);
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT)) {

			preparedStatement.setInt(1, course.getId());
			preparedStatement.setString(2, course.getName());
			preparedStatement.setInt(3, course.getCredits());

			int rows = preparedStatement.executeUpdate();
			System.out.println("Course inserted successffully!");
			System.out.println(rows + " no. of rows affected");

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void updateCourse(Courses course) {
		try (Connection connection = DriverManager.getConnection(PATH);
				PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {

			preparedStatement.setString(1, course.getName());
			preparedStatement.setInt(2, course.getCredits());
			preparedStatement.setInt(3, course.getId());

			int rows = preparedStatement.executeUpdate();
			System.out.println("Course updated successffully!");
			System.out.println(rows + " no. of rows affected");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void getAllCourses() {
		try (Connection connection = DriverManager.getConnection(PATH);
				Statement statement = connection.createStatement();) {
			System.out.println("Available Courses : ");
			ResultSet courses = statement.executeQuery(FETCH_ALL);
			ResultSetMetaData md=courses.getMetaData();
			while (courses.next()) {
				System.out.println(md.getColumnName(1)+": "+ courses.getInt("id"));
				System.out.println(md.getColumnName(2)+": "+ courses.getString("name"));
				System.out.println(md.getColumnName(3)+": "+ courses.getString("credits"));
				System.out.println("------------------------------------------");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void deleteCourseById(Courses course) {
		try (Connection connection = DriverManager.getConnection(PATH);
				PreparedStatement statement = connection.prepareStatement(DELETE)) {

			statement.setInt(1, course.getId());
			
			int rows = statement.executeUpdate();
			System.out.println("Course Deleted successffully!");
			System.out.println(rows+" no. of rows affected");

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
