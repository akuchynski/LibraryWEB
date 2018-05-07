package by.htp.library.dao.impl;

import static by.htp.library.dao.util.DBConnectionHelper.connect;
import static by.htp.library.dao.util.DBConnectionHelper.disconnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import by.htp.library.bean.Order;
import by.htp.library.bean.STATUS;
import by.htp.library.dao.OrderDao;

public class OrderDaoDBImpl implements OrderDao {

	private static final String SQL_CREATE_ORDER = "INSERT INTO employee_book (book_id, employee_id, days, date, status) VALUES (?, ?, ?, ?, ?)";
	private static final String SQL_READ_ORDERS = "SELECT * FROM employee_book";
	private static final String SQL_READ_ORDER_BY_ID = "SELECT * FROM employee_book WHERE order_id = ?";
	private static final String SQL_READ_ORDERS_BY_STATUS = "SELECT * FROM employee_book WHERE status = ?";
	private static final String SQL_READ_ORDERS_BY_EMPLOYEE_ID = "SELECT * FROM employee_book WHERE employee_id = ?";
	private static final String SQL_UPDATE_ORDER_BY_ID = "UPDATE employee_book SET book_id = ?, employee_id = ?, days = ? WHERE order_id = ?";
	
	@Override
	public void create(Order entity) {
		
		Connection connection = connect();

		try {

			PreparedStatement ps = connection.prepareStatement(SQL_CREATE_ORDER);
			
			ps.setInt(1, entity.getBookId());
			ps.setInt(2, entity.getEmplId());
			ps.setInt(3, entity.getDays());
			ps.setDate(4, java.sql.Date.valueOf(java.time.LocalDate.now()), new GregorianCalendar(TimeZone.getTimeZone("GMT+3")));
			ps.setString(5, entity.getStatus().name());
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		disconnect(connection);

	}

	@Override
	public Order read(int id) {
		Connection connection = connect();
		Order order = new Order();

		try {

			PreparedStatement ps = connection.prepareStatement(SQL_READ_ORDER_BY_ID);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				
				order.setId(rs.getInt("order_id"));
				order.setBookId(rs.getInt("book_id"));
				order.setEmplId(rs.getInt("employee_id"));
				order.setDays(rs.getInt("days"));
				order.setDate(rs.getDate("date"));	
				order.setStatus(STATUS.valueOf(rs.getString("status")));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		disconnect(connection);

		return order;
	}

	@Override
	public List<Order> readAll() {

		List<Order> orders = new ArrayList<>();
		Connection connection = connect();

		try {

			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery(SQL_READ_ORDERS);

			while (rs.next()) {
				
				Order order = new Order();
				order.setId(rs.getInt("order_id"));
				order.setBookId(rs.getInt("book_id"));
				order.setEmplId(rs.getInt("employee_id"));
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
	public List<Order> readOrdersByStatus(String status) {

		List<Order> orders = new ArrayList<>();
		Connection connection = connect();

		try {

			PreparedStatement ps = connection.prepareStatement(SQL_READ_ORDERS_BY_STATUS);
			ps.setString(1, status);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				
				Order order = new Order();
				order.setId(rs.getInt("order_id"));
				order.setBookId(rs.getInt("book_id"));
				order.setEmplId(rs.getInt("employee_id"));
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
	public List<Order> readOrdersByEmployeeId(int emplId) {

		List<Order> orders = new ArrayList<>();
		Connection connection = connect();

		try {

			PreparedStatement ps = connection.prepareStatement(SQL_READ_ORDERS_BY_EMPLOYEE_ID);
			ps.setInt(1, emplId);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				
				Order order = new Order();
				order.setId(rs.getInt("order_id"));
				order.setBookId(rs.getInt("book_id"));
				order.setEmplId(rs.getInt("employee_id"));
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
	public void update(int id, Order entity) {
		Connection connection = connect();

		try {

			PreparedStatement ps = connection.prepareStatement(SQL_UPDATE_ORDER_BY_ID);
			
			ps.setInt(1, entity.getBookId());
			ps.setInt(2, entity.getEmplId());
			ps.setInt(3, entity.getDays());
			ps.setInt(4, id);
			
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		disconnect(connection);
		
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		
	}
	
}
