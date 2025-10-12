package CRUD;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import DTO.Enrollments;
import DTO.Students;
import Interfaces.IEnrollmentCRUD;

public class EnrollmentCRUD implements IEnrollmentCRUD {
	final String PATH = "jdbc:postgresql://localhost:5432/studentdb?user=postgres&password=root1234";
	final String INSERT = "insert into enrollments values(?,?,?,?)";
	final String UPDATE_GRADE = "call sp_assignGrade(?,?,?)";
	final String FETCH_ALL_BY_ID = "Select * from enrollments where s_id=?";
	final String FETCH_ALL = "Select * from enrollments";
	final String DELETE = "Delete from enrollments where id=?";
	final String FETCH_GRADES = "Select c_id,grade from enrollments where s_id=?";
	final String VIEW_GPA = "select func_get_gpa(?)";

	@Override
	public void insertEnrollment(Enrollments enrollment) {
		try (Connection connection = DriverManager.getConnection(PATH);
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT)) {

			preparedStatement.setInt(1, enrollment.getId());
			preparedStatement.setInt(2, enrollment.getS_id());
			preparedStatement.setInt(3, enrollment.getC_id());
			preparedStatement.setString(4, null);

			int rows = preparedStatement.executeUpdate();
			System.out.println("Stundent with id " + enrollment.getS_id() + "is enrolled successffully for course id: "
					+ enrollment.getC_id());
			System.out.println(rows + " no. of rows affected");

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void updateGrade(Enrollments enrollment) {
		try (Connection connection = DriverManager.getConnection(PATH);
				CallableStatement callableStatement = connection.prepareCall(UPDATE_GRADE)) {

			callableStatement.setInt(1, enrollment.getS_id());
			callableStatement.setInt(2, enrollment.getC_id());
			callableStatement.setString(3, enrollment.getGrade());

			callableStatement.execute();
			int rows = callableStatement.getUpdateCount();
			System.out.println("Grade is assigned successfully!!");

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void deleteEnrollmentByID(Enrollments enrollment) {
		try (Connection connection = DriverManager.getConnection(PATH);
				PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {

			preparedStatement.setInt(1, enrollment.getId());

			int rows = preparedStatement.executeUpdate();
			System.out.println("Enrollment deleted successfully!");
			System.out.println(rows + " no. of rows affected");

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void getAllEnrollementsBysID(int student_id) {
		try (Connection connection = DriverManager.getConnection(PATH);
				PreparedStatement statement = connection.prepareStatement(FETCH_ALL_BY_ID)) {

			statement.setInt(1, student_id);

			System.out.println("All enrollments of a Student: ");
			ResultSet enrollments = statement.executeQuery();
			ResultSetMetaData md = enrollments.getMetaData();
			while (enrollments.next()) {
				System.out.println(md.getColumnName(1) + ": " + enrollments.getInt("id"));
				System.out.println(md.getColumnName(2) + ": " + enrollments.getInt("s_id"));
				System.out.println(md.getColumnName(3) + ": " + enrollments.getInt("c_id"));
				System.out.println(md.getColumnName(4) + ": " + enrollments.getString("grade"));
				System.out.println("------------------------------------------");

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void getAllEnrollments() {
		try (Connection connection = DriverManager.getConnection(PATH);
				Statement statement = connection.createStatement()) {

			System.out.println("All enrollments: ");
			ResultSet enrollments = statement.executeQuery(FETCH_ALL);
			ResultSetMetaData md = enrollments.getMetaData();
			while (enrollments.next()) {
				System.out.println(md.getColumnName(1) + ": " + enrollments.getInt("id"));
				System.out.println(md.getColumnName(2) + ": " + enrollments.getInt("s_id"));
				System.out.println(md.getColumnName(3) + ": " + enrollments.getInt("c_id"));
				System.out.println(md.getColumnName(4) + ": " + enrollments.getString("grade"));
				System.out.println("------------------------------------------");

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void getAllGrades(int student_id) {
		try (Connection connection = DriverManager.getConnection(PATH);
				PreparedStatement preparedStatement = connection.prepareStatement(FETCH_GRADES)) {
			preparedStatement.setInt(1, student_id);

			ResultSet grades = preparedStatement.executeQuery();
			ResultSetMetaData md = grades.getMetaData();
			System.out.println("Grade Details: ");
			while (grades.next()) {
				System.out.println(md.getColumnName(1) + ": " + grades.getInt("c_id"));
				System.out.println(md.getColumnName(2) + ": " + grades.getString("grade"));
				System.out.println("-----------------------------------------");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void getGPA(String grade) {
		try (Connection connection = DriverManager.getConnection(PATH);
				CallableStatement callableStatement = connection.prepareCall(VIEW_GPA)) {

			callableStatement.setString(1, grade);
			ResultSet res = callableStatement.executeQuery();
			if (res == null) {
				System.err.println("Something went wrong while fetching GPA!!!");
			} else {
				res.next();
				double gpa = res.getDouble(1);
				System.out.println("GPA for grade " + grade + " is :" + gpa);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
