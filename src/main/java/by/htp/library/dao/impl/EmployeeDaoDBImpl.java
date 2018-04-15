package by.htp.library.dao.impl;

import static by.htp.library.dao.util.DBConnectionHelper.connect;
import static by.htp.library.dao.util.DBConnectionHelper.disconnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import by.htp.library.bean.Employee;
import by.htp.library.dao.EmployeeDao;

public class EmployeeDaoDBImpl implements EmployeeDao {

	private static final String SQL_CREATE_EMPLOYEE = "INSERT INTO employee (name, surname, year) VALUES (?, ?, ?)";
	private static final String SQL_READ_EMPLOYEES = "SELECT * FROM employee";
	private static final String SQL_READ_EMPLOYEE_BY_ID = "SELECT * FROM employee WHERE emp_id = ?";
	private static final String SQL_READ_EMPLOYEES_BY_SURNAME = "SELECT * FROM employee WHERE surname = ?";
	private static final String SQL_UPDATE_EMPLOYEE_BY_ID = "UPDATE employee SET name = ?, surname = ?, year = ?  WHERE emp_id = ?";
	private static final String SQL_DELETE_EMPLOYEE_BY_ID = "DELETE FROM employee WHERE emp_id = ?";

	public void create(Employee entity) {

		Connection connection = connect();

		try {

			PreparedStatement ps = connection.prepareStatement(SQL_CREATE_EMPLOYEE);
			
			ps.setString(1, entity.getName());
			ps.setString(2, entity.getSurname());
			ps.setInt(3, entity.getYear());
			
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		disconnect(connection);

	}

	public List<Employee> readAll() {

		List<Employee> employees = new ArrayList<>();

		Connection connection = connect();

		try {
			
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery(SQL_READ_EMPLOYEES);

			while (rs.next()) {
				
				Employee employee = new Employee();
				employee.setId(rs.getInt("emp_id"));
				employee.setName(rs.getString("name"));
				employee.setSurname(rs.getString("surname"));
				employee.setYear(rs.getInt("year"));

				employees.add(employee);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		disconnect(connection);

		return employees;
	}

	public Employee read(int id) {

		Connection connection = connect();
		Employee employee = new Employee();

		try {
			
			PreparedStatement ps = connection.prepareStatement(SQL_READ_EMPLOYEE_BY_ID);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				employee.setId(rs.getInt("emp_id"));
				employee.setName(rs.getString("name"));
				employee.setSurname(rs.getString("surname"));
				employee.setYear(rs.getInt("year"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		disconnect(connection);

		return employee;
	}

	public List<Employee> readBySurname(String surname) {

		List<Employee> employees = new ArrayList<>();
		Connection connection = connect();

		try {
			
			PreparedStatement ps = connection.prepareStatement(SQL_READ_EMPLOYEES_BY_SURNAME);
			ps.setString(1, surname);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Employee employee = new Employee();
				employee.setId(rs.getInt("emp_id"));
				employee.setName(rs.getString("name"));
				employee.setSurname(rs.getString("surname"));
				employee.setYear(rs.getInt("year"));

				employees.add(employee);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		disconnect(connection);

		return employees;
	}

	public void update(int id, Employee entity) {

		Connection connection = connect();

		try {
			
			PreparedStatement ps = connection.prepareStatement(SQL_UPDATE_EMPLOYEE_BY_ID);
			
			ps.setString(1, entity.getName());
			ps.setString(2, entity.getSurname());
			ps.setInt(3, entity.getYear());
			ps.setInt(4, id);
			
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		disconnect(connection);
	}

	public void delete(int id) {

		Connection connection = connect();

		try {
			
			PreparedStatement ps = connection.prepareStatement(SQL_DELETE_EMPLOYEE_BY_ID);
			ps.setInt(1, id);
			
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		disconnect(connection);
	}
}
