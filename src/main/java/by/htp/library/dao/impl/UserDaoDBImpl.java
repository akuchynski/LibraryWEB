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
import by.htp.library.bean.ROLE;
import by.htp.library.bean.User;
import by.htp.library.dao.EmployeeDao;
import by.htp.library.dao.UserDao;

public class UserDaoDBImpl implements UserDao {

	private static final String SQL_CREATE_USER = "INSERT INTO user (user_id, login, email, password) VALUES (?, ?, ?, ?)";
	private static final String SQL_READ_USERS = "SELECT * FROM user";
	private static final String SQL_READ_USER_BY_LOGIN = "SELECT * FROM user WHERE login = ?";
	private static final String SQL_READ_USER_BY_LOGIN_PASSWORD = "SELECT * FROM user WHERE login = ? AND password = ?";
	private static final String SQL_READ_USER_ROLE_BY_LOGIN_PASSWORD = "SELECT role FROM user WHERE login = ? AND password = ?";

	@Override
	public void create(User user) {

		Connection connection = connect();

		try {

			PreparedStatement ps = connection.prepareStatement(SQL_CREATE_USER);
			
			ps.setInt(1, user.getId());
			ps.setString(2, user.getLogin());
			ps.setString(3, user.getEmail());
			ps.setString(4, user.getPassword());
			
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		disconnect(connection);
	}
	
	@Override
	public void createUserByEmployee(User user, Employee employee) {
		
		Connection connection = connect();

		try {

			PreparedStatement ps = connection.prepareStatement(SQL_CREATE_USER);
			
			EmployeeDao employeedao = new EmployeeDaoDBImpl();
			int empId = employeedao.readIdEmployee(employee);
			
			ps.setInt(1, empId);
			ps.setString(2, user.getLogin());
			ps.setString(3, user.getEmail());
			ps.setString(4, user.getPassword());
			
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		disconnect(connection);
	}
	
	@Override
	public User readByLogin(String login) {

		User user = new User();
		Connection connection = connect();

		try {

			PreparedStatement ps = connection.prepareStatement(SQL_READ_USER_BY_LOGIN);
			ps.setString(1, login);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				user.setId(rs.getInt("user_id"));
				user.setLogin(rs.getString("login"));
				user.setPassword(rs.getString("password"));
				user.setEmail(rs.getString("email"));
				user.setRole(ROLE.valueOf(rs.getString("role")));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		disconnect(connection);

		return user;
	}
	
	public List<User> readAll() {

		List<User> users = new ArrayList<>();
		Connection connection = connect();

		try {

			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery(SQL_READ_USERS);

			while (rs.next()) {
				User user = new User();
				user.setId(rs.getInt("user_id"));
				user.setLogin(rs.getString("login"));
				user.setPassword(rs.getString("password"));
				user.setEmail(rs.getString("email"));
				user.setRole(ROLE.valueOf(rs.getString("role")));
				
				users.add(user);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		disconnect(connection);

		return users;
	}

	public boolean userIsExist(String login, String password) {

		boolean result = false;
		Connection connection = connect();

		try {

			PreparedStatement ps = connection.prepareStatement(SQL_READ_USER_BY_LOGIN_PASSWORD);
			ps.setString(1, login);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				result = true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		disconnect(connection);

		return result;
	}
	
	@Override
	public ROLE getRoleByLoginPassword(String login, String password) {
		
		ROLE role = ROLE.UNKNOWN;
		Connection connection = connect();

		try {

			PreparedStatement ps = connection.prepareStatement(SQL_READ_USER_ROLE_BY_LOGIN_PASSWORD);
			ps.setString(1, login);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				role = ROLE.valueOf(rs.getString("role"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		disconnect(connection);

		
		return role;
	}

	@Override
	public User read(int id) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void update(int id, User entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub

	}

}
