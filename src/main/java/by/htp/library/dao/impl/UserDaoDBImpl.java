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
	private static final String SQL_READ_USER_BY_ID = "SELECT * FROM user WHERE user_id = ?";
	private static final String SQL_READ_USER_BY_LOGIN = "SELECT * FROM user WHERE login = ?";
	private static final String SQL_READ_USERS = "SELECT * FROM user";
	private static final String SQL_READ_LAST_USERS = "SELECT * FROM user order by user_id desc limit ?";
	private static final String SQL_READ_USER_BY_LOGIN_PASSWORD = "SELECT * FROM user WHERE login = ? AND password = ?";
	private static final String SQL_READ_USER_ROLE_BY_LOGIN_PASSWORD = "SELECT role FROM user WHERE login = ? AND password = ?";
	private static final String SQL_UPDATE_USER_BY_ID = "UPDATE user SET email = ?, password = ?, active = ? WHERE user_id = ?";
	private static final String SQL_DELETE_USER_BY_ID = "DELETE FROM user WHERE user_id = ?";

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
	public User read(int id) {

		User user = new User();
		Connection connection = connect();

		try {

			PreparedStatement ps = connection.prepareStatement(SQL_READ_USER_BY_ID);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				user.setId(rs.getInt("user_id"));
				user.setLogin(rs.getString("login"));
				user.setPassword(rs.getString("password"));
				user.setEmail(rs.getString("email"));
				user.setRole(ROLE.valueOf(rs.getString("role")));
				user.setActive(rs.getBoolean("active"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		disconnect(connection);

		return user;
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
				user.setActive(rs.getBoolean("active"));
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
				user.setActive(rs.getBoolean("active"));

				users.add(user);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		disconnect(connection);

		return users;
	}

	public List<User> readLastUsers(int count) {

		List<User> users = new ArrayList<>();
		Connection connection = connect();

		try {

			PreparedStatement ps = connection.prepareStatement(SQL_READ_LAST_USERS);
			ps.setInt(1, count);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				User user = new User();
				user.setId(rs.getInt("user_id"));
				user.setLogin(rs.getString("login"));
				user.setPassword(rs.getString("password"));
				user.setEmail(rs.getString("email"));
				user.setRole(ROLE.valueOf(rs.getString("role")));
				user.setActive(rs.getBoolean("active"));

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
	public boolean userIsActivated(String login) {
		
		boolean activate = false;
		Connection connection = connect();

		try {

			PreparedStatement ps = connection.prepareStatement(SQL_READ_USER_BY_LOGIN);
			ps.setString(1, login);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				if(rs.getInt("deactivate") != 0) {
					activate = true;
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		disconnect(connection);

		return activate;
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
	public void update(int id, User entity) {
		Connection connection = connect();

		try {

			PreparedStatement ps = connection.prepareStatement(SQL_UPDATE_USER_BY_ID);

			ps.setString(1, entity.getEmail());
			ps.setString(2, entity.getPassword());
			ps.setBoolean(3, entity.isActive());
			ps.setInt(4, id);

			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		disconnect(connection);

	}

	@Override
	public void delete(int id) {
		Connection connection = connect();

		try {

			PreparedStatement ps = connection.prepareStatement(SQL_DELETE_USER_BY_ID);
			ps.setInt(1, id);

			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		disconnect(connection);

	}
}
