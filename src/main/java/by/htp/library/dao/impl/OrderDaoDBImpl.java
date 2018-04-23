package by.htp.library.dao.impl;

import static by.htp.library.dao.util.DBConnectionHelper.connect;
import static by.htp.library.dao.util.DBConnectionHelper.disconnect;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import by.htp.library.bean.Book;
import by.htp.library.bean.Employee;
import by.htp.library.bean.Order;
import by.htp.library.bean.STATUS;
import by.htp.library.dao.BookDao;
import by.htp.library.dao.EmployeeDao;
import by.htp.library.dao.OrderDao;

public class OrderDaoDBImpl implements OrderDao {

	private static final String SQL_CREATE_ORDER_BY_BOOK_AND_EMPLOYEE = "INSERT INTO user (user_id, login, email, password) VALUES (?, ?, ?, ?)";
	private static final String SQL_READ_ORDERS = "SELECT * FROM employee_book";
	private static final String SQL_READ_USER_ROLE_BY_LOGIN_PASSWORD = "SELECT role FROM user WHERE login = ? AND password = ?";
	
	@Override
	public void createOrderByBookAndEmployee(Book book, Employee employee) {

	}


	@Override
	public List<Order> readAll() {
		
		BookDao bookdao = new BookDaoDBImpl();
		EmployeeDao employeedao = new EmployeeDaoDBImpl();

		List<Order> orders = new ArrayList<>();
		Connection connection = connect();

		try {

			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery(SQL_READ_ORDERS);

			while (rs.next()) {
				
				Order order = new Order();
				order.setId(rs.getInt("order_id"));
				order.setBook(bookdao.read(rs.getInt("book_id")));
				order.setEmployee(employeedao.read(rs.getInt("employee_id")));
				order.setDays(rs.getInt("days"));
				order.setDate(rs.getDate("date"));	
				order.setStatus(STATUS.valueOf(rs.getString("status")));
				
				orders.add(order);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		disconnect(connection);

		return orders;
	}
	
	@Override
	public void create(Order entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Order read(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(int id, Order entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		
	}
	
}
