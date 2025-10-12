package CRUD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import DTO.Students;
import Interfaces.IStudentCRUD;

public class StudentCRUD implements IStudentCRUD {
	final String PATH = "jdbc:postgresql://localhost:5432/studentdb?user=postgres&password=root1234";
	final String INSERT = "insert into students(id,name,email,dob) values(?,?,?,?)";
	final String UPDATE = "update students set name = ?, email = ?, dob = ? where id = ? ";
	final String FETCH_ALL = "Select * from students";
	final String DELETE = "Delete from students where id=?";

	@Override
	public void insertStudent(Students student) {
		try (Connection connection = DriverManager.getConnection(PATH);
				PreparedStatement statement = connection.prepareStatement(INSERT)) {

			statement.setInt(1, student.getId());
			statement.setString(2, student.getName());
			statement.setString(3, student.getEmail());
			statement.setString(4, student.getDob());

			int rows = statement.executeUpdate();
			System.out.println("Student inserted successffully!");
			System.out.println(rows + " no. of rows affected");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateStudentById(Students student) {
		try (Connection connection = DriverManager.getConnection(PATH);
				PreparedStatement statement = connection.prepareStatement(UPDATE)) {

			statement.setString(1, student.getName());
			statement.setString(2, student.getEmail());
			statement.setString(3, student.getDob());
			statement.setInt(4, student.getId());

			int rows = statement.executeUpdate();
			System.out.println("Student updated successffully!");
			System.out.println(rows + " no. of rows affected");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void getAllStudents() {
		try (Connection connection = DriverManager.getConnection(PATH);
				Statement statement = connection.createStatement();) {
			System.out.println("Registered Students: ");
			ResultSet students = statement.executeQuery(FETCH_ALL);
			ResultSetMetaData md= students.getMetaData();
			while (students.next()) {
				System.out.println(md.getColumnName(1)+": "+ students.getInt(1));
				System.out.println(md.getColumnName(2)+": "+  students.getString("name"));
				System.out.println(md.getColumnName(3)+": "+ students.getString("email"));
				System.out.println(md.getColumnName(4)+": "+  students.getString("dob"));
				System.out.println("------------------------------------------");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteStudentById(int id) {
		try (Connection connection = DriverManager.getConnection(PATH);
				PreparedStatement statement = connection.prepareStatement(DELETE)) {

			statement.setInt(1, id);

			int rows = statement.executeUpdate();
			System.out.println("Student Deleted successffully!");
			System.out.println(rows + " no. of rows affected");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void insertBulkStudents(List<Students> students) {
		try (Connection connection = DriverManager.getConnection(PATH);
				PreparedStatement statement = connection.prepareStatement(INSERT)) {

			connection.setAutoCommit(false);
			for (Students studs : students) {
				statement.setInt(1, studs.getId());
				statement.setString(2, studs.getName());
				statement.setString(3, studs.getEmail());
				statement.setString(4, studs.getDob());
				statement.addBatch();
			}
			int[] executeBatch = statement.executeBatch();
			connection.commit();
			System.out.println("Students are inserted successfully!");
			System.out.println(executeBatch.length + " no. of rows affected");
			System.out.println("Inserted Students details: ");
			for(Students studs:students) {
				System.out.println(studs);
			}
				
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
